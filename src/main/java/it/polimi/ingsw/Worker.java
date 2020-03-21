package it.polimi.ingsw;

public class Worker {
    private int  workerNum;
    private Cell currentPosition;


    public Worker(Cell position, int number){
        this.currentPosition = position;
        this.workerNum = number;
        position.setFreeSpace(false);
    }

    public void setWorkerPosition(Cell cell){
        this.currentPosition = cell;
        cell.setFreeSpace(false);
    }

    public Cell getWorkerPosition(){
        return this.currentPosition;
    }



    public boolean move(Cell cell) {
            if (this.getWorkerPosition().isClosedTo(cell) && cell.isFree()) {
                if (cell.getLevel() - currentPosition.getLevel() <= 1) {
                    currentPosition.setFreeSpace(true);
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
            System.out.println("Your move is not valid");
            return false;
        }

    }

    public void clear(Cell cell){
        cell.setFreeSpace(true);

    }

}
