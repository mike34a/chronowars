package com.miksinouf.chronowars.domain.piece;

import com.miksinouf.chronowars.domain.Position;

public class Piece {
    private Position position;

    public Piece(Position position) {
        this.position = position;
    }

    public Position move(Move move) {
        return new Position(position.getX() + move.dx, position.getY() + move.dy);
    }
}
