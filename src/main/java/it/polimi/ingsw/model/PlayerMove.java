package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.view.View;
import java.io.Serializable;

/**
 * PlayerMove class is sent to server by client to show his move.
 * It contains all the information about player's move:
 * {@link Player} who is making move, {@link Worker} of the player, two integers row and column
 * indicating the cell in which the player wants his worker to move or build.
 */
public class PlayerMove implements Serializable , Cloneable{

    /**
     * Cell row in which player wants to build or move
     */
    private int row;

    /**
     * Cell column in which player wants to build or move
     */
    private int column;

    /**
     * Player who is making the move
     */
    private Player player;

    /**
     * Worker used by the player to make the move
     */
    private Worker worker;

    /**
     * Player's View
     */
    private final View view;

    /**
     * Move string:
     * "M" move
     * "B" build
     */
    private String MoveOrBuild;

    /**
     * Player color
     */
    private PlayerColor color;

    /**
     * PlayerMove constructor with 3 parameters
     * @param worker
     * @param row
     * @param column
     */
    public PlayerMove(Worker worker, int row, int column) {
        this.worker=worker;
        this.player=null;
        this.row = row;
        this.column = column;
        this.view = null;
    }

    /**
     * PlayerMove constructor with 4 parameters
     * @param player
     * @param worker
     * @param row
     * @param column
     */
    public PlayerMove(Player player, Worker worker,int row, int column) {
        this.worker=worker;
        this.player=player;
        this.row = row;
        this.column = column;
        this.view = null;
        this.color = player.getColor();
    }

    /**
     * PlayerMove constructor with 1 parameter
     * @param player
     */
    public PlayerMove(Player player){
        this.worker = null;
        this.player = player;
        this.color = player.getColor();
        this.row = -1;
        this.column = -1;
        this.view = null;
    }

    /**
     * @param moveOrBuild
     * It sets move or build
     */
    public void setMoveOrBuild(String moveOrBuild){
        this.MoveOrBuild = moveOrBuild;
    }

    /**
     * @param worker
     * It sets workers move
     */
    public void setWorker(Worker worker){
        this.worker = worker;
    }

    /**
     * @param row
     * It sets row cell
     */
    public void setRow(int row){
        this.row = row;
    }

    /**
     * @param column
     * It sets column cell
     */
    public void setColumn(int column){
        this.column = column;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    /**
     * @return row cell
     */
    public int getRow() {
        return row;
    }

    /**
     * @return column cell
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return worker
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     *
     * @return move or build
     */
    public String getMoveOrBuild() {
        return MoveOrBuild;
    }

    /**
     * @param player
     * It sets player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return
     * It sets color player
     */
    public PlayerColor getColor() {
        return color;
    }

}
