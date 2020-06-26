package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerMoveTest {

    private Player player = new Player();
    private PlayerMove move = new PlayerMove(player);


    @Test
    void setMoveOrBuild() {
        move.setMoveOrBuild("M");
        assertNotEquals("B", move.getMoveOrBuild());
        assertEquals("M", move.getMoveOrBuild());

        move.setMoveOrBuild("B");
        assertNotEquals("M", move.getMoveOrBuild());
        assertEquals("B", move.getMoveOrBuild());

        move.setMoveOrBuild("END");
        assertNotEquals("B", move.getMoveOrBuild());
        assertEquals("END", move.getMoveOrBuild());

        move.setMoveOrBuild("D");
        assertNotEquals("END", move.getMoveOrBuild());
        assertEquals("D", move.getMoveOrBuild());
    }

    @Test
    void setWorker() {
        Worker worker = new Worker();
        move.setWorker(worker);
        assertEquals(move.getWorker(),worker);
    }

    @Test
    void setRow() {
        int row = 0;
        move.setRow(row);
        assertEquals(move.getRow(),row);
        assertNotEquals(move.getRow(),1);
    }

    @Test
    void setColumn() {
        int column = 0;
        move.setColumn(column);
        assertEquals(move.getColumn(),column);
        assertNotEquals(move.getColumn(),1);
    }

    @Test
    void getRow() {
        int row = 0;
        move.setRow(row);
        assertEquals(move.getRow(),row);
        assertNotEquals(move.getRow(),1);
    }

    @Test
    void getColumn() {
        int column = 0;
        move.setColumn(column);
        assertEquals(move.getColumn(),column);
        assertNotEquals(move.getColumn(),1);
    }

    @Test
    void getPlayer() {
        Player player = new Player();
        move.setPlayer(player);
        Player player1 = new Player();
        assertNotEquals(move.getPlayer(),player1);
        assertEquals(move.getPlayer(),player);
    }

    @Test
    void getWorker() {
        Worker worker = new Worker();
        move.setWorker(worker);
        assertEquals(move.getWorker(),worker);

    }

    @Test
    void getMoveOrBuild() {
        move.setMoveOrBuild("M");
        assertNotEquals("B", move.getMoveOrBuild());
        assertEquals("M", move.getMoveOrBuild());

        move.setMoveOrBuild("B");
        assertNotEquals("M", move.getMoveOrBuild());
        assertEquals("B", move.getMoveOrBuild());

        move.setMoveOrBuild("END");
        assertNotEquals("B", move.getMoveOrBuild());
        assertEquals("END", move.getMoveOrBuild());

        move.setMoveOrBuild("D");
        assertNotEquals("END", move.getMoveOrBuild());
        assertEquals("D", move.getMoveOrBuild());

    }

    @Test
    void setPlayer() {
        Player player = new Player();
        move.setPlayer(player);
        Player player1 = new Player();
        assertNotEquals(move.getPlayer(),player1);
        assertEquals(move.getPlayer(),player);
    }

    @Test
    void getColor() {
        Player player = new Player();
        player.setColor(PlayerColor.GREY);
        PlayerMove move = new PlayerMove(player);
        assertEquals(move.getColor(),PlayerColor.GREY);
        assertNotEquals(move.getColor(),PlayerColor.BLUE);
    }
}