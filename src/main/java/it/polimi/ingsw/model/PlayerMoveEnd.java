package it.polimi.ingsw.model;

/**
 * PlayerMoveEnd is a {@link PlayerMove} sent to server
 * by client when he wants to end his turn
 */
public class PlayerMoveEnd extends PlayerMove {

    private static final long serialVersionUID = -6339404514199154791L;

    /**
     * It is true if it's an end turn message
     */
    private boolean isEndMessage;

    /**
     * PlayerMoveEnd constructor with 2 parameters,
     * it inherits the constructor from PlayerMove
     *
     * @param player
     * @param isEndMessage
     */
    public PlayerMoveEnd(Player player, boolean isEndMessage) {
        super(player);
        this.isEndMessage = isEndMessage;
    }


}
