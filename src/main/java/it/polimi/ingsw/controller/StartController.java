package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.utils.CustomDate;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StartController {

    private Deck deck;
    private List<String> ChosenCards;
    private int playerNumber;
    private boolean Blue, Grey, White;

    public StartController(){
        this.deck = new Deck();
        this.ChosenCards = new ArrayList<>();
    }

    public void cleanDeck(){
        deck.clearDeck();
    }
    public void cleanChosenCards(){
        ChosenCards.clear();
    }
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void setPlayerBirthDate(ClientConnection c, Player currPlayer, Scanner in){
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
                currPlayer.setBirthDate(birthday);
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
                c.send("This color is already been chosen!");
                return true;
            }
            case "WHITE": if(!isWhite()){
                setWhite(true);
                return false;
            }
            else {
                c.send("This color is already been chosen!");
                return true;
            }
            case "GREY":  if(!isGrey()){
                setGrey(true);
                return false;
            }
            else {
                c.send("This color is already been chosen!");
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

    public void setCardPower(View playerView){
        String cardName;
        Scanner in;
        try {
            in= new Scanner(playerView.getClientConnection().getSocket().getInputStream());
            playerView.getClientConnection().send("Please choose one of these cards:");
            ChosenCards.forEach(s -> playerView.getClientConnection().send(s) );
            do{
                cardName=in.nextLine();
                if(ChosenCards.contains(cardName)){
                    playerView.getPlayer().setMyCard(cardName);
                    ChosenCards.remove(cardName);
                    break;
                }
                else
                    playerView.getClientConnection().send("Wrong Input");
            }while(true);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
    public void chooseCardsFromDeck(View firstPlayer){
        String cardName;
        int x=0;
        Scanner in;
        try {
            in= new Scanner(firstPlayer.getClientConnection().getSocket().getInputStream());
            firstPlayer.getClientConnection().send("Please pick up "+this.playerNumber+" cards from deck:");
            deck.getDeck().forEach((s, b) -> firstPlayer.getClientConnection().send(s));
            do{
                cardName=in.nextLine();
                if(deck.validCard(cardName) && deck.setChosenCard(cardName)) {
                    this.ChosenCards.add(cardName);
                    x++;
                }
                else
                    firstPlayer.getClientConnection().send("Wrong Input");
            }while(x<this.playerNumber);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    public void setFirstPlayerCard(View player1){
        player1.getPlayer().setMyCard(ChosenCards.get(0));
        player1.getClientConnection().send("Your card is"+ChosenCards.get(0));
        ChosenCards.remove(0);
    }

    public Turn setPlayerWorkerInOrder(Model model, View player1, View player2, View player3) {
        int compare;
        compare = player1.getPlayer().getBirthDate().compareDate(player2.getPlayer().getBirthDate());
        if(compare==1){
            //player1 è più giovane
            if(player3!=null){
                compare= player1.getPlayer().getBirthDate().compareDate(player3.getPlayer().getBirthDate());
                if(compare==1){
                    //il piu giovane è player1
                    model.setPlayOrder(player1.getPlayer().getColor(),player2.getPlayer().getColor(), player3.getPlayer().getColor());
                    //p1-p2-p3
                    chooseCardsFromDeck(player1);
                    setCardPower(player2);
                    setCardPower(player3);
                    setFirstPlayerCard(player1);
                    setWorkerPosition(model, player1);
                    setWorkerPosition(model, player2);
                    setWorkerPosition(model, player3);
                    return setTurn(player1.getPlayer(),player2.getPlayer(), player3.getPlayer());
                }
                if(compare==-1 || compare ==0){
                    //il più giovane è player 3
                    model.setPlayOrder(player3.getPlayer().getColor(),player1.getPlayer().getColor(), player2.getPlayer().getColor());
                    chooseCardsFromDeck(player3);
                    setCardPower(player1);
                    setCardPower(player2);
                    setFirstPlayerCard(player3);
                    setWorkerPosition(model, player3);
                    setWorkerPosition(model, player1);
                    setWorkerPosition(model, player2);
                    return setTurn(player3.getPlayer(),player1.getPlayer(), player2.getPlayer());
                }
            }else{
                model.setPlayOrder(player1.getPlayer().getColor(),player2.getPlayer().getColor());
                chooseCardsFromDeck(player1);
                setCardPower(player2);
                setFirstPlayerCard(player1);
                setWorkerPosition(model, player1);
                setWorkerPosition(model, player2);
                return setTurn(player1.getPlayer(),player2.getPlayer());
            }
        }else{
            //player 2 è più giovane
            if(player3!=null){
                compare= player2.getPlayer().getBirthDate().compareDate(player3.getPlayer().getBirthDate());
                if(compare==1){
                    //il piu giovane è player2
                    model.setPlayOrder(player2.getPlayer().getColor(),player3.getPlayer().getColor(), player1.getPlayer().getColor());
                    chooseCardsFromDeck(player2);
                    setCardPower(player3);
                    setCardPower(player1);
                    setFirstPlayerCard(player2);
                    setWorkerPosition(model, player2);
                    setWorkerPosition(model, player3);
                    setWorkerPosition(model, player1);
                    return setTurn(player2.getPlayer(),player3.getPlayer(),player1.getPlayer());
                }
                if(compare==-1 || compare ==0){
                    //il più giovane è player 3
                    model.setPlayOrder(player3.getPlayer().getColor(),player1.getPlayer().getColor(), player2.getPlayer().getColor());
                    chooseCardsFromDeck(player3);
                    setCardPower(player1);
                    setCardPower(player2);
                    setFirstPlayerCard(player3);
                    setWorkerPosition(model, player3);
                    setWorkerPosition(model, player1);
                    setWorkerPosition(model, player2);
                    return setTurn(player3.getPlayer(),player1.getPlayer(),player2.getPlayer());
                }
            }
            else{
                model.setPlayOrder(player2.getPlayer().getColor(),player1.getPlayer().getColor());
                chooseCardsFromDeck(player2);
                setCardPower(player1);
                setFirstPlayerCard(player2);
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
