package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

import java.io.Serializable;

public class ArtemisRuleDecorator extends StandardRuleDecorator {

    @Override
    public void play(PlayerMove move, Turn turn, Model model) {

        if(move instanceof PlayerMoveEnd){
            if(isEndAllowed(move, turn)) {
                model.endMessage(move,turn,model);
                move.getPlayer().getMyCard().setUsingCard(false);
                move.getPlayer().getMyCard().addCustomStep(2, "B");
            }
            else {
                model.notify(gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
                //move.getView().reportError(gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
            }return;
        }

        if(!model.isRightWorker(move, turn)){
            model.notify(gameMessage.insertAgain);
            //move.getView().reportError(gameMessage.insertAgain);
            return;
        }

        if(!checkStepType(move,turn)){
            model.notify(gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
            //move.getView().reportError(gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getRow()<0 || move.getRow()>=5 || move.getColumn()<0 || move.getColumn()>=5){
            model.notify(gameMessage.wrongInputMessage+"\n"+gameMessage.insertAgain);
            //move.getView().reportError(gameMessage.wrongInputMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!move.getWorker().getWorkerPosition().hasFreeCellClosed(model.getBoard().getPlayingBoard())){
            //this worker is stuck
            move.getWorker().setStuck(true);
            model.notify(gameMessage.workerStuck+"\n"+gameMessage.insertAgain);
            //move.getView().reportError(gameMessage.workerStuck+"\n"+gameMessage.insertAgain);
            //check if both worker1 && worker2 are stuck player lose
            if(model.isPlayerStuck(move)){
                //this player lose, both workers are stuck
                model.deletePlayer(move);
            }
            return;
        }

        if(!model.isReachableCell(move)){
            model.notify(gameMessage.notReachableCellMessage+"\n"+gameMessage.insertAgain);
            //move.getView().reportError(gameMessage.notReachableCellMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!model.isEmptyCell(move)){
            //read worker position and check if there are some empty cell where he can move in.
            model.notify(gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
            //move.getView().reportError(gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getMoveOrBuild().equals("M") ){
            if(!model.isLevelDifferenceAllowed(move)){
                model.notify(gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
                //move.getView().reportError(gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            if(model.checkStep(move, turn, model))
                //model.performMove(move);
                move(move, model, turn);
        }
        else{
            if(model.checkStep(move, turn, model))
                //model.performBuild(move);
                build(move, model, turn);
        }
    }

    @Override
    public void move(PlayerMove move, Model model, Turn turn) {
        //check if is second step, in this case check is the cell where i want to move in is different from the cell of first step
        boolean hasWon = model.hasWon(move);

        if(turn.getPlayerTurn(move.getPlayer()).getI()==2){
            //is the second move for this player, so he wants to use the card
            if(model.getBoard().getCell(move.getRow(),move.getColumn()).equals(turn.getPlayerTurn(move.getPlayer()).getStepI(1).getCellFrom())){
                model.sendError(move.getColor().toString()+" "+gameMessage.invalidMoveArtemis+"\n"+gameMessage.insertAgain);
                return;
            }
            move.getPlayer().getMyCard().setUsingCard(true);
        }
        model.setStep(move, turn, model);

        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).setFreeSpace(true);
        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).deleteCurrWorker();

        move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(),move.getColumn()));
        (model.getBoard().getCell(move.getRow(),move.getColumn())).setFreeSpace(false);
        model.getBoard().getCell(move.getRow(),move.getColumn()).setCurrWorker(move.getWorker());


        model.notifyView(move,hasWon);
    }

    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        if(turn.getPlayerTurn(move.getPlayer()).getI()==2){
            move.getPlayer().getMyCard().addCustomStep(2,"END");
        }
        model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell();
        model.setStep(move, turn, model);

        model.notifyView(move,false);
    }
    public boolean checkStepType(PlayerMove message, Turn turn){
        if(message.getPlayer().getMyCard().isUsingCard())
            return message.getMoveOrBuild().equals(message.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(message.getPlayer()).getI()));
        else
            return  message.getMoveOrBuild().equals(message.getPlayer().getMyCard().getStandardStepLetter(turn.getPlayerTurn(message.getPlayer()).getI()))
                    || message.getMoveOrBuild().equals(message.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(message.getPlayer()).getI()));
    }
    public boolean isEndAllowed(PlayerMove move, Turn turn){
        return move.getPlayer().getMyCard().isUsingCard() ? (move.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(move.getPlayer()).getI())).equals("END") :
                (move.getPlayer().getMyCard().getStandardStepLetter(turn.getPlayerTurn(move.getPlayer()).getI())).equals("END");
    }

}