package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardManagerTest {

    @Test
    void setCardFromFile() {
        CardManager cardManager = new CardManager();
        Player player = new Player();
        String cardName = "Apollo";
        assertNull(player.getMyCard());
        cardManager.setCardFromFile(player, cardName);
        assertEquals(player.getMyCard().getName(),"Apollo");
    }
    @Test
    void setCardFromFileException() {
        CardManager cardManager = new CardManager();
        Player player = new Player();
        String cardName = "WrongName";
        assertNull(player.getMyCard());
        cardManager.setCardFromFile(player, cardName);
        assertNull(player.getMyCard());
    }
}