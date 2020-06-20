package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PlayerMove;
import it.polimi.ingsw.utils.SetWorkerPosition;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public abstract class ClientSocketMessage {

    public ClientSocketMessage() {
    }

    public void initialize(){};
    public Thread initializeCLI(){
        return null;
    };
    public void parseInput(Object o){}
    public void send (PlayerMove playerMove){ }
    public void sendString (String playerMove){}
    public void readFromServer(){};
    public Thread readFromServerCLI(){
        return null;
    };
    public void updateBoard(SetWorkerPosition o){};
}