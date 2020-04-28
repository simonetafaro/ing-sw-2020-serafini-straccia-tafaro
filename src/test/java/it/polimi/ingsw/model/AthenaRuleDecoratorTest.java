package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class AthenaRuleDecoratorTest {

    private AthenaRuleDecorator athenaRuleDecorator = new AthenaRuleDecorator();

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