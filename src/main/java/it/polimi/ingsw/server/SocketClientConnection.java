package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observ.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketClientConnection extends Observable<String> implements Runnable {

    private ServerSocket serverSocket;
    private Server activeServer;
    private ServerSocket gameSocket;

    public SocketClientConnection(int port, Server server, int portGame) {
        this.activeServer = server;
        try {
            serverSocket = new ServerSocket(port);
            gameSocket = new ServerSocket(portGame);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Waiting a connection...");
                Socket client = serverSocket.accept();
                System.out.println("Connection accepted");
                ObjectInputStream input = new ObjectInputStream(
                        client.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(
                        client.getOutputStream());
                Integer clientId = (Integer) input.readObject();
                if (clientId == 0) {
                    clientId = Server.getClientId();
                    output.writeObject(clientId);
                    output.flush();
                    activeServer.getActiveClientConnection().put(clientId, client);
                    Server.increaseClientId();

                    // read player name and confirm
                    String playerName = (String) input.readObject();
                    activeServer.getPlayerData().put(clientId, playerName);
                    System.out.println("Name accepted: " + playerName);
                    output.writeObject("NAME ACCEPTED");
                    output.flush();

                    Player player = new Player(clientId, playerName);
                    int playerNumber = (int) input.readObject();
                    switch (playerNumber) {
                        case 2:
                            activeServer.getTwoPlayerMatch().add(player);
                            output.writeObject(new String("VALID NUMBER"));
                            output.flush();

                            if (activeServer.getTwoPlayerMatch().size() == 2) {
                                //create two players match
                                activeServer.createTwoPlayersMatch(activeServer.getTwoPlayerMatch().get(0), activeServer.getTwoPlayerMatch().get(1));
                                activeServer.getTwoPlayerMatch().clear();
                            }
                            break;
                        case 3:
                            activeServer.getThreePlayerMatch().add(player);
                            output.writeObject(new String("VALID NUMBER:" + playerNumber));
                            output.flush();
                            if (activeServer.getThreePlayerMatch().size() == 3) {
                                activeServer.createThreePlayersMatch(activeServer.getThreePlayerMatch().get(0), activeServer.getThreePlayerMatch().get(1), activeServer.getThreePlayerMatch().get(2));
                                activeServer.getThreePlayerMatch().clear();
                            }
                            break;
                    }

                    activeServer.getClientConnectionOutput().put(clientId, output);
                    activeServer.getClientConnectionInput().put(clientId, input);

                } else {

                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
