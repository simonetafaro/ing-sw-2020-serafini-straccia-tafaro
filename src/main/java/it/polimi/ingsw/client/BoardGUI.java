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


    private JButton c00;
    private JButton c01;
    private JButton c02;
    private JButton c03;
    private JButton c04;
    private JButton c10;
    private JButton c11;
    private JButton c12;
    private JButton c13;
    private JButton c14;
    private JButton c20;
    private JButton c21;
    private JButton c22;
    private JButton c23;
    private JButton c24;
    private JButton c30;
    private JButton c31;
    private JButton c32;
    private JButton c33;
    private JButton c34;
    private JButton c40;
    private JButton c41;
    private JButton c42;
    private JButton c43;
    private JButton c44;

    private ArrayList<JButton> boardButton;

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
        this.boardButton = new ArrayList<JButton>();
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

        boardButton.forEach((currentButton)->{
            currentButton = new JButton();
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



