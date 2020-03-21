package it.polimi.ingsw;

import java.util.Date;

public class Player {

    private PlayerColor color;
    private Date birthdate;
    private String nickname;
    private  boolean win ;
    //private Worker worker1;
    //private Worker worker2;
    //private Card myCard;


    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public void setBirthdate(Date birthdate) {
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

    /* public Player(PlayerColor color, Date birthdate, String nickname, Cell cell1, Cell cell2) {
        this.color = color;
        this.birthdate = birthdate;
        this.nickname = nickname;
        this.win =false;
        this.worker1 = new Worker(cell1,1);
        this.worker2 = new Worker(cell2,2);
        //Aggiungere paramentro myCard al costruttore
        //this.myCard= myCard;
    }*/


}
