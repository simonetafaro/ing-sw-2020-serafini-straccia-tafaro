package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

public class HephaestusRuleDecorator extends StandardRuleDecorator {

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
            //the worker must build the second time on the same space
            //in the second condition is checked not to build a dome
            if ((model.getBoard().getCell(move.getRow(), move.getColumn()) != turn.getPlayerTurn(move.getPlayer()).getStepI(2).getCellTo())
                    || model.getBoard().getCell(move.getRow(), move.getColumn()).getLevel() == 3) {
                model.sendError(move.getColor().toString() + " " + gameMessage.invalidMoveHephaestus + "\n" + gameMessage.insertAgain);
                return;
            }
            move.getPlayer().getMyCard().setUsingCard(true);
        }
        model.getBoard().getCell(move.getRow(), move.getColumn()).buildInCell();
        model.setStep(move, turn, model);


        model.notifyView(move, false);
    }

}
