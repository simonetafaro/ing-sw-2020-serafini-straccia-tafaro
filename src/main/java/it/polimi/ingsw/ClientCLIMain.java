package it.polimi.ingsw;

import it.polimi.ingsw.client.ConnectionManagerSocket;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.utils.SetWorkerPosition;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class used to initialize CLI Game. Main for players who want to play with CLI.
 * It interacts with ConnectionMangerSocket to save starting parameters game
 * and it is active until Game ends.
 */
public class ClientCLIMain {

    /**
     * Three Thread to manage color and card choice.
     * mainGame is game thread that ends at the end of the match
     */
    private Thread colorThread, cardThread, mainGame;

    /**
     * worker number
     */
    private int workersNum;

    /**
     * player number in the game
     */
    private int playerNumber;

    /**
     * Scanner used to manages string in input and output
     */
    private Scanner in;

    /**
     * Board game
     */
    private Board board;

    /**
     * class that manages initial connection and parameters
     */
    private ConnectionManagerSocket connectionManagerSocket;

    /**
     * Server IP
     */
    private String IPServer;

    /**
     * @param in Scanner
     * It waits for the IP server choice
     */
    public void chooseServerIP(Scanner in) {
        System.out.print("Insert IP of server (Es: 192.168.2.1): ");
        String serverIP = in.nextLine();
        this.IPServer = serverIP;
    }

    /**
     * @param in Scanner
     * It waits for the nickname and three
     * or two players match choice
     */
    public void setupPlayerData(Scanner in) {
        System.out.print("Insert nickname: ");
        String nickname = in.nextLine();
        System.out.print("Do you want to join 2 or 3 players match? ");
        this.playerNumber = Integer.parseInt(in.nextLine());

        this.connectionManagerSocket = new ConnectionManagerSocket(nickname, playerNumber);
        connectionManagerSocket.setServerData(IPServer);
    }

    /**
     * @throws IOException
     * It calls setup of ConnectionManagerSocket
     */
    public void connectToServer() throws IOException {
        connectionManagerSocket.setup();
    }

    /**
     * @param in Scanner
     * It manages color choice with CLI through ConnectionManagerSocket
     * that sends color to Server and it waits for the Server response.
     */
    public void showPopUpPlayerColor(Scanner in) {
        this.colorThread = connectionManagerSocket.receiveColorResponse(null);
        while (true) {
            connectionManagerSocket.resetPlayerColor();
            String color = setPlayerColor(in);
            connectionManagerSocket.setColor(color, null);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            while (connectionManagerSocket.getPlayerColor().equals("null")) {

            }
            if (connectionManagerSocket.getPlayerColor().equals(color))
                break;
        }
    }

    /**
     * @param in Scanner
     * @return color chosen by player
     */
    public String setPlayerColor(Scanner in) {
        String color = null;
        do {
            System.out.println("Choose the color :GREY, WHITE and BLUE");
            color = in.nextLine().toUpperCase();
        } while (parseInputColor(color));
        return color;
    }

    /**
     * @param color player color
     * @return true if color is BLUE, GREY or WHITE
     */
    public boolean parseInputColor(String color) {
        switch (color) {
            case "BLUE":
            case "GREY":
            case "WHITE":
                return false;
            default:
                return true;
        }

    }

    /**
     * @param in Scanner
     * It manages cards choice with CLI through ConnectionManagerSocket.
     * It saves cardThread from connectionManagerSocket that receive chosen
     * cards from Server. If this is the first player, he must choice three
     * or two cards.
     */
    public void showPopUpPlayerCards(Scanner in) {
        try {
            connectionManagerSocket.waitForFirstPlayer();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        this.cardThread = connectionManagerSocket.receiveCard(null, this);
        if (connectionManagerSocket.getOrder() == 0) {
            //first player, choose #playerNumber cards
            ArrayList<String> ChosenCards = chooseCardsFromDeck(in);
            connectionManagerSocket.sendObjectToServer(ChosenCards);
            //open main page
        } else {
            System.out.println("wait for cards...");
        }
    }

    /**
     * @param cards list of cards
     * It waits for the chosen card from player
     */
    public void chooseCard(ArrayList cards) {
        do {
            String cardName = this.in.nextLine();
            String cardNameFormatted = cardName.substring(0, 1).toUpperCase() + cardName.substring(1).toLowerCase();
            if (cards.contains(cardNameFormatted)) {
                connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " " + cardNameFormatted);
                break;
            } else
                System.out.println("Wrong Input");
        } while (true);
    }

    /**
     * @param in Scanner
     * @return cards chosen from the first player
     * It manages first cards choice in game from the first player
     */
    public ArrayList chooseCardsFromDeck(Scanner in) {
        ArrayList<String> ChosenCards = new ArrayList<>();
        Deck deck = new Deck();
        String cardName;
        int x = 0;

        System.out.println("Please pick up " + this.playerNumber + " cards from deck:");
        deck.getDeck().forEach((s, b) -> System.out.println(s));

        do {
            cardName = in.nextLine();
            if (deck.validCard(cardName) && deck.setChosenCard(cardName)) {
                ChosenCards.add(cardName);
                x++;
            } else
                System.out.println("Wrong Input");
        } while (x < this.playerNumber);

        return ChosenCards;
    }

    /**
     * Method called at the end of starting
     * parameters choice to start game
     */
    public void showMainBoard() {
        this.workersNum = 0;
        this.board = new Board();
        System.out.println("Starting game ...");
        this.mainGame = connectionManagerSocket.initializeMessageSocketCLI(this);
    }

    /**
     * It calles
     */
    public void setWorkers() {
        setWorkerPosition();
    }

    /**
     * It manages initial worker position
     * from CLI of this player
     */
    public void setWorkerPosition() {
        int x, y;
        String[] cellCoord;
        do {
            System.out.println("Where do you want to put your worker" + (workersNum + 1) + " (x,y)");
            cellCoord = in.nextLine().split(",");
            x = Integer.parseInt(cellCoord[0]);
            y = Integer.parseInt(cellCoord[1]);
        } while (x < 0 || x >= 5 || y < 0 || y >= 5);
        connectionManagerSocket.sendObjectToServer(new SetWorkerPosition(x, y, connectionManagerSocket.getPlayerColorEnum(), connectionManagerSocket.getclientID(), this.workersNum + 1));
    }

    /**
     * @param o
     * It sets initial worker positions
     * of other players in board game
     */
    public void addWorkerToBoard(SetWorkerPosition o) {
        this.board.getCell(o.getX(), o.getY()).setCurrWorker(new Worker(o.getID(), this.board.getCell(o.getX(), o.getY()), o.getWorkerNum(), o.getColor()));
    }

    /**
     * It prints board game sent by Server
     */
    public void printBoard() {
        System.out.println('\n');
        this.board.printBoard();
    }

    /**
     * @param board board game
     * It sets board game
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * It increments worker number
     */
    public void incrementWorkerNum() {
        this.workersNum++;
    }

    /**
     * @return worker number
     */
    public int getWorkersNum() {
        return this.workersNum;
    }

    /**
     * ClientCLIMain constructor, it calls all methods
     * to manages starting parameters choice: IP server,
     * Player nickname, three or two players match, player color
     * and cards choice. At the end it waits for the game end.
     */
    public ClientCLIMain() {
        int MAX_TRIES = 5, counter = 0;
        this.in = new Scanner(System.in);
        chooseServerIP(in);
        setupPlayerData(in);
        while (true) {
            try {
                connectToServer();
                showPopUpPlayerColor(in);
                try {
                    this.colorThread.join();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                    break;
                }
                showPopUpPlayerCards(in);
                try {
                    this.cardThread.join();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                    break;
                }
                showMainBoard();
                try {
                    this.mainGame.join();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                    break;
                }
            } catch (IOException serverNoAvailable) {
                if (counter < MAX_TRIES) {
                    System.out.println("Server seems to be offline, trying again to connect");
                    counter++;
                } else {
                    System.out.println("Cannot connect to socket server!");
                    break;
                }
            }
            break;
        }
    }

    /**
     * @param args
     * Main that launches ClientCLIMain
     */
    public static void main(String[] args) {
        new ClientCLIMain();
    }
}