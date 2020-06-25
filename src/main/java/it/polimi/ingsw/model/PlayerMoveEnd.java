package it.polimi.ingsw.model;

import it.polimi.ingsw.view.View;

/**
 * PlayerMoveEnd is a {@link PlayerMove} sent to server
 * by client when he wants to end his turn
 */
public class PlayerMoveEnd extends PlayerMove {

    /**
     * It is true if it's an end turn message
     */
    private boolean isEndMessage;

    /**
     * PlayerMoveEnd constructor with 3 parameters
     * @param player
     * @param view
     * @param isEndMessage
     */
    public PlayerMoveEnd(Player player, View view, boolean isEndMessage){
        super(player, view);
        this.isEndMessage= isEndMessage;
    }

    /**
     * PlayerMoveEnd constructor with 2 parameters,
     * it inherits the constructor from PlayerMove
     * @param player
     * @param isEndMessage
     */
    public PlayerMoveEnd(Player player, boolean isEndMessage){
        super(player);
        this.isEndMessage= isEndMessage;
    }



}
