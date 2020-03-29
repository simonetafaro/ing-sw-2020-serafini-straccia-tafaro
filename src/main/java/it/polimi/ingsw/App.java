package it.polimi.ingsw;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.CustomDate;
import it.polimi.ingsw.utils.PlayerColor;

import java.util.InputMismatchException;
import java.util.Scanner;


public class App 
{
    /**private static boolean grey=false, white=false, blue = false;

    public static void main( String[] args )
    {
        int numPlayer=0;
        int counter =0;
        Game myGame;
        Player player1= new Player();
        Player player2= new Player();
        Player player3= new Player();
        Scanner in = new Scanner(System.in);

        //Choose the number of players -> numPlayer
        do {
            try{
                //Scanner in = new Scanner(System.in);
                System.out.println("Choose the number of players?");
                numPlayer = in.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Ops, I would a number please ");
            }
        }while(numPlayer<2 || numPlayer>=4);
        System.out.println("You choose: "+ numPlayer+ " players.");

        //for each player set nickname, birthdate and choose color
        while(counter<numPlayer){
            switch (counter){
                case 0:
                            System.out.println("Insert nickname");
                            player1.setNickname(in.next());
                            setPlayerBirthdate(player1);
                            setPlayerColor(player1);
                            break;

                case 1:
                            System.out.println("Insert nickname");
                            player2.setNickname(in.next());
                            setPlayerBirthdate(player2);
                            setPlayerColor(player2);
                            break;

                case 2:
                            System.out.println("Insert nickname");
                            player3.setNickname(in.next());
                            setPlayerBirthdate(player3);
                            setPlayerColor(player3);
                            break;
            }
            counter++;
        }

        //create a Game instance with custom numPlayer
        myGame = new Game(numPlayer);
        //set Worker position for every players
        myGame.setPlayerWorkerinOrder(player1,player2,player3);
        //start logic of game
        myGame.Start();
        //end of game
        System.out.println("Game Over!");
    }


    public static boolean checkColorUnicity(PlayerColor color){
        switch (color){
            case BLUE:   if(!blue) {
                            blue = true;
                            return false;
                        }
                        else {
                            System.out.println("This color is been already choosen!");
                            return true;
                        }
            case WHITE: if(!white){
                            white=true;
                            return false;
                        }
                        else {
                            System.out.println("This color is been already choosen!");
                            return true;
                        }
            case GREY:  if(!grey){
                            grey=true;
                            return false;
                        }
                        else {
                            System.out.println("This color is been already choosen!");
                            return true;
                        }
            default:    System.out.println("This color doesn't exist!");
                        return true;
        }
    }

    static void setPlayerColor(Player currentPlayer){
        Scanner in = new Scanner(System.in);
        do{
            try {
                do{
                    System.out.println("Choose the color :GREY, WHITE and BLUE");
                    currentPlayer.setColor(PlayerColor.valueOf((in.next()).toUpperCase()));

                }while(checkColorUnicity(currentPlayer.getColor()));
                break;
            }
            catch (IllegalArgumentException e ){
                System.out.println("This color doesn't exist!");
            }
        }while(true);
    }

    static void setPlayerBirthdate(Player currPlayer){
        currPlayer.setBirthdate(new CustomDate());
        while(true){
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Insert your birth's day");
            currPlayer.getBirthdate().setDay(in.nextInt());
            System.out.println("Insert your birth's month");
            currPlayer.getBirthdate().setMonth(in.nextInt());
            System.out.println("Insert your birth's year");
            currPlayer.getBirthdate().setYear(in.nextInt());
            break;
        }
        catch (InputMismatchException e){
            System.out.println("Ops, I'would a number please ");
            }
        }
    }
     */
}
