package it.polimi.ingsw.model;

import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
class PanRuleDecoratorTest {
    Player player=new Player();
    View view=new RemoteView(player);
    Worker worker =new Worker();
    Cell cell =new Cell(1,1);
    PlayerMove playerMove=new PlayerMove(worker,1,2);
    Model model= new Model();

    @Test
    void panWinCondition() {
        worker.setWorkerPosition(cell);
        model.getBoard().getCell(playerMove.getRow(),playerMove.getColumn()).setLevel(0);
        playerMove.getWorker().getWorkerPosition().setLevel(2);
        assertTrue(PanRuleDecorator.panWinCondition(playerMove,model));
        model.getBoard().getCell(playerMove.getRow(),playerMove.getColumn()).setLevel(1);
        playerMove.getWorker().getWorkerPosition().setLevel(3);
        assertTrue(PanRuleDecorator.panWinCondition(playerMove,model));
        model.getBoard().getCell(playerMove.getRow(),playerMove.getColumn()).setLevel(1);
        playerMove.getWorker().getWorkerPosition().setLevel(2);
        assertFalse(PanRuleDecorator.panWinCondition(playerMove,model));
    }
}