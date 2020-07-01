package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

/**
 * the following method has been decorated: build
 */
public class DemeterRuleDecorator extends StandardRuleDecorator {

    /**
     * This method allows a worker to build an additional time
     *
     * @param move
     * @param model
     * @param turn
     */
    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        if (turn.getPlayerTurn(move.getPlayer()).getI() == 3) {
            //the worker cannot build the second time on the same space
            if (model.getBoard().getCell(move.getRow(), move.getColumn()).equals(turn.getPlayerTurn(move.getPlayer()).getStepI(2).getCellTo())) {
                model.sendError(move.getColor().toString() + " " + gameMessage.invalidMoveDemeter + "\n" + gameMessage.insertAgain);
                return;
            }
            move.getPlayer().getMyCard().setUsingCard(true);
        }
        model.getBoard().getCell(move.getRow(), move.getColumn()).buildInCell();
        model.setStep(move, turn, model);

        model.notifyView(move, false);
    }

}
