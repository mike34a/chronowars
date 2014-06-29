package com.miksinouf.chronowars.domain.player;

import com.miksinouf.chronowars.domain.board.Position;

public enum Color {
    WHITE,
    BLACK;
    
    public boolean isPositionValid(Position position) {
        int expectedModulo2Value = this == WHITE ? 1 : 0;
        return (position.getX() + position.getY()) % 2 == expectedModulo2Value;
    }
}