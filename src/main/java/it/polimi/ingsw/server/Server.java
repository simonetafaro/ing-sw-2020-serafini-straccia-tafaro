package it.polimi.ingsw.server;

import it.polimi.ingsw.client.PopUpNumberPlayer;
import it.polimi.ingsw.controller.StartController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.utils.gameMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<Player, ClientConnection> waitingConnection = new HashMap<>();
    private Map<Map, StartController> matchHandler = new HashMap<>();
    //private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();

    private int playerNumber;
    private StartController startController;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.startController= new StartController();
    }

    public StartController getStartController() {
        return startController;
    }

    /*public synchronized void deregisterConnection(ClientConnection c) {
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
    }*/

    public synchronized void lobby(ClientConnection c, Player player){
        if(waitingConnection.size()==0){
            if(c.isUsingGUI()){
                c.send("SetNumberPlayer");
                String number = c.read();
                System.out.println("numero scelto"+ number);
                playerNumber = Integer.parseInt(number);
            }
            else {
                //TODO check if input is a number between 2 and 3 and not a string
                c.send("You are the first player, choose the number of player please.");
                playerNumber = Integer.parseInt(c.read());
            }
        }
        c.send("Wait for cards");
        waitingConnection.put(player, c);
        if (waitingConnection.size() == playerNumber) {

            Model model = new Model();
            startController.setPlayerNumber(playerNumber);
            List<Player> keys = new ArrayList<>(waitingConnection.keySet());
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            ClientConnection c2 = waitingConnection.get(keys.get(1));
            ClientConnection c3 = null;
            Player player1 = keys.get(0);
            Player player2 = keys.get(1);
            //Player player1 = new Player(keys.get(0));
            //Player player2 = new Player(keys.get(1));
            Player player3 = null;
            View player1View = new RemoteView(player1, c1);
            View player2View = new RemoteView(player2, c2);
            View player3View = null;
            Controller controller = new Controller(model);

            model.addObserver(player1View);
            model.addObserver(player2View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);

            if(playerNumber==3){
                c3 = waitingConnection.get(keys.get(2));
                //player3 = new Player(keys.get(2));
                player3= keys.get(2);
                player3View = new RemoteView(player3, c3);
                model.addObserver(player3View);
                player3View.addObserver(controller);
            }

            waitingConnection.clear();

            controller.setTurn(startController.setPlayerWorkerInOrder(model, player1View, player2View, player3View));

            c1.asyncSend(model.getBoardCopy());
            c2.asyncSend(model.getBoardCopy());
            if(c3!=null)
                c3.asyncSend(model.getBoardCopy());



            if(model.getTurn()==(player1.getColor())){
                //p1 è il primo
                c1.asyncSend(gameMessage.TurnMessage);
                c2.asyncSend(gameMessage.waitMessage);
                if(c3!=null)
                    c3.asyncSend(gameMessage.waitMessage);
            }else {
                if(model.getTurn()==(player2.getColor())){
                    c2.asyncSend(gameMessage.TurnMessage);
                    if(c3!=null)
                        c3.asyncSend(gameMessage.waitMessage);
                    c1.asyncSend(gameMessage.waitMessage);
                }else{
                    if(c3!=null && model.getTurn()==(player3.getColor())){
                        c3.asyncSend(gameMessage.TurnMessage);
                        c1.asyncSend(gameMessage.waitMessage);
                        c2.asyncSend(gameMessage.waitMessage);
                    }
                }
            }
            c1.getInputFromClient();
            c2.getInputFromClient();
            if(c3!=null)
                c3.getInputFromClient();

            startController.cleanChosenCards();
            startController.cleanDeck();
            waitingConnection.clear();
            startController.setBlue(false);
            startController.setGrey(false);
            startController.setWhite(false);
        }
    }

    private void createMatch(Map<Player,ClientConnection> waitingConnection, int playerN){

        new Thread(new Runnable() {
            @Override
            public void run() {
                }
        }).start();

    }

    public void run(){
        while(true){
            try {
                    Socket newSocket = serverSocket.accept();

                    SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                    executor.submit(socketConnection);

            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
    }
