package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class PanRuleDecoratorTest {

    private Worker worker =new Worker();
    private Cell cell =new Cell(1,1);
    private PlayerMove playerMove=new PlayerMove(worker,1,2);
    private Model model= new Model();
    private PanRuleDecorator panRuleDecorator = new PanRuleDecorator();
    private Player player=new Player();
    private PlayerTurn playerTurn= new PlayerTurn(player);
    private Turn turn = new Turn(playerTurn);
    @Test
    void move() {
        Worker worker= new Worker(model.getBoard().getCell(0,0),1, PlayerColor.BLUE);
        PlayerMove playermove=new PlayerMove(player,worker,1,1);
        playermove.setMoveOrBuild("M");
        panRuleDecorator.move(playermove,model,turn);
        Assertions.assertTrue(model.getBoard().getCell(0,0).isFree());
        Assertions.assertFalse(model.getBoard().getCell(1,1).isFree());
    }
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