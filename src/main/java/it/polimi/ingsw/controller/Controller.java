package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observ.Observer;
import it.polimi.ingsw.utils.gameMessage;

public class Controller implements Observer<PlayerMove> {

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
    public void update(PlayerMove message) {
        performMove(message);
    }


}
