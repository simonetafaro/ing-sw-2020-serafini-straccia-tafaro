package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {



    @Test
    void testisNotClosedTo() {
        Cell cella=new Cell(4,4),cella1=new Cell(1,1);
        assertFalse(cella.isClosedTo(cella1));
    }
    @Test
    void testisClosedTo() {
        Cell cella=new Cell(4,4),cella1=new Cell(3,4);
        assertTrue(cella.isClosedTo(cella1));
    }


}

