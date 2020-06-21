package it.polimi.ingsw.client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PickUpCards implements Runnable {

    private Image eastPanelImageScaled;
    private ImageIcon eastPanelImage;

    private Image westPanelImageScaled;
    private ImageIcon westPanelImage;

    private Image centralPanelImageScaled;
    private ImageIcon centralPanelImage;

    private ImageIcon PanText;
    private ImageIcon PanImage;
    private JCheckBox PanButton;
    private ImageIcon ApolloText;
    private ImageIcon ApolloImage;
    private JCheckBox ApolloButton;
    private ImageIcon ArtemisText;
    private ImageIcon ArtemisImage;
    private JCheckBox ArtemisButton;
    private ImageIcon AthenaText;
    private ImageIcon AthenaImage;
    private JCheckBox AthenaButton;
    private ImageIcon AtlasText;
    private ImageIcon AtlasImage;
    private JCheckBox AtlasButton;
    private ImageIcon DemeterText;
    private ImageIcon DemeterImage;
    private JCheckBox DemeterButton;
    private ImageIcon HephaestusText;
    private ImageIcon HephaestusImage;
    private JCheckBox HephaestusButton;
    private ImageIcon MinotaurText;
    private ImageIcon MinotaurImage;
    private JCheckBox MinotaurButton;
    private ImageIcon PrometheusText;
    private ImageIcon PrometheusImage;
    private JCheckBox PrometheusButton;
    private ImageIcon SeleneText;
    private ImageIcon SeleneImage;
    private JCheckBox SeleneButton;
    private ImageIcon ZeusText;
    private ImageIcon ZeusImage;
    private JCheckBox ZeusButton;
    private ImageIcon PoseidonText;
    private ImageIcon PoseidonImage;
    private JCheckBox PoseidonButton;
    private ImageIcon HestiaText;
    private ImageIcon HestiaImage;
    private JCheckBox HestiaButton;
    private ImageIcon ChronusText;
    private ImageIcon ChronusImage;
    private JCheckBox ChronusButton;

    private ImageIcon MinotaurCard_Icon_Pressed;
    private ImageIcon MinotaurCard_Icon;
    private ImageIcon ApolloCard_Icon_Pressed;
    private ImageIcon ApolloCard_Icon;
    private ImageIcon DemeterCard_Icon_Pressed;
    private ImageIcon DemeterCard_Icon;
    private ImageIcon PanCard_Icon_Pressed;
    private ImageIcon PanCard_Icon;
    private ImageIcon HephaestusCard_Icon_Pressed;
    private ImageIcon HephaestusCard_Icon;
    private ImageIcon PrometheusCard_Icon_Pressed;
    private ImageIcon PrometheusCard_Icon;
    private ImageIcon AtlasCard_Icon_Pressed;
    private ImageIcon AtlasCard_Icon;
    private ImageIcon AthenaCard_Icon_Pressed;
    private ImageIcon AthenaCard_Icon;
    private ImageIcon ArtemisCard_Icon_Pressed;
    private ImageIcon ArtemisCard_Icon;
    private ImageIcon SeleneCard_Icon_Pressed;
    private ImageIcon SeleneCard_Icon;
    private ImageIcon ZeusCard_Icon_Pressed;
    private ImageIcon ZeusCard_Icon;
    private ImageIcon ChronusCard_Icon_Pressed;
    private ImageIcon ChronusCard_Icon;
    private ImageIcon HestiaCard_Icon_Pressed;
    private ImageIcon HestiaCard_Icon;
    private ImageIcon PoseidonCard_Icon_Pressed;
    private ImageIcon PoseidonCard_Icon;


    private JLabel ImageContainer;
    private JLabel logoLabel;
    private int cardCounter;
    private ArrayList<JCheckBox> buttonList;
    private JRadioButton playButton;

    private ArrayList<String> deck;
    private Map<JCheckBox, ImageIcon> ActiveCardList;
    private ConnectionManagerSocket connectionManagerSocket;

    private int playerNumber;
    private boolean firstPlayer;
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

    private class PickGodActionListener implements ActionListener {
        ImageIcon pressedButtonImage, ButtonImage, Text, GodImage;
        JCheckBox button;
        String godName;
        int x,y;

        public PickGodActionListener(JCheckBox button, ImageIcon pressedButtonImage, ImageIcon buttonImage, ImageIcon text, ImageIcon godImage, String godName) {
            this.pressedButtonImage = pressedButtonImage;
            this.ButtonImage = buttonImage;
            this.Text = text;
            this.GodImage = godImage;
            this.button = button;
            this.godName = godName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(PickGodActionListener.this.button.isSelected()){
                PickUpCards.this.cardCounter ++;
                deck.add(this.godName);
                if(PickUpCards.this.cardCounter == PickUpCards.this.playerNumber ){
                    PickUpCards.this.buttonList.forEach(currentButton -> {
                       if(!currentButton.isSelected()) {
                           currentButton.setEnabled(false);
                       }
                    });
                    PickUpCards.this.playButton.setVisible(true);
                }
                PickGodActionListener.this.button.setIcon(PickGodActionListener.this.pressedButtonImage);
                PickUpCards.this.logoLabel.setIcon(Text);
                PickUpCards.this.ImageContainer.setIcon(PickGodActionListener.this.GodImage);
                PickUpCards.this.ImageContainer.setVisible(true);
            }else{
                PickUpCards.this.playButton.setVisible(false);
                PickUpCards.this.cardCounter --;
                deck.remove(godName);
                PickUpCards.this.buttonList.forEach(currentButton -> {
                        currentButton.setEnabled(true);
                });
                PickGodActionListener.this.button.setIcon(PickGodActionListener.this.ButtonImage);
                PickUpCards.this.logoLabel.setIcon(Text);
                PickUpCards.this.ImageContainer.setVisible(false);
            }
        }
    }
    private class PlayActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            connectionManagerSocket.sendObjectToServer(deck);
            try{
                connectionManagerSocket.getCardThread().join();
            }catch (InterruptedException thread){
                System.err.println(thread.getMessage());
            }
            connectionManagerSocket.openBoardGui();
        }
    }


    private class ChooseYourCardListener implements ActionListener{
        String CardName;
        ImageIcon pressedButtonImage, Text, GodImage, ButtonImage;
        JCheckBox button;
        public ChooseYourCardListener(JCheckBox button, ImageIcon pressedButtonImage, ImageIcon text, ImageIcon godImage, String cardName, ImageIcon buttonImage) {
            CardName = cardName;
            this.pressedButtonImage = pressedButtonImage;
            Text = text;
            GodImage = godImage;
            this.button = button;
            this.ButtonImage = buttonImage;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(this.button.isSelected()){
                PickUpCards.this.ActiveCardList.forEach((CurrentButton, Image) -> {
                    CurrentButton.setIcon(Image);
                    if(CurrentButton!= this.button)
                        CurrentButton.setSelected(false);
                });
                PickUpCards.this.logoLabel.setVisible(false);
                PickUpCards.this.ImageContainer.setVisible(false);
                button.setIcon(this.pressedButtonImage);
                PickUpCards.this.logoLabel.setIcon(this.Text);
                PickUpCards.this.logoLabel.setVisible(true);
                PickUpCards.this.ImageContainer.setIcon(this.GodImage);
                PickUpCards.this.ImageContainer.setVisible(true);
                playButton.setVisible(true);
            }else{
                playButton.setVisible(false);
                this.button.setIcon(this.ButtonImage);
                PickUpCards.this.logoLabel.setVisible(false);
                PickUpCards.this.ImageContainer.setVisible(false);
            }
        }
    }

    private class ChooseCardActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ActiveCardList.forEach((ButtonName, Image) -> {
                if(ButtonName.isSelected()) {
                    if (ButtonName.equals(ApolloButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Apollo");
                    if (ButtonName.equals(DemeterButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Demeter");
                    if (ButtonName.equals(AtlasButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Atlas");
                    if (ButtonName.equals(ArtemisButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Artemis");
                    if (ButtonName.equals(PrometheusButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Prometheus");
                    if (ButtonName.equals(MinotaurButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Minotaur");
                    if (ButtonName.equals(PanButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Pan");
                    if (ButtonName.equals(AthenaButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Athena");
                    if (ButtonName.equals(HephaestusButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Hephaestus");
                    if (ButtonName.equals(ZeusButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Zeus");
                    if (ButtonName.equals(ChronusButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Chronus");
                    if (ButtonName.equals(SeleneButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Selene");
                    if (ButtonName.equals(HestiaButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Hestia");
                    if (ButtonName.equals(PoseidonButton))
                        connectionManagerSocket.sendObjectToServer(connectionManagerSocket.getclientID() + " Poseidon");

                }
            });
            //connectionManagerSocket.close();
            try{
                connectionManagerSocket.getCardThread().join();
            }catch (InterruptedException thread){
                System.err.println(thread.getMessage());
            }
            connectionManagerSocket.openBoardGui();
        }
    }

    public PickUpCards(JFrame mainFrame, int playerNumber, ConnectionManagerSocket connectionManagerSocket, boolean firstPlayer) {
        this.deck = new ArrayList<>();
        this.connectionManagerSocket = connectionManagerSocket;
        this.playerNumber = playerNumber;
        this.firstPlayer = firstPlayer;
        this.ActiveCardList = new HashMap<JCheckBox, ImageIcon>();
        buttonList = new ArrayList<JCheckBox>();
        JPanel rootPanel = new JPanel(new BorderLayout());

        westPanelImage = new ImageIcon(PATH + "Sx_Panel_Cards_new.png");
        westPanelImageScaled = new ImageIcon(westPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();
        JPanel westPanel = new PickUpCards.WestJPanel();

        GridBagLayout gblSxPanel = new GridBagLayout();
        gblSxPanel.columnWidths = new int[] { 474, 0 };
        gblSxPanel.rowHeights = new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gblSxPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gblSxPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        westPanel.setLayout(gblSxPanel);
        westPanel.setPreferredSize(new Dimension(459, 720));
        westPanel.setOpaque(true);
        rootPanel.add(westPanel, BorderLayout.WEST);


        PanText = new ImageIcon(PATH + "Pan_Text.png");
        ApolloText = new ImageIcon(PATH + "Apollo_Text.png");
        DemeterText = new ImageIcon(PATH + "Demeter_Text.png");
        HephaestusText = new ImageIcon(PATH + "Hephaestus_Text.png");
        PrometheusText = new ImageIcon(PATH + "Prometheus_Text.png");
        ArtemisText = new ImageIcon(PATH + "Artemis_Text.png");
        AthenaText = new ImageIcon(PATH + "Athena_Text.png");
        AtlasText = new ImageIcon(PATH + "Atlas_Text.png");
        MinotaurText = new ImageIcon(PATH + "Minotaur_Text.png");

        ZeusText = new ImageIcon(PATH + "Minotaur_Text.png");
        HestiaText = new ImageIcon(PATH + "Minotaur_Text.png");
        PoseidonText = new ImageIcon(PATH + "Minotaur_Text.png");
        SeleneText = new ImageIcon(PATH + "Minotaur_Text.png");
        ChronusText = new ImageIcon(PATH + "Minotaur_Text.png");


        logoLabel = new JLabel();
        logoLabel.setBorder(new EmptyBorder(12, 0, 30, 0));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbcDescriptionLabel = new GridBagConstraints();
        gbcDescriptionLabel.insets = new Insets(0, 0, 5, 0);
        gbcDescriptionLabel.anchor = GridBagConstraints.NORTH;
        gbcDescriptionLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcDescriptionLabel.gridx = 0;
        gbcDescriptionLabel.gridy = 0;

        westPanel.add(logoLabel, gbcDescriptionLabel);

        centralPanelImage = new ImageIcon(PATH + "Central_Panel_Cards_Image.png");
        centralPanelImageScaled = new ImageIcon(centralPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();
        JPanel centralPanel = new PickUpCards.CentralJPanel();
        //centralPanel.setBackground(Color.BLACK);
        centralPanel.setPreferredSize(new Dimension(362, 720));
        centralPanel.setOpaque(true);
        centralPanel.setLayout(gblSxPanel);
        rootPanel.add(centralPanel, BorderLayout.CENTER);

        GridBagConstraints gbcCardsImageLabel = new GridBagConstraints();
        gbcCardsImageLabel.insets = new Insets(130, 0, 0, 0);
        gbcCardsImageLabel.anchor = GridBagConstraints.CENTER;
        gbcCardsImageLabel.fill = GridBagConstraints.VERTICAL;
        gbcCardsImageLabel.gridx = 0;
        gbcCardsImageLabel.gridy = 0;

        ArtemisImage = new ImageIcon(PATH + "Artemis_Image.png");
        PanImage = new ImageIcon(PATH + "Pan_Image.png");
        AtlasImage = new ImageIcon(PATH + "Atlas_Image.png");
        DemeterImage = new ImageIcon(PATH + "Demeter_Image.png");
        HephaestusImage = new ImageIcon(PATH + "Hephaestus_Image.png");
        MinotaurImage = new ImageIcon(PATH + "Minotaur_Image.png");
        AthenaImage = new ImageIcon(PATH + "Athena_Image.png");
        PrometheusImage = new ImageIcon(PATH + "Prometheus_Image.png");
        ApolloImage = new ImageIcon(PATH + "Apollo_Image.png");

        ZeusImage = new ImageIcon(PATH + "Apollo_Image.png");
        ChronusImage = new ImageIcon(PATH + "Apollo_Image.png");
        PoseidonImage = new ImageIcon(PATH + "Apollo_Image.png");
        HestiaImage = new ImageIcon(PATH + "Apollo_Image.png");
        SeleneImage = new ImageIcon(PATH + "Apollo_Image.png");

        ImageContainer = new JLabel();
        ImageContainer.setBackground(new Color(0,0,0,0));
        centralPanel.add(ImageContainer, gbcCardsImageLabel);

        eastPanelImage = new ImageIcon(PATH + "Dx_Panel_Cards_new.png");
        eastPanelImageScaled = new ImageIcon(eastPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();;
        JPanel eastPanel = new PickUpCards.EastJPanel();
        eastPanel.setPreferredSize(new Dimension(459, 720));
        eastPanel.setOpaque(false);
        eastPanel.setLayout(gblSxPanel);
        rootPanel.add(eastPanel, BorderLayout.EAST);

        GridBagConstraints gbcCardsLabel = new GridBagConstraints();
        gbcCardsLabel.insets = new Insets(150, 0, 5, 60);
        gbcCardsLabel.anchor = GridBagConstraints.CENTER;
        gbcCardsLabel.fill = GridBagConstraints.VERTICAL;
        gbcCardsLabel.gridx = 0;
        gbcCardsLabel.gridy = 0;


        JPanel card = new JPanel(new GridLayout(5,3,5,5));
        card.setBackground(new Color(0,0,0,0));
        card.setOpaque(false);

        JScrollPane cardScroll = new ModernScrollPane(card,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        cardScroll.setMinimumSize(new Dimension(360,360));
        cardScroll.setOpaque(false);
        cardScroll.getViewport().setOpaque(false);
        Image PanCard = new ImageIcon(PATH + "CardPan.png").getImage();
        PanCard_Icon = new ImageIcon(PanCard);
        Image PanCard_pressed = new ImageIcon(PATH + "PanCardPressed.png").getImage();
        PanCard_Icon_Pressed = new ImageIcon(PanCard_pressed);
        PanButton = new JCheckBox();
        PanButton.setBackground(new Color(0,0,0,0));
        PanButton.setOpaque(false);
        PanButton.setHorizontalAlignment(SwingConstants.CENTER);
        PanButton.setIcon(PanCard_Icon);
        card.add(PanButton);

        Image ApolloCard = new ImageIcon(PATH + "CardApollo.png").getImage();
        ApolloCard_Icon = new ImageIcon(ApolloCard);
        Image ApolloCard_pressed = new ImageIcon(PATH + "ApolloCardPressed.png").getImage();
        ApolloCard_Icon_Pressed = new ImageIcon(ApolloCard_pressed);
        ApolloButton = new JCheckBox();
        ApolloButton.setBackground(new Color(0,0,0,0));
        ApolloButton.setOpaque(false);
        ApolloButton.setHorizontalAlignment(SwingConstants.CENTER);
        ApolloButton.setIcon(ApolloCard_Icon);
        card.add(ApolloButton);

        Image AtlasCard = new ImageIcon(PATH + "CardAtlas.png").getImage();
        AtlasCard_Icon = new ImageIcon(AtlasCard);
        Image AtlasCard_pressed = new ImageIcon(PATH + "AtlasCardPressed.png").getImage();
        AtlasCard_Icon_Pressed = new ImageIcon(AtlasCard_pressed);
        AtlasButton = new JCheckBox();
        AtlasButton.setBackground(new Color(0,0,0,0));
        AtlasButton.setOpaque(false);
        AtlasButton.setHorizontalAlignment(SwingConstants.CENTER);
        AtlasButton.setIcon(AtlasCard_Icon);
        card.add(AtlasButton);

        Image ArtemisCard = new ImageIcon(PATH + "CardArtemis.png").getImage();
        ArtemisCard_Icon = new ImageIcon(ArtemisCard);
        Image ArtemisCard_pressed = new ImageIcon(PATH + "ArtemisCardPressed.png").getImage();
        ArtemisCard_Icon_Pressed = new ImageIcon(ArtemisCard_pressed);
        ArtemisButton = new JCheckBox();
        ArtemisButton.setBackground(new Color(0,0,0,0));
        ArtemisButton.setOpaque(false);
        ArtemisButton.setHorizontalAlignment(SwingConstants.CENTER);
        ArtemisButton.setIcon(ArtemisCard_Icon);
        card.add(ArtemisButton);

        Image AthenaCard = new ImageIcon(PATH + "CardAthena.png").getImage();
        AthenaCard_Icon = new ImageIcon(AthenaCard);
        Image AthenaCard_pressed = new ImageIcon(PATH + "AthenaCardPressed.png").getImage();
        AthenaCard_Icon_Pressed = new ImageIcon(AthenaCard_pressed);
        AthenaButton = new JCheckBox();
        AthenaButton.setBackground(new Color(0,0,0,0));
        AthenaButton.setOpaque(false);
        AthenaButton.setHorizontalAlignment(SwingConstants.CENTER);
        AthenaButton.setIcon(AthenaCard_Icon);
        card.add(AthenaButton);

        Image DemeterCard = new ImageIcon(PATH + "CardDemeter.png").getImage();
        DemeterCard_Icon = new ImageIcon(DemeterCard);
        Image DemeterCard_pressed = new ImageIcon(PATH + "DemeterCardPressed.png").getImage();
        DemeterCard_Icon_Pressed = new ImageIcon(DemeterCard_pressed);
        DemeterButton = new JCheckBox();
        DemeterButton.setBackground(new Color(0,0,0,0));
        DemeterButton.setOpaque(false);
        DemeterButton.setHorizontalAlignment(SwingConstants.CENTER);
        DemeterButton.setIcon(DemeterCard_Icon);
        card.add(DemeterButton);

        Image HephaestusCard = new ImageIcon(PATH + "CardHephaestus.png").getImage();
        HephaestusCard_Icon = new ImageIcon(HephaestusCard);
        Image HephaestusCard_pressed = new ImageIcon(PATH + "HephaestusCardPressed.png").getImage();
        HephaestusCard_Icon_Pressed = new ImageIcon(HephaestusCard_pressed);
        HephaestusButton = new JCheckBox();
        HephaestusButton.setBackground(new Color(0,0,0,0));
        HephaestusButton.setOpaque(false);
        HephaestusButton.setHorizontalAlignment(SwingConstants.CENTER);
        HephaestusButton.setIcon(HephaestusCard_Icon);
        card.add(HephaestusButton);

        Image PrometheusCard = new ImageIcon(PATH + "CardPrometheus.png").getImage();
        PrometheusCard_Icon = new ImageIcon(PrometheusCard);
        Image PrometheusCard_pressed = new ImageIcon(PATH + "PrometheusCardPressed.png").getImage();
        PrometheusCard_Icon_Pressed = new ImageIcon(PrometheusCard_pressed);
        PrometheusButton = new JCheckBox();
        PrometheusButton.setBackground(new Color(0,0,0,0));
        PrometheusButton.setOpaque(false);
        PrometheusButton.setHorizontalAlignment(SwingConstants.CENTER);
        PrometheusButton.setIcon(PrometheusCard_Icon);
        card.add(PrometheusButton);

        Image MinotaurCard = new ImageIcon(PATH + "CardMinotaur.png").getImage();
        MinotaurCard_Icon = new ImageIcon(MinotaurCard);
        Image MinotaurCard_pressed = new ImageIcon(PATH + "MinotaurCardPressed.png").getImage();
        MinotaurCard_Icon_Pressed = new ImageIcon(MinotaurCard_pressed);
        MinotaurButton = new JCheckBox();
        MinotaurButton.setBackground(new Color(0,0,0,0));
        MinotaurButton.setOpaque(false);
        MinotaurButton.setHorizontalAlignment(SwingConstants.CENTER);
        MinotaurButton.setIcon(MinotaurCard_Icon);
        card.add(MinotaurButton);

        Image SeleneCard = new ImageIcon(PATH + "CardMinotaur.png").getImage();
        SeleneCard_Icon = new ImageIcon(SeleneCard);
        Image SeleneCard_pressed = new ImageIcon(PATH + "MinotaurCardPressed.png").getImage();
        SeleneCard_Icon_Pressed = new ImageIcon(SeleneCard_pressed);
        SeleneButton = new JCheckBox();
        SeleneButton.setBackground(new Color(0,0,0,0));
        SeleneButton.setOpaque(false);
        SeleneButton.setHorizontalAlignment(SwingConstants.CENTER);
        SeleneButton.setIcon(SeleneCard_Icon);
        card.add(SeleneButton);

        Image PoseidonCard = new ImageIcon(PATH + "CardMinotaur.png").getImage();
        PoseidonCard_Icon = new ImageIcon(PoseidonCard);
        Image PoseidonCard_pressed = new ImageIcon(PATH + "MinotaurCardPressed.png").getImage();
        PoseidonCard_Icon_Pressed = new ImageIcon(PoseidonCard_pressed);
        PoseidonButton = new JCheckBox();
        PoseidonButton.setBackground(new Color(0,0,0,0));
        PoseidonButton.setOpaque(false);
        PoseidonButton.setHorizontalAlignment(SwingConstants.CENTER);
        PoseidonButton.setIcon(PoseidonCard_Icon);
        card.add(PoseidonButton);

        Image ZeusCard = new ImageIcon(PATH + "CardMinotaur.png").getImage();
        ZeusCard_Icon = new ImageIcon(ZeusCard);
        Image ZeusCard_pressed = new ImageIcon(PATH + "MinotaurCardPressed.png").getImage();
        ZeusCard_Icon_Pressed = new ImageIcon(ZeusCard_pressed);
        ZeusButton = new JCheckBox();
        ZeusButton.setBackground(new Color(0,0,0,0));
        ZeusButton.setOpaque(false);
        ZeusButton.setHorizontalAlignment(SwingConstants.CENTER);
        ZeusButton.setIcon(ZeusCard_Icon);
        card.add(ZeusButton);

        Image ChronusCard = new ImageIcon(PATH + "CardMinotaur.png").getImage();
        ChronusCard_Icon = new ImageIcon(ChronusCard);
        Image ChronusCard_pressed = new ImageIcon(PATH + "MinotaurCardPressed.png").getImage();
        ChronusCard_Icon_Pressed = new ImageIcon(ChronusCard_pressed);
        ChronusButton = new JCheckBox();
        ChronusButton.setBackground(new Color(0,0,0,0));
        ChronusButton.setOpaque(false);
        ChronusButton.setHorizontalAlignment(SwingConstants.CENTER);
        ChronusButton.setIcon(ChronusCard_Icon);
        card.add(ChronusButton);

        Image HestiaCard = new ImageIcon(PATH + "CardMinotaur.png").getImage();
        HestiaCard_Icon = new ImageIcon(HestiaCard);
        Image HestiaCard_pressed = new ImageIcon(PATH + "MinotaurCardPressed.png").getImage();
        HestiaCard_Icon_Pressed = new ImageIcon(HestiaCard_pressed);
        HestiaButton = new JCheckBox();
        HestiaButton.setBackground(new Color(0,0,0,0));
        HestiaButton.setOpaque(false);
        HestiaButton.setHorizontalAlignment(SwingConstants.CENTER);
        HestiaButton.setIcon(HestiaCard_Icon);
        card.add(HestiaButton);

        buttonList.add(MinotaurButton);
        buttonList.add(ApolloButton);
        buttonList.add(ArtemisButton);
        buttonList.add(PanButton);
        buttonList.add(DemeterButton);
        buttonList.add(HephaestusButton);
        buttonList.add(PrometheusButton);
        buttonList.add(AtlasButton);
        buttonList.add(AthenaButton);
        buttonList.add(SeleneButton);
        buttonList.add(PoseidonButton);
        buttonList.add(ZeusButton);
        buttonList.add(ChronusButton);
        buttonList.add(HestiaButton);

        if(!firstPlayer){
            buttonList.forEach((GodButton) -> GodButton.setEnabled(false));
        }

        //cardScroll.setBorder(BorderFactory.createEmptyBorder());

        eastPanel.add(cardScroll, gbcCardsLabel);


        Image play = new ImageIcon(PATH + "start-game-button.png").getImage().getScaledInstance(169,91, Image.SCALE_SMOOTH);
        ImageIcon play_button = new ImageIcon(play);
        playButton = new JRadioButton();
        playButton.setIcon(play_button);
        playButton.setHorizontalAlignment(SwingConstants.CENTER);
        playButton.setOpaque(false);
        playButton.setVisible(false);

        GridBagConstraints gbcPlayButton = new GridBagConstraints();
        gbcPlayButton.insets = new Insets(0, 0, 35, 0);
        gbcPlayButton.anchor = GridBagConstraints.SOUTH;
        gbcPlayButton.fill = GridBagConstraints.HORIZONTAL;
        gbcPlayButton.gridx = 0;
        gbcPlayButton.gridy = 25;
        centralPanel.add(playButton, gbcPlayButton);

        mainFrame.getContentPane().add(rootPanel);
        mainFrame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
        mainFrame.setResizable(false);
        mainFrame.pack();
    }

    @Override
    public void run() {
        if(this.firstPlayer){
            MinotaurButton.addActionListener(new PickGodActionListener(MinotaurButton, MinotaurCard_Icon_Pressed, MinotaurCard_Icon, MinotaurText, MinotaurImage, "Minotaur"));
            AtlasButton.addActionListener(new PickGodActionListener(AtlasButton, AtlasCard_Icon_Pressed, AtlasCard_Icon, AtlasText, AtlasImage, "Atlas"));
            AthenaButton.addActionListener(new PickGodActionListener(AthenaButton, AthenaCard_Icon_Pressed, AthenaCard_Icon, AthenaText, AthenaImage, "Athena"));
            ArtemisButton.addActionListener(new PickGodActionListener(ArtemisButton, ArtemisCard_Icon_Pressed, ArtemisCard_Icon, ArtemisText, ArtemisImage, "Artemis"));
            PanButton.addActionListener(new PickGodActionListener(PanButton, PanCard_Icon_Pressed, PanCard_Icon, PanText, PanImage, "Pan"));
            HephaestusButton.addActionListener(new PickGodActionListener(HephaestusButton, HephaestusCard_Icon_Pressed, HephaestusCard_Icon, HephaestusText, HephaestusImage, "Hephaestus"));
            PrometheusButton.addActionListener(new PickGodActionListener(PrometheusButton, PrometheusCard_Icon_Pressed, PrometheusCard_Icon, PrometheusText, PrometheusImage, "Prometheus"));
            DemeterButton.addActionListener(new PickGodActionListener(DemeterButton, DemeterCard_Icon_Pressed, DemeterCard_Icon, DemeterText, DemeterImage, "Demeter"));
            ApolloButton.addActionListener(new PickGodActionListener(ApolloButton, ApolloCard_Icon_Pressed, ApolloCard_Icon, ApolloText, ApolloImage, "Apollo"));
            SeleneButton.addActionListener(new PickGodActionListener(SeleneButton, SeleneCard_Icon_Pressed, SeleneCard_Icon, SeleneText, SeleneImage, "Selene"));
            PoseidonButton.addActionListener(new PickGodActionListener(PoseidonButton, PoseidonCard_Icon_Pressed, PoseidonCard_Icon, PoseidonText, PoseidonImage, "Poseidon"));
            ZeusButton.addActionListener(new PickGodActionListener(ZeusButton, ZeusCard_Icon_Pressed, ZeusCard_Icon, ZeusText, ZeusImage, "Zeus"));
            ChronusButton.addActionListener(new PickGodActionListener(ChronusButton, ChronusCard_Icon_Pressed, ChronusCard_Icon, ChronusText, ChronusImage, "Chronus"));
            HestiaButton.addActionListener(new PickGodActionListener(HestiaButton, HestiaCard_Icon_Pressed, HestiaCard_Icon, HestiaText, HestiaImage, "Hestia"));

            playButton.addActionListener(new PlayActionListener());

        }else{
            playButton.addActionListener(new ChooseCardActionListener());
        }
        connectionManagerSocket.receiveCard(this,null);
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("prova");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        SwingUtilities.invokeLater(new PickUpCards(mainFrame, 2, null, false));
    }

    public void updateGodImage(ArrayList<String> cards){

        cards.forEach((CardName) -> {
            switch (CardName){
                case "Apollo":  ApolloButton.setEnabled(true);
                                ApolloButton.addActionListener(new ChooseYourCardListener(ApolloButton, ApolloCard_Icon_Pressed, ApolloText, ApolloImage,"Apollo", ApolloCard_Icon));
                                ActiveCardList.put(ApolloButton, ApolloCard_Icon);
                                break;
                case "Demeter": DemeterButton.setEnabled(true);
                                DemeterButton.addActionListener(new ChooseYourCardListener(DemeterButton, DemeterCard_Icon_Pressed, DemeterText, DemeterImage, "Demeter", DemeterCard_Icon));
                                ActiveCardList.put(DemeterButton, DemeterCard_Icon);
                                break;
                case "Artemis": ArtemisButton.setEnabled(true);
                                ArtemisButton.addActionListener(new ChooseYourCardListener(ArtemisButton, ArtemisCard_Icon_Pressed, ArtemisText, ArtemisImage, "Artemis", ArtemisCard_Icon));
                                ActiveCardList.put(ArtemisButton, ArtemisCard_Icon);
                                break;
                case "Athena":  AthenaButton.setEnabled(true);
                                AthenaButton.addActionListener(new ChooseYourCardListener(AthenaButton, AthenaCard_Icon_Pressed, AthenaText, AthenaImage, "Athena", AthenaCard_Icon));
                                ActiveCardList.put(AthenaButton, AthenaCard_Icon);
                                break;
                case "Atlas":   AtlasButton.setEnabled(true);
                                AtlasButton.addActionListener(new ChooseYourCardListener(AtlasButton, AtlasCard_Icon_Pressed, AtlasText, AtlasImage, "Atlas", AtlasCard_Icon));
                                ActiveCardList.put(AtlasButton, AtlasCard_Icon);
                                break;
                case "Hephaestus":  HephaestusButton.setEnabled(true);
                                    HephaestusButton.addActionListener(new ChooseYourCardListener(HephaestusButton, HephaestusCard_Icon_Pressed, HephaestusText, HephaestusImage, "Hephaestus", HephaestusCard_Icon));
                                    ActiveCardList.put(HephaestusButton, HephaestusCard_Icon);
                                    break;
                case "Minotaur":    MinotaurButton.setEnabled(true);
                                    MinotaurButton.addActionListener(new ChooseYourCardListener(MinotaurButton, MinotaurCard_Icon_Pressed, MinotaurText, MinotaurImage, "Minotaur", MinotaurCard_Icon));
                                    ActiveCardList.put(MinotaurButton, MinotaurCard_Icon);
                                    break;
                case "Pan": PanButton.setEnabled(true);
                            PanButton.addActionListener(new ChooseYourCardListener(PanButton, PanCard_Icon_Pressed, PanText, PanImage, "Pan", PanCard_Icon));
                            ActiveCardList.put(PanButton, PanCard_Icon);
                            break;
                case "Prometheus":  PrometheusButton.setEnabled(true);
                                    PrometheusButton.addActionListener(new ChooseYourCardListener(PrometheusButton, PrometheusCard_Icon_Pressed, PrometheusText, PrometheusImage, "Prometheus", PrometheusCard_Icon));
                                    ActiveCardList.put(PrometheusButton, PrometheusCard_Icon);
                                    break;
                case "Zeus":    ZeusButton.setEnabled(true);
                                ZeusButton.addActionListener(new ChooseYourCardListener(ZeusButton, ZeusCard_Icon_Pressed, ZeusText, ZeusImage, "Zeus", ZeusCard_Icon));
                                ActiveCardList.put(ZeusButton, ZeusCard_Icon);
                                break;
                case "Hestia":  HestiaButton.setEnabled(true);
                                HestiaButton.addActionListener(new ChooseYourCardListener(HestiaButton, HestiaCard_Icon_Pressed, HestiaText, HestiaImage, "Hestia", HestiaCard_Icon));
                                ActiveCardList.put(HestiaButton, HestiaCard_Icon);
                                break;
                case "Chronus": ChronusButton.setEnabled(true);
                                ChronusButton.addActionListener(new ChooseYourCardListener(ChronusButton, ChronusCard_Icon_Pressed, ChronusText, ChronusImage, "Chronus", ChronusCard_Icon));
                                ActiveCardList.put(ChronusButton, ChronusCard_Icon);
                                break;
                case "Poseidon":    PoseidonButton.setEnabled(true);
                                    PoseidonButton.addActionListener(new ChooseYourCardListener(PoseidonButton, PoseidonCard_Icon_Pressed, PoseidonText, PoseidonImage, "Poseidon", PoseidonCard_Icon));
                                    ActiveCardList.put(PoseidonButton, PoseidonCard_Icon);
                                    break;
                case "Selene":  SeleneButton.setEnabled(true);
                                SeleneButton.addActionListener(new ChooseYourCardListener(SeleneButton, SeleneCard_Icon_Pressed, SeleneText, SeleneImage, "Selene", SeleneCard_Icon));
                                ActiveCardList.put(SeleneButton, SeleneCard_Icon);
                                break;
            }
        });
    }

    public class ModernScrollPane extends JScrollPane {

        private static final long serialVersionUID = 8607734981506765935L;

        private static final int SCROLL_BAR_ALPHA_ROLLOVER = 100;
        private static final int SCROLL_BAR_ALPHA = 50;
        private static final int THUMB_SIZE = 8;
        private static final int SB_SIZE = 10;
        private final Color THUMB_COLOR = Color.BLACK;

        public ModernScrollPane(Component view) {
            this(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }

        public ModernScrollPane(int vsbPolicy, int hsbPolicy) {
            this(null, vsbPolicy, hsbPolicy);
        }

        public ModernScrollPane(Component view, int vsbPolicy, int hsbPolicy) {

            setBorder(null);
            // Set ScrollBar UI
            JScrollBar verticalScrollBar = getVerticalScrollBar();
            verticalScrollBar.setOpaque(false);
            verticalScrollBar.setUI(new ModernScrollBarUI(this));

            JScrollBar horizontalScrollBar = getHorizontalScrollBar();
            horizontalScrollBar.setOpaque(false);
            horizontalScrollBar.setUI(new ModernScrollBarUI(this));

            setLayout(new ScrollPaneLayout() {
                private static final long serialVersionUID = 5740408979909014146L;

                @Override
                public void layoutContainer(Container parent) {
                    Rectangle availR = ((JScrollPane) parent).getBounds();
                    availR.x = availR.y = 0;

                    // viewport
                    Insets insets = parent.getInsets();
                    availR.x = insets.left;
                    availR.y = insets.top;
                    availR.width -= insets.left + insets.right;
                    availR.height -= insets.top + insets.bottom;
                    if (viewport != null) {
                        viewport.setBounds(availR);
                    }

                    boolean vsbNeeded = isVerticalScrollBarfNecessary();
                    boolean hsbNeeded = isHorizontalScrollBarNecessary();

                    // vertical scroll bar
                    Rectangle vsbR = new Rectangle();
                    vsbR.width = SB_SIZE;
                    vsbR.height = availR.height - (hsbNeeded ? vsbR.width : 0);
                    vsbR.x = availR.x + availR.width - vsbR.width;
                    vsbR.y = availR.y;
                    if (vsb != null) {
                        vsb.setBounds(vsbR);
                    }

                    // horizontal scroll bar
                    Rectangle hsbR = new Rectangle();
                    hsbR.height = SB_SIZE;
                    hsbR.width = availR.width - (vsbNeeded ? hsbR.height : 0);
                    hsbR.x = availR.x;
                    hsbR.y = availR.y + availR.height - hsbR.height;
                    if (hsb != null) {
                        hsb.setBounds(hsbR);
                    }
                }
            });

            // Layering
            setComponentZOrder(getVerticalScrollBar(), 0);
            setComponentZOrder(getHorizontalScrollBar(), 1);
            setComponentZOrder(getViewport(), 2);

            viewport.setView(view);
        }
        private boolean isVerticalScrollBarfNecessary() {
            Rectangle viewRect = viewport.getViewRect();
            Dimension viewSize = viewport.getViewSize();
            return viewSize.getHeight() > viewRect.getHeight();
        }

        private boolean isHorizontalScrollBarNecessary() {
            Rectangle viewRect = viewport.getViewRect();
            Dimension viewSize = viewport.getViewSize();
            return viewSize.getWidth() > viewRect.getWidth();
        }

        /**
         * Class extending the BasicScrollBarUI and overrides all necessary methods
         */
        private class ModernScrollBarUI extends BasicScrollBarUI {

            private JScrollPane sp;

            public ModernScrollBarUI(ModernScrollPane sp) {
                this.sp = sp;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new InvisibleScrollBarButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new InvisibleScrollBarButton();
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                int alpha = isThumbRollover() ? SCROLL_BAR_ALPHA_ROLLOVER : SCROLL_BAR_ALPHA;
                int orientation = scrollbar.getOrientation();
                int x = thumbBounds.x;
                int y = thumbBounds.y;

                int width = orientation == JScrollBar.VERTICAL ? THUMB_SIZE : thumbBounds.width;
                width = Math.max(width, THUMB_SIZE);

                int height = orientation == JScrollBar.VERTICAL ? thumbBounds.height : THUMB_SIZE;
                height = Math.max(height, THUMB_SIZE);

                Graphics2D graphics2D = (Graphics2D) g.create();
                graphics2D.setColor(new Color(THUMB_COLOR.getRed(), THUMB_COLOR.getGreen(), THUMB_COLOR.getBlue(), alpha));
                graphics2D.fillRect(x, y, width, height);
                graphics2D.dispose();
            }

            @Override
            protected void setThumbBounds(int x, int y, int width, int height) {
                super.setThumbBounds(x, y, width, height);
                sp.repaint();
            }

            /**
             * Invisible Buttons, to hide scroll bar buttons
             */
            private class InvisibleScrollBarButton extends JButton {

                private static final long serialVersionUID = 1552427919226628689L;

                private InvisibleScrollBarButton() {
                    setOpaque(false);
                    setFocusable(false);
                    setFocusPainted(false);
                    setBorderPainted(false);
                    setBorder(BorderFactory.createEmptyBorder());
                }
            }
        }
    }
}
