package it.polimi.ingsw;



public class Worker {
    Cell currentPosition;
    int workerNnumber;

    public Worker(Cell currentPosition, int workerNnumber) {
        this.currentPosition = currentPosition;
        this.workerNnumber = workerNnumber;
    }

    public Cell getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Cell currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean move(Cell choosenCell){

        if (choosenCell.isFree()){
            //COntrollo se la cella scelta è tra le 8 possibili

            if(Math.abs(choosenCell.getPosX()-currentPosition.getPosX())<2){
                if(Math.abs(choosenCell.getPosY()-currentPosition.getPosY())<2){
                    if((choosenCell.getLevel()-currentPosition.getLevel())<2){
                        currentPosition.setFreeSpace(true);
                        setCurrentPosition(choosenCell);
                        choosenCell.setFreeSpace(false);
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public void build(Cell choosenCell){

    }

}
