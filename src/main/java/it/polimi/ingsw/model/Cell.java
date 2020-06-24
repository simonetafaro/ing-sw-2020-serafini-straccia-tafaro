package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Cell class keeps track of levels and workers
 * in Board game
 * {@link it.polimi.ingsw.model.Board}
 */
public class Cell implements Cloneable, Serializable {

    /**
     * coordinate x of the board
     */
    private int posX;

    /**
     * coordinate y of the board
     */
    private int posY;

    /**
     * block level
     */
    private int level;

    /**
     * true if the cell is empty
     * false if the cell is not empty
     */
    private boolean freeSpace;

    /**
     * pointer to worker in a cell
     */
    private Worker currWorker;

    /**
     * true when a dome is built
     */
    private boolean cronusRule;

    /**
     * empty Cell constructor
     */
    public Cell(){}

    /**
     * Cell constructor through the coordinates
     * @param posX coordinate x
     * @param posY coordinate y
     */
    public Cell(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
        this.level=0;
        this.currWorker = null;
    }

    /**
     * @param currWorker
     * Setter of currWorker
     */
    public void setCurrWorker(Worker currWorker) {
        this.currWorker = currWorker;
    }

    /**
     * @param freeSpace
     * Setter of freeSpace
     */
    public void setFreeSpace(boolean freeSpace){
        this.freeSpace=freeSpace;
    }

    /**
     * @param newPosition
     * Setter of coordinate x
     */
    public void setPosX(int newPosition){
        this.posX = newPosition;
    }

    /**
     * @param newPosition
     * Setter of coordinate y
     */
    public void setPosY(int newPosition){
        this.posY = newPosition;
    }

    /**
     * @param level
     * Setter of block level
     */
    public void setLevel(int level){
        this.level=level;
        if(this.level==4){
            this.setFreeSpace(false);
        }
    }

    /**
     * @return true if a dome has been built
     */
    public boolean isCronusRule() {
        return cronusRule;
    }

    /**
     * @return coordinate x
     */
    public int getPosX(){
        return this.posX;
    }

    /**
     * @return coordinate y
     */
    public int getPosY(){
        return this.posY;
    }

    /**
     * @return block level
     */
    public int getLevel(){
        return this.level;
    }

    /**
     * @return currWorker
     */
    public Worker getCurrWorker() {
        return currWorker;
    }

    /**
     * @return true if
     *          returns true if there is
     *          neither a dome nor a worker
     */
    public boolean isFree(){
        return this.freeSpace;
    }

    /**
     * add block level:
     * 1 first block
     * 2 second block
     * 3 third block
     * 4 dome
     */
    public void buildInCell(){
        this.level++;
        if(this.level==4){
            this.cronusRule = true;
            this.setFreeSpace(false);
        }
    }

    /**
     * It deletes worker pointer from a cell
     */
    public void deleteCurrWorker(){
        this.currWorker=null;
    }

    /**
     * @return true if a cell is adjacent to
     * @param cell
     */
    public boolean isClosedTo(Cell cell){
        return (this.getPosX() - cell.getPosX() <= 1 && this.getPosX() - cell.getPosX() >= -1) &&
                (this.getPosY() - cell.getPosY() <= 1 && this.getPosY() - cell.getPosY() >= -1);
    }


    /**
     * @param board
     * @return true if it has al least one near free cell whose level
     *         is not more than 1 higher than the level of the cell on
     *         which the method is called
     */
    public boolean hasFreeCellClosed(Cell[][] board){
        boolean bool=false;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                if((this.getPosX()+i)>=0 && (this.getPosY()+j)>=0 && (this.getPosY()+j)<5 && (this.getPosX()+i)<5 &&
                        board[this.getPosX()+i][this.getPosY()+j].getLevel()- this.getLevel()<=1) {
                    if ((board[this.getPosX() + i][this.getPosY() + j]).isFree()) {
                        bool = true;
                    }
                }
            }
        }
        return bool;
    }

    /**
     * @param board
     * @return true if worker has near free cell
     */
    public boolean canBuildInCells(Cell[][] board){
        boolean bool=false;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                if((this.getPosX()+i)>=0
                        && (this.getPosY()+j)>=0 && (this.getPosY()+j)<5 && (this.getPosX()+i)<5) {
                    if ((board[this.getPosX() + i][this.getPosY() + j]).isFree()) {
                        bool = true;
                    }
                }
            }
        }
        return bool;
    }

}
