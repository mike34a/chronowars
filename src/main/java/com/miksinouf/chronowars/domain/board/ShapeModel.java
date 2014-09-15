package com.miksinouf.chronowars.domain.board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class ShapeModel {
	public Integer length;
	public Integer height;
	public Integer score;
	public Set<Position> tokens;
	
	public ShapeModel(Integer length, Integer height, Integer score, Set<Position> model) {
		this.length = length;
		this.height = height;
		this.score = score;
		this.tokens = model;
	}
	
	@Override
	public String toString() {
		String rslt = "Model : \n";
		for (int h = 0; h < this.height; h++) {
			for (int l = 0; l < this.length; l++) {
				rslt += tokens.contains(new Position(l, h)) ? "*" :  " ";
			}
			rslt += "\n";
		}
		rslt += "\n";
		rslt += "Score : ";
		rslt += this.score.toString();
		rslt += "\n";
		return (rslt);
	}
}
