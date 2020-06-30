package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

public class PrometheusRuleDecorator extends StandardRuleDecorator {

    /**
     * in this method there is
     * an extra check if the worker
     * can build and then move without leveling up
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
                move.getPlayer().getMyCard().setCustomSteps(1, "M");
                move.getPlayer().getMyCard().setCustomSteps(2, "B");

                model.setGoUpLevel(1);
            } else
                model.sendError(move.getColor().toString() + " " + gameMessage.endYourTurn + "\n" + gameMessage.insertAgain);
            return;
        }

        if (!model.isRightWorker(move, turn)) {
            model.sendError(move.getColor().toString() + " " + gameMessage.wrongWorker + "\n" + gameMessage.insertAgain);
            return;
        }

        if (move.getRow() < 0 || move.getRow() >= 5 || move.getColumn() < 0 || move.getColumn() >= 5) {
            model.sendError(move.getColor().toString() + " " + gameMessage.wrongInputMessage + "\n" + gameMessage.insertAgain);
            return;
        }

        if (!checkStepType(move, turn)) {
            model.sendError(move.getColor().toString() + " " + gameMessage.wrongStepMessage + "\n" + gameMessage.insertAgain);
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
                move(move, model, turn);
        } else { //"B"
            //This worker cannot build if then he cannot move
            if ((turn.getPlayerTurn(move.getPlayer()).getI() == 1) && !canBuildInAndThenMove(move, model)) {
                model.sendError(move.getColor().toString() + " " + gameMessage.invalidMovePrometheus + "\n" + gameMessage.insertAgain);
                return;
            }
            if (model.fillStepInfo(move, turn, model))
                //model.performBuild(move);
                build(move, model, turn);
        }
    }

    @Override
    public void move(PlayerMove move, Model model, Turn turn) {
        boolean hasWon = model.hasWon(move);
        if (turn.getPlayerTurn(move.getPlayer()).getI() == 1) {
            //Can't go up for this turn
            move.getPlayer().getMyCard().setCustomSteps(1, "B");
            move.getPlayer().getMyCard().setCustomSteps(2, "END");

        }
        model.setStep(move, turn, model);
        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(), move.getWorker().getWorkerPosition().getPosY()).setFreeSpace(true);
        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(), move.getWorker().getWorkerPosition().getPosY()).deleteCurrWorker();
        move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(), move.getColumn()));
        (model.getBoard().getCell(move.getRow(), move.getColumn())).setFreeSpace(false);
        model.getBoard().getCell(move.getRow(), move.getColumn()).setCurrWorker(move.getWorker());

        model.notifyView(move, hasWon);
    }

    /**
     * this method allowed to build at the first step
     *
     * @param move
     * @param model
     * @param turn
     */
    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        if (turn.getPlayerTurn(move.getPlayer()).getI() == 1) {
            //Can't go up for this turn
            move.getPlayer().getMyCard().setUsingCard(true);
            model.setGoUpLevel(0);
        }
        if (turn.getPlayerTurn(move.getPlayer()).getI() == 2) {
            move.getPlayer().getMyCard().setCustomSteps(2, "END");
        }

        model.getBoard().getCell(move.getRow(), move.getColumn()).buildInCell();
        model.setStep(move, turn, model);

        model.notifyView(move, false);
    }

    /**
     * @param move
     * @param model
     * @return true
     * if after the build I want to do there is at least one cell left
     * where I can go which is on the same level as where I am
     */
    public boolean canBuildInAndThenMove(PlayerMove move, Model model) {
        boolean bool = false;
        Cell[][] board = model.getBoard().getPlayingBoard();
        Cell from = move.getWorker().getWorkerPosition();
        model.getBoard().getCell(move.getRow(), move.getColumn()).setLevel(model.getBoard().getCell(move.getRow(), move.getColumn()).getLevel() + 1);

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((from.getPosX() + i) >= 0 && (from.getPosY() + j) >= 0 && (from.getPosY() + j) < 5 && (from.getPosX() + i) < 5 &&
                        board[from.getPosX() + i][from.getPosY() + j].getLevel() - from.getLevel() <= 0) {
                    if ((board[from.getPosX() + i][from.getPosY() + j]).isFree()) {
                        bool = true;
                    }
                }
            }
        }
        model.getBoard().getCell(move.getRow(), move.getColumn()).setLevel(model.getBoard().getCell(move.getRow(), move.getColumn()).getLevel() - 1);
        return bool;
    }


}
