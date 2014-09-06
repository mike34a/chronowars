package com.miksinouf.chronowars.domain.board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;
import com.miksinouf.chronowars.domain.player.Color;

public class Shape {
	@Expose private ShapeType shape;
	@Expose public Set<Position> tokens;
	@Expose public Color playerColor;
	
	public Shape(ShapeType shape, Color c) {
		this.shape = shape;
		this.tokens = new HashSet<>();
		this.playerColor = c;
	}
	
	public Integer getScore() {
		return this.shape.points;
	}
	
	public Set<Position> getTokens() {
		return this.tokens;
	}
	
}
