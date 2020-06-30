package it.polimi.ingsw;

import it.polimi.ingsw.client.ConnectionManagerSocket;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.utils.SetWorkerPosition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientCLIMain {

    private static final String SRC = "src";
    private static final String MAIN = "main";
    private static final String RESOURCES = "resources";
    private static final String IMAGE = "images";
    private int clientID = 0;
    private Thread colorThread, cardThread, mainGame;
    private int workersNum;
    private int playerNumber;
    private Scanner in;
    private Board board;
    private ConnectionManagerSocket connectionManagerSocket;
    private String IPServer;

    private static final String PATH = SRC + File.separatorChar + MAIN + File.separatorChar + RESOURCES + File.separatorChar + IMAGE + File.separatorChar;

    public void chooseServerIP(Scanner in) {
        System.out.print("Insert IP of server (Es: 192.168.2.1): ");
        String serverIP = in.nextLine();
        this.IPServer = serverIP;
    }

    public void setupPlayerData(Scanner in) {
        System.out.print("Insert nickname: ");
        String nickname = in.nextLine();
        System.out.print("Do you want to join 2 or 3 players match? ");
        this.playerNumber = Integer.parseInt(in.nextLine());

        this.connectionManagerSocket = new ConnectionManagerSocket(nickname, playerNumber);
        connectionManagerSocket.setServerData(IPServer);
    }

    public void connectToServer() throws IOException {
        connectionManagerSocket.setup();
    }

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

    public String setPlayerColor(Scanner in) {
        String color = null;
        do {
            System.out.println("Choose the color :GREY, WHITE and BLUE");
            color = in.nextLine().toUpperCase();
        } while (parseInputColor(color));
        return color;
    }

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

    public void showMainBoard() {
        this.workersNum = 0;
        this.board = new Board();
        System.out.println("Starting game ...");
        this.mainGame = connectionManagerSocket.initializeMessageSocketCLI(this);
    }

    public void setWorkers() {
        setWorkerPosition();
    }

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

    public void addWorkerToBoard(SetWorkerPosition o) {
        this.board.getCell(o.getX(), o.getY()).setCurrWorker(new Worker(o.getID(), this.board.getCell(o.getX(), o.getY()), o.getWorkerNum(), o.getColor()));
    }

    public void printBoard() {
        System.out.println('\n');
        this.board.printBoard();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void incrementWorkerNum() {
        this.workersNum++;
    }

    public int getWorkersNum() {
        return this.workersNum;
    }

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

    public static void main(String[] args) {
        new ClientCLIMain();
    }
}