package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

import java.io.Serializable;

public class ApolloRuleDecorator extends StandardRuleDecorator implements Serializable {
    @Override
    public void play(PlayerMove move, Turn turn, Model model) {

            if(move instanceof PlayerMoveEnd){
                if(turn.getPlayerTurn(move.getPlayer().getID()).getI()==3) {
                    model.endMessage(move, turn, model);
                }
                else {
                    model.notify(gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
                    //move.getView().reportError(gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
                }return;
            }
            System.out.println("A");

            if(!model.isRightWorker(move, turn)){
                model.notify(gameMessage.insertAgain);
                //move.getView().reportError(gameMessage.insertAgain);
                return;
            }

            System.out.println("B");

            if(move.getRow()<0 || move.getRow()>=5 || move.getColumn()<0 || move.getColumn()>=5){
                model.notify(gameMessage.wrongInputMessage+"\n"+gameMessage.insertAgain);
                //move.getView().reportError(gameMessage.wrongInputMessage+"\n"+gameMessage.insertAgain);
                return;
            }

            System.out.println("C");

            if(!checkStepType(move,turn)){
                model.notify(gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
                //move.getView().reportError(gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            System.out.println("D");

            //se apollo ha worker vicino ed è stack non è really stack
            if(!hasFreeCellClosed(move.getWorker().getWorkerPosition(), model.getBoard().getPlayingBoard())){
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

            System.out.println("E");

            if(!model.isReachableCell(move)){
                model.notify(gameMessage.notReachableCellMessage+"\n"+gameMessage.insertAgain);
                //move.getView().reportError(gameMessage.notReachableCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }

            System.out.println("F");

            if(move.getMoveOrBuild().equals("M") ){
                //per apollo anche le celle con un worker sono empty
                if(!isEmptyCell(move, model)){
                    //read worker position and check if there are some empty cell where he can move in.
                    model.notify(gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
                    //move.getView().reportError(gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
                    return;
                }
                if(!canBuildFromCellTo(move, model)){
                    model.notify(gameMessage.invalidMove+"\n"+gameMessage.insertAgain);
                    //move.getView().reportError(gameMessage.invalidMove+"\n"+gameMessage.insertAgain);
                    return;
                }
                if(!model.isLevelDifferenceAllowed(move)){
                    model.notify(gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
                    //move.getView().reportError(gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
                    return;
                }
                System.out.println("G");
                if(model.checkStep(move, turn, model))
                    move(move, model, turn);
            }
            else{ //"B"
                if(!model.isEmptyCell(move)){
                    model.notify(gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
                    //move.getView().reportError(gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
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

        //model.notify(move);
        model.notifyView(move,hasWon);
    }
    public void switchWorkerPosition(PlayerMove move, Model model){
        Worker tempWorker;
        Cell from = move.getWorker().getWorkerPosition();
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
