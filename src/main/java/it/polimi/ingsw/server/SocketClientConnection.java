package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerMove;
import it.polimi.ingsw.observ.Observable;
import it.polimi.ingsw.utils.gameMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class SocketClientConnection  extends Observable<String> implements ClientConnection, Runnable{

    private final Socket socket;
    private ObjectOutputStream out;
    private Server server;
    private boolean firstPlayer;
    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server, boolean first) {
        this.socket = socket;
        this.server = server;
        this.firstPlayer=first;
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

    @Override
    public void run() {
        Scanner in;
        Player player = new Player();
        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            if(firstPlayer){
                send("Welcome! Choose the number of players?");
                int playerNum = Integer.parseInt(in.nextLine());
                server.setPlayerNumber(playerNum);
            }
            send("What is your name?");
            String move = in.nextLine();
            player.setNickname(move);
            server.getStartController().setPlayerBirthdate(this,player,in);
            server.getStartController().setPlayerColor(this,player,in);
            send("Wait for cards");

            server.lobby(this, player);

        }catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }/*finally{
            send("ramo finally della socketClientConnection");
            close();
        }*/
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
                        //Input: [M 1-2,2] [B 1-2,3] [M C 1-2,2] [B C 1-2,3] [END]
                        move= in.nextLine();
                        /*Questa notifiy chiama la update di message receiver in RemoteView
                         * Perchè remoteView observes that
                         */
                        notifyObserver(move.toUpperCase());
                        //latchTurn.await();
                    }
                }catch(Exception e){
                    System.out.println("End of getInputFromClient");
                    //setActive(false);
                }
            }
        });
        t.start();
    }
}
