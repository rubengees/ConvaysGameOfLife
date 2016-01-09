package com.rubengees.convaysgameoflife.logic;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class responsible for holding the cells and calculating each cycle.
 *
 * @author Ruben Gees
 */
public class Board {

    /**
     * The internal representation of the board.
     */
    private Cell[][] cells;

    /**
     * The constructor.
     *
     * @param aliveMatrix The initial matrix, representing the size of the board and the state of each cell.
     */
    public Board(boolean[][] aliveMatrix) {
        setAliveMatrix(aliveMatrix);
    }

    /**
     * Sets a new cell matrix, replacing the old one.
     *
     * @param aliveMatrix An array of booleans representing the state of the cells and the size of the board.
     * @throws IllegalArgumentException If the matrix is smaller than 3 x 3, which is required to make the application
     *                                  work properly.
     */
    public synchronized void setAliveMatrix(@NotNull boolean[][] aliveMatrix) throws IllegalArgumentException {
        Objects.requireNonNull(aliveMatrix);

        int rows = aliveMatrix.length;
        int columns = aliveMatrix[0].length;

        if (rows < 3 || columns < 3) {
            throw new IllegalArgumentException("The matrix must be at least 3 x 3");
        }

        cells = new Cell[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new Cell(aliveMatrix[i][j], i, j);
            }
        }
    }

    /**
     * Returns a copy of the internal cell matrix to be used when drawing.
     *
     * @return The matrix of the cells.
     */
    @NotNull
    public synchronized Cell[][] getCells() {
        //Return a copy of the internal matrix to achieve immutability
        return Utils.cloneMatrix(cells);
    }

    @NotNull
    public synchronized boolean[][] toAliveMatrix() {
        boolean[][] result = new boolean[cells.length][cells[0].length];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                result[i][j] = cells[i][j].isAlive();
            }
        }

        return result;
    }

    public synchronized void calculateCycle() {
        int rows = cells.length;
        int columns = cells[0].length;
        Cell[][] newMatrix = new Cell[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell current = cells[i][j].clone();
                int aliveNeighbours = calculateAliveNeighbours(current);

                if (aliveNeighbours < 2) {
                    current.setAlive(false);
                } else if (aliveNeighbours == 3) {
                    current.setAlive(true);
                } else if (aliveNeighbours > 3) {
                    current.setAlive(false);
                }

                newMatrix[i][j] = current;
            }
        }

        cells = newMatrix;
    }

    /**
     * Calculates the amount of living neighbours of a single cell. This also takes the borders of the board in account.
     *
     * @param cell The cell to calculate from.
     * @return The amount of alive neighbours.
     */
    private int calculateAliveNeighbours(Cell cell) {
        int result = 0;

        for (Cell neighbour : findNeighbours(cell)) {
            if (neighbour.isAlive()) {
                result++;
            }
        }

        return result;
    }

    /**
     * Returns the neighbours of a single cell, taking the borders of the board in account.
     *
     * @param cell The cell to find the neighbours from.
     * @return A List of the neighbours. The size is always 8.
     */
    private List<Cell> findNeighbours(@NotNull Cell cell) {
        Objects.requireNonNull(cell);

        List<Cell> result = new ArrayList<>(8);
        int rows = cells.length;
        int columns = cells[0].length;

        for (int i = cell.getX() - 1; i <= cell.getX() + 1; i++) {
            for (int j = cell.getY() - 1; j <= cell.getY() + 1; j++) {
                //Our cell which is no neighbour
                if (i == cell.getX() && j == cell.getY()) {
                    continue;
                }

                int x;
                int y;

                if (i < 0) {
                    x = rows - 1;
                } else if (i >= rows) {
                    x = 0;
                } else {
                    x = i;
                }

                if (j < 0) {
                    y = columns - 1;
                } else if (j >= columns) {
                    y = 0;
                } else {
                    y = j;
                }

                result.add(cells[x][y]);
            }
        }

        return result;
    }

    /**
     * Returns a copy of the cell at the given position.
     *
     * @param x The position on the x-axis.
     * @param y The position on the y-axis.
     * @return The cell.
     * @throws IllegalArgumentException If the x or y parameter is out of bounds,
     *                                  e.g. it's larger than the boards total size or lower than 0.
     */
    @NotNull
    public synchronized Cell getCell(int x, int y) throws IllegalArgumentException {
        if (x < 0 || y < 0 || x >= cells.length || y >= cells[0].length) {
            throw new IllegalArgumentException("The position is out of bounds");
        }

        //Clone the cell to grant immutability
        return cells[x][y].clone();
    }

    /**
     * Inverts the cell at the given position. Making a dead cell alive and a living cell dead.
     *
     * @param x The position on the x-axis.
     * @param y The position on the y-axis.
     * @throws IllegalArgumentException If the x or y parameter is out of bounds,
     *                                  e.g. it's larger than the boards total size or lower than 0.
     */
    public synchronized void invertCell(int x, int y) throws IllegalArgumentException {
        if (x < 0 || y < 0 || x >= cells.length || y >= cells[0].length) {
            throw new IllegalArgumentException("The position is out of bounds");
        }

        cells[x][y].setAlive(!cells[x][y].isAlive());
    }
}
