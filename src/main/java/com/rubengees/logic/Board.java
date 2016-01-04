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
        Objects.requireNonNull(aliveMatrix);

        if (aliveMatrix.length < 3) {
            throw new IllegalArgumentException("The matrix must be at least 3 x 3");
        }

        cells = new Cell[aliveMatrix.length][aliveMatrix.length];

        for (int i = 0; i < aliveMatrix.length; i++) {
            for (int j = 0; j < aliveMatrix[0].length; j++) {
                cells[j][i] = new Cell(aliveMatrix[j][i], j, i);
            }
        }
    }

    public Cell[][] getCells() {
        //Return a copy of the internal matrix to achieve immutability
        return Utils.cloneMatrix(cells);
    }

    public void calculateCycle() {
        Cell[][] newMatrix = new Cell[cells.length][cells[0].length];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                Cell current = cells[j][i].clone();
                int aliveNeighbours = calculateAliveNeighbours(current);

                if(aliveNeighbours < 2){
                    current.setAlive(false);
                }else if(aliveNeighbours == 3){
                    current.setAlive(true);
                }else if(aliveNeighbours > 3){
                    current.setAlive(false);
                }

                newMatrix[j][i] = current;
            }
        }

        cells = newMatrix;
    }

    private int calculateAliveNeighbours(Cell cell) {
        int result = 0;

        for (Cell neighbour : findNeighbours(cell)) {
            if(neighbour.isAlive()){
                result++;
            }
        }

        return result;
    }

    private List<Cell> findNeighbours(Cell cell) {
        List<Cell> result = new ArrayList<>(8);

        for (int i = cell.getX() - 1; i <= cell.getX() + 1; i++) {
            for (int j = cell.getY() - 1; j <= cell.getY() + 1; j++) {
                //Our cell which is no neighbour
                if(i == cell.getX() && j == cell.getY()){
                    continue;
                }

                int x;
                int y;

                if(i < 0){
                    x = cells.length - 1;
                }else if(i >= cells.length){
                    x = 0;
                }else{
                    x = i;
                }

                if(j < 0){
                    y = cells.length - 1;
                }else if(j >= cells.length){
                    y = 0;
                }else{
                    y = j;
                }

                result.add(cells[x][y]);
            }
        }

        return result;
    }
}
