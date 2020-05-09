package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.utils.OkayMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String ip;
    private int port;
    private boolean active = true;
    private Socket socket;
    private StartGame startGame;
    private PrintWriter socketOut;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        if (inputObject instanceof ClientGUIParameters) {
                            startGame = new StartGame(socket, socketIn);
                        }
                        if (inputObject instanceof String) {
                            if(inputObject.equals("SetNumberPlayer")){
                                new PopUpNumberPlayer(socketOut);
                            }
                            System.out.println((String) inputObject);
                        }
                        if (inputObject instanceof Board) {
                            ((Board) inputObject).printBoard();
                        }
                        if(inputObject instanceof CheckServerResponse){
                            startGame.serverResponse(((CheckServerResponse) inputObject).isCheckError());
                        }
                        if(inputObject instanceof PopUpNumberPlayer){
                            startGame.showPopUpNumberPlayer();
                        }
                    }
                }catch (Exception e ){
                    //System.out.println("fine della read");
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        //System.out.println("Write to socket");
                        String inputLine = stdin.nextLine();
                        socketOut.println(inputLine);
                        socketOut.flush();
                    }
                }catch(Exception e){
                    //System.out.println("fine della write");
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }


    public void run() throws IOException {

        socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }
}
