package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

public class HephaestusRuleDecorator extends StandardRuleDecorator {

    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        if(turn.getPlayerTurn(move.getPlayer()).getI()==3){
            //is the second move for this player, so he wants to use the card
            if( (model.getBoard().getCell(move.getRow(),move.getColumn()) != turn.getPlayerTurn(move.getPlayer()).getStepI(2).getCellTo())
                    || model.getBoard().getCell(move.getRow(),move.getColumn()).getLevel()==3 ){
                move.getView().reportError(gameMessage.invalidMoveHephaestus+"\n"+gameMessage.insertAgain);
                return;
            }
            move.getPlayer().getMyCard().setUsingCard(true);
        }
        model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell();
        model.setStep(move, turn, model);
        model.notifyView(move,false);
    }

}
