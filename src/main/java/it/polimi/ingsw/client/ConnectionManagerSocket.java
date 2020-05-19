package it.polimi.ingsw.client;

import it.polimi.ingsw.ClientGUIMain;
import it.polimi.ingsw.utils.CustomDate;
import it.polimi.ingsw.utils.PlayerColor;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionManagerSocket {

    private String nickname ;
    private PlayerColor color;
    private transient ExecutorService executor;
    protected boolean nameSet;
    private CustomDate customDate;
    private int playerNumber;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    public String playerColor;

    private Thread t;
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SOCKET_PORT = 12345;

    //private ClientData clientData;

    protected int clientID;

    public ConnectionManagerSocket(String nickname, PlayerColor color, CustomDate birthday, int playerNumber) {
        this.nickname = nickname;
        this.playerNumber = playerNumber;
        this.customDate = birthday;
        this.executor = Executors.newCachedThreadPool();
        this.color = color;
        this.clientID = 0;
        this.playerColor = null;
        //this.clientData = new ClientData();
    }

    public void setclientID(int clientID) {
        this.clientID = clientID;
    }

    public int getclientID() {
        return this.clientID;
    }

    public void setup() {
        try {
            this.initializeSocket();
            this.chooseColor();
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
        socket = new Socket(SERVER_ADDRESS, SOCKET_PORT);
        System.out.println("Connected to server " + SERVER_ADDRESS + " on port " + SOCKET_PORT);

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
        /*
        try {

            System.out.println("waiting");
            String matchCreated = (String) input.readObject();
            if (matchCreated.equals("Match created")){
                System.out.println("Match created.");
            }
            System.out.println("waiting");

        } catch (IOException e) {
            System.err.println("--"+e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("++"+e.getMessage());
        }
        */
        //this.close(socket);
        // delete references
        //socket = null;
        //output = null;
    }

    public void setColor(String color, showPopUpColor guiInstance){

        try{
            this.playerColor = color;
            output.writeObject(color);
            output.flush();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public Thread receiveColorResponse(showPopUpColor guiInstance){
        Thread t = new Thread(new Runnable() {
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
                System.out.println("thread colore clinet morto");
            }
        });
        t.start();
        return t;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    private boolean handleColorResponse(String colorResult, showPopUpColor guiInstance){
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
    private void close(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
