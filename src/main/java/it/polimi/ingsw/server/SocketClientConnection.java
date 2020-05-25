package it.polimi.ingsw.server;

import it.polimi.ingsw.client.CheckServerResponse;
import it.polimi.ingsw.client.ClientGUIParameters;
import it.polimi.ingsw.client.StartGame;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observ.Observable;
import it.polimi.ingsw.utils.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection  extends Observable<String> implements ClientConnection, Runnable{

    private final Socket socket = null;
    private ObjectOutputStream out;
    private Server server;
    private boolean active = true;
    private String nickName;
    private boolean firstPlayer;

    public void setFirstPlayer(boolean firstPlayer){
        this.firstPlayer = firstPlayer;
    }
    synchronized boolean isActive(){
        return active;
    }
    synchronized public Socket getSocket() {
        return socket;
    }
    synchronized public String read(){
        Scanner in;
        String input="";
        try {
            in = new Scanner(socket.getInputStream());
            input=in.nextLine();
            return input;
        }catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }
        return input;
    }


    @Override
    public void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }
    void close() {
        closeConnection();
        System.out.println("Deregistering client...");
        //server.deregisterConnection(this);
        System.out.println("Done!");
    }

    synchronized public void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void asyncSend(final Object message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }


    public void getInputFromClient(){
       Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String move;
                Scanner in;
                try {
                    in = new Scanner(socket.getInputStream());
                    while(isActive()){
                        //Input: [M 1-2,2] [B 1-2,3] [END]
                        move= in.nextLine();
                        notifyObserver(move.toUpperCase());
                    }
                }catch(Exception e){
                    System.out.println("End of getInputFromClient");
                    //setActive(false);
                }
            }
        });
        t.start();
    }

    public void setupGUIPlayer(){
        ObjectInputStream socketIn;
        boolean finish= true;
        try{
            socketIn = new ObjectInputStream(socket.getInputStream());

            do{
                Object obj = socketIn.readObject();
                if(!server.getStartController().checkColorUnicity(this,((Player) obj).getColor().toString()))
                {
                    //send("okay");
                    send(new CheckServerResponse(true));
                    finish = false;
                    if(obj instanceof Player){
                        System.out.println("Nome "+ ((Player) obj).getNickname()+ "Colore: "+((Player) obj).getColor().toString());
                        server.lobby(this, (Player) obj);
                    }
                }else{
                    //send("error");
                    send(new CheckServerResponse(false));
                }
            }while(finish);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        //socket.nextObject();
    }

    public void setupPlayer(){
        ObjectInputStream socketIn;
        Scanner scanner;
        boolean finish= true;
        try{
            socketIn = new ObjectInputStream(socket.getInputStream());
            scanner = new Scanner(socket.getInputStream());
            Object obj = socketIn.readObject();

            if(obj instanceof setupMessage){
                //check nickname
                if(server.checkNicknameUnicity(((setupMessage) obj).getNickname())){
                    this.nickName= ((setupMessage) obj).getNickname();
                    //messaggio in broadcast
                    //obj.getNickname si è connesso + Lista giocatori
                    server.matchManager(this, ((setupMessage) obj).getNickname());
                }
            }

            if(this.firstPlayer){
                int playerNumber = Integer.parseInt(scanner.nextLine());
                server.setPlayerNumber(playerNumber, this);
            }

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        //socket.nextObject();
    }

    public String getNickName() {
        return nickName;
    }


    /**new implementation*/
    private ServerSocket serverSocket;
    private Server activeServer;
    public SocketClientConnection(int port, Server server) {
        this.activeServer = server;
        try {
            serverSocket = new ServerSocket(port);
        }catch (IOException e ){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Waiting a connection...");
                Socket client = serverSocket.accept();
                System.out.println("Connection accepted");
                ObjectInputStream input = new ObjectInputStream(
                        client.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(
                        client.getOutputStream());
                /* Inizializzazione del client
                 * -ID
                 * -Nome
                 * -Data
                 * -Colore
                 * */
                Integer clientId = (Integer) input.readObject();
                if(clientId == 0){
                    clientId = Server.getClientId();
                    output.writeObject(clientId);
                    output.flush();
                    activeServer.getActiveClientConnection().put(clientId, client);
                    Server.increaseClientId();

                    // read player name and confirm
                    String playerName = (String) input.readObject();
                    activeServer.getPlayerData().put(clientId, playerName);
                    System.out.println("Name accepted: " + playerName);
                    output.writeObject("NAME ACCEPTED");
                    output.flush();

                    // read player birthday and confirm
                    CustomDate birthday = (CustomDate) input.readObject();
                    System.out.println("date accepted!");
                    output.writeObject("VALID DATE");
                    output.flush();
                    Player player = new Player(clientId, nickName, birthday);
                    int playerNumber = (int) input.readObject();
                    switch (playerNumber){
                        case 2: activeServer.getTwoPlayerMatch().add(player);
                                output.writeObject(new String("VALID NUMBER"));
                                output.flush();

                                if(activeServer.getTwoPlayerMatch().size() == 2){
                                    //create two players match
                                    activeServer.createTwoPlayersMatch(activeServer.getTwoPlayerMatch().get(0),activeServer.getTwoPlayerMatch().get(1));
                                    activeServer.getTwoPlayerMatch().clear();
                                }

                        case 3: activeServer.getThreePlayerMatch().add(player);
                                output.writeObject(new String("VALID NUMBER:" +playerNumber));
                                output.flush();
                                if(activeServer.getThreePlayerMatch().size() == 3){
                                    //create three players match
                                    //activeServer.createThreePlayersMatch();
                                }
                    }
                    activeServer.getClientConnectionOutput().put(clientId, output);
                    activeServer.getClientConnectionInput().put(clientId, input);
                } else{

                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
    }


}
