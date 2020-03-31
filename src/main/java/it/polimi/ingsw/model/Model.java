package it.polimi.ingsw.model;

import it.polimi.ingsw.observ.Observable;
import it.polimi.ingsw.utils.PlayerColor;

public class Model extends Observable<MoveMessage> {

    private Board board;
    /**Devo gestire il turno!!
     * Con una variabile color controllo se il color del player che ha giocato è uguale a quello del turno corrente
     * */
    private PlayerColor turn;
    private PlayerColor[] playOrder = new PlayerColor[3];
    private int players;

    public Model() {
        this.board = new Board();
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


    //metodo che mi effettua i cambiamenti, setta la cella non free
        //e controlla se il gioco è terminato nel caso avessi un vincitore
    public void performMove(PlayerMove move){

        boolean hasWon = (move.getWorker().getWorkerPosition().getLevel()==2) &&
                            ((board.getCell(move.getRow(),move.getColumn())).getLevel()==3);
        //Check if player with PAN wins
        board.getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).setFreeSpace(true);
        board.getCell(move.getWorker().getWorkerPosition().getPosX(),move.getWorker().getWorkerPosition().getPosY()).deleteCurrWorker();

        move.getWorker().setWorkerPosition(board.getCell(move.getRow(),move.getColumn()));
        (board.getCell(move.getRow(),move.getColumn())).setFreeSpace(false);
        board.getCell(move.getRow(),move.getColumn()).setCurrWorker(move.getWorker());

        /**Questa notify sollecita la update di Player1View e Player2View
         * passando come paramentro la nuova board e il giocatore dell'ultima mossa
         */
        try {
            int j=0;
            PlayerColor nextTurn=null;
            while(j<players){
                if(playOrder[j].equals(turn)){
                    nextTurn=playOrder[(j+1)%players];
                    break;
                }
                j++;
            }
            notify(new MoveMessage((Board) board.clone(), move.getPlayer(), hasWon, nextTurn));
        }catch (CloneNotSupportedException e){
            System.err.println(e.getMessage());
        }

    }

    public void updateTurn(){
        int i=0;
        while(i<players){
            if(playOrder[i].equals(turn)){
                turn=playOrder[(i+1)%players];
                System.out.println("Ora tocca a "+turn);
                break;
            }
            i++;
        }
    }

    public void setPlayOrder(PlayerColor color1, PlayerColor color2, PlayerColor color3){
        playOrder[0]=color1;
        playOrder[1]=color2;
        playOrder[2]=color3;
        turn= color1;
        players=3;
    }
    public void setPlayOrder(PlayerColor color1, PlayerColor color2){
        playOrder[0]=color1;
        playOrder[1]=color2;
        playOrder[2]=null;
        players=2;
        turn=color1;
    }
}