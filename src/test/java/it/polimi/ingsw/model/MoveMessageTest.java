package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoveMessageTest {

    @Test
    void getPlayer() {
        Player player = new Player();
        Board board = new Board();
        MoveMessage message = new MoveMessage(board,player, false, PlayerColor.BLUE);

        assertEquals(message.getPlayer(),player);
    }

    @Test
    void getBoard() {
        Player player = new Player();
        Board board = new Board();
        MoveMessage message = new MoveMessage(board,player, false, PlayerColor.BLUE);

        assertEquals(message.getBoard(),board);
    }

    @Test
    void getNextTurn() {
        Player player = new Player();
        Board board = new Board();
        MoveMessage message = new MoveMessage(board,player, false, PlayerColor.BLUE);

        assertEquals(message.getNextTurn(),PlayerColor.BLUE);

        message = new MoveMessage(board,player, false, PlayerColor.GREY);
        assertEquals(message.getNextTurn(),PlayerColor.GREY);
        message = new MoveMessage(board,player, false, PlayerColor.WHITE);
        assertEquals(message.getNextTurn(),PlayerColor.WHITE);

    }

    @Test
    void isHasWon() {
        Player player = new Player();
        Board board = new Board();
        MoveMessage message = new MoveMessage(board,player, false, PlayerColor.BLUE);

        assertFalse(message.isHasWon());

        message = new MoveMessage(board,player, true, PlayerColor.BLUE);
        assertTrue(message.isHasWon());
    }
}