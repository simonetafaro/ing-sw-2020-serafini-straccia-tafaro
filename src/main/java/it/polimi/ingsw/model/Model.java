package it.polimi.ingsw.model;

import it.polimi.ingsw.observ.Observable;
import it.polimi.ingsw.utils.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class Model extends Observable<MoveMessage> {

    private Board board;
    /*Devo gestire il turno!!
     * Con una variabile color controllo se il color del player che ha giocato è uguale a quello del turno corrente
     * */
    private PlayerColor turn;
    private int players;
    private PlayerColor[] playOrder = new PlayerColor[players];
    private List<PlayerColor> playOrder_List;
    //TODO manage playOrder as a List

    public Model() {
        this.board = new Board();
        playOrder_List = new ArrayList<>();
    }

    public void setPlayOrder(PlayerColor color1, PlayerColor color2, PlayerColor color3){
        /*playOrder[0]=color1;
        playOrder[1]=color2;
        playOrder[2]=color3;
        turn= color1;
        players=3;*/
        //List

        System.out.println("SetPLayerordermodel");
        playOrder_List.add(color1);
        playOrder_List.add(color2);
        playOrder_List.add(color3);
        turn= playOrder_List.get(0);
        players= playOrder_List.size();
    }
    public void setPlayOrder(PlayerColor color1, PlayerColor color2){
        /*playOrder[0]=color1;
        playOrder[1]=color2;
        playOrder[2]=null;
        turn=color1;
        players=2;
        */
        //List
        playOrder_List.add(color1);
        playOrder_List.add(color2);
        turn=playOrder_List.get(0);
        players= playOrder_List.size();
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
        /*
        int i=0;
        while(i<players){
            if(playOrder[i].equals(turn)){
                turn=playOrder[(i+1)%players];
                System.out.println("Ora tocca a "+turn);
                break;
            }
            i++;
        }
        */
        turn=playOrder_List.get((playOrder_List.indexOf(turn)+1)%playOrder_List.size());
        System.out.println("Ora tocca a "+turn);
    }
    public void deletePlayer(PlayerMove move){
        if(players==2){
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

}
