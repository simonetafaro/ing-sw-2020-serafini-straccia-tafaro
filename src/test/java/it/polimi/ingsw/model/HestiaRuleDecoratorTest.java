package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HestiaRuleDecoratorTest {
    HestiaRuleDecorator hestiaRuleDecorator=new HestiaRuleDecorator();
    private ZeusRuleDecorator ZeusRuleDecorator = new ZeusRuleDecorator();
    private Model model=new Model();
    private Worker worker= new Worker();
    private Player player=new Player();
    private PlayerTurn playerTurn= new PlayerTurn(player);
    private Turn turn = new Turn(playerTurn);

    @Test
    void build() {
        Worker worker= new Worker(model.getBoard().getCell(0,0),1, PlayerColor.BLUE);
        PlayerMove playermove=new PlayerMove(player,worker,1,1);
        playermove.setMoveOrBuild("B");
        hestiaRuleDecorator.build(playermove,model,turn);
        assertEquals(0, model.getBoard().getCell(0, 0).getLevel());
        assertEquals(1, model.getBoard().getCell(1, 1).getLevel());

    }

    @Test
    void secondBuild() {
        Worker worker= new Worker(model.getBoard().getCell(0,0),1, PlayerColor.BLUE);
        PlayerMove playermove=new PlayerMove(player,worker,1,0);
        playermove.setMoveOrBuild("M");
        playermove.setColor(PlayerColor.BLUE);
        player.setMyCard("Hestia");
        hestiaRuleDecorator.move(playermove, model, turn);
        PlayerMove moveB1=new PlayerMove(player,worker,1,1);
        moveB1.setMoveOrBuild("B");
        hestiaRuleDecorator.build(moveB1,model,turn);
        assertEquals(0, model.getBoard().getCell(0, 0).getLevel());
        assertEquals(1, model.getBoard().getCell(1, 1).getLevel());
        PlayerMove move=new PlayerMove(player,worker,1,1);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        hestiaRuleDecorator.build(move,model,turn);
        assertEquals(0, model.getBoard().getCell(0, 0).getLevel());
        assertEquals(2, model.getBoard().getCell(1, 1).getLevel());

    }
    @Test
    void secondBuildWrong() {
        Worker worker= new Worker(model.getBoard().getCell(0,0),1, PlayerColor.BLUE);
        PlayerMove playermove=new PlayerMove(player,worker,1,0);
        playermove.setMoveOrBuild("M");
        playermove.setColor(PlayerColor.BLUE);
        player.setMyCard("Hestia");
        hestiaRuleDecorator.move(playermove, model, turn);
        PlayerMove moveB1=new PlayerMove(player,worker,1,1);
        moveB1.setMoveOrBuild("B");
        hestiaRuleDecorator.build(moveB1,model,turn);
        assertEquals(0, model.getBoard().getCell(0, 0).getLevel());
        assertEquals(1, model.getBoard().getCell(1, 1).getLevel());
        PlayerMove move=new PlayerMove(player,worker,0,1);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        hestiaRuleDecorator.build(move,model,turn);
        assertEquals(0, model.getBoard().getCell(0, 0).getLevel());
        assertEquals(0, model.getBoard().getCell(0, 1).getLevel());
        assertEquals(1, model.getBoard().getCell(1, 1).getLevel());

    }
    @Test
    void checkPerimeterSpace() {
        PlayerMove playermove=new PlayerMove(player,worker,1,1);
        assertFalse(hestiaRuleDecorator.checkPerimeterSpace(playermove,model));
        PlayerMove move=new PlayerMove(player,worker,0,1);
        assertTrue(hestiaRuleDecorator.checkPerimeterSpace(move,model));

    }
}