package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerMove;
import it.polimi.ingsw.utils.SetWorkerPosition;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientSocketMessage{

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ConnectionManagerSocket connectionManagerSocket;


    public ClientSocketMessage(ConnectionManagerSocket connectionManagerSocket, ObjectInputStream input, ObjectOutputStream output) {
        this.inputStream = input;
        this.outputStream = output;
        this.connectionManagerSocket = connectionManagerSocket;
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
                        if(o instanceof SetWorkerPosition){
                            updateBoard((SetWorkerPosition) o);
                        }
                        if(o instanceof String){
                            if(((String) o).contains(connectionManagerSocket.getPlayerColor().toUpperCase())){
                                System.out.println("setto i workers");
                                connectionManagerSocket.getBoardGUI().setWorkers();
                            }
                        }
                        if(o instanceof PlayerMove)
                            parseInput((PlayerMove) o);
                        if(o instanceof ArrayList){
                            connectionManagerSocket.getBoardGUI().populatePlayersInfo((ArrayList) o);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });t.start();
    }

    public void updateBoard(SetWorkerPosition o){
        if(o.getID() == connectionManagerSocket.getclientID()){
            connectionManagerSocket.getBoardGUI().incrementWorkerNum();
            if(connectionManagerSocket.getBoardGUI().getWorkersNum() == 2)
                connectionManagerSocket.getBoardGUI().removeSetWorkersListener();
        }
        connectionManagerSocket.getBoardGUI().addWorkerToBoard(o.getWorkerNum(), o.getColor(), o.getX(), o.getY());
    }
}
