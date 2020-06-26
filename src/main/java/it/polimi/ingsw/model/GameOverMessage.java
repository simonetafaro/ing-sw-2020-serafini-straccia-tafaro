package it.polimi.ingsw.model;

/**
 * GameOverMessage is a {@link MoveMessage}. It is sent when
 * a player win and the game is over.
 */
public class GameOverMessage extends MoveMessage {

    /**
     * true if game is over
     */
    private boolean gameOver;

    /**
     * GameOverMessage constructor that
     * inherits MoveMessage constructor
     * @param player
     * @param board
     */
    public GameOverMessage(Player player, Board board){
        super(player,board);
        this.gameOver=true;
    }

    /**
     * @return true if game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }

}
