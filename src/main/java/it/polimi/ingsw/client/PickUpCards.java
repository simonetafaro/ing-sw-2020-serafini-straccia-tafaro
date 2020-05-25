package it.polimi.ingsw.client;

import it.polimi.ingsw.utils.CustomDate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

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
    private ImageIcon Artemis_Icon;
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

    private JLabel ImageContainer;
    private JLabel logoLabel;
    private int cardCounter;
    private ArrayList<JCheckBox> buttonList;
    private JRadioButton playButton;

    private int playerNumber;
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
        int x,y;

        public PickGodActionListener(JCheckBox button, ImageIcon pressedButtonImage, ImageIcon buttonImage, ImageIcon text, ImageIcon godImage) {
            this.pressedButtonImage = pressedButtonImage;
            this.ButtonImage = buttonImage;
            this.Text = text;
            this.GodImage = godImage;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(PickGodActionListener.this.button.isSelected()){
                PickUpCards.this.cardCounter ++;
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

        }
    }

    public PickUpCards(JFrame mainFrame, int playerNumber) {
        this.playerNumber = playerNumber;
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

        Image ArtemisImage = new ImageIcon(PATH + "Artemis_Image.png").getImage();
        Artemis_Icon = new ImageIcon(ArtemisImage);

        PanImage = new ImageIcon(PATH + "Pan_Image.png");
        AtlasImage = new ImageIcon(PATH + "Atlas_Image.png");
        DemeterImage = new ImageIcon(PATH + "Demeter_Image.png");
        HephaestusImage = new ImageIcon(PATH + "Hephaestus_Image.png");
        MinotaurImage = new ImageIcon(PATH + "Minotaur_Image.png");
        AthenaImage = new ImageIcon(PATH + "Athena_Image.png");
        PrometheusImage = new ImageIcon(PATH + "Prometheus_Image.png");
        ApolloImage = new ImageIcon(PATH + "Apollo_Image.png");

        ImageContainer = new JLabel();
        ImageContainer.setBackground(new Color(0,0,0,0));
        //ImageContainer.setOpaque(false);
        centralPanel.add(ImageContainer, gbcCardsImageLabel);

        eastPanelImage = new ImageIcon(PATH + "Dx_Panel_Cards_new.png");
        eastPanelImageScaled = new ImageIcon(eastPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();;
        JPanel eastPanel = new PickUpCards.EastJPanel();
        eastPanel.setPreferredSize(new Dimension(459, 720));
        eastPanel.setOpaque(true);
        eastPanel.setLayout(gblSxPanel);
        rootPanel.add(eastPanel, BorderLayout.EAST);

        GridBagConstraints gbcCardsLabel = new GridBagConstraints();
        gbcCardsLabel.insets = new Insets(150, 0, 5, 60);
        gbcCardsLabel.anchor = GridBagConstraints.CENTER;
        gbcCardsLabel.fill = GridBagConstraints.VERTICAL;
        gbcCardsLabel.gridx = 0;
        gbcCardsLabel.gridy = 0;

        JPanel card = new JPanel(new GridLayout(3,3,5,5));
        card.setBackground(new Color(0,0,0,0));
        card.setOpaque(false);

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

        buttonList.add(MinotaurButton);
        buttonList.add(ApolloButton);
        buttonList.add(ArtemisButton);
        buttonList.add(PanButton);
        buttonList.add(DemeterButton);
        buttonList.add(HephaestusButton);
        buttonList.add(PrometheusButton);
        buttonList.add(AtlasButton);
        buttonList.add(AthenaButton);

        eastPanel.add(card, gbcCardsLabel);


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
        MinotaurButton.addActionListener(new PickGodActionListener(MinotaurButton, MinotaurCard_Icon_Pressed, MinotaurCard_Icon, MinotaurText, MinotaurImage));
        AtlasButton.addActionListener(new PickGodActionListener(AtlasButton, AtlasCard_Icon_Pressed, AtlasCard_Icon, AtlasText, AtlasImage));
        AthenaButton.addActionListener(new PickGodActionListener(AthenaButton, AthenaCard_Icon_Pressed, AthenaCard_Icon, AthenaText, AthenaImage));
        ArtemisButton.addActionListener(new PickGodActionListener(ArtemisButton, ArtemisCard_Icon_Pressed, ArtemisCard_Icon, ArtemisText, Artemis_Icon));
        PanButton.addActionListener(new PickGodActionListener(PanButton, PanCard_Icon_Pressed, PanCard_Icon, PanText, PanImage));
        HephaestusButton.addActionListener(new PickGodActionListener(HephaestusButton, HephaestusCard_Icon_Pressed, HephaestusCard_Icon, HephaestusText, HephaestusImage));
        PrometheusButton.addActionListener(new PickGodActionListener(PrometheusButton, PrometheusCard_Icon_Pressed, PrometheusCard_Icon, PrometheusText, PrometheusImage));
        DemeterButton.addActionListener(new PickGodActionListener(DemeterButton, DemeterCard_Icon_Pressed, DemeterCard_Icon, DemeterText, DemeterImage));
        ApolloButton.addActionListener(new PickGodActionListener(ApolloButton, ApolloCard_Icon_Pressed, ApolloCard_Icon, ApolloText, ApolloImage));
        playButton.addActionListener(new PlayActionListener());
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("prova");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        SwingUtilities.invokeLater(new PickUpCards(mainFrame, 2));
    }
}
