package com.miksinouf.chronowars.domain.board;

public class Position {
    public final Integer x, y;

    public Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public static boolean isPairPosition(Position position) {
        return position.x + position.y % 2 == 0;
    }

    public static boolean isImpairPosition(Position position) {
        return position.x + position.y % 2 == 1;
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
            throw new IllegalMoveException(MoveResultType.TOKEN_OUT_OF_BOUNDS, newX, newY);
        }
    }

    public static PositionBuilder from(Position oldPosition) {
        return new PositionBuilder(oldPosition.x, oldPosition.y);
    }
    
    public static Position getInBetweenPosition(int x, int y, Move move) {
    	switch (move) {
        case UP:  return new Position(x, y - 1);
        case DOWN:  return new Position(x, y + 1);
        case RIGHT:  return new Position(x + 1, y);
        case LEFT:  return new Position(x - 1, y);
        default: return null;
    	}
    }
}
