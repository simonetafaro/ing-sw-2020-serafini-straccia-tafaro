package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocketMessage implements Runnable {

    private Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;


    public ClientSocketMessage(int PORT, String IP) throws IOException {
        try {
            this.socket = new Socket(IP, PORT);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            Object o = inputStream.readObject();
            parseInput(o);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void parseInput(Object o){
        if(o instanceof ClientSocketMessage){

        }
    }
}
