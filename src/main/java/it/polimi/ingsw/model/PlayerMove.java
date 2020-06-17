package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.view.View;

import java.io.Serializable;

public class PlayerMove implements Serializable , Cloneable{

    private int row;
    private int column;
    private Player player;
    private Worker worker;
    private final View view;
    private String MoveOrBuild;
    private PlayerColor color;

    public PlayerMove(String pippo){
        this.MoveOrBuild=pippo;
        this.row=0;
        this.column=0;
        this.player=null;
        this.view=null;
    }
    public PlayerMove(Player player, View view){
        this.row=-1;
        this.column=-1;
        this.player=player;
        this.worker=null;
        this.view=view;
        this.MoveOrBuild=null;
    }

    public PlayerMove(Worker worker, int row, int column) {
        this.worker=worker;
        this.player=null;
        this.row = row;
        this.column = column;
        this.view = null;
    }

    public PlayerMove(Player player, Worker worker,int row, int column) {
        this.worker=worker;
        this.player=player;
        this.row = row;
        this.column = column;
        this.view = null;
    }
    public PlayerMove(Player player, int worker, int row, int column, String moveOrBuild) {
        this.player = null;
        this.color = player.getColor();
        if(worker==1)
            this.worker=player.getWorker1();
        else
            this.worker=player.getWorker2();
        this.row = row;
        this.column = column;
        this.view = null;
        this.MoveOrBuild=moveOrBuild;
    }
    public PlayerMove(Player player){
        this.worker = null;
        this.player = player;
        this.color = player.getColor();
        this.row = -1;
        this.column = -1;
        this.view = null;
    }

    public PlayerMove(Player player, int worker, int row, int column, View view, String moveOrBuild) {
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

    public void setMoveOrBuild(String moveOrBuild){
        this.MoveOrBuild = moveOrBuild;
    }
    public void setWorker(Worker worker){
        this.worker = worker;
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setColumn(int column){
        this.column = column;
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
    public String getMoveOrBuild() {
        return MoveOrBuild;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerColor getColor() {
        return color;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
