package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Game class is used before that a game starts to initialize
 * {@link Model}, {@link Controller} and {@link RemoteView} class.
 * There is one Model and one Controller per Game, while
 * there is one RemoteView per player.
 */
public class Game {

    /**
     * players List in the game
     */
    private Map<Player, View> players;

    /**
     * Controller of the game
     */
    private Controller controller;

    /**
     * Model of the game, one per each game
     */
    private Model model;

    /**
     * Game constructor for 3 players game in which a match is created
     * @param player1
     * @param player2
     * @param player3
     */
    public Game(Player player1, Player player2, Player player3){
        View player1View = new RemoteView(player1);
        View player2View = new RemoteView(player2);
        View player3View = new RemoteView(player3);
        players = new HashMap<>();
        players.put(player1,player1View);
        players.put(player2,player2View);
        players.put(player3,player3View);
        this.model = new Model();
        this.model.setPlayers(player1, player2, player3);
        this.controller = new Controller(model);
        this.controller.setTurn(new Turn(player1.setMyTurn(),player2.setMyTurn(),player3.setMyTurn()));
        this.model.addObserver(player1View);
        this.model.addObserver(player2View);
        this.model.addObserver(player3View);
        player1View.addObserver(controller);
        player2View.addObserver(controller);
        player3View.addObserver(controller);
        model.setPlayOrder(player1.getColor(), player2.getColor(), player3.getColor());
        controller.setWorkersMessage();
    }

    /**
     * Game constructor for 2 players game in which a match is created
     * @param player1
     * @param player2
     */
    public Game(Player player1, Player player2){
        System.out.println(player1.getID()+" "+ player1.getNickname());
        View player1View = new RemoteView(player1);
        System.out.println(player2.getID()+" "+ player2.getNickname());
        View player2View = new RemoteView(player2);
        players = new HashMap<>();
        players.put(player1,player1View);
        players.put(player2,player2View);
        this.model = new Model();
        this.model.setPlayers(player1, player2);
        this.controller = new Controller(model);
        this.controller.setTurn(new Turn(player1.setMyTurn(),player2.setMyTurn()));
        this.model.addObserver(player1View);
        this.model.addObserver(player2View);
        player1View.addObserver(controller);
        player2View.addObserver(controller);
        model.setPlayOrder(player1.getColor(), player2.getColor());
        controller.setWorkersMessage();
    }

}
