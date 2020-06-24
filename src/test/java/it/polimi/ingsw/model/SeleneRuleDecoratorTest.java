package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//necessario il test della play come atlas aspetta per vedere se questa va migliorta o meno
class SeleneRuleDecoratorTest {
    private SeleneRuleDecorator selene=new SeleneRuleDecorator();
    private Model model=new Model();
    private Worker worker= new Worker();
    private Player player=new Player();
    private PlayerMove playermove=new PlayerMove(player,worker,1,1);
    private PlayerTurn playerTurn= new PlayerTurn(player);
    private Turn turn = new Turn(playerTurn);

    @Test
    void play() {
    }


}