package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class PanRuleDecoratorTest {

    private Worker worker =new Worker();
    private Cell cell =new Cell(1,1);
    private PlayerMove playerMove=new PlayerMove(worker,1,2);
    private Model model= new Model();
    private PanRuleDecorator panRuleDecorator = new PanRuleDecorator();


    @Test
    void panWinCondition() {
        worker.setWorkerPosition(cell);
        model.getBoard().getCell(playerMove.getRow(),playerMove.getColumn()).setLevel(0);
        playerMove.getWorker().getWorkerPosition().setLevel(2);
        assertTrue(panRuleDecorator.panWinCondition(playerMove,model));
        model.getBoard().getCell(playerMove.getRow(),playerMove.getColumn()).setLevel(1);
        playerMove.getWorker().getWorkerPosition().setLevel(3);
        assertTrue(panRuleDecorator.panWinCondition(playerMove,model));
        model.getBoard().getCell(playerMove.getRow(),playerMove.getColumn()).setLevel(1);
        playerMove.getWorker().getWorkerPosition().setLevel(2);
        assertFalse(panRuleDecorator.panWinCondition(playerMove,model));
    }
}