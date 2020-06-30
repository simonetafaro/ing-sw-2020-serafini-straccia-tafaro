package it.polimi.ingsw.model;

public class PanRuleDecorator extends StandardRuleDecorator {

    /**
     * in this move the condition to win is also to move down two or more level
     *
     * @param move
     * @param model
     * @param turn
     */
    @Override
    public void move(PlayerMove move, Model model, Turn turn) {
        boolean hasWon = model.hasWon(move) || panWinCondition(move, model);

        model.setStep(move, turn, model);
        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(), move.getWorker().getWorkerPosition().getPosY()).setFreeSpace(true);
        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(), move.getWorker().getWorkerPosition().getPosY()).deleteCurrWorker();
        move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(), move.getColumn()));
        (model.getBoard().getCell(move.getRow(), move.getColumn())).setFreeSpace(false);
        model.getBoard().getCell(move.getRow(), move.getColumn()).setCurrWorker(move.getWorker());


        model.notifyView(move, hasWon);
    }

    /**
     * @param move
     * @param model
     * @return true if the worker has gone down by at least two levels
     */
    public boolean panWinCondition(PlayerMove move, Model model) {
        return (move.getWorker().getWorkerPosition().getLevel() - model.getBoard().getCell(move.getRow(), move.getColumn()).getLevel()) >= 2;

    }
}
