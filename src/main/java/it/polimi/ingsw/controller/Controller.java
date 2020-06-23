package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observ.Observer;
import it.polimi.ingsw.utils.SetWorkerPosition;
import it.polimi.ingsw.utils.gameMessage;

import java.util.List;

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
        move.setPlayer(model.getPlayerFromColor(move.getColor()));
        if(!(move instanceof PlayerMoveEnd)) {
            if (move.getWorker().getWorkerNum() == 1)
                move.setWorker(model.getPlayerFromColor(move.getColor()).getWorker1());
            else
                move.setWorker(model.getPlayerFromColor(move.getColor()).getWorker2());
        }
        if(!isPlayerTurn(move.getPlayer())){
            this.model.notify(move.getColor()+" "+ gameMessage.wrongTurnMessage+"\n"+gameMessage.waitMessage);
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
            setWorker((SetWorkerPosition) message);
        }
        if(message instanceof PlayerMove) {
            performMove((PlayerMove) message);
        }
    }

    public void setWorker(SetWorkerPosition worker){
        if(!model.getBoard().getCell(worker.getX(), worker.getY()).isFree()){
            model.notifyOccupiedCell();
            return;
        }
        model.setWorkers(worker);
        model.notifySetWorker(worker);
    }
    public void setWorkersMessage(){
        model.notifySetWorkers();
    }

}
