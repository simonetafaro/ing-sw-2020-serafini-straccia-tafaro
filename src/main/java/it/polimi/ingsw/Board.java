package it.polimi.ingsw;

public class Board {

    private static final int N_ROWS = 5;
    private static final int N_COLS = 5;
    private Cell[][] board;

    public Board() {
        board = new Cell[N_COLS][N_COLS];
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                board[i][j]= new Cell(i,j);
                board[i][j].setFreeSpace(true);
            }
        }
    }

    public void clearAll(){
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                board[i][j].setFreeSpace(true);
                board[i][j].setLevel(0);
            }
        }
    }

}



