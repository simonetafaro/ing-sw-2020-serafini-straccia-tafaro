package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;
/**
 *Your Build: Your Worker may
 * build a dome at any level.
 */
public class AtlasRuleDecorator extends StandardRuleDecorator {

    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        if(move.getMoveOrBuild().equals("D")){
            model.getBoard().getCell(move.getRow(),move.getColumn()).setLevel(4);
        }else{
            model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell();
        }
        model.setStep(move, turn, model);
        model.notifyView(move,false);
    }
}
