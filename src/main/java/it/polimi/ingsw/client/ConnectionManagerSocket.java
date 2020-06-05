package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PlayerMove;
import it.polimi.ingsw.utils.CustomDate;
import it.polimi.ingsw.utils.PlayerColor;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionManagerSocket {

    private String nickname ;
    private PlayerColor myColor;
    private transient ExecutorService executor;
    protected boolean nameSet;
    private CustomDate customDate;
    private int playerNumber;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String playerColor;
    private JFrame mainFrame;
    private Thread t;
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SOCKET_PORT = 12345;
    private static final int SOCKET_MESSAGE_PORT = 14456;
    private int order;
    private ClientSocketMessage clientSocket;
    //private ClientData clientData;

    protected int clientID;

    public ConnectionManagerSocket(String nickname, CustomDate birthday, int playerNumber) {
        this.nickname = nickname;
        this.playerNumber = playerNumber;
        this.customDate = birthday;
        this.executor = Executors.newCachedThreadPool();
        this.myColor = null;
        this.clientID = 0;
        this.playerColor = null;
        //this.clientData = new ClientData();
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    public void setclientID(int clientID) {
        this.clientID = clientID;
    }

    public int getclientID() {
        return this.clientID;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setup() {
        try {
            this.initializeSocket();
            this.chooseColor();
            /*
            System.out.println("avvio colori ok");
            while(!colorSetted){
            }
            System.out.println("avvio carte");
            this.openPickUpCardPopUp(); */
            /**
             * Creates an always-on thread that works as a subscriber. When the
             * server publishes something, this thread is notified.
             */
            //executor.submit(new ClientSocketViewSUB(SERVER_ADDRESS, SOCKET_PORT, this));
            System.out.println("Subscriber back to main thread");
        } catch (IOException e) {
            System.out.println("Cannot connect to socket server (" + SERVER_ADDRESS
                    + ":" + SOCKET_PORT + ")");
        }

    }

    public void initializeSocket() throws IOException {

        System.out.println("Connected to server " + SERVER_ADDRESS + " on port " + SOCKET_PORT);
        socket = new Socket(SERVER_ADDRESS, SOCKET_PORT);
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
                    System.out.println("Name accepted");
                }

                //BIRTHDAY
                output.writeObject(this.customDate);
                output.flush();
                String serverDateAnswer = (String) input.readObject();
                if (serverDateAnswer.equals("VALID DATE")){
                    System.out.println("Date accepted");
                }

                //clientSocket = new ClientSocketMessage(SOCKET_MESSAGE_PORT,SERVER_ADDRESS);
                //clientSocket.readFromServer();

                //PLAYERNUMBER
                output.writeObject(this.playerNumber);
                output.flush();
                String serverPlayerNumberAnswer = (String) input.readObject();
                if (serverPlayerNumberAnswer.equals("VALID NUMBER")){
                    System.out.println("PLAYER NUMBER accepted");
                }

            } catch (IOException e) {
                System.err.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }
        } while (!nameSet);

        //this.close(socket);
        // delete references
        //socket = null;
        //output = null;
    }
    public void chooseColor() throws IOException {
        String matchCreated =null;
        do{
            try{
                 matchCreated = (String) input.readObject();
            }catch (ClassNotFoundException e){
                System.err.println(e.getMessage());
            }
            System.out.println(matchCreated);
        }while (!matchCreated.equals("Match created"));
    }

    public void setColor(String color, showPopUpColor guiInstance){
        System.out.println("colore"+color);
        try{
            this.playerColor = color;
            switch (playerColor.toUpperCase()){
                case "WHITE": this.myColor = PlayerColor.WHITE;
                case "BLUE": this.myColor = PlayerColor.BLUE;
                case "GREY": this.myColor = PlayerColor.GREY;
            }
            output.writeObject(color);
            output.flush();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
    public Thread receiveColorResponse(showPopUpColor guiInstance){
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean colorCheck = false;
                while(!colorCheck){
                    try {
                        String colorResult = (String) input.readObject();
                        colorCheck = handleColorResponse(colorResult, guiInstance);
                    }catch (ClassNotFoundException | IOException e){
                        System.err.println(e.getMessage());
                    }
                }
            }
        });
        t.start();
        return t;
    }
    public String getPlayerColor() {
        return playerColor;
    }
    private boolean handleColorResponse(String colorResult, showPopUpColor guiInstance) throws IOException {
        if(colorResult.toUpperCase().equals(Integer.toString(getclientID())+" "+getPlayerColor())){
            System.out.println("color ok");
            guiInstance.closeGUI();
            return true;
        }
        if(colorResult.contains("white")){
            guiInstance.lock("white");
        }
        if(colorResult.contains("blue")){
            guiInstance.lock("blue");
        }
        if(colorResult.contains("grey")){
            guiInstance.lock("grey");
        }
        return false;

    }

    public void waitForFirstPlayer() throws IOException{
        String orderPlayer = null;
        do{
            try{
                orderPlayer = (String) input.readObject();
            }catch (ClassNotFoundException e){
                System.err.println(e.getMessage());
            }
        }while (!orderPlayer.contains(" "+clientID));
        if(orderPlayer.equals("firstPlayer: "+ this.clientID))
            this.order = 0;
        if(orderPlayer.equals("secondPlayer: "+ this.clientID))
            this.order = 1;
        if(orderPlayer.equals("thirdPlayer: "+ this.clientID))
            this.order = 2;

        if(orderPlayer.equals("firstPlayer: "+ this.clientID))
            SwingUtilities.invokeLater(new PickUpCards(mainFrame,playerNumber, this, true));
        else
            SwingUtilities.invokeLater(new PickUpCards(mainFrame,playerNumber, this, false));
    }

    public Thread receiveCard(PickUpCards guiInstance){
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                int i= 1;
                Object obj;
                while(i<playerNumber){
                    try {
                        obj =input.readObject();
                        if (obj instanceof ArrayList){
                            ArrayList cards = null;
                            cards = (ArrayList) obj;
                            if(cards.size() == playerNumber && ConnectionManagerSocket.this.order == 1){
                                System.out.println("1");
                                guiInstance.updateGodImage(cards);
                                break;
                            }
                            if((cards.size() == (playerNumber-1)) && ConnectionManagerSocket.this.order == 2){
                                System.out.println("2");
                                guiInstance.updateGodImage(cards);
                                break;
                            }
                            if(ConnectionManagerSocket.this.order == 0)
                                break;
                            i++;
                        }

                    }catch (ClassNotFoundException | IOException e){
                        System.err.println(e.getMessage());
                    }
                }
            }
        });
        t.start();

        return t;
    }

    public void close() {
        try {
            this.socket.close();
            this.input.close();
            this.output.close();
            this.socket = null;
            this.output = null;
            this.input = null;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendObjectToServer(Object o){
        try{
            output.reset();
            output.writeObject(o);
            output.flush();
        }catch (IOException e ){
            System.err.println(e.getMessage());
        }
    }

    public void initializeMessageSocket(){
        this.clientSocket = new ClientSocketMessage(input, output);
        this.clientSocket.initialize();
    }
    public void openBoardGui(){
        mainFrame.getContentPane().removeAll();
        SwingUtilities.invokeLater(new BoardGUI(mainFrame,ConnectionManagerSocket.this));
        mainFrame.update(mainFrame.getGraphics());
    }

    public void sendToServer(PlayerMove playerMove){
        this.clientSocket.send(playerMove);
    }

    public void sendStringToServer(String playerMove){
        this.clientSocket.sendString(playerMove);
    }

}
