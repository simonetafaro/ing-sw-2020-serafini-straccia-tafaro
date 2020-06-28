package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.SetWorkerPosition;
import it.polimi.ingsw.utils.gameMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Cass created to manage ClI Game: interacts with {@link it.polimi.ingsw.ClientCLIMain}
 * It inherits from abstract {@link ClientSocketMessage} class
 */
public class ClientSocketMessageCLI extends ClientSocketMessage {

    /**
     * inputStream to read from Server
     */
    private ObjectInputStream inputStream;

    /**
     * outputStream to write to Server
     */
    private ObjectOutputStream outputStream;

    /**
     * class that manages initial connection and parameters
     */
    private ConnectionManagerSocket connectionManagerSocket;

    /**
     * Thread used to write to Server
     */
    private Thread writeToserver;

    /**
     * Scanner used to manages string in input and output
     */
    private Scanner in;

    /**
     * active is true if client is active in reading,
     * activeWrite is true if client is active in writing
     */
    private boolean active,activeWrite;

    /**
     * Constructor that inherits from super class
     * @param connectionManagerSocket
     * @param input
     * @param output
     */
    public ClientSocketMessageCLI(ConnectionManagerSocket connectionManagerSocket, ObjectInputStream input, ObjectOutputStream output) {
        super();
        this.inputStream = input;
        this.outputStream = output;
        this.connectionManagerSocket = connectionManagerSocket;
        this.active = true;
        this.activeWrite = true;
    }

    /**
     * @return reading Thread
     * It initializes scanner in
     */
    public Thread initializeCLI(){
        in = new Scanner(System.in);
        return readFromServerCLI();
    }

    /**
     * @return Thread in reading
     * Thread that parses input from Server.
     * Server sends a MoveMessage in response of a PlayerMove during game,
     * MoveMessage contains board that is printed and if a player wins or loses
     * quitGame method is called and this Thread ends.
     */
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
                            if(((String) o).contains("quitClient")){
                                System.out.println("One of your opponents disconnected, closing game...");
                                quitGame();
                                break;
                            }
                            if(((String) o).contains(connectionManagerSocket.getPlayerColor().toUpperCase())){
                                if(((String) o).contains(" workerOccupiedCell") || ((String) o).contains("setWorkers")){
                                    connectionManagerSocket.getBoardCLI().setWorkers();
                                }

                                String message = ((String) o).replace(connectionManagerSocket.getPlayerColorEnum().toString(), "");
                                System.out.println(message);

                                if(((String) o).contains(gameMessage.loseMessage)){
                                    activeWrite = false;
                                    closeOrWatchGame();
                                }
                            }
                        }
                        if (o instanceof GameOverMessage){
                            gameOverStuck(((MoveMessage)o).getPlayer().getColor());
                            break;
                        }
                        if(o instanceof MoveMessage){
                            if(((MoveMessage) o).isHasWon()){
                                gameOver(((MoveMessage)o).getPlayer().getColor());
                                break;
                            }
                            else{
                                connectionManagerSocket.getBoardCLI().setBoard(((MoveMessage)o).getBoard());
                                connectionManagerSocket.getBoardCLI().printBoard();
                            }
                        }

                        if(o instanceof ArrayList){
                            if( ((ArrayList) o).get(0) instanceof Player){
                                connectionManagerSocket.getBoardCLI().printBoard();
                                connectionManagerSocket.printOpponentInformation((ArrayList) o);
                                ClientSocketMessageCLI.this.writeToServer();
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e ){
                        System.out.println("Server connection is not available, closing game...");
                        quitGame();
                        break;
                    }
                }
            }
        });
        t.start();
        return t;
    }

    /**
     * @param o
     * Method used to setting initial worker positions
     */
    public void updateBoard(SetWorkerPosition o){
        connectionManagerSocket.getBoardCLI().addWorkerToBoard((SetWorkerPosition) o);
        if(o.getID() == connectionManagerSocket.getclientID()){
            connectionManagerSocket.getBoardCLI().incrementWorkerNum();
            if(connectionManagerSocket.getBoardCLI().getWorkersNum() < 2)
                connectionManagerSocket.getBoardCLI().setWorkers();
        }
    }

    /**
     * Thread used in writing. It is always listening on this client
     * and, after checking that the client
     * has entered the right input, sends a PlayerMove or
     * a PlayerMoveEnd to Server.
     */
    public void writeToServer(){
        writeToserver = new Thread(new Runnable() {
            @Override
            public void run() {
                //Scanner in = new Scanner(System.in);
                while (isActiveWrite()) {
                    String inputLine = in.nextLine().toUpperCase();
                    try {
                        if(checkInputStandard(inputLine)){
                            outputStream.writeObject(handleMove(inputLine.substring(0,1),
                                    Integer.parseInt(inputLine.substring(2, 3)),
                                    Integer.parseInt(inputLine.substring(4, 5)),
                                    Integer.parseInt(inputLine.substring(6, 7))));
                            outputStream.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        writeToserver.start();
    }

    /**
     * Input check
     * @param message input move string
     * @return true if client has entered a standard input
     * @throws IOException
     *
     * If client ended his turn a PlayerMoveEnd is sent to Server
     */
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

    /**
     * @param message input move string
     * @return if client has entered a standard input
     */
    private boolean standardInput(String message){
        //M 1-1,2
        return message.length()==7 && (message.charAt(0)=='M' || message.charAt(0)=='B' || message.charAt(0)=='D') && (message.charAt(2)=='1' ||
                message.charAt(2)=='2') && Integer.parseInt(message.substring(4,5))>=0 && Integer.parseInt(message.substring(4,5))<=4 &&
                Integer.parseInt(message.substring(6,7))>=0 && Integer.parseInt(message.substring(6,7))<=4;
    }

    /**
     * @param MoveOrBuild
     * @param worker
     * @param row
     * @param column
     * @return a PlayerMove
     * it creates a PlayerMove from string move in input
     */
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

    /**
     * @param color
     * Method called when game is over, It checks
     *      * if this client player has won or lost
     */
    public void gameOver (PlayerColor color){
        if(color.equals(connectionManagerSocket.getPlayerColorEnum())){
            System.out.println("YOU WIN!");
        } else {
            System.out.println("YOU LOSE!");
        }
        quitGame();
    }

    /**
     * @param color
     * Method called when game is over for player stuck, It checks
     * if player stuck is this client player or not
     */
    public void gameOverStuck (PlayerColor color){
        if(color.equals(connectionManagerSocket.getPlayerColorEnum())){
            System.out.println("YOU LOSE!");
        } else {
            System.out.println("YOU WIN!");
        }
        quitGame();
    }

    /**
     * Method called at the end of a game to close connection
     * and to close Threads
     */
    public void quitGame(){
        new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientSocketMessageCLI.this.active = false;
                connectionManagerSocket.close();
                System.exit(0);
            }
        }).start();
    }

    /**
     * @return true if reading Thread is still active
     */
    public boolean isActive(){
        return active;
    }

    /**
     * @return true if writing Thread is still active
     */
    public boolean isActiveWrite() {
        return activeWrite;
    }

    /**
     * It is called when this player has lost and it
     * gives the option to stay to watch the game or close the connection
     */
    public void closeOrWatchGame(){
        try {
            writeToserver.join();
        } catch (InterruptedException e ){
            System.err.println(e.getMessage());
        }
        System.out.println("Press any button to continue to watch the game or write NO if you want to close.");
        String closeOrNot = in.nextLine();
        if(closeOrNot.toUpperCase().equals("NO")){
            quitGame();
        }
    }
}