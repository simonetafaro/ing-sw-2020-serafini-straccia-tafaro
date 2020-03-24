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
        int playerInGame = playerNumber;
        int i=0;
        while (!winner){
            while(i<playerNumber && playerOrder[i]!=null){
                System.out.println(playerOrder[i].getNickname()+"'s turn");
                playerOrder[i].play(this.islandBoard);
                if(playerOrder[i].getWorker1().winner() || playerOrder[i].getWorker2().winner()){
                    //playerOrder[i] wins
                    System.out.println(playerOrder[i].getNickname()+" wins!");
                    winner=true;
                    break;
                }
                if(playerOrder[i].isWorker1Stuck() && playerOrder[i].isWorker2Stuck()) {
                    if (playerInGame == 2) {
                        if(playerOrder[(i+1)%3]!=null)
                            System.out.println(playerOrder[(i+1)%3].getNickname()+" wins!");
                        else
                            System.out.println(playerOrder[(i+2)%3].getNickname()+" wins!");
                        winner = true;
                        break;
                    }
                    else{   //i player sono 3
                        playerOrder[i].getWorker1().clear();
                        playerOrder[i].getWorker2().clear();
                        playerOrder[i]=null;
                        playerInGame--;
                    }
                }
                islandBoard.printBoard();
                if(i==2)
                    break;
                i++;
            }
            if(i==playerNumber-1)
                i=0;
            else
                i++;
        }
        //Ha vinto qualcuno
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
