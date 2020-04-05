package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.utils.CustomDate;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StartController {

    private boolean Blue, Grey, White;

    public void setPlayerBirthdate(ClientConnection c,Player currPlayer, Scanner in){
        CustomDate birthday = new CustomDate();
        int d,m,y;
        while(true){
            try {
                c.send("Insert your birth's day");
                d=Integer.parseInt(in.nextLine());
                birthday.setDay(d);
                c.send("Insert your birth's month");
                m=Integer.parseInt(in.nextLine());
                birthday.setMonth(m);
                c.send("Insert your birth's year");
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
    public void setPlayerColor(ClientConnection c,Player currentPlayer, Scanner in){
        String color= null;
        try {
            do{
                c.send("Choose the color :GREY, WHITE and BLUE");
                color = in.nextLine().toUpperCase();
            }while(checkColorUnicity(c,color));
            currentPlayer.setColor(PlayerColor.valueOf(color));
            return;
        }
        catch (IllegalArgumentException e ){
            c.send("This color doesn't exist!");
        }
    }
    public boolean checkColorUnicity(ClientConnection c, String color){
        switch (color){
            case "BLUE":   if(!isBlue()) {
                setBlue(true);
                return false;
            }
            else {
                c.send("This color is been already choosen!");
                return true;
            }
            case "WHITE": if(!isWhite()){
                setWhite(true);
                return false;
            }
            else {
                c.send("This color is been already choosen!");
                return true;
            }
            case "GREY":  if(!isGrey()){
                setGrey(true);
                return false;
            }
            else {
                c.send("This color is been already choosen!");
                return true;
            }
            default:    c.send("This color doesn't exist!");
                return true;
        }
    }

    public synchronized boolean isBlue() {
        return Blue;
    }
    public synchronized boolean isGrey() {
        return Grey;
    }
    public synchronized boolean isWhite() {
        return White;
    }

    public synchronized void setBlue(boolean blue) {
        Blue = blue;
    }
    public synchronized void setGrey(boolean grey) {
        Grey = grey;
    }
    public synchronized void setWhite(boolean white) {
        White = white;
    }

    public Turn setPlayerWorkerInOrder(Model model, View player1, View player2, View player3) {
        int compare;
        //trovare l'ordine di gioco tra i player
        compare = player1.getPlayer().getBirthdate().compareDate(player2.getPlayer().getBirthdate());
        if(compare==1){
            //player1 è più giovane
            if(player3!=null){
                compare= player1.getPlayer().getBirthdate().compareDate(player3.getPlayer().getBirthdate());
                if(compare==1){
                    //il piu giovane è player1
                    model.setPlayOrder(player1.getPlayer().getColor(),player2.getPlayer().getColor(), player3.getPlayer().getColor());
                    setWorkerPosition(model, player1);
                    setWorkerPosition(model, player2);
                    setWorkerPosition(model, player3);
                    return setTurn(player1.getPlayer(),player2.getPlayer(), player3.getPlayer());
                }
                if(compare==-1 || compare ==0){
                    //il più giovane è player 3
                    model.setPlayOrder(player3.getPlayer().getColor(),player1.getPlayer().getColor(), player2.getPlayer().getColor());
                    setWorkerPosition(model, player3);
                    setWorkerPosition(model, player1);
                    setWorkerPosition(model, player2);
                    return setTurn(player3.getPlayer(),player1.getPlayer(), player2.getPlayer());
                }
            }else{
                model.setPlayOrder(player1.getPlayer().getColor(),player2.getPlayer().getColor());
                setWorkerPosition(model, player1);
                setWorkerPosition(model, player2);
                return setTurn(player1.getPlayer(),player2.getPlayer());
            }
        }else{
            //player 2 è più giovane
            if(player3!=null){
                compare= player2.getPlayer().getBirthdate().compareDate(player3.getPlayer().getBirthdate());
                if(compare==1){
                    //il piu giovane è player2
                    model.setPlayOrder(player2.getPlayer().getColor(),player3.getPlayer().getColor(), player1.getPlayer().getColor());
                    setWorkerPosition(model, player2);
                    setWorkerPosition(model, player3);
                    setWorkerPosition(model, player1);
                    return setTurn(player2.getPlayer(),player3.getPlayer(),player1.getPlayer());
                }
                if(compare==-1 || compare ==0){
                    //il più giovane è player 3
                    model.setPlayOrder(player3.getPlayer().getColor(),player1.getPlayer().getColor(), player2.getPlayer().getColor());
                    setWorkerPosition(model, player3);
                    setWorkerPosition(model, player1);
                    setWorkerPosition(model, player2);
                    return setTurn(player3.getPlayer(),player1.getPlayer(),player2.getPlayer());
                }
            }
            else{
                model.setPlayOrder(player2.getPlayer().getColor(),player1.getPlayer().getColor());
                setWorkerPosition(model, player2);
                setWorkerPosition(model, player1);
                return setTurn(player2.getPlayer(),player1.getPlayer());
            }
        }
        return null;
    }

    public void setWorkerPosition(Model model, View player){
        String[] cellCoord;
        int x,y;
        Scanner in;
        try {
            in= new Scanner(player.getClientConnection().getSocket().getInputStream());
            do{
                do{
                    player.getClientConnection().send("Where do you want to put your worker1 (x,y)");
                    cellCoord = in.nextLine().split(",");
                    x=Integer.parseInt(cellCoord[0]);
                    y=Integer.parseInt(cellCoord[1]);
                }while( x<0 || x>=5 || y<0 || y>=5);
            }while(!model.getBoard().getCell(x,y).isFree());
            player.getPlayer().setWorker1(new Worker(model.getBoard().getCell(x,y), 1,player.getPlayer().getColor()));

            cellCoord=null;
            do{
                do{
                    player.getClientConnection().send("Where do you want to put your worker2 (x,y)");
                    cellCoord = in.nextLine().split(",");
                    x=Integer.parseInt(cellCoord[0]);
                    y=Integer.parseInt(cellCoord[1]);
                }while( x<0 || x>=5 || y<0 || y>=5);
            }while(!model.getBoard().getCell(x,y).isFree());
            player.getPlayer().setWorker2(new Worker(model.getBoard().getCell(x,y), 2,player.getPlayer().getColor()));

        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }

    public Turn setTurn(Player p1, Player p2, Player p3){
        return new Turn(p1.setMyTurn(),p2.setMyTurn(),p3.setMyTurn());
    }

    public Turn setTurn(Player p1, Player p2){
        return new Turn(p1.setMyTurn(),p2.setMyTurn());
    }
}
