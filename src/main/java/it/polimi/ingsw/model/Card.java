package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Card {
    private String name;
    private ArrayList<String> customSteps;
    private ArrayList<String> standardStep;
    private boolean usingCard;

    public Card (String name){
        this.name=name;
        this.usingCard=false;
        this.standardStep= new ArrayList<>();
        this.standardStep.add(0,"M");
        this.standardStep.add(1,"B");
        this.standardStep.add(2,"END");
        this.customSteps = new ArrayList<>();
    }

    public boolean isUsingCard() {
        return usingCard;
    }

    public void setUsingCard(boolean usingCard) {
        this.usingCard = usingCard;
    }

    public void addCustomStep(int i, String customStep){
        this.customSteps.add(i, customStep);
    }

    public String getName() {
        return name;
    }

    public String getStepLetter(int i){
        return customSteps.get(i-1);
        //return i==1 ? CustomM1 : (i==2 ? CustomM2 : (i==3 ? CustomM3 : CustomM4));
    }

    public String getStandardStepLetter(int i){
        return standardStep.get(i-1);
        //return i==1 ? StandardM1 : (i==2 ? StandardM2 : StandardM3);
    }

}
