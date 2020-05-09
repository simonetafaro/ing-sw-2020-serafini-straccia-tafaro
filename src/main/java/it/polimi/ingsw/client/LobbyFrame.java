package it.polimi.ingsw.client;

import javax.swing.*;
import java.awt.*;

public class LobbyFrame {

    public LobbyFrame() {
        JFrame mainFrame = new JFrame("Santorini - Lobby");
        mainFrame.setBackground(Color.lightGray);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setVisible(true);
        mainFrame.setSize(1920,1280);
    }
}
