package com.rubengees.logic;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class Cell implements Cloneable {

    private boolean alive;
    private int x;
    private int y;

    public Cell(boolean alive, int x, int y) {
        this.alive = alive;
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
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
    public String toString() {
        return "Cell{" +
                "alive=" + alive +
                ", x=" + x +
                ", y=" + y +
                '}';
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
