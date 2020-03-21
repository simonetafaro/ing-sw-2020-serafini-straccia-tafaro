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


    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public void setBirthdate(CustomDate birthdate) {
        this.birthdate = birthdate;
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

    public void setWorkerPosition(){
        Cell chosenCell;
        int x,y;
        Scanner in = new Scanner(System.in);
        System.out.println("Scegli la cella in cui posizionare il tuo worker1");
        System.out.println("Inserisci la coordinata x");
        x= in.nextInt();
        System.out.println("Inserisci la coordinata y");
        y= in.nextInt();
        System.out.println("Hai scelto ( " +x +" , "+y+" )");
        chosenCell = new Cell(x,y);
        worker1=new Worker(chosenCell,1);

        System.out.println("Scegli la cella in cui posizionare il tuo worker2");
        System.out.println("Inserisci la coordinata x");
        x= in.nextInt();
        System.out.println("Inserisci la coordinata y");
        y= in.nextInt();
        System.out.println("Hai scelto ( " +x +" , "+y+" )");
        chosenCell = new Cell(x,y);
        worker2=new Worker(chosenCell,2);
    }
}
