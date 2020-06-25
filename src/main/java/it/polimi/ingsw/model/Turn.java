package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Turn class represents one round of the game from first player to the
 * last one in order of play. It contains three or two pointer to
 * {@link PlayerTurn} and the corresponding {@link Player}
 */
public class Turn {

    /**
     * hashMap that relates player and his playerTurn
     */
    private Map<Player, PlayerTurn> turns = new HashMap<>();

    /**
     * Constructor with one parameter
     * @param playerTurn
     */
    public Turn (PlayerTurn playerTurn){
        turns.put(playerTurn.getTurnPlayer(),playerTurn);
    }

    /**
     * Constructor with two parameters for two players game
     * @param playerTurn1
     * @param playerTurn2
     */
    public Turn(PlayerTurn playerTurn1, PlayerTurn playerTurn2){
        turns.put(playerTurn1.getTurnPlayer(), playerTurn1);
        turns.put(playerTurn2.getTurnPlayer(), playerTurn2);
    }

    /**
     * Constructor with three parameters for three players game
     * @param playerTurn1
     * @param playerTurn2
     * @param playerTurn3
     */
    public Turn(PlayerTurn playerTurn1, PlayerTurn playerTurn2, PlayerTurn playerTurn3){
        turns.put(playerTurn1.getTurnPlayer(), playerTurn1);
        turns.put(playerTurn2.getTurnPlayer(), playerTurn2);
        turns.put(playerTurn3.getTurnPlayer(), playerTurn3);
    }

    /**
     * @param player
     * @return PlayerTurn of the corresponding Player
     */
    public PlayerTurn getPlayerTurn(Player player){
        return turns.get(player);
    }

}
