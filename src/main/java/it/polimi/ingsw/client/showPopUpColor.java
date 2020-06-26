package it.polimi.ingsw.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class showPopUpColor implements Runnable{


    private ConnectionManagerSocket connectionManagerSocket;

    private JRadioButton grey_button;
    private JRadioButton blue_button;
    private JRadioButton white_button;
    private JFrame mainFrame;
    private static final String SRC = "src";
    private static final String MAIN = "main";
    private static final String RESOURCES = "resources";
    private static final String IMAGE = "images";
    private static final String PATH = SRC + File.separatorChar + MAIN + File.separatorChar + RESOURCES + File.separatorChar + IMAGE + File.separatorChar;

    private class WhiteColor implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            connectionManagerSocket.setColor("WHITE", showPopUpColor.this);
        }
    }
    private class BlueColor implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            connectionManagerSocket.setColor("BLUE", showPopUpColor.this);
        }
    }
    private class GreyColor implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            connectionManagerSocket.setColor("GREY", showPopUpColor.this);
        }

    }

    public showPopUpColor(JFrame mainFrame, ConnectionManagerSocket connectionManagerSocket){
        this.connectionManagerSocket = connectionManagerSocket;
        this.mainFrame = mainFrame;
        JPanel popUpFrame = new JPanel();
        popUpFrame.setLayout(new GridLayout(2,1));

        Image titleImage = new ImageIcon(PATH + "chooseYourColor.png").getImage();
        ImageIcon titleIMG = new ImageIcon(titleImage);
        JLabel title = new JLabel();
        title.setIcon(titleIMG);
        popUpFrame.add(title);

        Image greyButton_image = new ImageIcon(PATH + "G_Workers_No_Shadow.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon greyButton_Icon = new ImageIcon(greyButton_image);
        Image greyButton_image_pressed = new ImageIcon(PATH + "G_Workers.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon greyButton_Icon_Pressed = new ImageIcon(greyButton_image_pressed);
        Image greyButton_image_No_Available = new ImageIcon(PATH + "G_Workers_No_Available.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon greyButton_Icon_No_Available = new ImageIcon(greyButton_image_No_Available);

        Image blueButton_image = new ImageIcon(PATH + "B_Workers_No_Shadow.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon blueButton_Icon = new ImageIcon(blueButton_image);
        Image blueButton_image_pressed = new ImageIcon(PATH + "B_Workers.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon blueButton_Icon_Pressed = new ImageIcon(blueButton_image_pressed);
        Image blueButton_image_No_Available = new ImageIcon(PATH + "B_Workers_No_Available.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon blueButton_Icon_No_Available = new ImageIcon(blueButton_image_No_Available);

        Image whiteButton_image = new ImageIcon(PATH + "W_workers_No_Press.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon whiteButton_Icon = new ImageIcon(whiteButton_image);
        Image whiteButton_image_pressed = new ImageIcon(PATH + "W_Workers.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon whiteButton_Icon_Pressed = new ImageIcon(whiteButton_image_pressed);
        Image whiteButton_image_No_Available = new ImageIcon(PATH + "W_Workers_No_Available.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon whiteButton_Icon_No_Available = new ImageIcon(whiteButton_image_No_Available);

        GridBagConstraints gbcWorkerColor = new GridBagConstraints();
        gbcWorkerColor.insets = new Insets(0, 150, 0, 150);
        gbcWorkerColor.fill = GridBagConstraints.HORIZONTAL;
        gbcWorkerColor.gridx = 0;
        gbcWorkerColor.gridy = 20;


        JPanel workersColorButton = new JPanel(new GridLayout(1,3, 25,0));
        workersColorButton.setBackground(new Color(0,0,0,0));
        workersColorButton.setOpaque(false);

        grey_button = new JRadioButton();
        grey_button.setBackground(new Color(0,0,0,0));
        grey_button.setOpaque(false);
        grey_button.setHorizontalAlignment(SwingConstants.CENTER);
        grey_button.setIcon(greyButton_Icon);
        grey_button.setPressedIcon(greyButton_Icon_Pressed);
        grey_button.setDisabledIcon(greyButton_Icon_No_Available);

        blue_button = new JRadioButton();
        blue_button.setBackground(new Color(0,0,0,0));
        blue_button.setOpaque(false);
        blue_button.setHorizontalAlignment(SwingConstants.CENTER);
        blue_button.setIcon(blueButton_Icon);
        blue_button.setPressedIcon(blueButton_Icon_Pressed);
        blue_button.setDisabledIcon(blueButton_Icon_No_Available);

        white_button = new JRadioButton();
        white_button.setBackground(new Color(0,0,0,0));
        white_button.setOpaque(false);
        white_button.setHorizontalAlignment(SwingConstants.CENTER);
        white_button.setIcon(whiteButton_Icon);
        white_button.setPressedIcon(whiteButton_Icon_Pressed);
        white_button.setDisabledIcon(whiteButton_Icon_No_Available);

        gbcWorkerColor.insets = new Insets(0, 150, 5, 150);
        workersColorButton.add(grey_button);
        workersColorButton.add(blue_button);
        workersColorButton.add(white_button);
        popUpFrame.add(workersColorButton,gbcWorkerColor);

        ButtonGroup color = new ButtonGroup();
        color.add(grey_button);
        color.add(white_button);
        color.add(blue_button);

        popUpFrame.setSize(1280,720);
        this.mainFrame.add(popUpFrame);
        this.mainFrame.setVisible(true);
        this.mainFrame.setEnabled(true);
        this.mainFrame.setSize(1280, 720);
    }

    public void closeGUI() throws IOException {
        mainFrame.getContentPane().removeAll();
        connectionManagerSocket.setMainFrame(mainFrame);
        connectionManagerSocket.waitForFirstPlayer();
        mainFrame.update(mainFrame.getGraphics());
    }

    public void lock(String color){
        switch (color){
            case "blue": blue_button.setEnabled(false);
                            break;
            case "white": white_button.setEnabled(false);
                            break;
            case "grey": grey_button.setEnabled(false);
                            break;
        }
    }

    @Override
    public void run() {
        blue_button.addActionListener(new BlueColor());
        white_button.addActionListener(new WhiteColor());
        grey_button.addActionListener(new GreyColor());
        connectionManagerSocket.receiveColorResponse(this);
    }
}
