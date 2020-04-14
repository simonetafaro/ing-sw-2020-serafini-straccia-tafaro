package it.polimi.ingsw.model;

import java.util.HashMap;

public class Card {
    private String name;
    public String mossa1;
    public String mossa2;
    public String mossa3;
    public String mossa4;

    public Card (String name){
        this.name=name;
        this.mossa1=null;
        this.mossa2=null;
        this.mossa3=null;
        this.mossa4=null;
    }

    public void setMossa1(String mossa1) {
        this.mossa1 = mossa1;
    }
    public void setMossa2(String mossa2) {
        this.mossa2 = mossa2;
    }
    public void setMossa3(String mossa3) {
        this.mossa3 = mossa3;
    }
    public void setMossa4(String mossa4) {
        this.mossa4 = mossa4;
    }

    public void setStandardGame(){
        this.mossa1="M"; //M
        this.mossa2="B"; //B
        this.mossa3="END"; //B
    }

    public String getStepLetter(int i){
        return i==1 ? mossa1 : (i==2 ? mossa2 : (i==3 ? mossa3 : mossa4));
    }

    /*  mazzo HashMap<String, boolean>;
    carteScelte HashMap<String, String> //la seconda stringa è il metodo relativo alla carta
            //Es per la carta Apollo.xml ho il metodo setApollo che va a sovrascrivere le regole standard
    carteScelte.get("Demeter.xml")
    chiamiamo questo metodo sul player quando vogliamo settare la carta.

    */
}
