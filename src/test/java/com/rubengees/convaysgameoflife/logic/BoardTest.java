package com.rubengees.convaysgameoflife.logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class BoardTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        boolean[][] aliveMatrix = new boolean[5][5];

        aliveMatrix[0][0] = false;
        aliveMatrix[0][1] = false;
        aliveMatrix[0][2] = false;
        aliveMatrix[0][3] = false;
        aliveMatrix[0][4] = false;
        aliveMatrix[1][0] = false;
        aliveMatrix[1][1] = false;
        aliveMatrix[1][2] = true;
        aliveMatrix[1][3] = false;
        aliveMatrix[1][4] = false;
        aliveMatrix[2][0] = false;
        aliveMatrix[2][1] = false;
        aliveMatrix[2][2] = true;
        aliveMatrix[2][3] = false;
        aliveMatrix[2][4] = false;
        aliveMatrix[3][0] = false;
        aliveMatrix[3][1] = false;
        aliveMatrix[3][2] = true;
        aliveMatrix[3][3] = false;
        aliveMatrix[3][4] = false;
        aliveMatrix[4][0] = false;
        aliveMatrix[4][1] = false;
        aliveMatrix[4][2] = false;
        aliveMatrix[4][3] = false;
        aliveMatrix[4][4] = false;

        this.board = new Board(aliveMatrix);
    }

    @Test
    public void testCalculateCycle() throws Exception {
        Cell[][] originally = board.getCells();

        board.calculateCycle();

        Cell[][] cells = board.getCells();
        Cell[][] expected = new Cell[5][5];

        expected[0][0] = new Cell(false, 0, 0);
        expected[0][1] = new Cell(false, 0, 1);
        expected[0][2] = new Cell(false, 0, 2);
        expected[0][3] = new Cell(false, 0, 3);
        expected[0][4] = new Cell(false, 0, 4);
        expected[1][0] = new Cell(false, 1, 0);
        expected[1][1] = new Cell(false, 1, 1);
        expected[1][2] = new Cell(false, 1, 2);
        expected[1][3] = new Cell(false, 1, 3);
        expected[1][4] = new Cell(false, 1, 4);
        expected[2][0] = new Cell(false, 2, 0);
        expected[2][1] = new Cell(true, 2, 1);
        expected[2][2] = new Cell(true, 2, 2);
        expected[2][3] = new Cell(true, 2, 3);
        expected[2][4] = new Cell(false, 2, 4);
        expected[3][0] = new Cell(false, 3, 0);
        expected[3][1] = new Cell(false, 3, 1);
        expected[3][2] = new Cell(false, 3, 2);
        expected[3][3] = new Cell(false, 3, 3);
        expected[3][4] = new Cell(false, 3, 4);
        expected[4][0] = new Cell(false, 4, 0);
        expected[4][1] = new Cell(false, 4, 1);
        expected[4][2] = new Cell(false, 4, 2);
        expected[4][3] = new Cell(false, 4, 3);
        expected[4][4] = new Cell(false, 4, 4);

        Assert.assertNotNull(cells);
        Assert.assertTrue(Arrays.deepEquals(cells, expected));

        board.calculateCycle();

        cells = board.getCells();

        Assert.assertNotNull(cells);
        Assert.assertTrue(Arrays.deepEquals(cells, originally));
    }

    @Test
    public void testGetCells() throws Exception {
        Cell[][] cells = board.getCells();
        Cell[][] expected = new Cell[5][5];

        expected[0][0] = new Cell(false, 0, 0);
        expected[0][1] = new Cell(false, 0, 1);
        expected[0][2] = new Cell(false, 0, 2);
        expected[0][3] = new Cell(false, 0, 3);
        expected[0][4] = new Cell(false, 0, 4);
        expected[1][0] = new Cell(false, 1, 0);
        expected[1][1] = new Cell(false, 1, 1);
        expected[1][2] = new Cell(true, 1, 2);
        expected[1][3] = new Cell(false, 1, 3);
        expected[1][4] = new Cell(false, 1, 4);
        expected[2][0] = new Cell(false, 2, 0);
        expected[2][1] = new Cell(false, 2, 1);
        expected[2][2] = new Cell(true, 2, 2);
        expected[2][3] = new Cell(false, 2, 3);
        expected[2][4] = new Cell(false, 2, 4);
        expected[3][0] = new Cell(false, 3, 0);
        expected[3][1] = new Cell(false, 3, 1);
        expected[3][2] = new Cell(true, 3, 2);
        expected[3][3] = new Cell(false, 3, 3);
        expected[3][4] = new Cell(false, 3, 4);
        expected[4][0] = new Cell(false, 4, 0);
        expected[4][1] = new Cell(false, 4, 1);
        expected[4][2] = new Cell(false, 4, 2);
        expected[4][3] = new Cell(false, 4, 3);
        expected[4][4] = new Cell(false, 4, 4);

        Assert.assertNotNull(cells);
        Assert.assertTrue(Arrays.deepEquals(cells, expected));
    }

    @Test
    public void testGetCellsNonSquare() throws Exception {
        boolean[][] aliveMatrix = new boolean[3][4];

        aliveMatrix[0][0] = true;
        aliveMatrix[0][1] = true;
        aliveMatrix[0][2] = false;
        aliveMatrix[0][3] = true;
        aliveMatrix[1][0] = true;
        aliveMatrix[1][1] = true;
        aliveMatrix[1][2] = false;
        aliveMatrix[1][3] = false;
        aliveMatrix[2][0] = false;
        aliveMatrix[2][1] = true;
        aliveMatrix[2][2] = false;
        aliveMatrix[2][3] = false;

        board = new Board(aliveMatrix);

        Cell[][] cells = board.getCells();
        Cell[][] expected = new Cell[3][4];

        expected[0][0] = new Cell(true, 0, 0);
        expected[0][1] = new Cell(true, 0, 1);
        expected[0][2] = new Cell(false, 0, 2);
        expected[0][3] = new Cell(true, 0, 3);
        expected[1][0] = new Cell(true, 1, 0);
        expected[1][1] = new Cell(true, 1, 1);
        expected[1][2] = new Cell(false, 1, 2);
        expected[1][3] = new Cell(false, 1, 3);
        expected[2][0] = new Cell(false, 2, 0);
        expected[2][1] = new Cell(true, 2, 1);
        expected[2][2] = new Cell(false, 2, 2);
        expected[2][3] = new Cell(false, 2, 3);


        Assert.assertNotNull(cells);
        Assert.assertTrue(Arrays.deepEquals(cells, expected));
    }
}