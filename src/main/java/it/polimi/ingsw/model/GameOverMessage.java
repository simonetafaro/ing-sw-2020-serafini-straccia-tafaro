package it.polimi.ingsw.model;


public class GameOverMessage extends MoveMessage {
    private boolean gameOver;

    public GameOverMessage(Player player, Board board){
        super(player,board);
        this.gameOver=true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

}
