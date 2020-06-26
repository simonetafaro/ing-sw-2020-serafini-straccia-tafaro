package it.polimi.ingsw.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetWorkerPositionTest {

    private SetWorkerPosition setWorkerPosition = new SetWorkerPosition(1,1,PlayerColor.BLUE,1,1);

    @Test
    void getX() {
        assertEquals(1,setWorkerPosition.getX());
        assertNotEquals(2,setWorkerPosition.getX());
    }

    @Test
    void getY() {
        assertEquals(1,setWorkerPosition.getY());
        assertNotEquals(2,setWorkerPosition.getY());
    }

    @Test
    void getColor() {
        assertEquals(PlayerColor.BLUE, setWorkerPosition.getColor());
        assertNotEquals(PlayerColor.GREY, setWorkerPosition.getColor());
        assertNotEquals(PlayerColor.WHITE, setWorkerPosition.getColor());
    }

    @Test
    void getID() {
        assertEquals(1,setWorkerPosition.getID());
        assertNotEquals(2,setWorkerPosition.getID());
    }

    @Test
    void getWorkerNum() {
        assertEquals(1,setWorkerPosition.getWorkerNum());
        assertNotEquals(2,setWorkerPosition.getWorkerNum());
    }
}