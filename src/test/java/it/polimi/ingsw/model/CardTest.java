package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    private Card card = new Card("Apollo");

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
        card.addCustomStep(0,"M");
        card.addCustomStep(1,"M");
        card.addCustomStep(2,"B");
        card.addCustomStep(3,"END");
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

    @Test
    void addCustomStep() {
        card.addCustomStep(0,"M");
        card.addCustomStep(1,"B");
        card.addCustomStep(2,"B");
        card.addCustomStep(3,"END");

        assertNotEquals(card.getStepLetter(1),"END");
        assertNotEquals(card.getStepLetter(1),"B");
        assertEquals(card.getStepLetter(1),"M");
        assertEquals(card.getStepLetter(2),"B");
        assertEquals(card.getStepLetter(3),"B");
        assertEquals(card.getStepLetter(4),"END");
    }

    @Test
    void setCustomSteps() {
        card.addCustomStep(0,"M");
        card.addCustomStep(1,"B");
        card.addCustomStep(2,"B");
        card.addCustomStep(3,"END");

        assertEquals(card.getStepLetter(1),"M");
        card.setCustomSteps(0,"B");
        assertNotEquals(card.getStepLetter(1),"M");
        assertEquals(card.getStepLetter(1),"B");

        assertEquals(card.getStepLetter(2),"B");
        card.setCustomSteps(1,"M");
        assertNotEquals(card.getStepLetter(2),"B");
        assertEquals(card.getStepLetter(2),"M");
    }

    @Test
    void getName() {
        assertEquals(card.getName(),"Apollo");
        assertNotEquals(card.getName(),"Pan");
    }
}