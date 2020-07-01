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

    /**
     *it is used to set worker's position
     * @param x coordinate x
     * @param y coordinate y
     * @param color color of the  player
     * @param ID player ID
     * @param workerNum worker number (man or woman )
     */
    public SetWorkerPosition(int x, int y, PlayerColor color, int ID, int workerNum) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.ID = ID;
        WorkerNum = workerNum;
    }

    /**
     * @return coordinate x
     */
    public int getX() {
        return x;
    }

    /**
     * @return coordinate y
     */
    public int getY() {
        return y;
    }

    /**
     * @return color of the  player
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * @return player ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @return worker number  (man or woman )
     */
    public int getWorkerNum() {
        return WorkerNum;
    }
}
