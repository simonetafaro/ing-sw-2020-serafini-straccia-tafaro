package it.polimi.ingsw;



import java.security.InvalidParameterException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    private static boolean grey=false, white=false, blue = false;

    public static void main( String[] args )
    {

        int numPlayer;
        int counter =0;
        Game myGame;
        Board myBoard;


        //parametri player1
        Player player1, player2, player3;

        System.out.println("Inserisci numero giocatori");

            //numPlayer = System.in.read();
        Scanner in = new Scanner(System.in);
        numPlayer = in.nextInt();
        System.out.print("Hai scelto: ");
        System.out.print(numPlayer);
        System.out.println(" giocatori.");

        while(counter<numPlayer){
            switch (counter){
                case 0:     player1= new Player();
                            System.out.println("Inserisci il nickname");
                            player1.setNickname(in.next());
                            //trovare getter per Date
                            //player1.setBirthdate(in.nextD);
                            setPlayerColor(player1);
                            break;

                case 1:     player2= new Player();
                            System.out.println("Inserisci il nickname");
                            player2.setNickname(in.next());
                            //trovare getter per Date
                            //player1.setBirthdate(in.nextD);
                            setPlayerColor(player2);
                            break;

                case 2:     player3= new Player();
                            System.out.println("Inserisci il nickname");
                            player3.setNickname(in.next());
                            //trovare getter per Date
                            //player1.setBirthdate(in.nextD);
                            setPlayerColor(player3);
                            break;
            }
            counter++;
        }
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

}
