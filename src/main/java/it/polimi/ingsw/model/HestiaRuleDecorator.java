package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;
/* in this class we decorate build*/
   /*Hestia effect
   Your Worker may
build one additional time, but this
cannot be on a perimeter space.*/
public class HestiaRuleDecorator extends StandardRuleDecorator{
    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        if(turn.getPlayerTurn(move.getPlayer()).getI()==3){
            //is the second move for this player, so he wants to use the card
            if(checkPerimeterSpace(move,model)){
                model.sendError(move.getColor().toString()+" "+gameMessage.invalidMoveHestia+"\n"+gameMessage.insertAgain);
                return;
            }
            move.getPlayer().getMyCard().setUsingCard(true);
        }
        model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell();
        model.setStep(move, turn, model);
        model.notifyView(move,false);
    }

    /*This method check if a player want do an action in a perimeter space.*/
    public boolean checkPerimeterSpace(PlayerMove move,Model model){
        for(int i=0;i<5;i++)
            if(model.getBoard().getCell(move.getRow(), move.getColumn()).equals(model.getBoard().getCell(i,0))||
                    model.getBoard().getCell(move.getRow(), move.getColumn()).equals(model.getBoard().getCell(i,4))||
                    model.getBoard().getCell(move.getRow(), move.getColumn()).equals(model.getBoard().getCell(0,i))||
                    model.getBoard().getCell(move.getRow(), move.getColumn()).equals(model.getBoard().getCell(4,i))){
                return true;
            }
                return false;

    }
}
