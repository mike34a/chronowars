package com.miksinouf.chronowars.domain.board;

public class Position {
    private Integer x, y;

    public Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public static boolean isPairPosition(Position position) {
        return position.getX() + position.getY() % 2 == 0;
    }

    public static boolean isImpairPosition(Position position) {
        return position.getX() + position.getY() % 2 == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return x.equals(position.x) && y.equals(position.y);
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public boolean equals(Integer x, Integer y) {
        return this.x.equals(x) && this.y.equals(y);
    }

    public static void assertPositionLegal(int newX, int newY) throws IllegalMoveException {
        if (newX < 0 || newY < 0 || newX >= Board.SIZE || newY >= Board.SIZE) {
            throw new IllegalMoveException(IllegalMoveException.TOKEN_OUT_OF_BOUNDS, new Position(newX, newY).toString());
        }
    }

    public static PositionBuilder from(Position oldPosition) {
        return new PositionBuilder(oldPosition.getX(), oldPosition.getY());
    }
}
