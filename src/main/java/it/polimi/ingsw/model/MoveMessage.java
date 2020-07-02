package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import java.io.Serializable;

/**
 * MoveMessage class is the server's response to the client
 * after it has sent it a {@link PlayerMove}. It contains the following information:
 * player who made the move, board's copy, if the player has won and next turn color player
 */
public class MoveMessage implements Serializable {

    private static final long serialVersionUID = -6339404514199154793L;

    /**
     * Player
     */
    private final Player player;

    /**
     * Update board
     */
    private final Board board;

    /**
     * True if the player has just won
     */
    private final boolean hasWon;

    /**
     * Next turn color player
     */
    private PlayerColor nextTurn;

    /**
     * MoveMessage constructor with 2 parameters
     *
     * @param player
     * @param board
     */
    MoveMessage(Player player, Board board) {
        this.player = player;
        this.board = board;
        this.hasWon = false;
    }

    /**
     * MoveMessage constructor with 4 parameters
     *
     * @param board
     * @param player
     * @param hasWon
     * @param nextTurn
     */
    MoveMessage(Board board, Player player, boolean hasWon, PlayerColor nextTurn) {
        this.player = player;
        this.board = board;
        this.hasWon = hasWon;
        this.nextTurn = nextTurn;
    }

    /**
     * @return player who made the move
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return update board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return pointer to the next turn player
     */
    public PlayerColor getNextTurn() {
        return nextTurn;
    }

    /**
     * @return true if the player has just won
     */
    public boolean isHasWon() {
        return hasWon;
    }

}
