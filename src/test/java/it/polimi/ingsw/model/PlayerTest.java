package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.PlayerColor;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player = new Player();

    @Test
    void setColor() {
        player.setColor(PlayerColor.GREY);
        assertEquals(player.getColor(),PlayerColor.GREY);
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

    @Test
    void getInput() {
        try {
            Socket socket = new Socket();
            Player player = new Player();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            player.setInput(inputStream);

            assertEquals(inputStream,player.getInput());
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Test
    void setInput() {
        try {
            Socket socket = new Socket();
            Player player = new Player();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            player.setInput(inputStream);

            assertEquals(inputStream,player.getInput());
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Test
    void getOutput() {
        try {
            Socket socket = new Socket();
            Player player = new Player();
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            player.setOutput(outputStream);

            assertEquals(outputStream,player.getOutput());
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Test
    void setOutput() {
        try {
            Socket socket = new Socket();
            Player player = new Player();
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            player.setOutput(outputStream);

            assertEquals(outputStream,player.getOutput());
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Test
    void getID() {
        Socket socket = new Socket();
        Player player = new Player(1,"Apollo",socket);

        assertEquals(player.getID(),1);
        assertNotEquals(player.getID(),2);
        assertNotEquals(player.getID(),3);

    }
}