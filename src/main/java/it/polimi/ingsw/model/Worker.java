package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import java.io.Serializable;

/**
 * Workers are game pieces. Each {@link Player} can have two workers.
 * Worker class gives information about:
 * if it's worker1 or worker2, {@link Cell} position on the board
 * and Player through his {@link PlayerColor}
 */
public class Worker implements Serializable {

    /**
     * An integer depending on if it's
     * worker1 or worker2 of a player
     */
    private int  workerNum;

    /**
     * Pointer of worker position on the board
     */
    private Cell currentPosition;

    /**
     * Player color
     */
    private PlayerColor color;

    /**
     * It's true if worker can't move
     */
    private boolean stuck;

    /**
     * ID client of the player
     */
    private int ID;

    /**
     * Empty constructor
     */
    public Worker(){}

    /**
     * Constructor with ID client
     * @param ID
     * @param position
     * @param number
     * @param color
     */
    public Worker(int ID, Cell position, int number, PlayerColor color){
        this.ID = ID;
        this.currentPosition = position;
        this.workerNum = number;
        this.color = color;
        position.setFreeSpace(false);
        position.setCurrWorker(this);
    }

    /**
     * Constructor without ID
     * @param position
     * @param number
     * @param color
     */
    public Worker(Cell position, int number, PlayerColor color){
        this.ID = 0;
        this.currentPosition = position;
        this.workerNum = number;
        this.color = color;
        position.setFreeSpace(false);
        position.setCurrWorker(this);
    }

    /**
     * @param cell is changed
     *      It changes the new worker position
     *      by freeing the previous cell and moving
     *      pointer to worker in the new cell
     */
    public void setWorkerPosition(Cell cell){
        this.currentPosition = cell;
        cell.setFreeSpace(false);
        cell.setCurrWorker(this);
    }

    /**
     * @param stuck is set true when a worker can't
     *      move because it has no adjacent free cells
     */
    public void setStuck(boolean stuck) {
        this.stuck = stuck;
    }

    /**
     * @param color
     * It sets color
     */
    public void setColor(PlayerColor color){
        this.color=color;
    }

    /**
     * @return Worker position
     */
    public Cell getWorkerPosition(){
        return this.currentPosition;
    }

    /**
     * @return Worker number
     */
    public int getWorkerNum() {
        return workerNum;
    }

    /**
     * @return a char depending on PlayerColor:
     * 'W' WHITE
     * 'G' GREY
     * 'B' BLUE
     */
    public char getColor() {
        switch (this.color){
            case WHITE: return 'W';
            case GREY:  return 'G';
            case BLUE:  return 'B';
            default: return ' ';
        }

    }

    /**
     * @return a PlayerColor
     */
    public PlayerColor getPlayerColor(){
        return this.color;
    }

    /**
     * @return true if the worker is stuck
     */
    public boolean isStuck() {
        return stuck;
    }

    /**
     * @return ID
     */
    public int getID(){
        return this.ID;
    }

    /**
     * Method called when a player leaves the game.
     * It deletes worker by freeing cell position and
     * setting worker pointer in that cell to null
     */
    public void clear(){
        this.currentPosition.setFreeSpace(true);
        this.currentPosition.setCurrWorker(null);
    }

}
