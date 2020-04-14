package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

public class StandardRuleDecorator implements CardRuleDecorator {

    //private Model model;
    //private Turn turn;


    @Override
    public void play(PlayerMove move, Turn turn, Model model) {
        System.out.println("Standard rule decorator");
        //this.model= model;
        //this.turn= turn;
        if(move instanceof PlayerMoveEnd){
            if(turn.getPlayerTurn(move.getPlayer()).getI()==3)
                endMessage(move,turn,model);
            else
                move.getView().reportError(gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getRow()<0 || move.getRow()>=5 || move.getColumn()<0 || move.getColumn()>=5){
            move.getView().reportError(gameMessage.wrongInputMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!checkStepType(move,turn)){
            move.getView().reportError(gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!move.getWorker().getWorkerPosition().hasFreeCellClosed(model.getBoard().getPlayingBoard())){
            //this worker is stuck
            move.getWorker().setStuck(true);
            move.getView().reportError(gameMessage.workerStuck+"\n"+gameMessage.insertAgain);
            //check if both worker1 && worker2 are stuck player lose
            if(isPlayerStuck(move)){
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

        // && move.getMoveOrBuild().equals(turn.getPlayerTurn(move.getPlayer()).getCurrStep())

        if(move.getMoveOrBuild().equals("M") ){
            if(!model.isLevelDifferenceAllowed(move)){
                move.getView().reportError(gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            if(setStep(move, turn, model))
                //model.performMove(move);
                move(move, model);
        }
        else{
            if(setStep(move, turn, model))
                //model.performBuild(move);
                build(move, model);
        }
    }

    public void move(PlayerMove move, Model model) {
        boolean hasWon = model.hasWon(move);

        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).setFreeSpace(true);
        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).deleteCurrWorker();

        move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(),move.getColumn()));
        (model.getBoard().getCell(move.getRow(),move.getColumn())).setFreeSpace(false);
        model.getBoard().getCell(move.getRow(),move.getColumn()).setCurrWorker(move.getWorker());

        model.notifyView(move,hasWon);
    }

    public void build(PlayerMove move, Model model) {
        model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell();
        model.notifyView(move,false);
    }

    public boolean checkStepType(PlayerMove message, Turn turn){
        return message.getMoveOrBuild()
                .equals(message.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(message.getPlayer()).getI()));
    }
    public void endMessage(PlayerMove message, Turn turn, Model model){
        turn.getPlayerTurn(message.getPlayer()).resetStep();
        model.endNotifyView(message,false);
    }
    public boolean setStep(PlayerMove move, Turn turn, Model model){
        if(turn.getPlayerTurn(move.getPlayer()).isFirstStep())
            turn.getPlayerTurn(move.getPlayer()).setTurnWorker(move.getWorker());
        if(!turn.getPlayerTurn(move.getPlayer()).getTurnWorker().equals(move.getWorker())){
            move.getView().reportError(gameMessage.wrongWorker);
            return false;
        }
        //set both worker of this player !stuck
        move.getPlayer().getWorker1().setStuck(false);
        move.getPlayer().getWorker2().setStuck(false);
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setType(move.getMoveOrBuild());
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellFrom(move.getWorker().getWorkerPosition());
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellTo(model.getBoard().getCell(move.getRow(),move.getColumn()));
        turn.getPlayerTurn(move.getPlayer()).updateStep();
        return true;
    }
    public boolean isPlayerStuck(PlayerMove move){
        return move.getPlayer().getWorker1().isStuck()&&move.getPlayer().getWorker2().isStuck();
    }

}
