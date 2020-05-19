package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.utils.OkayMessage;
import it.polimi.ingsw.utils.setupMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String ip;
    private String nickname;
    private int port;
    private boolean active = true;
    private Socket socket;
    private StartGame startGame;
    private PrintWriter socketOut;
    private ObjectOutputStream socketObjectOut;

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
                        if(inputObject instanceof String){
                            if(((String) inputObject).indexOf("The first player is ")>0){
                                if(startGame != null){
                                    startGame.showPopUpNumberPlayer();
                                }else{
                                    System.out.println("Please insert player number: ");
                                }
                            }
                        }

                        if (inputObject instanceof ClientGUIParameters) {
                            //startGame = new StartGame(socket, socketIn, socketOut);

                        }
                        if (inputObject instanceof String) {
                            if(inputObject.equals("SetNumberPlayer")){
                                startGame.showPopUpNumberPlayer();

                            }

                            System.out.println((String) inputObject);

                        }
                        if (inputObject instanceof Board) {
                            ((Board) inputObject).printBoard();

                        }
                        if(inputObject instanceof CheckServerResponse){
                            startGame.serverResponse(((CheckServerResponse) inputObject).isCheckError());

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

    public void setupParameters(){
        Scanner in = new Scanner(System.in);

        System.out.println("Do you want to use CLI or GUI ?");
        String CLI_GUI =in.nextLine();
        switch (CLI_GUI.toUpperCase()){
            case "CLI": setupCLI(in);
                        break;
            default:    setupGUI();
                        break;
        }
    }

    public void setupCLI(Scanner in){
        System.out.println("Please insert your nickname");
        String nickname = in.nextLine();
        System.out.println("Please insert your birthday. ES: 01/10/1997");
        String birthday = in.nextLine();
        try {
            socketObjectOut.reset();
            socketObjectOut.writeObject(new setupMessage(nickname, Integer.parseInt(birthday.substring(0, 2)), Integer.parseInt(birthday.substring(3, 5)), Integer.parseInt(birthday.substring(6, 10))));
            socketObjectOut.flush();
        }catch (IOException e ){
            System.err.println(e.getMessage());
        }
    }
    public void setupGUI(){
        startGame = new StartGame(socketObjectOut);
    }

    public void run() throws IOException {

        socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        socketObjectOut = new ObjectOutputStream(socket.getOutputStream());
        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            setupParameters();
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
