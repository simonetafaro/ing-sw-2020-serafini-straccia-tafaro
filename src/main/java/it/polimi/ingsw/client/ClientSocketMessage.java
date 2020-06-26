package it.polimi.ingsw.client;

import it.polimi.ingsw.utils.SetWorkerPosition;


public abstract class ClientSocketMessage {

    public ClientSocketMessage() {
    }

    public void initialize(){};
    public Thread initializeCLI(){
        return null;
    };
    public void readFromServer(){};
    public Thread readFromServerCLI(){
        return null;
    };
    public void updateBoard(SetWorkerPosition o){};
}