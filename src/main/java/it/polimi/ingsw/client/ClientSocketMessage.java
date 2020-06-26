package it.polimi.ingsw.client;

import it.polimi.ingsw.utils.SetWorkerPosition;

/**
 * Cass created to manage ClI and GUI game: It communicates with Server
 * through two Thread in CLI, one active in reading and the other
 * active in writing; while only one in GUI.
 * The player sends his move from writing Thread and then receives message
 * in reading Thread.  It's one per client.
 */
public abstract class ClientSocketMessage {

    /**
     * Empty constructor
     */
    public ClientSocketMessage() {}

    /**
     * Initializes reading Thread in GUI
     */
    public void initialize(){};

    /**
     * @return Thread
     * It initializes reading Thread in CLI
     */
    public Thread initializeCLI(){
        return null;
    };

    /**
     * Reading Thread in GUI
     */
    public void readFromServer(){};

    /**
     * @return Thread
     * reading Thread in CLI
     */
    public Thread readFromServerCLI(){
        return null;
    };

    /**
     * @param o
     * Used in setting initial worker position
     */
    public void updateBoard(SetWorkerPosition o){};
}