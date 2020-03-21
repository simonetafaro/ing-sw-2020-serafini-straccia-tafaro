package it.polimi.ingsw;

public class Game {

    private int playerNumber =0;
    private Board islandBoard;
    private boolean winner;

    public Game(int playerNumber) {
        this.playerNumber = playerNumber;
        this.islandBoard = new Board();
        this.winner = false;
    }

    public Board getIslandBoard() {
        return islandBoard;
    }


    public void Start(){

    }

    public void getPlayerOrder(Player player1, Player player2, Player player3){

        int compare;
        //trovare l'ordine di gioco tra i player
        compare = player1.getBirthdate().compareDate(player2.getBirthdate());
        if(compare==1){
            //player1 è più giovane
            if(player3.getBirthdate()!=null){
                compare= player1.getBirthdate().compareDate(player3.getBirthdate());
                if(compare==1){
                    //il piu giovane è player1
                    player1.setWorkerPosition();
                    player2.setWorkerPosition();
                    player3.setWorkerPosition();
                }
                if(compare==-1 || compare ==0){
                    //il più giovane è player 3
                    player3.setWorkerPosition();
                    player1.setWorkerPosition();
                    player2.setWorkerPosition();
                }
            }else{
                player1.setWorkerPosition();
                player2.setWorkerPosition();
            }
        }
        else{
            //player 2 è più giovane
            if(player3.getBirthdate()!=null){
                compare= player2.getBirthdate().compareDate(player3.getBirthdate());
                if(compare==1){
                    //il piu giovane è player2
                    player2.setWorkerPosition();
                    player3.setWorkerPosition();
                    player1.setWorkerPosition();
                }
                if(compare==-1 || compare ==0){
                    //il più giovane è player 3
                    player3.setWorkerPosition();
                    player1.setWorkerPosition();
                    player2.setWorkerPosition();
                }
            }
            else{
                player2.setWorkerPosition();
                player1.setWorkerPosition();
            }
        }

    }
}
