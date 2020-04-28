package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    private PlayerColor GREY = PlayerColor.GREY ;
    private Cell cell = new Cell();
    private Worker worker = new Worker(cell,1,GREY);

    @Test
    void setWorkerPosition() {
        worker.setWorkerPosition(cell);
        assertFalse(cell.isFree());
        assertEquals(cell.getCurrWorker(),worker);
    }

    @Test
    void clear() {
        cell.setFreeSpace(false);
        assertFalse(cell.isFree());
        assertEquals(cell.getCurrWorker(),worker);
        worker.clear();

        assertTrue(cell.isFree());
        assertNull(cell.getCurrWorker());
    }

    @Test
    void setStuck() {
        worker.setStuck(false);
        assertFalse(worker.isStuck());
        worker.setStuck(true);
        assertTrue(worker.isStuck());
    }

    @Test
    void setColor() {
        worker.setColor(PlayerColor.GREY);
        assertEquals(worker.getColor(),'G');
    }

    @Test
    void getWorkerPosition() {
        Cell cell = new Cell(0,0);
        worker.setWorkerPosition(cell);

        assertEquals(worker.getWorkerPosition(),cell);
    }

    @Test
    void getWorkerNum() {
        Cell cell = new Cell(0,0);
        Worker worker1 = new Worker(cell,1,PlayerColor.GREY);
        assertEquals(worker1.getWorkerNum(),1);

        Worker worker2 = new Worker(cell,2,PlayerColor.GREY);
        assertEquals(worker2.getWorkerNum(),2);
    }

    @Test
    void getColor() {
        Cell cell = new Cell(0,0);
        Worker worker1 = new Worker(cell,1,PlayerColor.GREY);

        assertEquals(worker1.getColor(),'G');
    }

    @Test
    void isStuck() {
        worker.setStuck(false);
        assertFalse(worker.isStuck());
        worker.setStuck(true);
        assertTrue(worker.isStuck());
    }


}