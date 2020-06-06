package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerMove;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.SetWorkerPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

public class BoardGUI implements Runnable{

    private JFrame mainframe;
    private ConnectionManagerSocket connectionManagerSocket;

    private ImageIcon mainPanelImage;
    private Image mainPanelImageScaled, powerColumnImageScaled;

    private ImageIcon level1_Icon, level2_Icon, level3_Icon, dome_Icon;
    private ImageIcon   worker_Man_White_Icon, worker_Woman_White_Icon,
                        worker_Man_Blue_Icon, worker_Woman_Blue_Icon,
                        worker_Man_Grey_Icon, worker_Woman_Grey_Icon;

    private JLayeredPane[][] boardButton;
    private  JPanel BoardPanel;
    private int workersNum;

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

    private class PowerColumn extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            //int updatedWidth = this.getWidth();
            //int updatedHeight = this.getHeight();

            int updatedWidth = 410;
            int updatedHeight = 720;

            if (1280 -  powerColumnImageScaled.getWidth(null) > 720 - powerColumnImageScaled.getHeight(null)) {
                updatedWidth = updatedHeight
                        * powerColumnImageScaled.getWidth(null)
                        / powerColumnImageScaled.getHeight(null);
            }
            if (1280 - powerColumnImageScaled.getWidth(null) < 720 - powerColumnImageScaled.getHeight(null)) {
                updatedHeight = updatedWidth
                        * powerColumnImageScaled.getHeight(null)
                        / powerColumnImageScaled.getWidth(null);
            }

            int x = (410 - updatedWidth) / 2;
            int y = (720 - updatedHeight) / 2;
            g.drawImage(powerColumnImageScaled, x, y, updatedWidth,
                    updatedHeight, null);
        }
    }

    private class SetWorker implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(BoardGUI.this.workersNum<2){
                int x = (int) getCellX(BoardPanel.getMousePosition().getX());
                int y = (int) getCellY(BoardPanel.getMousePosition().getY());
                connectionManagerSocket.sendObjectToServer(new SetWorkerPosition(y, x, connectionManagerSocket.getPlayerColorEnum(), connectionManagerSocket.getclientID(), BoardGUI.this.workersNum+1));
            }

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

        }

        public double getCellX(double x){
            return (x/100);
        }

        public double getCellY(double y){
            return (y/100);
        }

    }

    public BoardGUI(JFrame mainframe, ConnectionManagerSocket connectionManagerSocket){
        this.mainframe = mainframe;
        this.workersNum = 0;
        this.connectionManagerSocket = connectionManagerSocket;
        //this.mainframe.setSize(1280,755);
        //this.boardButton = new ArrayList<JLayeredPane>();
        this.boardButton = new JLayeredPane[5][5];

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
        gbcBoardConstraint.insets = new Insets(113, 145, 115, 640);
        gbcBoardConstraint.anchor = GridBagConstraints.WEST;
        gbcBoardConstraint.fill = GridBagConstraints.BOTH;
        gbcBoardConstraint.gridx = 0;
        gbcBoardConstraint.gridy = 0;

        JLayeredPane BoardContainer = new JLayeredPane();
        BoardPanel = new JPanel(new GridLayout(5,5,8,8));
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

        Image worker_Man_Blue = new ImageIcon(PATH + "Blue_Worker_Man.png").getImage();
        worker_Man_Blue_Icon = new ImageIcon(worker_Man_Blue.getScaledInstance(29,50,Image.SCALE_SMOOTH));
        Image worker_Woman_Blue = new ImageIcon(PATH + "Blue_Worker_Woman.png").getImage();
        worker_Woman_Blue_Icon = new ImageIcon(worker_Woman_Blue.getScaledInstance(29,50,Image.SCALE_SMOOTH));

        Image worker_Man_White = new ImageIcon(PATH + "White_Worker_Man.png").getImage();
        worker_Man_White_Icon = new ImageIcon(worker_Man_White.getScaledInstance(29,50,Image.SCALE_SMOOTH));
        Image worker_Woman_White = new ImageIcon(PATH + "White_Worker_Woman.png").getImage();
        worker_Woman_White_Icon = new ImageIcon(worker_Woman_White.getScaledInstance(29,50,Image.SCALE_SMOOTH));

        Image worker_Man_Grey = new ImageIcon(PATH + "Grey_Worker_Man.png").getImage();
        worker_Man_Grey_Icon = new ImageIcon(worker_Man_Grey.getScaledInstance(29,50,Image.SCALE_SMOOTH));
        Image worker_Woman_Grey = new ImageIcon(PATH + "Grey_Worker_Woman.png").getImage();
        worker_Woman_Grey_Icon = new ImageIcon(worker_Woman_Grey.getScaledInstance(29,50,Image.SCALE_SMOOTH));


        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                boardButton[x][y] = new JLayeredPane();
                BoardPanel.add(boardButton[x][y]);
            }
        }


        GridBagConstraints gbcDxPanelConstraint = new GridBagConstraints();
        gbcDxPanelConstraint.insets = new Insets(0, 0, 0, 0);
        gbcDxPanelConstraint.anchor = GridBagConstraints.EAST;
        gbcDxPanelConstraint.fill = GridBagConstraints.VERTICAL;
        gbcDxPanelConstraint.gridx = 0;
        gbcDxPanelConstraint.gridy = 0;

        JPanel dxPanel = new JPanel();

        GridBagLayout gridBagLayoutDxPanel = new GridBagLayout();
        gridBagLayoutDxPanel.columnWidths = new int[] { 0, 0 };
        gridBagLayoutDxPanel.rowHeights = new int[] { 0, 0 };
        gridBagLayoutDxPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gridBagLayoutDxPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        dxPanel.setLayout(gridBagLayoutDxPanel);

        dxPanel.setBackground(new Color(0,0,0,0));

        Image moveButton = new ImageIcon(PATH + "MoveButton.png").getImage();
        ImageIcon moveButton_Icon = new ImageIcon(moveButton.getScaledInstance(120,120,Image.SCALE_SMOOTH));
        Image buildButton = new ImageIcon(PATH + "BuildButton.png").getImage();
        ImageIcon buildButton_Icon = new ImageIcon(buildButton.getScaledInstance(120,120,Image.SCALE_SMOOTH));
        Image domeButton = new ImageIcon(PATH + "DomeButton.png").getImage();
        ImageIcon domeButton_Icon = new ImageIcon(domeButton.getScaledInstance(120,87,Image.SCALE_SMOOTH));
        Image doneButton = new ImageIcon(PATH + "DoneButton.png").getImage();
        ImageIcon doneButton_Icon = new ImageIcon(doneButton.getScaledInstance(120,118,Image.SCALE_SMOOTH));

        JLabel moveButtonLabel = new JLabel(moveButton_Icon);
        moveButtonLabel.setVisible(true);
        moveButtonLabel.setOpaque(true);
        moveButtonLabel.setBackground(new Color(0,0,0,0));

        JLabel buildButtonLabel = new JLabel(buildButton_Icon);
        buildButtonLabel.setVisible(true);
        buildButtonLabel.setOpaque(true);
        buildButtonLabel.setBackground(new Color(0,0,0,0));

        JLabel domeButtonLabel = new JLabel(domeButton_Icon);
        domeButtonLabel.setVisible(true);
        domeButtonLabel.setOpaque(true);
        domeButtonLabel.setBackground(new Color(0,0,0,0));
        domeButtonLabel.setBounds(0, 0, 90, 90);

        JLabel doneButtonLabel = new JLabel(doneButton_Icon);
        doneButtonLabel.setVisible(true);
        doneButtonLabel.setOpaque(true);
        doneButtonLabel.setBackground(new Color(0,0,0,0));
        doneButtonLabel.setBounds(0, 0, 90, 90);

        GridBagConstraints gbcButtonColumnConstraint = new GridBagConstraints();
        gbcButtonColumnConstraint.insets = new Insets(0, 0, 0, 0);
        gbcButtonColumnConstraint.anchor = GridBagConstraints.WEST;
        gbcButtonColumnConstraint.gridx = 0;
        gbcButtonColumnConstraint.gridy = 0;
        gbcButtonColumnConstraint.weightx= 1;

        JPanel buttonColumn = new JPanel(new GridLayout(4,1, 0,6));
        buttonColumn.setVisible(true);
        buttonColumn.setOpaque(true);
        buttonColumn.setBackground(new Color(0,0,0,0));
        buttonColumn.add(moveButtonLabel);
        buttonColumn.add(buildButtonLabel);
        buttonColumn.add(domeButtonLabel);
        buttonColumn.add(doneButtonLabel);

        dxPanel.add(buttonColumn, gbcButtonColumnConstraint);

        ImageIcon powerColumnImage = new ImageIcon(PATH + "powerColumn.png");
        powerColumnImageScaled = powerColumnImage.getImage();
        JPanel powerColumn = new PowerColumn();
        powerColumn.setPreferredSize(new Dimension(410,720));
        powerColumn.setVisible(true);
        powerColumn.setBackground(new Color(0,0,0,0));
        gbcButtonColumnConstraint.gridx = 1;
        gbcButtonColumnConstraint.gridwidth= 2;

        dxPanel.add(powerColumn, gbcButtonColumnConstraint);


        mainPanel.add(dxPanel, gbcDxPanelConstraint);
        mainPanel.add(BoardPanel, gbcBoardConstraint);

        mainPanel.setVisible(true);
        mainframe.setVisible(true);
        mainframe.setResizable(false);
        mainframe.pack();
    }

    @Override
    public void run() {
        this.connectionManagerSocket.initializeMessageSocket(this);
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

    public void setWorkers(){
        this.workersNum = 0;
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                boardButton[x][y].addMouseListener(new SetWorker());
            }
        }
    }
    public void removeSetWorkersListener(){
        this.workersNum = 0;
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                boardButton[x][y].removeMouseListener(new SetWorker());
            }
        }
    }

    public void incrementWorkerNum(){
        this.workersNum++;
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
        JLabel worker = new JLabel(worker_Man_Blue_Icon);
        worker.setVisible(true);
        worker.setOpaque(true);
        worker.setBackground(new Color(0,0,0,0));
        worker.setBounds(30, 20, 29, 50);
        currCell.add(worker,0);
    }
    public void addWorkerWoman(JLayeredPane currCell){
        JLabel worker = new JLabel(worker_Woman_Blue_Icon);
        worker.setVisible(true);
        worker.setOpaque(true);
        worker.setBackground(new Color(0,0,0,0));
        worker.setBounds(30, 20, 29, 50);
        currCell.add(worker,0);
    }
    public void addWorkerToBoard(int workerNum, PlayerColor color, int x, int y){
        JLabel worker = null;
        switch (color){
            case WHITE: worker = (workerNum == 1 ? new JLabel(worker_Man_White_Icon) : new JLabel(worker_Woman_White_Icon));
                        break;
            case GREY: worker = (workerNum == 1 ? new JLabel(worker_Man_Grey_Icon) : new JLabel(worker_Woman_Grey_Icon));
                        break;
            case BLUE: worker = (workerNum == 1 ? new JLabel(worker_Man_Blue_Icon) : new JLabel(worker_Woman_Blue_Icon));
                        break;
        }

        worker.setVisible(true);
        worker.setOpaque(true);
        worker.setBackground(new Color(0,0,0,0));
        worker.setBounds(30, 20, 29, 50);
        boardButton[x][y].add(worker,0);
    }
    public void removeWorker(JLayeredPane currCell){
        currCell.remove(0);
    }

    public int getWorkersNum() {
        return workersNum;
    }
}



