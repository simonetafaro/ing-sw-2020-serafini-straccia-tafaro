package it.polimi.ingsw.model;

import it.polimi.ingsw.observ.Observable;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.SetWorkerPosition;
import it.polimi.ingsw.utils.gameMessage;

import java.util.ArrayList;
import java.util.List;

public class Model extends Observable<Object> {

    private Board board;
    private List<Player> players;
    private PlayerColor turn;
    private List<PlayerColor> playOrder_List;
    private int level;

    public Model() {
        this.board = new Board();
        playOrder_List = new ArrayList<>();
        this.players = new ArrayList<Player>();
        this.level=1;
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

    protected void setGoUpLevel(int level) {
        this.level = level;
    }

    public PlayerColor getTurn() {
        return turn;
    }
    public Board getBoard() {
        return board;
    }
    public Player getPlayer(int ID){
        for (Player player : this.players) {
            if(player.getID() == ID)
                return player;
        }
        return null;
    }

    public int getLevel() {
        return level;
    }

    public void endNotifyView(PlayerMove move, boolean hasWon){
        PlayerColor nextTurn = playOrder_List.get((playOrder_List.indexOf(turn)+1)%playOrder_List.size());
        notifyObserver(move);
        updateTurn();
    }
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
    public void notifyView(PlayerMove move, boolean hasWon){
        try {
            notifyObserver(new MoveMessage((Board) board.clone(), move.getPlayer(), hasWon, turn));
        }catch (CloneNotSupportedException e){
            System.err.println(e.getMessage());
        }
    }
    public void notify(Object message){
        notifyObserver(message);
    }

    public void sendError(String s){
        notifyObserver(s);
    }

    public boolean isReachableCell(PlayerMove move){
        return move.getWorker().getWorkerPosition().isClosedTo(board.getCell(move.getRow(),move.getColumn()));
    }
    public boolean isEmptyCell(PlayerMove move){
        return board.getCell(move.getRow(),move.getColumn()).isFree();
    }
    public boolean isLevelDifferenceAllowed(PlayerMove move){
        return ((board.getCell(move.getRow(), move.getColumn())).getLevel() - ((move.getWorker().getWorkerPosition()).getLevel())) <= this.level;
    }

    public void updateTurn(){
        turn=playOrder_List.get((playOrder_List.indexOf(turn)+1)%playOrder_List.size());
        notifyObserver(getTurn() + " Is your Turn!");
    }
    public void updateTurnSetupPhase(){
        turn=playOrder_List.get((playOrder_List.indexOf(turn)+1)%playOrder_List.size());
    }

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

    public void endMessage(PlayerMove message, Turn turn, Model model){
        turn.getPlayerTurn(message.getPlayer()).resetStep();
        model.endNotifyView(message,false);
    }
    public void setStep(PlayerMove move, Turn turn, Model model){
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setType(move.getMoveOrBuild());
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellFrom(move.getWorker().getWorkerPosition());
        turn.getPlayerTurn(move.getPlayer()).getCurrStep().setCellTo(model.getBoard().getCell(move.getRow(),move.getColumn()));
        turn.getPlayerTurn(move.getPlayer()).updateStep();
    }
    public boolean checkStep(PlayerMove move, Turn turn, Model model){
        setFirstStep(move, turn);
        //set both worker of this player !stuck
        move.getPlayer().getWorker1().setStuck(false);
        move.getPlayer().getWorker2().setStuck(false);
        return true;
    }
    public boolean isPlayerStuck(PlayerMove move){
        return move.getPlayer().getWorker1().isStuck() && move.getPlayer().getWorker2().isStuck();
    }

    public boolean isRightWorker(PlayerMove move, Turn turn){
        if((!turn.getPlayerTurn(move.getPlayer()).isFirstStep())){
            if(turn.getPlayerTurn(move.getPlayer()).getTurnWorker().getWorkerNum() != move.getWorker().getWorkerNum()) {
                notify(gameMessage.wrongWorker);
                return false;
            }
        }
        return true;
    }

    public void setFirstStep(PlayerMove move, Turn turn){
        if(turn.getPlayerTurn(move.getPlayer()).isFirstStep())
            turn.getPlayerTurn(move.getPlayer()).setTurnWorker(move.getWorker());

    }
    public void setPlayers (Player p1, Player p2, Player p3){
        this.players.add(p1);
        this.players.add(p2);
        this.players.add(p3);
    }
    public void setPlayers (Player p1, Player p2){
        this.players.add(p1);
        this.players.add(p2);
    }
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

    public Player getPlayerFromColor(PlayerColor color){
        for (Player player : this.players) {
            if(player.getColor().equals(color))
                return player;
        }
        return null;
    }
}
