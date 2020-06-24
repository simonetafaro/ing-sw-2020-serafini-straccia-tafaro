package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;
/**
 *Your Build: Your Worker may
 * build one additional time, but not
 * on the same space.
 */
public class DemeterRuleDecorator extends StandardRuleDecorator {

    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        if(turn.getPlayerTurn(move.getPlayer()).getI()==3){
            //is the second build for this player, so he wants to use the card
            if(model.getBoard().getCell(move.getRow(),move.getColumn()) == turn.getPlayerTurn(move.getPlayer()).getStepI(2).getCellTo()){
                model.sendError(move.getColor().toString()+" "+gameMessage.invalidMoveDemeter+"\n"+gameMessage.insertAgain);
                return;
            }
            move.getPlayer().getMyCard().setUsingCard(true);
        }
        model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell();
        model.setStep(move, turn, model);

        model.notifyView(move,false);
    }

}
