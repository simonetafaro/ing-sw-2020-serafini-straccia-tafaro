package it.polimi.ingsw.server;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.PopUpNumberPlayer;
import it.polimi.ingsw.controller.StartController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.utils.ColorCheck;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.setupMessage;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.utils.gameMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    //private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();

    private Map<Integer, List<ClientConnection>> singleMatchConnection = new HashMap<>();
    private Map<Integer, Integer> matchHandler = new HashMap<>();

    private List<ClientConnection> activeConnection = new ArrayList<>();

    private int playerNumber;
    private Integer currentMatch = 0;
    private StartController startController;



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
            if(true){
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



    public void matchManager(ClientConnection socketConnection, String nickname){
        if(singleMatchConnection.get(currentMatch).size() == 0){
            //primo giocatore connesso in questa partita
            singleMatchConnection.get(currentMatch).add(socketConnection);
            socketConnection.setFirstPlayer(true);
            broadcastStringMessage(singleMatchConnection.get(currentMatch) , "The first player is "+nickname);
        }
    }

    public void broadcastStringMessage(List<ClientConnection> match, String message){
        match.forEach( c -> c.send(message));
    }

    public void broadcastObjectMessage(List<ClientConnection> match, Object message){
        match.forEach( c -> c.send(message));
    }

    /*Return true if name is unique*/
    public boolean checkNicknameUnicity(String name){
        boolean result = true;
        for(ClientConnection c: activeConnection){
            result = result && !c.getNickName().equals(name);
        }
        return result;
    }
    public void setPlayerNumber(int playerNumber, ClientConnection connection){
        singleMatchConnection.get(currentMatch).forEach(c -> {
                if(c.equals(connection)){
                    matchHandler.put(currentMatch, playerNumber);
                }
        });
        addPlayerToMatch();
    }

    public void addPlayerToMatch(){
        while((singleMatchConnection.get(currentMatch).size() < matchHandler.get(currentMatch)) || activeConnection.size() >0 ) {
            singleMatchConnection.get(currentMatch).add(activeConnection.get(0));
            broadcastStringMessage(singleMatchConnection.get(currentMatch), activeConnection.get(0).getNickName()+" is now in the match");
            activeConnection.remove(0);
        }
        if(singleMatchConnection.get(currentMatch).size() == matchHandler.get(currentMatch)){
            this.currentMatch++;
            if(activeConnection.get(0) != null){
                matchManager(activeConnection.get(0), activeConnection.get(0).getNickName());
                activeConnection.remove(0);
            }
        }
    }



    /**new implementations*/
    private static int clientId = 1;
    private List<Player> twoPlayerMatch;
    private List<Player> threePlayerMatch;
    private Map<Integer, String> playerData;
    private Map<Integer, Socket> activeClientConnection;
    private Map<Integer, ObjectOutputStream> ClientConnectionOutput;
    private Map<Integer, ObjectInputStream> ClientConnectionInput;

    public static int getClientId() {
        return clientId;
    }
    public static void increaseClientId() {
        clientId++;
    }

    public List<Player> getTwoPlayerMatch() {
        return twoPlayerMatch;
    }
    public List<Player> getThreePlayerMatch() {
        return threePlayerMatch;
    }
    public Map<Integer, Socket> getActiveClientConnection() {
        return activeClientConnection;
    }
    public Map<Integer, String> getPlayerData() {
        return playerData;
    }

    public Map<Integer, ObjectOutputStream> getClientConnectionOutput() {
        return ClientConnectionOutput;
    }

    public Map<Integer, ObjectInputStream> getClientConnectionInput() {
        return ClientConnectionInput;
    }

    public Server() throws IOException {
        this.activeClientConnection = new HashMap<>();
        this.playerData = new HashMap<>();
        this.twoPlayerMatch = new ArrayList<>();
        this.threePlayerMatch = new ArrayList<>();
        this.ClientConnectionOutput = new HashMap<>();
        this.ClientConnectionInput = new HashMap<>();
        /*
        this.serverSocket = new ServerSocket(PORT);
        this.startController= new StartController();
        */
    }

    public void run(){
        ExecutorService executorSocket = Executors.newCachedThreadPool();
        //thread always on
        executorSocket.submit(new SocketClientConnection(PORT, this));

    }

    public void createTwoPlayersMatch(Player player1, Player player2){
        new Thread(new Runnable() {
            @Override
            public void run() {
                    ColorCheck matchColor = new ColorCheck(true, true, true);
                    ObjectOutputStream output1, output2;
                    List<ObjectOutputStream> broadcast = new ArrayList<>();
                    List<String> playerName = new ArrayList<>();
                    ObjectInputStream input1, input2;
                    try {
                        playerName.add(playerData.get(player1.getID()));
                        playerName.add(playerData.get(player2.getID()));

                        input1 = ClientConnectionInput.get(player1.getID());
                        output1 = ClientConnectionOutput.get(player1.getID());
                        broadcast.add(output1);

                        input2 = ClientConnectionInput.get(player2.getID());
                        output2 = ClientConnectionOutput.get(player2.getID());
                        broadcast.add(output2);

                        broadcastMessage(broadcast, "Match created");

                        //broadcastMessage(broadcast, "Players in this match:"+player1.getNickname()+player2.getNickname());

                        Thread t1 = handleColorChoose(matchColor, input1, player1.getID(), broadcast);
                        Thread t2 = handleColorChoose(matchColor, input2, player2.getID(), broadcast);
                        t1.join();
                        t2.join();

                        broadcastMessage(broadcast, "firstPlayer: "+player1.getID());

                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                }
        }).start();
    }

    public void broadcastMessage(List<ObjectOutputStream> match, Object message){
        match.forEach( c -> {
            try {
                c.writeObject(message);
                c.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized boolean parseColor(ColorCheck matchColor, String color, int player1, List<ObjectOutputStream> broadcast){
        switch (color){
            case "WHITE": if(matchColor.isWhite()){
                broadcastMessage(broadcast, player1+" white");
                matchColor.setWhite(false);
                return true;
            }else{
                broadcastMessage(broadcast, "white locked");
                return false;
            }
            case "BLUE": if(matchColor.isBlue()){
                broadcastMessage(broadcast, player1+" blue");
                matchColor.setBlue(false);
                return true;
            }else{
                broadcastMessage(broadcast, "blue locked");
                return false;
            }
            case "GREY": if(matchColor.isGrey()){
                broadcastMessage(broadcast, player1+" grey");
                matchColor.setGrey(false);
                return true;
            }else{
                broadcastMessage(broadcast, "grey locked");
                return false;
            }
        }
        return false;
    }

    private Thread handleColorChoose (ColorCheck matchColor, ObjectInputStream input, int player, List broadcast){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String color = null;
                boolean playerColor = false;
                while (!playerColor) {
                    try {
                        color = (String) input.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    System.out.println(color);
                    playerColor = parseColor(matchColor, color, player, broadcast);
                }
                System.out.println("thread colore server morto");
            }
        });
        t.start();
        return t;
    }

}
