package it.polimi.ingsw.model;

import it.polimi.ingsw.view.View;

public class PlayerMoveEnd extends PlayerMove {
    private boolean isEndMessage;
    //utilizzare ancje per chiedere se viole costruire una cuopla o mossa normale
    private boolean wantToUseCardPower;

    public PlayerMoveEnd(Player player, View view, boolean isEndMessage){
        super(player, view);
        this.isEndMessage= isEndMessage;
    }
}
