package it.polimi.ingsw;

public class Worker {
    private int  workerNum;
    private Cell currentPosition;
    private  PlayerColor color;
    private boolean win;


    public Worker(Cell position, int number, PlayerColor color){
        this.currentPosition = position;
        this.workerNum = number;
        position.setFreeSpace(false);
        this.color= color;
    }

    public int getWorkerNum() {
        return workerNum;
    }

    public char getColor() {
        switch (this.color){
            case WHITE: return 'W';
            case GREY:  return 'G';
            case BLUE:  return 'B';
            default: return ' ';
        }

    }

    public void setWorkerPosition(Cell cell){
        this.currentPosition = cell;
        cell.setFreeSpace(false);
        cell.setCurrWorker(this);
    }

    public Cell getWorkerPosition(){
        return this.currentPosition;
    }

    public boolean winner() {
        return win;
    }

    public boolean move(Cell cell) {
            if (this.getWorkerPosition().isClosedTo(cell) && cell.isFree()) {
                if (cell.getLevel() - currentPosition.getLevel() <= 1) {
                    if(currentPosition.getLevel()==2 && cell.getLevel()==3){
                        win = true;
                    }
                    currentPosition.setFreeSpace(true);
                    currentPosition.deleteCurrWorker();
                    this.setWorkerPosition(cell);

                    return true;
                }
                else{
                    return false;
                }
            }
                else {
                    System.out.println("Your move is not valid");
                    return false;

            }
    }

    public boolean build(Cell cell){
        if (this.getWorkerPosition().isClosedTo(cell) && cell.isFree()) {
           cell.setLevel(cell.getLevel()+1);
           return true;
        }
        else{
            System.out.println("Your build is not valid");
            return false;
        }

    }

    public void clear(){
        this.currentPosition.setFreeSpace(true);
        this.currentPosition.setCurrWorker(null);

    }

}
