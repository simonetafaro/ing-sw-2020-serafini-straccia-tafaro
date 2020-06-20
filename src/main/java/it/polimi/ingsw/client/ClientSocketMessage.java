package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.SetWorkerPosition;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientSocketMessage{

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
                while(true) {
                    try {
                        Object o = ClientSocketMessage.this.inputStream.readObject();
                        if(o instanceof SetWorkerPosition){
                            updateBoard((SetWorkerPosition) o);
                        }
                        if(o instanceof String){
                            System.out.println((String) o);
                            if(((String) o).contains(connectionManagerSocket.getPlayerColorEnum().toString())){
                                if(((String) o).contains("setWorkers"))
                                    connectionManagerSocket.getBoardGUI().setWorkers();

                                String message = ((String) o).replace(connectionManagerSocket.getPlayerColorEnum().toString(), "");
                                connectionManagerSocket.getBoardGUI().getPopUpBox().infoBox(message, "received from server");
                            }
                        }
                        if(o instanceof MoveMessage){
                            if(((MoveMessage) o).getMove() instanceof PlayerMoveEnd) {
                            }
                            else{
                                scanBoard((MoveMessage) o);
                            }
                        }
                        if(o instanceof ArrayList){
                            if (((ArrayList) o).get(0) instanceof Player)
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
    public void scanBoard(MoveMessage message){
        Board board = message.getBoard();
        for(int x = 0; x<5; x++){
            for(int y=0; y<5; y++){
                for (Component component : connectionManagerSocket.getBoardGUI().getBoardButton(x, y).getComponents()) {
                    component.setVisible(false);
                }
                connectionManagerSocket.getBoardGUI().getBoardButton(x,y).removeAll();
                int level = board.getCell(x,y).getLevel();
                for(int j=0; j<level; j++){
                    connectionManagerSocket.getBoardGUI().addLevelToBoard(j+1,connectionManagerSocket.getBoardGUI().getBoardButton(x,y));
                }
                if(board.getCell(x,y).getCurrWorker()!=null) {
                    if (board.getCell(x, y).getCurrWorker().getPlayerColor().equals(connectionManagerSocket.getPlayerColorEnum())) {
                        //updateDataPlayer(message, x, y);

                        if(board.getCell(x,y).getCurrWorker().getWorkerNum() == 1){
                            connectionManagerSocket.getBoardGUI().getBoardButton(x,y).add(connectionManagerSocket.getBoardGUI().getWorker1());
                            connectionManagerSocket.getBoardGUI().getWorker1().setVisible(true);
                            Cell newPosition = new Cell(x,y);
                            connectionManagerSocket.getPlayer().getWorker1().setWorkerPosition(newPosition);

                        }
                        else{
                            connectionManagerSocket.getBoardGUI().getBoardButton(x,y).add(connectionManagerSocket.getBoardGUI().getWorker2());
                            connectionManagerSocket.getBoardGUI().getWorker2().setVisible(true);
                            Cell newPosition = new Cell(x,y);
                            connectionManagerSocket.getPlayer().getWorker2().setWorkerPosition(newPosition);
                        }
                    }
                    else
                        connectionManagerSocket.getBoardGUI().addWorker(board.getCell(x,y).getCurrWorker().getPlayerColor(), board.getCell(x,y).getCurrWorker().getWorkerNum(), x, y);

                }
                connectionManagerSocket.getBoardGUI().getBoardButton(x,y).repaint();
            }
        }
    }

    public void updateBoard(SetWorkerPosition o){
        if(o.getID() == connectionManagerSocket.getclientID()){
            connectionManagerSocket.getBoardGUI().incrementWorkerNum();
            if(connectionManagerSocket.getBoardGUI().getWorkersNum() == 2)
                connectionManagerSocket.getBoardGUI().removeSetWorkersListener();
            connectionManagerSocket.getBoardGUI().addMyWorkerToBoard(o.getWorkerNum(), o.getColor(), o.getX(), o.getY());
        }
        else {
            connectionManagerSocket.getBoardGUI().addWorker(o.getColor(), o.getWorkerNum(), o.getX(), o.getY());
        }
    }
}
