package com.miksinouf.chronowars.domain.board;

import java.util.HashSet;
import java.util.Set;

import com.miksinouf.chronowars.domain.player.Color;

public class Shape {
	private ShapeType shape;
	public Set<Position> tokens;
	public Color playerColor;
	
	public Shape(ShapeType shape, Color c) {
		this.shape = shape;
		this.tokens = new HashSet<Position>();
		this.playerColor = c;
	}
	
	public Integer getScore() {
		return this.shape.points;
	}
	
	public Set<Position> getTokens() {
		return this.tokens;
	}
	
}
