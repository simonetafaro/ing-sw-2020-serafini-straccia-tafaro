package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.observ.Observable;
import it.polimi.ingsw.utils.CustomDate;
import it.polimi.ingsw.utils.PlayerColor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection  extends Observable<String> implements ClientConnection, Runnable{

    private Socket socket;
    private ObjectOutputStream out;
    private Server server;
    private boolean firstPlayer;
    //questo attributo potrebbe servire quando i workers di questo client non si possono più muovere
    //setto questo attributo a false
    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server, boolean first) {
        this.socket = socket;
        this.server = server;
        this.firstPlayer=first;
    }

    synchronized boolean isActive(){
        return active;
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

    synchronized public Socket getSocket() {
        return socket;
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
            setPlayerBirthdate(player,in);
            setPlayerColor(player,in);

            //TODO: setWorkerPosition in (random, age) order

            /***/do{
                do{
                    send("Where do you want to put your worker1 (x,y)");
                    cellCoord = in.nextLine().split(",");
                    x=Integer.parseInt(cellCoord[0]);
                    y=Integer.parseInt(cellCoord[1]);
                }while( x<0 || x>=5 || y<0 || y>=5);
            }while(!server.getModel().getBoard().getCell(x,y).isFree());
            player.setWorker1(new Worker(server.getModel().getBoard().getCell(x,y), 1,player.getColor()));

            do{
                do{
                    send("Where do you want to put your worker2 (x,y)");
                    cellCoord = in.nextLine().split(",");
                    x=Integer.parseInt(cellCoord[0]);
                    y=Integer.parseInt(cellCoord[1]);
                }while( x<0 || x>=5 || y<0 || y>=5);
            }while(!server.getModel().getBoard().getCell(x,y).isFree());
            player.setWorker2(new Worker(server.getModel().getBoard().getCell(x,y), 2,player.getColor()));

            /***/
            server.lobby(this, player);

            //TODO wait start command from server
            /**while(waitForServerStart){
             *  }
             */
             /*We can use this while with a value setted by Server
             *  In the lobby function we can ask to each client in the order we choose to set their workers' position
             *  When all the setting are done we "unlock" this while so each client can go on and play the game
             * */
            while(isActive()){
                /**Ciclo che processa l'input sul client
                 * */
                move = in.nextLine();
                //TODO manage build input and card
                /**send("Do you want to use your card power?(YES/NO)");
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
                //build= in.nextLine();
                /**Questa notifiy chiama la update di message receiver in RemoteView
                 * Perchè remoteView observes that
                 * */
                notify(move);
            }
        }catch (IOException | NoSuchElementException e) {
            send("error catched");
            System.err.println("Error!" + e.getMessage());
        }finally{
            send("ramo finally della socketClientConnection");
            close();
        }
    }

    public void setPlayerColor(Player currentPlayer,Scanner in){
        String color= null;
            try {
                do{
                    send("Choose the color :GREY, WHITE and BLUE");
                    color = in.nextLine().toUpperCase();
                }while(checkColorUnicity(color));
                currentPlayer.setColor(PlayerColor.valueOf(color));
                return;
            }
            catch (IllegalArgumentException e ){
                send("This color doesn't exist!");
            }
    }

    public boolean checkColorUnicity(String color){
        switch (color){
            case "BLUE":   if(!server.isBlue()) {
                            server.setBlue(true);
                            return false;
                        }
                        else {
                            send("This color is been already choosen!");
                            return true;
                        }
            case "WHITE": if(!server.isWhite()){
                            server.setWhite(true);
                            return false;
                        }
                        else {
                            send("This color is been already choosen!");
                            return true;
                        }
            case "GREY":  if(!server.isGrey()){
                            server.setGrey(true);
                            return false;
                        }
                        else {
                            send("This color is been already choosen!");
                            return true;
                        }
            default:    send("This color doesn't exist!");
                        return true;
        }
    }

    public void setPlayerBirthdate(Player currPlayer,Scanner in){
        CustomDate birthday = new CustomDate();
        int d,m,y;
        while(true){
            try {
                send("Insert your birth's day");
                d=Integer.parseInt(in.nextLine());
                birthday.setDay(d);
                send("Insert your birth's month");
                m=Integer.parseInt(in.nextLine());
                birthday.setMonth(m);
                send("Insert your birth's year");
                y=Integer.parseInt(in.nextLine());
                birthday.setYear(y);
                currPlayer.setBirthdate(birthday);
                break;
            }
            catch (InputMismatchException e){
                System.out.println("Ops, I'would a number please ");
            }
        }
    }
}
