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

            System.out.println("SetCardFromFile");
            NodeList controllerStepIterator = document.getElementsByTagName("controllerStep");
            for(int i=0; i<controllerStepIterator.getLength(); i++){
                NamedNodeMap a = controllerStepIterator.item(i).getAttributes();
                System.out.println("Mossa"+(i+1)+"="+a.getNamedItem("State").getNodeValue());
                switch (i){
                    case 0:
                        myCard.setMossa1(a.getNamedItem("State").getNodeValue());
                        break;
                    case 1:
                        myCard.setMossa2(a.getNamedItem("State").getNodeValue());
                        break;
                    case 2:
                        myCard.setMossa3(a.getNamedItem("State").getNodeValue());
                        break;
                    case 3:
                        myCard.setMossa4(a.getNamedItem("State").getNodeValue());
                        break;
                }
            }
            player.setCard(myCard);


            //Placement Rules PARSING
            CardRuleDecorator cardRuleDecorator;
            NodeList placementRules = document.getElementsByTagName("placementRule");

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
