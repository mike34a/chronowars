package com.miksinouf.chronowars.domain.board;

public enum ShapeType {
    LOWER_I(1),
    UPPER_C(3),
    UPPER_I(5),
    UPPER_O(8);
    
    public final int points;

    ShapeType(Integer points) {
        this.points = points;
    }
    
    public Integer getPoints() {
    	return points;
    }
}
