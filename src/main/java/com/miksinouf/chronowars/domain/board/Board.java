package com.miksinouf.chronowars.domain.board;

import com.miksinouf.chronowars.domain.games.GamesQueueSingleton;
import com.miksinouf.chronowars.domain.player.Color;
import com.miksinouf.chronowars.domain.player.Tokens;
import com.miksinouf.chronowars.domain.player.TooManyTokensException;

public class Board {

    public static final int SIZE = 8;

    private final Tokens whiteTokens;
    private final Tokens blackTokens;
    public Color colorToPlay;

    public Board() {
        this.whiteTokens = new Tokens(SIZE, GamesQueueSingleton.MAX_NUMBER_OF_TOKENS, Color.WHITE);
        this.blackTokens = new Tokens(SIZE, GamesQueueSingleton.MAX_NUMBER_OF_TOKENS, Color.BLACK);
        this.colorToPlay = Color.WHITE;
    }

    public Integer size() {
        return SIZE;
    }

    public void placeToken(int x, int y) throws IllegalMoveException, TooManyTokensException {
    	if (colorToPlay == Color.WHITE)
    		placeWhiteToken(x, y);
    	else
    		placeBlackToken(x, y);
    }
    
    /*TODO : Change to private*/
    public void placeWhiteToken(int x, int y) throws IllegalMoveException, TooManyTokensException {
        whiteTokens.addToken(x, y);
        this.colorToPlay = Color.BLACK;
    }

    /*TODO : Change to private*/
    public void placeBlackToken(int x, int y) throws IllegalMoveException, TooManyTokensException {
        blackTokens.addToken(x, y);
        this.colorToPlay = Color.WHITE;
    }
    
    public void moveToken(int x, int y, Move move) throws IllegalMoveException {
    	if (colorToPlay == Color.WHITE)
    		moveWhiteToken(x, y, move);
    	else
    		moveBlackToken(x, y, move);
    }
    
    private void moveWhiteToken(int x, int y, Move move) throws IllegalMoveException {
    	assertInBetween(x, y, move, blackTokens);
        whiteTokens.moveToken(x, y, move);
        this.colorToPlay = Color.BLACK;
    }

    private void moveBlackToken(int x, int y, Move move) throws IllegalMoveException {
    	assertInBetween(x, y, move, whiteTokens);
        blackTokens.moveToken(x, y, move);
        this.colorToPlay = Color.WHITE;
    }
    
    private void assertInBetween(int x, int y, Move move, Tokens opponnentTokens) throws IllegalMoveException {
    	if (move == Move.UP || move == Move.DOWN || move == Move.LEFT || move == Move.RIGHT)
    	{
	    	if (!(opponnentTokens.tokensPositions().contains(Position.getInBetweenPosition(x, y, move))))
	    		throw new IllegalMoveException(MoveResultType.NO_TOKEN_INBETWEEN, x, y);
    	}   	
    }
}
