package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.SetWorkerPosition;
import it.polimi.ingsw.utils.gameMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientSocketMessageCLI extends ClientSocketMessage {
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ConnectionManagerSocket connectionManagerSocket;
    private boolean active;

    public ClientSocketMessageCLI(ConnectionManagerSocket connectionManagerSocket, ObjectInputStream input, ObjectOutputStream output) {
        super();
        this.inputStream = input;
        this.outputStream = output;
        this.connectionManagerSocket = connectionManagerSocket;
        this.active = true;
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
                while(isActive()) {
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
                                String message = ((String) o).replace(connectionManagerSocket.getPlayerColorEnum().toString(), "");
                                System.out.println(message);
                            }
                        }
                        if(o instanceof MoveMessage){
                            if(((MoveMessage) o).isHasWon())
                                gameOver(((MoveMessage)o).getPlayer().getColor());
                            else{
                                connectionManagerSocket.getBoardCLI().setBoard(((MoveMessage)o).getBoard());
                                connectionManagerSocket.getBoardCLI().printBoard();
                            }
                        }
                        if(o instanceof ArrayList){
                            //problema, nella partita a 3 il primo giocatore entra qui e stampa una board perchè riceve una lista
                            // che è la lista delle carte scelte
                            //risolvere
                            if( ((ArrayList) o).get(0) instanceof Player){
                                connectionManagerSocket.getBoardCLI().printBoard();
                                connectionManagerSocket.printOpponentInformation((ArrayList) o);
                                ClientSocketMessageCLI.this.writeToServer();
                            }
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

    public void writeToServer(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner in = new Scanner(System.in);
                try {
                    while (isActive()) {
                        String inputLine = in.nextLine().toUpperCase();
                        if(checkInputStandard(inputLine)){
                            outputStream.writeObject(handleMove(inputLine.substring(0,1),
                                    Integer.parseInt(inputLine.substring(2, 3)),
                                    Integer.parseInt(inputLine.substring(4, 5)),
                                    Integer.parseInt(inputLine.substring(6, 7))));
                            outputStream.flush();
                        }
                    }
                }catch(Exception e){
                    //System.out.println("fine della write");
                }
            }
        });
        t.start();
    }

    public boolean checkInputStandard(String message) throws IOException{
        if(message.equals("END")){
            outputStream.writeObject(new PlayerMoveEnd(connectionManagerSocket.getPlayer(), true));
            outputStream.flush();
            return false ;
        }
        if(standardInput(message))
            return true;
        else
            System.out.println(gameMessage.wrongInputMessage+ gameMessage.insertAgain);

        return false;
    }

    private boolean standardInput(String message){
        //M 1-1,2
        return message.length()==7 && (message.charAt(0)=='M' || message.charAt(0)=='B' || message.charAt(0)=='D') && (message.charAt(2)=='1' ||
                message.charAt(2)=='2') && Integer.parseInt(message.substring(4,5))>=0 && Integer.parseInt(message.substring(4,5))<=4 &&
                Integer.parseInt(message.substring(6,7))>=0 && Integer.parseInt(message.substring(6,7))<=4;
    }

    public PlayerMove handleMove(String MoveOrBuild, int worker, int row, int column) {
        if(worker == 1) {
            PlayerMove move = new PlayerMove(connectionManagerSocket.getPlayer(), connectionManagerSocket.getPlayer().getWorker1(), row, column);
            move.setMoveOrBuild(MoveOrBuild);
            return move;
        }else{
            PlayerMove move = new PlayerMove(connectionManagerSocket.getPlayer(), connectionManagerSocket.getPlayer().getWorker2(), row, column);
            move.setMoveOrBuild(MoveOrBuild);
            return move;
        }

    }

    public void gameOver (PlayerColor color){
        if(color.equals(connectionManagerSocket.getPlayerColorEnum())){
            System.out.println("YOU WIN!");
        } else {
            System.out.println("YOU LOSE!");
        }
        this.active = false;
        connectionManagerSocket.close();
    }

    public boolean isActive(){
        return active;
    }
}