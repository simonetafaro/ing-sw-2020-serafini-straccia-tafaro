package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;

public class MoveMessage {

    private final Player player;

    private final Board board;
    private final boolean  hasWon;
    PlayerColor nextTurn;

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

    public boolean isHasWon() {
        return hasWon;
    }

    public PlayerColor getNextTurn() {
        return nextTurn;
    }
}
