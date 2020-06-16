package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class Turn {

    private Map<Player, PlayerTurn> turns = new HashMap<>();
    private Map<Integer ,PlayerTurn> turnMap = new HashMap<>();

    /*
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
     */

    public Turn(PlayerTurn playerTurn1, PlayerTurn playerTurn2){
        turnMap.put(playerTurn1.getTurnPlayer().getID(), playerTurn1);
        turnMap.put(playerTurn2.getTurnPlayer().getID(), playerTurn2);
    }
    public Turn(PlayerTurn playerTurn1, PlayerTurn playerTurn2, PlayerTurn playerTurn3){
        turnMap.put(playerTurn1.getTurnPlayer().getID(), playerTurn1);
        turnMap.put(playerTurn2.getTurnPlayer().getID(), playerTurn2);
        turnMap.put(playerTurn3.getTurnPlayer().getID(), playerTurn3);
    }
    public PlayerTurn getPlayerTurn(int ID){
        return turnMap.get(ID);
    }
}
