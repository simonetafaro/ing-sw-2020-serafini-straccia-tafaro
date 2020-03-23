package it.polimi.ingsw;

public class Game {

    private int playerNumber;
    private Board islandBoard;
    private boolean winner;
    private Player[] playerOrder;

    public Game(int playerNumber) {
        this.playerNumber = playerNumber;
        this.islandBoard = new Board();
        this.winner = false;
        playerOrder= new Player[3];
    }

    public Board getIslandBoard() {
        return islandBoard;
    }


    public void Start(){

        int i=0;
        while (!winner){
            while(i<playerNumber){
                System.out.println("E' il turno di "+playerOrder[i].getNickname());
                playerOrder[i].play(this.islandBoard);
                islandBoard.printBoard();
                i++;
            }
            i=0;
        }


    }

    public void setPlayerWorkerinOrder(Player player1, Player player2, Player player3){

        int compare;
        //trovare l'ordine di gioco tra i player
        compare = player1.getBirthdate().compareDate(player2.getBirthdate());
        if(compare==1){
            //player1 è più giovane
            if(player3.getBirthdate()!=null){
                compare= player1.getBirthdate().compareDate(player3.getBirthdate());
                if(compare==1){
                    //il piu giovane è player1
                    player1.setWorkerPosition(islandBoard);
                    player2.setWorkerPosition(islandBoard);
                    player3.setWorkerPosition(islandBoard);
                    playerOrder[0]=player1;
                    playerOrder[1]=player2;
                    playerOrder[2]=player3;
                }
                if(compare==-1 || compare ==0){
                    //il più giovane è player 3
                    player3.setWorkerPosition(islandBoard);
                    player1.setWorkerPosition(islandBoard);
                    player2.setWorkerPosition(islandBoard);
                    playerOrder[0]=player3;
                    playerOrder[1]=player1;
                    playerOrder[2]=player2;
                }
            }else{
                player1.setWorkerPosition(islandBoard);
                player2.setWorkerPosition(islandBoard);
                playerOrder[0]=player1;
                playerOrder[1]=player2;
            }
        }
        else{
            //player 2 è più giovane
            if(player3.getBirthdate()!=null){
                compare= player2.getBirthdate().compareDate(player3.getBirthdate());
                if(compare==1){
                    //il piu giovane è player2
                    player2.setWorkerPosition(islandBoard);
                    player3.setWorkerPosition(islandBoard);
                    player1.setWorkerPosition(islandBoard);
                    playerOrder[0]=player2;
                    playerOrder[1]=player3;
                    playerOrder[2]=player1;
                }
                if(compare==-1 || compare ==0){
                    //il più giovane è player 3
                    player3.setWorkerPosition(islandBoard);
                    player1.setWorkerPosition(islandBoard);
                    player2.setWorkerPosition(islandBoard);
                    playerOrder[0]=player3;
                    playerOrder[1]=player1;
                    playerOrder[2]=player2;
                }
            }
            else{
                player2.setWorkerPosition(islandBoard);
                player1.setWorkerPosition(islandBoard);
                playerOrder[0]=player2;
                playerOrder[1]=player1;
            }
        }
        islandBoard.printBoard();
    }
}
