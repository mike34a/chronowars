package com.miksinouf.chronowars.domain.board;

public class PositionBuilder {

    private final int x, y;

    public PositionBuilder(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position moving(Move move) throws IllegalMoveException {
        final int newX = x + move.dx;
        final int newY = y + move.dy;

        Position.assertPositionLegal(newX, newY);
        return new Position(newX, newY);
    }

}
