package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class AthenaRuleDecoratorTest {

    private AthenaRuleDecorator athenaRuleDecorator = new AthenaRuleDecorator();
    private Model model=new Model();
    private Player player=new Player();
    private PlayerTurn playerTurn= new PlayerTurn(player);
    private Turn turn = new Turn(playerTurn);
    private  Worker worker= new Worker(model.getBoard().getCell(0,0),1, PlayerColor.BLUE);
    private Worker worker1= new Worker(model.getBoard().getCell(0,2),2, PlayerColor.BLUE);
    private PlayerMove playermove=new PlayerMove(player,worker,1,1);

    @Test
    void playM_B_END() {
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY,PlayerColor.WHITE);
        playermove.setMoveOrBuild("M");
        player.setMyCard("Athena");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        athenaRuleDecorator.play(playermove,turn,model);
        assertTrue(model.getBoard().getCell(0,0).isFree());
        assertFalse(model.getBoard().getCell(1,1).isFree());
        PlayerMove move=new PlayerMove(player,worker,1,2);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        athenaRuleDecorator.play(move,turn,model);
        assertEquals(0, model.getBoard().getCell(1, 1).getLevel());
        assertEquals(1, model.getBoard().getCell(1, 2).getLevel());
        PlayerMoveEnd moveend=new PlayerMoveEnd(player,true);
        //move.setColor(PlayerColor.BLUE);
        athenaRuleDecorator.play(moveend,turn,model);
        assertFalse(move.getPlayer().getMyCard().isUsingCard());
    }

    @Test
    void playWrongStepB() {
        playermove.setMoveOrBuild("B");
        playermove.setColor(PlayerColor.BLUE);
        player.setMyCard("Athena");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        athenaRuleDecorator.play(playermove,turn,model);
        assertEquals(0, model.getBoard().getCell(0, 0).getLevel());
        assertEquals(0, model.getBoard().getCell(1, 1).getLevel());
    }

    @Test
    void playNotEndAllowed() {
        player.setMyCard("Athena");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        PlayerMoveEnd playerMoveEnd = new PlayerMoveEnd(player,true);
        playerMoveEnd.setColor(PlayerColor.BLUE);
        athenaRuleDecorator.play(playerMoveEnd,turn,model);

    }
    @Test
    void playOutOfBoard() {
        PlayerMove move =new PlayerMove(player,worker,1,5);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Athena");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        athenaRuleDecorator.play(move,turn,model);
        assertFalse(model.getBoard().getCell(0,0).isFree());

    }
    @Test
    void playWrongBuild() {
        playermove.setMoveOrBuild("M");
        player.setMyCard("Athena");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        athenaRuleDecorator.play(playermove, turn, model);
        assertTrue(model.getBoard().getCell(0, 0).isFree());
        assertFalse(model.getBoard().getCell(1, 1).isFree());
        PlayerMove move = new PlayerMove(player, worker1, 1, 2);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        athenaRuleDecorator.play(move, turn, model);
        assertEquals(0, model.getBoard().getCell(1, 2).getLevel());
    }

    @Test
    void playNotEmptyCell() {
        model.getBoard().getCell(0,1).setLevel(4);
        PlayerMove move =new PlayerMove(player,worker,0,1);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Athena");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        athenaRuleDecorator.play(move, turn, model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
    }

    @Test
    void playNotReachableCell() {
        PlayerMove move =new PlayerMove(player,worker,3,3);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Athena");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        athenaRuleDecorator.play(move, turn, model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
        assertTrue(model.getBoard().getCell(3,3).isFree());
    }

    @Test
    void playNotDifferenceAllowCell() {
        model.getBoard().getCell(1,1).setLevel(2);
        playermove.setMoveOrBuild("M");
        playermove.setColor(PlayerColor.BLUE);
        player.setMyCard("Athena");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        athenaRuleDecorator.play(playermove, turn, model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
        assertTrue(model.getBoard().getCell(1,1).isFree());
    }

    @Test
    void playStuck() {
        model.setPlayOrder(PlayerColor.BLUE,PlayerColor.GREY,PlayerColor.WHITE);
        model.getBoard().getCell(1,0).setLevel(2);
        model.getBoard().getCell(0,1).setLevel(2);
        model.getBoard().getCell(1,1).setLevel(2);
        model.getBoard().getCell(1,2).setLevel(2);
        model.getBoard().getCell(1,3).setLevel(2);
        model.getBoard().getCell(0,3).setLevel(2);
        playermove.setMoveOrBuild("M");
        playermove.setColor(PlayerColor.BLUE);
        player.setMyCard("Athena");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        athenaRuleDecorator.play(playermove,turn,model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
        assertTrue(model.getBoard().getCell(1,1).isFree());
        PlayerMove move=new PlayerMove(player,worker1,1,2);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        athenaRuleDecorator.play(move,turn,model);
        assertTrue(model.getBoard().getCell(0,2).isFree());
        assertTrue(model.getBoard().getCell(1,2).isFree());
    }

    @Test
    void move() {
        Worker worker= new Worker(model.getBoard().getCell(0,0),1, PlayerColor.BLUE);
        PlayerMove playermove=new PlayerMove(player,worker,1,1);
        playermove.setMoveOrBuild("M");
        athenaRuleDecorator.move(playermove,model,turn);
        assertTrue(model.getBoard().getCell(0,0).isFree());
        assertFalse(model.getBoard().getCell(1,1).isFree());
    }
    @Test
    void moveUp() {
        Worker worker= new Worker(model.getBoard().getCell(0,0),1, PlayerColor.BLUE);
        model.getBoard().getCell(1,1).setLevel(1);
        PlayerMove playermove=new PlayerMove(player,worker,1,1);
        playermove.setMoveOrBuild("M");
        athenaRuleDecorator.move(playermove,model,turn);
        assertEquals(0,model.getLevel());
        assertTrue(model.getBoard().getCell(0,0).isFree());
        assertFalse(model.getBoard().getCell(1,1).isFree());
    }
    @Test
    void isLevelDifferenceAllowedAthena() {
        Model model = new Model();
        Worker worker = new Worker();
        Cell cell = model.getBoard().getCell(0,0);
        worker.setWorkerPosition(cell);
        PlayerMove move = new PlayerMove(worker,1,1);
        assertTrue(athenaRuleDecorator.isLevelDifferenceAllowedAthena(move,model));
    }

    @Test
    void isNotLevelDifferenceAllowedAthena(){
        Model model = new Model();
        Worker worker = new Worker();
        Cell cell = model.getBoard().getCell(0,0);
        worker.setWorkerPosition(cell);
        cell = model.getBoard().getCell(1,1);
        cell.setLevel(2);
        PlayerMove move = new PlayerMove(worker,1,1);

        assertFalse(athenaRuleDecorator.isLevelDifferenceAllowedAthena(move,model));
    }
}