package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    private Card card = new Card("Apollo");
    //sono presenti altri due metodi che però non sono usati pertanto non testati
    @Test
    void isUsingCard() {
        card.setUsingCard(true);
        assertTrue(card.isUsingCard());
    }

    @Test
    void notIsUsingCard() {
        card.setUsingCard(false);
        assertFalse(card.isUsingCard());
    }

    @Test
    void setUsingCard() {
        boolean usingCard=false;
        card.setUsingCard(usingCard);
        assertFalse(card.isUsingCard());
        usingCard=true;
        card.setUsingCard(usingCard);
        assertTrue(card.isUsingCard());
    }

    @Test
    void getStepLetter() {
        int i = 1;
        card.setCustomM1("M");
        card.setCustomM2("M");
        card.setCustomM3("B");
        card.setCustomM4("END");
        assertEquals(card.getStepLetter(i),"M");
        i = 2;
        assertEquals(card.getStepLetter(i),"M");
        i = 3;
        assertEquals(card.getStepLetter(i),"B");
        i = 4;
        assertEquals(card.getStepLetter(i),"END");
    }

    @Test
    void getStandardStepLetter() {
        int i = 1;
        assertEquals(card.getStandardStepLetter(i),"M");
        i = 2;
        assertEquals(card.getStandardStepLetter(i),"B");
        i = 3;
        assertEquals(card.getStandardStepLetter(i),"END");
    }
}