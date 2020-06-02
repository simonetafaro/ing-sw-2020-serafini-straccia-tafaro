package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PlayerMove;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocketMessage{

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;


    public ClientSocketMessage(ObjectInputStream input, ObjectOutputStream output) {
        this.inputStream = input;
        this.outputStream = output;
    }

    public void initialize(){
        readFromServer();
    }

    public void parseInput(Object o){
        if(o instanceof ClientSocketMessage){

        }
    }
    public void send (PlayerMove playerMove){
        try {
            outputStream.reset();
            outputStream.writeObject(playerMove);
            outputStream.flush();
        }catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
    public void sendString (String playerMove){
        try {
            outputStream.reset();
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
                        Object o = ClientSocketMessage.this.inputStream.readObject();
                        if(o instanceof PlayerMove)
                            parseInput((PlayerMove) o);
                        else
                            System.out.println("non sono una playerMove");
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
