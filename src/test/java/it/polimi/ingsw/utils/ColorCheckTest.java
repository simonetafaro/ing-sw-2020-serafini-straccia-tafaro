package it.polimi.ingsw.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorCheckTest {

    private ColorCheck color = new ColorCheck(false,false,false);

    @Test
    void isBlue() {
        assertFalse(color.isBlue());
        color.setBlue(true);
        assertTrue(color.isBlue());
        color.setBlue(false);
        assertFalse(color.isBlue());
    }

    @Test
    void isGrey() {
        assertFalse(color.isGrey());
        color.setGrey(true);
        assertTrue(color.isGrey());
        color.setGrey(false);
        assertFalse(color.isGrey());
    }

    @Test
    void isWhite() {
        assertFalse(color.isWhite());
        color.setWhite(true);
        assertTrue(color.isWhite());
        color.setWhite(false);
        assertFalse(color.isWhite());
    }

    @Test
    void setGrey() {
        assertFalse(color.isGrey());
        color.setGrey(true);
        assertTrue(color.isGrey());
        color.setGrey(false);
        assertFalse(color.isGrey());
    }

    @Test
    void setWhite() {
        assertFalse(color.isWhite());
        color.setWhite(true);
        assertTrue(color.isWhite());
        color.setWhite(false);
        assertFalse(color.isWhite());
    }

    @Test
    void setBlue() {
        assertFalse(color.isBlue());
        color.setBlue(true);
        assertTrue(color.isBlue());
        color.setBlue(false);
        assertFalse(color.isBlue());
    }
}