package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

public class ArtemisRuleDecorator extends StandardRuleDecorator implements CardRuleDecorator  {

    @Override
    public void play(PlayerMove move, Turn turn, Model model) {
        System.out.println("Artemis rule decorator- Play");
        //this.model= model;
        //this.turn= turn;

        if(move instanceof PlayerMoveEnd){
            if(isEndAllowed(move, turn)) {
                model.endMessage(move,turn,model);
                move.getPlayer().getMyCard().setUsingCard(false);
                move.getPlayer().getMyCard().setMossa3("B");
            }
            else
                move.getView().reportError(gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!checkStepType(move,turn)){
            move.getView().reportError(gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getRow()<0 || move.getRow()>=5 || move.getColumn()<0 || move.getColumn()>=5){
            move.getView().reportError(gameMessage.wrongInputMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!move.getWorker().getWorkerPosition().hasFreeCellClosed(model.getBoard().getPlayingBoard())){
            //this worker is stuck
            move.getWorker().setStuck(true);
            move.getView().reportError(gameMessage.workerStuck+"\n"+gameMessage.insertAgain);
            //check if both worker1 && worker2 are stuck player lose
            if(model.isPlayerStuck(move)){
                //this player lose, both workers are stuck
                model.deletePlayer(move);
            }
            return;
        }

        if(!model.isReachableCell(move)){
            move.getView().reportError(gameMessage.notReachableCellMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!model.isEmptyCell(move)){
            //read worker position and check if there are some empty cell where he can move in.
            move.getView().reportError(gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getMoveOrBuild().equals("M") ){
            if(!model.isLevelDifferenceAllowed(move)){
                move.getView().reportError(gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
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
        System.out.println("Artemis Move");

        if(turn.getPlayerTurn(move.getPlayer()).getI()==2){
            //is the second move for this player, so he wants to use the card
            if(model.getBoard().getCell(move.getRow(),move.getColumn()) == turn.getPlayerTurn(move.getPlayer()).getStepI(1).getCellFrom()){
                move.getView().reportError(gameMessage.invalidMoveArtemis+"\n"+gameMessage.insertAgain);
                return;
            }
            move.getPlayer().getMyCard().setUsingCard(true);
        }

        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).setFreeSpace(true);
        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).deleteCurrWorker();

        move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(),move.getColumn()));
        (model.getBoard().getCell(move.getRow(),move.getColumn())).setFreeSpace(false);
        model.getBoard().getCell(move.getRow(),move.getColumn()).setCurrWorker(move.getWorker());

        model.setStep(move, turn, model);
        model.notifyView(move,hasWon);
    }

    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        System.out.println("Artemis Build");
        if(turn.getPlayerTurn(move.getPlayer()).getI()==2){
            move.getPlayer().getMyCard().setMossa3("END");
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