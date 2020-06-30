package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observ.Observer;
import it.polimi.ingsw.utils.SetWorkerPosition;
import it.polimi.ingsw.utils.gameMessage;

/**
 * Controller (Model View Controller Pattern) class that manage game
 */
public class Controller implements Observer<Object> {

    private final Model model;
    private Turn turn;

    /**
     * @param model Controller constructor, assign model to controller
     */
    public Controller(Model model) {
        this.model = model;
    }

    /**
     * @param turn set Turn in controller, used for manage game
     */
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    /**
     * @return turn
     * Used only in testClass
     */
    public Turn getTurn() {
        return turn;
    }

    /**
     * @param move This method assign to "move" player and worker object of the model.
     *             If is player Turn calls play method, based on player card, that manage game step.
     *             Otherwise show message "wrong turn".
     */
    public synchronized void performMove(PlayerMove move) {
        move.setPlayer(model.getPlayerFromColor(move.getColor()));
        if (!(move instanceof PlayerMoveEnd)) {
            if (move.getWorker().getWorkerNum() == 1)
                move.setWorker(model.getPlayerFromColor(move.getColor()).getWorker1());
            else
                move.setWorker(model.getPlayerFromColor(move.getColor()).getWorker2());
        }
        if (!isPlayerTurn(move.getPlayer())) {
            this.model.notify(move.getColor() + " " + gameMessage.wrongTurnMessage + "\n" + gameMessage.waitMessage);
            return;
        }
        move.getPlayer().getMyCardMethod().play(move, turn, model);
    }

    /**
     * @param player
     * @return true if is player turn
     */
    public boolean isPlayerTurn(Player player) {
        return player.getColor() == model.getTurn();
    }

    /**
     * @param message Observer-Observable pattern, this method run when notify in RemoteView is called
     */
    @Override
    public void update(Object message) {
        if (message instanceof SetWorkerPosition) {
            setWorker((SetWorkerPosition) message);
        }
        if (message instanceof PlayerMove) {
            performMove((PlayerMove) message);
        }
        if (message instanceof String) {
            if (message.equals("quitGameClientCloseConnection"))
                model.notify("quitClient");
        }
    }

    /**
     * @param worker Manage setup of workers position on the board for the first time
     */
    public void setWorker(SetWorkerPosition worker) {
        if (!model.getBoard().getCell(worker.getX(), worker.getY()).isFree()) {
            model.notify(model.getTurn() + " workerOccupiedCell");
            return;
        }
        model.setWorkers(worker);
        model.notifySetWorker(worker);
    }

    /**
     * Notify remoteView when is time to set worker position on the board
     */
    public void setWorkersMessage() {
        model.notify(model.getTurn() + "setWorkers");
    }

}
