package it.polimi.ingsw;

public class Game {
    private int playerNumber =0;
    private Board islandBoard;
    private boolean winner;

    public Game(int playerNumber) {
        this.playerNumber = playerNumber;
        this.islandBoard = new Board();
        this.winner = false;
    }

    public Board getIslandBoard() {
        return islandBoard;
    }

    public void Start(){

    }
}
