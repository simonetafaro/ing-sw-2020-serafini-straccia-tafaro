package it.polimi.ingsw.client;

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

    }
}
