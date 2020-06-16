package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;

import java.io.Serializable;

public class Worker implements Serializable {
    private int  workerNum;
    private Cell currentPosition;
    private PlayerColor color;
    private boolean stuck;
    private int ID;

    public Worker(){}
    public Worker(int ID, Cell position, int number, PlayerColor color){
        this.ID = ID;
        this.currentPosition = position;
        this.workerNum = number;
        this.color = color;
        position.setFreeSpace(false);
        position.setCurrWorker(this);
    }


    public Worker(Cell position, int number, PlayerColor color){
        this.ID = 0;
        this.currentPosition = position;
        this.workerNum = number;
        this.color = color;
        position.setFreeSpace(false);
        position.setCurrWorker(this);
    }

    public void setWorkerPosition(Cell cell){
        this.currentPosition = cell;
        cell.setFreeSpace(false);
        cell.setCurrWorker(this);
    }
    public void setStuck(boolean stuck) {
        this.stuck = stuck;
    }
    public void setColor(PlayerColor color){
        this.color=color;
    }

    public Cell getWorkerPosition(){
        return this.currentPosition;
    }
    public int getWorkerNum() {
        return workerNum;
    }
    public char getColor() {
        switch (this.color){
            case WHITE: return 'W';
            case GREY:  return 'G';
            case BLUE:  return 'B';
            default: return ' ';
        }

    }
    public PlayerColor getPlayerColor(){
        return this.color;
    }
    public boolean isStuck() {
        return stuck;
    }
    public int getID(){
        return this.ID;
    }
    public void clear(){
        this.currentPosition.setFreeSpace(true);
        this.currentPosition.setCurrWorker(null);
    }

}
