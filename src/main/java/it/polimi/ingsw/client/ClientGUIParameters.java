package it.polimi.ingsw.client;
import java.io.Serializable;

public class ClientGUIParameters implements Serializable {

    private boolean usingGui;

    public ClientGUIParameters(boolean usingGui) {
        this.usingGui= usingGui;
    }

    private boolean isUsingGui(){
        return usingGui;
    }
}
