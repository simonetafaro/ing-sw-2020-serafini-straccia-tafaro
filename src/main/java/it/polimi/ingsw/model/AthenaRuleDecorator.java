package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

public class AthenaRuleDecorator extends StandardRuleDecorator {

    /**
     * in this method
     * there is a changed condition for goes up
     * the worker with the power of Athena
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
            if (!isLevelDifferenceAllowedAthena(move, model)) {
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
     * this method is a normal move with a check if the worker how have Athena power goes up
     *
     * @param move
     * @param model
     * @param turn
     */
    @Override
    public void move(PlayerMove move, Model model, Turn turn) {
        boolean hasWon = model.hasWon(move);

        //reset normal goUpCondition (max one level up)
        model.setGoUpLevel(1);

        //If the worker how have  Athena  power goes up change GoUpCondition and nobody can go up

        if (model.getBoard().getCell(move.getRow(), move.getColumn()).getLevel() - move.getWorker().getWorkerPosition().getLevel() > 0)
            model.setGoUpLevel(0);
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
     * @return true if the difference of the cell in which I am ,
     * compared to the one where I want to move is at most 1
     */
    public boolean isLevelDifferenceAllowedAthena(PlayerMove move, Model model) {
        return ((model.getBoard().getCell(move.getRow(), move.getColumn())).getLevel() - ((move.getWorker().getWorkerPosition()).getLevel())) <= 1;
    }
}
