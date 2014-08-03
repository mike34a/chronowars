package com.miksinouf.chronowars.domain.player;

import com.miksinouf.chronowars.domain.board.Position;

public enum Color {
    WHITE,
    BLACK;
    
    public boolean isPositionValid(Position position) {
        int expectedModulo2Value = this == WHITE ? 1 : 0;
        return (position.x + position.y) % 2 == expectedModulo2Value;
    }
    
    public Color getOpponentColor() {
    	return this == WHITE ? BLACK : WHITE;
    }
}
