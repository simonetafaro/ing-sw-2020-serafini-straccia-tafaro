package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.PlayerMove;
import it.polimi.ingsw.observ.Observer;
import it.polimi.ingsw.utils.gameMessage;

public class Controller implements Observer<PlayerMove> {

    private final Model model;

    public Controller(Model model){
        //super();
        this.model = model;
    }

    private synchronized void performMove(PlayerMove move){
        /**Controlla che è il turno del giocatore che ha inviato la mossa
         * */
        if(!model.isPlayerTurn(move.getPlayer())){
            move.getView().reportError(gameMessage.wrongTurnMessage);
            return;
        }
        //Check input
        if(move.getRow()<0 || move.getRow()>=5 || move.getColumn()<0 || move.getColumn()>=5){
            move.getView().reportError(gameMessage.wrongInputMessage+"\n"+gameMessage.moveAgainMessage);
            return;
        }
        /**Controlla che la cella scelta dal giocatore sia libera, tra quelle possibili
         * e che il dislivello non sia maggiore di 1
         * */
        if(!model.isReachableCell(move)){
            move.getView().reportError(gameMessage.notReachableCellMessage+"\n"+gameMessage.moveAgainMessage);
            return;
        }
        if(!model.isEmptyCell(move)){
            move.getView().reportError(gameMessage.occupiedCellMessage+"\n"+gameMessage.moveAgainMessage);
            return;
        }
        if(!model.isLevelDifferenceAllowed(move)){
            move.getView().reportError(gameMessage.tooHighCellMessage+"\n"+gameMessage.moveAgainMessage);
            return;
        }

        /**Nel caso di turno e mossa consentiti
         * chiamo la model che va a effetturare la mossa.
         * */
        System.out.println("la mossa che voglio fare è fattibile");
        model.performMove(move);
        model.updateTurn();
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
