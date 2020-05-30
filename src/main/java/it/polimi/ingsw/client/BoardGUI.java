package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerMove;

import javax.swing.*;

public class BoardGUI implements Runnable{

    private JFrame mainframe;
    private ConnectionManagerSocket connectionManagerSocket;


    public BoardGUI(JFrame mainframe, ConnectionManagerSocket connectionManagerSocket){
        this.mainframe = mainframe;
        this.connectionManagerSocket = connectionManagerSocket;

    }



    @Override
    public void run() {
        this.connectionManagerSocket.initializeMessageSocket();
        PlayerMove playermove =new PlayerMove("test"+connectionManagerSocket.getclientID());
        this.connectionManagerSocket.sendToServer(playermove);

    }
}
