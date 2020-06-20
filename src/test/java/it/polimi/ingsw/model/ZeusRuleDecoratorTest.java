package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZeusRuleDecoratorTest {
    private ZeusRuleDecorator ZeusRuleDecorator = new ZeusRuleDecorator();
    private Model model=new Model();
    private Worker worker= new Worker();
    private Player player=new Player();
    private PlayerMove playermove=new PlayerMove(player,worker,1,1);
    private PlayerTurn playerTurn= new PlayerTurn(player);
    private Turn turn = new Turn(playerTurn);
    @Test
    void zeusbuild() {
        turn.getPlayerTurn(player).getStepI(1).setCellTo(model.getBoard().getCell(1,1));
        assertTrue(ZeusRuleDecorator.Zeusbuild(playermove,model,turn));
        turn.getPlayerTurn(player).getStepI(1).setCellTo(model.getBoard().getCell(0,1));
        assertFalse(ZeusRuleDecorator.Zeusbuild(playermove,model,turn));
    }
    //return model.getBoard().getCell(move.getRow(), move.getColumn()).equals(turn.getPlayerTurn(move.getPlayer()).getStepI(1).getCellTo());
}