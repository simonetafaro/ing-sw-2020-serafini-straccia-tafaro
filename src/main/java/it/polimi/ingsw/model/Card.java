package it.polimi.ingsw.model;

import java.util.List;

public class Card {
    private String name;
    //TODO this will be a list
    private String CustomM1;
    private String CustomM2;
    private String CustomM3;
    private String CustomM4;

    private String StandardM1= "M", StandardM2= "B", StandardM3="END";

    private List<String> MosseCarta;
    private List<String> MosseStandard;

    private boolean usingCard;

    public Card (String name){
        this.name=name;
        this.CustomM1 =null;
        this.CustomM2 =null;
        this.CustomM3 =null;
        this.CustomM4 =null;
        this.usingCard=false;
    }

    public boolean isUsingCard() {
        return usingCard;
    }

    public void setUsingCard(boolean usingCard) {
        this.usingCard = usingCard;
    }

    public void setCustomM1(String customM1) {
        this.CustomM1 = customM1;
    }
    public void setCustomM2(String customM2) {
        this.CustomM2 = customM2;
    }
    public void setCustomM3(String customM3) {
        this.CustomM3 = customM3;
    }
    public void setCustomM4(String customM4) {
        this.CustomM4 = customM4;
    }

    public void setStandardGame(){
        this.CustomM1 ="M"; //M
        this.CustomM2 ="B"; //B
        this.CustomM3 ="END"; //B
    }

    public String getName() {
        return name;
    }

    public String getStepLetter(int i){
        return i==1 ? CustomM1 : (i==2 ? CustomM2 : (i==3 ? CustomM3 : CustomM4));
    }

    public String getStandardStepLetter(int i){
        return i==1 ? StandardM1 : (i==2 ? StandardM2 : StandardM3);
    }

}
