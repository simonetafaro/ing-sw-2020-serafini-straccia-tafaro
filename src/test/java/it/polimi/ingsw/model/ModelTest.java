package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class ModelTest {

    private Worker worker= new Worker();
    private Cell cell=new Cell();
    private Model model= new Model();
    private PlayerMove move= new PlayerMove(worker,1,1);

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

    @Test
    void getTurn() {
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY);
        assertEquals(model.getTurn(),PlayerColor.BLUE);

        model.updateTurn();
        assertEquals(model.getTurn(), PlayerColor.GREY);

        model.updateTurn();
        assertEquals(model.getTurn(), PlayerColor.BLUE);

    }

    @Test
    void updateTurn() {
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY);
        assertEquals(model.getTurn(),PlayerColor.BLUE);

        model.updateTurn();
        assertEquals(model.getTurn(), PlayerColor.GREY);

        model.updateTurn();
        assertEquals(model.getTurn(), PlayerColor.BLUE);
    }

    @Test
    void hasWon() {
        Model model = new Model();
        Cell cell1 = model.getBoard().getCell(0,0);
        Cell cell2 = model.getBoard().getCell(1,1);
        Worker worker = new Worker();
        cell1.setLevel(2);
        worker.setWorkerPosition(cell1);
        cell2.setLevel(3);
        PlayerMove move = new PlayerMove(worker,1,1);
        assertTrue(model.hasWon(move));
    }

    @Test
    void hasNotWon(){
        Model model = new Model();
        Cell cell1 = model.getBoard().getCell(0,0);
        Cell cell2 = model.getBoard().getCell(1,1);
        Worker worker = new Worker();
        cell1.setLevel(3);
        worker.setWorkerPosition(cell1);
        cell2.setLevel(3);
        PlayerMove move = new PlayerMove(worker,1,1);
        assertFalse(model.hasWon(move));

        Cell cell3 = model.getBoard().getCell(2,2);
        Cell cell4 = model.getBoard().getCell(2,3);
        Worker worker1 = new Worker();
        cell3.setLevel(1);
        worker1.setWorkerPosition(cell3);
        cell4.setLevel(2);
        PlayerMove move1 = new PlayerMove(worker1,2,3);
        assertFalse(model.hasWon(move1));
    }

    @Test
    void isPlayerStuck() {
        Player player = new Player();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        player.setWorker1(worker1);
        player.setWorker2(worker2);
        worker1.setStuck(true);
        worker2.setStuck(true);

        PlayerMove move = new PlayerMove(player,worker1,1,1);
        assertTrue(model.isPlayerStuck(move));
    }

    @Test
    void isNotPlayerStuck(){
        Player player = new Player();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        player.setWorker1(worker1);
        player.setWorker2(worker2);
        worker1.setStuck(false);
        worker2.setStuck(true);

        PlayerMove move = new PlayerMove(player,worker1,1,1);
        assertFalse(model.isPlayerStuck(move));
    }
}