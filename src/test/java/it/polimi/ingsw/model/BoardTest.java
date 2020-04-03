package it.polimi.ingsw.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
//bord tornata uguale quell creata

    @Test
    void clearAllSpaceTest() {
        Board board=new Board();
        board.getBoard()[1][2].setFreeSpace(false);
        board.getBoard()[0][0].setFreeSpace(false);
        board.getBoard()[4][4].setFreeSpace(false);
        board.getBoard()[0][4].setFreeSpace(false);
        board.getBoard()[3][4].setFreeSpace(false);
        board.clearAll();
        assertTrue(  board.getBoard()[1][2].isFree());
        assertTrue(  board.getBoard()[0][0].isFree());
        assertTrue(  board.getBoard()[4][4].isFree());
        assertTrue(  board.getBoard()[0][4].isFree());
        assertTrue(  board.getBoard()[3][4].isFree());
    }
    //controllo che dopo la clear all i livelli siano a zero
    @Test
    void clearAllLevelTest() {
        Board board=new Board();
        board.getBoard()[1][2].setLevel(1);
        board.getBoard()[0][0].setLevel(2);
        board.getBoard()[4][4].setLevel(3);
        board.getBoard()[0][4].setLevel(4);
        board.getBoard()[3][4].setLevel(3);
        board.clearAll();
        assertEquals(  board.getBoard()[1][2].getLevel(),0);
        assertEquals(  board.getBoard()[0][0].getLevel(),0);
        assertEquals(  board.getBoard()[4][4].getLevel(),0);
        assertEquals(  board.getBoard()[0][4].getLevel(),0);
        assertEquals(  board.getBoard()[3][4].getLevel(),0);
    }

    @Test
    void canBuiltSpaceTest(){
        Board board=new Board();
        board.getBoard()[0][0].setFreeSpace(false);
        board.getBoard()[0][1].setFreeSpace(false);
        board.getBoard()[0][2].setFreeSpace(false);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][2].setFreeSpace(false);
        board.getBoard()[2][0].setFreeSpace(false);
        board.getBoard()[2][1].setFreeSpace(false);
        board.getBoard()[2][2].setFreeSpace(false);
        board.getBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getBoard()[1][1].canBuildInCells(board.getBoard()));
        board.clearAll();

    }

    @Test
    void canBuildLevelTest(){
        Board board=new Board();
        board.getBoard()[0][0].setLevel(4);
        board.getBoard()[0][1].setLevel(4);
        board.getBoard()[0][2].setLevel(4);
        board.getBoard()[1][0].setLevel(4);
        board.getBoard()[1][2].setLevel(4);
        board.getBoard()[2][0].setLevel(4);
        board.getBoard()[2][1].setLevel(4);
        board.getBoard()[2][2].setLevel(4);
        board.getBoard()[1][1].setLevel(1);
        board.getBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getBoard()[1][1].canBuildInCells(board.getBoard()));
        board.clearAll();

    }

    @Test
    void canBuildMixTest(){
        Board board=new Board();
        board.getBoard()[0][0].setLevel(4);
        board.getBoard()[0][1].setFreeSpace(false);
        board.getBoard()[0][2].setLevel(4);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][2].setLevel(4);
        board.getBoard()[2][0].setFreeSpace(false);
        board.getBoard()[2][1].setLevel(4);
        board.getBoard()[2][2].setFreeSpace(false);
        board.getBoard()[1][1].setLevel(4);
        board.getBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getBoard()[1][1].canBuildInCells(board.getBoard()));
        board.clearAll();
    }
    @Test
    void canBuildTrueLevelTest(){
        Board board=new Board();
        board.getBoard()[0][0].setLevel(2);
        board.getBoard()[0][1].setFreeSpace(false);
        board.getBoard()[0][2].setLevel(4);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][2].setLevel(4);
        board.getBoard()[2][0].setFreeSpace(false);
        board.getBoard()[2][1].setLevel(4);
        board.getBoard()[2][2].setFreeSpace(false);
        board.getBoard()[1][1].setLevel(1);
        board.getBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getBoard()[1][1].canBuildInCells(board.getBoard()));
        board.clearAll();
    }
    @Test
    void canbuildLevelTruespaceTest(){
        Board board=new Board();
        board.getBoard()[0][0].setLevel(4);
        //board.getBoard()[0][1].setFreeSpace(false);
        board.getBoard()[0][2].setLevel(4);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][2].setLevel(4);
        board.getBoard()[2][0].setFreeSpace(false);
        board.getBoard()[2][1].setLevel(4);
        board.getBoard()[2][2].setFreeSpace(false);
        board.getBoard()[1][1].setLevel(1);
        board.getBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getBoard()[1][1].canBuildInCells(board.getBoard()));
        board.clearAll();
    }
    @Test
    void canBuildLevelTrueMixlTest(){
        Board board=new Board();
        board.getBoard()[0][0].setLevel(2);
        //board.getBoard()[0][1].setFreeSpace(false);
        board.getBoard()[0][2].setLevel(4);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][2].setLevel(4);
        board.getBoard()[2][0].setFreeSpace(false);
        board.getBoard()[2][1].setLevel(4);
        board.getBoard()[2][2].setFreeSpace(false);
        board.getBoard()[1][1].setLevel(1);
        board.getBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getBoard()[1][1].canBuildInCells(board.getBoard()));
        board.clearAll();
    }
    @Test
    void canBuildLevelTestCorner(){
        Board board=new Board();
        board.getBoard()[0][1].setLevel(4);
        board.getBoard()[1][0].setLevel(4);
        board.getBoard()[1][1].setLevel(4);
        board.getBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getBoard()[0][0].canBuildInCells(board.getBoard()));
        board.clearAll();

    }
    @Test
    void canBuildSpaceTestCorner(){
        Board board=new Board();
        board.getBoard()[0][1].setFreeSpace(false);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][1].setFreeSpace(false);
        board.getBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getBoard()[0][0].canBuildInCells(board.getBoard()));
        board.clearAll();

    }
    @Test
    void canBuildMixTestCorner(){
        Board board=new Board();
        board.getBoard()[0][1].setLevel(4);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][1].setFreeSpace(false);
        board.getBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getBoard()[0][0].canBuildInCells(board.getBoard()));
        board.clearAll();

    }
    @Test
    void canBuildLevelTestCornerTrue(){
        Board board=new Board();
        board.getBoard()[0][1].setLevel(1);
        board.getBoard()[1][0].setLevel(3);
        board.getBoard()[1][1].setLevel(2);
        board.getBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getBoard()[0][0].canBuildInCells(board.getBoard()));
        board.clearAll();

    }
    @Test
    void canBuildSpaceTestCornerTrue(){
        Board board=new Board();
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][1].setFreeSpace(false);
        board.getBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getBoard()[0][0].canBuildInCells(board.getBoard()));
        board.clearAll();

    }
    @Test
    void canBuildMixTestCornerTrue(){
        Board board=new Board();
        board.getBoard()[0][1].setLevel(3);
        board.getBoard()[1][0].setLevel(1);
        board.getBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getBoard()[0][0].canBuildInCells(board.getBoard()));
        board.clearAll();

    }

    @Test
    void hasfreecellSpaceTest(){
        Board board=new Board();
        board.getBoard()[0][0].setFreeSpace(false);
        board.getBoard()[0][1].setFreeSpace(false);
        board.getBoard()[0][2].setFreeSpace(false);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][2].setFreeSpace(false);
        board.getBoard()[2][0].setFreeSpace(false);
        board.getBoard()[2][1].setFreeSpace(false);
        board.getBoard()[2][2].setFreeSpace(false);
        board.getBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getBoard()[1][1].hasFreeCellClosed(board.getBoard()));
        board.clearAll();

    }

    @Test
    void hasfreeLevelTest(){
        Board board=new Board();
        board.getBoard()[0][0].setLevel(3);
        board.getBoard()[0][1].setLevel(4);
        board.getBoard()[0][2].setLevel(4);
        board.getBoard()[1][0].setLevel(3);
        board.getBoard()[1][2].setLevel(4);
        board.getBoard()[2][0].setLevel(3);
        board.getBoard()[2][1].setLevel(4);
        board.getBoard()[2][2].setLevel(3);
        board.getBoard()[1][1].setLevel(1);
        board.getBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getBoard()[1][1].hasFreeCellClosed(board.getBoard()));
        board.clearAll();

    }
    //mix dei due casi sopra
    @Test
    void hasfreeLevelMixTest(){
        Board board=new Board();
        board.getBoard()[0][0].setLevel(3);
        board.getBoard()[0][1].setFreeSpace(false);
        board.getBoard()[0][2].setLevel(4);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][2].setLevel(4);
        board.getBoard()[2][0].setFreeSpace(false);
        board.getBoard()[2][1].setLevel(4);
        board.getBoard()[2][2].setFreeSpace(false);
        board.getBoard()[1][1].setLevel(1);
        board.getBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getBoard()[1][1].hasFreeCellClosed(board.getBoard()));
        board.clearAll();
    }
    @Test
    void hasfreeLevelTrueLevelTest(){
        Board board=new Board();
        board.getBoard()[0][0].setLevel(2);
        board.getBoard()[0][1].setFreeSpace(false);
        board.getBoard()[0][2].setLevel(4);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][2].setLevel(4);
        board.getBoard()[2][0].setFreeSpace(false);
        board.getBoard()[2][1].setLevel(4);
        board.getBoard()[2][2].setFreeSpace(false);
        board.getBoard()[1][1].setLevel(1);
        board.getBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getBoard()[1][1].hasFreeCellClosed(board.getBoard()));
        board.clearAll();
    }
    @Test
    void hasfreeLevelTruespaceTest(){
        Board board=new Board();
        board.getBoard()[0][0].setLevel(3);
        //board.getBoard()[0][1].setFreeSpace(false);
        board.getBoard()[0][2].setLevel(4);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][2].setLevel(4);
        board.getBoard()[2][0].setFreeSpace(false);
        board.getBoard()[2][1].setLevel(4);
        board.getBoard()[2][2].setFreeSpace(false);
        board.getBoard()[1][1].setLevel(1);
        board.getBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getBoard()[1][1].hasFreeCellClosed(board.getBoard()));
        board.clearAll();
    }
    @Test
    void hasfreeLevelTrueMixlTest(){
        Board board=new Board();
        board.getBoard()[0][0].setLevel(2);
        //board.getBoard()[0][1].setFreeSpace(false);
        board.getBoard()[0][2].setLevel(4);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][2].setLevel(4);
        board.getBoard()[2][0].setFreeSpace(false);
        board.getBoard()[2][1].setLevel(4);
        board.getBoard()[2][2].setFreeSpace(false);
        board.getBoard()[1][1].setLevel(1);
        board.getBoard()[1][1].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getBoard()[1][1].hasFreeCellClosed(board.getBoard()));
        board.clearAll();
    }
    @Test
    void hasfreeLevelTestCorner(){
        Board board=new Board();
        board.getBoard()[0][1].setLevel(4);
        board.getBoard()[1][0].setLevel(3);
        board.getBoard()[1][1].setLevel(2);
        board.getBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getBoard()[0][0].hasFreeCellClosed(board.getBoard()));
        board.clearAll();

    }
    @Test
    void hasfreeSpaceTestCorner(){
        Board board=new Board();
        board.getBoard()[0][1].setFreeSpace(false);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][1].setFreeSpace(false);
        board.getBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getBoard()[0][0].hasFreeCellClosed(board.getBoard()));
        board.clearAll();

    }
    @Test
    void hasfreeMixTestCorner(){
        Board board=new Board();
        board.getBoard()[0][1].setLevel(3);
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][1].setFreeSpace(false);
        board.getBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertFalse(board.getBoard()[0][0].hasFreeCellClosed(board.getBoard()));
        board.clearAll();

    }
    @Test
    void hasfreeLevelTestCornerTrue(){
        Board board=new Board();
        board.getBoard()[0][1].setLevel(1);
        board.getBoard()[1][0].setLevel(3);
        board.getBoard()[1][1].setLevel(2);
        board.getBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getBoard()[0][0].hasFreeCellClosed(board.getBoard()));
        board.clearAll();

    }
    @Test
    void hasfreeSpaceTestCornerTrue(){
        Board board=new Board();
        board.getBoard()[1][0].setFreeSpace(false);
        board.getBoard()[1][1].setFreeSpace(false);
        board.getBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getBoard()[0][0].hasFreeCellClosed(board.getBoard()));
        board.clearAll();

    }
    @Test
    void hasfreeMixTestCornerTrue(){
        Board board=new Board();
        board.getBoard()[0][1].setLevel(3);
        board.getBoard()[1][0].setLevel(1);
        board.getBoard()[0][0].setFreeSpace(false);
        //quella sopraè stata inserita sennno il metodo cerca anche la mia this
        assertTrue(board.getBoard()[0][0].hasFreeCellClosed(board.getBoard()));
        board.clearAll();

    }
}