package it.polimi.ingsw.model;

import it.polimi.ingsw.view.View;

public class PlayerMoveEnd extends PlayerMove {
    private boolean isEndMessage;

    public PlayerMoveEnd(Player player, View view, boolean isEndMessage){
        super(player, view);
        this.isEndMessage= isEndMessage;
    }
}
