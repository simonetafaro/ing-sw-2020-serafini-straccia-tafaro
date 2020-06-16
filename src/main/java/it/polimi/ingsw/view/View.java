package it.polimi.ingsw.view;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.MoveMessage;
import it.polimi.ingsw.model.PlayerMove;
import it.polimi.ingsw.model.PlayerMoveEnd;
import it.polimi.ingsw.observ.*;
import it.polimi.ingsw.server.ClientConnection;

import java.io.Serializable;

public abstract class View extends Observable<Object> implements Observer<Object>, Serializable {

    private Player player;

    protected View(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }

    protected abstract void showMessage(Object message);


    void handleMove(String MoveOrBuild, int worker, int row, int column) {
        notifyObserver(new PlayerMove(player, worker, row, column, this, MoveOrBuild));
    }

    public void reportError(String message){
        showMessage(message);
    }

    public abstract ClientConnection getClientConnection();

    public void isEndNotify(){
        notifyObserver(new PlayerMoveEnd(getPlayer(),this,true));
    }

    public void writeToClient(Object o){}
}
