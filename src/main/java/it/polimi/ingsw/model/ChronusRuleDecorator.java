package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

public class ChronusRuleDecorator extends StandardRuleDecorator {

    @Override
    public void play(PlayerMove move, Turn turn, Model model) {
        if(move instanceof PlayerMoveEnd){
            if(cronusrule(model)){
                model.notifyView(move,true);
                return;
            }
            if(isEndAllowed(move, turn)) {
                model.endMessage(move,turn,model);
                move.getPlayer().getMyCard().setUsingCard(false);
            }
            else
                model.sendError(move.getColor().toString()+" "+gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
            return;
        }
        if(cronusrule(model)){
            model.notifyView(move,true);
        }
        if(!model.isRightWorker(move, turn)){
            model.sendError(move.getColor().toString()+" "+gameMessage.insertAgain);
            return;
        }

        if(!checkStepType(move,turn)){
            move.getView().reportError(gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
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

    public boolean cronusrule(Model model){
        int conter=0;
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(model.getBoard().getCell(i, j).isCronusRule()){
                    conter++;
                }
                }
            }
        return conter > 4;
    }

}
