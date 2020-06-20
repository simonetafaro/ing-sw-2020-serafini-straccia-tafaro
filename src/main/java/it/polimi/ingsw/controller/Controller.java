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
        if(!isPlayerTurn(move.getPlayer())){
            //move.getView().reportError(gameMessage.wrongTurnMessage+"\n"+gameMessage.waitMessage);
            this.model.notify(gameMessage.wrongTurnMessage+"\n"+gameMessage.waitMessage);
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
            //updateDataPlayerMove((PlayerMove) message);
            performMove((PlayerMove) message);
        }
    }

    public void updateDataPlayerMove(PlayerMove move){
        if(turn.getPlayerTurn(move.getPlayer()).isFirstStep()) {
            System.out.println("sono nell'if");
            turn.getPlayerTurn(move.getPlayer()).setTurnWorker(move.getWorker());
        }
    }

    public void setWorker(SetWorkerPosition worker){
        if(!model.getBoard().getCell(worker.getX(), worker.getY()).isFree()){
            model.notifyOccupiedCell();
            return;
        }
        model.setWorkers(worker);
        /*
        Player currPlayer = model.getPlayer(worker.getID());
        if(worker.getWorkerNum() == 1 )
            currPlayer.setWorker1(new Worker(worker.getID(), model.getBoard().getCell(worker.getX(), worker.getY()), 1, worker.getColor()));
        else
            currPlayer.setWorker2(new Worker(worker.getID(), model.getBoard().getCell(worker.getX(), worker.getY()), 2, worker.getColor()));

         */
        model.notifySetWorker(worker);
    }

    public void setWorkersMessage(){
        model.notifySetWorkers();
    }

}
