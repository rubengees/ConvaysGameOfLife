package com.rubengees.convaysgameoflife.logic;

/**
 * Class, holding the information of a single cell.
 *
 * @author Ruben Gees
 */
public class Cell implements Cloneable {

    private boolean alive;
    private int x;
    private int y;

    /**
     * The constructor.
     *
     * @param alive True if the cell si alive, false otherwise
     * @param x     The position on the x-axis.
     * @param y     The position on the y-axis.
     */
    public Cell(boolean alive, int x, int y) {
        this.alive = alive;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns if the cell is alive.
     *
     * @return True, if the cell is alive, false otherwise.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Returns if the cell is alive.
     *
     * @param alive True, if the cell is alive, false otherwise.
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Returns the position on the x-axis.
     *
     * @return The position.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the position on the y-axis.
     *
     * @return The position.
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (alive != cell.alive) return false;
        if (x != cell.x) return false;
        return y == cell.y;

    }

    @Override
    public int hashCode() {
        int result = (alive ? 1 : 0);
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public Cell clone() {
        try {
            return (Cell) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
}
