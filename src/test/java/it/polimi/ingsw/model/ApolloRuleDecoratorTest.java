package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class ApolloRuleDecoratorTest {

    private ApolloRuleDecorator apollo = new ApolloRuleDecorator();
    private Player player=new Player();
    private Model model= new Model();
    private Board board=model.getBoard();
    private PlayerTurn playerTurn= new PlayerTurn(player);
    private Turn turn = new Turn(playerTurn);
    private Worker worker= new Worker(model.getBoard().getCell(0,0),1, PlayerColor.BLUE);
    private Worker worker1= new Worker(model.getBoard().getCell(0,2),2, PlayerColor.BLUE);
    private PlayerMove playermove=new PlayerMove(player,worker,1,1);
    @Test
    void playM_B_END() {
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY,PlayerColor.WHITE);
        playermove.setMoveOrBuild("M");
        player.setMyCard("Apollo");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        apollo.play(playermove,turn,model);
        Assertions.assertTrue(model.getBoard().getCell(0,0).isFree());
        Assertions.assertFalse(model.getBoard().getCell(1,1).isFree());
        PlayerMove move=new PlayerMove(player,worker,1,2);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        apollo.play(move,turn,model);
        assertEquals(0, model.getBoard().getCell(1, 1).getLevel());
        assertEquals(1, model.getBoard().getCell(1, 2).getLevel());
        PlayerMoveEnd moveend=new PlayerMoveEnd(player,true);
        //move.setColor(PlayerColor.BLUE);
        apollo.play(moveend,turn,model);
        assertFalse(move.getPlayer().getMyCard().isUsingCard());
    }

    @Test
    void playWrongStepB() {
        playermove.setMoveOrBuild("B");
        playermove.setColor(PlayerColor.BLUE);
        player.setMyCard("Apollo");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        apollo.play(playermove,turn,model);
        assertEquals(0, model.getBoard().getCell(0, 0).getLevel());
        assertEquals(0, model.getBoard().getCell(1, 1).getLevel());
    }

    @Test
    void playNotEndAllowed() {
        player.setMyCard("Apollo");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        PlayerMoveEnd playerMoveEnd = new PlayerMoveEnd(player,true);
        playerMoveEnd.setColor(PlayerColor.BLUE);
        apollo.play(playerMoveEnd,turn,model);

    }
    @Test
    void playOutOfBoard() {
        PlayerMove move =new PlayerMove(player,worker,1,5);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Apollo");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        apollo.play(move,turn,model);
        assertFalse(model.getBoard().getCell(0,0).isFree());

    }
    @Test
    void playWrongBuild() {
        playermove.setMoveOrBuild("M");
        player.setMyCard("Apollo");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        apollo.play(playermove, turn, model);
        assertTrue(model.getBoard().getCell(0, 0).isFree());
        assertFalse(model.getBoard().getCell(1, 1).isFree());
        PlayerMove move = new PlayerMove(player, worker1, 1, 2);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        apollo.play(move, turn, model);
        assertEquals(0, model.getBoard().getCell(1, 2).getLevel());
    }

    @Test
    void playNotEmptyCell() {
        model.getBoard().getCell(0,1).setLevel(4);
        PlayerMove move =new PlayerMove(player,worker,0,1);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Apollo");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        apollo.play(move, turn, model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
    }

    @Test
    void playNotReachableCell() {
        PlayerMove move =new PlayerMove(player,worker,3,3);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Apollo");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        apollo.play(move, turn, model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
        assertTrue(model.getBoard().getCell(3,3).isFree());
    }

    @Test
    void playNotDifferenceAllowCell() {
        model.getBoard().getCell(1,1).setLevel(2);
        playermove.setMoveOrBuild("M");
        playermove.setColor(PlayerColor.BLUE);
        player.setMyCard("Apollo");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        apollo.play(playermove, turn, model);
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
        player.setMyCard("Apollo");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        apollo.play(playermove,turn,model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
        assertTrue(model.getBoard().getCell(1,1).isFree());
        PlayerMove move=new PlayerMove(player,worker1,1,2);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        apollo.play(move,turn,model);
        assertTrue(model.getBoard().getCell(0,2).isFree());
        assertTrue(model.getBoard().getCell(1,2).isFree());
    }

    @Test
    void playB_NotFreeCell() {
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY,PlayerColor.WHITE);
        playermove.setMoveOrBuild("M");
        player.setMyCard("Apollo");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        apollo.play(playermove,turn,model);
        assertTrue(model.getBoard().getCell(0,0).isFree());
        assertFalse(model.getBoard().getCell(1,1).isFree());
        PlayerMove move=new PlayerMove(player,worker,0,2);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        apollo.play(move,turn,model);
        assertEquals(0, model.getBoard().getCell(0, 2).getLevel());
    }
    @Test
    void playM_CannotBuild() {
        Player player1=new Player();
        Worker worker2= new Worker(model.getBoard().getCell(1,1),1, PlayerColor.BLUE);
        Worker worker3= new Worker(model.getBoard().getCell(3,2),2, PlayerColor.BLUE);
        PlayerTurn playerTurn1= new PlayerTurn(player1);
        Turn turn = new Turn(playerTurn,playerTurn1);
        PlayerMove move=new PlayerMove(player1,worker2,0,0);
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY,PlayerColor.WHITE);
        model.getBoard().getCell(1,0).setLevel(4);
        model.getBoard().getCell(0,1).setLevel(4);
        player1.setMyCard("Apollo");
        player1.setWorker1(worker2);
        player1.setWorker2(worker3);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Apollo");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        apollo.play(move,turn,model);
        assertEquals(player.getWorker1(), model.getBoard().getCell(0, 0).getCurrWorker());
        assertEquals(player1.getWorker1(), model.getBoard().getCell(1, 1).getCurrWorker());
    }
    @Test
    void switchWorkerPosition(){
        Worker worker1 = new Worker(board.getCell(0,0),1, PlayerColor.BLUE);
        Worker worker2 = new Worker(board.getCell(0,1),1, PlayerColor.GREY);
        player.setWorker1(worker1);
        PlayerMove move = new PlayerMove(player.getWorker1(),0,1);
        apollo.switchWorkerPosition(move,model);
        assertEquals(board.getCell(0,0).getCurrWorker(),worker2);
        assertEquals(board.getCell(0,1).getCurrWorker(),worker1);
    }

    @Test
    void hasFreeCellClosedMyWorker(){
        Cell cell =board.getCell(0,0);
        Worker worker =new Worker(board.getCell(0,0),1, PlayerColor.BLUE);
        Worker worker1 =new Worker(board.getCell(0,1),1, PlayerColor.BLUE);
        player.setWorker1(worker);
        player.setWorker2(worker1);
        worker1.setWorkerPosition(board.getCell(1,0));
        worker.setWorkerPosition(board.getCell(0,0));
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        board.getPlayingBoard()[0][1].setFreeSpace(false);
        assertFalse(apollo.hasFreeCellClosed(cell,model.getBoard().getPlayingBoard()));
     }

     @Test
    void hasFreeCellClosedLevel(){
        Board board=model.getBoard();
        Cell cell1 =board.getCell(1,0);
        Cell cell =board.getCell(0,0);
        Cell cell2 =board.getCell(0,1);
        Cell cell3 =board.getCell(1,1);
        cell.setLevel(0);
        cell1.setLevel(4);
        cell2.setLevel(3);
        cell3.setLevel(2);
        Worker worker =new Worker(cell,1, PlayerColor.BLUE);
        assertFalse(apollo.hasFreeCellClosed(worker.getWorkerPosition(),model.getBoard().getPlayingBoard()));

    }

    @Test
    void hasFreeCellClosedNotMyWorker(){
        Board board=model.getBoard();
        Cell cell =board.getCell(0,0);
        Worker worker =new Worker(board.getCell(0,0),1, PlayerColor.BLUE);
        player.setWorker1(worker);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        assertTrue(apollo.hasFreeCellClosed(cell,model.getBoard().getPlayingBoard()));

    }

    @Test
    void hasFreeCellClosedNotMyWorkerLevel(){
        Board board=model.getBoard();
        Cell cell = board.getCell(0,0);
        Cell cell1 = board.getCell(0,1);
        Worker worker = new Worker(board.getCell(0,0),1, PlayerColor.BLUE);
        cell1.setLevel(2);
        cell.setLevel(0);
        player.setWorker1(worker);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        assertFalse(apollo.hasFreeCellClosed(cell,model.getBoard().getPlayingBoard()));
    }

    @Test
    void canBuildFromCellTo(){
        Model model = new Model();
        Cell cell1 = model.getBoard().getCell(0,0);
        Cell cell2 = model.getBoard().getCell(1,1);
        cell2.setLevel(3);
        Worker worker = new Worker();
        worker.setWorkerPosition(cell1);
        PlayerMove move = new PlayerMove(worker,1,1);
        assertTrue(apollo.canBuildFromCellTo(move,model));
    }

    //canBuildFromCellTo tested with no cell free closed to the one in which i want to move
    @Test
    void notCanBuildFromCellTo(){
        Model model = new Model();
        Worker worker1 = new Worker(model.getBoard().getCell(1,1),1,PlayerColor.BLUE);
        Cell cell1 = model.getBoard().getCell(0,0);
        Cell cell2 = model.getBoard().getCell(0,1);
        Cell cell3 = model.getBoard().getCell(1,0);
        cell1.setFreeSpace(false);
        cell2.setFreeSpace(false);
        cell3.setFreeSpace(false);
        PlayerMove move = new PlayerMove(worker1,0,0);
        assertFalse(apollo.canBuildFromCellTo(move,model));
    }

    //isEmptyCell tested with cell, where i want to move, occupied by worker of the player that is doing the move
    @Test
    void notIsEmptyCellForSameWorker(){
        Model model = new Model();
        Player player = new Player();
        player.setColor(PlayerColor.BLUE);
        Worker worker1 = new Worker(model.getBoard().getCell(0,0),1,PlayerColor.BLUE);
        Worker worker2 = new Worker(model.getBoard().getCell(1,1),2,PlayerColor.BLUE);
        player.setWorker1(worker1);
        player.setWorker2(worker2);
        PlayerMove move = new PlayerMove(player,player.getWorker1(),1,1);
        assertFalse(apollo.isEmptyCell(move,model));
    }

    //isEmptyCell tested with cell, where i want to move, occupied by worker of a different player
    @Test
    void isEmptyCell(){
        Model model = new Model();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);

        Worker worker1 = new Worker(model.getBoard().getCell(0,0),1,PlayerColor.BLUE);
        Worker worker2 = new Worker(model.getBoard().getCell(1,1),1,PlayerColor.GREY);
        player1.setWorker1(worker1);
        player2.setWorker1(worker2);
        PlayerMove move = new PlayerMove(player1,player1.getWorker1(),1,1);
        assertTrue(apollo.isEmptyCell(move,model));
    }

    @Test
    void move() {
        Model model = new Model();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);

        Worker worker1 = new Worker(model.getBoard().getCell(0,0),1,PlayerColor.BLUE);
        Worker worker2 = new Worker(model.getBoard().getCell(1,1),1,PlayerColor.GREY);
        player1.setWorker1(worker1);
        player2.setWorker1(worker2);
        PlayerMove move = new PlayerMove(player1,player1.getWorker1(),1,1);
        Turn turn = new Turn(player1.setMyTurn(),player2.setMyTurn());

        apollo.move(move, model, turn);

        //switchWorkerPosition test
        assertTrue(model.getBoard().getCell(1,1).equals(worker1.getWorkerPosition()));
        assertTrue(model.getBoard().getCell(0,0).equals(worker2.getWorkerPosition()));

        move = new PlayerMove(player1,player1.getWorker1(),1,0);
        apollo.move(move, model, turn);

        //standard move test
        assertTrue(model.getBoard().getCell(1,0).equals(worker1.getWorkerPosition()));
        assertTrue(model.getBoard().getCell(1,1).isFree());
    }
}