package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerMain {

    public static void main( String[] args )
    {
        Server server;
        FileSystem fileSystem = FileSystems.getDefault();
        System.out.println(fileSystem.getSeparator());

        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }

}
