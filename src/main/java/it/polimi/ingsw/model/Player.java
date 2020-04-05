package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.CustomDate;
import it.polimi.ingsw.utils.PlayerColor;

import java.util.Scanner;

public class Player {

    private PlayerColor color;
    private CustomDate birthdate;
    private String nickname;

    private Worker worker1;
    private Worker worker2;
    private PlayerTurn myTurn;
    private Card myCard;


    public Player(){}

    public Player(Player player) {
        this.nickname = player.getNickname();
        this.color = player.getColor();
        this.birthdate = player.getBirthdate();
        this.worker1=player.worker1;
        this.worker2=player.worker2;
        setMyCard();
    }

    public PlayerTurn setMyTurn() {
        this.myTurn = new PlayerTurn(this);
        return this.myTurn;
    }
    public void setColor(PlayerColor color) {
        this.color = color;
    }
    public void setBirthdate(CustomDate birtday) {
        this.birthdate = birtday;
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
    public void setMyCard(){
        this.myCard = new Card("StandardCard");
        this.myCard.setStandardGame();
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
    public CustomDate getBirthdate() {
        return birthdate;
    }
    public Card getMyCard() {
        return myCard;
    }
}
