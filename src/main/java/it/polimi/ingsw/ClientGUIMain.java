package it.polimi.ingsw;

import it.polimi.ingsw.client.ConnectionManagerSocket;
import it.polimi.ingsw.client.PopUpNumberPlayer;
import it.polimi.ingsw.client.StartGame;
import it.polimi.ingsw.client.showPopUpColor;
import it.polimi.ingsw.utils.CustomDate;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.setupMessage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.ObjectOutputStream;

public class ClientGUIMain implements Runnable{

    private JFrame mainFrame;
    private JPanel rootPanel;
    private ImageIcon westPanelImage;
    private Image westPanelImageScaled;
    private Image eastPanelImageScaled;
    private ImageIcon eastPanelImage;
    private Image centralPanelImageScaled;
    private ImageIcon centralPanelImage;
    private JTextField playerTextField;
    private  JRadioButton playButton;
    private JTextField dayField, monthField, yearField;
    private ImageIcon threeButton_Icon, threeButton_Icon_Pressed;
    private ImageIcon twoButton_Icon, twoButton_Icon_Pressed;


    private ButtonGroup color;
    private JRadioButton grey_button;
    private JRadioButton blue_button;
    private JRadioButton white_button;
    private ImageIcon blueButton_Icon_No_Available;
    private ImageIcon whiteButton_Icon_No_Available;
    private ImageIcon greyButton_Icon_No_Available;

    private JCheckBox two_Players_button;
    private JCheckBox three_Players_button;
    private ButtonGroup playersNumberButtonGroup;

    private static final String SRC = "src";
    private static final String MAIN = "main";
    private static final String RESOURCES = "resources";
    private static final String IMAGE = "images";
    private PlayerColor playerColor;
    private int clientID = 0;
    private int playerNumber;

    private static final String PATH = SRC + File.separatorChar + MAIN + File.separatorChar + RESOURCES + File.separatorChar + IMAGE + File.separatorChar;

    private class EastJPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int updatedWidth = this.getWidth();
            int updatedHeight = this.getHeight();

            if (this.getWidth() - eastPanelImageScaled.getWidth(null) > this
                    .getHeight() - eastPanelImageScaled.getHeight(null)) {
                updatedWidth = updatedHeight
                        * eastPanelImageScaled.getWidth(null)
                        / eastPanelImageScaled.getHeight(null);
            }
            if (this.getWidth() - eastPanelImageScaled.getWidth(null) < this
                    .getHeight() - eastPanelImageScaled.getHeight(null)) {
                updatedHeight = updatedWidth
                        * eastPanelImageScaled.getHeight(null)
                        / eastPanelImageScaled.getWidth(null);
            }

            int x = (this.getWidth() - updatedWidth) / 2;
            int y = (this.getHeight() - updatedHeight) / 2;
            g.drawImage(eastPanelImageScaled, x, y, updatedWidth,
                    updatedHeight, null);
        }
    }

    private class WestJPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int updatedWidth = this.getWidth();
            int updatedHeight = this.getHeight();

            if (this.getWidth() - westPanelImageScaled.getWidth(null) > this
                    .getHeight() - westPanelImageScaled.getHeight(null)) {
                updatedWidth = updatedHeight
                        * westPanelImageScaled.getWidth(null)
                        / westPanelImageScaled.getHeight(null);
            }
            if (this.getWidth() - westPanelImageScaled.getWidth(null) < this
                    .getHeight() - westPanelImageScaled.getHeight(null)) {
                updatedHeight = updatedWidth
                        * westPanelImageScaled.getHeight(null)
                        / westPanelImageScaled.getWidth(null);
            }

            int x = (this.getWidth() - updatedWidth) / 2;
            int y = (this.getHeight() - updatedHeight) / 2;
            g.drawImage(westPanelImageScaled, x, y, updatedWidth,
                    updatedHeight, null);
        }
    }

    private class CentralJPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int updatedWidth = this.getWidth();
            int updatedHeight = this.getHeight();

            if (this.getWidth() - centralPanelImageScaled.getWidth(null) > this
                    .getHeight() - centralPanelImageScaled.getHeight(null)) {
                updatedWidth = updatedHeight
                        * centralPanelImageScaled.getWidth(null)
                        / centralPanelImageScaled.getHeight(null);
            }
            if (this.getWidth() - centralPanelImageScaled.getWidth(null) < this
                    .getHeight() - centralPanelImageScaled.getHeight(null)) {
                updatedHeight = updatedWidth
                        * centralPanelImageScaled.getHeight(null)
                        / centralPanelImageScaled.getWidth(null);
            }

            int x = (this.getWidth() - updatedWidth) / 2;
            int y = (this.getHeight() - updatedHeight) / 2;
            g.drawImage(centralPanelImageScaled, x, y, updatedWidth,
                    updatedHeight, null);
        }
    }

    private class PlayActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ConnectionManagerSocket connectionManagerSocket;

            String playerName = playerTextField.getText();
            String day = dayField.getText();
            String month = monthField.getText();
            String year = yearField.getText();
            int day_value, month_value, year_value, playerNumber = 0;
            CustomDate birthday;
            if(playerName != null){
                if(two_Players_button.isSelected() || three_Players_button.isSelected()){
                    if(day!=null && month!=null && year!=null){
                        //TODO check day,month and year value, must be an integer
                        day_value = Integer.parseInt(day);
                        month_value = Integer.parseInt(month);
                        year_value = Integer.parseInt(year);
                        birthday = new CustomDate(day_value, month_value, year_value);
                        if(two_Players_button.isSelected()){
                            playerNumber= 2;
                            ClientGUIMain.this.playerNumber = 2;
                        }
                        if(three_Players_button.isSelected()){
                            playerNumber= 3;
                            ClientGUIMain.this.playerNumber = 3;
                        }
                        System.out.println("invio i dati");
                        connectionManagerSocket = new ConnectionManagerSocket(playerName, playerColor, birthday, playerNumber);
                        //connectionManagerSocket.setMainFrame(ClientGUIMain.this.mainFrame);
                        connectionManagerSocket.setup();
                        //mainFrame.dispose();
                        //connectionManagerSocket.setColor(mainFrame);
                        showPopUpPlayerColor(connectionManagerSocket);
                    }
                }
            }

        }
    }

    public void showPopUpPlayerColor(ConnectionManagerSocket connectionManagerSocket){
        mainFrame.getContentPane().removeAll();
        mainFrame.update(mainFrame.getGraphics());
        SwingUtilities.invokeLater(new showPopUpColor(mainFrame, connectionManagerSocket));
    }

    public ClientGUIMain(){
        mainFrame = new JFrame("Santorini Game");
        mainFrame.setBackground(Color.lightGray);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        mainFrame.getContentPane().setLayout(gridBagLayout);

        rootPanel = new JPanel();
        rootPanel.setBackground(new Color(0,0,0,60));
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        mainFrame.getContentPane().add(rootPanel, gbcPanel);
        rootPanel.setLayout(new BorderLayout(0, 0));

        westPanelImage = new ImageIcon(PATH + "SX_Panel.png");
        westPanelImageScaled = new ImageIcon(westPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();

        JPanel westPanel = new ClientGUIMain.WestJPanel();
        westPanel.setPreferredSize(new Dimension(280, 10));
        westPanel.setOpaque(false);
        rootPanel.add(westPanel, BorderLayout.WEST);

        eastPanelImage = new ImageIcon(PATH + "DX_Panel.png");
        eastPanelImageScaled = new ImageIcon(eastPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();
        JPanel eastPanel = new ClientGUIMain.EastJPanel();
        eastPanel.setPreferredSize(new Dimension(280, 10));
        eastPanel.setOpaque(false);
        rootPanel.add(eastPanel, BorderLayout.EAST);

        centralPanelImage = new ImageIcon(PATH + "Central_Panel_Crop.png");
        centralPanelImageScaled = new ImageIcon(centralPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();
        JPanel centralPanel = new ClientGUIMain.CentralJPanel();
        centralPanel.setBackground(Color.BLACK);
        centralPanel.setPreferredSize(new Dimension(720, 10));
        centralPanel.setOpaque(true);

        GridBagLayout gblCenterPanel = new GridBagLayout();
        gblCenterPanel.columnWidths = new int[] { 474, 0 };
        gblCenterPanel.rowHeights = new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gblCenterPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gblCenterPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        centralPanel.setLayout(gblCenterPanel);

        JLabel logoLabel = new JLabel();
        logoLabel.setBorder(new EmptyBorder(0, 0, 30, 0));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setIcon(new ImageIcon(PATH + "empty_title.png"));
        logoLabel.setOpaque(false);
        GridBagConstraints gbcLogoLabel = new GridBagConstraints();
        gbcLogoLabel.insets = new Insets(0, 0, 5, 0);
        gbcLogoLabel.anchor = GridBagConstraints.NORTH;
        gbcLogoLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcLogoLabel.gridx = 0;
        gbcLogoLabel.gridy = 0;
        centralPanel.add(logoLabel, gbcLogoLabel);

        JLabel playerLabel = new JLabel("INSERT YOUR NICKNAME:");
        //playerLabel.setFont(fontTitilliumBoldUpright);
        playerLabel.setForeground(Color.DARK_GRAY);
        GridBagConstraints gbcLblName = new GridBagConstraints();
        gbcLblName.insets = new Insets(0, 200, 5, 200);
        gbcLblName.gridx = 0;
        gbcLblName.gridy = 5;
        centralPanel.add(playerLabel, gbcLblName);

        playerTextField = new JTextField();
        playerTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        playerTextField.setHorizontalAlignment(SwingConstants.CENTER);
        playerTextField.setBackground(new Color(214, 217, 223));
        playerTextField.setText("Player");
        GridBagConstraints gbcTxtAsd = new GridBagConstraints();
        gbcTxtAsd.insets = new Insets(0, 200, 5, 200);
        gbcTxtAsd.fill = GridBagConstraints.HORIZONTAL;
        gbcTxtAsd.gridx = 0;
        gbcTxtAsd.gridy = 8;
        centralPanel.add(playerTextField, gbcTxtAsd);
        playerTextField.setColumns(10);

        JLabel dateLabel = new JLabel("INSERT YOUR BIRTHDAY:");
        dateLabel.setForeground(Color.darkGray);
        GridBagConstraints gbcLblDate = new GridBagConstraints();
        gbcLblDate.insets = new Insets(10, 200, 5, 200);
        gbcLblDate.gridx = 0;
        gbcLblDate.gridy = 11;
        centralPanel.add(dateLabel, gbcLblDate);

        JPanel datePickerPanel = new JPanel(new GridLayout(1,3,10,10));
        datePickerPanel.setOpaque(false);

        dayField = new JTextField();
        dayField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        dayField.setHorizontalAlignment(SwingConstants.CENTER);
        dayField.setBackground(new Color(214, 217, 223));
        dayField.setBorder(BorderFactory.createCompoundBorder(
                dayField.getBorder(),
                BorderFactory.createEmptyBorder(0, 0, 0, 15)));
        dayField.setText("Day");
        datePickerPanel.add(dayField);
        monthField = new JTextField();
        monthField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        monthField.setHorizontalAlignment(SwingConstants.CENTER);
        monthField.setBackground(new Color(214, 217, 223));
        monthField.setBorder(BorderFactory.createCompoundBorder(
                monthField.getBorder(),
                BorderFactory.createEmptyBorder(0, 0, 0, 15)));
        monthField.setText("Month");
        datePickerPanel.add(monthField);
        yearField = new JTextField();
        yearField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        yearField.setHorizontalAlignment(SwingConstants.CENTER);
        yearField.setBackground(new Color(214, 217, 223));
        yearField.setText("Year");
        datePickerPanel.add(yearField);

        GridBagConstraints gbcTxtDate = new GridBagConstraints();
        gbcTxtDate.insets = new Insets(0, 200, 5, 200);
        gbcTxtDate.fill = GridBagConstraints.HORIZONTAL;
        gbcTxtDate.gridx = 0;
        gbcTxtDate.gridy = 15;
        centralPanel.add(datePickerPanel, gbcTxtDate);

        JLabel colorLabel = new JLabel("CHOOSE PREFERRED PLAYER NUMBER:");
        colorLabel.setForeground(Color.darkGray);
        gbcLblDate.gridy = 17;
        centralPanel.add(colorLabel, gbcLblDate);

        Image twoButton_image = new ImageIcon(PATH + "2Players.png").getImage();
        twoButton_Icon = new ImageIcon(twoButton_image);
        Image twoButton_image_Pressed = new ImageIcon(PATH + "2Players_pressed.png").getImage();
        twoButton_Icon_Pressed = new ImageIcon(twoButton_image_Pressed);

        Image threeButton_image = new ImageIcon(PATH + "3Players.png").getImage();
        threeButton_Icon = new ImageIcon(threeButton_image);
        Image threeButton_image_pressed = new ImageIcon(PATH + "3Players_pressed.png").getImage();
        threeButton_Icon_Pressed = new ImageIcon(threeButton_image_pressed);

        JPanel playerNumberButtons = new JPanel(new GridLayout(1,2, 15,0));
        playerNumberButtons.setBackground(new Color(0,0,0,0));
        playerNumberButtons.setOpaque(false);

        two_Players_button = new JCheckBox();
        two_Players_button.setBackground(new Color(0,0,0,0));
        two_Players_button.setOpaque(false);
        two_Players_button.setHorizontalAlignment(SwingConstants.CENTER);
        two_Players_button.setIcon(twoButton_Icon);
        two_Players_button.setDisabledIcon(twoButton_Icon_Pressed);

        two_Players_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(two_Players_button.isSelected()){
                    two_Players_button.setIcon(twoButton_Icon_Pressed);
                    three_Players_button.setIcon(threeButton_Icon);
                }else{
                    two_Players_button.setIcon(twoButton_Icon);
                }
            }
        });

        three_Players_button = new JCheckBox();
        three_Players_button.setBackground(new Color(0,0,0,0));
        three_Players_button.setOpaque(false);
        three_Players_button.setHorizontalAlignment(SwingConstants.CENTER);
        three_Players_button.setIcon(threeButton_Icon);
        three_Players_button.setDisabledIcon(threeButton_Icon_Pressed);

        three_Players_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(three_Players_button.isSelected()){
                    three_Players_button.setIcon(threeButton_Icon_Pressed);
                    two_Players_button.setIcon(twoButton_Icon);
                }else{
                    two_Players_button.setIcon(twoButton_Icon);
                }
            }
        });

        playerNumberButtons.add(two_Players_button);
        playerNumberButtons.add(three_Players_button);

        ButtonGroup playerNumber = new ButtonGroup();
        playerNumber.add(two_Players_button);
        playerNumber.add(three_Players_button);

        GridBagConstraints gbcPlayerNumber = new GridBagConstraints();
        gbcPlayerNumber.insets = new Insets(0, 150, 0, 150);
        gbcPlayerNumber.fill = GridBagConstraints.HORIZONTAL;
        gbcPlayerNumber.gridx = 0;
        gbcPlayerNumber.gridy = 20;

        centralPanel.add(playerNumberButtons, gbcPlayerNumber);

        playButton = new JRadioButton();

        Image play = new ImageIcon(PATH + "button_play.png").getImage().getScaledInstance(126,141, Image.SCALE_SMOOTH);
        ImageIcon play_button = new ImageIcon(play);
        playButton.setIcon(play_button);
        playButton.setHorizontalAlignment(SwingConstants.CENTER);
        //playButton.setBackground(new Color(0,0,0,0));
        playButton.setOpaque(false);

        //playButton.addActionListener(new ClientGUIMain.PlayActionListener());

        GridBagConstraints gbcPlayButton = new GridBagConstraints();
        gbcPlayButton.insets = new Insets(0, 240, 15, 240);
        gbcPlayButton.fill = GridBagConstraints.HORIZONTAL;
        gbcPlayButton.gridx = 0;
        gbcPlayButton.gridy = 25;
        centralPanel.add(playButton, gbcPlayButton);

        rootPanel.add(centralPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(1280, 720);
        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);


    }

    @Override
    public void run() {
        playButton.addActionListener(new PlayActionListener());
        //two_Players_button.addActionListener(new PlayerNumberButtonActionListener());
        //three_Players_button.addActionListener(new PlayerNumberButtonActionListener());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ClientGUIMain());
    }
}
