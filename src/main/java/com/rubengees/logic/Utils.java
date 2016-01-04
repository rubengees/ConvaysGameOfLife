package com.rubengees.logic;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class Utils {

    public static Cell[][] cloneMatrix(Cell[][] src) {
        Cell[][] result = new Cell[src.length][src[0].length];

        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src[0].length; j++) {
                result[i][j] = src[i][j].clone();
            }
        }

        return result;
    }

}
