package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PlayerMove;
import it.polimi.ingsw.utils.SetWorkerPosition;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSocketMessageCLI extends ClientSocketMessage{
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ConnectionManagerSocket connectionManagerSocket;

    public ClientSocketMessageCLI(ConnectionManagerSocket connectionManagerSocket, ObjectInputStream input, ObjectOutputStream output) {
        super();
        this.inputStream = input;
        this.outputStream = output;
        this.connectionManagerSocket = connectionManagerSocket;
    }

    public Thread initializeCLI(){
        return readFromServerCLI();
    }

    public void parseInput(Object o){
        if(o instanceof ClientSocketMessageCLI){

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

    public Thread readFromServerCLI(){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Object o = ClientSocketMessageCLI.this.inputStream.readObject();
                        if(o instanceof SetWorkerPosition){
                            updateBoard((SetWorkerPosition) o);
                        }
                        if(o instanceof String){
                            if(((String) o).contains(connectionManagerSocket.getPlayerColor().toUpperCase())){
                                if(((String) o).contains(" workerOccupiedCell") || ((String) o).contains("setWorkers")){
                                    connectionManagerSocket.getBoardCLI().setWorkers();
                                }
                            }
                        }
                        if(o instanceof PlayerMove)
                            parseInput((PlayerMove) o);

                        if(o instanceof ArrayList){
                            //problema, nella partita a 3 il primo giocatore entra qui e stampa una board perchè riceve una lista
                            // che è la lista delle carte scelte
                            //risolvere
                            connectionManagerSocket.getBoardCLI().printBoard();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        return t;
    }

    public void updateBoard(SetWorkerPosition o){
        //connectionManagerSocket.getBoardCLI().addWorkerToBoard(o.getWorkerNum(), o.getColor(), o.getX(), o.getY());
        connectionManagerSocket.getBoardCLI().addWorkerToBoard((SetWorkerPosition) o);
        if(o.getID() == connectionManagerSocket.getclientID()){
            connectionManagerSocket.getBoardCLI().incrementWorkerNum();
            if(connectionManagerSocket.getBoardCLI().getWorkersNum() < 2)
                connectionManagerSocket.getBoardCLI().setWorkers();
        }

    }
}
