package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

public class PoseidonRuleDecorator extends StandardRuleDecorator {
    private boolean ican;
    @Override
    public void play(PlayerMove move, Turn turn, Model model) {
        if(move instanceof PlayerMoveEnd){
            if(isEndAllowedDecorator(move, turn)) {
                model.endMessage(move,turn,model);
                move.getPlayer().getMyCard().setUsingCard(false);
                ican=false;
            }
            else
                move.getView().reportError(gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!isRightWorkerDecorator(move, turn)){
            if(turn.getPlayerTurn(move.getPlayer()).getI()==3){
                turn.getPlayerTurn(move.getPlayer()).setTurnWorker(move.getWorker());
                ican=true;
            }
            else{
                move.getView().reportError(gameMessage.wrongWorker);
                move.getView().reportError(gameMessage.insertAgain);
                return;
            }
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
    public void build(PlayerMove move, Model model, Turn turn) {
        if(ican){
            if(!(move.getWorker().getWorkerPosition().getLevel()==0)){
                move.getView().getClientConnection().send(gameMessage.invalidBuildPoseidon);
                ican=false;
                return;
        }
            else {
                model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell();
                move.getPlayer().getMyCard().setUsingCard(true);
            }
        }
        else if(turn.getPlayerTurn(move.getPlayer()).getI()<3) { model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell(); }
            else {
                move.getView().getClientConnection().send(gameMessage.wrongStepMessage );
                return; }

        model.setStep(move, turn, model);
        model.notifyView(move,false);
    }

    public boolean isRightWorkerDecorator(PlayerMove move, Turn turn){
        return (turn.getPlayerTurn(move.getPlayer()).isFirstStep()) || turn.getPlayerTurn(move.getPlayer()).getTurnWorker().equals(move.getWorker());
    }
    public boolean isEndAllowedDecorator(PlayerMove move, Turn turn){
        return move.getPlayer().getMyCard().isUsingCard() ? (move.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(move.getPlayer()).getI())).equals("END")||turn.getPlayerTurn(move.getPlayer()).getI()>3 :
                (move.getPlayer().getMyCard().getStandardStepLetter(turn.getPlayerTurn(move.getPlayer()).getI())).equals("END");
    }

}
