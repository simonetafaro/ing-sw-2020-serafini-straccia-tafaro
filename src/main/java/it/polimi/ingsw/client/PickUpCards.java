package it.polimi.ingsw.client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class PickUpCards implements Runnable {

    private Image eastPanelImageScaled;
    private ImageIcon eastPanelImage;

    private Image westPanelImageScaled;
    private ImageIcon westPanelImage;

    private Image centralPanelImageScaled;
    private ImageIcon centralPanelImage;

    private ImageIcon PanText;
    private JCheckBox PanButton;
    private ImageIcon ApolloText;
    private JCheckBox ApolloButton;
    private ImageIcon ArtemisText;
    private JCheckBox ArtemisButton;
    private ImageIcon AthenaText;
    private JCheckBox AthenaButton;
    private ImageIcon AtlasText;
    private JCheckBox AtlasButton;
    private ImageIcon DemeterText;
    private JCheckBox DemeterButton;
    private ImageIcon HephaestusText;
    private JCheckBox HephaetusButton;
    private ImageIcon MinotaurText;
    private JCheckBox MinotaurButton;
    private ImageIcon PrometheusText;
    private JCheckBox PrometheusButton;

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

    public PickUpCards(JFrame mainFrame) {

        JPanel rootPanel = new JPanel(new GridLayout(1,3));

        westPanelImage = new ImageIcon(PATH + "Sx_Panel_Cards.png");
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
        westPanel.setPreferredSize(new Dimension(640, 1280));
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

        JLabel logoLabel = new JLabel();
        logoLabel.setBorder(new EmptyBorder(12, 0, 30, 0));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setIcon(MinotaurText);

        GridBagConstraints gbcDescriptionLabel = new GridBagConstraints();
        gbcDescriptionLabel.insets = new Insets(0, 0, 5, 0);
        gbcDescriptionLabel.anchor = GridBagConstraints.NORTH;
        gbcDescriptionLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcDescriptionLabel.gridx = 0;
        gbcDescriptionLabel.gridy = 0;

        westPanel.add(logoLabel, gbcDescriptionLabel);



        centralPanelImage = new ImageIcon(PATH + "Central_Panel_Cards.png");
        centralPanelImageScaled = new ImageIcon(centralPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();
        JPanel centralPanel = new PickUpCards.CentralJPanel();
        //centralPanel.setBackground(Color.BLACK);
        centralPanel.setPreferredSize(new Dimension(640, 1280));
        centralPanel.setOpaque(true);
        rootPanel.add(centralPanel, BorderLayout.CENTER);

        eastPanelImage = new ImageIcon(PATH + "Dx_Panel_Cards.png");
        eastPanelImageScaled = new ImageIcon(eastPanelImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();
        JPanel eastPanel = new PickUpCards.EastJPanel();
        eastPanel.setPreferredSize(new Dimension(640, 1280));
        eastPanel.setOpaque(true);
        eastPanel.setLayout(gblSxPanel);
        rootPanel.add(eastPanel, BorderLayout.EAST);




        GridBagConstraints gbcCardsLabel = new GridBagConstraints();
        gbcCardsLabel.insets = new Insets(180, 0, 5, 50);
        gbcCardsLabel.anchor = GridBagConstraints.LINE_START;
        gbcCardsLabel.fill = GridBagConstraints.VERTICAL;
        gbcCardsLabel.gridx = 0;
        gbcCardsLabel.gridy = 0;

        JPanel card = new JPanel(new GridLayout(3,3,5,5));
        card.setBackground(new Color(0,0,0,0));
        card.setOpaque(false);

        Image PanCard = new ImageIcon(PATH + "CardPan.png").getImage();
        ImageIcon PanCard_Icon = new ImageIcon(PanCard);
        Image PanCard_pressed = new ImageIcon(PATH + "CardPanPressed.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon PanCard_Icon_Pressed = new ImageIcon(PanCard_pressed);
        PanButton = new JCheckBox();
        PanButton.setBackground(new Color(0,0,0,0));
        PanButton.setOpaque(false);
        PanButton.setHorizontalAlignment(SwingConstants.CENTER);
        PanButton.setIcon(PanCard_Icon);
        card.add(PanButton);

        Image ApolloCard = new ImageIcon(PATH + "CardApollo.png").getImage();
        ImageIcon ApolloCard_Icon = new ImageIcon(ApolloCard);
        Image ApolloCard_pressed = new ImageIcon(PATH + "CardApolloPressed.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon ApolloCard_Icon_Pressed = new ImageIcon(ApolloCard_pressed);
        ApolloButton = new JCheckBox();
        ApolloButton.setBackground(new Color(0,0,0,0));
        ApolloButton.setOpaque(false);
        ApolloButton.setHorizontalAlignment(SwingConstants.CENTER);
        ApolloButton.setIcon(ApolloCard_Icon);
        card.add(ApolloButton);

        Image AtlasCard = new ImageIcon(PATH + "CardAtlas.png").getImage();
        ImageIcon AtlasCard_Icon = new ImageIcon(AtlasCard);
        Image AtlasCard_pressed = new ImageIcon(PATH + "CardAtlasPressed.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon AtlasCard_Icon_Pressed = new ImageIcon(AtlasCard_pressed);
        AtlasButton = new JCheckBox();
        AtlasButton.setBackground(new Color(0,0,0,0));
        AtlasButton.setOpaque(false);
        AtlasButton.setHorizontalAlignment(SwingConstants.CENTER);
        AtlasButton.setIcon(AtlasCard_Icon);
        card.add(AtlasButton);

        Image ArtemisCard = new ImageIcon(PATH + "CardArtemis.png").getImage();
        ImageIcon ArtemisCard_Icon = new ImageIcon(ArtemisCard);
        Image ArtemisCard_pressed = new ImageIcon(PATH + "CardArtemisPressed.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon ArtemisCard_Icon_Pressed = new ImageIcon(ArtemisCard_pressed);
        ArtemisButton = new JCheckBox();
        ArtemisButton.setBackground(new Color(0,0,0,0));
        ArtemisButton.setOpaque(false);
        ArtemisButton.setHorizontalAlignment(SwingConstants.CENTER);
        ArtemisButton.setIcon(ArtemisCard_Icon);
        card.add(ArtemisButton);

        Image AthenaCard = new ImageIcon(PATH + "CardAthena.png").getImage();
        ImageIcon AthenaCard_Icon = new ImageIcon(AthenaCard);
        Image AthenaCard_pressed = new ImageIcon(PATH + "CardAthenaPressed.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon AthenaCard_Icon_Pressed = new ImageIcon(AthenaCard_pressed);
        AthenaButton = new JCheckBox();
        AthenaButton.setBackground(new Color(0,0,0,0));
        AthenaButton.setOpaque(false);
        AthenaButton.setHorizontalAlignment(SwingConstants.CENTER);
        AthenaButton.setIcon(AthenaCard_Icon);
        card.add(AthenaButton);

        Image DemeterCard = new ImageIcon(PATH + "CardDemeter.png").getImage();
        ImageIcon DemeterCard_Icon = new ImageIcon(DemeterCard);
        Image DemeterCard_pressed = new ImageIcon(PATH + "CardDemeterPressed.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon DemeterCard_Icon_Pressed = new ImageIcon(DemeterCard_pressed);
        DemeterButton = new JCheckBox();
        DemeterButton.setBackground(new Color(0,0,0,0));
        DemeterButton.setOpaque(false);
        DemeterButton.setHorizontalAlignment(SwingConstants.CENTER);
        DemeterButton.setIcon(DemeterCard_Icon);
        card.add(DemeterButton);

        Image HephaestusCard = new ImageIcon(PATH + "CardHephaestus.png").getImage();
        ImageIcon HephaestusCard_Icon = new ImageIcon(HephaestusCard);
        Image HephaestusCard_pressed = new ImageIcon(PATH + "CardHephaestusPressed.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon HephaestusCard_Icon_Pressed = new ImageIcon(HephaestusCard_pressed);
        HephaetusButton = new JCheckBox();
        HephaetusButton.setBackground(new Color(0,0,0,0));
        HephaetusButton.setOpaque(false);
        HephaetusButton.setHorizontalAlignment(SwingConstants.CENTER);
        HephaetusButton.setIcon(HephaestusCard_Icon);
        card.add(HephaetusButton);

        Image PrometheusCard = new ImageIcon(PATH + "CardPrometheus.png").getImage();
        ImageIcon PrometheusCard_Icon = new ImageIcon(PrometheusCard);
        Image PrometheusCard_pressed = new ImageIcon(PATH + "CardPrometheusPressed.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon PrometheusCard_Icon_Pressed = new ImageIcon(PrometheusCard_pressed);
        PrometheusButton = new JCheckBox();
        PrometheusButton.setBackground(new Color(0,0,0,0));
        PrometheusButton.setOpaque(false);
        PrometheusButton.setHorizontalAlignment(SwingConstants.CENTER);
        PrometheusButton.setIcon(PrometheusCard_Icon);
        card.add(PrometheusButton);

        Image MinotaurCard = new ImageIcon(PATH + "CardMinotaur.png").getImage();
        ImageIcon MinotaurCard_Icon = new ImageIcon(MinotaurCard);
        Image MinotaurCard_pressed = new ImageIcon(PATH + "CardMinotaurPressed.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon MinotaurCard_Icon_Pressed = new ImageIcon(MinotaurCard_pressed);
        MinotaurButton = new JCheckBox();
        MinotaurButton.setBackground(new Color(0,0,0,0));
        MinotaurButton.setOpaque(false);
        MinotaurButton.setHorizontalAlignment(SwingConstants.CENTER);
        MinotaurButton.setIcon(MinotaurCard_Icon);
        card.add(MinotaurButton);

        eastPanel.add(card, gbcCardsLabel);


        mainFrame.getContentPane().add(rootPanel);
        mainFrame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(1180, 820);
        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
        mainFrame.setResizable(false);
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("prova");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        SwingUtilities.invokeLater(new PickUpCards(mainFrame));
    }
}
