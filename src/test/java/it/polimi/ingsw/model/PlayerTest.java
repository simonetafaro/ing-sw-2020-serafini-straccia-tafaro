package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.CustomDate;
import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player = new Player();

    @Test
    void setColor() {
        player.setColor(PlayerColor.GREY);
        assertEquals(player.getColor(),PlayerColor.GREY);
    }

    @Test
    void setBirthDate() {
        CustomDate date = new CustomDate();
        date.setDay(32);
        date.setMonth(32);
        date.setYear(32);

        player.setBirthDate(date);
        assertEquals(player.getBirthDate(),date);
    }

    @Test
    void setNickname() {
        player.setNickname("Pippo");

        assertEquals(player.getNickname(),"Pippo");
    }

    @Test
    void setWorker1() {
        Worker worker1 = new Worker();
        player.setWorker1(worker1);

        assertEquals(player.getWorker1(),worker1);
    }

    @Test
    void setWorker2() {
        Worker worker2 = new Worker();
        player.setWorker2(worker2);

        assertEquals(player.getWorker2(),worker2);
    }

    @Test
    void setCard() {
        Card card = new Card("Apollo");
        player.setCard(card);

        assertEquals(player.getMyCard(),card);
    }

    @Test
    void setMyCardMethod() {
        CardRuleDecorator cardRuleDecorator = new StandardRuleDecorator();
        player.setMyCardMethod(cardRuleDecorator);

        assertEquals(player.getMyCardMethod(),cardRuleDecorator);
    }

    @Test
    void getNickname() {
        player.setNickname("Pippo");

        assertEquals(player.getNickname(),"Pippo");
    }

    @Test
    void getWorker1() {
        Worker worker1 = new Worker();
        player.setWorker1(worker1);

        assertEquals(player.getWorker1(),worker1);
    }

    @Test
    void getWorker2() {
        Worker worker2 = new Worker();
        player.setWorker2(worker2);

        assertEquals(player.getWorker2(),worker2);
    }

    @Test
    void getColor() {
        player.setColor(PlayerColor.GREY);
        assertEquals(player.getColor(),PlayerColor.GREY);
    }

    @Test
    void getBirthDate() {
        CustomDate date = new CustomDate();
        date.setDay(32);
        date.setMonth(32);
        date.setYear(32);

        player.setBirthDate(date);
        assertEquals(player.getBirthDate(),date);
    }

    @Test
    void getMyCardMethod() {
        CardRuleDecorator cardMethod = new StandardRuleDecorator();
        player.setMyCardMethod(cardMethod);
        assertEquals(player.getMyCardMethod(),cardMethod);
    }

    @Test
    void getMyCard() {
        Card card = new Card("Apollo");
        player.setCard(card);

        assertEquals(player.getMyCard(),card);
    }


}