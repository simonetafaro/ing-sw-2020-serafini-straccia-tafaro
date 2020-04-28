package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observ.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection  extends Observable<String> implements ClientConnection, Runnable{

    private final Socket socket;
    private ObjectOutputStream out;
    private Server server;
    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
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

            send("What is your name?");
            String move = in.nextLine();
            player.setNickname(move);
            server.getStartController().setPlayerBirthDate(this,player,in);
            server.getStartController().setPlayerColor(this,player,in);

            server.lobby(this, player);

        }catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }
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
}
