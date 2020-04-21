package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    private static final PlayerColor GREY =PlayerColor.GREY ;
    Cell cell= new Cell();
    Worker worker =new Worker(cell,1,GREY);

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
        assertEquals(cell.getCurrWorker(),null);


    }
}