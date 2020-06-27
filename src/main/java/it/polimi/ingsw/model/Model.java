package it.polimi.ingsw.model;

import it.polimi.ingsw.observ.Observable;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.SetWorkerPosition;
import it.polimi.ingsw.utils.gameMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Model (Model View Controller pattern) class contains all the information about the game,
 * such as board, players and rules
 */
public class Model extends Observable<Object> {

    /**
     * Private instance of game board
     */
    private Board board;
    /**
     * List of player in game
     */
    private List<Player> players;
    /**
     * Color of current player
     */
    private PlayerColor turn;
    /**
     * List of playerColor used for turn rotation
     */
    private List<PlayerColor> playOrder_List;
    /**
     * This number represent the number of levels that each player can go up during move step
     */
    private int level;

    public Model() {
        this.board = new Board();
        playOrder_List = new ArrayList<>();
        this.players = new ArrayList<Player>();
        this.level=1;
    }
    /**
     * @param color1
     * @param color2
     * @param color3
     * Populate playOrder_List and assign to turn the first player
     */
    public void setPlayOrder(PlayerColor color1, PlayerColor color2, PlayerColor color3){
        playOrder_List.add(color1);
        playOrder_List.add(color2);
        playOrder_List.add(color3);
        turn= playOrder_List.get(0);
    }

    /**
     * @param color1
     * @param color2
     * Populate playOrder_List and assign to turn the first player
     */
    public void setPlayOrder(PlayerColor color1, PlayerColor color2){
        playOrder_List.add(color1);
        playOrder_List.add(color2);
        turn=playOrder_List.get(0);
    }

    /**
     * @param level
     * Assign int to level (0 or 1)
     */
    protected void setGoUpLevel(int level) {
        this.level = level;
    }

    /**
     * @return turn
     * return current Player color
     */
    public PlayerColor getTurn() {
        return turn;
    }

    /**
     * @return board
     * return board game instance
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @param ID
     * @return player
     * return player identified by ID
     */
    public Player getPlayer(int ID){
        for (Player player : this.players) {
            if(player.getID() == ID)
                return player;
        }
        return null;
    }

    /**
     * @return level
     * return int that means how many level each player can go up during move step
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param move
     * @param hasWon
     * notify RemoteView when player end turn
     */
    public void endNotifyView(PlayerMove move, boolean hasWon){
        PlayerColor nextTurn = playOrder_List.get((playOrder_List.indexOf(turn)+1)%playOrder_List.size());
        notifyObserver(move);
        updateTurn();
    }

    /**
     * @param worker
     * Notify RemoteView when is time to set workers on the board
     */
    public void notifySetWorker(SetWorkerPosition worker){
        notifyObserver(worker);
        players.forEach((player)-> {
            if(player.getID() == worker.getID()){
                if(player.getWorker1() != null && player.getWorker2() != null){
                    if(player.getColor().equals(playOrder_List.get(playOrder_List.size()-1))){
                        notifyObserver(this.players);
                        updateTurn();
                    }else{
                        updateTurnSetupPhase();
                        notifyObserver(getTurn() + "setWorkers");
                    }
                }
            }
        });
    }

    /**
     * @param move
     * @param hasWon
     * notify RemoteView at the end of each Step (move or build)
     */
    public void notifyView(PlayerMove move, boolean hasWon){
        try {
            notifyObserver(new MoveMessage((Board) board.clone(), move.getPlayer(), hasWon, turn));
        }catch (CloneNotSupportedException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param message
     * notify to remoteView
     */
    public void notify(Object message){
        notifyObserver(message);
    }

    /**
     * @param s
     * Notify RemoteView when there are errors during game
     * E.g. : "This cell is too far from your position"..
     */
    public void sendError(String s){
        notifyObserver(s);
    }

    /**
     * @param move
     * @return true if cell where player want to move or build in is near his position
     */
    public boolean isReachableCell(PlayerMove move){
        return move.getWorker().getWorkerPosition().isClosedTo(board.getCell(move.getRow(),move.getColumn()));
    }

    /**
     * @param move
     * @return true if cell in move is free (no worker or dome)
     */
    public boolean isEmptyCell(PlayerMove move){
        return board.getCell(move.getRow(),move.getColumn()).isFree();
    }

    /**
     * @param move
     * @return true if player can move up from his position to the destination cell
     */
    public boolean isLevelDifferenceAllowed(PlayerMove move){
        return ((board.getCell(move.getRow(), move.getColumn())).getLevel() - ((move.getWorker().getWorkerPosition()).getLevel())) <= this.level;
    }

    /**
     * Update Turn during game
     */
    public void updateTurn(){
        turn=playOrder_List.get((playOrder_List.indexOf(turn)+1)%playOrder_List.size());
        notifyObserver(getTurn() + " Is your Turn!");
    }

    /**
     * Update Turn during workers setting phase
     */
    public void updateTurnSetupPhase(){
        turn=playOrder_List.get((playOrder_List.indexOf(turn)+1)%playOrder_List.size());
    }

    /**
     * @param move
     * remove player present in move from game, because he is stuck, so he can't move his workers
     */
    public void deletePlayer(PlayerMove move){
        if(playOrder_List.size()==2){
            endGamePlayerStuck(move);
            return;
        }
        move.getPlayer().getWorker1().clear();
        move.getPlayer().getWorker2().clear();
        updateTurn();
        deletePlayerFromGame(move.getPlayer().getColor());
        notifyView(move,false);
        notify(move.getColor()+" "+gameMessage.loseMessage);

    }

    /**
     * @param color
     * remove playerColor of player that has been deleted from game
     */
    public void deletePlayerFromGame(PlayerColor color){
        playOrder_List.remove(color);
    }

    /**
     * @param move
     * Notify remoteView that game is over because one player is stuck, so the opponent win
     */
    public void endGamePlayerStuck(PlayerMove move){
        try {
            notifyObserver(new GameOverMessage(move.getPlayer(), (Board) board.clone()));
        }catch (CloneNotSupportedException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param move
     * @return true is worker moves up from second to third level
     */
    public boolean hasWon(PlayerMove move){
        return (move.getWorker().getWorkerPosition().getLevel()==2) &&
                ((board.getCell(move.getRow(),move.getColumn())).getLevel()==3);
    }

    public void endMessage(PlayerMove message, Turn turn, Model model){
        turn.getPlayerTurn(message.getPlayer()).resetStep();
        model.endNotifyView(message,false);
    }

    /**
     * @param move
     * @param turn
     * @param model
     * Fill step information after move or build
     */
    public void setStep(PlayerMove move, Turn turn, Model model){
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setType(move.getMoveOrBuild());
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellFrom(move.getWorker().getWorkerPosition());
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellTo(model.getBoard().getCell(move.getRow(),move.getColumn()));
        turn.getPlayerTurn(move.getPlayer()).updateStep();
    }

    /**
     * @param move
     * @param turn
     * @param model
     * @return true
     * Set the Step info and sets both workers of this player not stuck
     */
    public boolean fillStepInfo(PlayerMove move, Turn turn, Model model){
        setFirstStep(move, turn);
        //set both worker of this player !stuck
        move.getPlayer().getWorker1().setStuck(false);
        move.getPlayer().getWorker2().setStuck(false);
        return true;
    }

    /**
     * @param move
     * @return true if both workers of the player are stuck
     */
    public boolean isPlayerStuck(PlayerMove move){
        return move.getPlayer().getWorker1().isStuck() && move.getPlayer().getWorker2().isStuck();
    }

    /**
     * @param move
     * @param turn
     * @return true if worker is the same of previuos step during this turn
     */
    public boolean isRightWorker(PlayerMove move, Turn turn){
        if((!turn.getPlayerTurn(move.getPlayer()).isFirstStep())){
            if(turn.getPlayerTurn(move.getPlayer()).getTurnWorker().getWorkerNum() != move.getWorker().getWorkerNum()) {
                notify(gameMessage.wrongWorker);
                return false;
            }
        }
        return true;
    }

    /**
     * @param move
     * @param turn
     * If it is the first step of this turn sets the worker that must be the same for each step of this turn
     */
    public void setFirstStep(PlayerMove move, Turn turn){
        if(turn.getPlayerTurn(move.getPlayer()).isFirstStep())
            turn.getPlayerTurn(move.getPlayer()).setTurnWorker(move.getWorker());

    }

    /**
     * @param p1
     * @param p2
     * @param p3
     * Populate players list
     */
    public void setPlayers (Player p1, Player p2, Player p3){
        this.players.add(p1);
        this.players.add(p2);
        this.players.add(p3);
    }

    /**
     * @param p1
     * @param p2
     * Populate players list
     */
    public void setPlayers (Player p1, Player p2){
        this.players.add(p1);
        this.players.add(p2);
    }

    /**
     * @param worker
     * Set workers instance to each player
     */
    public void setWorkers(SetWorkerPosition worker){
        players.forEach(player ->{
            if(player.getID() == worker.getID()){
                if(worker.getWorkerNum() == 1)
                    player.setWorker1(new Worker(worker.getID(), this.getBoard().getCell(worker.getX(), worker.getY()), 1, worker.getColor()));
                else
                    player.setWorker2(new Worker(worker.getID(), this.getBoard().getCell(worker.getX(), worker.getY()), 2, worker.getColor()));
            }
        });
    }

    /**
     * @param color
     * @return player that has the same color of parameter color
     */
    public Player getPlayerFromColor(PlayerColor color){
        for (Player player : this.players) {
            if(player.getColor().equals(color))
                return player;
        }
        return null;
    }
}
