package it.polimi.ingsw.model;
import it.polimi.ingsw.utils.gameMessage;

/**Management of normal play with move-build-end*/
public class StandardRuleDecorator implements CardRuleDecorator {

    /**
     * In this method the player's move is managed, with feasibility checks
     * @param move  cell, worker and type of move
     * @param turn
     * @param model
     *
     */
    @Override
    public void play(PlayerMove move, Turn turn, Model model) {
        if(move instanceof PlayerMoveEnd){
            if(isEndAllowed(move, turn)) {
                model.endMessage(move,turn,model);
                move.getPlayer().getMyCard().setUsingCard(false);
            }
            else {
                model.sendError(move.getColor().toString()+" "+gameMessage.endYourTurn + "\n" + gameMessage.insertAgain);
            }
            return;
        }

        if(!model.isRightWorker(move, turn)){
            model.sendError(move.getColor().toString()+" "+gameMessage.wrongWorker+" "+gameMessage.insertAgain);
            return;
        }

        if(!checkStepType(move,turn)){
            model.sendError(move.getColor().toString()+" "+gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getRow()<0 || move.getRow()>=5 || move.getColumn()<0 || move.getColumn()>=5){
            model.sendError(move.getColor().toString()+" "+gameMessage.wrongInputMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getMoveOrBuild().equals("M") && !move.getWorker().getWorkerPosition().hasFreeCellClosed(model.getBoard().getPlayingBoard())){
            //this worker is stuck
            move.getWorker().setStuck(true);
            model.sendError(move.getColor().toString()+" "+gameMessage.workerStuck+"\n"+gameMessage.insertAgain);
            //check if both worker1 && worker2 are stuck player lose
            if(model.isPlayerStuck(move)){
                //this player lose, both workers are stuck
                model.deletePlayer(move);
            }
            return;
        }

        if(!model.isReachableCell(move)){
            model.sendError(move.getColor().toString()+" "+gameMessage.notReachableCellMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!model.isEmptyCell(move)){
            //read worker position and check if there are some empty cell where he can move in.
            model.sendError(move.getColor().toString()+" "+gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getMoveOrBuild().equals("M") ){
            if(!model.isLevelDifferenceAllowed(move)){
                model.sendError(move.getColor().toString()+" "+gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            if(model.checkStep(move, turn, model))
                move(move, model, turn);
        }
        else{
            if(model.checkStep(move, turn, model))
                build(move, model, turn);
        }
    }

    /**
     *move the worker to the cell indicated by the "move"
     * @param move
     * @param model
     * @param turn
     */
    public void move(PlayerMove move, Model model, Turn turn){
        boolean hasWon = model.hasWon(move);

        model.setStep(move, turn, model);
        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).setFreeSpace(true);
        model.getBoard().getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).deleteCurrWorker();
        move.getWorker().setWorkerPosition(model.getBoard().getCell(move.getRow(),move.getColumn()));
        (model.getBoard().getCell(move.getRow(),move.getColumn())).setFreeSpace(false);
        model.getBoard().getCell(move.getRow(),move.getColumn()).setCurrWorker(move.getWorker());

        model.notifyView(move,hasWon);
    }

    /**
     *  builds in the cell indicated by the "move"
     * @param move
     * @param model
     * @param turn
     */
    public void build(PlayerMove move, Model model, Turn turn) {
        model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell();
        model.setStep(move, turn, model);
        model.notifyView(move,false);
    }

    /**
     * @param message
     * @param turn
     * @return true if the type of move is correct
     */
    public boolean checkStepType(PlayerMove message, Turn turn){
        if(message.getPlayer().getMyCard().isUsingCard()) {
            return message.getMoveOrBuild().equals(message.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(message.getPlayer()).getI()));
        }else
            return  message.getMoveOrBuild().equals(message.getPlayer().getMyCard().getStandardStepLetter(turn.getPlayerTurn(message.getPlayer()).getI()))
                    || message.getMoveOrBuild().equals(message.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(message.getPlayer()).getI()));
    }

    /**
     * @param move
     * @param turn
     * @return true if the end is allowed
     */
    public boolean isEndAllowed(PlayerMove move, Turn turn){
        return move.getPlayer().getMyCard().isUsingCard() ? (move.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(move.getPlayer()).getI())).equals("END") :
                (move.getPlayer().getMyCard().getStandardStepLetter(turn.getPlayerTurn(move.getPlayer()).getI())).equals("END");
    }

}
