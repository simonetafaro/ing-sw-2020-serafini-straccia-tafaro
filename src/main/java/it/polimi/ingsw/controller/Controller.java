package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observ.Observer;
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
        if(message instanceof Worker) {
            setWorker((Worker) message);
            model.notifySetWorker((Worker) message);
        }
        System.out.println("controller update");
        //123performMove(message);
    }

    public void setWorker(Worker worker){
        Player currPlayer = model.getPlayer(worker.getID());
        if(worker.getWorkerNum() == 1 )
            currPlayer.setWorker1(new Worker(worker.getID(), model.getBoard().getCell(worker.getWorkerPosition().getPosX(), worker.getWorkerPosition().getPosY()), 1, worker.getPlayerColor()));
        else
            currPlayer.setWorker2(new Worker(worker.getID(), model.getBoard().getCell(worker.getWorkerPosition().getPosX(), worker.getWorkerPosition().getPosY()), 2, worker.getPlayerColor()));
    }

}
