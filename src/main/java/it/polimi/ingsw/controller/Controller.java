package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observ.Observer;
import it.polimi.ingsw.utils.SetWorkerPosition;
import it.polimi.ingsw.utils.gameMessage;

public class Controller implements Observer<Object> {

    private final Model model;
    private Turn turn;

    public Controller(Model model){
        this.model = model;
    }
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    private synchronized void performMove(PlayerMove move){
        if(!isPlayerTurn(move.getPlayer())){
            move.getView().reportError(gameMessage.wrongTurnMessage+"\n"+gameMessage.waitMessage);
            return;
        }

        move.getPlayer().getMyCardMethod().play(move, turn, model);
    }
    public boolean isPlayerTurn(Player player) {
        return player.getColor() == model.getTurn();
    }

    @Override
    public void update(Object message) {
        if(message instanceof SetWorkerPosition) {
            System.out.println("set worker ricevuto");
            setWorker((SetWorkerPosition) message);
        }

        //123performMove(message);
    }

    public void setWorker(SetWorkerPosition worker){
        if(!model.getBoard().getCell(worker.getX(), worker.getY()).isFree())
            return;

        Player currPlayer = model.getPlayer(worker.getID());
        if(worker.getWorkerNum() == 1 )
            currPlayer.setWorker1(new Worker(worker.getID(), model.getBoard().getCell(worker.getX(), worker.getY()), 1, worker.getColor()));
        else
            currPlayer.setWorker2(new Worker(worker.getID(), model.getBoard().getCell(worker.getX(), worker.getY()), 2, worker.getColor()));
        model.notifySetWorker(worker);
    }

    public void setWorkersMessage(){
        model.notifySetWorkers();
    }

}
