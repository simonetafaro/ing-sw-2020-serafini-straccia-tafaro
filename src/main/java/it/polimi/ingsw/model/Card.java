package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Card class used to store sequence of
 * both standard moves and custom move
 */
public class Card implements Serializable {

    /**
     * card name
     */
    private String name;

    /**
     * List of custom moves that depends on card,
     * allowed only by a player that has that card
     * and wants to use it
     */
    private ArrayList<String> customSteps;

    /**
     * List of standard moves, allowed by all the players
     */
    private ArrayList<String> standardStep;

    /**
     * Choice of player to use or not card
     */
    private boolean usingCard;

    /**
     * Card constructor
     * @param name
     * It initializes standard moves with the characters:
     * "M" move
     * "B" build
     * "END" end
     * and custom moves
     */
    public Card (String name){
        this.name=name;
        this.usingCard=false;
        this.standardStep= new ArrayList<>();
        this.standardStep.add(0,"M");
        this.standardStep.add(1,"B");
        this.standardStep.add(2,"END");
        this.customSteps = new ArrayList<>();
    }

    /**
     * @return true if player is usingCard
     */
    public boolean isUsingCard() {
        return usingCard;
    }

    /**
     * @param usingCard
     * It changes usingCard
     */
    public void setUsingCard(boolean usingCard) {
        this.usingCard = usingCard;
    }

    /**
     * @param i index of list
     * @param customStep move character
     * It populate custom moves list
     */
    public void addCustomStep(int i, String customStep){
        this.customSteps.add(i, customStep);
    }

    /**
     * @param i index
     * @param customStep move character
     * It changes an existing move in custom move list
     */
    public void setCustomSteps(int i, String customStep){
        this.customSteps.set(i, customStep);
    }

    /**
     * @return card name
     */
    public String getName() {
        return name;
    }

    /**
     * @param i index
     * @return move at index i in custom move list
     */
    public String getStepLetter(int i){
        return customSteps.get(i-1);
    }

    /**
     * @param i index
     * @return move at index i in standard move list
     */
    public String getStandardStepLetter(int i){
        return standardStep.get(i-1);
    }

}
