package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;


/**
 * the following method has been decorated: build
 * the following method has been implemented:checkPerimeterSpace
 */
public class HestiaRuleDecorator extends StandardRuleDecorator {
    /**
     * This method allowed a worker to build an additional time
     *
     * @param move
     * @param model
     * @param turn
     */
    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        if (turn.getPlayerTurn(move.getPlayer()).getI() == 3) {
            //the worker cannon builds the second time in the perimeter space
            if (checkPerimeterSpace(move, model)) {
                model.sendError(move.getColor().toString() + " " + gameMessage.invalidMoveHestia + "\n" + gameMessage.insertAgain);
                return;
            }
            move.getPlayer().getMyCard().setUsingCard(true);
        }
        model.getBoard().getCell(move.getRow(), move.getColumn()).buildInCell();
        model.setStep(move, turn, model);
        model.notifyView(move, false);
    }

    /**
     * @param move
     * @param model
     * @return true if a player want do an action in a perimeter space.
     */
    public boolean checkPerimeterSpace(PlayerMove move, Model model) {
        for (int i = 0; i < 5; i++)
            if (model.getBoard().getCell(move.getRow(), move.getColumn()).equals(model.getBoard().getCell(i, 0)) ||
                    model.getBoard().getCell(move.getRow(), move.getColumn()).equals(model.getBoard().getCell(i, 4)) ||
                    model.getBoard().getCell(move.getRow(), move.getColumn()).equals(model.getBoard().getCell(0, i)) ||
                    model.getBoard().getCell(move.getRow(), move.getColumn()).equals(model.getBoard().getCell(4, i))) {
                return true;
            }
        return false;

    }
}
