package it.polimi.ingsw;

import java.util.InputMismatchException;
import java.util.Scanner;


public class App 
{
    private static boolean grey=false, white=false, blue = false;
    private static CustomDate birthday = new CustomDate();

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
                System.out.println("Inserisci numero giocatori");
                numPlayer = in.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("ops I'would a number please ");
            }
        }while(numPlayer<2 || numPlayer>=4);
        System.out.print("Hai scelto: ");
        System.out.print(numPlayer);
        System.out.println(" giocatori.");


        Scanner in = new Scanner(System.in);
        while(counter<numPlayer){
            switch (counter){
                case 0:
                            System.out.println("Inserisci il nickname");
                            player1.setNickname(in.next());
                            setPlayerBirthdate(player1);
                            setPlayerColor(player1);
                            break;

                case 1:
                            System.out.println("Inserisci il nickname");
                            player2.setNickname(in.next());
                            setPlayerBirthdate(player2);
                            setPlayerColor(player2);
                            break;

                case 2:
                            System.out.println("Inserisci il nickname");
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
    }


    private static boolean checkColorUnicity(PlayerColor color){
        switch (color){
            case BLUE:   if(!blue) {
                            blue = true;
                            return false;
                        }
                        else {
                            System.out.println("Il colore è già stato scelto");
                            return true;
                        }
            case WHITE: if(!white){
                            white=true;
                            return false;
                        }
                        else {
                            System.out.println("Il colore è già stato scelto");
                            return true;
                        }
            case GREY:  if(!grey){
                            grey=true;
                            return false;
                        }
                        else {
                            System.out.println("Il colore è già stato scelto");
                            return true;
                        }
            default:    System.out.println("Non esiste");
                        return true;
        }
    }

    static void setPlayerColor(Player currentPlayer){
        Scanner in = new Scanner(System.in);
        do{
            try {
                do{
                    System.out.println("Scegli colore tra GREY, WHITE and BLUE");
                    currentPlayer.setColor(PlayerColor.valueOf((in.next()).toUpperCase()));

                }while(checkColorUnicity(currentPlayer.getColor()));
                break;
            }
            catch (IllegalArgumentException e ){
                System.out.println("Questo colore non esiste");
            }
        }while(true);
    }

    static void setPlayerBirthdate(Player currPlayer){
        currPlayer.setBirthdate();
        while(true){
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Inserisci il giorno di nascita");
            currPlayer.getBirthdate().setDay(in.nextInt());
            System.out.println("Inserisci il mese di nascita");
            currPlayer.getBirthdate().setMonth(in.nextInt());
            System.out.println("Inserisci l'anno di nascita");
            currPlayer.getBirthdate().setYear(in.nextInt());
            break;
        }
        catch (InputMismatchException e){
            System.out.println("ops I'would a number please ");
            }
        }
    }
}
