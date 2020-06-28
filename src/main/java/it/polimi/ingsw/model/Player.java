package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CardManager;
import it.polimi.ingsw.utils.PlayerColor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;


/**
 * This class contains all the information about a player:
 * player's {@link Card}, player color, two pointer
 * to his {@link Worker} and {@link PlayerTurn}
 */
public class Player implements Serializable {

    private static final long serialVersionUID = -6339404514199154792L;

    /**
     * Controller that manages to get the sequence of custom
     * moves from the xml file based on the player's card
     */
    private transient CardManager cardManager;

    /**
     * player color
     */
    private PlayerColor color;

    /**
     * player's nickname
     */
    private String nickname;

    /**
     * ID of the corresponding client
     */
    private int ID;

    /**
     * pointer to worker 1
     */
    private Worker worker1;

    /**
     * pointer to worker 2
     */
    private Worker worker2;

    /**
     * PlayerTurn gives information about the moves
     * each player makes during his turn
     */
    private transient PlayerTurn myTurn;

    /**
     * myCardDecorator is used to initialize Card
     * Decorator of the corresponding card
     */
    private transient CardRuleDecorator myCardMethod;

    /**
     * player's card
     */
    private Card myCard;

    /**
     * ObjectInputStream of player's client
     */
    private transient ObjectInputStream input;

    /**
     * ObjectOutputStream of player's client
     */
    private transient ObjectOutputStream output;

    /**
     * @return ObjectInputStream of player's client
     */
    public ObjectInputStream getInput() {
        return input;
    }

    /**
     * @param input
     * It sets ObjectInputStream of player's client
     */
    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    /**
     * @return ObjectOutputStream of player's client
     */
    public ObjectOutputStream getOutput() {
        return output;
    }

    /**
     * @param output
     * It sets ObjectOutputStream of player's client
     */
    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    /**
     * Player empty constructor to initialize CardManager()
     */
    public Player(){
        this.worker1=null;
        this.worker2=null;
        this.cardManager= new CardManager();
    }

    /**
     * Player constructor to initialize CardManager()
     * @param ID
     * @param nickname
     */
    public Player(int ID, String nickname) {
        this.nickname = nickname;
        this.ID = ID;
        this.cardManager = new CardManager();
    }

    /**
     * It initialize PlayerTurn and returns it
     * @return PlayerTurn
     */
    public PlayerTurn setMyTurn() {
        this.myTurn = new PlayerTurn(this);
        return this.myTurn;
    }

    /**
     * @param color
     * It changes player color
     */
    public void setColor(PlayerColor color) {
        this.color = color;
    }

    /**
     * @param nickname
     * It sets nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @param worker1
     * It sets worker 1
     */
    public void setWorker1(Worker worker1) {
        this.worker1 = worker1;

    }

    /**
     * @param worker2
     * It sets worker 2
     */
    public void setWorker2(Worker worker2) {
        this.worker2 = worker2;

    }

    /**
     * @param cardName
     * It initializes Card with the sequence of custom moves from xml file
     */
    public void setMyCard(String cardName){
        cardManager.setCardFromFile(this,cardName);
    }

    /**
     * @param card
     * It sets card
     */
    public void setCard (Card card){ this.myCard=card; }

    /**
     * @param cardRuleDecorator
     * It sets cardRuleDecorator
     */
    public void setMyCardMethod(CardRuleDecorator cardRuleDecorator){
        this.myCardMethod=cardRuleDecorator;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return pointer to worker 1
     */
    public Worker getWorker1() {
        return worker1;

    }

    /**
     * @return pointer to worker2
     */
    public Worker getWorker2() {
        return worker2;
    }

    /**
     * @return color
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * @return card
     */
    public Card getMyCard(){
        return this.myCard;
    }

    /**
     * @return ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @return myCardRuleDecorator
     */
    public CardRuleDecorator getMyCardMethod() {
        return myCardMethod;
    }
}
