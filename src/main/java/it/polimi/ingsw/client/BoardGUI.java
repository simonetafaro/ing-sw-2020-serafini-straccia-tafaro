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


    private JLayeredPane c00;
    private JLayeredPane c01;
    private JLayeredPane c02;
    private JLayeredPane c03;
    private JLayeredPane c04;
    private JLayeredPane c10;
    private JLayeredPane c11;
    private JLayeredPane c12;
    private JLayeredPane c13;
    private JLayeredPane c14;
    private JLayeredPane c20;
    private JLayeredPane c21;
    private JLayeredPane c22;
    private JLayeredPane c23;
    private JLayeredPane c24;
    private JLayeredPane c30;
    private JLayeredPane c31;
    private JLayeredPane c32;
    private JLayeredPane c33;
    private JLayeredPane c34;
    private JLayeredPane c40;
    private JLayeredPane c41;
    private JLayeredPane c42;
    private JLayeredPane c43;
    private JLayeredPane c44;

    private ArrayList<JLayeredPane> boardButton;

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
        this.boardButton = new ArrayList<JLayeredPane>();

        /*this.boardButton.add(c00);
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
        */
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

        JLayeredPane BoardContainer = new JLayeredPane();
        JPanel BoardPanel = new JPanel(new GridLayout(5,5,8,8));
        BoardContainer.add(BoardPanel, JLayeredPane.DEFAULT_LAYER);
        BoardPanel.setBackground(new Color(0,0,0,0));
        BoardPanel.setOpaque(false);
        BoardPanel.setVisible(true);

        Image level1 = new ImageIcon(PATH + "level1.png").getImage();
        ImageIcon level1_Icon = new ImageIcon(level1);
        Image level2 = new ImageIcon(PATH + "level2.png").getImage();
        ImageIcon level2_Icon = new ImageIcon(level2);
        Image level3 = new ImageIcon(PATH + "level3.png").getImage();
        ImageIcon level3_Icon = new ImageIcon(level3);
        Image dome = new ImageIcon(PATH + "dome.png").getImage();
        ImageIcon dome_Icon = new ImageIcon(dome);

        JLabel level1_label = new JLabel(level1_Icon);
        level1_label.setOpaque(true);
        level1_label.setVisible(true);
        JLabel level2_label = new JLabel(level2_Icon);
        level2_label.setOpaque(true);
        level2_label.setVisible(true);
        JLabel level3_label = new JLabel(level3_Icon);
        level3_label.setOpaque(true);
        level3_label.setVisible(true);
        JLabel dome_label = new JLabel(dome_Icon);
        dome_label.setOpaque(true);
        dome_label.setVisible(true);

        for(int i=0; i<25; i++){
            JLayeredPane currCell = new JLayeredPane();
            JLabel l1 = new JLabel(level1_Icon);
            l1.setVisible(true);
            l1.setOpaque(true);
            l1.setBackground(new Color(0,0,0,0));
            currCell.add(l1, -1);
            boardButton.add(currCell);
            BoardPanel.add(currCell);
        }
        JLabel currCell1 = new JLabel(dome_Icon);
        currCell1.setVisible(true);
        currCell1.setOpaque(true);
        currCell1.setBackground(new Color(0,0,0,0));
        boardButton.get(1).add(currCell1,0);

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



