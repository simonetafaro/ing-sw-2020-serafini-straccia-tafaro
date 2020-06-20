package it.polimi.ingsw.model;

import java.io.Serializable;

public class Board implements Cloneable, Serializable {

    private static final int N_ROWS = 5;
    private static final int N_COLS = 5;
    private Cell[][] board;


    public Board() {
        this.board = new Cell[N_COLS][N_COLS];
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                board[i][j]= new Cell(i,j);
                board[i][j].setFreeSpace(true);
            }
        }
    }

    public Cell[][] getPlayingBoard() {
        return board;
    }

    public Cell getCell(int x, int y){
        return board[x][y];
    }

    public void clearAll(){
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                board[i][j].setFreeSpace(true);
                board[i][j].setLevel(0);
            }
        }
    }

    public void printBoard(){
        int a,i,j,k=0;
        //TODO code optimization and row and column number
        for(a=0; a<N_ROWS; a++){
            for (i = 1; i <= 4; i++)
            {
                for(j = 1; j <= 40; j++)
                {
                    if (i == 1 || i == 4 || j ==((8*k)+1) || j == ((k*8)+8) )
                        System.out.print("*");
                    else
                    {
                        if((this.board[a][j/8]).getCurrWorker() != null && i==2 && j==(4+(8*k)))
                            System.out.print((this.board[a][j/8]).getCurrWorker().getColor());
                        else{
                            if((this.board[a][j/8]).getCurrWorker()  != null && i==2 && j==5+8*k)
                                System.out.print((this.board[a][j/8]).getCurrWorker().getWorkerNum());
                            else{
                                if(i==3 && j==4+8*k)
                                    System.out.print("L");
                                else{
                                    if(i==3 && j==5+8*k)
                                        System.out.print((this.board[a][j/8]).getLevel());
                                    else
                                        System.out.print(" ");
                                }
                            }
                        }
                    }
                    if(j%8 ==0)
                        k++;
                }
                System.out.println();
                k=0;
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}



