package it.polimi.ingsw.model;

import java.util.List;

public class PlayerTurn {
    private Step[] steps= new Step[3];
    private Player turnPlayer;
    private Worker worker;
    private Step currStep;
    private int i;

    public PlayerTurn(Player player){
        this.turnPlayer=player;
        steps[0]=new Step();
        steps[1]=new Step();
        steps[2]=new Step();
        this.i=0;
        currStep = steps[i];
        this.worker= null;
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }
    public Step getCurrStep() {
        return currStep;
    }
    public int getI(){
        return i+1;
    }

    public void updateStep(){
        i++;
        currStep=steps[i];
    }
    public void resetStep(){
        this.i=0;
        currStep=steps[i];
    }

    public boolean isFirstStep(){
        return currStep==steps[0];
    }
    public void setTurnWorker(Worker worker) {
        this.worker = worker;
    }
    public Worker getTurnWorker() {
        return this.worker;
    }

}
