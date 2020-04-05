package it.polimi.ingsw.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

  /*  @Test
//controlla che la cella venga setta a occupata dopo la set
    void TestSetWorkerPosition() {
        Cell cell = new Cell(0, 0);
        Worker worker1 = new Worker();
        worker1.setWorkerPosition(cell);
        assertFalse(cell.isFree());
    }

    //test che non entra se non deve andare
    @Test
    void testInvalidmove() {
        Cell cell = new Cell(1, 1), cell1 = new Cell(0, 0), cell2 = new Cell(0, 1);
        Worker worker;
        worker = new Worker(cell, 1, PlayerColor.BLUE);
        cell.setLevel(0);
        cell1.setLevel(2);
        cell2.setLevel(3);
        worker.setWorkerPosition(cell);
        assertFalse(worker.move(cell1));
        assertFalse(worker.move(cell2));
    }

    @Test
    void testValidmove() {
        Cell cell = new Cell(1, 1), cell1 = new Cell(0, 0), cell2 = new Cell(0, 1);
        Worker worker = new Worker();
        worker.setWorkerPosition(cell);
        cell1.setFreeSpace(true);
        assertTrue(worker.move(cell1));

    }

    @Test

    void testWinMove() {
        Cell cell = new Cell(1, 2), cell1 = new Cell(1, 1),
                cell2 = new Cell(0, 0), cell3 = new Cell(0, 1);
        Worker worker = new Worker();
        cell.setLevel(0);
        cell.setFreeSpace(true);
        cell1.setLevel(1);
        cell1.setFreeSpace(true);
        cell2.setLevel(2);
        cell2.setFreeSpace(true);
        cell3.setLevel(3);
        cell3.setFreeSpace(true);
        worker.setWorkerPosition(cell);
        worker.move(cell1);
        worker.move(cell2);
        worker.move(cell3);
        assertTrue(worker.winner());

    }

    @Test
        //non posso costruire dove ho una cupola
    void notBuildDome() {
        Cell cell = new Cell(1, 1), cell1 = new Cell(0, 0), cell2 = new Cell(0, 1);
        Worker worker = new Worker();
        worker.setWorkerPosition(cell);
        cell2.setLevel(4);
        assertFalse(worker.build(cell2));
    }

    @Test
    void notBuildWorker() {
        Cell cell = new Cell(1, 1), cell1 = new Cell(0, 0), cell2 = new Cell(0, 1);
        Worker worker = new Worker(), worker1 = new Worker();
        worker.setWorkerPosition(cell);
        worker1.setWorkerPosition(cell2);
        assertFalse(worker.build(cell2));
    }

    @Test
    void Build() {
        Cell cell = new Cell(1, 1), cell1 = new Cell(0, 0), cell2 = new Cell(0, 1);
        Worker worker = new Worker(), worker1 = new Worker();
        worker.setWorkerPosition(cell);
        cell2.setFreeSpace(true);
        assertTrue(worker.build(cell2));
    }*/
}