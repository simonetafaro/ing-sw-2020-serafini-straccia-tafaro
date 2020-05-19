package it.polimi.ingsw.client;

import javax.swing.*;
import java.awt.*;

public class LobbyFrame {

    public LobbyFrame(JFrame mainFrame) {

        mainFrame.getContentPane().removeAll();
        mainFrame.update(mainFrame.getGraphics());

        JPanel center = new JPanel();
        center.setBackground(Color.RED);
        mainFrame.add(center);
        mainFrame.setVisible(true);
    }
}
