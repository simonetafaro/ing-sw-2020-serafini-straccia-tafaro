package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.FileManager;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import java.lang.reflect.Constructor;

public class CardManager {

    private static final String PATH = "toolcards/";
    private Card myCard;
    private FileManager fileFinder;

    public CardManager(){
        this.fileFinder = new FileManager();
    }
    public void setCardFromFile(Player player, String cardName){
        try {
            Document document = fileFinder.getFileDocument(PATH.concat(cardName).concat(".xml"));

            myCard = new Card(cardName);

            NodeList controllerStepIterator = document.getElementsByTagName("controllerStep");
            for(int i=0; i<controllerStepIterator.getLength(); i++){
                NamedNodeMap a = controllerStepIterator.item(i).getAttributes();

                myCard.addCustomStep(i,a.getNamedItem("State").getNodeValue());
            }
            player.setCard(myCard);


            //Placement Rules PARSING
            CardRuleDecorator cardRuleDecorator;
            NodeList placementRules = document.getElementsByTagName("customRule");

            //Build the placement rule by decorating it with additional rules, following the Decorator Pattern
            //cardRuleDecorator = new EmptyCardRuleDecorator();
            cardRuleDecorator = new StandardRuleDecorator();

            for(int i=0; i<placementRules.getLength(); i++){

                NamedNodeMap attributes = placementRules.item(i).getAttributes();

                //Parse from xml the decoratorName constraint
                String decoratorName = attributes.getNamedItem("decoratorName").getNodeValue();
                    /*
                    Creates a PlacementRule decorator of the specified type in "decoratorName"
                    and then decorates it with the previous rules (default is EmptyPlacementRule)
                    */

                Class<?> currentClass = Class.forName(CardRuleDecorator.class.getPackage().getName()+"."+decoratorName);
                //Constructor<?> currentConstructor = currentClass.getConstructor(CardRuleDecorator.class);
                //cardRuleDecorator = (CardRuleDecorator) currentConstructor.newInstance(cardRuleDecorator);
                Constructor<?> currentConstructor = currentClass.getConstructor();
                cardRuleDecorator = (CardRuleDecorator) currentConstructor.newInstance();
                player.setMyCardMethod(cardRuleDecorator);

            }

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
