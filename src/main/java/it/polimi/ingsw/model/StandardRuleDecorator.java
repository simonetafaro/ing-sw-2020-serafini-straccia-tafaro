package it.polimi.ingsw.model;
import it.polimi.ingsw.utils.gameMessage;

/** Decorator for standard game: move - build - end */
public class StandardRuleDecorator implements CardRuleDecorator {

    @Override
    public void play(PlayerMove move, Turn turn, Model model) {
        if(move instanceof PlayerMoveEnd){
            if(isEndAllowed(move, turn)) {
                model.endMessage(move,turn,model);
                move.getPlayer().getMyCard().setUsingCard(false);
            }
            else {
                model.notify(gameMessage.endYourTurn + "\n" + gameMessage.insertAgain);
                //move.getView().reportError(gameMessage.endYourTurn + "\n" + gameMessage.insertAgain);
            }
            return;
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
            model.notify(gameMessage.notReachableCellMessage+"\n"+gameMessage.insertAgain);
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
    public void move(PlayerMove move, Model model, Turn turn) {
        boolean hasWon = model.hasWon(move);

        model.setStep(move, turn, model);
        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).setFreeSpace(true);
        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).deleteCurrWorker();
        move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(),move.getColumn()));
        (model.getBoard().getCell(move.getRow(),move.getColumn())).setFreeSpace(false);
        model.getBoard().getCell(move.getRow(),move.getColumn()).setCurrWorker(move.getWorker());

        model.notify(move);
        //model.notifyView(move,hasWon);
    }
    public void build(PlayerMove move, Model model, Turn turn) {
        model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell();

        model.setStep(move, turn, model);
        model.notify(move);
       //model.notifyView(move,false);
    }
    public boolean checkStepType(PlayerMove message, Turn turn){
        if(message.getPlayer().getMyCard().isUsingCard()) {
            return message.getMoveOrBuild().equals(message.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(message.getPlayer().getID()).getI()));
        }else
            return  message.getMoveOrBuild().equals(message.getPlayer().getMyCard().getStandardStepLetter(turn.getPlayerTurn(message.getPlayer().getID()).getI()))
                    || message.getMoveOrBuild().equals(message.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(message.getPlayer().getID()).getI()));
    }
    public boolean isEndAllowed(PlayerMove move, Turn turn){
        return move.getPlayer().getMyCard().isUsingCard() ? (move.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(move.getPlayer().getID()).getI())).equals("END") :
                (move.getPlayer().getMyCard().getStandardStepLetter(turn.getPlayerTurn(move.getPlayer().getID()).getI())).equals("END");
    }

}
