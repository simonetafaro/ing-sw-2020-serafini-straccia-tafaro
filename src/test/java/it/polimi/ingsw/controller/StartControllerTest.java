package it.polimi.ingsw.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StartControllerTest {

    private StartController startController = new StartController();

    @Test
    void isBlue() {
        startController.setBlue(true);
        assertTrue(startController.isBlue());
        startController.setBlue(false);
        assertFalse(startController.isBlue());
        }

    @Test
    void isGrey() {
        startController.setGrey(true);
        assertTrue(startController.isGrey());
        startController.setGrey(false);
        assertFalse(startController.isGrey());

    }

    @Test
    void isWhite() {
        startController.setWhite(true);
        assertTrue(startController.isWhite());
        startController.setWhite(false);
        assertFalse(startController.isWhite());
    }

    @Test
    void setBlue() {
        startController.setBlue(true);
        assertTrue(startController.isBlue());
        startController.setBlue(false);
        assertFalse(startController.isBlue());
    }

    @Test
    void setGrey() {
        startController.setGrey(true);
        assertTrue(startController.isGrey());
        startController.setGrey(false);
        assertFalse(startController.isGrey());
    }

    @Test
    void setWhite() {
        startController.setWhite(true);
        assertTrue(startController.isWhite());

        startController.setWhite(false);
        assertFalse(startController.isWhite());

    }

}