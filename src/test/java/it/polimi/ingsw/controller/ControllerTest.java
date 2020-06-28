package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.SetWorkerPosition;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void isPlayerTurn() {
        Model model = new Model();
        Controller controller = new Controller(model);
        Player player =new Player();
        Player player1 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player.setColor(PlayerColor.GREY);
        model.setPlayOrder(player.getColor(),player1.getColor());
        assertTrue(controller.isPlayerTurn(player));
        assertFalse(controller.isPlayerTurn(player1));
    }

    @Test
    void update() {
        Model model = new Model();
        Controller controller = new Controller(model);

        Player player1 = new Player(1,"Apollo");
        Player player2 = new Player(2,"Apollo");
        Player player3 = new Player(3,"Apollo");
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);
        player3.setColor(PlayerColor.WHITE);

        model.setPlayers(player1,player2,player3);
        SetWorkerPosition worker = new SetWorkerPosition(1,1,player1.getColor(),1,1);
        controller.update(worker);
        assertNotNull(player1.getWorker1());

        SetWorkerPosition worker1 = new SetWorkerPosition(1,2 ,player2.getColor(),2,1);
        model.getBoard().getCell(1,2).setFreeSpace(false);
        controller.update(worker1);
        assertNull(player2.getWorker1());
    }

    @Test
    void updatePlayerMove() {
        Model model = new Model();
        Controller controller = new Controller(model);

        Player player1 = new Player(1,"Apollo");
        Player player2 = new Player(2,"Apollo");
        Player player3 = new Player(3,"Apollo");
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);
        player3.setColor(PlayerColor.WHITE);

        model.setPlayers(player1,player2,player3);
        Worker worker = new Worker(01, new Cell(1,1), 1, PlayerColor.BLUE);
        Worker worker2 = new Worker(01, new Cell(1,4), 2, PlayerColor.BLUE);
        player1.setWorker2(worker2);
        player1.setWorker1(worker);
        PlayerMove playerMove = new PlayerMove(player1, worker, 1,1);

        controller.update(playerMove);
    }

    @Test
    void setWorker() {
        Model model = new Model();
        Controller controller = new Controller(model);

        Player player1 = new Player(1,"Apollo");
        Player player2 = new Player(2,"Apollo");
        Player player3 = new Player(3,"Apollo");
        player1.setColor(PlayerColor.BLUE);
        player2.setColor(PlayerColor.GREY);
        player3.setColor(PlayerColor.WHITE);

        model.setPlayers(player1,player2,player3);
        SetWorkerPosition worker = new SetWorkerPosition(1,1,player1.getColor(),1,1);
        controller.setWorker(worker);
        assertNotNull(player1.getWorker1());

        SetWorkerPosition worker1 = new SetWorkerPosition(1,2 ,player2.getColor(),2,1);
        model.getBoard().getCell(1,2).setFreeSpace(false);
        controller.setWorker(worker1);
        assertNull(player2.getWorker1());
    }

    @Test
    void setWorkersMessage() {
        Model model = new Model();
        Controller controller = new Controller(model);

        controller.setWorkersMessage();

    }

    @Test
    void setTurn() {
        Model model = new Model();
        Controller controller = new Controller(model);

        Player player = new Player();
        PlayerTurn playerTurn = new PlayerTurn(player);
        Turn turn  = new Turn(playerTurn);
        controller.setTurn(turn);
        assertEquals(turn, controller.getTurn());
    }

    @Test
    void performMoveWorker1(){
        Model model = new Model();

        Player player1 = new Player();
        player1.setColor(PlayerColor.BLUE);
        player1.setMyCardMethod(new ApolloRuleDecorator());
        Player player2 = new Player();
        player2.setColor(PlayerColor.GREY);
        model.setPlayers(player1, player2);
        model.setPlayOrder(player1.getColor(), player2.getColor());
        PlayerTurn playerTurn= new PlayerTurn(player1);
        Turn turn = new Turn(playerTurn);
        CardManager cardManager = new CardManager();
        cardManager.setCardFromFile(player1, "Apollo");

        Controller controller = new Controller(model);
        controller.setTurn(turn);
        Worker worker = new Worker(01, new Cell(1,1), 1, PlayerColor.BLUE);
        Worker worker2 = new Worker(01, new Cell(1,4), 2, PlayerColor.BLUE);
        player1.setWorker2(worker2);
        player1.setWorker1(worker);
        PlayerMove playerMove = new PlayerMove(player1, worker, 1,1);
        playerMove.setMoveOrBuild("M");
        playerMove.setColor(player1.getColor());
        controller.performMove(playerMove);
        assertTrue(playerMove.getPlayer().equals(model.getPlayerFromColor(PlayerColor.BLUE)));
        assertEquals(playerMove.getPlayer().getWorker1(), model.getPlayerFromColor(PlayerColor.BLUE).getWorker1());

    }

    @Test
    void performMoveWorker2(){
        Model model = new Model();

        Player player1 = new Player();
        player1.setColor(PlayerColor.BLUE);
        Player player2 = new Player();
        player2.setColor(PlayerColor.GREY);
        model.setPlayers(player1, player2);

        Controller controller = new Controller(model);
        Worker worker = new Worker(01, new Cell(1,1), 2, PlayerColor.BLUE);
        player1.setWorker2(worker);
        PlayerMove playerMove = new PlayerMove(player1, worker, 1,1);
        controller.performMove(playerMove);
        assertTrue(playerMove.getPlayer().equals(model.getPlayerFromColor(PlayerColor.BLUE)));
        assertEquals(playerMove.getPlayer().getWorker2(), model.getPlayerFromColor(PlayerColor.BLUE).getWorker2());
    }
}