package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HestiaRuleDecoratorTest {
    HestiaRuleDecorator hestiaRuleDecorator=new HestiaRuleDecorator();
    private ZeusRuleDecorator ZeusRuleDecorator = new ZeusRuleDecorator();
    private Model model=new Model();
    private Worker worker= new Worker();
    private Player player=new Player();

    @Test
    void checkPerimeterSpace() {
        PlayerMove playermove=new PlayerMove(player,worker,1,1);
        assertFalse(hestiaRuleDecorator.checkPerimeterSpace(playermove,model));
        PlayerMove move=new PlayerMove(player,worker,0,1);
        assertTrue(hestiaRuleDecorator.checkPerimeterSpace(move,model));

    }
}