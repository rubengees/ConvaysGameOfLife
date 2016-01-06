package com.rubengees.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class Board {

    private Cell[][] cells;

    public Board(boolean[][] aliveMatrix) {
        setAliveMatrix(aliveMatrix);
    }

    public synchronized void setAliveMatrix(boolean[][] aliveMatrix) {
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

    public synchronized Cell[][] getCells() {
        //Return a copy of the internal matrix to achieve immutability
        return Utils.cloneMatrix(cells);
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

    private int calculateAliveNeighbours(Cell cell) {
        int result = 0;

        for (Cell neighbour : findNeighbours(cell)) {
            if (neighbour.isAlive()) {
                result++;
            }
        }

        return result;
    }

    private List<Cell> findNeighbours(Cell cell) {
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

    public synchronized Cell getCell(int x, int y) {
        return cells[x][y].clone();
    }

    public synchronized void invertCell(int x, int y) {
        cells[x][y].setAlive(!cells[x][y].isAlive());
    }
}
