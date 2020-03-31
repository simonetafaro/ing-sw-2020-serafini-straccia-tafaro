package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
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
        if(!isPlayerTurn(move.getPlayer()))
            return;
        //Check input
        if(move.getRow()<0 || move.getRow()>=5 || move.getColumn()<0 || move.getColumn()>=5){
            move.getView().reportError(gameMessage.wrongInputMessage+"\n"+gameMessage.moveAgainMessage);
            return;
        }
        /**Controlla che la cella scelta dal giocatore sia libera, tra quelle possibili
         * e che il dislivello non sia maggiore di 1
         * */
        if(!isReachableCell(move))
            return;
        if(!isEmptyCell(move))
            return;
        if(!isLevelDifferenceAllowed(move))
            return;

            /**Nel caso di turno e mossa consentiti
         * chiamo la model che va a effetturare la mossa.
         * */
        System.out.println("la mossa che voglio fare è fattibile");
        model.performMove(move);
        model.updateTurn();
    }

    //metodi che mi controllano se la mossa che voglio fare è possibile
    //mio turno, cella vuota e nelle 8 adiacenti e con un dislivello di massimo 1
    public boolean isPlayerTurn(Player player) {

        return player.getColor() == model.getTurn();
    }
    public boolean isReachableCell(PlayerMove move){
        return move.getWorker().getWorkerPosition().isClosedTo(model.getBoard().getCell(move.getRow(),move.getColumn()));
    }
    public boolean isEmptyCell(PlayerMove move){
        return model.getBoard().getCell(move.getRow(),move.getColumn()).isFree();
    }
    public boolean isLevelDifferenceAllowed(PlayerMove move){
        return (model.getBoard().getCell(move.getRow(), move.getColumn())).getLevel() - ((move.getWorker().getWorkerPosition()).getLevel()) <= 1;
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
