package it.polimi.ingsw.utils;

import java.io.Serializable;

/**
 * Class used between client and server when each player choose the cell on the board for his workers
 */
public class SetWorkerPosition implements Serializable {


    private static final long serialVersionUID = -6339404514199154796L;

    /**
     * Coordinate of the cell
     */
    private int x, y;
    /**
     * Color of the player that is sending this message
     */
    private PlayerColor color;
    /**
     * ID of the player that is sending this message
     */
    private int ID;
    /**
     * This number represent if is the man or the woman worker
     */
    private int WorkerNum;

    public SetWorkerPosition(int x, int y, PlayerColor color, int ID, int workerNum) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.ID = ID;
        WorkerNum = workerNum;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PlayerColor getColor() {
        return color;
    }

    public int getID() {
        return ID;
    }

    public int getWorkerNum() {
        return WorkerNum;
    }
}
