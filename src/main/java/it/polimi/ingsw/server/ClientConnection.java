package it.polimi.ingsw.server;

import it.polimi.ingsw.observ.Observer;

import java.net.Socket;

public interface ClientConnection {
    void closeConnection();

    void addObserver(Observer<String> observer);

    void asyncSend(Object message);
    Socket getSocket();
    void send(Object message);
    String read();
}
