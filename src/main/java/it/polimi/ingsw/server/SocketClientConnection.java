package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerMove;
import it.polimi.ingsw.observ.Observable;
import it.polimi.ingsw.utils.gameMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class SocketClientConnection  extends Observable<String> implements ClientConnection, Runnable{

    private final Socket socket;
    private ObjectOutputStream out;
    private Server server;
    private boolean firstPlayer;
    //questo attributo potrebbe servire quando i workers di questo client non si possono più muovere
    //setto questo attributo a false
    private boolean active = true;
    private Thread t;
    private boolean readCard, askMove, askBuild;
    private static CountDownLatch latchMove, latchTurn;


    public SocketClientConnection(Socket socket, Server server, boolean first) {
        this.socket = socket;
        this.server = server;
        this.firstPlayer=first;
    }

    public CountDownLatch getLatchMove() {
        return latchMove;
    }

    public void setReadCard(boolean readCard) {
        this.readCard = readCard;
    }

    public void setAskMove(boolean askMove) {
        this.askMove = askMove;
    }

    public void setAskBuild(boolean askBuild) {
        this.askBuild = askBuild;
    }

    synchronized boolean isActive(){
        return active;
    }
    synchronized public Socket getSocket() {
        return socket;
    }
    synchronized public String read(){
        Scanner in;
        String input="";
        try {
            in = new Scanner(socket.getInputStream());
            input=in.nextLine();
            return input;
        }catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }
        return input;
    }

    @Override
    public void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }
    void close() {
        closeConnection();
        System.out.println("Deregistering client...");
        //server.deregisterConnection(this);
        System.out.println("Done!");
    }

    synchronized public void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void asyncSend(final Object message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    @Override
    public void run() {
        Scanner in;
        Player player = new Player();
        String[] cellCoord = null;
        int x,y;
        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            if(firstPlayer){
                send("Welcome! Choose the number of players?");
                int playerNum = Integer.parseInt(in.nextLine());
                server.setPlayerNumber(playerNum);
            }
            send("What is your name?");
            String move = in.nextLine();
            player.setNickname(move);
            server.getStartController().setPlayerBirthdate(this,player,in);
            server.getStartController().setPlayerColor(this,player,in);

            server.lobby(this, player);


        }catch (IOException | NoSuchElementException e) {
            send("error catched");
            System.err.println("Error!" + e.getMessage());
        }/**finally{
            send("ramo finally della socketClientConnection");
            close();
        }*/
    }


    public void getInputFromClient(){
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                String move,build,cardString;
                boolean card=false;
                Scanner in;
                try {
                    in = new Scanner(socket.getInputStream());
                    while(isActive()){
                        latchTurn= new CountDownLatch(1);
                        while(latchTurn.getCount()!=0){
                                cardString=readCard(in);
                                notifyObserver(cardString);
                        }
                            //YES/NO
                            if(!card){
                                latchMove= new CountDownLatch(1);
                                //1-1,1
                                askMove(in);
                                latchMove.await();
                                latchMove= new CountDownLatch(1);
                                //System.out.println("post wait pippo");
                                //1-1,2
                                askBuild(in);
                                latchMove.await();
                            }else{

                            }
                            //build=in.nextLine();
                            //notifyObserver(build);
                            //wait();

                            //askBuild(in);
                        //}
                        //se voglio usare una carta chiamo una sequenza custom di askMove e askBuild
                        //build= in.nextLine();
                        /* sulle carte chiamo i metodi sulla carta
                         *  lo switch sovraccarica
                         *  }
                         */
                        /*We can use this while with a value setted by Server
                         *  In the lobby function we can ask to each client in the order we choose to set their workers' position
                         *  When all the setting are done we "unlock" this while so each client can go on and play the game
                         * */
                        /*
                         * if(in.nextLine().toUpperCase().equals("YES"))
                         *  playermove.setUsingCard(true);
                         * else
                         *  playermove.setUsingCard(false);
                         *
                         * For handle build input we can change the PlayerMove class
                         * The input will be like YES 1-2,2-2,3
                         *  The player want to use card's power, move the build
                         *
                         * */
                        /*Questa notifiy chiama la update di message receiver in RemoteView
                         * Perchè remoteView observes that
                         */

                    }
                }catch(Exception e){
                    System.out.println("End of getInputFromClient");
                    //setActive(false);
                }
            }
        });
        t.start();

    }

    /*public void notifyasd(String move){
        notifyObserver(move);
    }*/
    private void askMove(Scanner in){
        String move=in.nextLine();
        notifyObserver(move);
    }
    private void askBuild(Scanner in){
        String build=in.nextLine();
        notifyObserver(build);
    }
    private String readCard(Scanner in){
        String card= in.nextLine().toUpperCase();
        notifyObserver(card);
        return card;
    }
}
