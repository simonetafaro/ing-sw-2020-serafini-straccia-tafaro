package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class GameOverMessageTest {

    @Test
    void isGameOver() {
        Player player = new Player();
        Board board = new Board();
        GameOverMessage gameOverMessage = new GameOverMessage(player,board);
        assertTrue(gameOverMessage.isGameOver());
    }
}