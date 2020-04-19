package it.polimi.ingsw.model;

public class EmptyCardRuleDecorator implements CardRuleDecorator {

    @Override
    public void play(PlayerMove move, Turn turn, Model model) {
        System.out.println("Empty");
    }
}
