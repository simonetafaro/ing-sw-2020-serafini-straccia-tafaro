package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTurnTest {

    private Player player = new Player();
    private PlayerTurn playerTurn = new PlayerTurn(player);

        @Test
        void getTurnPlayer() {
            assertEquals(playerTurn.getTurnPlayer(), player);
        }

        @Test
        void getI() {
            playerTurn.resetStep();
            assertEquals(playerTurn.getI(), 1);
            playerTurn.updateStep();
            assertEquals(playerTurn.getI(), 2);
        }

        @Test
        void updateStep() {
            playerTurn.resetStep();
            playerTurn.updateStep();
            assertEquals(playerTurn.getI(), 2);
        }

        @Test
        void resetStep() {
            playerTurn.resetStep();
            assertEquals(playerTurn.getI(),1);
        }

        @Test
        void isFirstStep() {
            assertEquals(playerTurn.isFirstStep(), true);
        }

        @Test
        void setTurnWorker() {
            Worker worker = new Worker();
            playerTurn.setTurnWorker(worker);
            assertEquals(playerTurn.getTurnWorker(), worker);
        }

        @Test
        void getTurnWorker() {
            Worker worker = new Worker();
            playerTurn.setTurnWorker(worker);
            assertEquals(playerTurn.getTurnWorker(), worker);
        }



}