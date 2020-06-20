package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

import java.io.Serializable;

public class ApolloRuleDecorator extends StandardRuleDecorator {
    @Override
    public void play(PlayerMove move, Turn turn, Model model) {

            if(move instanceof PlayerMoveEnd){
                if(turn.getPlayerTurn(move.getPlayer()).getI()==3) {
                    model.endMessage(move, turn, model);
                }
                else {
                    model.sendError(move.getColor().toString()+" "+gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
                }return;
            }

            if(!model.isRightWorker(move, turn)){
                model.sendError(move.getColor().toString()+" "+gameMessage.insertAgain);
                return;
            }


            if(move.getRow()<0 || move.getRow()>=5 || move.getColumn()<0 || move.getColumn()>=5){
                model.sendError(move.getColor().toString()+" "+gameMessage.wrongInputMessage+"\n"+gameMessage.insertAgain);
                return;
            }


            if(!checkStepType(move,turn)){
                model.sendError(move.getColor().toString()+" "+ gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
                return;
            }

            //se apollo ha worker vicino ed è stack non è really stack
            if(!hasFreeCellClosed(move.getWorker().getWorkerPosition(), model.getBoard().getPlayingBoard())){
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

            if(move.getMoveOrBuild().equals("D")){
                model.sendError(move.getColor().toString()+" "+gameMessage.notReachableCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }

            if(move.getMoveOrBuild().equals("M") ){
                //per apollo anche le celle con un worker sono empty
                if(!isEmptyCell(move, model)){
                    //read worker position and check if there are some empty cell where he can move in.
                    model.sendError(move.getColor().toString()+" "+gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
                    return;
                }
                if(!canBuildFromCellTo(move, model)){
                    model.sendError(move.getColor().toString()+" "+gameMessage.invalidMove+"\n"+gameMessage.insertAgain);
                    return;
                }
                if(!model.isLevelDifferenceAllowed(move)){
                    model.sendError(move.getColor().toString()+" "+gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
                    return;
                }
                if(model.checkStep(move, turn, model))
                    move(move, model, turn);
            }
            else{ //"B"
                if(!model.isEmptyCell(move)){
                    model.sendError(move.getColor().toString()+" "+gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
                    return;
                }
                if(model.checkStep(move, turn, model))
                    //model.performBuild(move);
                    build(move, model, turn);
            }
        }

    @Override
    public void move(PlayerMove move, Model model, Turn turn) {
        boolean hasWon = model.hasWon(move);

        model.setStep(move,turn, model);

        if(model.getBoard().getCell(move.getRow(),move.getColumn()).getCurrWorker()!=null){
            switchWorkerPosition(move, model);
        }else{
            model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).setFreeSpace(true);
            model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).deleteCurrWorker();

            move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(),move.getColumn()));
            (model.getBoard().getCell(move.getRow(),move.getColumn())).setFreeSpace(false);
            model.getBoard().getCell(move.getRow(),move.getColumn()).setCurrWorker(move.getWorker());
        }

        model.notifyView(move,hasWon);
    }
    public void switchWorkerPosition(PlayerMove move, Model model){
        Worker tempWorker;
        Cell from = model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(), move.getWorker().getWorkerPosition().getPosY());
        Cell to = model.getBoard().getCell(move.getRow(), move.getColumn());

        tempWorker = to.getCurrWorker();
        to.setCurrWorker(from.getCurrWorker());
        move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(),move.getColumn()));
        from.setCurrWorker(tempWorker);

        tempWorker.setWorkerPosition(from);
    }
    public boolean hasFreeCellClosed(Cell from, Cell[][] board){
        boolean bool=false;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                if((from.getPosX()+i)>=0 && (from.getPosY()+j)>=0 && (from.getPosY()+j)<5 && (from.getPosX()+i)<5 &&
                        board[from.getPosX()+i][from.getPosY()+j].getLevel()- from.getLevel()<=1) {
                    if ((board[from.getPosX() + i][from.getPosY() + j]).isFree() ||
                            ((board[from.getPosX() + i][from.getPosY() + j]).getCurrWorker()!=null &&
                                    !isApolloWorker(from, (board[from.getPosX() + i][from.getPosY() + j]).getCurrWorker() )) ){
                        bool = true;
                    }
                }
            }
        }
        return bool;
    }
    public boolean isEmptyCell(PlayerMove move, Model model){
        Cell to = model.getBoard().getCell(move.getRow(),move.getColumn());

        return to.isFree() ||
                (to.getCurrWorker()!= move.getPlayer().getWorker1() &&
                    to.getCurrWorker()!= move.getPlayer().getWorker2() &&
                        model.getBoard().getCell(move.getRow(), move.getColumn()).getCurrWorker()!=null);
    }
    public boolean canBuildFromCellTo(PlayerMove move, Model model){
        return model.getBoard().getCell(move.getRow(),move.getColumn()).canBuildInCells(model.getBoard().getPlayingBoard());
    }
    public boolean isApolloWorker(Cell from, Worker to){
        return from.getCurrWorker().getColor()==to.getColor();
    }

}
