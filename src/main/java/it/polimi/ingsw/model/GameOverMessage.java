package it.polimi.ingsw.model;

/**
 * GameOverMessage is a MoveMessage. It is sent when
 * a player win and the game is finished.
 * {@link it.polimi.ingsw.model.MoveMessage}
 */
public class GameOverMessage extends MoveMessage {

    /**
     * true if game finished
     */
    private boolean gameOver;

    /**
     * GameOverMessage constructor
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
