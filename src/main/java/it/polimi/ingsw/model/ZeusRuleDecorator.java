package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;


public class ZeusRuleDecorator extends StandardRuleDecorator {
    /** this method allows Zeus to build under yourself
     * @param move  cell, worker and type of move
     * @param turn
     * @param model
     */
    @Override
    public void play(PlayerMove move, Turn turn, Model model) {
        if(move instanceof PlayerMoveEnd){
            if(isEndAllowed(move, turn)) {
                model.endMessage(move,turn,model);
                move.getPlayer().getMyCard().setUsingCard(false);
            }
            else
                model.sendError(move.getColor().toString()+" "+gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!model.isRightWorker(move, turn)){
            model.sendError(move.getColor().toString()+" "+gameMessage.insertAgain);
            return;
        }

        if(!checkStepType(move,turn)){
            model.sendError(move.getColor().toString()+" "+gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getRow()<0 || move.getRow()>=5 || move.getColumn()<0 || move.getColumn()>=5){
            model.sendError(move.getColor().toString()+" "+gameMessage.wrongInputMessage+"\n"+gameMessage.insertAgain);
            return;
        }

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



        if(move.getMoveOrBuild().equals("M") ){
            if(!model.isReachableCell(move)){
                model.sendError(move.getColor().toString()+" "+gameMessage.notReachableCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }

            if(!model.isEmptyCell(move)){
                //read worker position and check if there are some empty cell where he can move in.
                model.sendError(move.getColor().toString()+" "+gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            if(!model.isLevelDifferenceAllowed(move)){
                model.sendError(move.getColor().toString()+" "+gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            if(model.fillStepInfo(move, turn, model))
                //model.performMove(move);
                move(move, model, turn);
        }
        //if the worker have the Zeus power can build under your self
        else{
            if(!model.isReachableCell(move)&&!Zeusbuild(move,model,turn)){
                model.sendError(move.getColor().toString()+" "+gameMessage.notReachableCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }

            if(!model.isEmptyCell(move)&&!Zeusbuild(move,model,turn)){
                //read worker position and check if there are some empty cell where he can move in.
                model.sendError(move.getColor().toString()+" "+gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            if(ZeusTohight(move, model)&&Zeusbuild(move, model, turn)){
                model.sendError(move.getColor().toString()+" "+gameMessage.invalidBuildZeus+"\n"+gameMessage.insertAgain);
                return;
            }
            if(model.fillStepInfo(move, turn, model))
                //model.performBuild(move);
                build(move, model, turn);
        }
    }

    /**
     * @param move
     * @param model
     * @param turn
     * @return true if the cell where the worker wants to build is the one where he is
     */
 public boolean Zeusbuild(PlayerMove move, Model model,Turn turn){
     return model.getBoard().getCell(move.getRow(), move.getColumn()).equals(turn.getPlayerTurn(move.getPlayer()).getStepI(1).getCellTo());
 }


    /**
     * @param move
     * @param model
     * @return true if the cell where the worker wants to build is at level three
     */
 public boolean ZeusTohight(PlayerMove move, Model model){
     return model.getBoard().getCell(move.getRow(), move.getColumn()).getLevel()==3;
 }
}