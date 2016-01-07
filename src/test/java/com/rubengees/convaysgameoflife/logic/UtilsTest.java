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
public class UtilsTest {

    Cell[][] matrix;

    @Before
    public void setUp() throws Exception {
        matrix = new Cell[2][2];

        matrix[0][0] = new Cell(true, 0, 0);
        matrix[0][1] = new Cell(true, 0, 1);
        matrix[1][0] = new Cell(false, 1, 0);
        matrix[1][1] = new Cell(true, 1, 1);
    }

    @Test
    public void testCloneMatrix() throws Exception {
        Assert.assertTrue(Arrays.deepEquals(matrix, Utils.cloneMatrix(matrix)));
    }
}