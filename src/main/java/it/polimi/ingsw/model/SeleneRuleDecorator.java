package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

/**
 * the following methods have been decorated: play,move
 * the following method has been implemented: isRightWorker
 */
public class SeleneRuleDecorator extends StandardRuleDecorator {
    private boolean IamWoman = false;

    /**
     * in this method there is
     * an extra check if the worker is a woman
     *
     * @param move  cell, worker and type of move
     * @param turn
     * @param model
     */
    @Override
    public void play(PlayerMove move, Turn turn, Model model) {
        if (move instanceof PlayerMoveEnd) {
            if (isEndAllowed(move, turn)) {
                model.endMessage(move, turn, model);
                move.getPlayer().getMyCard().setUsingCard(false);
            } else
                model.sendError(move.getColor().toString() + " " + gameMessage.endYourTurn + "\n" + gameMessage.insertAgain);
            return;
        }
        //female Worker may build regardless of which Worker moved.
        if (!isRightWorker(move, turn)) {
            if (move.getWorker().getWorkerNum() == 2)
                IamWoman = true;
            else {
                model.sendError(move.getColor().toString() + " " + gameMessage.wrongWorker + "\n" + gameMessage.insertAgain);
                return;
            }
        }

        if (!checkStepType(move, turn)) {
            model.sendError(move.getColor().toString() + " " + gameMessage.wrongStepMessage + "\n" + gameMessage.insertAgain);
            return;
        }

        if (move.getRow() < 0 || move.getRow() >= 5 || move.getColumn() < 0 || move.getColumn() >= 5) {
            model.sendError(move.getColor().toString() + " " + gameMessage.wrongInputMessage + "\n" + gameMessage.insertAgain);
            return;
        }

        if (move.getMoveOrBuild().equals("M") && !move.getWorker().getWorkerPosition().hasFreeCellClosed(model.getBoard().getPlayingBoard())) {
            //this worker is stuck
            move.getWorker().setStuck(true);
            model.sendError(move.getColor().toString() + " " + gameMessage.workerStuck + "\n" + gameMessage.insertAgain);
            //check if both worker1 && worker2 are stuck player lose
            if (model.isPlayerStuck(move)) {
                //this player lose, both workers are stuck
                model.deletePlayer(move);
            }
            return;
        }

        if (!model.isReachableCell(move)) {
            model.sendError(move.getColor().toString() + " " + gameMessage.notReachableCellMessage + "\n" + gameMessage.insertAgain);
            return;
        }

        if (!model.isEmptyCell(move)) {
            //read worker position and check if there are some empty cell where he can move in.
            model.sendError(move.getColor().toString() + " " + gameMessage.occupiedCellMessage + "\n" + gameMessage.insertAgain);
            return;
        }

        if (move.getMoveOrBuild().equals("M")) {
            if (!model.isLevelDifferenceAllowed(move)) {
                model.sendError(move.getColor().toString() + " " + gameMessage.tooHighCellMessage + "\n" + gameMessage.insertAgain);
                return;
            }
            if (model.fillStepInfo(move, turn, model))
                //model.performMove(move);
                move(move, model, turn);
        } else {
            if (model.fillStepInfo(move, turn, model))
                //model.performBuild(move);
                build(move, model, turn);
        }
    }

    /**
     * this method allows you to build a
     * dome for women instead of any build
     *
     * @param move
     * @param model
     * @param turn
     */
    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        if (IamWoman) {
            model.getBoard().getCell(move.getRow(), move.getColumn()).buildDome();
        } else {
            if ((move.getWorker().getWorkerNum() == 2) && (move.getMoveOrBuild().equals("D"))) {
                model.getBoard().getCell(move.getRow(), move.getColumn()).buildDome();
            } else {
                model.getBoard().getCell(move.getRow(), move.getColumn()).buildInCell();
            }
        }
        IamWoman = false;
        model.setStep(move, turn, model);
        model.notifyView(move, false);
    }

    /**
     * @param move
     * @param turn
     * @return true if it is the first step (M) or if the first step's worker
     * is the same of the move's worker
     */
    public boolean isRightWorker(PlayerMove move, Turn turn) {
        return (turn.getPlayerTurn(move.getPlayer()).isFirstStep()) || turn.getPlayerTurn(move.getPlayer()).getTurnWorker().equals(move.getWorker());
    }
}