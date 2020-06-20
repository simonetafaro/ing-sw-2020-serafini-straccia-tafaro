package it.polimi.ingsw.model;

import java.io.Serializable;

public class Cell implements Cloneable, Serializable {

    private int posX;
    private int posY;
    private int level;
    private boolean freeSpace;
    private Worker currWorker;
    private boolean cronusRule;
    public Cell(){}
    public Cell(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
        this.level=0;
        this.currWorker = null;
    }

    public void setCurrWorker(Worker currWorker) {
        this.currWorker = currWorker;
    }
    public void setFreeSpace(boolean freeSpace){
        this.freeSpace=freeSpace;
    }
    public void setPosX(int newPosition){
        this.posX = newPosition;
    }
    public void setPosY(int newPosition){
        this.posY = newPosition;
    }
    public void setLevel(int level){
        this.level=level;
        if(this.level==4){
            this.cronusRule = true;
            this.setFreeSpace(false);
        }
    }

    public boolean isCronusRule() {
        return cronusRule;
    }

    public int getPosX(){
        return this.posX;
    }
    public int getPosY(){
        return this.posY;
    }
    public int getLevel(){
        return this.level;
    }
    public Worker getCurrWorker() {
        return currWorker;
    }
    public boolean isFree(){
        return this.freeSpace;
    }

    public void buildInCell(){
        this.level++;
        if(this.level==4){
            this.setFreeSpace(false);
        }
    }
    public void deleteCurrWorker(){
        this.currWorker=null;
    }
    public boolean isClosedTo(Cell cell){
        return (this.getPosX() - cell.getPosX() <= 1 && this.getPosX() - cell.getPosX() >= -1) &&
                (this.getPosY() - cell.getPosY() <= 1 && this.getPosY() - cell.getPosY() >= -1);
    }

    /**return true if worker has near free cell**/
    public boolean hasFreeCellClosed(Cell[][] board){
        boolean bool=false;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                if((this.getPosX()+i)>=0 && (this.getPosY()+j)>=0 && (this.getPosY()+j)<5 && (this.getPosX()+i)<5 &&
                        board[this.getPosX()+i][this.getPosY()+j].getLevel()- this.getLevel()<=1) {
                    if ((board[this.getPosX() + i][this.getPosY() + j]).isFree()) {
                        bool = true;
                        //System.out.println(("(" + (this.getPosX() + i) + "," + (this.getPosY() + j) + ")"));
                    }
                }
            }
        }
        return bool;
    }
    public boolean canBuildInCells(Cell[][] board){
        boolean bool=false;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                if((this.getPosX()+i)>=0
                        && (this.getPosY()+j)>=0 && (this.getPosY()+j)<5 && (this.getPosX()+i)<5) {
                    if ((board[this.getPosX() + i][this.getPosY() + j]).isFree()) {
                        bool = true;
                        //System.out.println(("(" + (this.getPosX() + i) + "," + (this.getPosY() + j) + ")"));
                    }
                }
            }
        }
        return bool;
    }

    @Override
    protected final Cell clone(){
        return this.clone();
    }


}
