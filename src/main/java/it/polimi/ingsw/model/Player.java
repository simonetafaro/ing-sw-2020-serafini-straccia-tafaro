package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CardManager;
import it.polimi.ingsw.utils.CustomDate;
import it.polimi.ingsw.utils.PlayerColor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Player implements Serializable {

    private transient CardManager cardManager;
    private PlayerColor color;
    private transient CustomDate birthDate;
    private String nickname;
    private int ID;
    private Worker worker1;
    private Worker worker2;
    private transient PlayerTurn myTurn;
    private transient CardRuleDecorator myCardMethod;
    private Card myCard;
    private transient Socket socket;
    private transient ObjectInputStream input;
    private transient ObjectOutputStream output;

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public Player(){
        this.worker1=null;
        this.worker2=null;
        this.cardManager= new CardManager();
    }

    public Player(int ID, String nickname, Socket socket) {
        this.nickname = nickname;
        this.ID = ID;
        this.cardManager = new CardManager();
        this.socket = socket;
    }

    public PlayerTurn setMyTurn() {
        this.myTurn = new PlayerTurn(this);
        return this.myTurn;
    }
    public void setColor(PlayerColor color) {
        this.color = color;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setWorker1(Worker worker1) {
        this.worker1 = worker1;

    }
    public void setWorker2(Worker worker2) {
        this.worker2 = worker2;

    }
    public void setMyCard(String cardName){
        cardManager.setCardFromFile(this,cardName);
    }
    public void setCard (Card card){ this.myCard=card; }
    public void setMyCardMethod(CardRuleDecorator cardRuleDecorator){
        this.myCardMethod=cardRuleDecorator;
    }

    public Socket getSocket() {
        return socket;
    }
    public String getNickname() {
        return nickname;
    }
    public Worker getWorker1() {
        return worker1;

    }
    public Worker getWorker2() {
        return worker2;
    }
    public PlayerColor getColor() {
        return color;
    }
    public Card getMyCard(){
        return this.myCard;
    }
    public int getID() {
        return ID;
    }

    public CardRuleDecorator getMyCardMethod() {
        return myCardMethod;
    }
}
