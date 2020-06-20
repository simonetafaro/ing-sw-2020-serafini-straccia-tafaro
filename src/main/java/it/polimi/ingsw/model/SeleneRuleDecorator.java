package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;
/*Selene effect:
Instead of your normal build, your
female Worker may build a dome at any level
regardless of which Worker moved.*/
public class SeleneRuleDecorator extends StandardRuleDecorator {
    private boolean IamWoman=false;
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

        if(!isRightWorkerDecorator(move, turn)){
            if(move.getWorker().getWorkerNum()==1)
                IamWoman=true;
            else{
                model.sendError(move.getColor().toString()+" "+gameMessage.wrongWorker);
                model.sendError(move.getColor().toString()+" "+gameMessage.insertAgain);
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
    /*
    this method allows you to build a
    copula for women instead of any build*/
    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        if(IamWoman){
            model.getBoard().getCell(move.getRow(), move.getColumn()).setLevel(4);
        }
        else {
            if(move.getWorker().getWorkerNum()==1){
                model.sendError(move.getColor().toString()+" "+gameMessage.atlasCard);
            String buildDome = move.getView().getClientConnection().read();
            if (buildDome.toUpperCase().equals("YES"))
                model.getBoard().getCell(move.getRow(), move.getColumn()).setLevel(4);
            else
                model.getBoard().getCell(move.getRow(), move.getColumn()).buildInCell();
            }
            else{
                model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell();
            }
        }
        IamWoman=false;
        model.setStep(move, turn, model);
        model.notifyView(move,false);
    }
    public boolean isRightWorkerDecorator(PlayerMove move, Turn turn){
        return (turn.getPlayerTurn(move.getPlayer()).isFirstStep()) || turn.getPlayerTurn(move.getPlayer()).getTurnWorker().equals(move.getWorker());
    }
}