package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RemoteView extends View implements Serializable{

    private transient ObjectInputStream input;
    private transient ObjectOutputStream output;
    private transient int id;
    private transient Thread readThread;

    public RemoteView(Player player){
        super(player);
        this.id = player.getID();
        this.input= player.getInput();
        this.output = player.getOutput();
        this.readThread = readFromClient();
        readThread.start();
    }

    public Thread readFromClient(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(true){
                        Object o = RemoteView.this.input.readObject();
                        notifyObserver(o);
                    }

                } catch (IOException | ClassNotFoundException e){
                    System.err.println(e.getMessage());
                }
            }
        });
        return t;
    }

    public void writeToClient(Object o){
        try{
            this.output.reset();
            this.output.writeObject(o);
            this.output.flush();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Object message) {

        writeToClient(message);
        /*
        if(message instanceof MoveMessage){
            String resultMsg = "";

            try {
                showMessage(((MoveMessage) message).getBoard().clone());
            }catch (CloneNotSupportedException e){
                System.err.println(e.getMessage());
            }


            if(message instanceof GameOverMessage){
                if(((MoveMessage) message).getPlayer() == getPlayer())
                    resultMsg = gameMessage.loseMessage + "\n";
                else
                    resultMsg = gameMessage.winMessage + "\n";
                showMessage(resultMsg);
                return;
            }

            if (((MoveMessage) message).isHasWon()) {
                if (((MoveMessage) message).getPlayer() == getPlayer()) {


                    resultMsg = gameMessage.winMessage + "\n";
                } else {
                    resultMsg = gameMessage.loseMessage + "\n";
                }
            }
            else {
                if(((MoveMessage) message).getNextTurn()== getPlayer().getColor())
                    resultMsg += gameMessage.TurnMessage;
                else
                    resultMsg += gameMessage.waitMessage;
            }
            showMessage(resultMsg);

        }

         */
        /*Update chiamata dalla notify del model quando effettuo un cambiamento sul model
         * Il paramentro che ricevo contiene la nuova board aggiornata,
         * */
        //Mostro il nuovo campo di gioco aggiornato
        }

}
