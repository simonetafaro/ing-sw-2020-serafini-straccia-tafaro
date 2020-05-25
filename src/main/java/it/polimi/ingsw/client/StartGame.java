package it.polimi.ingsw.client;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.PrintWriter;

public class StartGame{

    private ObjectOutputStream socketObjectOut;
    private ObjectInputStream socketIn;
    private PrintWriter socketout;

    private JFrame mainFrame;
    private JPanel rootPanel;
    private JPanel topPanel;
    private JPanel dataPanel, sxPanel, dxPanel;
    private JLabel dataTitle;
    private JTextField nickname;


    private ImageIcon westPanelImage;
    private Image westPanelImageScaled;
    private Image eastPanelImageScaled;
    private ImageIcon eastPanelImage;


    private Image centralPanelImageScaled;
    private ImageIcon centralPanelImage;


    private JTextField playerTextField;

    private  JRadioButton playButton;
    private ButtonGroup color;
    private JRadioButton grey_button;
    private JRadioButton blue_button;
    private JRadioButton white_button;


    private JTextField dayField, monthField, yearField;
    private Player player;
    private PlayerColor playerColor;

    private ImageIcon blueButton_Icon_No_Available;
    private JLabel blueImage;
    private ImageIcon whiteButton_Icon_No_Available;
    private JLabel whiteImage;
    private ImageIcon greyButton_Icon_No_Available;
    private JLabel greyImage;

    private static final String SRC = "src";
    private static final String MAIN = "main";
    private static final String RESOURCES = "resources";
    private static final String IMAGE = "images";

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
            String playerName = playerTextField.getText();
            String day = dayField.getText();
            String month = monthField.getText();
            String year = yearField.getText();
            int day_value, month_value, year_value;
            if(playerName != null && day!=null && month!=null && year!=null){
                    //TODO check day,month and year value, must be an integer
                    day_value = Integer.parseInt(day);
                    month_value = Integer.parseInt(month);
                    year_value = Integer.parseInt(year);
                    sendObject(new setupMessage(playerName, day_value, month_value, year_value));
            }
        }
    }

    public void sendObject(Object o){
        //TODO handle exception
        try{
            socketObjectOut.reset();
            socketObjectOut.writeObject(o);
            socketObjectOut.flush();
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    public void serverResponse(boolean response){
        if(response){

            new LobbyFrame(mainFrame);
        }
        else{
            if(player.getColor().equals(PlayerColor.BLUE)){
                blue_button.setEnabled(false);
            }
            if(player.getColor().equals(PlayerColor.WHITE)){
                white_button.setEnabled(false);
            }
            if(player.getColor().equals(PlayerColor.GREY)){
                grey_button.setEnabled(false);
            }
        }
    }

    public StartGame(ObjectOutputStream socketObjectOut){

        this.socketObjectOut = socketObjectOut;

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

        JPanel westPanel = new WestJPanel();
        westPanel.setPreferredSize(new Dimension(280, 10));
        westPanel.setOpaque(false);
        rootPanel.add(westPanel, BorderLayout.WEST);

        eastPanelImage = new ImageIcon(PATH + "DX_Panel.png");
        eastPanelImageScaled = new ImageIcon(eastPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();
        JPanel eastPanel = new EastJPanel();
        eastPanel.setPreferredSize(new Dimension(280, 10));
        eastPanel.setOpaque(false);
        rootPanel.add(eastPanel, BorderLayout.EAST);

        centralPanelImage = new ImageIcon(PATH + "Central_Panel_Crop.png");
        centralPanelImageScaled = new ImageIcon(centralPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();
        JPanel centralPanel = new CentralJPanel();
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
        /*
        JLabel colorLabel = new JLabel("CHOOSE YOUR COLOR:");
        colorLabel.setForeground(Color.darkGray);
        gbcLblDate.gridy = 17;
        centralPanel.add(colorLabel, gbcLblDate);

        Image greyButton_image = new ImageIcon(PATH + "G_Workers_No_Shadow.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon greyButton_Icon = new ImageIcon(greyButton_image);
        Image greyButton_image_pressed = new ImageIcon(PATH + "G_Workers.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon greyButton_Icon_Pressed = new ImageIcon(greyButton_image_pressed);
        Image greyButton_image_No_Available = new ImageIcon(PATH + "G_Workers_No_Available.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        greyButton_Icon_No_Available = new ImageIcon(greyButton_image_No_Available);


        Image blueButton_image = new ImageIcon(PATH + "B_Workers_No_Shadow.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon blueButton_Icon = new ImageIcon(blueButton_image);
        Image blueButton_image_pressed = new ImageIcon(PATH + "B_Workers.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon blueButton_Icon_Pressed = new ImageIcon(blueButton_image_pressed);
        Image blueButton_image_No_Available = new ImageIcon(PATH + "B_Workers_No_Available.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        blueButton_Icon_No_Available = new ImageIcon(blueButton_image_No_Available);


        Image whiteButton_image = new ImageIcon(PATH + "W_workers_No_Press.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon whiteButton_Icon = new ImageIcon(whiteButton_image);
        Image whiteButton_image_pressed = new ImageIcon(PATH + "W_Workers.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        ImageIcon whiteButton_Icon_Pressed = new ImageIcon(whiteButton_image_pressed);
        Image whiteButton_image_No_Available = new ImageIcon(PATH + "W_Workers_No_Available.png").getImage().getScaledInstance(144,124, Image.SCALE_SMOOTH);
        whiteButton_Icon_No_Available = new ImageIcon(whiteButton_image_No_Available);

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
        grey_button.setDisabledIcon(greyButton_Icon_No_Available);
        grey_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grey_button.setIcon(greyButton_Icon_Pressed);
                if(!blue_button.getIcon().equals(blueButton_Icon_No_Available))
                    blue_button.setIcon(blueButton_Icon);
                if(!white_button.getIcon().equals(whiteButton_Icon_No_Available))
                    white_button.setIcon(whiteButton_Icon);

            }
        });
        blue_button = new JRadioButton();
        blue_button.setBackground(new Color(0,0,0,0));
        blue_button.setOpaque(false);
        blue_button.setHorizontalAlignment(SwingConstants.CENTER);
        blue_button.setIcon(blueButton_Icon);
        blue_button.setDisabledIcon(blueButton_Icon_No_Available);
        blue_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blue_button.setIcon(blueButton_Icon_Pressed);
                if(!grey_button.getIcon().equals(greyButton_Icon_No_Available))
                    grey_button.setIcon(greyButton_Icon);
                if(!white_button.getIcon().equals(whiteButton_Icon_No_Available))
                    white_button.setIcon(whiteButton_Icon);
            }
        });
        white_button = new JRadioButton();
        white_button.setBackground(new Color(0,0,0,0));
        white_button.setOpaque(false);
        white_button.setHorizontalAlignment(SwingConstants.CENTER);
        white_button.setIcon(whiteButton_Icon);
        white_button.setDisabledIcon(whiteButton_Icon_No_Available);
        white_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!blue_button.getIcon().equals(blueButton_Icon_No_Available))
                    blue_button.setIcon(blueButton_Icon);
                if(!grey_button.getIcon().equals(greyButton_Icon_No_Available))
                    grey_button.setIcon(greyButton_Icon);
                white_button.setIcon(whiteButton_Icon_Pressed);
            }
        });

        gbcWorkerColor.insets = new Insets(0, 150, 5, 150);
        workersColorButton.add(grey_button);
        workersColorButton.add(blue_button);
        workersColorButton.add(white_button);
        centralPanel.add(workersColorButton,gbcWorkerColor);

        color = new ButtonGroup();
        color.add(grey_button);
        color.add(white_button);
        color.add(blue_button);
        */

        playButton = new JRadioButton();

        Image play = new ImageIcon(PATH + "button_play.png").getImage().getScaledInstance(126,141, Image.SCALE_SMOOTH);
        ImageIcon play_button = new ImageIcon(play);
        playButton.setIcon(play_button);
        playButton.setHorizontalAlignment(SwingConstants.CENTER);
        //playButton.setBackground(new Color(0,0,0,0));
        playButton.setOpaque(false);

        playButton.addActionListener(new PlayActionListener());

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

    public void showPopUpNumberPlayer(){
        mainFrame.getContentPane().removeAll();
        mainFrame.update(mainFrame.getGraphics());
        new PopUpNumberPlayer(socketout, mainFrame);
    }

    /*public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        SwingUtilities.invokeLater(new StartGame());
    }*/
}