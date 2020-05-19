package it.polimi.ingsw.client;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;


public class PopUpNumberPlayer {

    private static final String SRC = "src";
    private static final String MAIN = "main";
    private static final String RESOURCES = "resources";
    private static final String IMAGE = "images";

    private static final String PATH = SRC + File.separatorChar + MAIN + File.separatorChar + RESOURCES + File.separatorChar + IMAGE + File.separatorChar;

    public PopUpNumberPlayer(PrintWriter socketOut, JFrame mainFrame){

        JPanel popUpFrame = new JPanel();
        popUpFrame.setLayout(new GridLayout(2,1));

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        Image titleImage = new ImageIcon(PATH + "playerNumberTitle.png").getImage();
        ImageIcon titleIMG = new ImageIcon(titleImage);
        JLabel title = new JLabel();
        title.setIcon(titleIMG);
        popUpFrame.add(title);

        Image twoButton_image = new ImageIcon(PATH + "2Players.png").getImage().getScaledInstance(188,170, Image.SCALE_SMOOTH);
        ImageIcon twoButton_Icon = new ImageIcon(twoButton_image);

        JRadioButton two_button = new JRadioButton();
        two_button.setBackground(new Color(0,0,0,0));
        two_button.setOpaque(false);
        two_button.setHorizontalAlignment(SwingConstants.CENTER);
        two_button.setIcon(twoButton_Icon);


        Image threeButton_image = new ImageIcon(PATH + "3Players.png").getImage().getScaledInstance(188,170, Image.SCALE_SMOOTH);
        ImageIcon threeButton_Icon = new ImageIcon(threeButton_image);

        JRadioButton three_button = new JRadioButton();
        three_button.setBackground(new Color(0,0,0,0));
        three_button.setOpaque(false);
        three_button.setHorizontalAlignment(SwingConstants.CENTER);
        three_button.setIcon(threeButton_Icon);

        JPanel buttons = new JPanel(new GridLayout(1,2,10,30));

        buttons.add(two_button, SwingConstants.CENTER);
        buttons.add(three_button,SwingConstants.CENTER);
        popUpFrame.add(buttons);
        popUpFrame.setSize(1280,720);
        mainFrame.add(popUpFrame);
        mainFrame.setVisible(true);

        two_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                socketOut.println("2");
                socketOut.flush();
                //mainFrame.remove(popUpFrame);
                new LobbyFrame(mainFrame);
            }
        });

        three_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                socketOut.println("3");
                socketOut.flush();
                //mainFrame.remove(popUpFrame);
                new LobbyFrame(mainFrame);
            }
        });
    }
}
