package com.miksinouf.chronowars.domain.board;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;
import com.miksinouf.chronowars.domain.games.GamesQueueSingleton;
import com.miksinouf.chronowars.domain.player.Color;
import com.miksinouf.chronowars.domain.player.Tokens;
import com.miksinouf.chronowars.domain.player.TooManyTokensException;

public class Board {

    public static final int SIZE = 8;

    @Expose private final Tokens whiteTokens;
    @Expose private final Tokens blackTokens;
    
    private final Set<Shape> shapes;
    @Expose public Shape maxShape;
    
    @Expose public Color colorToPlay;

    public Board() {
        this.whiteTokens = new Tokens(SIZE, GamesQueueSingleton.MAX_NUMBER_OF_TOKENS, Color.WHITE);
        this.blackTokens = new Tokens(SIZE, GamesQueueSingleton.MAX_NUMBER_OF_TOKENS, Color.BLACK);
        this.colorToPlay = Color.WHITE;
        this.shapes = new HashSet<Shape>();
        this.maxShape = new Shape(ShapeType.NONE, Color.WHITE);
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
    
    public MoveResult moveToken(int x, int y, Move move) throws IllegalMoveException {
    	MoveResult rslt;
    	if (colorToPlay == Color.WHITE)
    		rslt = moveWhiteToken(x, y, move);
    	else
    		rslt = moveBlackToken(x, y, move);
    	findShapes();
    	changePlayer();
    	return (rslt);
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
        this.shapes.clear();
        this.maxShape = new Shape(ShapeType.NONE, Color.WHITE);
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

		/* Example : * RIGHT * RIGHT */
		for (Move m : Move.values()) {
			if (m == Move.UP || m == Move.RIGHT) {
				if ((t3 = hasTokenAt(t2 = hasTokenAt(token, m, c.getOpponnentColor()), m, c)) != null) {
					lowerI.tokens.add(token);
					lowerI.tokens.add(t2);
					lowerI.tokens.add(t3);
					this.shapes.add(lowerI);
				} 				  
			}
    	}
    }
    
    public void findUpperC(Position token, Color c) {
    	Shape lowerC = new Shape(ShapeType.UPPER_C, colorToPlay);
    	Position t2;
    	Position t3;
    	Position t4;
    	Position t5;

		/* Example : * UP * RIGHT * RIGHT * DOWN */
		for (Move m : Move.values()) {
			if (m == Move.DOWN || m == Move.UP || m == Move.LEFT || m == Move.RIGHT) {
				if ((t5 = hasTokenAt(t4 = hasTokenAt(t3 = hasTokenAt(t2 = hasTokenAt(
						token, m, c.getOpponnentColor()), 
						m.getNextDirection(), c), 
						m.getNextDirection(), c.getOpponnentColor()), 
						m.getOppositeDirection(), c)) != null) {
					lowerC.tokens.add(token);
					lowerC.tokens.add(t2);
					lowerC.tokens.add(t3);
					lowerC.tokens.add(t4);
					lowerC.tokens.add(t5);
					this.shapes.add(lowerC); 					
				}
			}
		}
    }
    
    public void findUpperI(Position token, Color c) {
    	Shape upperI = new Shape(ShapeType.UPPER_I, colorToPlay);
    	Position t2;
    	Position t3;
    	Position t4;
    	Position t5;
    	Position t6;
    	Position t7;

		/* Example : (1)UP * UP * (1)RIGHT * RIGHT * (2)UP * (2) DOWN */
		for (Move m : Move.values()) {
			if (m == Move.DOWN || m == Move.UP || m == Move.LEFT || m == Move.RIGHT) {
				if ((t3 = hasTokenAt(t2 = hasTokenAt(token, m, c.getOpponnentColor()), m, c)) != null) {
					if ((t5 = hasTokenAt(t4 = hasTokenAt(t2, m.getNextDirection(), c), m.getNextDirection(), c.getOpponnentColor())) != null) {
						if ((t6 = hasTokenAt(t5, m, c)) != null && (t7 = hasTokenAt(t5, m.getOppositeDirection(), c)) != null) {
							upperI.tokens.add(token);
							upperI.tokens.add(t2);
							upperI.tokens.add(t3);
							upperI.tokens.add(t4);
							upperI.tokens.add(t5);
							upperI.tokens.add(t6);
							upperI.tokens.add(t7);
							this.shapes.add(upperI);
						}
					}		
				}
			}
		}
    }
    
    public void findUpperO(Position token, Color c) {
    	Shape upperO = new Shape(ShapeType.UPPER_O, colorToPlay);
    	Position t2;
    	Position t3;
    	Position t4;
    	Position t5;
    	Position t6;
    	Position t7;
    	Position t8;

		/* Example : * UP * UP * RIGHT * RIGHT * DOWN * DOWN * LEFT*/
		for (Move m : Move.values()) {
			if (m == Move.DOWN || m == Move.UP || m == Move.LEFT || m == Move.RIGHT) {
				if ((t8 = hasTokenAt(t7 = hasTokenAt(t6 = hasTokenAt((t5 = hasTokenAt(t4 = hasTokenAt(t3 = hasTokenAt(t2 = hasTokenAt(
						token, m, c.getOpponnentColor()), 
						m, c), 
						m.getNextDirection(), c.getOpponnentColor()), 
						m.getNextDirection(), c)),
						m.getOppositeDirection(), c.getOpponnentColor()),
						m.getOppositeDirection(), c),
						m.getPreviousDirection(), c.getOpponnentColor())) != null) {
					upperO.tokens.add(token);
					upperO.tokens.add(t2);
					upperO.tokens.add(t3);
					upperO.tokens.add(t4);
					upperO.tokens.add(t5);
					upperO.tokens.add(t6);
					upperO.tokens.add(t7);
					upperO.tokens.add(t8);
					this.shapes.add(upperO); 					
				}
			}
		}
    }
}
