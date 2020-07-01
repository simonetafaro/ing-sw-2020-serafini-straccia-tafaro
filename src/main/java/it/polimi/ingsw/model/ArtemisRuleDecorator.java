package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;


public class ArtemisRuleDecorator extends StandardRuleDecorator {
    /**
     * this method allowed to a player how have artemis to move a second time
     *
     * @param move
     * @param model
     * @param turn
     */

    @Override
    public void play(PlayerMove move, Turn turn, Model model) {

        if (move instanceof PlayerMoveEnd) {
            if (isEndAllowed(move, turn)) {
                model.endMessage(move, turn, model);
                move.getPlayer().getMyCard().setUsingCard(false);
                move.getPlayer().getMyCard().setCustomSteps(2, "B");
            } else {
                model.sendError(move.getColor().toString() + " " + gameMessage.endYourTurn + "\n" + gameMessage.insertAgain);
            }
            return;
        }

        if (!model.isRightWorker(move, turn)) {
            model.sendError(move.getColor().toString() + " " + gameMessage.wrongWorker + "\n" + gameMessage.insertAgain);
            return;
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
     * @param move
     * @param model
     * @param turn
     */
    @Override
    public void move(PlayerMove move, Model model, Turn turn) {
        //check if is second step, in this case check is the cell where i want to move in is different from the cell of first step
        boolean hasWon = model.hasWon(move);

        if (turn.getPlayerTurn(move.getPlayer()).getI() == 2) {
            //is the second move for this player, so he wants to use the card

            if (model.getBoard().getCell(move.getRow(), move.getColumn()).equals(turn.getPlayerTurn(move.getPlayer()).getStepI(1).getCellFrom())) {
                model.sendError(move.getColor().toString() + " " + gameMessage.invalidMoveArtemis + "\n" + gameMessage.insertAgain);
                return;
            }
            move.getPlayer().getMyCard().setUsingCard(true);
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
     * in this method the third step is set 'END'
     *
     * @param move
     * @param model
     * @param turn
     */
    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        if (turn.getPlayerTurn(move.getPlayer()).getI() == 2) {
            move.getPlayer().getMyCard().setCustomSteps(2, "END");
        }
        model.getBoard().getCell(move.getRow(), move.getColumn()).buildInCell();
        model.setStep(move, turn, model);

        model.notifyView(move, false);
    }

}