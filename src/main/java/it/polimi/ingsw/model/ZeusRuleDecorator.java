package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;
/* in this class we decorate play*/
public class ZeusRuleDecorator extends StandardRuleDecorator {
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

        if(!move.getWorker().getWorkerPosition().hasFreeCellClosed(model.getBoard().getPlayingBoard())){
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
            if(model.checkStep(move, turn, model))
                //model.performMove(move);
                move(move, model, turn);
        }
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

            if(model.checkStep(move, turn, model))
                //model.performBuild(move);
                build(move, model, turn);
        }
    }
    /*this method allow to the worker to build a block under itself */
 public boolean Zeusbuild(PlayerMove move, Model model,Turn turn){
     return model.getBoard().getCell(move.getRow(), move.getColumn()).equals(turn.getPlayerTurn(move.getPlayer()).getStepI(1).getCellTo());
 }
}
//non fa altro che dire se la cella dove bilto è quella dove sono allora posso
//probabilmente con la logica dei layer devo fare in modo che nel leyer invece di 0 venga messo -1 se so questo potere