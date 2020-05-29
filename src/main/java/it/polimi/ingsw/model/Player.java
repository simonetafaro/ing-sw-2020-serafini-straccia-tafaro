package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CardManager;
import it.polimi.ingsw.utils.CustomDate;
import it.polimi.ingsw.utils.PlayerColor;

import java.io.Serializable;
import java.net.Socket;

public class Player implements Serializable {

    private transient CardManager cardManager;
    private PlayerColor color;
    private CustomDate birthDate;
    private String nickname;
    private int ID;
    private transient Worker worker1;
    private transient Worker worker2;
    private transient PlayerTurn myTurn;
    private transient CardRuleDecorator myCardMethod;
    private transient Card myCard;
    private Socket socket;

    public Player(){
        this.worker1=null;
        this.worker2=null;
        this.cardManager= new CardManager();
    }

    /*public Player(Player player) {
        this.nickname = player.getNickname();
        this.color = player.getColor();
        this.birthDate = player.getBirthDate();
        this.worker1=player.worker1;
        this.worker2=player.worker2;
        this.cardManager= new CardManager();
    }*/

    public Player(int ID, String nickname, CustomDate birthDate, Socket socket) {
        this.birthDate = birthDate;
        this.nickname = nickname;
        this.ID = ID;
        this.cardManager = new CardManager();
        this.socket = socket;
    }

    public Player(String name, CustomDate birthDate, PlayerColor color){
        this.nickname = name;
        this.birthDate = birthDate;
        this.color = color;
        this.worker1 = null;
        this.worker2 = null;
        this.cardManager = new CardManager();
    }


    public PlayerTurn setMyTurn() {
        this.myTurn = new PlayerTurn(this);
        return this.myTurn;
    }
    public void setColor(PlayerColor color) {
        this.color = color;
    }
    public void setBirthDate(CustomDate birthDate) {
        this.birthDate = birthDate;
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
    public CustomDate getBirthDate() {
        return birthDate;
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
