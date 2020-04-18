package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.List;

public class Card {
    private String name;
    private String mossa1;
    private String mossa2;
    private String mossa3;
    private String mossa4;

    private String StandardM1= "M", StandardM2= "B", StandardM3="END";

    private List<String> MosseCarta;
    private List<String> MosseStandard;

    private boolean usingCard;

    public Card (String name){
        this.name=name;
        this.mossa1=null;
        this.mossa2=null;
        this.mossa3=null;
        this.mossa4=null;
        this.usingCard=false;
    }

    public boolean isUsingCard() {
        return usingCard;
    }

    public void setUsingCard(boolean usingCard) {
        this.usingCard = usingCard;
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

    public String getStandardStepLetter(int i){
        return i==1 ? StandardM1 : (i==2 ? StandardM2 : StandardM3);
    }

}
