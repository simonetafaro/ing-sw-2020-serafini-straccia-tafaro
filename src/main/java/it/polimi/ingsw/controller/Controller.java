package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observ.Observer;
import it.polimi.ingsw.utils.gameMessage;

public class Controller implements Observer<PlayerMove> {

    private final Model model;
    private Turn turn;

    public Controller(Model model){
        //super();
        this.model = model;
    }
    public void setTurn(Turn turn) {
        this.turn = turn;
    }
    //move for italian people is the new "mossa"
    private synchronized void performMove(PlayerMove move){
        /**Controlla che è il turno del giocatore che ha inviato la mossa
         * */
        if(!isPlayerTurn(move.getPlayer())){
            move.getView().reportError(gameMessage.wrongTurnMessage+"\n"+gameMessage.waitMessage);
            return;
        }
        if(move instanceof PlayerMoveEnd){
            if(turn.getPlayerTurn(move.getPlayer()).getI()==3)
                endMessage(move);
            else
                move.getView().reportError(gameMessage.endYourTurn+"\n"+gameMessage.insertAgain);
            return;
        }
        if(!checkStepType(move)){
            move.getView().reportError(gameMessage.wrongStepMessage+"\n"+gameMessage.insertAgain);
            return;
        }
        //Check input
        if(move.getRow()<0 || move.getRow()>=5 || move.getColumn()<0 || move.getColumn()>=5){
            move.getView().reportError(gameMessage.wrongInputMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(!move.getWorker().getWorkerPosition().hasFreeCellClosed(model.getBoard().getPlayingBoard())){
            //this worker is stuck
            move.getWorker().setStuck(true);
            move.getView().reportError(gameMessage.workerStuck+"\n"+gameMessage.insertAgain);
            //check if both worker1 && worker2 are stuck player lose
            if(isPlayerStuck(move)){
                //this player lose, both workers are stuck
                model.deletePlayer(move);
            }
            return;
        }

        if(!model.isReachableCell(move)){
            move.getView().reportError(gameMessage.notReachableCellMessage+"\n"+gameMessage.insertAgain);
            return;
        }
        if(!model.isEmptyCell(move)){
            //read worker position and check if there are some empty cell where he can move in.
            move.getView().reportError(gameMessage.occupiedCellMessage+"\n"+gameMessage.insertAgain);
            return;
        }

        if(move.getMoveOrBuild()=='M'){
            if(!model.isLevelDifferenceAllowed(move)){
                move.getView().reportError(gameMessage.tooHighCellMessage+"\n"+gameMessage.insertAgain);
                return;
            }
            if(setStep(move))
                model.performMove(move);
        }
        else{
            if(setStep(move))
                model.performBuild(move);
        }
        //System.out.println("la mossa che voglio fare è fattibile");
    }
    public boolean isPlayerTurn(Player player) {
        return player.getColor() == model.getTurn();
    }

    @Override
    public void update(PlayerMove message) {
        /**Questa update è sollecitata quando una remoteView (player1View o player2View)
         * fa una notify. Riceve come paramentro una playerMove che contiene
         * -Row, column
         * -Player
         * -Worker scelto
         * -View
         * */

        performMove(message);
    }

    public boolean checkStepType(PlayerMove message){
        return message.getMoveOrBuild() == message.getPlayer().getMyCard().getStepLetter(turn.getPlayerTurn(message.getPlayer()).getI());
    }
    public void endMessage(PlayerMove message){
        turn.getPlayerTurn(message.getPlayer()).resetStep();
        model.endNotifyView(message,false);
    }
    public boolean setStep(PlayerMove move){
        if(turn.getPlayerTurn(move.getPlayer()).isFirstStep())
            turn.getPlayerTurn(move.getPlayer()).setTurnWorker(move.getWorker());
        if(!turn.getPlayerTurn(move.getPlayer()).getTurnWorker().equals(move.getWorker())){
                move.getView().reportError(gameMessage.wrongWorker);
                return false;
            }
        //set both worker of this player !stuck
        move.getPlayer().getWorker1().setStuck(false);
        move.getPlayer().getWorker2().setStuck(false);
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setType(move.getMoveOrBuild());
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellFrom(move.getWorker().getWorkerPosition());
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellTo(model.getBoard().getCell(move.getRow(),move.getColumn()));
        turn.getPlayerTurn(move.getPlayer()).updateStep();
        return true;
    }
    public boolean isPlayerStuck(PlayerMove move){
        return move.getPlayer().getWorker1().isStuck()&&move.getPlayer().getWorker2().isStuck();
    }
}
