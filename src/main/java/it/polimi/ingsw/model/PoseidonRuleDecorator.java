package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

/**
 * End of Your Turn: If your
 * unmoved Worker is on the
 * ground level, it may build up to
 * three times.
 */
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
                model.sendError(move.getColor().toString()+" "+gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!isRightWorkerDecorator(move, turn)){
            if(turn.getPlayerTurn(move.getPlayer()).getI()==3){
                turn.getPlayerTurn(move.getPlayer()).setTurnWorker(move.getWorker());
                ican=true;
            }
            else{
                model.sendError(move.getColor().toString()+" "+gameMessage.wrongWorker+" "+gameMessage.insertAgain);
                return;
            }
        }

        if(!checkStepType(move,turn)){
            model.sendError(move.getColor().toString()+" "+gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getRow()<0 || move.getRow()>=5 || move.getColumn()<0 || move.getColumn()>=5){
            model.sendError(move.getColor().toString()+" "+gameMessage.wrongInputMessage+"\n"+gameMessage.insertAgain);
            return;
        }
        if(turn.getPlayerTurn(move.getPlayer()).getI()>2){
            if(!move.getWorker().getWorkerPosition().canBuildInCells(model.getBoard().getPlayingBoard())){
                model.sendError(move.getColor().toString()+" "+gameMessage.invalidBuildPoseidon2);
                return;
            }
        }
        else {
            if(move.getMoveOrBuild().equals("M") && !move.getWorker().getWorkerPosition().hasFreeCellClosed(model.getBoard().getPlayingBoard())){
            //this worker is stuck
                 move.getWorker().setStuck(true);
                 model.sendError(move.getColor().toString()+" "+gameMessage.workerStuck+"\n"+gameMessage.insertAgain);
            //check if both worker1 && worker2 are stuck player lose
                 if(model.isPlayerStuck(move)){
                //this player lose, both workers are stuck
                     model.deletePlayer(move);
                }
            return;
            }
        }

        if(!model.isReachableCell(move)){
            model.sendError(move.getColor().toString()+" "+gameMessage.notReachableCellMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!model.isEmptyCell(move)){
            //read worker position and check if there are some empty cell where he can move in.
            model.sendError(move.getColor().toString()+" "+gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getMoveOrBuild().equals("M") ){
            if(!model.isLevelDifferenceAllowed(move)){
                model.sendError(move.getColor().toString()+" "+gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            if(model.fillStepInfo(move, turn, model))
                //model.performMove(move);
                move(move, model, turn);
        }
        else{
            if(model.fillStepInfo(move, turn, model))
                //model.performBuild(move);
                build(move, model, turn);
        }
    }


    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        if(ican){
            if(!(move.getWorker().getWorkerPosition().getLevel()==0)){
                model.sendError(move.getColor().toString()+" "+gameMessage.invalidBuildPoseidon);
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
                model.sendError(move.getColor().toString()+" "+gameMessage.wrongStepMessage );
                return; }

        model.setStep(move, turn, model);
        model.notifyView(move,false);
    }

    public boolean isIcan() {
        return ican;
    }

    public boolean isRightWorkerDecorator(PlayerMove move, Turn turn){
        return (turn.getPlayerTurn(move.getPlayer()).isFirstStep()) || turn.getPlayerTurn(move.getPlayer()).getTurnWorker().equals(move.getWorker());
    }
    public boolean isEndAllowedDecorator(PlayerMove move, Turn turn){
        return move.getPlayer().getMyCard().isUsingCard() ? (move.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(move.getPlayer()).getI())).equals("END")||turn.getPlayerTurn(move.getPlayer()).getI()>3 :
                (move.getPlayer().getMyCard().getStandardStepLetter(turn.getPlayerTurn(move.getPlayer()).getI())).equals("END");
    }

}
