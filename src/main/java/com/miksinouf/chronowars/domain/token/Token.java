package com.miksinouf.chronowars.domain.token;

import com.miksinouf.chronowars.domain.Position;

public class Token {
    private Position position;
    private Integer boardSize;

    public Token(Position position, Integer boardSize) throws IllegalMoveException {
        this.position = position;
        this.boardSize = boardSize;
        assertPositionLegal(position.getX(), position.getY());
    }

    public void move(Move move) throws IllegalMoveException {
        final int newX = position.getX() + move.dx;
        final int newY = position.getY() + move.dy;

        assertPositionLegal(newX, newY);
        position = new Position(newX, newY);
    }

    public Position getPosition() {
        return position;
    }

    private void assertPositionLegal(int newX, int newY) throws IllegalMoveException {
        if (newX < 0 || newY < 0 || newX >= boardSize || newY >= boardSize) {
            throw new IllegalMoveException();
        }
    }

    public boolean hasPosition(Integer x, Integer y) {
        return position.equals(new Position(x, y));
    }
}
