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

    public void writeToClient(Object o){}
}
