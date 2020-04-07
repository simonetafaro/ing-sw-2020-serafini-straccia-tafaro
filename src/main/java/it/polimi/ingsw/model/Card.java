package it.polimi.ingsw.model;

import java.util.HashMap;

public class Card {
    private String name;
    public char mossa1;
    public char mossa2;
    public char mossa3;
    private String Move_Free, Move_Near, Move_Level;
    private String Win_Condition;

    public Card (String name){
        this.name=name;
        this.mossa1='\0';
        this.mossa2='\0';
        this.mossa3='\0';
        this.Move_Free="board.getCell(move.getRow(),move.getColumn()).isFree()";
        this.Move_Near="";
        this.Move_Level="";
    }

    public void setStandardGame(){
        this.mossa1='M'; //M
        this.mossa2='B'; //B
        this.mossa3=' '; //B
    }
    private void setDemeter(){
        this.mossa1='M'; //M
        this.mossa2='B'; //B
        this.mossa3='B'; //B
    }
    private void setApollo(){
        //standard sequence
        this.Move_Free="!(board.getCell(move.getRow(),move.getColumn()).getLevel()==4)";
    }
    private void setArtemis(){
        this.mossa1='B';
        this.mossa2='M';
        this.mossa3='B';
    }
    private void setAtlas(){

    }
    private void setAthena(){

    }
    private void setHephaestus(){
        this.mossa1='M';
        this.mossa2='B';
        this.mossa3='B';
    }
    private void setMinotaur(){

    }
    private void setPan(){

    }
    private void setPrometheus(){
        this.mossa1='B';
        this.mossa2='M';
        this.mossa3='B';
    }

    public String getMove_Free(){
        return "aaaa";
    }
    public char getStepLetter(int i){
        return i==1 ? mossa1 : (i==2 ? mossa2 : mossa3);
    }

    /*  mazzo HashMap<String, boolean>;
    carteScelte <>
    carteScelte.get("Demeter")
    //player.card=new Card();
    //player.card.setDemeter();

*/
}
