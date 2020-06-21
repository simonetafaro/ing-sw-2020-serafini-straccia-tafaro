package it.polimi.ingsw.server;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.ColorCheck;
import it.polimi.ingsw.utils.PlayerColor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server{

    private static int clientId = 1;
    private List<Player> twoPlayerMatch;
    private List<Player> threePlayerMatch;
    private Map<Integer, String> playerData;
    private Map<Integer, Socket> activeClientConnection;
    private Map<Integer, ObjectOutputStream> ClientConnectionOutput;
    private Map<Integer, ObjectInputStream> ClientConnectionInput;
    private List<Game> activeGame;
    private static final int PORT = 12345;
    private final int  portGame = 14456;

    public Server() throws IOException {
        this.activeClientConnection = new HashMap<>();
        this.playerData = new HashMap<>();
        this.twoPlayerMatch = new ArrayList<>();
        this.threePlayerMatch = new ArrayList<>();
        this.ClientConnectionOutput = new HashMap<>();
        this.ClientConnectionInput = new HashMap<>();
        this.activeGame = new ArrayList<>();
    }

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


    public void run(){
        ExecutorService executorSocket = Executors.newCachedThreadPool();
        //thread always on
        executorSocket.submit(new SocketClientConnection(PORT, this, portGame));

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
                ArrayList<String> cards = new ArrayList<>();
                Deck deck = new Deck();
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

                        Thread t1 = handleColorChoose(matchColor, input1, player1, broadcast);
                        Thread t2 = handleColorChoose(matchColor, input2, player2, broadcast);
                        t1.join();
                        t2.join();

                        broadcastMessage(broadcast, "firstPlayer: "+player1.getID());
                        broadcastMessage(broadcast, "secondPlayer: "+player2.getID());

                        try {
                            Object obj = input1.readObject();
                            if(obj instanceof ArrayList){
                                cards = (ArrayList) obj;
                                cards.forEach((cardName)-> System.out.println(cardName));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        cards.forEach((cardName)-> { deck.setChosenCard(cardName); });

                        broadcastMessage(broadcast, cards);
                        String cardName = (String) input2.readObject();
                        String[] parts = cardName.split(" ");
                        cards.remove(parts[1]);
                        player2.setMyCard(parts[1]);

                        player1.setMyCard(cards.get(0));
                        player1.setInput(input1);
                        player1.setOutput(output1);
                        player2.setInput(input2);
                        player2.setOutput(output2);
                        //call lobby

                        Game game = new Game(player1, player2);

                }catch (Exception e){
                        System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    public void createThreePlayersMatch(Player player1, Player player2, Player player3){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ColorCheck matchColor = new ColorCheck(true, true, true);
                ObjectOutputStream output1, output2, output3;
                List<ObjectOutputStream> broadcast = new ArrayList<>();
                List<String> playerName = new ArrayList<>();
                ObjectInputStream input1, input2, input3;
                ArrayList<String> cards = new ArrayList<>();
                Deck deck = new Deck();
                try {
                    playerName.add(playerData.get(player1.getID()));
                    playerName.add(playerData.get(player2.getID()));
                    playerName.add(playerData.get(player3.getID()));

                    input1 = ClientConnectionInput.get(player1.getID());
                    output1 = ClientConnectionOutput.get(player1.getID());
                    broadcast.add(output1);

                    input2 = ClientConnectionInput.get(player2.getID());
                    output2 = ClientConnectionOutput.get(player2.getID());
                    broadcast.add(output2);

                    input3 = ClientConnectionInput.get(player3.getID());
                    output3 = ClientConnectionOutput.get(player3.getID());
                    broadcast.add(output3);

                    broadcastMessage(broadcast, "Match created");

                    //broadcastMessage(broadcast, "Players in this match:"+player1.getNickname()+player2.getNickname());

                    Thread t1 = handleColorChoose(matchColor, input1, player1, broadcast);
                    Thread t2 = handleColorChoose(matchColor, input2, player2, broadcast);
                    Thread t3 = handleColorChoose(matchColor, input3, player3, broadcast);
                    t1.join();
                    t2.join();
                    t3.join();

                    broadcastMessage(broadcast, "firstPlayer: "+player1.getID());
                    broadcastMessage(broadcast, "secondPlayer: "+player2.getID());
                    broadcastMessage(broadcast, "thirdPlayer: "+player3.getID());


                    Object obj = input1.readObject();
                    if(obj instanceof ArrayList){
                        cards = (ArrayList) obj;
                        //cards.forEach((cardName)-> System.out.println(cardName));
                    }

                    cards.forEach((cardName)-> { deck.setChosenCard(cardName); });

                    broadcastMessage(broadcast, cards);
                    String cardName = (String) input2.readObject();
                    String[] parts = cardName.split(" ");
                    cards.remove(parts[1]);
                    player2.setMyCard(parts[1]);

                    broadcastMessage(broadcast, cards);
                    cardName = (String) input3.readObject();
                    parts = cardName.split(" ");
                    cards.remove(parts[1]);
                    player3.setMyCard(parts[1]);

                    player1.setMyCard(cards.get(0));
                    player1.setInput(input1);
                    player2.setInput(input2);
                    player3.setInput(input3);
                    player1.setOutput(output1);
                    player2.setOutput(output2);
                    player3.setOutput(output3);

                    Game game = new Game(player1, player2, player3);
                    //call lobby
                }catch (Exception e){
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    public void broadcastMessage(List<ObjectOutputStream> match, Object message){
        match.forEach( c -> {
            try {
                c.reset();
                c.writeObject(message);
                c.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized boolean parseColor(ColorCheck matchColor, String color, Player player, List<ObjectOutputStream> broadcast){
        switch (color){
            case "WHITE": if(matchColor.isWhite()){
                broadcastMessage(broadcast, player.getID()+" white");
                matchColor.setWhite(false);
                player.setColor(PlayerColor.WHITE);
                return true;
            }else{
                broadcastMessage(broadcast, "white locked");
                return false;
            }
            case "BLUE": if(matchColor.isBlue()){
                broadcastMessage(broadcast, player.getID()+" blue");
                matchColor.setBlue(false);
                player.setColor(PlayerColor.BLUE);
                return true;
            }else{
                broadcastMessage(broadcast, "blue locked");
                return false;
            }
            case "GREY": if(matchColor.isGrey()){
                broadcastMessage(broadcast, player.getID()+" grey");
                matchColor.setGrey(false);
                player.setColor(PlayerColor.GREY);
                return true;
            }else{
                broadcastMessage(broadcast, "grey locked");
                return false;
            }
        }
        return false;
    }

    private Thread handleColorChoose (ColorCheck matchColor, ObjectInputStream input, Player player, List broadcast){
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
