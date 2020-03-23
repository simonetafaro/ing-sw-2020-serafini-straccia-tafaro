package it.polimi.ingsw;

import java.util.Date;
import java.util.Scanner;

public class Player {

    private PlayerColor color;
    private CustomDate birthdate;
    private String nickname;
    private  boolean win ;
    private Worker worker1;
    private Worker worker2;
    //private Card myCard;


    public String getNickname() {
        return nickname;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public void setBirthdate() {
        this.birthdate = new CustomDate();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    /*public void setWorker1(Worker worker1) {
        this.worker1 = worker1;
    }

    public void setWorker2(Worker worker2) {
        this.worker2 = worker2;
    }*/

    public PlayerColor getColor() {
        return color;
    }

    public CustomDate getBirthdate() {
        return birthdate;
    }

    public void setWorkerPosition(Board myBoard){
        int x,y;
        Scanner in = new Scanner(System.in);
        System.out.println(nickname);
        do {
            do {
                System.out.println("Scegli la cella in cui posizionare il tuo worker1");
                System.out.println("Inserisci la coordinata x");
                x = in.nextInt();
                System.out.println("Inserisci la coordinata y");
                y = in.nextInt();

            } while (x < 0 || x >= 5 || y < 0 || y >= 5);
            System.out.println("Hai scelto ( " + x + " , " + y + " )");

        }while(!(myBoard.getBoard()[x][y]).isFree());

        worker1 = new Worker(myBoard.getBoard()[x][y], 1, this.color);
        (myBoard.getBoard()[x][y]).setCurrWorker(this.worker1);

        //(myBoard.getBoard()[x][y]).printCell();

        do {
            do {
                System.out.println("Scegli la cella in cui posizionare il tuo worker2");
                System.out.println("Inserisci la coordinata x");
                x = in.nextInt();
                System.out.println("Inserisci la coordinata y");
                y = in.nextInt();
            } while (x < 0 || x >= 5 || y < 0 || y >= 5);
            System.out.println("Hai scelto ( " + x + " , " + y + " )");
        }while(!(myBoard.getBoard()[x][y]).isFree());
        worker2=new Worker(myBoard.getBoard()[x][y],2, this.color);
        (myBoard.getBoard()[x][y]).setCurrWorker(this.worker2);

    }

    public void play(Board islandBoard){
        Scanner in = new Scanner(System.in);
        int x,y;
        System.out.println("Which worker do you want to move: Worker1 or Worker2 ?");

        switch (in.nextInt()){
            case 1:     worker1.getWorkerPosition().hasFreeCellClosed(islandBoard.getBoard());
                        do{
                            do {
                                System.out.println("Please insert the cell where you want to move your worker");
                                x = in.nextInt();
                                y = in.nextInt();
                            }while(x<0 || x>4 || y<0 || y>4);
                        }while(!worker1.move(islandBoard.getBoard()[x][y]));

                        worker1.getWorkerPosition().canBuildInCells(islandBoard.getBoard());
                        do{
                            do{
                                System.out.println("Please insert the cell where you want to build");
                                x=in.nextInt();
                                y=in.nextInt();
                            }while(x<0 || x>4 || y<0 || y>4);
                        }while(!worker1.build(islandBoard.getBoard()[x][y]));
                        break;

            case 2:     worker2.getWorkerPosition().hasFreeCellClosed(islandBoard.getBoard());
                        do{
                            do {
                                System.out.println("Please insert the cell where you want to move your worker");
                                x = in.nextInt();
                                y = in.nextInt();
                            }while(x<0 || x>4 || y<0 || y>4);
                        }while(!worker2.move(islandBoard.getBoard()[x][y]));

                        worker2.getWorkerPosition().canBuildInCells(islandBoard.getBoard());
                        do{
                            do{
                                System.out.println("Please insert the cell where you want to build");
                                x=in.nextInt();
                                y=in.nextInt();
                            }while(x<0 || x>4 || y<0 || y>4);
                        }while(!worker2.build(islandBoard.getBoard()[x][y]));
                        break;

            default:    System.out.println("Invalid Input");
                        this.play(islandBoard);
        }

    }
}
