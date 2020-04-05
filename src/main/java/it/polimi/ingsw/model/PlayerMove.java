package it.polimi.ingsw.model;

import it.polimi.ingsw.view.View;

public class PlayerMove {

    /**Row & Column scelti dall'utente dove muovere il worker
     * */
    private final int row;
    private final int column;

    /**Il player che ha fatto la mossa, e il worker scelto (1 o 2)
     * */
    private final Player player;
    private Worker worker;
    /**La view con cui interagisco
     **/
    private final View view;
    private char MoveOrBuild;
    //private boolean usingCard;

    //TODO setting choosed card and add store build position

    public PlayerMove(Player player, View view){
        this.row=-1;
        this.column=-1;
        this.player=player;
        this.worker=null;
        this.view=view;
        this.MoveOrBuild=' ';
    }

    public PlayerMove( Worker worker, int row, int column) {

        this.worker=worker;
        this.player=null;
        this.row = row;
        this.column = column;
        this.view = null;
    }
    /**
     * With this attribute I can check if the user during this turn wants to use his card's power
     * */


    public PlayerMove(Player player, int worker, int row, int column, View view, char moveOrBuild) {
        this.player = player;
        if(worker==1)
            this.worker=player.getWorker1();
        else
            this.worker=player.getWorker2();
        this.row = row;
        this.column = column;
        this.view = view;
        this.MoveOrBuild=moveOrBuild;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public Player getPlayer() {
        return player;
    }
    public View getView() {
        return view;
    }
    public Worker getWorker() {
        return worker;
    }
    public char getMoveOrBuild() {
        return MoveOrBuild;
    }
}
