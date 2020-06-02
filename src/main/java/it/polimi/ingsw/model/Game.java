package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private Map<Player, View> players;
    private Controller controller;
    private Model model;

    public Game(Player player1, Player player2, Player player3){
        View player1View = new RemoteView(player1);
        View player2View = new RemoteView(player2);
        View player3View = new RemoteView(player3);
        players = new HashMap<>();
        players.put(player1,player1View);
        players.put(player2,player2View);
        players.put(player3,player3View);
        this.model = new Model();
        this.controller = new Controller(model);
        this.model.addObserver(player1View);
        this.model.addObserver(player2View);
        this.model.addObserver(player3View);
        player1View.addObserver(controller);
        player2View.addObserver(controller);
        player3View.addObserver(controller);

    }

    public Game(Player player1, Player player2){
        System.out.println(player1.getID()+" "+ player1.getNickname());
        View player1View = new RemoteView(player1);
        System.out.println(player2.getID()+" "+ player2.getNickname());
        View player2View = new RemoteView(player2);
        players = new HashMap<>();
        players.put(player1,player1View);
        players.put(player2,player2View);
        this.model = new Model();
        this.controller = new Controller(model);
        this.model.addObserver(player1View);
        this.model.addObserver(player2View);
        player1View.addObserver(controller);
        player2View.addObserver(controller);
    }
}
