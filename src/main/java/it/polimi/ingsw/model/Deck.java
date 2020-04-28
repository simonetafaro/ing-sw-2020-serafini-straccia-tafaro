package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class Deck {
    private Map<String, Boolean> deck = new HashMap<>();

    public Deck(){
        this.deck.put("Apollo", false);
        this.deck.put("Artemis", false);
        this.deck.put("Athena", false);
        this.deck.put("Atlas", false);
        this.deck.put("Demeter", false);
        this.deck.put("Hephaestus", false);
        this.deck.put("Minotaur", false);
        this.deck.put("Pan", false);
        this.deck.put("Prometheus", false);
    }

    public boolean validCard(String cardName){
        return deck.containsKey(cardName);
    }
    public boolean setChosenCard(String cardName){
        return deck.replace(cardName, false, true);
    }
    public Map getDeck(){
        return this.deck;
    }

    public void clearDeck(){
        deck.forEach((nome, bool_value) -> deck.replace(nome, bool_value, false));
    }
}
