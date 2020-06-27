package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.FileManager;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.lang.reflect.Constructor;

public class CardManager {

    private static final String PATH = "toolcards/";
    private FileManager fileFinder;

    public CardManager(){
        this.fileFinder = new FileManager();
    }

    /**
     * @param player
     * @param cardName
     * Assign card information to player from cardName.xml file
     */
    public void setCardFromFile(Player player, String cardName){
        try {
            Document document = fileFinder.getFileDocument(PATH.concat(cardName).concat(".xml"));

            Card myCard = new Card(cardName);

            NodeList controllerStepIterator = document.getElementsByTagName("controllerStep");
            for(int i=0; i<controllerStepIterator.getLength(); i++){
                NamedNodeMap a = controllerStepIterator.item(i).getAttributes();

                myCard.addCustomStep(i,a.getNamedItem("State").getNodeValue());
            }
            player.setCard(myCard);

            CardRuleDecorator cardRuleDecorator;
            NodeList placementRules = document.getElementsByTagName("customRule");

            cardRuleDecorator = new StandardRuleDecorator();

            for(int i=0; i<placementRules.getLength(); i++){

                NamedNodeMap attributes = placementRules.item(i).getAttributes();

                String decoratorName = attributes.getNamedItem("decoratorName").getNodeValue();
                Class<?> currentClass = Class.forName(CardRuleDecorator.class.getPackage().getName()+"."+decoratorName);
                Constructor<?> currentConstructor = currentClass.getConstructor();
                cardRuleDecorator = (CardRuleDecorator) currentConstructor.newInstance();
                player.setMyCardMethod(cardRuleDecorator);

            }

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
