package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.ColorCheck;
import it.polimi.ingsw.utils.PlayerColor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class is used to manage active connection, creating new game room.
 */
public class Server {

    /**
     * Each client is identified by an unique ID
     */
    private static int clientId = 1;
    /**
     * Temporary lobby that contains players who want to play in a two players match
     */
    private List<Player> twoPlayerMatch;
    /**
     * Temporary lobby that contains players who want to play in a three players match
     */
    private List<Player> threePlayerMatch;
    /**
     * Association between client ID and nickname
     */
    private Map<Integer, String> playerData;
    /**
     * Association between connected client ID and his socket
     */
    private Map<Integer, Socket> activeClientConnection;
    /**
     * For each client ID associate his output stream
     */
    private Map<Integer, ObjectOutputStream> ClientConnectionOutput;
    /**
     * For each client ID associate his input stream
     */
    private Map<Integer, ObjectInputStream> ClientConnectionInput;

    /**
     * Port used to open new Server Socket
     */
    private static final int PORT = 12345;

    /**
     * Server constructor, initialize list and Map
     */
    public Server() throws IOException {
        this.activeClientConnection = new HashMap<>();
        this.playerData = new HashMap<>();
        this.twoPlayerMatch = new ArrayList<>();
        this.threePlayerMatch = new ArrayList<>();
        this.ClientConnectionOutput = new HashMap<>();
        this.ClientConnectionInput = new HashMap<>();
    }

    /**
     * @return current clientID
     */
    public static int getClientId() {
        return clientId;
    }

    /**
     * Increase Client ID when new connection is accepted
     */
    public static void increaseClientId() {
        clientId++;
    }

    /**
     * @return list of two players lobby
     */
    public List<Player> getTwoPlayerMatch() {
        return twoPlayerMatch;
    }

    /**
     * @return list of three players lobby
     */
    public List<Player> getThreePlayerMatch() {
        return threePlayerMatch;
    }

    /**
     * @return list of active connection
     */
    public Map<Integer, Socket> getActiveClientConnection() {
        return activeClientConnection;
    }

    /**
     * @return Map that associate clientID with his nickname
     */
    public Map<Integer, String> getPlayerData() {
        return playerData;
    }

    /**
     * @return Map that associate clientID with his Output stream
     */
    public Map<Integer, ObjectOutputStream> getClientConnectionOutput() {
        return ClientConnectionOutput;
    }

    /**
     * @return Map that associate clientID with his Input stream
     */
    public Map<Integer, ObjectInputStream> getClientConnectionInput() {
        return ClientConnectionInput;
    }

    /**
     * Run new Thread (SocketClientConnection) that handle new incoming connection
     */
    public void run() {
        ExecutorService executorSocket = Executors.newCachedThreadPool();
        executorSocket.submit(new SocketClientConnection(PORT, this));

    }

    /**
     * @param player1 contains data of first player
     * @param player2 contains data of second player
     *                  Create Game instance and start game
     */
    public void createTwoPlayersMatch(Player player1, Player player2) {
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

                    Thread t1 = handleColorChoose(matchColor, input1, player1, broadcast);
                    Thread t2 = handleColorChoose(matchColor, input2, player2, broadcast);
                    t1.join();
                    t2.join();

                    broadcastMessage(broadcast, "firstPlayer: " + player1.getID());
                    broadcastMessage(broadcast, "secondPlayer: " + player2.getID());


                    Object obj = input1.readObject();
                    if (obj instanceof ArrayList) {
                        cards = (ArrayList) obj;
                    }

                    cards.forEach((cardName) -> {
                        deck.setChosenCard(cardName);
                    });

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

                    Game game = new Game(player1, player2);

                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    broadcastMessage(broadcast, "quitClient");
                }
            }
        }).start();
    }

    /**
     * @param player1 contains data of first player
     * @param player2 contains data of second player
     * @param player3 contains data of third playes
     *                  Create Game instance and start game
     */
    public void createThreePlayersMatch(Player player1, Player player2, Player player3) {
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

                    Thread t1 = handleColorChoose(matchColor, input1, player1, broadcast);
                    Thread t2 = handleColorChoose(matchColor, input2, player2, broadcast);
                    Thread t3 = handleColorChoose(matchColor, input3, player3, broadcast);
                    t1.join();
                    t2.join();
                    t3.join();

                    broadcastMessage(broadcast, "firstPlayer: " + player1.getID());
                    broadcastMessage(broadcast, "secondPlayer: " + player2.getID());
                    broadcastMessage(broadcast, "thirdPlayer: " + player3.getID());


                    Object obj = input1.readObject();
                    if (obj instanceof ArrayList) {
                        cards = (ArrayList) obj;
                    }

                    cards.forEach((cardName) -> {
                        deck.setChosenCard(cardName);
                    });

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

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    /**
     * @param match contains list of output stream
     * @param message contains object we want to send
     *                Used to send broadcast message to all players in the match
     */
    public void broadcastMessage(List<ObjectOutputStream> match, Object message) {
        match.forEach(c -> {
            try {
                c.reset();
                c.writeObject(message);
                c.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @param matchColor
     * @param color
     * @param player
     * @param broadcast
     * @return true if color is free
     * Check if the color can be assigned to the player or not
     */
    public synchronized boolean parseColor(ColorCheck matchColor, String color, Player player, List<ObjectOutputStream> broadcast) {
        switch (color) {
            case "WHITE":
                if (matchColor.isWhite()) {
                    broadcastMessage(broadcast, player.getID() + " white");
                    matchColor.setWhite(false);
                    player.setColor(PlayerColor.WHITE);
                    return true;
                } else {
                    broadcastMessage(broadcast, "white locked");
                    return false;
                }
            case "BLUE":
                if (matchColor.isBlue()) {
                    broadcastMessage(broadcast, player.getID() + " blue");
                    matchColor.setBlue(false);
                    player.setColor(PlayerColor.BLUE);
                    return true;
                } else {
                    broadcastMessage(broadcast, "blue locked");
                    return false;
                }
            case "GREY":
                if (matchColor.isGrey()) {
                    broadcastMessage(broadcast, player.getID() + " grey");
                    matchColor.setGrey(false);
                    player.setColor(PlayerColor.GREY);
                    return true;
                } else {
                    broadcastMessage(broadcast, "grey locked");
                    return false;
                }
        }
        return false;
    }

    /**
     * @param matchColor
     * @param input
     * @param player
     * @param broadcast
     * @return Thread
     * used to handle color choose from client
     */
    private Thread handleColorChoose(ColorCheck matchColor, ObjectInputStream input, Player player, List broadcast) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String color = null;
                boolean playerColor = false;
                while (!playerColor) {
                    try {
                        color = (String) input.readObject();
                    } catch (IOException e) {
                        //e.printStackTrace();
                        broadcastMessage(broadcast, "quitClient");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    playerColor = parseColor(matchColor, color, player, broadcast);
                }
            }
        });
        t.start();
        return t;
    }
}
