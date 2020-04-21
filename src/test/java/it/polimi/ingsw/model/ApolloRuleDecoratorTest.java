package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
class ApolloRuleDecoratorTest {

    Player player=new Player();
    Player player1=new Player();
    View view=new RemoteView(player);
    Cell cell1 =new Cell(0,1);
    Model model= new Model();
    Board board=model.getBoard();

    @Test
    void switchWorkerPosition(){
        Worker worker =new Worker(board.getCell(0,0),1, PlayerColor.BLUE);
        Worker worker1 =new Worker(board.getCell(0,1),1, PlayerColor.GREY);
        player.setWorker1(worker);
        PlayerMove move =new PlayerMove(player.getWorker1(),0,1);
        ApolloRuleDecorator.switchWorkerPosition(move,model);
        assertEquals(board.getCell(0,0).getCurrWorker(),worker1);
        assertEquals(board.getCell(0,1).getCurrWorker(),worker);

}
    @Test
    void hasfreeCellClosedMyWorker(){
        Cell cell =board.getCell(0,0);
         Worker worker =new Worker(board.getCell(0,0),1, PlayerColor.BLUE);
         Worker worker1 =new Worker(board.getCell(0,1),1, PlayerColor.BLUE);
        player.setWorker1(worker);
        player.setWorker2(worker1);
        worker1.setWorkerPosition(board.getCell(1,0));
        worker.setWorkerPosition(board.getCell(0,0));
         board.getPlayingBoard()[1][1].setFreeSpace(false);
         board.getPlayingBoard()[0][1].setFreeSpace(false);
         assertFalse(ApolloRuleDecorator.hasFreeCellClosed(cell,model.getBoard().getPlayingBoard()));

     }
     @Test
    void hasfreeCellClosedLevel(){
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
        assertFalse(ApolloRuleDecorator.hasFreeCellClosed(worker.getWorkerPosition(),model.getBoard().getPlayingBoard()));

    }
    @Test
    void hasfreeCellClosedNotMyWorker(){
        Board board=model.getBoard();
        Cell cell =board.getCell(0,0);
        Worker worker =new Worker(board.getCell(0,0),1, PlayerColor.BLUE);
        Worker worker1 =new Worker(board.getCell(0,1),1, PlayerColor.GREY);
        player.setWorker1(worker);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        assertTrue(ApolloRuleDecorator.hasFreeCellClosed(cell,model.getBoard().getPlayingBoard()));

    }
    @Test
    void hasfreeCellClosedNotMyWorkerLevel(){
        Board board=model.getBoard();
        Cell cell =board.getCell(0,0);
        Cell cell1 =board.getCell(0,1);
        Worker worker =new Worker(board.getCell(0,0),1, PlayerColor.BLUE);
        Worker worker1 =new Worker(cell1,1, PlayerColor.GREY);
        cell1.setLevel(2);
        cell.setLevel(0);
        player.setWorker1(worker);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        assertFalse(ApolloRuleDecorator.hasFreeCellClosed(cell,model.getBoard().getPlayingBoard()));
    }
}