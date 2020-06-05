package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class BoardGUI implements Runnable{

    private JFrame mainframe;
    private ConnectionManagerSocket connectionManagerSocket;

    private ImageIcon mainPanelImage;
    private Image mainPanelImageScaled;

    private ImageIcon level1_Icon, level2_Icon, level3_Icon, dome_Icon;
    private ImageIcon worker_Man_Icon, worker_Woman_Icon;

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

    private class SetWorker implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }


    public BoardGUI(JFrame mainframe, ConnectionManagerSocket connectionManagerSocket){
        this.mainframe = mainframe;
        this.connectionManagerSocket = connectionManagerSocket;
        //this.mainframe.setSize(1280,755);
        this.boardButton = new ArrayList<JLayeredPane>();

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
        level1_Icon = new ImageIcon(level1.getScaledInstance(90,90,Image.SCALE_SMOOTH));
        Image level2 = new ImageIcon(PATH + "level2.png").getImage();
        level2_Icon = new ImageIcon(level2.getScaledInstance(75,75,Image.SCALE_SMOOTH));
        Image level3 = new ImageIcon(PATH + "level3.png").getImage();
        level3_Icon = new ImageIcon(level3.getScaledInstance(60,60,Image.SCALE_SMOOTH));
        Image dome = new ImageIcon(PATH + "dome.png").getImage();
        dome_Icon = new ImageIcon(dome.getScaledInstance(45,45,Image.SCALE_SMOOTH));
        Image worker_Man = new ImageIcon(PATH + "Blue_Worker_Man.png").getImage();
        worker_Man_Icon = new ImageIcon(worker_Man.getScaledInstance(29,50,Image.SCALE_SMOOTH));
        Image worker_Woman = new ImageIcon(PATH + "Blue_Worker_Woman.png").getImage();
        worker_Woman_Icon = new ImageIcon(worker_Woman.getScaledInstance(29,50,Image.SCALE_SMOOTH));


        for(int i=0; i<25; i++){
            JLayeredPane currCell = new JLayeredPane();
            boardButton.add(currCell);
            BoardPanel.add(currCell);
        }

        addLevel1(boardButton.get(1));
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
        //boardButton.forEach((currButton)-> currButton.);
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

    public void addLevel1(JLayeredPane currCell){
        JLabel l1 = new JLabel(level1_Icon);
        l1.setVisible(true);
        l1.setOpaque(true);
        l1.setBackground(new Color(0,0,0,0));
        l1.setBounds(0, 0, 90, 90);
        currCell.add(l1, -1);
    }
    public void addLevel2(JLayeredPane currCell){
        JLabel lv2 = new JLabel(level2_Icon);
        lv2.setVisible(true);
        lv2.setOpaque(true);
        lv2.setBackground(new Color(0,0,0,0));
        lv2.setBounds(7, 7, 75, 75);
        currCell.add(lv2,0);
    }
    public void addLevel3(JLayeredPane currCell){
        JLabel lv3 = new JLabel(level3_Icon);
        lv3.setVisible(true);
        lv3.setOpaque(true);
        lv3.setBackground(new Color(0,0,0,0));
        lv3.setBounds(15, 15, 60, 60);
        currCell.add(lv3,0);
    }
    public void addDome(JLayeredPane currCell){
        JLabel dome = new JLabel(dome_Icon);
        dome.setVisible(true);
        dome.setOpaque(true);
        dome.setBackground(new Color(0,0,0,0));
        dome.setBounds(22, 22, 45, 45);
        currCell.add(dome,0);
    }
    public void addWorkerMan(JLayeredPane currCell){
        JLabel worker = new JLabel(worker_Man_Icon);
        worker.setVisible(true);
        worker.setOpaque(true);
        worker.setBackground(new Color(0,0,0,0));
        worker.setBounds(30, 20, 29, 50);
        currCell.add(worker,0);
    }
    public void addWorkerWoman(JLayeredPane currCell){
        JLabel worker = new JLabel(worker_Woman_Icon);
        worker.setVisible(true);
        worker.setOpaque(true);
        worker.setBackground(new Color(0,0,0,0));
        worker.setBounds(30, 20, 29, 50);
        currCell.add(worker,0);
    }
    public void removeWorker(JLayeredPane currCell){
        currCell.remove(0);
    }
}



