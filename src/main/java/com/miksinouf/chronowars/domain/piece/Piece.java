package com.miksinouf.chronowars.domain.piece;

import com.miksinouf.chronowars.domain.Position;

public class Piece {
    private Position position;
    private Integer boardSize;

    public Piece(Position position, Integer boardSize) {
        this.position = position;
        this.boardSize = boardSize;
    }

    public Position move(Move move) throws InvalidMoveException {
        final int newX = position.getX() + move.dx;
        final int newY = position.getY() + move.dy;

        if (newX < 0 || newY < 0 || newX >= boardSize || newY >= boardSize) {
            throw new InvalidMoveException();
        }
        return new Position(newX, newY);
    }
}
