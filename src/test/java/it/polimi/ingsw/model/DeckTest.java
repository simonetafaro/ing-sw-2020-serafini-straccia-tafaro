package it.polimi.ingsw.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    private Deck deck = new Deck();

    //TODO: Handle uppercase letter
    @Test
    void validCard() {
        assertFalse(deck.validCard("Hermes"));
        assertTrue(deck.validCard("Apollo"));
        assertFalse(deck.validCard("\0"));
        assertFalse(deck.validCard(" "));
        assertTrue(deck.validCard("Artemis"));
        assertTrue(deck.validCard("Atlas"));
        assertTrue(deck.validCard("Athena"));
        assertTrue(deck.validCard("Demeter"));
        assertTrue(deck.validCard("Hephaestus"));
        assertTrue(deck.validCard("Minotaur"));
        assertTrue(deck.validCard("Pan"));
        assertTrue(deck.validCard("Prometheus"));
        assertFalse(deck.validCard("APOLLO"));
        assertFalse(deck.validCard("PAN"));
    }

    @Test
    void setChosenCard() {
        assertTrue(deck.setChosenCard("Apollo"));
        assertFalse(deck.setChosenCard("Apollo"));
        assertFalse(deck.setChosenCard("Apollo"));
        assertTrue(deck.setChosenCard("Atlas"));
        assertFalse(deck.setChosenCard("Atlas"));
    }

}