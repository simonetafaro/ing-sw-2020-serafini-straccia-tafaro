package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

/**
 * the following methods have been decorated: play,move
 * the following methods have been implemented:pushWorkerPosition, minotaurMoveAllowed,pushingInCell,isMinotaurWorker,hasFreeCellClosed,isEmptyCell
 */
public class MinotaurRuleDecorator extends StandardRuleDecorator {

    /**
     * in this method there is
     * an extra check for the stuck worker ,
     * because if he can push an opponent worker
     * he is not really stack
     *
     * @param move  cell, worker and type of move
     * @param turn
     * @param model
     */
    @Override
    public void play(PlayerMove move, Turn turn, Model model) {
        if (move instanceof PlayerMoveEnd) {
            if (turn.getPlayerTurn(move.getPlayer()).getI() == 3)
                model.endMessage(move, turn, model);
            else
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
        //if Minotaur can push a player is not stuck
        if (move.getMoveOrBuild().equals("M") && !hasFreeCellClosed(move.getWorker().getWorkerPosition(), model.getBoard().getPlayingBoard())) {
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

        if (move.getMoveOrBuild().equals("M")) {
            if (!isEmptyCell(move, model)) {
                model.sendError(move.getColor().toString() + " " + gameMessage.occupiedCellMessage + "\n" + gameMessage.insertAgain);
                return;
            }
            if (!model.isLevelDifferenceAllowed(move)) {
                model.sendError(move.getColor().toString() + " " + gameMessage.tooHighCellMessage + "\n" + gameMessage.insertAgain);
                return;
            }
            if (model.fillStepInfo(move, turn, model))
                move(move, model, turn);
        } else { //"B"
            if (!model.isEmptyCell(move)) {
                model.sendError(move.getColor().toString() + " " + gameMessage.occupiedCellMessage + "\n" + gameMessage.insertAgain);
                return;
            }
            if (model.fillStepInfo(move, turn, model))
                //model.performBuild(move);
                build(move, model, turn);
        }
    }

    /**
     * This metod allowed to push an opponent worker
     *
     * @param move
     * @param model
     * @param turn
     */
    @Override
    public void move(PlayerMove move, Model model, Turn turn) {
        boolean hasWon = model.hasWon(move);
        model.setStep(move, turn, model);
        //if in the cell where I want go there is an opponent worker , push it
        if (model.getBoard().getCell(move.getRow(), move.getColumn()).getCurrWorker() != null) {
            pushWorkerPosition(move, model);
        } else {
            model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(), move.getWorker().getWorkerPosition().getPosY()).setFreeSpace(true);
            model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(), move.getWorker().getWorkerPosition().getPosY()).deleteCurrWorker();
            move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(), move.getColumn()));
            (model.getBoard().getCell(move.getRow(), move.getColumn())).setFreeSpace(false);
            model.getBoard().getCell(move.getRow(), move.getColumn()).setCurrWorker(move.getWorker());

        }
        model.notifyView(move, hasWon);
    }

    /**
     * push the opponent worker
     *
     * @param move
     * @param model
     */
    public void pushWorkerPosition(PlayerMove move, Model model) {
        Cell from = model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(), move.getWorker().getWorkerPosition().getPosY());
        Cell to = model.getBoard().getCell(move.getRow(), move.getColumn());

        Cell pushingInCell = pushingInCell(from, to, model.getBoard().getPlayingBoard());

        pushingInCell.setCurrWorker(to.getCurrWorker());
        to.getCurrWorker().setWorkerPosition(pushingInCell);
        pushingInCell.setFreeSpace(false);

        to.setCurrWorker(from.getCurrWorker());
        from.setFreeSpace(true);
        from.setCurrWorker(null);
        move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(), move.getColumn()));

    }

    /**
     * @param from  cell where the worker is
     * @param board game board
     * @return true if the worker has at least one cell to move to or an opposing worker to push
     */
    public boolean hasFreeCellClosed(Cell from, Cell[][] board) {
        boolean bool = false;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((from.getPosX() + i) >= 0 && (from.getPosY() + j) >= 0 && (from.getPosY() + j) < 5 && (from.getPosX() + i) < 5 &&
                        board[from.getPosX() + i][from.getPosY() + j].getLevel() - from.getLevel() <= 1) {
                    if ((board[from.getPosX() + i][from.getPosY() + j]).isFree() ||
                            ((board[from.getPosX() + i][from.getPosY() + j]).getCurrWorker() != null &&
                                    !isMinotaurWorker(from, (board[from.getPosX() + i][from.getPosY() + j]).getCurrWorker()) &&
                                    minotaurMoveAllowed(from, board[from.getPosX() + i][from.getPosY() + j], board))) {
                        bool = true;
                    }
                }
            }
        }
        return bool;
    }

    /**
     * @param move
     * @param model
     * @return true if the cell where I want to move the worker is free or there is an opposing worker to push
     */
    public boolean isEmptyCell(PlayerMove move, Model model) {
        Cell to = model.getBoard().getCell(move.getRow(), move.getColumn());
        return to.isFree() ||
                ((to.getCurrWorker() != move.getPlayer().getWorker1()) && (to.getCurrWorker() != move.getPlayer().getWorker2()) &&
                        ((model.getBoard().getCell(move.getRow(), move.getColumn()).getCurrWorker() != null) &&
                                (minotaurMoveAllowed(move.getWorker().getWorkerPosition(), to, model.getBoard().getPlayingBoard()))));
    }

    /**
     * @param from  cell where the worker is
     * @param to    cell where the  opposing worker is
     * @param board game board
     * @return true if minotaur in cell from can push the worker in the cell after cell to
     */
    public boolean minotaurMoveAllowed(Cell from, Cell to, Cell[][] board) {

        if (from.getPosY() == to.getPosY()) {
            if (to.getPosX() == 4 || to.getPosX() == 0)
                return false;
            if (from.getPosX() - to.getPosX() > 0) {
                return board[to.getPosX() - 1][to.getPosY()].isFree();
            } else {
                return board[to.getPosX() + 1][to.getPosY()].isFree();
            }
        }

        if (from.getPosX() == to.getPosX()) {
            if (to.getPosY() == 4 || to.getPosY() == 0)
                return false;
            if (from.getPosY() - to.getPosY() > 0)
                return board[from.getPosX()][to.getPosY() - 1].isFree();
            else
                return board[from.getPosX()][to.getPosY() + 1].isFree();
        }

        //sono in diagonale
        if (to.getPosX() == 4 || to.getPosY() == 4 || to.getPosX() == 0 || to.getPosY() == 0)
            return false;
        if (from.getPosX() > to.getPosX()) {
            if (from.getPosY() < to.getPosY())
                return board[to.getPosX() - 1][to.getPosY() + 1].isFree();
            else
                return board[to.getPosX() - 1][to.getPosY() - 1].isFree();
        } else {
            if (from.getPosY() < to.getPosY())
                return board[to.getPosX() + 1][to.getPosY() + 1].isFree();
            else
                return board[to.getPosX() + 1][to.getPosY() - 1].isFree();
        }
    }

    /**
     * @param from  cell where the worker is
     * @param to    cell where the  opposing worker is
     * @param board game board
     * @return the cell where the opposing worker must move
     */
    public Cell pushingInCell(Cell from, Cell to, Cell[][] board) {

        if (from.getPosY() == to.getPosY()) {
            if (from.getPosX() - to.getPosX() > 0)
                return board[to.getPosX() - 1][to.getPosY()];
            else
                return board[to.getPosX() + 1][to.getPosY()];
        }

        if (from.getPosX() == to.getPosX()) {
            if (from.getPosY() - to.getPosY() > 0)
                return board[from.getPosX()][to.getPosY() - 1];
            else
                return board[from.getPosX()][to.getPosY() + 1];
        }

        //sono in diagonale
        if (from.getPosX() > to.getPosX()) {
            if (from.getPosY() < to.getPosY())
                return board[to.getPosX() - 1][to.getPosY() + 1];
            else
                return board[to.getPosX() - 1][to.getPosY() - 1];
        } else {
            if (from.getPosY() < to.getPosY())
                return board[to.getPosX() + 1][to.getPosY() + 1];
            else
                return board[to.getPosX() + 1][to.getPosY() - 1];
        }

    }

    /**
     * @param from cell where the worker is
     * @param to   worker
     * @return true if the worker in the cell is from the same player as the worker To
     */
    public boolean isMinotaurWorker(Cell from, Worker to) {
        return from.getCurrWorker().getColor() == to.getColor();
    }
}
