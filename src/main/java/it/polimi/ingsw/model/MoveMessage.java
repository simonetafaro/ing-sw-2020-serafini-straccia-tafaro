package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;

import java.io.Serializable;

public class MoveMessage implements Serializable {

    private final Player player;
    private final Board board;
    private final boolean  hasWon;
    private PlayerColor nextTurn;

    MoveMessage(Player player, Board board){
        this.player= player;
        this.board=board;
        this.hasWon=false;
    }
    MoveMessage(Board board, Player player, boolean hasWon, PlayerColor nextTurn) {
        this.player = player;
        this.board = board;
        this.hasWon = hasWon;
        this.nextTurn=nextTurn;
    }

    public Player getPlayer() {
        return player;
    }
    public Board getBoard() {
        return board;
    }
    public PlayerColor getNextTurn() {
        return nextTurn;
    }
    public boolean isHasWon() {
        return hasWon;
    }

}
