package it.polimi.ingsw.model;

import java.util.HashMap;

public class Card {
    private String name;
    public char mossa1;
    public char mossa2;
    public char mossa3;
    private String Move_Free, Move_Near, Move_Level;
    private String Build_Free, Build_Near;
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
        //this.Move_Level="STANDARD";
        //this.Move_Near="STANDARD";
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

    public String getMoveFree(){
        return Move_Free;
    }
    public String getMoveLevel(){
        return Move_Free;
    }
    public String getMoveNear(){
        return Move_Free;
    }

    public char getStepLetter(int i){
        return i==1 ? mossa1 : (i==2 ? mossa2 : mossa3);
    }

    /*  mazzo HashMap<String, boolean>;
    carteScelte HashMap<String, String> //la seconda stringa è il metodo relativo alla carta
            //Es per la carta Apollo ho il metodo setApollo che va a sovrascrivere le regole standard
    carteScelte.get("Demeter")
    chiamiamo questo metodo sul player quando vogliamo settare la carta.

    */
}
