package com.miksinouf.chronowars.domain.board;

public enum Move {
    UP(0, -2),
    UP_RIGHT(1, -1),
    RIGHT(2, 0),
    DOWN_RIGHT(1, 1),
    DOWN(0, 2),
    DOWN_LEFT(-1, 1),
    LEFT(-2, 0),
    UP_LEFT(-1, -1);
    
    public final int dx;
    public final int dy;

    Move(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
