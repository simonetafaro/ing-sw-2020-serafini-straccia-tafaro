package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;

import java.io.Serializable;

public class MoveMessage implements Serializable {

    private final Player player;
    private final Board board;
    private final boolean  hasWon;
    private PlayerColor nextTurn;
    private PlayerMove move;
    private boolean usingCard;
    private Cell from;
    private Cell to;

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
    MoveMessage(PlayerMove move, Board board, boolean hasWon, boolean usingCard){
        this.player = move.getPlayer();
        this.board = board;
        this.hasWon = hasWon;
        this.nextTurn = null;
        this.usingCard = usingCard;
        this.move = move;
        this.from = null;
        this.to = null;
    }
    MoveMessage(PlayerMove move, Cell from, Cell to,Board board, boolean hasWon, boolean usingCard){
        this.player = move.getPlayer();
        this.board = board;
        this.hasWon = hasWon;
        this.nextTurn = null;
        this.usingCard = usingCard;
        this.move = move;
        this.from = from;
        this.to = to;
    }

    public Cell getFrom() {
        return from;
    }

    public Cell getTo() {
        return to;
    }

    public PlayerMove getMove() {
        return move;
    }

    public boolean isUsingCard() {
        return usingCard;
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
