package it.polimi.ingsw.model;

import it.polimi.ingsw.observ.Observable;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.gameMessage;

import java.util.ArrayList;
import java.util.List;

public class Model extends Observable<MoveMessage> {

    private Board board;
    private PlayerColor turn;
    private List<PlayerColor> playOrder_List;

    public Model() {
        this.board = new Board();
        playOrder_List = new ArrayList<>();
    }

    public void setPlayOrder(PlayerColor color1, PlayerColor color2, PlayerColor color3){
        playOrder_List.add(color1);
        playOrder_List.add(color2);
        playOrder_List.add(color3);
        turn= playOrder_List.get(0);
    }
    public void setPlayOrder(PlayerColor color1, PlayerColor color2){
        playOrder_List.add(color1);
        playOrder_List.add(color2);
        turn=playOrder_List.get(0);
    }

    public PlayerColor getTurn() {
        return turn;
    }
    public Board getBoardCopy(){
        try{
            return (Board) board.clone();
        }catch (CloneNotSupportedException e){
            System.err.println(e.getMessage());
        }
        return null;
    }
    public Board getBoard() {
        return board;
    }

    public void performMove(PlayerMove move){

        boolean hasWon = (move.getWorker().getWorkerPosition().getLevel()==2) &&
                            ((board.getCell(move.getRow(),move.getColumn())).getLevel()==3);

        board.getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).setFreeSpace(true);
        board.getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).deleteCurrWorker();

        move.getWorker().setWorkerPosition(board.getCell(move.getRow(),move.getColumn()));
        (board.getCell(move.getRow(),move.getColumn())).setFreeSpace(false);
        board.getCell(move.getRow(),move.getColumn()).setCurrWorker(move.getWorker());

        notifyView(move,hasWon);
    }
    public void performBuild(PlayerMove move){
        board.getCell(move.getRow(),move.getColumn()).buildInCell();
        notifyView(move,false);
    }

    public void endNotifyView(PlayerMove move, boolean hasWon){
        /**Questa notify sollecita la update di Player1View e Player2View
         * passando come paramentro la nuova board e il giocatore dell'ultima mossa
         */
        try {
            /*int j=0;
            PlayerColor nextTurn=null;
            while(j<players){
                if(playOrder[j].equals(turn)){
                    nextTurn=playOrder[(j+1)%players];
                    break;
                }
                j++;
            }
            */
            PlayerColor nextTurn = playOrder_List.get((playOrder_List.indexOf(turn)+1)%playOrder_List.size());
            notifyObserver(new MoveMessage((Board) board.clone(), move.getPlayer(), hasWon, nextTurn));
        }catch (CloneNotSupportedException e){
            System.err.println(e.getMessage());
        }
        updateTurn();
    }
    public void notifyView(PlayerMove move, boolean hasWon){
        /**Questa notify sollecita la update di Player1View e Player2View
         * passando come paramentro la nuova board e il giocatore dell'ultima mossa
         */
        try {
            notifyObserver(new MoveMessage((Board) board.clone(), move.getPlayer(), hasWon, turn));
        }catch (CloneNotSupportedException e){
            System.err.println(e.getMessage());
        }
    }

    //metodi che mi controllano se la mossa che voglio fare è possibile
    //mio turno, cella vuota e nelle 8 adiacenti e con un dislivello di massimo 1
    public boolean isReachableCell(PlayerMove move){
        return move.getWorker().getWorkerPosition().isClosedTo(board.getCell(move.getRow(),move.getColumn()));
        /*WITH CARD
          return move.getPlayer().getMyCard().getMoveNear();
        * */
    }
    public boolean isEmptyCell(PlayerMove move){
        return board.getCell(move.getRow(),move.getColumn()).isFree();
    }
    public boolean isLevelDifferenceAllowed(PlayerMove move){
        return (board.getCell(move.getRow(), move.getColumn())).getLevel() - ((move.getWorker().getWorkerPosition()).getLevel()) <= 1;
    }
    public void updateTurn(){
        turn=playOrder_List.get((playOrder_List.indexOf(turn)+1)%playOrder_List.size());
    }
    public void deletePlayer(PlayerMove move){
        if(playOrder_List.size()==2){
            endGamePlayerStuck(move);
            return;
        }
        //3 players and 1 is stuck
        //delete workers of this player from board
        move.getPlayer().getWorker1().clear();
        move.getPlayer().getWorker2().clear();
        //update this.players--
        updateTurn();
        deletePlayerFromGame(move.getPlayer().getColor());
        //update turn
        notifyView(move,false);
        move.getView().reportError(gameMessage.loseMessage);
        //kill thread of move.getPlayer()
    }
    public void deletePlayerFromGame(PlayerColor color){
        playOrder_List.remove(color);
    }
    public void endGamePlayerStuck(PlayerMove move){
        try {
            notifyObserver(new GameOverMessage(move.getPlayer(), (Board) board.clone()));
        }catch (CloneNotSupportedException e){
            System.err.println(e.getMessage());
        }
    }

    public boolean hasWon(PlayerMove move){
        return (move.getWorker().getWorkerPosition().getLevel()==2) &&
                ((board.getCell(move.getRow(),move.getColumn())).getLevel()==3);
    }

    /**Standard Method */
    public void endMessage(PlayerMove message, Turn turn, Model model){
        turn.getPlayerTurn(message.getPlayer()).resetStep();
        model.endNotifyView(message,false);
    }
    public void setStep(PlayerMove move, Turn turn, Model model){
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setType(move.getMoveOrBuild());
        /**EDIT*/
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellFrom(move.getWorker().getWorkerPosition());
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellTo(model.getBoard().getCell(move.getRow(),move.getColumn()));
        turn.getPlayerTurn(move.getPlayer()).updateStep();
    }
    public boolean checkStep(PlayerMove move, Turn turn, Model model){
        setFirstStep(move, turn);
        //set both worker of this player !stuck
        move.getPlayer().getWorker1().setStuck(false);
        move.getPlayer().getWorker2().setStuck(false);
        //turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellFrom(move.getWorker().getWorkerPosition());

        return true;
    }
    public boolean isPlayerStuck(PlayerMove move){
        return move.getPlayer().getWorker1().isStuck()&&move.getPlayer().getWorker2().isStuck();
    }

    public boolean isRightWorker(PlayerMove move, Turn turn){
        if((!turn.getPlayerTurn(move.getPlayer()).isFirstStep()) && !turn.getPlayerTurn(move.getPlayer()).getTurnWorker().equals(move.getWorker())){
            move.getView().reportError(gameMessage.wrongWorker);
            return false;
        }
        return true;
    }

    public void setFirstStep(PlayerMove move, Turn turn){
        if(turn.getPlayerTurn(move.getPlayer()).isFirstStep())
            turn.getPlayerTurn(move.getPlayer()).setTurnWorker(move.getWorker());
    }
    /**end*/

}
