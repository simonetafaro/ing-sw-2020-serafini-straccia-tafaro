package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Deck class manages card choice. Each {@link Player} can have
 * only a card so that in a game can be chosen two or three cards
 */
public class Deck {

    /**
     * hash map that relates each card name with a boolean
     * that indicates if the card has been chosen or not
     */
    private Map<String, Boolean> deck = new HashMap<>();

    /**
     * Deck constructor: it initializes deck hash map
     */
    public Deck(){
        this.deck.put("Apollo", false);
        this.deck.put("Artemis", false);
        this.deck.put("Athena", false);
        this.deck.put("Atlas", false);
        this.deck.put("Chronus",false);
        this.deck.put("Demeter", false);
        this.deck.put("Hephaestus", false);
        this.deck.put("Hestia",false);
        this.deck.put("Minotaur", false);
        this.deck.put("Pan", false);
        this.deck.put("Poseidon", false);
        this.deck.put("Prometheus", false);
        this.deck.put("Selene",false);
        this.deck.put("Zeus", false);
    }

    /**
     * @param cardName
     * @return true if deck contains the card
     */
    public boolean validCard(String cardName){
        return deck.containsKey(cardName);
    }

    /**
     * @param cardName
     * @return true if a card has already been chosen
     */
    public boolean setChosenCard(String cardName){
        return deck.replace(cardName, false, true);
    }

    /**
     * @return deck
     */
    public Map getDeck(){
        return this.deck;
    }

}
