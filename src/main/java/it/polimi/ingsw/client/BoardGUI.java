package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.FileManager;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.SetWorkerPosition;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.regex.Pattern;

public class BoardGUI implements Runnable{

    private JFrame mainframe;
    private ConnectionManagerSocket connectionManagerSocket;

    private ImageIcon mainPanelImage;
    private Image mainPanelImageScaled, powerColumnImageScaled;

    private ImageIcon level1_Icon, level2_Icon, level3_Icon, dome_Icon;
    private ImageIcon   worker_Man_White_Icon, worker_Woman_White_Icon,
                        worker_Man_Blue_Icon, worker_Woman_Blue_Icon,
                        worker_Man_Grey_Icon, worker_Woman_Grey_Icon;
    private JLabel additionalIsland;
    private JLayeredPane[][] boardButton;
    private  JPanel BoardPanel;
    private JPanel powerColumn;
    private JPanel topPowerColumn;
    private List<JRadioButton> opponentPowers;
    private int workersNum;
    private JLabel powerTextContainer;
    private JLabel myPowerDescription;
    private Player player;
    private static final String SRC = "src";
    private static final String MAIN = "main";
    private static final String RESOURCES = "resources";
    private static final String IMAGE = "images";
    private static final String PATH = SRC + File.separatorChar + MAIN + File.separatorChar + RESOURCES + File.separatorChar + IMAGE + File.separatorChar;
    private static final String PATHFILE = "toolcards/";

    private Worker workerMove;

    private JButton worker1G;
    private JButton worker2G;
    private JButton worker1W;
    private JButton worker2W;
    private JButton worker1B;
    private JButton worker2B;

    private JButton moveButton;
    private JButton buildButton;
    private JButton domeButton;
    private JButton doneButton;

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

            int updatedWidth = 322;
            int updatedHeight = 600;

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

            int x = (322 - updatedWidth) / 2;
            int y = (600 - updatedHeight) / 2;
            g.drawImage(powerColumnImageScaled, x, y, updatedWidth,
                    updatedHeight, null);
        }
    }

    private class SetWorker implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
                int x = (int) getCellX(BoardPanel.getMousePosition().getX());
                int y = (int) getCellY(BoardPanel.getMousePosition().getY());
                connectionManagerSocket.sendObjectToServer(new SetWorkerPosition(y, x, connectionManagerSocket.getPlayerColorEnum(), connectionManagerSocket.getclientID(), BoardGUI.this.workersNum+1));

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
    private class ChangePowerDescription implements ActionListener{
        ImageIcon workerImage;
        StringBuilder textDescription;
        JRadioButton button;
        public ChangePowerDescription(JRadioButton button, PlayerColor color, StringBuilder textDescription) {
            Image workers;
            ImageIcon workers_scaled;
            switch (color){
                case WHITE: workers = new ImageIcon(PATH + "W_Workers.png").getImage();
                            workers_scaled = new ImageIcon(workers.getScaledInstance(72,62, Image.SCALE_SMOOTH));
                            this.workerImage = workers_scaled;
                            break;
                case BLUE:  workers = new ImageIcon(PATH + "B_Workers.png").getImage();
                            workers_scaled = new ImageIcon(workers.getScaledInstance(72,62, Image.SCALE_SMOOTH));
                            this.workerImage = workers_scaled;
                            break;
                case GREY:  workers = new ImageIcon(PATH + "G_Workers.png").getImage();
                            workers_scaled = new ImageIcon(workers.getScaledInstance(72,62, Image.SCALE_SMOOTH));
                            this.workerImage = workers_scaled;
                            break;
            }
            this.textDescription = textDescription;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            /*
            BoardGUI.this.opponentColor.removeAll();
            BoardGUI.this.opponentColor.add(workerImage);
            */
            BoardGUI.this.opponentPowers.forEach((button) -> {
                button.setEnabled(true);
            });
            this.button.setEnabled(false);
            BoardGUI.this.powerTextContainer.removeAll();
            BoardGUI.this.powerTextContainer.setText(textDescription.toString());

        }
    }
    private class MoveListeners implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount()<2) {
                removeButtonListeners();
                System.out.println("mouseLiatenres");
                PlayerMove move = new PlayerMove(BoardGUI.this.connectionManagerSocket.getPlayer(), BoardGUI.this.workerMove.getWorkerNum(), (int) getCell(BoardPanel.getMousePosition().getY()), (int) getCell(BoardPanel.getMousePosition().getX()), "M");
                //removeWorker(BoardGUI.this.workerMove.getWorkerPosition().getPosX(), BoardGUI.this.workerMove.getWorkerPosition().getPosY());
                //addWorkerToBoard(BoardGUI.this.workerMove.getWorkerNum(), BoardGUI.this.workerMove.getPlayerColor(),
                //        (int) getCellY(BoardPanel.getMousePosition().getY()), (int) getCellX(BoardPanel.getMousePosition().getX()));
                connectionManagerSocket.sendObjectToServer(move);
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

        public double getCell(double x){
            return (x/100);
        }

        /*public double getCellY(double y){
            return (y/100);
        }

         */

    }
    private class BuildListeners implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount()<2) {
                removeButtonListeners();
                PlayerMove move = new PlayerMove(BoardGUI.this.connectionManagerSocket.getPlayer(), BoardGUI.this.workerMove.getWorkerNum(), (int) getCell(BoardPanel.getMousePosition().getY()), (int) getCell(BoardPanel.getMousePosition().getX()), "B");
                //addLevel(BoardGUI.this.boardButton[(int) getCell(BoardPanel.getMousePosition().getY())][(int) getCell(BoardPanel.getMousePosition().getX())]);
                connectionManagerSocket.sendObjectToServer(move);
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

        public double getCell(double x){
            return (x/100);
        }
        /*
        public double getCellY(double y){
            return (y/100);
        }

         */

    }
    private class ButtonMoveListeners implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("ButtonListeners");
            //BoardGUI.this.worker1.addActionListener(new WorkerListeners());
            //BoardGUI.this.worker2.addActionListener(new WorkerListeners());
            //removeWorkerListeners();
            addMoveListeners();
        }
    }
    private class ButtonBuildListeners implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //BoardGUI.this.worker1.addActionListener(new WorkerListeners());
            //BoardGUI.this.worker2.addActionListener(new WorkerListeners());
            //removeWorkerListeners();
            addBuildListeners();
        }
    }
    private class ButtonDomeListeners implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    private class ButtonDoneListeners implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("actionDonelisteners");
            PlayerMoveEnd moveEnd = new PlayerMoveEnd(connectionManagerSocket.getPlayer(), true);
            //removeMoveListeners();
            connectionManagerSocket.sendObjectToServer(moveEnd);
        }
    }
    private class WorkerListeners implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("workerListeners");
            int x = (int) getCellX(BoardPanel.getMousePosition().getX());
            int y = (int) getCellY(BoardPanel.getMousePosition().getY());
            System.out.println(x+", "+y);
            System.out.println(BoardGUI.this.connectionManagerSocket.getPlayer().getWorker1().getWorkerPosition().getPosX()+", "+BoardGUI.this.connectionManagerSocket.getPlayer().getWorker1().getWorkerPosition().getPosY());
            System.out.println(BoardGUI.this.connectionManagerSocket.getPlayer().getWorker2().getWorkerPosition().getPosX()+", "+BoardGUI.this.connectionManagerSocket.getPlayer().getWorker2().getWorkerPosition().getPosY());
            Cell workerPosition = new Cell(x, y);
            if(BoardGUI.this.connectionManagerSocket.getPlayer().getWorker1().getWorkerPosition().getPosX() == workerPosition.getPosY() &&
                    BoardGUI.this.connectionManagerSocket.getPlayer().getWorker1().getWorkerPosition().getPosY() == workerPosition.getPosX()) {
                System.out.println("uguale a worker1");
                BoardGUI.this.workerMove = BoardGUI.this.connectionManagerSocket.getPlayer().getWorker1();
                addButtonListeners();
            }else if(BoardGUI.this.connectionManagerSocket.getPlayer().getWorker2().getWorkerPosition().getPosX() == workerPosition.getPosY() &&
                        BoardGUI.this.connectionManagerSocket.getPlayer().getWorker2().getWorkerPosition().getPosY() == workerPosition.getPosX()) {
                System.out.println("uguale a worker2");
                BoardGUI.this.workerMove = BoardGUI.this.connectionManagerSocket.getPlayer().getWorker2();
                addButtonListeners();
            }
/*
            BoardGUI.this.name = BoardGUI.this.boardButton[x][y].getComponent(0).getName();
            System.out.println(name);
            char color = name.charAt(0);
            System.out.println(color);
            char workerN = name.charAt(1);
            System.out.println(workerN);
            switch (color) {
                case 'B':
                    if (workerN == 'W')
                        BoardGUI.this.worker = new Worker(workerPosition, 2, PlayerColor.BLUE);
                    else
                        BoardGUI.this.worker = new Worker(workerPosition, 1, PlayerColor.BLUE);
                    break;
                case 'W':
                    if (workerN == 'W')
                        BoardGUI.this.worker = new Worker(workerPosition, 2, PlayerColor.WHITE);
                    else
                        BoardGUI.this.worker = new Worker(workerPosition, 1, PlayerColor.WHITE);
                    break;
                case 'G':
                    if (workerN == 'W')
                        BoardGUI.this.worker = new Worker(workerPosition, 2, PlayerColor.GREY);
                    else
                        BoardGUI.this.worker = new Worker(workerPosition, 1, PlayerColor.GREY);
                    break;
            }
 */
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

        Image moveImage = new ImageIcon(PATH + "MoveButton.png").getImage();
        ImageIcon moveButton_Icon = new ImageIcon(moveImage.getScaledInstance(120,120,Image.SCALE_SMOOTH));
        Image buildImage = new ImageIcon(PATH + "BuildButton.png").getImage();
        ImageIcon buildButton_Icon = new ImageIcon(buildImage.getScaledInstance(120,120,Image.SCALE_SMOOTH));
        Image domeImage = new ImageIcon(PATH + "DomeButton.png").getImage();
        ImageIcon domeButton_Icon = new ImageIcon(domeImage.getScaledInstance(120,87,Image.SCALE_SMOOTH));
        Image doneImage = new ImageIcon(PATH + "DoneButton.png").getImage();
        ImageIcon doneButton_Icon = new ImageIcon(doneImage.getScaledInstance(120,118,Image.SCALE_SMOOTH));

        this.moveButton = new JButton(moveButton_Icon);
        this.moveButton.setVisible(true);
        this.moveButton.setOpaque(true);
        this.moveButton.setBorder(null);
        this.moveButton.setContentAreaFilled(false);
        this.moveButton.setBackground(new Color(0,0,0,0));

        this.buildButton = new JButton(buildButton_Icon);
        this.buildButton.setVisible(true);
        this.buildButton.setOpaque(true);
        this.buildButton.setBorder(null);
        this.buildButton.setContentAreaFilled(false);
        this.buildButton.setBackground(new Color(0,0,0,0));

        this.domeButton = new JButton(domeButton_Icon);
        this.domeButton.setVisible(true);
        this.domeButton.setOpaque(true);
        this.domeButton.setBorder(null);
        this.domeButton.setContentAreaFilled(false);
        this.domeButton.setBackground(new Color(0,0,0,0));
        this.domeButton.setBounds(0, 0, 90, 90);

        this.doneButton = new JButton(doneButton_Icon);
        this.doneButton.setVisible(true);
        this.doneButton.setOpaque(true);
        this.doneButton.setBorder(null);
        this.doneButton.setContentAreaFilled(false);
        this.doneButton.setBackground(new Color(0,0,0,0));
        this.doneButton.setBounds(0, 0, 90, 90);

        GridBagConstraints gbcButtonColumnConstraint = new GridBagConstraints();
        gbcButtonColumnConstraint.insets = new Insets(0, 0, 0, 40);
        gbcButtonColumnConstraint.anchor = GridBagConstraints.WEST;
        gbcButtonColumnConstraint.gridx = 0;
        gbcButtonColumnConstraint.gridy = 0;
        gbcButtonColumnConstraint.weightx= 1;

        JPanel buttonColumn = new JPanel(new GridLayout(4,1, 0,6));
        buttonColumn.setVisible(true);
        buttonColumn.setOpaque(false);
        buttonColumn.setBackground(new Color(0,0,0,0));
        buttonColumn.add(this.moveButton);
        buttonColumn.add(this.buildButton);
        buttonColumn.add(this.domeButton);
        buttonColumn.add(this.doneButton);

        dxPanel.add(buttonColumn, gbcButtonColumnConstraint);

        topPowerColumn = new JPanel(new BorderLayout());
        topPowerColumn.setPreferredSize(new Dimension(215,180));
        topPowerColumn.setBackground(new Color(0,0,0,0));
        topPowerColumn.setVisible(true);
        topPowerColumn.setOpaque(false);

        ImageIcon powerColumnImage = new ImageIcon(PATH + "powerColumn.png");
        powerColumnImageScaled = powerColumnImage.getImage();

        ImageIcon additionalIslandImage = new ImageIcon(PATH + "island_image.png");

        powerColumn = new PowerColumn();
        powerColumn.setPreferredSize(new Dimension(322,600));
        powerColumn.setVisible(true);
        powerColumn.setOpaque(false);
        powerColumn.setBackground(new Color(0,0,0,0));
        gbcButtonColumnConstraint.gridx = 1;
        gbcButtonColumnConstraint.gridwidth= 2;
        gbcButtonColumnConstraint.anchor = GridBagConstraints.NORTH;
        gbcButtonColumnConstraint.insets = new Insets(20,0,0,5);
        dxPanel.add(topPowerColumn, gbcButtonColumnConstraint);

        gbcButtonColumnConstraint.anchor = GridBagConstraints.SOUTH;

        //topPowerColumn.add(godImage, BorderLayout.WEST);
        gbcButtonColumnConstraint.insets = new Insets(0,0,0,5);
        dxPanel.add(powerColumn, gbcButtonColumnConstraint);

        additionalIsland = new JLabel(additionalIslandImage);
        additionalIsland.setBackground(new Color(0,0,0,0));
        additionalIsland.setVisible(false);
        additionalIsland.setOpaque(false);
        gbcButtonColumnConstraint.insets = new Insets(125,0,0,5);
        gbcButtonColumnConstraint.anchor = GridBagConstraints.NORTHEAST;
        dxPanel.add(additionalIsland, gbcButtonColumnConstraint);

        powerTextContainer = new JLabel();
        powerTextContainer.setVisible(true);
        powerTextContainer.setPreferredSize(new Dimension(200,250));
        gbcButtonColumnConstraint.insets = new Insets(0,0,123,5);
        gbcButtonColumnConstraint.anchor = GridBagConstraints.CENTER;
        dxPanel.add(powerTextContainer, gbcButtonColumnConstraint);
        dxPanel.setComponentZOrder(powerTextContainer, 0);

        myPowerDescription = new JLabel();
        myPowerDescription.setVisible(true);
        myPowerDescription.setPreferredSize(new Dimension(200,250));
        gbcButtonColumnConstraint.insets = new Insets(0,0,0,5);
        gbcButtonColumnConstraint.anchor = GridBagConstraints.SOUTH;
        dxPanel.add(myPowerDescription, gbcButtonColumnConstraint);
        dxPanel.setComponentZOrder(myPowerDescription, 0);

        dxPanel.setOpaque(false);
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
        //this.workersNum = 0;
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                this.boardButton[x][y].removeMouseListener(boardButton[x][y].getMouseListeners()[0]);
            }

        }
    }

    public void incrementWorkerNum(){
        this.workersNum++;
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

    public void addAdditionalIsland(){
        this.additionalIsland.setVisible(true);
        this.mainframe.validate();
    }

    public int getWorkersNum() {
        return workersNum;
    }

    public void populatePlayersInfo(ArrayList players){
        FileManager f = new FileManager();
        this.opponentPowers = new ArrayList<JRadioButton>();
        int index = 0;
        if(players.size() == 3)
            addAdditionalIsland();
        for(Object p : players){
            if(p instanceof Player){
                if(((Player) p).getColor() != connectionManagerSocket.getPlayerColorEnum()){
                    createPlayerInfo((Player) p, f, index);
                    index ++;
                }else{
                    this.connectionManagerSocket.setPlayer((Player)p);;
                    addMyPowerInfo((Player) p, f);
                }
            }
        }
        addWorkerListeners();
    }
    public void addMyPowerInfo (Player player, FileManager fileFinder){
        try {
            Document document = fileFinder.getFileDocument(PATHFILE.concat(player.getMyCard().getName()).concat(".xml"));

            NodeList GodImage = document.getElementsByTagName("ImageIconURL");
            Image god_Image = new ImageIcon(PATH + GodImage.item(0).getTextContent()).getImage();
            ImageIcon god_Image_Icon = new ImageIcon(god_Image);

            NodeList powerDescription = document.getElementsByTagName("PowerDescription");
            String text = powerDescription.item(0).getTextContent();
            StringBuilder sb = new StringBuilder(64);
            sb.append("<html>"+ text +"</html>");
            this.myPowerDescription.setText(sb.toString());


        } catch (IOException | ParserConfigurationException | SAXException | URISyntaxException e){
            System.err.println(e.getMessage());
        }
    }
    public void createPlayerInfo(Player player, FileManager fileFinder, int index){
        try{
            Document document = fileFinder.getFileDocument(PATHFILE.concat(player.getMyCard().getName()).concat(".xml"));

            NodeList GodImage = document.getElementsByTagName("ImageIconURL");
            Image god_Image = new ImageIcon(PATH + GodImage.item(0).getTextContent()).getImage();
            ImageIcon god_Image_Icon = new ImageIcon(god_Image);

            NodeList GodImagePressed = document.getElementsByTagName("ImagePressedIconURL");
            Image GodImagePressed_Image = new ImageIcon(PATH + GodImagePressed.item(0).getTextContent()).getImage();
            ImageIcon GodImagePressed_Icon = new ImageIcon(GodImagePressed_Image);

            JRadioButton godImageButton = new JRadioButton();
            godImageButton.setIcon(god_Image_Icon);
            godImageButton.setDisabledIcon(GodImagePressed_Icon);
            godImageButton.setVisible(true);
            godImageButton.setOpaque(false);
            if(index == 0){
                this.topPowerColumn.add(godImageButton, BorderLayout.WEST);
                this.topPowerColumn.setOpaque(false);
            }else {
                this.topPowerColumn.add(godImageButton, BorderLayout.EAST);
            }
            this.opponentPowers.add(godImageButton);
            this.mainframe.validate();

            NodeList powerDescription = document.getElementsByTagName("PowerDescription");
            String text = powerDescription.item(0).getTextContent();
            StringBuilder sb = new StringBuilder(64);
            sb.append("<html>"+ text +"</html>");
            this.powerTextContainer.setText(sb.toString());

            godImageButton.addActionListener(new ChangePowerDescription(godImageButton, player.getColor(), sb));

        }catch (IOException | ParserConfigurationException | SAXException | URISyntaxException e ){
            System.err.println(e.getMessage());
        }
    }

    public void addLevel1(JLayeredPane currCell){
        JLabel lv1 = new JLabel(level1_Icon);
        lv1.setVisible(true);
        lv1.setOpaque(false);
        lv1.setBorder(null);
        lv1.setBackground(new Color(0,0,0,0));
        lv1.setBounds(0, 0, 90, 90);
        currCell.add(lv1, 1,-1);
    }
    public void addLevel2(JLayeredPane currCell){
        JLabel lv2 = new JLabel(level2_Icon);
        lv2.setVisible(true);
        lv2.setOpaque(false);
        lv2.setBorder(null);
        lv2.setBackground(new Color(0,0,0,0));
        lv2.setBounds(7, 7, 75, 75);
        currCell.add(lv2,2,0);
    }
    public void addLevel3(JLayeredPane currCell){
        JLabel lv3 = new JLabel(level3_Icon);
        lv3.setVisible(true);
        lv3.setOpaque(false);
        lv3.setBorder(null);
        lv3.setBackground(new Color(0,0,0,0));
        lv3.setBounds(15, 15, 60, 60);
        currCell.add(lv3,3,0);
    }
    public void addDome(JLayeredPane currCell){
        JLabel dome = new JLabel(dome_Icon);
        dome.setVisible(true);
        dome.setOpaque(false);
        dome.setBorder(null);
        dome.setBackground(new Color(0,0,0,0));
        dome.setBounds(22, 22, 45, 45);
        currCell.add(dome,4,0);
    }
    public void addLevel(JLayeredPane currCell){
        int position = currCell.highestLayer();
        switch (position){
            case 0:
                addLevel1(currCell);
                break;
            case 1:
                addLevel2(currCell);
                break;
            case 2:
                addLevel3(currCell);
                break;
            case 3:
                addDome(currCell);
                break;
        }

    }
/*
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
        worker.setOpaque(false);
        worker.setBackground(new Color(0,0,0,0));
        worker.setBounds(30, 20, 29, 50);
        boardButton[x][y].add(worker,0);
    }

 */
    public void addWorkerToBoard(int workerNum, PlayerColor color, int x, int y){
        switch (color){
            case WHITE: if(workerNum == 1) {
                            this.worker1W = new JButton(worker_Man_White_Icon);
                            addReadyWorker(this.worker1W,x,y);
                        } else{
                            this.worker2W = new JButton(worker_Woman_White_Icon);
                            addReadyWorker(this.worker2W,x,y);
                        }
                        break;
            case GREY:  if(workerNum == 1) {
                            this.worker1G = new JButton(worker_Man_Grey_Icon);
                            addReadyWorker(this.worker1G,x,y);
                        } else{
                            this.worker2G = new JButton(worker_Woman_Grey_Icon);
                            addReadyWorker(this.worker2G,x,y);
                        }
                        break;
            case BLUE:  if(workerNum == 1) {
                            this.worker1B = new JButton(worker_Man_Blue_Icon);
                            addReadyWorker(this.worker1B,x,y);
                        } else{
                            this.worker2B = new JButton(worker_Woman_Blue_Icon);
                            addReadyWorker(this.worker2B,x,y);
                        }
                        break;
        }
    }
    public void removeWorker(int x, int y){
        //this.boardButton[x][y].getComponent(0).setVisible(false);
        //this.boardButton[x][y].remove(0);
        /*
        if(this.workerMove.getWorkerNum()==1)
            boardButton[x][y].remove(this.worker1);
        else
            boardButton[x][y].remove(this.worker2);

         */
        //int pos = boardButton[x][y].getLayer(boardButton[x][y].getComponent(0));
        //boardButton[x][y].remove(boardButton[x][y].getComponent(5));
        //boardButton[x][y].getComponent(0).setVisible(false);
        //boardButton[x][y].remove(boardButton[x][y].getComponent(0));
    }
    public void removeWorker(JButton worker){
        int layer = 0;
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                layer = boardButton[x][y].highestLayer();
                if(layer == 5){
                    if(boardButton[x][y].getComponent(0).equals(worker)) {
                        boardButton[x][y].getComponent(0).setVisible(false);
                        boardButton[x][y].remove(0);
                    }
                }
            }
        }
    }
    public void addReadyWorker(JButton worker, int x , int y){
        worker.setVisible(true);
        worker.setOpaque(false);
        worker.setBorder(null);
        worker.setContentAreaFilled(false);
        worker.setBackground(new Color(0,0,0,0));
        worker.setBounds(30, 20, 29, 50);
        boardButton[x][y].add(worker,5,0);
        boardButton[x][y].setLayer(worker,5);
    }

    public void addMoveListeners(){
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                this.boardButton[x][y].addMouseListener(new MoveListeners());
            }
        }
    }
    public void addBuildListeners(){
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                this.boardButton[x][y].addMouseListener(new BuildListeners());
            }
        }
    }
    public void addButtonListeners(){
        this.moveButton.addActionListener(new ButtonMoveListeners());
        this.buildButton.addActionListener(new ButtonBuildListeners());
        this.domeButton.addActionListener(new ButtonDomeListeners());
        this.doneButton.addActionListener(new ButtonDoneListeners());

    }
    public void addWorkerListeners(){
        if(connectionManagerSocket.getPlayerColorEnum().equals(PlayerColor.BLUE)){
            this.worker1B.addActionListener(new WorkerListeners());
            this.worker2B.addActionListener(new WorkerListeners());
        }
        if(connectionManagerSocket.getPlayerColorEnum().equals(PlayerColor.GREY)){
            this.worker1G.addActionListener(new WorkerListeners());
            this.worker2G.addActionListener(new WorkerListeners());
        }
        if(connectionManagerSocket.getPlayerColorEnum().equals(PlayerColor.WHITE)){
            this.worker1W.addActionListener(new WorkerListeners());
            this.worker2W.addActionListener(new WorkerListeners());
        }

    }
    public void removeButtonListeners(){
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                this.boardButton[x][y].removeMouseListener(boardButton[x][y].getMouseListeners()[0]);
            }

        }
    }
    public void removeWorkerListeners(){
        if(connectionManagerSocket.getPlayerColorEnum().equals(PlayerColor.BLUE)){
            this.worker1B.removeActionListener(this.worker1B.getActionListeners()[0]);
            this.worker2B.removeActionListener(this.worker2B.getActionListeners()[0]);
        }
        if(connectionManagerSocket.getPlayerColorEnum().equals(PlayerColor.GREY)){
            this.worker1G.removeActionListener(this.worker1G.getActionListeners()[0]);
            this.worker2G.removeActionListener(this.worker2G.getActionListeners()[0]);
        }
        if(connectionManagerSocket.getPlayerColorEnum().equals(PlayerColor.WHITE)){
            this.worker1W.removeActionListener(this.worker1W.getActionListeners()[0]);
            this.worker2W.removeActionListener(this.worker2W.getActionListeners()[0]);
        }
    }
    public void removeMoveListeners(){
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                this.boardButton[x][y].removeMouseListener(this.boardButton[x][y].getMouseListeners()[0]);
                this.boardButton[x][y].removeMouseListener(this.boardButton[x][y].getMouseListeners()[0]);
            }
        }
    }

    public void showMove(PlayerMove move){
        if(move.getPlayer().getColor().equals(PlayerColor.BLUE)){
            if(move.getWorker().getWorkerNum()==1){
                removeWorker(this.worker1B);
                addReadyWorker(this.worker1B,move.getRow(), move.getColumn());
            }
            else{
                removeWorker(this.worker2B);
                addReadyWorker(this.worker2B,move.getRow(), move.getColumn());
            }
        }
        if(move.getPlayer().getColor().equals(PlayerColor.WHITE)){
            if(move.getWorker().getWorkerNum()==1){
                removeWorker(this.worker1W);
                addReadyWorker(this.worker1W,move.getRow(), move.getColumn());
            }
            else{
                removeWorker(this.worker2W);
                addReadyWorker(this.worker2W,move.getRow(), move.getColumn());
            }
        }
        if(move.getPlayer().getColor().equals(PlayerColor.GREY)){
            if(move.getWorker().getWorkerNum()==1){
                removeWorker(this.worker1G);
                addReadyWorker(this.worker1G,move.getRow(), move.getColumn());
            }
            else{
                removeWorker(this.worker2G);
                addReadyWorker(this.worker2G,move.getRow(), move.getColumn());
            }
        }
    }
    public void showBuild(PlayerMove move){
        addLevel(BoardGUI.this.boardButton[move.getRow()][move.getColumn()]);
    }
}



