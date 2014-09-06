package com.miksinouf.chronowars.domain.board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sound.sampled.Line;

public class ShapeModels {
    public static final int MODEL_TOKEN = '*';
	public Set<ShapeModel> models;
	
	public ShapeModels(String path) throws BadModelFormatException{
		models = new HashSet<ShapeModel>();
		Integer length = 0;
		Integer height = 0;
		Integer lineNumber = 0;
		Integer modelScore = 0;
		Set<Position> model = null;
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String line : lines) {
			lineNumber++;
			if (isNumeric(line)) {
			    if (model != null) {
			    	models.add(new ShapeModel(length, height, modelScore, model));
			    	model = new HashSet<Position>();
			    	length = 0;
			    	height = 0;
			    }
			    else
			    	model = new HashSet<Position>();
			    modelScore = Integer.parseInt(line);
			}
			else{
				for (Integer i = 0; i < line.length(); i++) {
					if (line.charAt(i) == MODEL_TOKEN || line.charAt(i) == ' ') {
						if (line.charAt(i) == MODEL_TOKEN) {
							model.add(new Position(i, height));
						}
					}
					else
						throw new BadModelFormatException(lineNumber, line, i);
				}
				length = Math.max(length, line.length());
				height++;
			}
		}
	    if (model != null) {
	    	models.add(new ShapeModel(length, height, modelScore, model));
	    	model = new HashSet<Position>();
	    	length = 0;
	    	height = 0;
	    }
	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}
