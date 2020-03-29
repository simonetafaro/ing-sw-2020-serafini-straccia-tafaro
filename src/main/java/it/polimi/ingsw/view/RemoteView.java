package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.MoveMessage;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observ.Observer;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.utils.gameMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {
        @Override
        public void update(String message) {
            /**Questa update è sollecitata dalla notify di una SocketClientConnection.
             * Quando un client scrive qualcosa viene lanciata una notify
             * */
            /**Questo stampa sul server ciò che il client ha scritto
             * */
            System.out.println("Received from " +getPlayer().getNickname() +" "+ message);
            try{
                /**Gestione del format dell'input 1-1,2 ES:[Worker1, move to cell (1,2)]
                 */
                handleMove( Integer.parseInt(message.substring(0,1)),
                            Integer.parseInt(message.substring(2,3)),
                            Integer.parseInt(message.substring(4,5)));

            }catch(IllegalArgumentException e){
                clientConnection.asyncSend("Error!");
            }
        }
    }

    private ClientConnection clientConnection;

    public RemoteView(Player player, ClientConnection c) {
        super(player);
        this.clientConnection = c;
        /**Aggiungo un observer alla connessione tra client e server
         * */
        c.addObserver(new MessageReceiver());

    }

    @Override
    protected void showMessage(Object message) {
        clientConnection.asyncSend(message);
    }

    @Override
    public void update(MoveMessage message) {
        /**Update chiamata dalla notify del model quando effettuo un cambiamento sul model
         * Il paramentro che ricevo contiene la nuova board aggiornata,
         * */
        //Mostro il nuovo campo di gioco aggiornato
        System.out.println("Notifica alle remoteview dal model");

        try {
            showMessage(message.getBoard().clone());
        }catch (CloneNotSupportedException e){
            System.err.println(e.getMessage());
        }
        //message.getBoard().printBoard();
        String resultMsg = "";

        if (message.isHasWon()) {
            if (message.getPlayer() == getPlayer()) {
                /**Il player che ha fatto questa mossa ha vinto,
                 * se sono nella sua view mando messaggio WIN
                 * altrimenti mando messaggio LOSE
                 **/
                resultMsg = gameMessage.winMessage + "\n";
            } else {
                resultMsg = gameMessage.loseMessage + "\n";
            }
        }
        else {
            if(message.getNextTurn()== getPlayer().getColor())
                resultMsg += gameMessage.moveMessage;
            else
                resultMsg += gameMessage.waitMessage;
        }
        /**Stampo a video del client il messaggio creato
         * */
        showMessage(resultMsg);
    }

}
