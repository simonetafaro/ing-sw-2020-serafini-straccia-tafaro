package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    Worker worker= new Worker();
    Cell cell=new Cell();
    Cell cell1=new Cell();
    Model model= new Model();
    Player player =new Player();
    PlayerMove move= new PlayerMove(worker,1,1);
    @Test
    void isReachableCell() {
        cell.setPosX(1);
        cell.setPosY(2);
        worker.setWorkerPosition(cell);
        assertTrue(model.isReachableCell(move));
    }

    @Test
    void isNotReachableCell() {
        cell.setPosX(4);
        cell.setPosY(4);
        worker.setWorkerPosition(cell);
        assertFalse(model.isReachableCell(move));
    }
    @Test
    void isEmptyLevelCell() {
        model.getBoard().getCell(move.getRow(),move.getColumn()).setLevel(4);
        assertFalse(model.isEmptyCell(move));
    }
    @Test
    void isEmptySpaceCell() {
        model.getBoard().getCell(move.getRow(),move.getColumn()).setFreeSpace(false);
        assertFalse(model.isEmptyCell(move));
    }
    @Test
    void isTrueEmptyLevelCell() {
        model.getBoard().getCell(move.getRow(),move.getColumn()).setLevel(3);
        assertTrue(model.isEmptyCell(move));
    }
    @Test
    void isTrueEmptySpaceCell() {
        model.getBoard().getCell(move.getRow(),move.getColumn()).setFreeSpace(true);
        assertTrue(model.isEmptyCell(move));
    }
    @Test
    void isLevelDifferenceAllowed() {
        model.getBoard().getCell(move.getRow(),move.getColumn()).setLevel(3);
        cell.setPosX(4);
        cell.setPosY(4);
        cell.setLevel(1);
        worker.setWorkerPosition(cell);
        assertFalse(model.isLevelDifferenceAllowed(move));
    }
    @Test
    void isTrueLevelDifferenceAllowed() {
        model.getBoard().getCell(move.getRow(),move.getColumn()).setLevel(3);
        cell.setPosX(4);
        cell.setPosY(4);
        cell.setLevel(2);
        worker.setWorkerPosition(cell);
        assertTrue(model.isLevelDifferenceAllowed(move));
    }
    @Test
    void isTrue1LevelDifferenceAllowed() {
        model.getBoard().getCell(move.getRow(),move.getColumn()).setLevel(3);
        cell.setPosX(4);
        cell.setPosY(4);
        cell.setLevel(3);
        worker.setWorkerPosition(cell);
        assertTrue(model.isLevelDifferenceAllowed(move));
    }
}