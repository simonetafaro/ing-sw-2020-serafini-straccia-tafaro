package it.polimi.ingsw.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
//bord tornata uguale quell creata

    @Test
    void clearAllSpaceTest() {
        Board board=new Board();
        board.getPlayingBoard()[1][2].setFreeSpace(false);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        board.getPlayingBoard()[4][4].setFreeSpace(false);
        board.getPlayingBoard()[0][4].setFreeSpace(false);
        board.getPlayingBoard()[3][4].setFreeSpace(false);
        board.clearAll();
        assertTrue(  board.getPlayingBoard()[1][2].isFree());
        assertTrue(  board.getPlayingBoard()[0][0].isFree());
        assertTrue(  board.getPlayingBoard()[4][4].isFree());
        assertTrue(  board.getPlayingBoard()[0][4].isFree());
        assertTrue(  board.getPlayingBoard()[3][4].isFree());
    }

    //controllo che dopo la clear all i livelli siano a zero
    @Test
    void clearAllLevelTest() {
        Board board=new Board();
        board.getPlayingBoard()[1][2].setLevel(1);
        board.getPlayingBoard()[0][0].setLevel(2);
        board.getPlayingBoard()[4][4].setLevel(3);
        board.getPlayingBoard()[0][4].setLevel(4);
        board.getPlayingBoard()[3][4].setLevel(3);
        board.clearAll();
        assertEquals(  board.getPlayingBoard()[1][2].getLevel(),0);
        assertEquals(  board.getPlayingBoard()[0][0].getLevel(),0);
        assertEquals(  board.getPlayingBoard()[4][4].getLevel(),0);
        assertEquals(  board.getPlayingBoard()[0][4].getLevel(),0);
        assertEquals(  board.getPlayingBoard()[3][4].getLevel(),0);
    }

    @Test
    void canBuiltSpaceTest(){
        Board board=new Board();
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        board.getPlayingBoard()[0][1].setFreeSpace(false);
        board.getPlayingBoard()[0][2].setFreeSpace(false);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][2].setFreeSpace(false);
        board.getPlayingBoard()[2][0].setFreeSpace(false);
        board.getPlayingBoard()[2][1].setFreeSpace(false);
        board.getPlayingBoard()[2][2].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getPlayingBoard()[1][1].canBuildInCells(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void canBuildLevelTest(){
        Board board=new Board();
        board.getPlayingBoard()[0][0].setLevel(4);
        board.getPlayingBoard()[0][1].setLevel(4);
        board.getPlayingBoard()[0][2].setLevel(4);
        board.getPlayingBoard()[1][0].setLevel(4);
        board.getPlayingBoard()[1][2].setLevel(4);
        board.getPlayingBoard()[2][0].setLevel(4);
        board.getPlayingBoard()[2][1].setLevel(4);
        board.getPlayingBoard()[2][2].setLevel(4);
        board.getPlayingBoard()[1][1].setLevel(1);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getPlayingBoard()[1][1].canBuildInCells(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void canBuildMixTest(){
        Board board=new Board();
        board.getPlayingBoard()[0][0].setLevel(4);
        board.getPlayingBoard()[0][1].setFreeSpace(false);
        board.getPlayingBoard()[0][2].setLevel(4);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][2].setLevel(4);
        board.getPlayingBoard()[2][0].setFreeSpace(false);
        board.getPlayingBoard()[2][1].setLevel(4);
        board.getPlayingBoard()[2][2].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setLevel(4);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getPlayingBoard()[1][1].canBuildInCells(board.getPlayingBoard()));
        board.clearAll();
    }

    @Test
    void canBuildTrueLevelTest(){
        Board board=new Board();
        board.getPlayingBoard()[0][0].setLevel(2);
        board.getPlayingBoard()[0][1].setFreeSpace(false);
        board.getPlayingBoard()[0][2].setLevel(4);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][2].setLevel(4);
        board.getPlayingBoard()[2][0].setFreeSpace(false);
        board.getPlayingBoard()[2][1].setLevel(4);
        board.getPlayingBoard()[2][2].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setLevel(1);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getPlayingBoard()[1][1].canBuildInCells(board.getPlayingBoard()));
        board.clearAll();
    }

    @Test
    void canBuildLevelTrueSpaceTest(){
        Board board=new Board();
        board.getPlayingBoard()[0][0].setLevel(4);
        //board.getBoard()[0][1].setFreeSpace(false);
        board.getPlayingBoard()[0][2].setLevel(4);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][2].setLevel(4);
        board.getPlayingBoard()[2][0].setFreeSpace(false);
        board.getPlayingBoard()[2][1].setLevel(4);
        board.getPlayingBoard()[2][2].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setLevel(1);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getPlayingBoard()[1][1].canBuildInCells(board.getPlayingBoard()));
        board.clearAll();
    }

    @Test
    void canBuildLevelTrueMixTest(){
        Board board=new Board();
        board.getPlayingBoard()[0][0].setLevel(2);
        //board.getBoard()[0][1].setFreeSpace(false);
        board.getPlayingBoard()[0][2].setLevel(4);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][2].setLevel(4);
        board.getPlayingBoard()[2][0].setFreeSpace(false);
        board.getPlayingBoard()[2][1].setLevel(4);
        board.getPlayingBoard()[2][2].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setLevel(1);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getPlayingBoard()[1][1].canBuildInCells(board.getPlayingBoard()));
        board.clearAll();
    }

    @Test
    void canBuildLevelTestCorner(){
        Board board=new Board();
        board.getPlayingBoard()[0][1].setLevel(4);
        board.getPlayingBoard()[1][0].setLevel(4);
        board.getPlayingBoard()[1][1].setLevel(4);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getPlayingBoard()[0][0].canBuildInCells(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void canBuildSpaceTestCorner(){
        Board board=new Board();
        board.getPlayingBoard()[0][1].setFreeSpace(false);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getPlayingBoard()[0][0].canBuildInCells(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void canBuildMixTestCorner(){
        Board board=new Board();
        board.getPlayingBoard()[0][1].setLevel(4);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getPlayingBoard()[0][0].canBuildInCells(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void canBuildLevelTestCornerTrue(){
        Board board=new Board();
        board.getPlayingBoard()[0][1].setLevel(1);
        board.getPlayingBoard()[1][0].setLevel(3);
        board.getPlayingBoard()[1][1].setLevel(2);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getPlayingBoard()[0][0].canBuildInCells(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void canBuildSpaceTestCornerTrue(){
        Board board=new Board();
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getPlayingBoard()[0][0].canBuildInCells(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void canBuildMixTestCornerTrue(){
        Board board=new Board();
        board.getPlayingBoard()[0][1].setLevel(3);
        board.getPlayingBoard()[1][0].setLevel(1);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getPlayingBoard()[0][0].canBuildInCells(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void hasFreeCellSpaceTest(){
        Board board=new Board();
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        board.getPlayingBoard()[0][1].setFreeSpace(false);
        board.getPlayingBoard()[0][2].setFreeSpace(false);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][2].setFreeSpace(false);
        board.getPlayingBoard()[2][0].setFreeSpace(false);
        board.getPlayingBoard()[2][1].setFreeSpace(false);
        board.getPlayingBoard()[2][2].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getPlayingBoard()[1][1].hasFreeCellClosed(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void hasFreeLevelTest(){
        Board board=new Board();
        board.getPlayingBoard()[0][0].setLevel(3);
        board.getPlayingBoard()[0][1].setLevel(4);
        board.getPlayingBoard()[0][2].setLevel(4);
        board.getPlayingBoard()[1][0].setLevel(3);
        board.getPlayingBoard()[1][2].setLevel(4);
        board.getPlayingBoard()[2][0].setLevel(3);
        board.getPlayingBoard()[2][1].setLevel(4);
        board.getPlayingBoard()[2][2].setLevel(3);
        board.getPlayingBoard()[1][1].setLevel(1);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getPlayingBoard()[1][1].hasFreeCellClosed(board.getPlayingBoard()));
        board.clearAll();

    }

    //mix dei due casi sopra
    @Test
    void hasFreeLevelMixTest(){
        Board board=new Board();
        board.getPlayingBoard()[0][0].setLevel(3);
        board.getPlayingBoard()[0][1].setFreeSpace(false);
        board.getPlayingBoard()[0][2].setLevel(4);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][2].setLevel(4);
        board.getPlayingBoard()[2][0].setFreeSpace(false);
        board.getPlayingBoard()[2][1].setLevel(4);
        board.getPlayingBoard()[2][2].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setLevel(1);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getPlayingBoard()[1][1].hasFreeCellClosed(board.getPlayingBoard()));
        board.clearAll();
    }

    @Test
    void hasFreeLevelTrueLevelTest(){
        Board board=new Board();
        board.getPlayingBoard()[0][0].setLevel(2);
        board.getPlayingBoard()[0][1].setFreeSpace(false);
        board.getPlayingBoard()[0][2].setLevel(4);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][2].setLevel(4);
        board.getPlayingBoard()[2][0].setFreeSpace(false);
        board.getPlayingBoard()[2][1].setLevel(4);
        board.getPlayingBoard()[2][2].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setLevel(1);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getPlayingBoard()[1][1].hasFreeCellClosed(board.getPlayingBoard()));
        board.clearAll();
    }

    @Test
    void hasFreeLevelTrueSpaceTest(){
        Board board=new Board();
        board.getPlayingBoard()[0][0].setLevel(3);
        //board.getBoard()[0][1].setFreeSpace(false);
        board.getPlayingBoard()[0][2].setLevel(4);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][2].setLevel(4);
        board.getPlayingBoard()[2][0].setFreeSpace(false);
        board.getPlayingBoard()[2][1].setLevel(4);
        board.getPlayingBoard()[2][2].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setLevel(1);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getPlayingBoard()[1][1].hasFreeCellClosed(board.getPlayingBoard()));
        board.clearAll();
    }

    @Test
    void hasFreeLevelTrueMixTest(){
        Board board=new Board();
        board.getPlayingBoard()[0][0].setLevel(2);
        //board.getBoard()[0][1].setFreeSpace(false);
        board.getPlayingBoard()[0][2].setLevel(4);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][2].setLevel(4);
        board.getPlayingBoard()[2][0].setFreeSpace(false);
        board.getPlayingBoard()[2][1].setLevel(4);
        board.getPlayingBoard()[2][2].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setLevel(1);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getPlayingBoard()[1][1].hasFreeCellClosed(board.getPlayingBoard()));
        board.clearAll();
    }

    @Test
    void hasFreeLevelTestCorner(){
        Board board=new Board();
        board.getPlayingBoard()[0][1].setLevel(4);
        board.getPlayingBoard()[1][0].setLevel(3);
        board.getPlayingBoard()[1][1].setLevel(2);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getPlayingBoard()[0][0].hasFreeCellClosed(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void hasFreeSpaceTestCorner(){
        Board board=new Board();
        board.getPlayingBoard()[0][1].setFreeSpace(false);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getPlayingBoard()[0][0].hasFreeCellClosed(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void hasFreeMixTestCorner(){
        Board board=new Board();
        board.getPlayingBoard()[0][1].setLevel(3);
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getPlayingBoard()[0][0].hasFreeCellClosed(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void hasFreeLevelTestCornerTrue(){
        Board board=new Board();
        board.getPlayingBoard()[0][1].setLevel(1);
        board.getPlayingBoard()[1][0].setLevel(3);
        board.getPlayingBoard()[1][1].setLevel(2);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getPlayingBoard()[0][0].hasFreeCellClosed(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void hasFreeSpaceTestCornerTrue(){
        Board board=new Board();
        board.getPlayingBoard()[1][0].setFreeSpace(false);
        board.getPlayingBoard()[1][1].setFreeSpace(false);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getPlayingBoard()[0][0].hasFreeCellClosed(board.getPlayingBoard()));
        board.clearAll();

    }

    @Test
    void hasFreeMixTestCornerTrue(){
        Board board=new Board();
        board.getPlayingBoard()[0][1].setLevel(3);
        board.getPlayingBoard()[1][0].setLevel(1);
        board.getPlayingBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getPlayingBoard()[0][0].hasFreeCellClosed(board.getPlayingBoard()));
        board.clearAll();

    }
}