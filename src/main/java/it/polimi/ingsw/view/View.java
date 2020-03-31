package it.polimi.ingsw.view;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.MoveMessage;
import it.polimi.ingsw.model.PlayerMove;
import it.polimi.ingsw.observ.*;

import java.io.IOException;

public abstract class View extends Observable<PlayerMove> implements Observer<MoveMessage> {

    private Player player;

    protected View(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }

    protected abstract void showMessage(Object message);

    /**Questa funzione viene chiamata dalla remoteView dopo che un client
     * scrive qualcosa e fa una notify
     * */
    void handleMove(int worker, int row, int column) {
        /**Stampa sul server la mossa ricevuta
         **/
        System.out.println("Worker"+worker+" move to "+row + " " + column);
        /**Questa notify chiama la update all'interno del controller
         * perchè il controller è observer di Player1View e Player2View
         * */
        notify(new PlayerMove(player, worker, row, column, this));
    }

    public void reportError(String message){
        showMessage(message);
    }

}