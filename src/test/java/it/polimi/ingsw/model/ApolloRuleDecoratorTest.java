package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class ApolloRuleDecoratorTest {

    private ApolloRuleDecorator apollo = new ApolloRuleDecorator();
    private Player player=new Player();
    private Model model= new Model();
    private Board board=model.getBoard();

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
        Worker worker1 =new Worker(board.getCell(0,1),1, PlayerColor.GREY);
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
        Worker worker1 = new Worker(cell1,1, PlayerColor.GREY);
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