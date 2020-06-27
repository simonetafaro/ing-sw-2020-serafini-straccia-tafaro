package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

/**
 * Your Move: Your Worker may
 * move into an opponent Worker’s
 * space, if their Worker can be
 * forced one space straight backwards to an
 * unoccupied space at any level.
 */
public class MinotaurRuleDecorator extends StandardRuleDecorator {

    @Override
    public void play(PlayerMove move, Turn turn, Model model) {
        if(move instanceof PlayerMoveEnd){
            if(turn.getPlayerTurn(move.getPlayer()).getI()==3)
                model.endMessage(move,turn,model);
            else
                model.sendError(move.getColor().toString()+" "+gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
            return;
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
            model.sendError(move.getColor().toString()+" "+gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getMoveOrBuild().equals("M") && !hasFreeCellClosed(move.getWorker().getWorkerPosition(), model.getBoard().getPlayingBoard())){
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

        if(move.getMoveOrBuild().equals("M") ){
            if(!isEmptyCell(move, model)){
                model.sendError(move.getColor().toString()+" "+gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            if(!model.isLevelDifferenceAllowed(move)){
                model.sendError(move.getColor().toString()+" "+gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            if(model.fillStepInfo(move, turn, model))
                move(move, model, turn);
        }
        else{ //"B"
            if(!model.isEmptyCell(move)){
                model.sendError(move.getColor().toString()+" "+gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            if(model.fillStepInfo(move, turn, model))
                //model.performBuild(move);
                build(move, model, turn);
        }
    }

    @Override
    public void move(PlayerMove move, Model model, Turn turn) {
        boolean hasWon = model.hasWon(move);
        model.setStep(move, turn, model);
        if(model.getBoard().getCell(move.getRow(),move.getColumn()).getCurrWorker()!=null){
            pushWorkerPosition(move, model);
        }else{
            model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).setFreeSpace(true);
            model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).deleteCurrWorker();
            move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(),move.getColumn()));
            (model.getBoard().getCell(move.getRow(),move.getColumn())).setFreeSpace(false);
            model.getBoard().getCell(move.getRow(),move.getColumn()).setCurrWorker(move.getWorker());

        }
        model.notifyView(move,hasWon);
    }
    public void pushWorkerPosition(PlayerMove move, Model model){
        Cell from = model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(), move.getWorker().getWorkerPosition().getPosY());
        Cell to = model.getBoard().getCell(move.getRow(), move.getColumn());

        Cell pushingInCell = pushingInCell(from, to, model.getBoard().getPlayingBoard());

        pushingInCell.setCurrWorker(to.getCurrWorker());
        to.getCurrWorker().setWorkerPosition(pushingInCell);
        pushingInCell.setFreeSpace(false);

        to.setCurrWorker(from.getCurrWorker());
        from.setFreeSpace(true);
        from.setCurrWorker(null);
        move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(),move.getColumn()));

    }
    public boolean hasFreeCellClosed(Cell from, Cell[][] board){
        boolean bool=false;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                if((from.getPosX()+i)>=0 && (from.getPosY()+j)>=0 && (from.getPosY()+j)<5 && (from.getPosX()+i)<5 &&
                        board[from.getPosX()+i][from.getPosY()+j].getLevel()- from.getLevel()<=1) {
                    if ((board[from.getPosX() + i][from.getPosY() + j]).isFree() ||
                            ((board[from.getPosX() + i][from.getPosY() + j]).getCurrWorker()!=null &&
                                    !isMinotaurWorker(from, (board[from.getPosX() + i][from.getPosY() + j]).getCurrWorker()) &&
                                        minotaurMoveAllowed(from, board[from.getPosX() + i][from.getPosY() + j], board))){
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
                ( (to.getCurrWorker()!= move.getPlayer().getWorker1()) && (to.getCurrWorker() != move.getPlayer().getWorker2()) &&
                        ((model.getBoard().getCell(move.getRow(), move.getColumn()).getCurrWorker()!=null) &&
                        (minotaurMoveAllowed(move.getWorker().getWorkerPosition(), to, model.getBoard().getPlayingBoard()))));
    }

    /**Return true if minotaur in cell from can push the worker in the cell after cell to
     * */
    public boolean minotaurMoveAllowed(Cell from, Cell to, Cell[][] board){

        if(from.getPosY()==to.getPosY()){
            if(to.getPosX()==4 || to.getPosX()==0)
                return false;
            if(from.getPosX()-to.getPosX()>0){
                return board[to.getPosX()-1][to.getPosY()].isFree();
            }
            else {
                return board[to.getPosX()+1][to.getPosY()].isFree();
            }
        }

        if(from.getPosX()==to.getPosX()){
            if(to.getPosY()==4 || to.getPosY()==0)
                return false;
            if(from.getPosY()-to.getPosY()>0)
                return board[from.getPosX()][to.getPosY()-1].isFree();
            else
                return board[from.getPosX()][to.getPosY()+1].isFree();
        }

        //sono in diagonale
        if(to.getPosX()==4 || to.getPosY()==4 || to.getPosX()==0 || to.getPosY()==0)
            return false;
        if(from.getPosX()>to.getPosX()){
            if(from.getPosY()<to.getPosY())
                return board[to.getPosX()-1][to.getPosY()+1].isFree();
            else
                return board[to.getPosX()-1][to.getPosY()-1].isFree();
        }else{
            if(from.getPosY()<to.getPosY())
                return board[to.getPosX()+1][to.getPosY()+1].isFree();
            else
                return board[to.getPosX()+1][to.getPosY()-1].isFree();
        }
    }
    public Cell pushingInCell(Cell from, Cell to, Cell[][] board){

        if(from.getPosY()==to.getPosY()){
            if(from.getPosX()-to.getPosX()>0)
                return board[to.getPosX()-1][to.getPosY()];
            else
                return board[to.getPosX()+1][to.getPosY()];
        }

        if(from.getPosX()==to.getPosX()){
            if(from.getPosY()-to.getPosY()>0)
                return board[from.getPosX()][to.getPosY()-1];
            else
                return board[from.getPosX()][to.getPosY()+1];
        }

        //sono in diagonale
        if(from.getPosX()>to.getPosX()){
            if(from.getPosY()<to.getPosY())
                return board[to.getPosX()-1][to.getPosY()+1];
            else
                return board[to.getPosX()-1][to.getPosY()-1];
        }else{
            if(from.getPosY()<to.getPosY())
                return board[to.getPosX()+1][to.getPosY()+1];
            else
                return board[to.getPosX()+1][to.getPosY()-1];
        }

    }
    public boolean isMinotaurWorker(Cell from, Worker to){
        return from.getCurrWorker().getColor()==to.getColor();
    }
}
