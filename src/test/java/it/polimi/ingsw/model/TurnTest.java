package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {

    @Test
    void getPlayerTurn() {
        Player player1 = new Player();
        Player player2 = new Player();
        PlayerTurn playerTurn1 = new PlayerTurn(player1);
        PlayerTurn playerTurn2 = new PlayerTurn(player2);
        Turn turn = new Turn(playerTurn1,playerTurn2);

        assertEquals(turn.getPlayerTurn(player1.getID()),playerTurn1);
        assertEquals(turn.getPlayerTurn(player2.getID()),playerTurn2);
    }
}