package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class Turn {

    private Map<Player, PlayerTurn> turns = new HashMap<>();

    public Turn (PlayerTurn playerTurn){
        turns.put(playerTurn.getTurnPlayer(),playerTurn);
    }
    public Turn(PlayerTurn playerTurn1, PlayerTurn playerTurn2){
        turns.put(playerTurn1.getTurnPlayer(), playerTurn1);
        turns.put(playerTurn2.getTurnPlayer(), playerTurn2);
    }
    public Turn(PlayerTurn playerTurn1, PlayerTurn playerTurn2, PlayerTurn playerTurn3){
        turns.put(playerTurn1.getTurnPlayer(), playerTurn1);
        turns.put(playerTurn2.getTurnPlayer(), playerTurn2);
        turns.put(playerTurn3.getTurnPlayer(), playerTurn3);
    }
    public PlayerTurn getPlayerTurn(Player player){
        return turns.get(player);
    }

}
