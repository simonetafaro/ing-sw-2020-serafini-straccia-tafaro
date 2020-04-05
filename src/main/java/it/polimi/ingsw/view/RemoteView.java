package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.MoveMessage;
import it.polimi.ingsw.model.PlayerMove;
import it.polimi.ingsw.model.PlayerMoveEnd;
import it.polimi.ingsw.observ.Observer;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.utils.gameMessage;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String>{
        @Override
        public void update(String message) {
            /*Questa update è sollecitata dalla notify di una SocketClientConnection.
             * Quando un client scrive qualcosa viene lanciata una notify
             * */
            /*Questo stampa sul server ciò che il client ha scritto
             * */
            System.out.println("Received from " +getPlayer().getNickname() +" "+ message);
            try{
                /*Gestione del format dell'input M 1-1,2
                 * ES:[Move Worker1, move to cell (1,2)]
                 */
                //M 1-2,3
                if(message.equals("END")){
                    isEndNotify();
                    return;
                }
                if(standardInput(message))
                    handleMove(message.charAt(0),
                            Integer.parseInt(message.substring(2, 3)),
                            Integer.parseInt(message.substring(4, 5)),
                            Integer.parseInt(message.substring(6, 7)));
                else
                    clientConnection.send(gameMessage.wrongInputMessage+ gameMessage.insertAgain);
            }catch(IllegalArgumentException e){
                clientConnection.asyncSend("Error!");
            }
        }
    }

    private boolean standardInput(String message){
        //M 1-1,2
        return message.length()==7 && (message.charAt(0)=='M' || message.charAt(0)=='B') && (message.charAt(2)=='1' ||
                    message.charAt(2)=='2') && Integer.parseInt(message.substring(4,5))>=0 && Integer.parseInt(message.substring(4,5))<=4 &&
                        Integer.parseInt(message.substring(6,7))>=0 && Integer.parseInt(message.substring(6,7))<=4;
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
        //clientConnection.asyncSend(message);
        clientConnection.send(message);
    }

    @Override
    public void update(MoveMessage message) {
        /*Update chiamata dalla notify del model quando effettuo un cambiamento sul model
         * Il paramentro che ricevo contiene la nuova board aggiornata,
         * */
        //Mostro il nuovo campo di gioco aggiornato
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
                resultMsg += gameMessage.TurnMessage;
            else
                resultMsg += gameMessage.waitMessage;
        }
        /**Stampo a video del client il messaggio creato
         * */
        showMessage(resultMsg);

        /*if(message.getPlayer() == getPlayer())
            clientConnection.getLatchMove().countDown();
        */
        }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }
}
