package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {

    @Test
    void getPlayerTurn() {
        Player player1 = new Player();
        Player player2 = new Player();

        PlayerTurn playerTurn1 = player1.setMyTurn();
        PlayerTurn playerTurn2 = player2.setMyTurn();

        Turn turn = new Turn(playerTurn1,playerTurn2);

        assertEquals(turn.getPlayerTurn(player1),playerTurn1);
        assertEquals(turn.getPlayerTurn(player2),playerTurn2);

        Player player3 = new Player();
        Player player4 = new Player();
        Player player5 = new Player();

        PlayerTurn playerTurn3 = player3.setMyTurn();
        PlayerTurn playerTurn4 = player4.setMyTurn();
        PlayerTurn playerTurn5 = player5.setMyTurn();

        turn = new Turn(playerTurn3,playerTurn4,playerTurn5);

        assertEquals(turn.getPlayerTurn(player3),playerTurn3);
        assertEquals(turn.getPlayerTurn(player4),playerTurn4);
        assertEquals(turn.getPlayerTurn(player5),playerTurn5);
    }
}