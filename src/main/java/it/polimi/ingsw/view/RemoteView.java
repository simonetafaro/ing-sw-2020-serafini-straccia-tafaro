package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RemoteView extends View implements Serializable {

    private transient ObjectInputStream input;
    private transient ObjectOutputStream output;
    private transient int id;
    private transient Thread readThread;

    public RemoteView(Player player) {
        super(player);
        this.id = player.getID();
        this.input = player.getInput();
        this.output = player.getOutput();
        this.readThread = readFromClient();
        readThread.start();
    }

    public Thread readFromClient() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Object o = RemoteView.this.input.readObject();
                        notifyObserver(o);
                    }

                } catch (ClassNotFoundException e) {
                    System.err.println(e.getMessage());
                } catch (IOException e) {
                    clientCloseConnection();
                }
            }
        });
        return t;
    }

    public void writeToClient(Object o) {
        try {
            this.output.reset();
            this.output.writeObject(o);
            this.output.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Object message) {
        writeToClient(message);
    }

    public void clientCloseConnection() {
        notifyObserver("quitGameClientCloseConnection");
    }
}
