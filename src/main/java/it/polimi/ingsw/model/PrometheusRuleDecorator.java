package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

public class PrometheusRuleDecorator extends StandardRuleDecorator implements CardRuleDecorator {

    private boolean canGoUp=true;
    @Override
    public void play(PlayerMove move, Turn turn, Model model) {
        System.out.println("Prometheus rule decorator - play");

        if(move instanceof PlayerMoveEnd){
            if(isEndAllowed(move, turn)) {
                model.endMessage(move,turn,model);
                move.getPlayer().getMyCard().setUsingCard(false);
                move.getPlayer().getMyCard().setMossa3("B");
                canGoUp=true;
            }
            else
                move.getView().reportError(gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!model.isRightWorker(move, turn)){
            move.getView().reportError(gameMessage.insertAgain);
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
            if(!isLevelDifferenceAllowed(move, model)){
                move.getView().reportError(gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            if(model.checkStep(move, turn, model))
                move(move, model, turn);
        }
        else{ //"B"
            if((turn.getPlayerTurn(move.getPlayer()).getI()==1) && !canBuildInAndThenMove(move, model)){
                move.getView().reportError(gameMessage.invalidMovePrometheus+"\n"+gameMessage.insertAgain);
                return;
            }
            if(model.checkStep(move, turn, model))
                //model.performBuild(move);
                build(move, model, turn);
        }
    }

    @Override
    public void move(PlayerMove move, Model model, Turn turn) {
        System.out.println("Prometheus Move");
        boolean hasWon = model.hasWon(move);

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
        System.out.println("Prometheus Build");
        if(turn.getPlayerTurn(move.getPlayer()).getI()==1){
            move.getPlayer().getMyCard().setUsingCard(true);
            canGoUp= false;
        }
        if(turn.getPlayerTurn(move.getPlayer()).getI()==2){
            move.getPlayer().getMyCard().setMossa3("END");
        }
        model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell();
        model.setStep(move, turn, model);
        model.notifyView(move,false);
    }

    public boolean canBuildInAndThenMove(PlayerMove move, Model model){
        //se construisce in model.getBoard().getPlayingBoard(move.getRow(),move.getColumn()) ha celle libere in cui muoversi senza salire
        boolean bool=false;
        Cell[][] board= model.getBoard().getPlayingBoard();
        Cell from = move.getWorker().getWorkerPosition();
        model.getBoard().getCell(move.getRow(),move.getColumn()).setLevel(model.getBoard().getCell(move.getRow(),move.getColumn()).getLevel()+1);

        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                if((from.getPosX()+i)>=0 && (from.getPosY()+j)>=0 && (from.getPosY()+j)<5 && (from.getPosX()+i)<5 &&
                        board[from.getPosX()+i][from.getPosY()+j].getLevel()- from.getLevel()<=0) {
                    if ((board[from.getPosX() + i][from.getPosY() + j]).isFree() ){
                        bool = true;
                    }
                }
            }
        }
        model.getBoard().getCell(move.getRow(),move.getColumn()).setLevel(model.getBoard().getCell(move.getRow(),move.getColumn()).getLevel()-1);
        return bool;
    }

    public boolean isLevelDifferenceAllowed(PlayerMove move, Model model){
        return canGoUp ? ((model.getBoard().getCell(move.getRow(), move.getColumn())).getLevel() - ((move.getWorker().getWorkerPosition()).getLevel()) <= 1) :
                ((model.getBoard().getCell(move.getRow(), move.getColumn())).getLevel() - ((move.getWorker().getWorkerPosition()).getLevel()) <= 0);

    }

}
