package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.gameMessage;

public class AtlasRuleDecorator extends StandardRuleDecorator {

    @Override
    public void build(PlayerMove move, Model model, Turn turn) {
        System.out.println("Atlas Build");
        move.getView().getClientConnection().send(gameMessage.atlasCard);
        String buildDome = move.getView().getClientConnection().read();
        if(buildDome.toUpperCase().equals("YES"))
            model.getBoard().getCell(move.getRow(),move.getColumn()).setLevel(4);
        else
            model.getBoard().getCell(move.getRow(),move.getColumn()).buildInCell();

        model.setStep(move, turn, model);
        model.notifyView(move,false);
    }
}
