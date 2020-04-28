package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void testisNotClosedTo() {
        Cell cell=new Cell(4,4);
        Cell cell1=new Cell(1,1);
        assertFalse(cell.isClosedTo(cell1));
    }

    @Test
    void testisClosedTo() {
        Cell cell=new Cell(4,4);
        Cell cell1=new Cell(3,4);
        assertTrue(cell.isClosedTo(cell1));
    }

    @Test
    void setCurrWorker() {
        Cell cell = new Cell();
        Worker worker = new Worker();
        cell.setCurrWorker(worker);
        assertEquals(cell.getCurrWorker(),worker);
    }

    @Test
    void setFreeSpace() {
        Cell cell = new Cell();
        cell.setFreeSpace(true);
        assertTrue(cell.isFree());
        cell.setFreeSpace(false);
        assertFalse(cell.isFree());
    }

    @Test
    void setPosX() {
        Cell cell = new Cell(0,1);
        assertEquals(cell.getPosX(),0);
    }

    @Test
    void setPosY() {
        Cell cell = new Cell(1,0);
        assertEquals(cell.getPosY(),0);
    }

    @Test
    void setLevel() {
        Cell cell = new Cell();
        cell.setLevel(3);
        assertEquals(cell.getLevel(),3);
        cell.setLevel(4);
        assertEquals(cell.getLevel(),4);
        assertFalse(cell.isFree());
    }

    @Test
    void getPosX() {
        Cell cell = new Cell(0,1);
        assertEquals(cell.getPosX(),0);
    }

    @Test
    void getPosY() {
        Cell cell = new Cell(1,0);
        assertEquals(cell.getPosY(),0);
    }

    @Test
    void getLevel() {
        Cell cell = new Cell();
        cell.setLevel(3);
        assertEquals(cell.getLevel(),3);
    }

    @Test
    void getCurrWorker() {
        Cell cell = new Cell();
        Worker worker = new Worker();
        cell.setCurrWorker(worker);
        assertEquals(cell.getCurrWorker(),worker);
    }

    @Test
    void isFree() {
        Cell cell = new Cell();
        cell.setFreeSpace(false);
        assertFalse(cell.isFree());

        cell.setFreeSpace(true);
        assertTrue(cell.isFree());

        Worker worker = new Worker();
        worker.setWorkerPosition(cell);
        assertFalse(cell.isFree());

        cell.setFreeSpace(true);
        cell.setLevel(4);
        assertFalse(cell.isFree());
    }

    @Test
    void buildInCell() {
        Cell cell = new Cell();

        cell.setLevel(1);
        cell.buildInCell();
        assertEquals(cell.getLevel(),2);

        cell.setLevel(3);
        cell.buildInCell();
        assertEquals(cell.getLevel(),4);
        assertFalse(cell.isFree());
    }

    //non viene messo a null il puntatore alla getworkerposition in worker
    @Test
    void deleteCurrWorker() {
        Cell cell = new Cell(0,0);
        Worker worker = new Worker();

        worker.setWorkerPosition(cell);
        cell.deleteCurrWorker();

        assertNull(cell.getCurrWorker());
    }

    @Test
    void hasFreeCellClosed() {
        Board board = new Board();

        //false test for all dome in the corner
        Cell cell = board.getCell(0,0);
        Cell cell1 = board.getCell(0,1);
        Cell cell2 = board.getCell(1,0);
        Cell cell3 = board.getCell(1,1);
        cell.setFreeSpace(false);
        cell1.setLevel(4);
        cell2.setLevel(4);
        cell3.setLevel(4);
        assertFalse(cell.hasFreeCellClosed(board.getPlayingBoard()));

        //false test for all workers in the corner
        cell = board.getCell(4,4);
        cell1 = board.getCell(3,3);
        cell2 = board.getCell(3,4);
        cell3 = board.getCell(4,3);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        Worker worker3 = new Worker();
        cell.setFreeSpace(false);
        worker1.setWorkerPosition(cell1);
        worker2.setWorkerPosition(cell2);
        worker3.setWorkerPosition(cell3);
        assertFalse(cell.hasFreeCellClosed(board.getPlayingBoard()));
        worker1.clear();
        worker2.clear();
        worker3.clear();

        //true test in the middle of the board
        cell = board.getCell(2,2);
        cell1 = board.getCell(2,3);
        cell2 = board.getCell(1,3);
        cell3 = board.getCell(1,2);
        Cell cell4 = board.getCell(1,1);
        Cell cell5 = board.getCell(2,1);
        Cell cell6 = board.getCell(3,1);
        Cell cell7 = board.getCell(3,2);
        cell.setFreeSpace(false);
        cell1.setFreeSpace(false);
        cell2.setFreeSpace(false);
        cell3.setFreeSpace(false);
        cell4.setFreeSpace(false);
        cell5.setFreeSpace(false);
        cell6.setFreeSpace(false);
        cell7.setFreeSpace(false);
        assertTrue(cell.hasFreeCellClosed(board.getPlayingBoard()));

        //false test in the middle of the board
        cell = board.getCell(2,2);
        cell1 = board.getCell(2,3);
        cell2 = board.getCell(1,3);
        cell3 = board.getCell(1,2);
        cell4 = board.getCell(1,1);
        cell5 = board.getCell(2,1);
        cell6 = board.getCell(3,1);
        cell7 = board.getCell(3,2);
        Cell cell8 = board.getCell(3,3);
        cell.setFreeSpace(false);
        cell1.setFreeSpace(false);
        cell2.setFreeSpace(false);
        cell3.setFreeSpace(false);
        cell4.setFreeSpace(false);
        cell5.setFreeSpace(false);
        cell6.setFreeSpace(false);
        cell7.setFreeSpace(false);
        cell8.setFreeSpace(false);
        assertFalse(cell.hasFreeCellClosed(board.getPlayingBoard()));
    }

    @Test
    void canBuildInCells() {
        Board board = new Board();

        //false test for all dome in the corner
        Cell cell = board.getCell(0,0);
        Cell cell1 = board.getCell(0,1);
        Cell cell2 = board.getCell(1,0);
        Cell cell3 = board.getCell(1,1);
        cell.setFreeSpace(false);
        cell1.setLevel(4);
        cell2.setLevel(4);
        cell3.setLevel(4);
        assertFalse(cell.canBuildInCells(board.getPlayingBoard()));

        //false test for all workers in the corner
        cell = board.getCell(4,4);
        cell1 = board.getCell(3,3);
        cell2 = board.getCell(3,4);
        cell3 = board.getCell(4,3);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        Worker worker3 = new Worker();
        cell.setFreeSpace(false);
        worker1.setWorkerPosition(cell1);
        worker2.setWorkerPosition(cell2);
        worker3.setWorkerPosition(cell3);
        assertFalse(cell.canBuildInCells(board.getPlayingBoard()));
        worker1.clear();
        worker2.clear();
        worker3.clear();

        //true test in the middle of the board
        cell = board.getCell(2,2);
        cell1 = board.getCell(2,3);
        cell2 = board.getCell(1,3);
        cell3 = board.getCell(1,2);
        Cell cell4 = board.getCell(1,1);
        Cell cell5 = board.getCell(2,1);
        Cell cell6 = board.getCell(3,1);
        Cell cell7 = board.getCell(3,2);
        cell.setFreeSpace(false);
        cell1.setFreeSpace(false);
        cell2.setFreeSpace(false);
        cell3.setFreeSpace(false);
        cell4.setFreeSpace(false);
        cell5.setFreeSpace(false);
        cell6.setFreeSpace(false);
        cell7.setFreeSpace(false);
        assertTrue(cell.canBuildInCells(board.getPlayingBoard()));

        //false test in the middle of the corner
        cell = board.getCell(2,2);
        cell1 = board.getCell(2,3);
        cell2 = board.getCell(1,3);
        cell3 = board.getCell(1,2);
        cell4 = board.getCell(1,1);
        cell5 = board.getCell(2,1);
        cell6 = board.getCell(3,1);
        cell7 = board.getCell(3,2);
        Cell cell8 = new Cell(3,3);
        cell.setFreeSpace(false);
        cell1.setFreeSpace(false);
        cell2.setFreeSpace(false);
        cell3.setFreeSpace(false);
        cell4.setFreeSpace(false);
        cell5.setFreeSpace(false);
        cell6.setFreeSpace(false);
        cell7.setFreeSpace(false);
        cell8.setFreeSpace(false);
        assertTrue(cell.canBuildInCells(board.getPlayingBoard()));
    }

}

