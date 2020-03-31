package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.CustomDate;
import it.polimi.ingsw.utils.PlayerColor;

import java.util.Scanner;

public class Player {

    private PlayerColor color;
    private CustomDate birthdate;
    private String nickname;

    private Worker worker1;
    private Worker worker2;
    private boolean worker1Stuck, worker2Stuck;
    //TODO card attribute with getter and setter
    // private Card myCard;


    public Player(){}

    public Player(Player player) {
        this.nickname = player.getNickname();
        this.color = player.getColor();
        this.birthdate = player.getBirthdate();
        this.worker1=player.worker1;
        this.worker2=player.worker2;
        worker1Stuck=false;
        worker2Stuck=false;
    }

    public String getNickname() {
        return nickname;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public void setBirthdate(CustomDate birtday) {
        this.birthdate = birtday;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Worker getWorker1() {
        return worker1;
    }

    public Worker getWorker2() {
        return worker2;
    }

    public void setWorker1(Worker worker1) {
        this.worker1 = worker1;
    }

    public void setWorker2(Worker worker2) {
        this.worker2 = worker2;
    }

    public PlayerColor getColor() {
        return color;
    }

    public CustomDate getBirthdate() {
        return birthdate;
    }

    public void setWorkerPosition(Board myBoard){
        int x,y;
        Scanner in = new Scanner(System.in);
        //System.out.println(nickname);
        do {
            do {
                System.out.println("Choose the cell for worker1");
                System.out.println("Choose the ROW");
                x = in.nextInt();
                System.out.println("Choose the COLUMN");
                y = in.nextInt();

            } while (x < 0 || x >= 5 || y < 0 || y >= 5);
            System.out.println("You choose ( " + x + " , " + y + " )");

        }while(!(myBoard.getBoard()[x][y]).isFree());

        worker1 = new Worker(myBoard.getBoard()[x][y], 1, this.color);
        (myBoard.getBoard()[x][y]).setCurrWorker(this.worker1);

        //(myBoard.getBoard()[x][y]).printCell();

        do {
            do {
                System.out.println("Choose the cell for worker2");
                System.out.println("Choose the ROW");
                x = in.nextInt();
                System.out.println("Choose the COLUMN");
                y = in.nextInt();
            } while (x < 0 || x >= 5 || y < 0 || y >= 5);
            System.out.println("You choose ( " + x + " , " + y + " )");
        }while(!(myBoard.getBoard()[x][y]).isFree());
        worker2=new Worker(myBoard.getBoard()[x][y],2, this.color);
        (myBoard.getBoard()[x][y]).setCurrWorker(this.worker2);

    }

    public boolean isWorker1Stuck() {
        return worker1Stuck;
    }

    public boolean isWorker2Stuck() {
        return worker2Stuck;
    }

    public void play(Board islandBoard){
        Scanner in = new Scanner(System.in);
        int x,y;
        System.out.println(this.getNickname()+" which worker do you want to move: Worker1 or Worker2 ?");

        switch (in.nextInt()){
            case 1:     if(worker1.getWorkerPosition().hasFreeCellClosed(islandBoard.getBoard())){
                            do{
                                do {
                                    System.out.println("Please insert the cell where you want to move your worker");
                                    x = in.nextInt();
                                    y = in.nextInt();
                                }while(x<0 || x>4 || y<0 || y>4);
                            }while(!worker1.move(islandBoard.getBoard()[x][y]));
                            if(worker1.winner()){
                                break;
                            }

                            worker1.getWorkerPosition().canBuildInCells(islandBoard.getBoard());
                            do{
                                do{
                                    System.out.println("Please insert the cell where you want to build");
                                    x=in.nextInt();
                                    y=in.nextInt();
                                }while(x<0 || x>4 || y<0 || y>4);
                            }while(!worker1.build(islandBoard.getBoard()[x][y]));
                        }
                        else{
                            System.out.println("You can't move Worker1");
                            this.worker1Stuck = true;
                            if(this.worker2Stuck){
                                System.out.println("You are out!");
                                break;
                            }
                            this.play(islandBoard);
                        }
                        break;

            case 2:     if(worker2.getWorkerPosition().hasFreeCellClosed(islandBoard.getBoard())){
                            do{
                                do {
                                    System.out.println("Please insert the cell where you want to move your worker");
                                    x = in.nextInt();
                                    y = in.nextInt();
                                }while(x<0 || x>4 || y<0 || y>4);
                            }while(!worker2.move(islandBoard.getBoard()[x][y]));
                            if(worker1.winner()){
                                break;
                            }

                            worker2.getWorkerPosition().canBuildInCells(islandBoard.getBoard());
                            do{
                                do{
                                    System.out.println("Please insert the cell where you want to build");
                                    x=in.nextInt();
                                    y=in.nextInt();
                                }while(x<0 || x>4 || y<0 || y>4);
                            }while(!worker2.build(islandBoard.getBoard()[x][y]));
                        }
                        else{
                                System.out.println("You can't move Worker2");
                                this.worker2Stuck = true;
                                if(this.worker1Stuck){
                                    System.out.println("You are out!");
                                    break;
                                }
                                this.play(islandBoard);
                            }
                        break;

            default:    System.out.println("Invalid Input");
                        this.play(islandBoard);
        }
        this.worker2Stuck =false;
        this.worker1Stuck =false;
    }
}
