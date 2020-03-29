package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.utils.gameMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<Player, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();

    private int playerNumber;
    private boolean firstPlayer;
    private boolean Blue, Grey, White;
    private Model model;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.firstPlayer=true;
        this.model = new Model();
    }

    public synchronized Model getModel() {
        return model;
    }

    public synchronized void deregisterConnection(ClientConnection c) {
        ClientConnection opponent = playingConnection.get(c);
        if(opponent != null) {
            opponent.closeConnection();
        }
        playingConnection.remove(c);
        playingConnection.remove(opponent);
        Iterator<Player> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
            }
        }
    }

    public synchronized void lobby(ClientConnection c, Player player){
        waitingConnection.put(player, c);
        //Creo la partita se ho nella lobby il numero di giocatori scelto
        if (waitingConnection.size() == playerNumber) {
            //Questa sarà una lista di player
            List<Player> keys = new ArrayList<>(waitingConnection.keySet());
            //Crea le ClientConnection per ogni player presente nella Hasmap
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            ClientConnection c2 = waitingConnection.get(keys.get(1));
            ClientConnection c3 = null;
            //Creo due istanze di player con i dati inseriti dai client
            Player player1 = new Player(keys.get(0));
            Player player2 = new Player(keys.get(1));
            Player player3 = null;
            //Creo una remote view per ogni player con la relativa connessione creata
            View player1View = new RemoteView(player1, c1);
            View player2View = new RemoteView(player2, c2);
            View player3View = null;
            //Creo un controller a cui passo il model creato
            Controller controller = new Controller(this.model);
            //Le remoteView dei players osservano il model
            model.addObserver(player1View);
            model.addObserver(player2View);
            //Il controller osserva le remoteView dei players
            player1View.addObserver(controller);
            player2View.addObserver(controller);
            //playingConnection.put(c1, c2);
            //playingConnection.put(c2, c1);
            if(playerNumber==3){
                c3 = waitingConnection.get(keys.get(2));
                player3 = new Player(keys.get(2));
                player3View = new RemoteView(player3, c3);
                model.addObserver(player3View);
                player3View.addObserver(controller);
            }
            setPlayerWorkerinOrder(model, player1View, player2View, player3View);

            /**c1.asyncSend(model.getBoardCopy());
            c2.asyncSend(model.getBoardCopy());
            if(c3!=null)
                c3.asyncSend(model.getBoardCopy());
            */
            waitingConnection.clear();
            //gestione stampa primo turno

            if(model.isPlayerTurn(player1)){
                //p1 è il primo
                c1.asyncSend(gameMessage.moveMessage);
                c2.asyncSend(gameMessage.waitMessage);
                if(c3!=null)
                    c3.asyncSend(gameMessage.waitMessage);
            }else {
                if(model.isPlayerTurn(player2)){
                    c2.asyncSend(gameMessage.moveMessage);
                    if(c3!=null)
                        c3.asyncSend(gameMessage.waitMessage);
                    c1.asyncSend(gameMessage.waitMessage);
                }else{
                    if(c3!=null && model.isPlayerTurn(player3)){
                        c3.asyncSend(gameMessage.moveMessage);
                        c1.asyncSend(gameMessage.waitMessage);
                        c2.asyncSend(gameMessage.waitMessage);
                    }
                }
            }
            //model.getBoardCopy().printBoard();
        }
    }

    public void run(){
        while(true){
            try {
                    Socket newSocket = serverSocket.accept();
                    if(firstPlayer){
                        SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this, firstPlayer);
                        firstPlayer=false;
                        executor.submit(socketConnection);
                    }
                    else {
                        SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this, firstPlayer);
                        executor.submit(socketConnection);
                    }
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

    public synchronized boolean isBlue() {
            return Blue;
    }
    public synchronized boolean isGrey() {
            return Grey;

    }
    public synchronized boolean isWhite() {
            return White;
    }
    public synchronized void setBlue(boolean blue) {
            Blue = blue;
    }
    public synchronized void setGrey(boolean grey) {
            Grey = grey;
    }
    public synchronized void setWhite(boolean white) {
            White = white;
    }

    public synchronized void setPlayerNumber(int playerNumber){
        System.out.println("Set playerNumber");
        this.playerNumber=playerNumber;
    }

    public void setPlayerWorkerinOrder(Model model, View player1, View player2, View player3) {
        int compare;
        //trovare l'ordine di gioco tra i player
        compare = player1.getPlayer().getBirthdate().compareDate(player2.getPlayer().getBirthdate());
        if(compare==1){
            //player1 è più giovane
            if(player3!=null){
                compare= player1.getPlayer().getBirthdate().compareDate(player3.getPlayer().getBirthdate());
                if(compare==1){
                    //il piu giovane è player1
                    model.setPlayOrder(player1.getPlayer().getColor(),player2.getPlayer().getColor(), player3.getPlayer().getColor());
                }
                if(compare==-1 || compare ==0){
                    //il più giovane è player 3
                    model.setPlayOrder(player3.getPlayer().getColor(),player1.getPlayer().getColor(), player2.getPlayer().getColor());
                }
            }else{
                model.setPlayOrder(player1.getPlayer().getColor(),player2.getPlayer().getColor());
            }
        }else{
            //player 2 è più giovane
            if(player3!=null){
                compare= player2.getPlayer().getBirthdate().compareDate(player3.getPlayer().getBirthdate());
                if(compare==1){
                    //il piu giovane è player2
                    model.setPlayOrder(player2.getPlayer().getColor(),player3.getPlayer().getColor(), player1.getPlayer().getColor());
                }
                if(compare==-1 || compare ==0){
                    //il più giovane è player 3
                    model.setPlayOrder(player3.getPlayer().getColor(),player1.getPlayer().getColor(), player2.getPlayer().getColor());
                }
            }
            else{
                model.setPlayOrder(player2.getPlayer().getColor(),player1.getPlayer().getColor());
            }
        }
    }
}
