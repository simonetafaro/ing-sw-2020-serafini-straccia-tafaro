package it.polimi.ingsw.client;

import java.io.Serializable;

public class CheckServerResponse implements Serializable {

    private boolean checkError;

    public CheckServerResponse(boolean checkError) {
        this.checkError = checkError;
    }

    public boolean isCheckError() {
        return checkError;
    }
}
