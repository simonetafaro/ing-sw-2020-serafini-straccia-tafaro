package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observ.Observer;
import it.polimi.ingsw.utils.gameMessage;

public class Controller implements Observer<PlayerMove> {

    private final Model model;
    private Turn turn;

    public Controller(Model model){
        //super();
        this.model = model;
    }
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    //move for italian people is the new "mossa"
    private synchronized void performMove(PlayerMove move){
        /**Controlla che è il turno del giocatore che ha inviato la mossa
         * */
        if(!isPlayerTurn(move.getPlayer())){
            move.getView().reportError(gameMessage.wrongTurnMessage+"\n"+gameMessage.waitMessage);
            return;
        }

        move.getPlayer().getMyCardMethod().play(move, turn, model);
        //System.out.println("la mossa che voglio fare è fattibile");
    }
    public boolean isPlayerTurn(Player player) {
        return player.getColor() == model.getTurn();
    }

    @Override
    public void update(PlayerMove message) {
        /**Questa update è sollecitata quando una remoteView (player1View o player2View)
         * fa una notify. Riceve come paramentro una playerMove che contiene
         * -Row, column
         * -Player
         * -Worker scelto
         * -View
         * */

        performMove(message);
    }


}
