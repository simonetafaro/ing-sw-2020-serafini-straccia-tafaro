package it.polimi.ingsw;

import java.util.InputMismatchException;
import java.util.Scanner;


public class App 
{
    private static boolean grey=false, white=false, blue = false;

    public static void main( String[] args )
    {
        int numPlayer=0;
        int counter =0;
        Game myGame;
        Player player1= new Player();
        Player player2= new Player();
        Player player3= new Player();

        do {
            try{
                Scanner in = new Scanner(System.in);
                System.out.println("How many players?");
                numPlayer = in.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Ops, I would a number please ");
            }
        }while(numPlayer<2 || numPlayer>=4);
        System.out.print("You choose: ");
        System.out.print(numPlayer);
        System.out.println(" players.");


        Scanner in = new Scanner(System.in);
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


        myGame = new Game(numPlayer);
        myGame.setPlayerWorkerinOrder(player1,player2,player3);

        myGame.Start();
        System.out.println("Game Over!");
    }


    private static boolean checkColorUnicity(PlayerColor color){
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
        currPlayer.setBirthdate();
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
}
