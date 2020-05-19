package it.polimi.ingsw.server;

import it.polimi.ingsw.observ.Observer;

import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public interface ClientConnection {
    void closeConnection();

    void addObserver(Observer<String> observer);

    void asyncSend(Object message);
    Socket getSocket();
    void send(Object message);
    String read();
    void getInputFromClient();
    String getNickName();
    void setFirstPlayer(boolean firstPlayer);
}
