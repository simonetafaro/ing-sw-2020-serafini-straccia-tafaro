package it.polimi.ingsw;

public class Cell {

    private int posX;
    private int posY;
    private int level;
    private boolean freeSpace;

    public Cell(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
        this.level=0;
    }

    public boolean isClosedTo(Cell cell){
        return (this.getPosX() - cell.getPosX() <= 1 && this.getPosX() - cell.getPosX() >= -1) &&
                (this.getPosY() - cell.getPosY() <= 1 && this.getPosY() - cell.getPosY() >= -1);
    }

    public boolean hasFreeCellClosed(Cell[][] board){
        boolean bool=false;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                if((board[this.getPosX()+i][this.getPosY()+j]).isFree() && this.getPosX()+i>=0
                            && this.getPosY()+j>=0 && this.getPosY()+j<5 && this.getPosX()+i<5 &&
                        board[this.getPosX()+i][this.getPosY()+j].getLevel()- this.getLevel()<=1){
                        bool=true;
                        System.out.println(("("+this.getPosX()+i+","+this.getPosY()+j+")"));

               }
           }
       }
       return bool;
    }


    public void setPosX(int newPosition){
        this.posX = newPosition;
    }

    public void setPosY(int newPosition){
        this.posY = newPosition;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public int getLevel(){
        return this.level;
    }

    public void setLevel(int level){
        this.level=level;
        if(this.level==4){
            this.setFreeSpace(false);
        }
    }

    public boolean isFree(){
        return this.freeSpace;
    }

    public void setFreeSpace(boolean freeSpace){
        this.freeSpace=freeSpace;
    }

}
