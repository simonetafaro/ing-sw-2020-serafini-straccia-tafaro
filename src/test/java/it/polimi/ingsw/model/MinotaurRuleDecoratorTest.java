package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinotaurRuleDecoratorTest {

    private MinotaurRuleDecorator minotaur = new MinotaurRuleDecorator();
    private Model model=new Model();
    private Player player=new Player();
    private PlayerTurn playerTurn= new PlayerTurn(player);
    private Turn turn = new Turn(playerTurn);
    private  Worker worker= new Worker(model.getBoard().getCell(0,0),1, PlayerColor.BLUE);
    private Worker worker1= new Worker(model.getBoard().getCell(0,2),2, PlayerColor.BLUE);
    private PlayerMove playermove=new PlayerMove(player,worker,1,1);


    @Test
    void playM_B_END() {
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY,PlayerColor.WHITE);
        playermove.setMoveOrBuild("M");
        player.setMyCard("Minotaur");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        minotaur.play(playermove,turn,model);
        assertTrue(model.getBoard().getCell(0,0).isFree());
        assertFalse(model.getBoard().getCell(1,1).isFree());
        PlayerMove move=new PlayerMove(player,worker,1,2);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        minotaur.play(move,turn,model);
        assertEquals(0, model.getBoard().getCell(1, 1).getLevel());
        assertEquals(1, model.getBoard().getCell(1, 2).getLevel());
        PlayerMoveEnd moveend=new PlayerMoveEnd(player,true);
        //move.setColor(PlayerColor.BLUE);
        minotaur.play(moveend,turn,model);
        assertFalse(move.getPlayer().getMyCard().isUsingCard());
    }

    @Test
    void playWrongStepB() {
        playermove.setMoveOrBuild("B");
        playermove.setColor(PlayerColor.BLUE);
        player.setMyCard("Minotaur");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        minotaur.play(playermove,turn,model);
        assertEquals(0, model.getBoard().getCell(0, 0).getLevel());
        assertEquals(0, model.getBoard().getCell(1, 1).getLevel());
    }

    @Test
    void playNotEndAllowed() {
        player.setMyCard("Minotaur");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        PlayerMoveEnd playerMoveEnd = new PlayerMoveEnd(player,true);
        playerMoveEnd.setColor(PlayerColor.BLUE);
        minotaur.play(playerMoveEnd,turn,model);

    }
    @Test
    void playOutOfBoard() {
        PlayerMove move =new PlayerMove(player,worker,1,5);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Minotaur");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        minotaur.play(move,turn,model);
        assertFalse(model.getBoard().getCell(0,0).isFree());

    }
    @Test
    void playWrongBuild() {
        playermove.setMoveOrBuild("M");
        player.setMyCard("Minotaur");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        minotaur.play(playermove, turn, model);
        assertTrue(model.getBoard().getCell(0, 0).isFree());
        assertFalse(model.getBoard().getCell(1, 1).isFree());
        PlayerMove move = new PlayerMove(player, worker1, 1, 2);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        minotaur.play(move, turn, model);
        assertEquals(0, model.getBoard().getCell(1, 2).getLevel());
    }

    @Test
    void playNotEmptyCell() {
        model.getBoard().getCell(0,1).setLevel(4);
        PlayerMove move =new PlayerMove(player,worker,0,1);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Minotaur");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        minotaur.play(move, turn, model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
    }
    @Test
    void playNotEmptyCell_B() {
        playermove.setMoveOrBuild("M");
        player.setMyCard("Minotaur");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        minotaur.play(playermove,turn,model);
        assertTrue(model.getBoard().getCell(0,0).isFree());
        assertFalse(model.getBoard().getCell(1,1).isFree());
        PlayerMove move=new PlayerMove(player,worker,0,2);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        minotaur.play(move,turn,model);
        assertEquals(0, model.getBoard().getCell(1, 1).getLevel());
        assertEquals(0, model.getBoard().getCell(0, 2).getLevel());
    }
    @Test
    void playNotReachableCell() {
        PlayerMove move =new PlayerMove(player,worker,3,3);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Minotaur");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        minotaur.play(move, turn, model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
        assertTrue(model.getBoard().getCell(3,3).isFree());
    }

    @Test
    void playNotDifferenceAllowCell() {
        model.getBoard().getCell(1,1).setLevel(2);
        playermove.setMoveOrBuild("M");
        playermove.setColor(PlayerColor.BLUE);
        player.setMyCard("Minotaur");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        minotaur.play(playermove, turn, model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
        assertTrue(model.getBoard().getCell(1,1).isFree());
    }

    @Test
    void playStuck() {
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY,PlayerColor.WHITE);
        model.getBoard().getCell(1,0).setLevel(2);
        model.getBoard().getCell(0,1).setLevel(2);
        model.getBoard().getCell(1,1).setLevel(2);
        model.getBoard().getCell(1,2).setLevel(2);
        model.getBoard().getCell(1,3).setLevel(2);
        model.getBoard().getCell(0,3).setLevel(2);
        playermove.setMoveOrBuild("M");
        playermove.setColor(PlayerColor.BLUE);
        player.setMyCard("Minotaur");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        minotaur.play(playermove,turn,model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
        assertTrue(model.getBoard().getCell(1,1).isFree());
        PlayerMove move=new PlayerMove(player,worker1,1,2);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        minotaur.play(move,turn,model);
        assertTrue(model.getBoard().getCell(0,2).isFree());
        assertTrue(model.getBoard().getCell(1,2).isFree());
    }
    @Test
    void playM_Switch() {
        Player player1=new Player();
        Worker worker2= new Worker(model.getBoard().getCell(1,1),1, PlayerColor.BLUE);
        Worker worker3= new Worker(model.getBoard().getCell(3,2),2, PlayerColor.BLUE);
        PlayerTurn playerTurn1= new PlayerTurn(player1);
        Turn turn = new Turn(playerTurn,playerTurn1);
        PlayerMove move=new PlayerMove(player,worker,1,1);
        player1.setMyCard("Minotaur");
        player1.setWorker1(worker2);
        player1.setWorker2(worker3);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Minotaur");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        minotaur.play(move,turn,model);
        assertEquals(player1.getWorker1(), model.getBoard().getCell(2, 2).getCurrWorker());
        assertEquals(player.getWorker1(), model.getBoard().getCell(1, 1).getCurrWorker());
    }

    @Test
    void playM_SwitchNotAllowed() {
        Player player1=new Player();
        Worker worker2= new Worker(model.getBoard().getCell(1,1),1, PlayerColor.BLUE);
        Worker worker3= new Worker(model.getBoard().getCell(3,2),2, PlayerColor.BLUE);
        PlayerTurn playerTurn1= new PlayerTurn(player1);
        Turn turn = new Turn(playerTurn,playerTurn1);
        PlayerMove move=new PlayerMove(player1,worker2,0,0);
        player1.setMyCard("Minotaur");
        player1.setWorker1(worker2);
        player1.setWorker2(worker3);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Minotaur");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        minotaur.play(move,turn,model);
        assertEquals(player1.getWorker1(), model.getBoard().getCell(1, 1).getCurrWorker());
        assertEquals(player.getWorker1(), model.getBoard().getCell(0, 0).getCurrWorker());
    }
    @Test
    void move() {
        Worker worker= new Worker(model.getBoard().getCell(0,0),1, PlayerColor.BLUE);
        PlayerMove playermove=new PlayerMove(player,worker,1,1);
        playermove.setMoveOrBuild("M");
        minotaur.move(playermove,model,turn);
        assertTrue(model.getBoard().getCell(0,0).isFree());
        assertFalse(model.getBoard().getCell(1,1).isFree());
    }

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