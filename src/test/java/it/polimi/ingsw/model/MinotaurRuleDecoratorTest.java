package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinotaurRuleDecoratorTest {

    private MinotaurRuleDecorator minotaur = new MinotaurRuleDecorator();

    //false test with worker at the edge of the board in all directions
    @Test
    void notMinotaurMoveAllowed() {
        Model model = new Model();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();

        //tested in all diagonals
        worker1.setWorkerPosition(model.getBoard().getCell(1, 1));
        worker2.setWorkerPosition(model.getBoard().getCell(0,0));
        assertFalse(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(1, 1));
        worker2.setWorkerPosition(model.getBoard().getCell(0,2));
        assertFalse(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(3, 3));
        worker2.setWorkerPosition(model.getBoard().getCell(4,4));
        assertFalse(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(3, 3));
        worker2.setWorkerPosition(model.getBoard().getCell(4,2));
        assertFalse(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        //tested along x
        worker1.setWorkerPosition(model.getBoard().getCell(1, 1));
        worker2.setWorkerPosition(model.getBoard().getCell(0,1));
        assertFalse(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(3, 3));
        worker2.setWorkerPosition(model.getBoard().getCell(4,3));
        assertFalse(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        //tested along y
        worker1.setWorkerPosition(model.getBoard().getCell(1, 1));
        worker2.setWorkerPosition(model.getBoard().getCell(1,0));
        assertFalse(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(1, 3));
        worker2.setWorkerPosition(model.getBoard().getCell(1,4));
        assertFalse(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();
    }

    //true test with worker in the middle of the board
    @Test
    void minotaurMoveAllowed() {
        Model model = new Model();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();

        //tested in all diagonals
        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(3,3));
        assertTrue(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(1,1));
        assertTrue(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(1,3));
        assertTrue(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(3,1));
        assertTrue(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        //tested along x
        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(3,2));
        assertTrue(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(1,2));
        assertTrue(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        //tested along y
        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(2,3));
        assertTrue(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(2,1));
        assertTrue(minotaur.minotaurMoveAllowed(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(),model.getBoard().getPlayingBoard()));
        worker1.clear();
        worker2.clear();
    }

    @Test
    void pushingInCell() {
        Model model = new Model();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();

        //tested in all diagonals
        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(3,3));
        Cell cell = minotaur.pushingInCell(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(), model.getBoard().getPlayingBoard());
        assertEquals(cell,model.getBoard().getCell(4,4));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(1,1));
        cell = minotaur.pushingInCell(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(), model.getBoard().getPlayingBoard());
        assertEquals(cell,model.getBoard().getCell(0,0));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(1,3));
        cell = minotaur.pushingInCell(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(), model.getBoard().getPlayingBoard());
        assertEquals(cell,model.getBoard().getCell(0,4));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(3,1));
        cell = minotaur.pushingInCell(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(), model.getBoard().getPlayingBoard());
        assertEquals(cell,model.getBoard().getCell(4,0));
        worker1.clear();
        worker2.clear();

        //tested along x
        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(3,2));
        cell = minotaur.pushingInCell(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(), model.getBoard().getPlayingBoard());
        assertEquals(cell,model.getBoard().getCell(4,2));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(1,2));
        cell = minotaur.pushingInCell(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(), model.getBoard().getPlayingBoard());
        assertEquals(cell,model.getBoard().getCell(0,2));
        worker1.clear();
        worker2.clear();

        //tested along y
        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(2,3));
        cell = minotaur.pushingInCell(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(), model.getBoard().getPlayingBoard());
        assertEquals(cell,model.getBoard().getCell(2,4));
        worker1.clear();
        worker2.clear();

        worker1.setWorkerPosition(model.getBoard().getCell(2, 2));
        worker2.setWorkerPosition(model.getBoard().getCell(2,1));
        cell = minotaur.pushingInCell(worker1.getWorkerPosition(),
                worker2.getWorkerPosition(), model.getBoard().getPlayingBoard());
        assertEquals(cell,model.getBoard().getCell(2,0));
        worker1.clear();
        worker2.clear();
    }

    @Test
    void pushWorkerPosition() {
        Model model = new Model();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();

        worker1.setWorkerPosition(model.getBoard().getCell(2,2));
        worker2.setWorkerPosition(model.getBoard().getCell(2,3));
        PlayerMove move = new PlayerMove(worker1,2,3);
        minotaur.pushWorkerPosition(move, model);
        assertFalse(model.getBoard().getCell(2, 4).isFree());
        assertTrue(model.getBoard().getCell(2, 2).isFree());

        assertEquals(model.getBoard().getCell(2, 3).getCurrWorker(),worker1);
        assertEquals(model.getBoard().getCell(2, 4).getCurrWorker(),worker2);

    }

    //true test with a worker of different player
    @Test
    void hasFreeCellClosed() {
        Model model = new Model();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);

        Worker worker1 = new Worker(model.getBoard().getCell(0,0),1, player1.getColor());
        Worker worker2 = new Worker(model.getBoard().getCell(1,1),1, player2.getColor());
        player1.setWorker1(worker1);
        player2.setWorker1(worker2);
        Cell cell1 = model.getBoard().getCell(1,0);
        Cell cell2 = model.getBoard().getCell(0,1);
        cell1.setFreeSpace(false);
        cell2.setFreeSpace(false);
        assertTrue(minotaur.hasFreeCellClosed(model.getBoard().getCell(0,0),
                model.getBoard().getPlayingBoard()));
    }

    //false test with a worker of the same player
    @Test
    void notHasFreeCellClosedForSameWorker() {
        Model model = new Model();
        Player player1 = new Player();
        player1.setColor(PlayerColor.BLUE);

        Worker worker1 = new Worker(model.getBoard().getCell(0,0),1, player1.getColor());
        Worker worker2 = new Worker(model.getBoard().getCell(1,1),2, player1.getColor());
        player1.setWorker1(worker1);
        player1.setWorker2(worker2);
        Cell cell1 = model.getBoard().getCell(1,0);
        Cell cell2 = model.getBoard().getCell(0,1);
        cell1.setFreeSpace(false);
        cell2.setFreeSpace(false);
        assertFalse(minotaur.hasFreeCellClosed(model.getBoard().getCell(0,0),
                model.getBoard().getPlayingBoard()));

    }

    //false test because of the !minotaurMoveAllowed
    @Test
    void notHasFreeCellClosedForInvalidMove() {
        Model model = new Model();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);

        Worker worker1 = new Worker(model.getBoard().getCell(0,1),1, player1.getColor());
        Worker worker2 = new Worker(model.getBoard().getCell(1,1),1, player2.getColor());
        player1.setWorker1(worker1);
        player2.setWorker1(worker2);
        Cell cell1 = model.getBoard().getCell(0,0);
        Cell cell2 = model.getBoard().getCell(0,2);
        Cell cell3 = model.getBoard().getCell(1,0);
        Cell cell4 = model.getBoard().getCell(1,2);
        Cell cell5 = model.getBoard().getCell(2,0);
        Cell cell6 = model.getBoard().getCell(2,1);
        Cell cell7 = model.getBoard().getCell(2,2);
        cell1.setFreeSpace(false);
        cell2.setFreeSpace(false);
        cell3.setFreeSpace(false);
        cell4.setFreeSpace(false);
        cell5.setFreeSpace(false);
        cell6.setFreeSpace(false);
        cell7.setFreeSpace(false);
        assertFalse(minotaur.hasFreeCellClosed(model.getBoard().getCell(1,1),
                model.getBoard().getPlayingBoard()));
    }

    //false test. Tested only for !minotaurMoveAllowed because already tested for Apollo
    @Test
    void isEmptyCell() {
        Model model = new Model();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);

        Worker worker1 = new Worker(model.getBoard().getCell(1,1),1,PlayerColor.BLUE);
        Worker worker2 = new Worker(model.getBoard().getCell(0,1),1,PlayerColor.GREY);
        player1.setWorker1(worker1);
        player2.setWorker1(worker2);
        PlayerMove move = new PlayerMove(player2,player2.getWorker1(),0,1);
        assertFalse(minotaur.isEmptyCell(move,model));
    }
}