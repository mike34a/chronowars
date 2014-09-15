package com.miksinouf.chronowars.domain.board;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;
import com.miksinouf.chronowars.domain.games.GamesQueue;
import com.miksinouf.chronowars.domain.player.Color;
import com.miksinouf.chronowars.domain.player.Tokens;
import com.miksinouf.chronowars.domain.player.TooManyTokensException;

public class Board {

    public static final int SIZE = 8;

    @Expose private final Tokens whiteTokens;
    @Expose private final Tokens blackTokens;
    
    private ShapeModels models;
    @Expose public ShapeModel maxShape;
    
    @Expose public Color colorToPlay;

    public Board() {
        this.whiteTokens = new Tokens(SIZE, GamesQueue.MAX_NUMBER_OF_TOKENS, Color.WHITE);
        this.blackTokens = new Tokens(SIZE, GamesQueue.MAX_NUMBER_OF_TOKENS, Color.BLACK);
        this.colorToPlay = Color.WHITE;
        this.maxShape = new ShapeModel(0, 0, 0, new HashSet<Position>());
        try {
			this.models = new ShapeModels("models.txt");
		} catch (BadModelFormatException e) {
			this.models = null;
			e.printStackTrace();
		}
    }

    public Integer size() {
        return SIZE;
    }

    public void placeToken(int x, int y) throws IllegalMoveException, TooManyTokensException {
    	if (colorToPlay == Color.WHITE)
    		placeWhiteToken(x, y);
    	else
    		placeBlackToken(x, y);
    	findShapes(x, y);
    	changePlayer();
    }
    
    /*TODO : Change to private*/
    public void placeWhiteToken(int x, int y) throws IllegalMoveException, TooManyTokensException {
        whiteTokens.addToken(x, y);
    }

    /*TODO : Change to private*/
    public void placeBlackToken(int x, int y) throws IllegalMoveException, TooManyTokensException {
        blackTokens.addToken(x, y);
    }
    
    public MoveResult moveToken(int x, int y, Move move) throws IllegalMoveException {
    	MoveResult moveResult;
    	if (colorToPlay == Color.WHITE)
    		moveResult = moveWhiteToken(x, y, move);
    	else
    		moveResult = moveBlackToken(x, y, move);
    	findShapes(x + move.dx, y + move.dy);
    	changePlayer();
    	return (moveResult);
    }
    
    public void changePlayer() {
    	this.colorToPlay = this.colorToPlay == Color.WHITE ? Color.BLACK : Color.WHITE;
    }
    
    private MoveResult moveWhiteToken(int x, int y, Move move) throws IllegalMoveException {
    	assertInBetween(x, y, move, blackTokens);
        return (whiteTokens.moveToken(x, y, move));
    }

    private MoveResult moveBlackToken(int x, int y, Move move) throws IllegalMoveException {
    	assertInBetween(x, y, move, whiteTokens);
        return (blackTokens.moveToken(x, y, move));
    }
    
    private void assertInBetween(int x, int y, Move move, Tokens opponentTokens) throws IllegalMoveException {
    	if (move == Move.UP || move == Move.DOWN || move == Move.LEFT || move == Move.RIGHT)
    	{
	    	if (!(opponentTokens.tokensPositions().contains(Position.getInBetweenPosition(x, y, move))))
	    		throw new IllegalMoveException(MoveResultType.NO_TOKEN_INBETWEEN, x, y);
    	}   	
    }
    
    public Position hasTokenAt(Position token, Move d, Color c) {
    	if (token != null) {
	    	if (c == Color.BLACK) { 
		    	for(Position nextToken : blackTokens.tokensPositions()) {
		    		if (nextToken.isAt(token, d))
		    			return nextToken;
		    	}
		    	return null;
	    	}
	    	else {
		    	for(Position nextToken : whiteTokens.tokensPositions()) {
		    		if (nextToken.isAt(token, d))
		    			return nextToken;
		    	}
		    	return null;
	    	}
    	}
    	return null;
    }
    
    public void findShapes(Integer x, Integer y) {
        this.maxShape = new ShapeModel(0, 0, 0, new HashSet<Position>());
        for (ShapeModel shape : models.models) {
        	findShape(shape, x, y);
        }
        /*
    	for(Position token : whiteTokens.tokensPositions()){
        	findLowerI(token, Color.WHITE);
        	findUpperC(token, Color.WHITE);
        	findUpperI(token, Color.WHITE);
        	findUpperO(token, Color.WHITE);
        }
    	for(Position token : blackTokens.tokensPositions()){
        	findLowerI(token, Color.BLACK);
        	findUpperC(token, Color.BLACK);
        	findUpperI(token, Color.BLACK);
        	findUpperO(token, Color.BLACK);
        }
        */
    }
    
    public void findShape(ShapeModel model, Integer posX, Integer posY) {
    	ShapeModel shape;
    	if (model.score > this.maxShape.score) {
	    	for (Integer checkY = 0; checkY < model.height; checkY++) {
	    		for (Integer checkX = 0; checkX < model.length; checkX++) {
	    			shape = checkShape(model, posX, posY, checkX, checkY, "original");
	    			shape = shape != null ? shape : checkShape(model, posX, posY, checkX, checkY, "left");
	    			shape = shape != null ? shape : checkShape(model, posX, posY, checkX, checkY, "down");
	    			shape = shape != null ? shape : checkShape(model, posX, posY, checkX, checkY, "right");
	    			if (shape != null)
	    				this.maxShape = shape;
	    		}
	    	}
    	}
    }
    
    private ShapeModel checkShape(ShapeModel model, Integer posX, Integer posY, Integer checkX, Integer checkY, String rotation) {
    	Position actualPosition;
    	Boolean found = true;
    	ShapeModel shape = new ShapeModel(model.length, model.height, model.score, new HashSet<Position>());
		for (Position p : model.tokens) {
			switch (rotation) {
			case "original":
				actualPosition = new Position(posX - checkX + p.x , posY - checkY + p.y);
				break;
			case "down":
				actualPosition = new Position(posX + checkX - p.x , posY + checkY - p.y);
				break;
			case "left":
				actualPosition = new Position(posX - checkY + p.y , posY + checkX - p.x);
				break;
			case "right":
				actualPosition = new Position(posX + checkY - p.y, posY - checkX + p.x);
				break;
			default:
				actualPosition = new Position(posX, posY);	
			}
			shape.tokens.add(actualPosition);
			if (!whiteTokens.tokensPositions().contains(actualPosition) && !blackTokens.tokensPositions().contains(actualPosition))
				found = false;
		}
		if (found && shape.tokens.contains(new Position(posX, posY)))
			return shape;
		else
			return null;
    }
}