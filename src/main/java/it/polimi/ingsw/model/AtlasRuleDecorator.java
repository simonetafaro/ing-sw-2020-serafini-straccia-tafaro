package it.polimi.ingsw.model;


public class AtlasRuleDecorator extends StandardRuleDecorator {

    /**
     * This method allowed a worker to build a dome at any level
     *
     * @param move
     * @param model
     * @param turn
     */
    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        //if in the playermove the attribute getMoveOrBuild is "D" the worker can Build a Dome
        if (move.getMoveOrBuild().equals("D")) {
            model.getBoard().getCell(move.getRow(), move.getColumn()).buildDome();
        } else {
            model.getBoard().getCell(move.getRow(), move.getColumn()).buildInCell();
        }
        model.setStep(move, turn, model);
        model.notifyView(move, false);
    }
}
