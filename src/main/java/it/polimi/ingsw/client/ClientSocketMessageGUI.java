package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.SetWorkerPosition;
import it.polimi.ingsw.utils.gameMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientSocketMessageGUI extends ClientSocketMessage {

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ConnectionManagerSocket connectionManagerSocket;
    private boolean active;

    public ClientSocketMessageGUI(ConnectionManagerSocket connectionManagerSocket, ObjectInputStream input, ObjectOutputStream output) {
        super();
        this.inputStream = input;
        this.outputStream = output;
        this.connectionManagerSocket = connectionManagerSocket;
        this.active = true;
    }
    public void initialize(){
        readFromServer();
    }

    public void readFromServer(){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                while(isActive()) {
                    try {
                        Object o = ClientSocketMessageGUI.this.inputStream.readObject();
                        if(o instanceof SetWorkerPosition){
                            updateBoard((SetWorkerPosition) o);
                        }
                        if(o instanceof String){
                            System.out.println((String) o);
                            if(((String) o).contains(connectionManagerSocket.getPlayerColorEnum().toString())){

                                String message = ((String) o).replace(connectionManagerSocket.getPlayerColorEnum().toString(), "");
                                connectionManagerSocket.getBoardGUI().getPopUpBox().infoBox(message, "received from server");

                                if(((String) o).contains("setWorkers"))
                                    connectionManagerSocket.getBoardGUI().setWorkers();
                                if(((String) o).contains(gameMessage.loseMessage)){
                                    closeOrWatchGame();
                                }
                            }
                        }
                        if (o instanceof GameOverMessage){
                            gameOverStuck(((MoveMessage)o).getPlayer().getColor());
                            break;
                        }

                        if(o instanceof MoveMessage){
                            scanBoard((MoveMessage) o);

                            if(((MoveMessage) o).isHasWon()){
                                gameOver(((MoveMessage)o).getPlayer().getColor());
                                break;
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
                            connectionManagerSocket.getPlayer().getWorker1().setWorkerPosition(board.getCell(x,y));

                        }
                        else{
                            connectionManagerSocket.getBoardGUI().getBoardButton(x,y).add(connectionManagerSocket.getBoardGUI().getWorker2());
                            connectionManagerSocket.getBoardGUI().getWorker2().setVisible(true);
                            connectionManagerSocket.getPlayer().getWorker2().setWorkerPosition(board.getCell(x,y));
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
    public void gameOver (PlayerColor color){
        if(color.equals(connectionManagerSocket.getPlayerColorEnum())){
            connectionManagerSocket.getBoardGUI().getPopUpBox().infoBox("YOU WIN!", "Message From Server - Game Over");
        } else {
            connectionManagerSocket.getBoardGUI().getPopUpBox().infoBox("YOU LOSE!", "Message From Server - Game Over");
        }
        quitGame();

    }
    public void gameOverStuck (PlayerColor color){
       if(color.equals(connectionManagerSocket.getPlayerColorEnum())){
           connectionManagerSocket.getBoardGUI().getPopUpBox().infoBox("YOU LOSE!", "Message From Server - Game Over");
        } else {
            connectionManagerSocket.getBoardGUI().getPopUpBox().infoBox("YOU WIN!", "Message From Server - Game Over");
        }
        quitGame();
    }

    public void quitGame(){
        new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientSocketMessageGUI.this.active = false;
                connectionManagerSocket.close();
                connectionManagerSocket.disposeAll();
            }
        }).start();
    }
    public boolean isActive(){
        return active;
    }

    public void closeOrWatchGame(){
        if(connectionManagerSocket.getBoardGUI().CloseGuiOrNot() == JOptionPane.NO_OPTION){
            quitGame();
        }
    }
}