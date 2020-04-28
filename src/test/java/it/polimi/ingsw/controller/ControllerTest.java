package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerMove;
import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;

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
}