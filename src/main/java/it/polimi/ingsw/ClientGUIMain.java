package it.polimi.ingsw;

import it.polimi.ingsw.client.ConnectionManagerSocket;
import it.polimi.ingsw.client.showPopUpColor;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Class used to initialize GUI Game. Main for players who want to play with GUI.
 * It creates starting GUI. Starting GUI is divided into three parts with three JPanel.
 * East and West Panel have a decorative purpose. In the Central Panel there are
 * starting parameters choice: nickname, server IP and if player wants to play a
 * two or three players match
 */
public class ClientGUIMain implements Runnable {

    /**
     * Frame of the window
     */
    private JFrame mainFrame;

    /**
     * three Panels images and icons
     */
    private JPanel rootPanel;
    private ImageIcon westPanelImage;
    private Image westPanelImageScaled;
    private Image eastPanelImageScaled;
    private ImageIcon eastPanelImage;
    private Image centralPanelImageScaled;
    private ImageIcon centralPanelImage;

    /**
     * Nickname player text and server IP text choices
     */
    private JTextField playerTextField, serverIpField;

    /**
     * play JButton, two players match and three players match JCheckBox
     * and their ImageIcons
     */
    private JRadioButton playButton;
    private ImageIcon threeButton_Icon, threeButton_Icon_Pressed;
    private ImageIcon twoButton_Icon, twoButton_Icon_Pressed;
    private JCheckBox two_Players_button;
    private JCheckBox three_Players_button;

    /**
     * Internal class created for the east JPanel,
     * It modifies height and width JPanel image
     */
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

    /**
     * Internal class created for the west JPanel,
     * It modifies height and width JPanel image
     */
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

    /**
     * Internal class created for the central JPanel,
     * It modifies height and width JPanel image
     */
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

    /**
     * Listener to play button
     */
    private class PlayActionListener implements ActionListener {

        /**
         * @param e
         * When a player clicks play button, it controls if all parameters are set
         * then it initializes ConnectionManagerSocket
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            ConnectionManagerSocket connectionManagerSocket;
            String serverIP = serverIpField.getText();
            String playerName = playerTextField.getText();
            if (serverIP.equals(""))
                return;
            int playerNumber = 0;
            if (playerName != null) {
                if (two_Players_button.isSelected() || three_Players_button.isSelected()) {
                    if (two_Players_button.isSelected()) {
                        playerNumber = 2;
                    }
                    if (three_Players_button.isSelected()) {
                        playerNumber = 3;
                    }
                    connectionManagerSocket = new ConnectionManagerSocket(playerName, playerNumber);
                    connectionManagerSocket.setServerData(serverIP);
                    connectionManagerSocket.setMainFrame(ClientGUIMain.this.mainFrame);
                    int MAX_TRIES = 5, counter = 0;
                    while (true) {
                        try {
                            connectionManagerSocket.setup();
                            showPopUpPlayerColor(connectionManagerSocket);
                            break;
                        } catch (IOException socketNoAvailable) {
                            if (counter < MAX_TRIES) {
                                System.out.println("Server seems to be offline, trying again to connect");
                                counter++;
                            } else {
                                System.out.println("Cannot connect to socket server!");
                                mainFrame.dispose();
                                break;
                            }
                        }
                    }
                }
            }

        }
    }

    /**
     * @param connectionManagerSocket
     * Method called when player play button, it removes components from mainframe
     * and it initializes color choice GUI
     */
    public void showPopUpPlayerColor(ConnectionManagerSocket connectionManagerSocket) {
        mainFrame.getContentPane().removeAll();
        mainFrame.update(mainFrame.getGraphics());
        SwingUtilities.invokeLater(new showPopUpColor(mainFrame, connectionManagerSocket));
    }

    /**
     * ClientGUIMain constructor initializes all the components in
     * the GUI and it takes all the components images
     */
    public ClientGUIMain() {
        mainFrame = new JFrame("Santorini Game");
        mainFrame.setBackground(Color.lightGray);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        mainFrame.getContentPane().setLayout(gridBagLayout);

        rootPanel = new JPanel();
        rootPanel.setBackground(new Color(0, 0, 0, 60));
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        mainFrame.getContentPane().add(rootPanel, gbcPanel);
        rootPanel.setLayout(new BorderLayout(0, 0));

        westPanelImage = new ImageIcon(this.getClass().getResource("/images/SX_Panel.png"));
        westPanelImageScaled = new ImageIcon(westPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();

        JPanel westPanel = new ClientGUIMain.WestJPanel();
        westPanel.setPreferredSize(new Dimension(280, 10));
        westPanel.setOpaque(false);
        rootPanel.add(westPanel, BorderLayout.WEST);

        eastPanelImage = new ImageIcon(this.getClass().getResource("/images/DX_Panel.png"));
        eastPanelImageScaled = new ImageIcon(eastPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();
        JPanel eastPanel = new ClientGUIMain.EastJPanel();
        eastPanel.setPreferredSize(new Dimension(280, 10));
        eastPanel.setOpaque(false);
        rootPanel.add(eastPanel, BorderLayout.EAST);

        centralPanelImage = new ImageIcon(this.getClass().getResource("/images/Central_Panel_Crop.png"));
        centralPanelImageScaled = new ImageIcon(centralPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();
        JPanel centralPanel = new ClientGUIMain.CentralJPanel();
        centralPanel.setBackground(Color.BLACK);
        centralPanel.setPreferredSize(new Dimension(720, 10));
        centralPanel.setOpaque(true);

        GridBagLayout gblCenterPanel = new GridBagLayout();
        gblCenterPanel.columnWidths = new int[]{474, 0};
        gblCenterPanel.rowHeights = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gblCenterPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblCenterPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        centralPanel.setLayout(gblCenterPanel);

        JLabel logoLabel = new JLabel();
        logoLabel.setBorder(new EmptyBorder(0, 0, 30, 0));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/empty_title.png")));
        logoLabel.setOpaque(false);
        GridBagConstraints gbcLogoLabel = new GridBagConstraints();
        gbcLogoLabel.insets = new Insets(0, 0, 5, 0);
        gbcLogoLabel.anchor = GridBagConstraints.NORTH;
        gbcLogoLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcLogoLabel.gridx = 0;
        gbcLogoLabel.gridy = 0;
        centralPanel.add(logoLabel, gbcLogoLabel);

        JLabel serverData = new JLabel("INSERT SERVER IP:");
        serverData.setForeground(Color.DARK_GRAY);

        GridBagConstraints gbcLblName = new GridBagConstraints();
        gbcLblName.insets = new Insets(0, 200, 5, 200);
        gbcLblName.gridx = 0;
        gbcLblName.gridy = 5;

        centralPanel.add(serverData, gbcLblName);

        serverIpField = new JTextField();
        serverIpField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        serverIpField.setHorizontalAlignment(SwingConstants.CENTER);
        serverIpField.setBackground(new Color(214, 217, 223));
        serverIpField.setText("127.0.0.1");
        gbcLblName.gridy = 8;
        gbcLblName.fill = GridBagConstraints.HORIZONTAL;
        centralPanel.add(serverIpField, gbcLblName);
        serverIpField.setColumns(10);
        serverIpField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                serverIpField.setText("");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (serverIpField.getText().equals(""))
                    serverIpField.setText("127.0.0.1");
            }
        });

        JLabel playerLabel = new JLabel("INSERT YOUR NICKNAME:");
        playerLabel.setForeground(Color.DARK_GRAY);
        gbcLblName.fill = GridBagConstraints.CENTER;
        gbcLblName.gridy = 10;

        centralPanel.add(playerLabel, gbcLblName);

        playerTextField = new JTextField();
        playerTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        playerTextField.setHorizontalAlignment(SwingConstants.CENTER);
        playerTextField.setBackground(new Color(214, 217, 223));
        playerTextField.setText("Nickname");
        gbcLblName.fill = GridBagConstraints.HORIZONTAL;
        gbcLblName.gridy = 12;
        centralPanel.add(playerTextField, gbcLblName);
        playerTextField.setColumns(10);
        playerTextField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playerTextField.setText("");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (playerTextField.getText().equals(""))
                    playerTextField.setText("Nickname");
            }
        });

        GridBagConstraints gbcLblDate = new GridBagConstraints();
        gbcLblDate.insets = new Insets(10, 200, 5, 200);
        gbcLblDate.gridx = 0;
        gbcLblDate.gridy = 11;

        JLabel colorLabel = new JLabel("CHOOSE PREFERRED PLAYER NUMBER:");
        colorLabel.setForeground(Color.darkGray);
        gbcLblDate.gridy = 17;
        centralPanel.add(colorLabel, gbcLblDate);

        Image twoButton_image = new ImageIcon(this.getClass().getResource("/images/2Players.png")).getImage();
        twoButton_Icon = new ImageIcon(twoButton_image);
        Image twoButton_image_Pressed = new ImageIcon(this.getClass().getResource("/images/2Players_pressed.png")).getImage();
        twoButton_Icon_Pressed = new ImageIcon(twoButton_image_Pressed);

        Image threeButton_image = new ImageIcon(this.getClass().getResource("/images/3Players.png")).getImage();
        threeButton_Icon = new ImageIcon(threeButton_image);
        Image threeButton_image_pressed = new ImageIcon(this.getClass().getResource("/images/3Players_pressed.png")).getImage();
        threeButton_Icon_Pressed = new ImageIcon(threeButton_image_pressed);

        JPanel playerNumberButtons = new JPanel(new GridLayout(1, 2, 15, 0));
        playerNumberButtons.setBackground(new Color(0, 0, 0, 0));
        playerNumberButtons.setOpaque(false);

        two_Players_button = new JCheckBox();
        two_Players_button.setBackground(new Color(0, 0, 0, 0));
        two_Players_button.setOpaque(false);
        two_Players_button.setHorizontalAlignment(SwingConstants.CENTER);
        two_Players_button.setIcon(twoButton_Icon);
        two_Players_button.setDisabledIcon(twoButton_Icon_Pressed);

        two_Players_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (two_Players_button.isSelected()) {
                    two_Players_button.setIcon(twoButton_Icon_Pressed);
                    three_Players_button.setIcon(threeButton_Icon);
                } else {
                    two_Players_button.setIcon(twoButton_Icon);
                }
            }
        });

        three_Players_button = new JCheckBox();
        three_Players_button.setBackground(new Color(0, 0, 0, 0));
        three_Players_button.setOpaque(false);
        three_Players_button.setHorizontalAlignment(SwingConstants.CENTER);
        three_Players_button.setIcon(threeButton_Icon);
        three_Players_button.setDisabledIcon(threeButton_Icon_Pressed);

        three_Players_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (three_Players_button.isSelected()) {
                    three_Players_button.setIcon(threeButton_Icon_Pressed);
                    two_Players_button.setIcon(twoButton_Icon);
                } else {
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

        Image play = new ImageIcon(this.getClass().getResource("/images/button_play.png")).getImage().getScaledInstance(126, 141, Image.SCALE_SMOOTH);
        ImageIcon play_button = new ImageIcon(play);
        Image play_pressed = new ImageIcon(this.getClass().getResource("/images/button_play_pressed.png")).getImage().getScaledInstance(126, 141, Image.SCALE_SMOOTH);
        ImageIcon play_button_pressed = new ImageIcon(play_pressed);

        playButton.setIcon(play_button);
        playButton.setPressedIcon(play_button_pressed);
        playButton.setHorizontalAlignment(SwingConstants.CENTER);
        playButton.setOpaque(false);

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
        mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width / 2, dim.height / 2 - mainFrame.getSize().height / 2);
    }

    /**
     * It adds listener to play button
     */
    @Override
    public void run() {
        playButton.addActionListener(new PlayActionListener());
    }

    /**
     * @param args
     * Main that launches ClientGUIMain
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ClientGUIMain());
    }
}
