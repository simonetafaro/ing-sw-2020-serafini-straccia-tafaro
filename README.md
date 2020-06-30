# Santorini - Progetto Ingegneria del software 2019/2020
This is the repository for the java implementation of the board game Santorini developed by Simone Tafaro, Agnese Straccia and Cristiano Serafini.

## Project Info
### Folders structure:
  - src/main: contains the java source code.
  - src/test: application unit tests using junit.
  - resources/images: contains all the graphic resources used in this project.
  - resources/toolcard: contains xml file that contains all the information about the cards used in the game.


## How to run this project
### Main class
  - scr/ServerMain: this is the main used for the server machine.
  - scr/ClientGUIMain: used for the graphic interface of the game.
  - scr/ClientCLIMain: used for the command line interface of the game.
  
### JAR
  - GC52-ServerMain.jar
  - GC52-ClientGUIMain.jar
  - GC52-ClientCLIMain.jar
  
### Steps
  1. Start Server laucher
  
    - Run "java -jar GC52-ServerMain.jar", it will start automatically and wait for incoming Socket connections.
  
  2. Start Client Interface
    
    - Command Line Interface (CLI)
      Run "java -jar GC52-ClientCLIMain.jar"
    
    - Graphical User Interface
      Run "java -jar GC52-ClientGUIMain.jar"
    
   Remember to set the correct value of the server ip in the first step of both versions.
   Each player during setup step selects a number (2 or 3) and he will be admitted in a waiting room. Following players will be automatically placed in the waiting room based on the number they pressed.
   When two people join the "2 players version" a new game will be created. (Same for the "3 Player version")
   When a game starts, Server will continue to accept new incoming connection from other players and they will be put in another waiting room, and a new game will start with the same rules as before.
    
  3. ***ENJOY IT!***
  
## Developed features
  - [x] Complete rules
  - [x] CLI
  - [x] GUI
  - [x] Socket
  - [x] Advanced functionality
      1. Multiple games
      2. Advanced Gods

## Test coverage
  ![CodeCoverageImage](/Deliverables/CodeCoverage.png)
