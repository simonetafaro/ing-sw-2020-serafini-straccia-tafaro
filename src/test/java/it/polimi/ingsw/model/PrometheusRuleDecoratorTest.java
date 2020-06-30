package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrometheusRuleDecoratorTest {

    private PrometheusRuleDecorator prometheusRuleDecorator= new PrometheusRuleDecorator();
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
        player.setMyCard("Prometheus");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        prometheusRuleDecorator.play(playermove,turn,model);
        assertTrue(model.getBoard().getCell(0,0).isFree());
        assertFalse(model.getBoard().getCell(1,1).isFree());
        PlayerMove move=new PlayerMove(player,worker,1,2);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        prometheusRuleDecorator.play(move,turn,model);
        assertEquals(0, model.getBoard().getCell(1, 1).getLevel());
        assertEquals(1, model.getBoard().getCell(1, 2).getLevel());
        PlayerMoveEnd moveend=new PlayerMoveEnd(player,true);
        //move.setColor(PlayerColor.BLUE);
        prometheusRuleDecorator.play(moveend,turn,model);
        assertFalse(move.getPlayer().getMyCard().isUsingCard());
    }
    @Test
    void playB_M_B() {
        playermove.setMoveOrBuild("B");
        player.setMyCard("Prometheus");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        prometheusRuleDecorator.play(playermove,turn,model);
        PlayerMove move=new PlayerMove(player,worker,1,0);
        move.setMoveOrBuild("M");
        prometheusRuleDecorator.play(move,turn,model);
        PlayerMove move1=new PlayerMove(player,worker,0,0);
        move1.setMoveOrBuild("B");
        prometheusRuleDecorator.play(move1,turn,model);
        assertEquals(1, model.getBoard().getCell(1, 1).getLevel());
        assertEquals(1, model.getBoard().getCell(0, 0).getLevel());
        assertEquals(worker, model.getBoard().getCell(1,0 ).getCurrWorker());
    }
    @Test
    void playWrongStepB() {
        playermove.setMoveOrBuild("B");
        player.setMyCard("Prometheus");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        prometheusRuleDecorator.play(playermove,turn,model);
        PlayerMove move=new PlayerMove(player,worker,1,0);
        move.setMoveOrBuild("M");
        prometheusRuleDecorator.play(move,turn,model);
        PlayerMove move1=new PlayerMove(player,worker,0,0);
        move1.setMoveOrBuild("B");
        prometheusRuleDecorator.play(move1,turn,model);
        PlayerMove move2=new PlayerMove(player,worker,0,0);
        move2.setMoveOrBuild("B");
        move2.setColor(PlayerColor.BLUE);
        prometheusRuleDecorator.play(move2,turn,model);
        assertEquals(1, model.getBoard().getCell(1, 1).getLevel());
        assertEquals(1, model.getBoard().getCell(0, 0).getLevel());
        assertEquals(worker, model.getBoard().getCell(1,0 ).getCurrWorker());
    }
    @Test
    void playWrongStepSecondB() {
        playermove.setMoveOrBuild("B");
        player.setMyCard("Prometheus");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        prometheusRuleDecorator.play(playermove,turn,model);
        PlayerMove move=new PlayerMove(player,worker,1,0);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        prometheusRuleDecorator.play(move,turn,model);
        assertEquals(1, model.getBoard().getCell(1, 1).getLevel());
        assertEquals(0, model.getBoard().getCell(1, 0).getLevel());
        assertEquals(worker, model.getBoard().getCell(0,0 ).getCurrWorker());
    }
    @Test
    void playNotEndAllowed() {
        player.setMyCard("Prometheus");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        PlayerMoveEnd playerMoveEnd = new PlayerMoveEnd(player,true);
        playerMoveEnd.setColor(PlayerColor.BLUE);
        prometheusRuleDecorator.play(playerMoveEnd,turn,model);

    }
    @Test
    void playOutOfBoard() {
        PlayerMove move =new PlayerMove(player,worker,1,5);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Prometheus");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        prometheusRuleDecorator.play(move,turn,model);
        assertFalse(model.getBoard().getCell(0,0).isFree());

    }
    @Test
    void playWrongBuild() {
        playermove.setMoveOrBuild("M");
        player.setMyCard("Prometheus");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        prometheusRuleDecorator.play(playermove, turn, model);
        assertTrue(model.getBoard().getCell(0, 0).isFree());
        assertFalse(model.getBoard().getCell(1, 1).isFree());
        PlayerMove move = new PlayerMove(player, worker1, 1, 2);
        move.setMoveOrBuild("B");
        move.setColor(PlayerColor.BLUE);
        prometheusRuleDecorator.play(move, turn, model);
        assertEquals(0, model.getBoard().getCell(1, 2).getLevel());
    }

    @Test
    void playNotEmptyCell() {
        model.getBoard().getCell(0,1).setLevel(4);
        PlayerMove move =new PlayerMove(player,worker,0,1);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Prometheus");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        prometheusRuleDecorator.play(move, turn, model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
    }

    @Test
    void playNotReachableCell() {
        PlayerMove move =new PlayerMove(player,worker,3,3);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        player.setMyCard("Prometheus");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        prometheusRuleDecorator.play(move, turn, model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
        assertTrue(model.getBoard().getCell(3,3).isFree());
    }

    @Test
    void playNotDifferenceAllowCell() {
        model.getBoard().getCell(1,1).setLevel(2);
        playermove.setMoveOrBuild("M");
        playermove.setColor(PlayerColor.BLUE);
        player.setMyCard("Prometheus");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        prometheusRuleDecorator.play(playermove, turn, model);
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
        player.setMyCard("Prometheus");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        prometheusRuleDecorator.play(playermove,turn,model);
        assertFalse(model.getBoard().getCell(0,0).isFree());
        assertTrue(model.getBoard().getCell(1,1).isFree());
        PlayerMove move=new PlayerMove(player,worker1,1,2);
        move.setMoveOrBuild("M");
        move.setColor(PlayerColor.BLUE);
        prometheusRuleDecorator.play(move,turn,model);
        assertTrue(model.getBoard().getCell(0,2).isFree());
        assertTrue(model.getBoard().getCell(1,2).isFree());
    }
    @Test
    void playFirstBuildNotAllowed() {
        model.getBoard().getCell(0,1).setLevel(1);
        model.getBoard().getCell(1,0).setLevel(1);
        playermove.setMoveOrBuild("B");
        playermove.setColor(PlayerColor.BLUE);
        player.setMyCard("Prometheus");
        player.setWorker1(worker);
        player.setWorker2(worker1);
        prometheusRuleDecorator.play(playermove,turn,model);
        assertEquals(0,model.getBoard().getCell(1,1).getLevel());
    }
    @Test
    void move() {
        Worker worker= new Worker(model.getBoard().getCell(0,0),1, PlayerColor.BLUE);
        PlayerMove playermove=new PlayerMove(player,worker,1,1);
        player.setMyCard("Prometheus");
        playermove.setMoveOrBuild("M");
        prometheusRuleDecorator.move(playermove,model,turn);
        assertTrue(model.getBoard().getCell(0,0).isFree());
        assertFalse(model.getBoard().getCell(1,1).isFree());
    }

    @Test
    void build() {

        Worker worker= new Worker(model.getBoard().getCell(0,0),1, PlayerColor.BLUE);
        PlayerMove playermove=new PlayerMove(player,worker,1,1);
        player.setMyCard("Prometheus");
        playermove.setMoveOrBuild("B");
        prometheusRuleDecorator.build(playermove,model,turn);
        assertEquals(0, model.getBoard().getCell(0, 0).getLevel());
        assertEquals(1, model.getBoard().getCell(1, 1).getLevel());

    }
    //false test: worker.getWorkerPosition().getLevel() = cellTo.getLevel()
    @Test
    void notCanBuildInAndThenMoveForLevel() {
        Model model = new Model();
        Worker worker = new Worker();

        worker.setWorkerPosition(model.getBoard().getCell(0,0));
        PlayerMove move = new PlayerMove(worker,1,1);
        model.getBoard().getCell(0,1).setLevel(4);
        model.getBoard().getCell(1,0).setLevel(4);
        model.getBoard().getCell(1,1).setLevel(0);
        assertFalse(prometheusRuleDecorator.canBuildInAndThenMove(move,model));
    }

    //true test: worker.getWorkerPosition().getLevel() > cellTo.getLevel()
    @Test
    void canBuildInAndThenMove() {
        Model model = new Model();
        Worker worker = new Worker();

        worker.setWorkerPosition(model.getBoard().getCell(0,0));
        PlayerMove move = new PlayerMove(worker,1,1);
        model.getBoard().getCell(0,1).setLevel(4);
        model.getBoard().getCell(1,0).setLevel(4);
        model.getBoard().getCell(1,1).setLevel(0);
        model.getBoard().getCell(0,0).setLevel(1);
        assertTrue(prometheusRuleDecorator.canBuildInAndThenMove(move,model));
    }

    //false test: even if worker.getWorkerPosition().getLevel() > to.getLevel(), cellTo is not free
    @Test
    void notCanBuildInAndThenMoveForNotFree() {
        Model model = new Model();
        Worker worker = new Worker();
        Worker worker1 = new Worker();

        worker1.setWorkerPosition(model.getBoard().getCell(1,1));
        worker.setWorkerPosition(model.getBoard().getCell(0,0));
        PlayerMove move = new PlayerMove(worker,1,1);
        model.getBoard().getCell(0,1).setLevel(4);
        model.getBoard().getCell(1,0).setLevel(4);
        model.getBoard().getCell(1,1).setLevel(0);
        model.getBoard().getCell(0,0).setLevel(1);
        assertFalse(prometheusRuleDecorator.canBuildInAndThenMove(move,model));
    }
}