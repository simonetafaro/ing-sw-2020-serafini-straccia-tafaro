package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PlayerMove;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocketMessage implements Runnable {

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;


    public ClientSocketMessage(int PORT, String IP) throws IOException {
        try {
            System.out.println("prima della socket ");
            this.socket = new Socket(IP, PORT);
            System.out.println("dopo della socket ");
           // inputStream = new ObjectInputStream(socket.getInputStream());
           // outputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        System.out.println("clietsocketmessage");
        while(true) {
            try {
                Object o = inputStream.readObject();
                parseInput((PlayerMove) o);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void initialize(){
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        }catch(IOException e) {
        System.err.println(e.getMessage());
        }
        readFromServer();
    }
    public void parseInput(Object o){
        if(o instanceof ClientSocketMessage){

        }
    }
    public void send (PlayerMove playerMove){
        try {
            outputStream.writeObject(playerMove);
            outputStream.flush();
        }catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
    public void readFromServer(){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("clietsocketmessage");
                while(true) {
                    try {
                        Object o = inputStream.readObject();
                        parseInput((PlayerMove) o);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });t.start();
    }
}
