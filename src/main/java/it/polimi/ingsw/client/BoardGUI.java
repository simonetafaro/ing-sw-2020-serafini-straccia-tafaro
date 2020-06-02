package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerMove;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class BoardGUI implements Runnable{

    private JFrame mainframe;
    private ConnectionManagerSocket connectionManagerSocket;


    private ImageIcon mainPanelImage;
    private Image mainPanelImageScaled;


    private JLabel c00;
    private JLabel c01;
    private JLabel c02;
    private JLabel c03;
    private JLabel c04;
    private JLabel c10;
    private JLabel c11;
    private JLabel c12;
    private JLabel c13;
    private JLabel c14;
    private JLabel c20;
    private JLabel c21;
    private JLabel c22;
    private JLabel c23;
    private JLabel c24;
    private JLabel c30;
    private JLabel c31;
    private JLabel c32;
    private JLabel c33;
    private JLabel c34;
    private JLabel c40;
    private JLabel c41;
    private JLabel c42;
    private JLabel c43;
    private JLabel c44;

    private ArrayList<JLabel> boardButton;

    private static final String SRC = "src";
    private static final String MAIN = "main";
    private static final String RESOURCES = "resources";
    private static final String IMAGE = "images";
    private static final String PATH = SRC + File.separatorChar + MAIN + File.separatorChar + RESOURCES + File.separatorChar + IMAGE + File.separatorChar;

    private class MainJPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            //int updatedWidth = this.getWidth();
            //int updatedHeight = this.getHeight();

            int updatedWidth = 1280;
            int updatedHeight = 720;

            if (1280 -  mainPanelImageScaled.getWidth(null) > 720 - mainPanelImageScaled.getHeight(null)) {
                updatedWidth = updatedHeight
                        * mainPanelImageScaled.getWidth(null)
                        / mainPanelImageScaled.getHeight(null);
            }
            if (1280 - mainPanelImageScaled.getWidth(null) < 720 - mainPanelImageScaled.getHeight(null)) {
                updatedHeight = updatedWidth
                        * mainPanelImageScaled.getHeight(null)
                        / mainPanelImageScaled.getWidth(null);
            }

            int x = (1280 - updatedWidth) / 2;
            int y = (720 - updatedHeight) / 2;
            g.drawImage(mainPanelImageScaled, x, y, updatedWidth,
                    updatedHeight, null);
        }
    }

    public BoardGUI(JFrame mainframe, ConnectionManagerSocket connectionManagerSocket){
        this.mainframe = mainframe;
        this.connectionManagerSocket = connectionManagerSocket;
        //this.mainframe.setSize(1280,755);
        this.boardButton = new ArrayList<JLabel>();
        this.boardButton.add(c00);
        this.boardButton.add(c01);
        this.boardButton.add(c02);
        this.boardButton.add(c03);
        this.boardButton.add(c04);
        this.boardButton.add(c10);
        this.boardButton.add(c11);
        this.boardButton.add(c12);
        this.boardButton.add(c13);
        this.boardButton.add(c14);
        this.boardButton.add(c20);
        this.boardButton.add(c21);
        this.boardButton.add(c22);
        this.boardButton.add(c23);
        this.boardButton.add(c24);
        this.boardButton.add(c30);
        this.boardButton.add(c31);
        this.boardButton.add(c32);
        this.boardButton.add(c33);
        this.boardButton.add(c34);
        this.boardButton.add(c40);
        this.boardButton.add(c41);
        this.boardButton.add(c42);
        this.boardButton.add(c43);
        this.boardButton.add(c44);

        mainPanelImage = new ImageIcon(PATH + "BoardBackground.png");
        mainPanelImageScaled = mainPanelImage.getImage();
        JPanel mainPanel = new MainJPanel();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainPanel.setPreferredSize(new Dimension(1280,720));
        mainframe.setLocation(dim.width/2-1280/2, dim.height/2-720/2);
        mainframe.add(mainPanel);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        mainPanel.setLayout(gridBagLayout);

        GridBagConstraints gbcBoardConstraint = new GridBagConstraints();
        gbcBoardConstraint.insets = new Insets(110, 145, 115, 640);
        gbcBoardConstraint.anchor = GridBagConstraints.WEST;
        gbcBoardConstraint.fill = GridBagConstraints.BOTH;
        gbcBoardConstraint.gridx = 0;
        gbcBoardConstraint.gridy = 0;

        JPanel BoardPanel = new JPanel(new GridLayout(5,5,8,8));
        BoardPanel.setBackground(new Color(0,0,0,0));
        BoardPanel.setOpaque(false);

        Image ApolloCard = new ImageIcon(PATH + "W_Workers.png").getImage();
        ImageIcon ApolloCard_Icon = new ImageIcon(ApolloCard);


        boardButton.forEach((currentButton)->{
            currentButton = new JLabel();
            currentButton.setBackground(new Color(0,0,0,50));
            currentButton.setIcon(ApolloCard_Icon);
            currentButton.setOpaque(true);
            BoardPanel.add(currentButton);
        });

        mainPanel.add(BoardPanel, gbcBoardConstraint);

        mainPanel.setVisible(true);
        mainframe.setVisible(true);
        mainframe.setResizable(false);
        mainframe.pack();
    }

    @Override
    public void run() {
        //this.connectionManagerSocket.initializeMessageSocket();
        //System.out.println("gui"+connectionManagerSocket.getclientID());
        //PlayerMove playermove =new PlayerMove("test"+connectionManagerSocket.getclientID());
        //this.connectionManagerSocket.sendToServer(playermove);

    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("prova");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        SwingUtilities.invokeLater(new BoardGUI(mainFrame,  null));
    }
}



