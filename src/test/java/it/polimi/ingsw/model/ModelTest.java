package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.SetWorkerPosition;
import org.junit.jupiter.api.Test;

import java.net.Socket;

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

    @Test
    void testSetPlayOrder2Players() {
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY);
        assertEquals(model.getTurn(),PlayerColor.BLUE);
        assertNotEquals(model.getTurn(),PlayerColor.GREY);

        model.updateTurn();
        assertEquals(model.getTurn(),PlayerColor.GREY);
        assertNotEquals(model.getTurn(),PlayerColor.BLUE);

        model.updateTurn();
        assertEquals(model.getTurn(),PlayerColor.BLUE);
        assertNotEquals(model.getTurn(),PlayerColor.GREY);
    }

    @Test
    void testSetPlayOrder3Players() {
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY, PlayerColor.WHITE);
        assertEquals(model.getTurn(),PlayerColor.BLUE);
        assertNotEquals(model.getTurn(),PlayerColor.GREY);
        assertNotEquals(model.getTurn(),PlayerColor.WHITE);

        model.updateTurn();
        assertEquals(model.getTurn(),PlayerColor.GREY);
        assertNotEquals(model.getTurn(),PlayerColor.BLUE);
        assertNotEquals(model.getTurn(),PlayerColor.WHITE);

        model.updateTurn();
        assertEquals(model.getTurn(),PlayerColor.WHITE);
        assertNotEquals(model.getTurn(),PlayerColor.BLUE);
        assertNotEquals(model.getTurn(),PlayerColor.GREY);

        model.updateTurn();
        assertEquals(model.getTurn(),PlayerColor.BLUE);
        assertNotEquals(model.getTurn(),PlayerColor.WHITE);
        assertNotEquals(model.getTurn(),PlayerColor.GREY);
    }

    @Test
    void setGoUpLevel() {
        model.setGoUpLevel(0);

        Cell cell = model.getBoard().getCell(0,0);
        cell.setLevel(2);
        Worker worker = new Worker(model.getBoard().getCell(0,1),1,PlayerColor.GREY);
        worker.getWorkerPosition().setLevel(0);
        PlayerMove move = new PlayerMove(worker,0,0);
        assertFalse(model.isLevelDifferenceAllowed(move));

        cell.setLevel(0);
        worker.getWorkerPosition().setLevel(0);
        assertTrue(model.isLevelDifferenceAllowed(move));
    }

    @Test
    void getBoard() {
        Board board = model.getBoard();
        assertEquals(model.getBoard(),board);
    }

    @Test
    void getPlayer() {
        Player player1 = new Player(1,"Apollo");
        Player player2 = new Player(2,"Pan");
        Player player3 = new Player(3,"Hephaestus");

        model.setPlayers(player1, player2, player3);
        assertEquals(model.getPlayer(1), player1);
        assertEquals(model.getPlayer(2), player2);
        assertEquals(model.getPlayer(3), player3);
        assertNotEquals(model.getPlayer(1), player3);
        assertNotEquals(model.getPlayer(2), player1);

        assertNull(model.getPlayer(4));
    }

    @Test
    void updateTurnSetupPhase() {
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY);
        assertEquals(model.getTurn(),PlayerColor.BLUE);
        assertNotEquals(model.getTurn(),PlayerColor.GREY);

        model.updateTurnSetupPhase();
        assertEquals(model.getTurn(),PlayerColor.GREY);
        assertNotEquals(model.getTurn(),PlayerColor.BLUE);

        model.updateTurnSetupPhase();
        assertEquals(model.getTurn(),PlayerColor.BLUE);
        assertNotEquals(model.getTurn(),PlayerColor.GREY);

        Model model1 = new Model();

        model1.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY, PlayerColor.WHITE);
        assertEquals(model1.getTurn(),PlayerColor.BLUE);
        assertNotEquals(model1.getTurn(),PlayerColor.GREY);
        assertNotEquals(model1.getTurn(),PlayerColor.WHITE);

        model1.updateTurnSetupPhase();
        assertEquals(model1.getTurn(),PlayerColor.GREY);
        assertNotEquals(model1.getTurn(),PlayerColor.BLUE);
        assertNotEquals(model1.getTurn(),PlayerColor.WHITE);

        model1.updateTurnSetupPhase();
        assertEquals(model1.getTurn(),PlayerColor.WHITE);
        assertNotEquals(model1.getTurn(),PlayerColor.BLUE);
        assertNotEquals(model1.getTurn(),PlayerColor.GREY);

        model1.updateTurnSetupPhase();
        assertEquals(model1.getTurn(),PlayerColor.BLUE);
        assertNotEquals(model1.getTurn(),PlayerColor.WHITE);
        assertNotEquals(model1.getTurn(),PlayerColor.GREY);
    }

    @Test
    void deletePlayerFromGame() {
        Player player1 = new Player();
        player1.setColor(PlayerColor.BLUE);
        Player player2 = new Player();
        player1.setColor(PlayerColor.GREY);

        model.setPlayOrder(player1.getColor(), player2.getColor());

        assertEquals(model.getTurn(), player1.getColor());
        model.deletePlayerFromGame(PlayerColor.BLUE);
        assertEquals(model.getTurn(), player1.getColor());
    }

    @Test
    void setStep() {
        /*
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

        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellTo(model.getBoard().getCell(move.getRow(),move.getColumn()));
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellFrom(move.getPlayer().getWorker1().getWorkerPosition());
        model.setStep(move,turn,model);

        assertEquals(turn.getPlayerTurn(move.getPlayer()).getCurrStep().getCellTo(), model.getBoard().getCell(move.getRow(), move.getColumn()));
        assertEquals(turn.getPlayerTurn(move.getPlayer()).getCurrStep().getCellFrom(), move.getPlayer().getWorker1().getWorkerPosition());

         */
    }

    @Test
    void checkStep() {
        Model model = new Model();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);

        Worker worker1 = new Worker(model.getBoard().getCell(0,0),1,player1.getColor());
        Worker worker2 = new Worker(model.getBoard().getCell(0,0),2,player1.getColor());
        player1.setWorker1(worker1);
        player1.setWorker2(worker2);

        PlayerMove move = new PlayerMove(player1,player1.getWorker1(),1,1);
        Turn turn = new Turn(player1.setMyTurn(),player2.setMyTurn());

        model.fillStepInfo(move,turn,model);
        assertFalse(move.getPlayer().getWorker2().isStuck());
        assertFalse(move.getPlayer().getWorker1().isStuck());
    }

    @Test
    void isRightWorker() {
        Model model = new Model();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);

        Worker worker1 = new Worker(model.getBoard().getCell(0,0),1,player1.getColor());
        Worker worker2 = new Worker(model.getBoard().getCell(1,1),2,player1.getColor());
        player1.setWorker1(worker1);
        player1.setWorker2(worker2);
        PlayerMove move = new PlayerMove(player1,player1.getWorker1(),1,1);
        Turn turn = new Turn(player1.setMyTurn(),player2.setMyTurn());

        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellTo(model.getBoard().getCell(move.getRow(),move.getColumn()));
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellFrom(move.getPlayer().getWorker1().getWorkerPosition());

        assertTrue(model.isRightWorker(move,turn));
        model.setFirstStep(move,turn);
        assertTrue(model.isRightWorker(move,turn));

        turn.getPlayerTurn(move.getPlayer()).updateStep();
        turn.getPlayerTurn(move.getPlayer()).setTurnWorker(worker2);
        assertFalse(model.isRightWorker(move,turn));
    }

    @Test
    void setFirstStep() {
        Model model = new Model();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);

        Worker worker1 = new Worker(model.getBoard().getCell(0,0),1,player1.getColor());
        player1.setWorker1(worker1);

        PlayerMove move = new PlayerMove(player1,player1.getWorker1(),1,1);
        Turn turn = new Turn(player1.setMyTurn(),player2.setMyTurn());

        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellTo(model.getBoard().getCell(move.getRow(),move.getColumn()));
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellFrom(move.getPlayer().getWorker1().getWorkerPosition());

        model.setFirstStep(move,turn);
        assertEquals(turn.getPlayerTurn(move.getPlayer()).getTurnWorker(), move.getWorker());
    }

    @Test
    void setPlayers() {
        Player player1 = new Player(1,"Apollo");
        Player player2 = new Player(2,"Pan");
        Player player3 = new Player(3,"Hephaestus");

        model.setPlayers(player1, player2, player3);
        assertEquals(model.getPlayer(1), player1);
        assertEquals(model.getPlayer(2), player2);
        assertEquals(model.getPlayer(3), player3);
        assertNotEquals(model.getPlayer(1), player3);
        assertNotEquals(model.getPlayer(2), player1);
    }

    @Test
    void testSetPlayers() {
        Player player1 = new Player(1,"Apollo");
        Player player2 = new Player(2,"Pan");

        model.setPlayers(player1, player2);
        assertEquals(model.getPlayer(1), player1);
        assertEquals(model.getPlayer(2), player2);
        assertNotEquals(model.getPlayer(1), player2);
        assertNotEquals(model.getPlayer(2), player1);
    }

    @Test
    void setWorkers() {
        Player player1 = new Player(1,"Apollo");
        Player player2 = new Player(2,"Pan");
        player1.setColor(PlayerColor.GREY);
        player2.setColor(PlayerColor.BLUE);

        model.setPlayers(player1, player2);
        SetWorkerPosition worker = new SetWorkerPosition(1,1,player1.getColor(),1,1);
        model.setWorkers(worker);
        assertEquals(player1.getWorker1().getPlayerColor(),player1.getColor());

        worker = new SetWorkerPosition(1,1,player1.getColor(),1,2);
        model.setWorkers(worker);
        assertEquals(player1.getWorker2().getPlayerColor(),player1.getColor());
    }

    @Test
    void getPlayerFromColor() {
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);
        player3.setColor(PlayerColor.WHITE);

        model.setPlayers(player1, player2, player3);
        assertEquals(model.getPlayerFromColor(PlayerColor.BLUE), player1);
        assertEquals(model.getPlayerFromColor(PlayerColor.GREY), player2);
        assertEquals(model.getPlayerFromColor(PlayerColor.WHITE), player3);
        assertNotEquals(model.getPlayerFromColor(PlayerColor.BLUE), player3);
        assertNotEquals(model.getPlayerFromColor(PlayerColor.WHITE), player1);

        Model model1 = new Model();
        model1.setPlayers(player1,player2);
        assertNull(model1.getPlayerFromColor(PlayerColor.WHITE));
    }

    @Test
    void endNotifyView() {
        PlayerMove move = new PlayerMove(new Player(),new Worker(),1,1);
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY);
        assertEquals(model.getTurn(),PlayerColor.BLUE);

        model.endNotifyView(move,false);
        assertEquals(model.getTurn(), PlayerColor.GREY);

        model.endNotifyView(move,false);
        assertEquals(model.getTurn(), PlayerColor.BLUE);
    }

    @Test
    void deletePlayer() {
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);
        player3.setColor(PlayerColor.WHITE);
        Worker worker = new Worker();
        player1.setWorker1(worker);
        Worker worker1 = new Worker();
        player1.setWorker2(worker1);
        worker1.setWorkerPosition(model.getBoard().getCell(2,2));
        worker.setWorkerPosition(model.getBoard().getCell(2,1));

        PlayerMove move = new PlayerMove(player1,worker ,1,1);
        model.setPlayers(player1, player2, player3);
        model.setPlayOrder(player1.getColor(),player2.getColor(),player3.getColor());

        model.deletePlayer(move);
        assertTrue(model.getBoard().getCell(worker.getWorkerPosition().getPosX(),worker.getWorkerPosition().getPosY()).isFree());
        assertNull(model.getBoard().getCell(worker.getWorkerPosition().getPosX(),worker.getWorkerPosition().getPosY()).getCurrWorker());
        assertEquals(model.getTurn(),player2.getColor());

        Model model1 = new Model();
        model1.setPlayOrder(player1.getColor(),player2.getColor());
        model1.deletePlayer(move);
    }

    @Test
    void endMessage() {
        Model model = new Model();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);

        Worker worker1 = new Worker(model.getBoard().getCell(0,0),1,player1.getColor());
        player1.setWorker1(worker1);

        PlayerMove move = new PlayerMove(player1,player1.getWorker1(),1,1);
        Turn turn = new Turn(player1.setMyTurn(),player2.setMyTurn());

        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY);
        assertEquals(model.getTurn(),PlayerColor.BLUE);

        model.endMessage(move,turn, model);
        assertEquals(model.getTurn(), PlayerColor.GREY);

        model.endMessage(move,turn, model);
        assertEquals(model.getTurn(), PlayerColor.BLUE);
    }

    @Test
    void notifySetWorker() {
        Model model = new Model();

        Player player1 = new Player(1,"Apollo");
        Player player2 = new Player(2,"Apollo");
        Player player3 = new Player(3,"Apollo");
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);
        player3.setColor(PlayerColor.WHITE);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        player3.setWorker1(worker1);
        player3.setWorker2(worker2);


        model.setPlayOrder(player1.getColor(),player2.getColor(),player3.getColor());
        model.setPlayers(player1,player2,player3);

        SetWorkerPosition worker = new SetWorkerPosition(1,1,player3.getColor(),3,1);
        model.notifySetWorker(worker);
        assertEquals(model.getTurn(),player2.getColor());

        worker1 = new Worker();
        worker2 = new Worker();
        player1.setWorker1(worker1);
        player1.setWorker2(worker2);

        worker = new SetWorkerPosition(1,1,player1.getColor(),1,1);
        model.notifySetWorker(worker);
        assertEquals(model.getTurn(),player3.getColor());

    }
}