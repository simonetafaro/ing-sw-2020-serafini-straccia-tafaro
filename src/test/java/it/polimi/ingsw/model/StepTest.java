package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StepTest {

    @Test
    void setType() {
        Step step = new Step();
        step.setType("M");
        assertEquals(step.getType(),"M");
        step.setType("B");
        assertEquals(step.getType(),"B");
        step.setType("END");
        assertEquals(step.getType(),"END");
        step.setType(" ");
        assertEquals(step.getType()," ");
        step.setType("\0");
        assertEquals(step.getType(),"\0");
    }

    @Test
    void setCellFrom() {
        Step step = new Step();
        Cell cell = new Cell(0,0);
        step.setCellFrom(cell);
        assertEquals(step.getCellFrom(),cell);
    }

    @Test
    void setCellTo() {
        Step step = new Step();
        Cell cell = new Cell(0,0);
        step.setCellTo(cell);
        assertEquals(step.getCellTo(),cell);
    }

    @Test
    void getType() {
        Step step = new Step();
        step.setType("M");
        assertEquals(step.getType(),"M");
        step.setType("B");
        assertEquals(step.getType(),"B");
        step.setType("END");
        assertEquals(step.getType(),"END");
        step.setType(" ");
        assertEquals(step.getType()," ");
        step.setType("\0");
        assertEquals(step.getType(),"\0");
    }

    @Test
    void getCellFrom() {
        Step step = new Step();
        Cell cell = new Cell(0,0);
        step.setCellFrom(cell);
        assertEquals(step.getCellFrom(),cell);
    }

    @Test
    void getCellTo() {
        Step step = new Step();
        Cell cell = new Cell(0,0);
        step.setCellTo(cell);
        assertEquals(step.getCellTo(),cell);
    }
}