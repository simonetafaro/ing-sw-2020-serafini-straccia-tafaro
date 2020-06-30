package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Game board class that contains
 * cells of the board.
 * {@link Cell}
 */
public class Board implements Cloneable, Serializable {

    private static final long serialVersionUID = -6339404514199154798L;

    /**
     * rows number
     */
    private static final int N_ROWS = 5;

    /**
     * column number
     */
    private static final int N_COLS = 5;

    /**
     * 5 x 5 board array of Cells
     */
    private Cell[][] board;

    /**
     * Board class constructor.
     * It initializes game board and initializes
     * all cells to empty
     */
    public Board() {
        this.board = new Cell[N_COLS][N_COLS];
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                board[i][j] = new Cell(i, j);
                board[i][j].setFreeSpace(true);
            }
        }
    }

    /**
     * @return game board
     */
    public Cell[][] getPlayingBoard() {
        return board;
    }

    /**
     * @param x: row of game board
     * @param y: column of game board
     * @return cell (x,y) of game board
     */
    public Cell getCell(int x, int y) {
        return board[x][y];
    }

    /**
     * Game board is reinitialized:
     * each level is set to 0 and all cells are empty
     */
    public void clearAll() {
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                board[i][j].setFreeSpace(true);
                board[i][j].setLevel(0);
            }
        }
    }

    /**
     * game board printing
     */
    public void printBoard() {
        int a, i, j, k = 0;
        //TODO code optimization and row and column number
        for (a = 0; a < N_ROWS; a++) {
            for (i = 1; i <= 4; i++) {
                for (j = 1; j <= 40; j++) {
                    if (i == 1 || i == 4 || j == ((8 * k) + 1) || j == ((k * 8) + 8))
                        System.out.print("*");
                    else {
                        if ((this.board[a][j / 8]).getCurrWorker() != null && i == 2 && j == (4 + (8 * k)))
                            System.out.print((this.board[a][j / 8]).getCurrWorker().getColor());
                        else {
                            if ((this.board[a][j / 8]).getCurrWorker() != null && i == 2 && j == 5 + 8 * k)
                                System.out.print((this.board[a][j / 8]).getCurrWorker().getWorkerNum());
                            else {
                                if (i == 3 && j == 4 + 8 * k)
                                    System.out.print("L");
                                else {
                                    if (i == 3 && j == 5 + 8 * k) {
                                        if ((this.board[a][j / 8]).isDome())
                                            System.out.print("4");
                                        else
                                            System.out.print((this.board[a][j / 8]).getLevel());
                                    } else
                                        System.out.print(" ");
                                }
                            }
                        }
                    }
                    if (j % 8 == 0)
                        k++;
                }
                System.out.println();
                k = 0;
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}



