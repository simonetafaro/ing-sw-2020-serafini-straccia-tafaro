package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.FileManager;
import it.polimi.ingsw.utils.PlayerColor;
import it.polimi.ingsw.utils.SetWorkerPosition;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.List;
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
    private JLabel additionalIsland;
    private JLayeredPane[][] boardButton;
    private JPanel BoardPanel;
    private JPanel powerColumn;
    private JPanel topPowerColumn;
    private List<JRadioButton> opponentPowers;
    private int workersNum;
    private JLabel powerTextContainer;
    private JLabel myPowerDescription;
    private static final String SRC = "src";
    private static final String MAIN = "main";
    private static final String RESOURCES = "resources";
    private static final String IMAGE = "images";
    private static final String PATH = SRC + File.separatorChar + MAIN + File.separatorChar + RESOURCES + File.separatorChar + IMAGE + File.separatorChar;
    private static final String PATHFILE = "toolcards/";

    private PlayerMove move;
    private PlayerMove moveEnd;

    public JButton worker1;
    public JButton worker2;

    private JButton moveButton;
    private JButton buildButton;
    private JButton domeButton;
    private JButton doneButton;
    private PopUpBox popUpBox;

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
                int x = (int) getCell(BoardPanel.getMousePosition().getX());
                int y = (int) getCell(BoardPanel.getMousePosition().getY());
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

        public double getCell(double x){
            return (x/100);
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
                case WHITE: //workers = new ImageIcon(PATH + "W_Workers.png").getImage();
                            workers = new ImageIcon(this.getClass().getResource("/images/W_Workers.png")).getImage();
                            workers_scaled = new ImageIcon(workers.getScaledInstance(72,62, Image.SCALE_SMOOTH));
                            this.workerImage = workers_scaled;
                            break;
                case BLUE:  //workers = new ImageIcon(PATH + "B_Workers.png").getImage();
                            workers = new ImageIcon(this.getClass().getResource("/images/B_Workers.png")).getImage();
                            workers_scaled = new ImageIcon(workers.getScaledInstance(72,62, Image.SCALE_SMOOTH));
                            this.workerImage = workers_scaled;
                            break;
                case GREY:  //workers = new ImageIcon(PATH + "G_Workers.png").getImage();
                            workers = new ImageIcon(this.getClass().getResource("/images/G_Workers.png")).getImage();
                            workers_scaled = new ImageIcon(workers.getScaledInstance(72,62, Image.SCALE_SMOOTH));
                            this.workerImage = workers_scaled;
                            break;
            }
            this.textDescription = textDescription;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
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
            int column = (int) getCellX(BoardPanel.getMousePosition().getX());
            int row = (int) getCellY(BoardPanel.getMousePosition().getY());

            if((BoardGUI.this.move.getWorker() != null) && (BoardGUI.this.move.getMoveOrBuild() != null)) {
                BoardGUI.this.move.setRow(row);
                BoardGUI.this.move.setColumn(column);
                verifyPlayerMove();
            }else
                BoardGUI.this.wrongStepSequence();
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
    private class ButtonMoveListeners implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(BoardGUI.this.move.getWorker() != null) {
                BoardGUI.this.move.setMoveOrBuild("M");
                verifyPlayerMove();
            }else
                BoardGUI.this.wrongStepSequence();
        }
    }
    private class ButtonBuildListeners implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(BoardGUI.this.move.getWorker() != null) {
                BoardGUI.this.move.setMoveOrBuild("B");
                verifyPlayerMove();
            }else
                BoardGUI.this.wrongStepSequence();
        }
    }
    private class ButtonDomeListeners implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(BoardGUI.this.connectionManagerSocket.getPlayer().getMyCard().getName().equals("Atlas")
                || (BoardGUI.this.connectionManagerSocket.getPlayer().getMyCard().getName().equals("Selene"))){
                if(BoardGUI.this.move.getWorker() != null) {
                    BoardGUI.this.move.setMoveOrBuild("D");
                    verifyPlayerMove();
                }
            }else{
                BoardGUI.this.popUpBox.infoBox("You can't use this button. Only with Atlas or Selene you can build Dome everywhere", "DomeButton");
            }

        }
    }
    private class ButtonDoneListeners implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            BoardGUI.this.moveEnd = new PlayerMoveEnd(connectionManagerSocket.getPlayer(), true);
            verifyPlayerMove();
        }
    }
    private class WorkerListeners implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int x = (int) getCellX(BoardPanel.getMousePosition().getX());
            int y = (int) getCellY(BoardPanel.getMousePosition().getY());
            Cell workerPosition = new Cell(x, y);
            //reset PlayerMove when click on worker different from worker already selected

            if (BoardGUI.this.connectionManagerSocket.getPlayer().getWorker1().getWorkerPosition().getPosX() == workerPosition.getPosY() && BoardGUI.this.connectionManagerSocket.getPlayer().getWorker1().getWorkerPosition().getPosY() == workerPosition.getPosX()) {
                if (BoardGUI.this.move.getWorker() == null) {
                    BoardGUI.this.move.setWorker(BoardGUI.this.connectionManagerSocket.getPlayer().getWorker1());
                }else {
                    if (!BoardGUI.this.move.getWorker().equals(BoardGUI.this.connectionManagerSocket.getPlayer().getWorker1())) {
                        BoardGUI.this.move = new PlayerMove(connectionManagerSocket.getPlayer());
                        BoardGUI.this.move.setWorker(BoardGUI.this.connectionManagerSocket.getPlayer().getWorker1());
                    }
                }
            }
            else {
                if(BoardGUI.this.connectionManagerSocket.getPlayer().getWorker2().getWorkerPosition().getPosX() == workerPosition.getPosY() && BoardGUI.this.connectionManagerSocket.getPlayer().getWorker2().getWorkerPosition().getPosY() == workerPosition.getPosX()) {
                    if(BoardGUI.this.move.getWorker() == null) {
                        BoardGUI.this.move.setWorker(BoardGUI.this.connectionManagerSocket.getPlayer().getWorker2());
                    }else {
                        if(!BoardGUI.this.move.getWorker().equals(BoardGUI.this.connectionManagerSocket.getPlayer().getWorker2())){
                            BoardGUI.this.move = new PlayerMove(connectionManagerSocket.getPlayer());
                            BoardGUI.this.move.setWorker(BoardGUI.this.connectionManagerSocket.getPlayer().getWorker2());
                        }
                    }
                }
            }
            verifyPlayerMove();
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
        this.popUpBox = new PopUpBox();

        //mainPanelImage = new ImageIcon(PATH + "BoardBackground.png");
        mainPanelImage = new ImageIcon(this.getClass().getResource("/images/BoardBackground.png"));

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

        //Image level1 = new ImageIcon(PATH + "level1.png").getImage();
        Image level1 =  new ImageIcon(this.getClass().getResource("/images/level1.png")).getImage();
        level1_Icon = new ImageIcon(level1.getScaledInstance(90,90,Image.SCALE_SMOOTH));
        //Image level2 = new ImageIcon(PATH + "level2.png").getImage();
        Image level2 =  new ImageIcon(this.getClass().getResource("/images/level2.png")).getImage();
        level2_Icon = new ImageIcon(level2.getScaledInstance(75,75,Image.SCALE_SMOOTH));
        //Image level3 = new ImageIcon(PATH + "level3.png").getImage();
        Image level3 =  new ImageIcon(this.getClass().getResource("/images/level3.png")).getImage();
        level3_Icon = new ImageIcon(level3.getScaledInstance(60,60,Image.SCALE_SMOOTH));
        //Image dome = new ImageIcon(PATH + "dome.png").getImage();
        Image dome =  new ImageIcon(this.getClass().getResource("/images/dome.png")).getImage();
        dome_Icon = new ImageIcon(dome.getScaledInstance(45,45,Image.SCALE_SMOOTH));

        //Image worker_Man_Blue = new ImageIcon(PATH + "Blue_Worker_Man.png").getImage();
        Image worker_Man_Blue =  new ImageIcon(this.getClass().getResource("/images/Blue_Worker_Man.png")).getImage();
        worker_Man_Blue_Icon = new ImageIcon(worker_Man_Blue.getScaledInstance(29,50,Image.SCALE_SMOOTH));
        //Image worker_Woman_Blue = new ImageIcon(PATH + "Blue_Worker_Woman.png").getImage();
        Image worker_Woman_Blue =  new ImageIcon(this.getClass().getResource("/images/Blue_Worker_Woman.png")).getImage();
        worker_Woman_Blue_Icon = new ImageIcon(worker_Woman_Blue.getScaledInstance(29,50,Image.SCALE_SMOOTH));

        //Image worker_Man_White = new ImageIcon(PATH + "White_Worker_Man.png").getImage();
        Image worker_Man_White =  new ImageIcon(this.getClass().getResource("/images/White_Worker_Man.png")).getImage();
        worker_Man_White_Icon = new ImageIcon(worker_Man_White.getScaledInstance(29,50,Image.SCALE_SMOOTH));
        //Image worker_Woman_White = new ImageIcon(PATH + "White_Worker_Woman.png").getImage();
        Image worker_Woman_White =  new ImageIcon(this.getClass().getResource("/images/White_Worker_Woman.png")).getImage();
        worker_Woman_White_Icon = new ImageIcon(worker_Woman_White.getScaledInstance(29,50,Image.SCALE_SMOOTH));

        //Image worker_Man_Grey = new ImageIcon(PATH + "Grey_Worker_Man.png").getImage();
        Image worker_Man_Grey =  new ImageIcon(this.getClass().getResource("/images/Grey_Worker_Man.png")).getImage();
        worker_Man_Grey_Icon = new ImageIcon(worker_Man_Grey.getScaledInstance(29,50,Image.SCALE_SMOOTH));
        //Image worker_Woman_Grey = new ImageIcon(PATH + "Grey_Worker_Woman.png").getImage();
        Image worker_Woman_Grey =  new ImageIcon(this.getClass().getResource("/images/Grey_Worker_Woman.png")).getImage();
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

        //Image moveImage = new ImageIcon(PATH + "MoveButton.png").getImage();
        Image moveImage =  new ImageIcon(this.getClass().getResource("/images/MoveButton.png")).getImage();
        ImageIcon moveButton_Icon = new ImageIcon(moveImage.getScaledInstance(120,120,Image.SCALE_SMOOTH));
        //Image buildImage = new ImageIcon(PATH + "BuildButton.png").getImage();
        Image buildImage =  new ImageIcon(this.getClass().getResource("/images/BuildButton.png")).getImage();
        ImageIcon buildButton_Icon = new ImageIcon(buildImage.getScaledInstance(120,120,Image.SCALE_SMOOTH));
        //Image domeImage = new ImageIcon(PATH + "DomeButton.png").getImage();
        Image domeImage =  new ImageIcon(this.getClass().getResource("/images/DomeButton.png")).getImage();
        ImageIcon domeButton_Icon = new ImageIcon(domeImage.getScaledInstance(120,87,Image.SCALE_SMOOTH));
        //Image doneImage = new ImageIcon(PATH + "DoneButton.png").getImage();
        Image doneImage =  new ImageIcon(this.getClass().getResource("/images/DoneButton.png")).getImage();
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

        //ImageIcon powerColumnImage = new ImageIcon(PATH + "powerColumn.png");
        ImageIcon powerColumnImage =  new ImageIcon(this.getClass().getResource("/images/powerColumn.png"));
        powerColumnImageScaled = powerColumnImage.getImage();

        //ImageIcon additionalIslandImage = new ImageIcon(PATH + "island_image.png");
        ImageIcon additionalIslandImage =  new ImageIcon(this.getClass().getResource("/images/island_image.png"));

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

        //this.move = new PlayerMove(connectionManagerSocket.getPlayer());

        mainPanel.setVisible(true);
        mainframe.setVisible(true);
        mainframe.setResizable(false);
        mainframe.pack();
    }

    @Override
    public void run() {
        this.connectionManagerSocket.initializeMessageSocket(this);
    }

    public void setWorkers(){
        this.workersNum = 0;
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                boardButton[x][y].addMouseListener(new SetWorker());
            }
        }
    }

    public JLayeredPane getBoardButton(int x, int y){
        return this.boardButton[x][y];
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
        this.move = new PlayerMove(connectionManagerSocket.getPlayer());
        addMoveListeners();
        addButtonListeners();
        addWorkerListeners();
    }
    public void addMyPowerInfo (Player player, FileManager fileFinder){
        try {
            Document document = fileFinder.getFileDocument(PATHFILE.concat(player.getMyCard().getName()).concat(".xml"));

            NodeList GodImage = document.getElementsByTagName("ImageIconURL");
            //Image god_Image = new ImageIcon(PATH + GodImage.item(0).getTextContent()).getImage();
            Image god_Image = new ImageIcon(this.getClass().getResource("/images/"+GodImage.item(0).getTextContent())).getImage();

            ImageIcon god_Image_Icon = new ImageIcon(god_Image);

            NodeList powerDescription = document.getElementsByTagName("PowerDescription");
            String text = powerDescription.item(0).getTextContent();
            StringBuilder sb = new StringBuilder();
            String color = player.getColor().toString();
            sb.append("<html>"+"COLOR -> "+ color + "  " + System.lineSeparator()+text+"</html>");
            this.myPowerDescription.setText(sb.toString());


        } catch (IOException | ParserConfigurationException | SAXException | URISyntaxException e){
            System.err.println(e.getMessage());
        }
    }
    public void createPlayerInfo(Player player, FileManager fileFinder, int index){
        try{
            Document document = fileFinder.getFileDocument(PATHFILE.concat(player.getMyCard().getName()).concat(".xml"));

            NodeList GodImage = document.getElementsByTagName("ImageIconURL");
            //Image god_Image = new ImageIcon(PATH + GodImage.item(0).getTextContent()).getImage();
            Image god_Image = new ImageIcon(this.getClass().getResource("/images/"+GodImage.item(0).getTextContent())).getImage();

            ImageIcon god_Image_Icon = new ImageIcon(god_Image);

            NodeList GodImagePressed = document.getElementsByTagName("ImagePressedIconURL");
            //Image GodImagePressed_Image = new ImageIcon(PATH + GodImagePressed.item(0).getTextContent()).getImage();
            Image GodImagePressed_Image = new ImageIcon(this.getClass().getResource("/images/"+GodImagePressed.item(0).getTextContent())).getImage();

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
            StringBuilder sb = new StringBuilder();
            String color = player.getColor().toString();
            sb.append("<html>"+"COLOR -> "+ color + "  "+ System.lineSeparator()+text+"</html>");
            this.powerTextContainer.setText(sb.toString());

            godImageButton.addActionListener(new ChangePowerDescription(godImageButton, player.getColor(), sb));

        }catch (IOException | ParserConfigurationException | SAXException | URISyntaxException e ){
            System.err.println(e.getMessage());
        }
    }

    public void addLevelToBoard(int j, JLayeredPane cell){
        switch (j){
            case 1: addLevel1(cell);
                break;
            case 2: addLevel2(cell);
                break;
            case 3: addLevel3(cell);
                break;
            case 4: addDome(cell);
                break;
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

    public void addMyWorkerToBoard(int workerNum, PlayerColor color, int x, int y){
        switch (color){
            case WHITE: if(workerNum == 1) {
                            this.worker1 = new JButton(worker_Man_White_Icon);
                            addReadyWorker(this.worker1,x,y);
                        } else{
                            this.worker2 = new JButton(worker_Woman_White_Icon);
                            addReadyWorker(this.worker2,x,y);
                        }
                        break;
            case GREY:  if(workerNum == 1) {
                            this.worker1 = new JButton(worker_Man_Grey_Icon);
                            addReadyWorker(this.worker1,x,y);
                        } else{
                            this.worker2 = new JButton(worker_Woman_Grey_Icon);
                            addReadyWorker(this.worker2,x,y);
                        }
                        break;
            case BLUE:  if(workerNum == 1) {
                            this.worker1 = new JButton(worker_Man_Blue_Icon);
                            addReadyWorker(this.worker1,x,y);
                        } else{
                            this.worker2 = new JButton(worker_Woman_Blue_Icon);
                            addReadyWorker(this.worker2,x,y);
                        }
                        break;
        }
    }
    public void addWorker(PlayerColor color, int workerNum, int x, int y){
        switch (color){
            case WHITE: if(workerNum == 1) {
                addWorkerToBoard(worker_Man_White_Icon,x ,y);
            } else{
                addWorkerToBoard(worker_Woman_White_Icon,x ,y);
            }
                break;
            case GREY:  if(workerNum == 1) {
                addWorkerToBoard(worker_Man_Grey_Icon,x ,y);
            } else{
                addWorkerToBoard(worker_Woman_Grey_Icon,x ,y);
            }
                break;
            case BLUE:  if(workerNum == 1) {
                addWorkerToBoard(worker_Man_Blue_Icon,x ,y);
            } else{
                addWorkerToBoard(worker_Woman_Blue_Icon,x ,y);
            }
                break;
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
    public void addWorkerToBoard(ImageIcon icon, int x, int y){
        JLabel worker = new JLabel(icon);
        worker.setVisible(true);
        worker.setOpaque(true);
        worker.setBackground(new Color(0,0,0,0));
        worker.setBounds(30, 20, 29, 50);
        this.boardButton[x][y].add(worker,5,0);
        this.boardButton[x][y].setLayer(worker,5);
    }

    public void addMoveListeners(){
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                this.boardButton[x][y].addMouseListener(new MoveListeners());
            }
        }
    }
    public void addButtonListeners(){
        this.moveButton.addActionListener(new ButtonMoveListeners());
        this.buildButton.addActionListener(new ButtonBuildListeners());
        this.doneButton.addActionListener(new ButtonDoneListeners());
        if(connectionManagerSocket.getPlayer().getMyCard().getName().equals("Atlas") || connectionManagerSocket.getPlayer().getMyCard().getName().equals("Selene"))
            this.domeButton.addActionListener(new ButtonDomeListeners());
    }
    public void addWorkerListeners(){
        this.worker1.addActionListener(new WorkerListeners());
        this.worker2.addActionListener(new WorkerListeners());
    }

    public void verifyPlayerMove() {
        if(this.moveEnd != null){
            connectionManagerSocket.sendObjectToServer(moveEnd);
            BoardGUI.this.moveEnd = null;
        }
        else{
            if(BoardGUI.this.move.getPlayer()!= null && BoardGUI.this.move.getRow()!= -1 && BoardGUI.this.move.getColumn()!= -1
                    && BoardGUI.this.move.getWorker()!=null && BoardGUI.this.move.getMoveOrBuild()!=null) {
                connectionManagerSocket.sendObjectToServer(move);
                BoardGUI.this.move = new PlayerMove(connectionManagerSocket.getPlayer());
            }
        }
    }

    public JButton getWorker1(){
        return this.worker1;
    }
    public JButton getWorker2(){
        return this.worker2;
    }
    public PopUpBox getPopUpBox() {
        return popUpBox;
    }

    public void wrongStepSequence(){
        this.popUpBox.infoBox("Wrong sequence! \n You should select your worker first, than select the action you would like to perform and last you click the cell!", "WrongStepMethod");
    }
    public class PopUpBox {

        public void infoBox(String infoMessage, String titleBar)
        {
            //JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
            int TIME_VISIBLE = 5000;
            JOptionPane pane = new JOptionPane(infoMessage,
                    JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = pane.createDialog(mainframe, titleBar);
            dialog.setModal(false);
            dialog.setVisible(true);

            new Timer(TIME_VISIBLE, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.setVisible(false);
                }
            }).start();

        }
    }

    public int CloseGuiOrNot(){
        return JOptionPane.showConfirmDialog(mainframe,
                "Press YES to continue to watch the game or NO if you want to close.",
                "Workers stuck, you lose!",
                JOptionPane.YES_NO_CANCEL_OPTION);
    }

}