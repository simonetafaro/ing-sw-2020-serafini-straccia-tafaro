package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrometheusRuleDecoratorTest {

    private PrometheusRuleDecorator prometheusRuleDecorator= new PrometheusRuleDecorator();

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