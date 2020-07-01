package it.polimi.ingsw.client;

import it.polimi.ingsw.ClientCLIMain;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.FileManager;
import it.polimi.ingsw.utils.PlayerColor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * ConnectionManagerSocket manages client connection to Server.
 * It initializes client with starting parameter: nickname, color and card.
 * It knows {@link BoardGUI}, if player is using GUI, or {@link ClientCLIMain}
 * if player is using CLI. It is one per each connection client.
 */
public class ConnectionManagerSocket {

    /**
     * Nickname of player
     */
    private String nickname;

    /**
     * player color
     */
    private PlayerColor myColor;

    /**
     * true if nickname is set
     */
    protected boolean nameSet;

    /**
     * player number
     */
    private int playerNumber;

    /**
     * socket client
     */
    private Socket socket;

    /**
     * ObjectOutputStream of client
     */
    private ObjectOutputStream output;

    /**
     * ObjectInputStream of client
     */
    private ObjectInputStream input;

    /**
     * parameters used to manages color choice
     */
    private String playerColor, temporaryColor;

    /**
     * mainframe of boardUI
     */
    private JFrame mainFrame;

    /**
     * Thread used to manages card and color choice
     */
    private Thread t, cardThread;

    /**
     * Socket Port
     */
    private static final int SOCKET_PORT = 12345;

    /**
     * Ip server
     */
    private String Server_IP;

    /**
     * order of this player turn
     */
    private int order;

    /**
     * pointer to ClientSocketMessage
     */
    private ClientSocketMessage clientSocket;

    /**
     * pointer to BoardGUI
     */
    private BoardGUI boardGUI;

    /**
     * pointer to ClientCLIMain
     */
    private ClientCLIMain boardCLI;

    /**
     * player
     */
    private Player player;

    /**
     * client ID
     */
    protected int clientID;

    /**
     * Class constructor
     *
     * @param nickname
     * @param playerNumber
     */
    public ConnectionManagerSocket(String nickname, int playerNumber) {
        this.nickname = nickname;
        this.playerNumber = playerNumber;
        this.myColor = null;
        this.clientID = 0;
        this.playerColor = "null";
        this.boardGUI = null;
    }

    /**
     * @param IP server IP
     *           It sets server IP
     */
    public void setServerData(String IP) {
        this.Server_IP = IP;
    }

    /**
     * @param mainFrame It sets board GUI JFrame of a player that is using GUI
     */
    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * @param clientID It sets client ID
     */
    public void setclientID(int clientID) {
        this.clientID = clientID;
    }

    /**
     * @param player It sets Player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return client ID
     */
    public int getclientID() {
        return this.clientID;
    }

    /**
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return Board CLI if player is playing with CLI
     */
    public ClientCLIMain getBoardCLI() {
        return boardCLI;
    }

    /**
     * @return Board GUI if player is playing with GUI
     */
    public BoardGUI getBoardGUI() {
        return boardGUI;
    }

    /**
     * @return temporary color used in color choice
     */
    public String getTemporaryColor() {
        return this.temporaryColor;
    }

    /**
     * @return Player color
     */
    public PlayerColor getPlayerColorEnum() {
        switch (playerColor.toUpperCase()) {
            case "WHITE":
                return PlayerColor.WHITE;
            case "BLUE":
                return PlayerColor.BLUE;
            case "GREY":
                return PlayerColor.GREY;
        }
        return null;
    }

    /**
     * @return Player string color
     */
    public String getPlayerColor() {
        synchronized (playerColor) {
            return this.playerColor;
        }
    }

    /**
     * @return card Thread
     */
    public Thread getCardThread() {
        return cardThread;
    }

    /**
     * @return order of the player in the game
     */
    public int getOrder() {
        return order;
    }

    /**
     * @throws IOException It calls starting methods
     */
    public void setup() throws IOException {
        this.initializeSocket();
        this.waitForOpponents();
    }

    /**
     * @throws IOException Starting method to choose nickname and player number
     */
    public void initializeSocket() throws IOException {

        socket = new Socket(Server_IP, SOCKET_PORT);
        System.out.println("Connected to server " + Server_IP + " on port " + SOCKET_PORT);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());

        do {
            try {
                output.writeObject(new Integer(this.getclientID()));
                output.flush();
                /*Wait for ID assigned from the server*/
                Integer clientIDRequested = (Integer) input.readObject();
                this.setclientID((int) clientIDRequested);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }
        } while (this.getclientID() == 0);

        do {
            try {
                //NICKNAME
                output.writeObject(this.nickname);
                output.flush();
                String serverAnswer = (String) input.readObject();
                if ("NAME ACCEPTED".equals(serverAnswer)) {
                    nameSet = true;
                }

                //PLAYERNUMBER
                output.writeObject(this.playerNumber);
                output.flush();
                String serverPlayerNumberAnswer = (String) input.readObject();

            } catch (IOException e) {
                System.err.println(e.getMessage());
                ServerCloseConnection("Server connection is not available, closing game...");
                break;
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }
        } while (!nameSet);
    }

    /**
     * @throws IOException Starting method to choose color
     */
    public void waitForOpponents() throws IOException {
        String matchCreated = null;
        do {
            try {
                matchCreated = (String) input.readObject();
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }
        } while (!matchCreated.equals("Match created"));
    }

    /**
     * @param boardGUI It initializes ClientSocketMessageGUI before game starts
     */
    public void initializeMessageSocket(BoardGUI boardGUI) {
        this.boardGUI = boardGUI;
        this.clientSocket = new ClientSocketMessageGUI(this, input, output);
        this.clientSocket.initialize();
    }

    /**
     * @param BoardCLI
     * @return reading Thread in CLI
     * It initializes ClientSocketMessageCLI before game starts
     */
    public Thread initializeMessageSocketCLI(ClientCLIMain BoardCLI) {
        this.boardCLI = BoardCLI;
        this.clientSocket = new ClientSocketMessageCLI(this, input, output);
        return this.clientSocket.initializeCLI();
    }

    /**
     * It resets player color
     */
    public void resetPlayerColor() {
        synchronized (playerColor) {
            this.playerColor = "null";
        }
    }

    /**
     * @param colorResult
     * @param guiInstance
     * @return true if color
     * @throws IOException Method that manages color choices through players
     */
    private boolean handleColorResponse(String colorResult, showPopUpColor guiInstance) throws IOException {
        if (colorResult.toUpperCase().equals(Integer.toString(getclientID()) + " " + getTemporaryColor())) {
            if (guiInstance != null) {
                guiInstance.pressedColor(getTemporaryColor().toLowerCase());
                guiInstance.closeGUI();
            }
            this.playerColor = this.temporaryColor;
            return true;
        }
        if (colorResult.contains("white")) {
            if (guiInstance != null)
                guiInstance.lock("white");
            else
                System.out.println("White already chosen");
        }
        if (colorResult.contains("blue")) {
            if (guiInstance != null)
                guiInstance.lock("blue");
            else
                System.out.println("Blue already chosen");
        }
        if (colorResult.contains("grey")) {
            if (guiInstance != null)
                guiInstance.lock("grey");
            else
                System.out.println("Grey already chosen");
        }
        this.playerColor = "reset";
        return false;
    }

    /**
     * @param color
     * @param guiInstance It sets player color and sends it to server
     */
    public void setColor(String color, showPopUpColor guiInstance) {
        try {
            this.temporaryColor = color;
            switch (color.toUpperCase()) {
                case "WHITE":
                    this.myColor = PlayerColor.WHITE;
                case "BLUE":
                    this.myColor = PlayerColor.BLUE;
                case "GREY":
                    this.myColor = PlayerColor.GREY;
            }
            output.writeObject(color);
            output.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * @param guiInstance
     * @return Thread color
     * thread used to choice color, it is active unless
     * player choose a color not already chosen
     */
    public Thread receiveColorResponse(showPopUpColor guiInstance) {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean colorCheck = false;
                while (!colorCheck) {
                    try {
                        String colorResult = (String) input.readObject();
                        if (colorResult.equals("quitClient")) {
                            ServerCloseConnection("One of your opponents disconnected, closing game...");
                            break;
                        }
                        colorCheck = handleColorResponse(colorResult, guiInstance);
                    } catch (ClassNotFoundException e) {
                        System.err.println(e.getMessage());
                    } catch (IOException e) {
                        ServerCloseConnection("Server connection is not available, closing game...");
                        break;
                    }
                }
            }
        });
        t.start();
        return t;
    }

    /**
     * @param guiInstance
     * @param cliInstance
     * @return Thread card
     * Thread that manages card choice, it is used both for CLI and GUI
     */
    public Thread receiveCard(PickUpCards guiInstance, ClientCLIMain cliInstance) {
        this.cardThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                Object obj;
                while (i < playerNumber) {
                    try {
                        obj = input.readObject();
                        if (obj instanceof ArrayList) {
                            ArrayList cards = null;
                            cards = (ArrayList) obj;
                            if (cards.size() == playerNumber && ConnectionManagerSocket.this.order == 1) {
                                if (guiInstance != null)
                                    guiInstance.updateGodImage(cards);
                                else {
                                    System.out.println("Please choose one of these cards:");
                                    for (Object card : cards) {
                                        System.out.println((String) card);
                                    }
                                    cliInstance.chooseCard(cards);
                                }
                                break;
                            }
                            if ((cards.size() == (playerNumber - 1)) && ConnectionManagerSocket.this.order == 2) {
                                if (guiInstance != null)
                                    guiInstance.updateGodImage(cards);
                                else {
                                    System.out.println("Please choose one of these cards:");
                                    for (Object card : cards) {
                                        System.out.println((String) card);
                                    }
                                    cliInstance.chooseCard(cards);
                                }
                                break;
                            }
                            if (ConnectionManagerSocket.this.order == 0)
                                break;
                            i++;
                        } else {
                            if (obj instanceof String) {
                                if (((String) obj).contains("quitClient")) {
                                    ServerCloseConnection("One of your opponents disconnected, closing game...");
                                    break;
                                }
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        System.err.println(e.getMessage());
                    } catch (IOException e) {
                        ServerCloseConnection("Server connection is not available, closing game...");
                        break;
                    }
                }
            }
        });
        this.cardThread.start();

        return this.cardThread;
    }

    /**
     * @throws IOException Method to differentiate between players for the choice of cards:
     *                     first player must choose three or two cards to use in game and
     *                     then he is the last one to make his personal choice
     */
    public void waitForFirstPlayer() throws IOException {
        String orderPlayer = null;
        do {
            try {
                orderPlayer = (String) input.readObject();
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
            } catch (IOException e) {
                ServerCloseConnection("Server connection is not available, closing game...");
                break;
            }
        } while (!orderPlayer.contains(" " + clientID));
        if (orderPlayer.equals("firstPlayer: " + this.clientID))
            this.order = 0;
        if (orderPlayer.equals("secondPlayer: " + this.clientID))
            this.order = 1;
        if (orderPlayer.equals("thirdPlayer: " + this.clientID))
            this.order = 2;
        if (this.mainFrame != null) {
            if (orderPlayer.equals("firstPlayer: " + this.clientID))
                SwingUtilities.invokeLater(new PickUpCards(mainFrame, playerNumber, this, true));
            else
                SwingUtilities.invokeLater(new PickUpCards(mainFrame, playerNumber, this, false));
        }
    }

    /**
     * Method called at the end of the game
     * to close all the links with Server
     */
    public void close() {
        try {
            if (socket != null) {
                this.socket.close();
                this.input.close();
                this.output.close();
            }
            this.socket = null;
            this.output = null;
            this.input = null;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param o Method to send Object to Server
     */
    public void sendObjectToServer(Object o) {
        try {
            output.reset();
            output.writeObject(o);
            output.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * It show Board GUI when game starts
     */
    public void openBoardGui() {
        mainFrame.getContentPane().removeAll();
        SwingUtilities.invokeLater(new BoardGUI(mainFrame, ConnectionManagerSocket.this));
        mainFrame.update(mainFrame.getGraphics());
    }

    /**
     * @param players It shows in CLI each player with his card
     */
    public void printOpponentInformation(ArrayList players) {
        FileManager f = new FileManager();
        String PATHFILE = "toolcards/";

        System.out.println("Opponents information :");
        players.forEach((player) -> {
            if (((Player) player).getID() == this.getclientID()) {
                System.out.println("ME -> " + ((Player) player).getColor());
                this.setPlayer(((Player) player));
            } else
                System.out.println(((Player) player).getNickname() + " " + ((Player) player).getColor());

            try {
                Document document = f.getFileDocument(PATHFILE.concat(((Player) player).getMyCard().getName()).concat(".xml"));
                NodeList GodDescription = document.getElementsByTagName("PowerDescription");
                System.out.println(((Player) player).getMyCard().getName() + ": " + GodDescription.item(0).getTextContent());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Method called to close GUI when game ends
     */
    public void disposeAll() {
        this.mainFrame.dispose();
    }

    /**
     * @param message close connection message
     * Method called when there are connection problems,
     * if player is using GUI a popUp appears, while he is using CLI
     * this method prints message.
     */
    public void ServerCloseConnection(String message) {
        int TIME_VISIBLE = 5000;
        if (mainFrame != null) {
            JOptionPane pane = new JOptionPane(message,
                    JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = pane.createDialog(mainFrame, "CloseConnection");
            dialog.setModal(false);
            dialog.setVisible(true);
            new Timer(TIME_VISIBLE, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.setVisible(false);
                    mainFrame.dispose();
                    System.exit(0);
                }
            }).start();
        } else {
            System.out.println(message);
            new Timer(TIME_VISIBLE, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            }).start();
        }
    }
}
