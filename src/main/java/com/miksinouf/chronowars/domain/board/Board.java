package com.miksinouf.chronowars.domain.board;

import java.util.HashSet;
import java.util.Set;

import com.miksinouf.chronowars.domain.games.GamesQueueSingleton;
import com.miksinouf.chronowars.domain.player.Color;
import com.miksinouf.chronowars.domain.player.Tokens;
import com.miksinouf.chronowars.domain.player.TooManyTokensException;

public class Board {

    public static final int SIZE = 8;

    private final Tokens whiteTokens;
    private final Tokens blackTokens;
    
    private final Set<Shape> shapes;
    
    public Color colorToPlay;

    public Board() {
        this.whiteTokens = new Tokens(SIZE, GamesQueueSingleton.MAX_NUMBER_OF_TOKENS, Color.WHITE);
        this.blackTokens = new Tokens(SIZE, GamesQueueSingleton.MAX_NUMBER_OF_TOKENS, Color.BLACK);
        this.colorToPlay = Color.WHITE;
        this.shapes = new HashSet<Shape>();
    }

    public Integer size() {
        return SIZE;
    }
    
    public Set<Shape> getShapes() {
    	return this.shapes;
    }

    public void placeToken(int x, int y) throws IllegalMoveException, TooManyTokensException {
    	if (colorToPlay == Color.WHITE)
    		placeWhiteToken(x, y);
    	else
    		placeBlackToken(x, y);
    	findShapes();
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
    
    public void moveToken(int x, int y, Move move) throws IllegalMoveException {
    	if (colorToPlay == Color.WHITE)
    		moveWhiteToken(x, y, move);
    	else
    		moveBlackToken(x, y, move);
    	findShapes();
    	changePlayer();
    }
    
    public void changePlayer() {
    	this.colorToPlay = this.colorToPlay == Color.WHITE ? Color.BLACK : Color.WHITE;
    }
    
    private void moveWhiteToken(int x, int y, Move move) throws IllegalMoveException {
    	assertInBetween(x, y, move, blackTokens);
        whiteTokens.moveToken(x, y, move);
    }

    private void moveBlackToken(int x, int y, Move move) throws IllegalMoveException {
    	assertInBetween(x, y, move, whiteTokens);
        blackTokens.moveToken(x, y, move);
    }
    
    private void assertInBetween(int x, int y, Move move, Tokens opponnentTokens) throws IllegalMoveException {
    	if (move == Move.UP || move == Move.DOWN || move == Move.LEFT || move == Move.RIGHT)
    	{
	    	if (!(opponnentTokens.tokensPositions().contains(Position.getInBetweenPosition(x, y, move))))
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
    
    public void findShapes() {
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
    }
    
    public void findLowerI(Position token, Color c) {
    	Shape lowerI = new Shape(ShapeType.LOWER_I, colorToPlay);
    	Position t2;
    	Position t3;
    	if (c == Color.WHITE) {
        	if ((t3 = hasTokenAt(t2 = hasTokenAt(token, Move.UP, Color.BLACK), Move.UP, Color.WHITE)) != null) {
        		lowerI.tokens.add(token);
        		lowerI.tokens.add(t2);
        		lowerI.tokens.add(t3);
        		this.shapes.add(lowerI);
        	}
    	}
    	else {
        	if ((t3 = hasTokenAt(t2 = hasTokenAt(token, Move.UP, Color.WHITE), Move.UP, Color.BLACK)) != null) {
        		lowerI.tokens.add(token);
        		lowerI.tokens.add(t2);
        		lowerI.tokens.add(t3);
        		this.shapes.add(lowerI);
        	}
    	}
    }
    
    public void findUpperC(Position token, Color c) {
    	if (c == Color.WHITE) {
        	for(Position nextToken : blackTokens.tokensPositions()){
            } 		
    	}
    	else {
        	for(Position nextToken : whiteTokens.tokensPositions()){
            } 	  		
    	}	
    }
    
    public void findUpperI(Position token, Color c) {
    	if (c == Color.WHITE) {
        	for(Position nextToken : blackTokens.tokensPositions()){
            } 		
    	}
    	else {
        	for(Position nextToken : whiteTokens.tokensPositions()){
            } 	  		
    	} 	
    }
    
    public void findUpperO(Position token, Color c) {
    	if (c == Color.WHITE) {
        	for(Position nextToken : blackTokens.tokensPositions()){
            } 		
    	}
    	else {
        	for(Position nextToken : whiteTokens.tokensPositions()){
            } 	  		
    	} 	
    }
}
