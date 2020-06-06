package it.polimi.ingsw.utils;

import java.io.Serializable;

public class SetWorkerPosition implements Serializable {
    private int x,y;
    private PlayerColor color;
    private int ID;
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
