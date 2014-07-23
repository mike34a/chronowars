package com.miksinouf.chronowars.domain.board;

import com.miksinouf.chronowars.domain.games.GamesQueueSingleton;
import com.miksinouf.chronowars.domain.player.Color;
import com.miksinouf.chronowars.domain.player.Tokens;
import com.miksinouf.chronowars.domain.player.TooManyTokensException;

public class Board {

    public static final int SIZE = 8;

    private final Tokens whiteTokens;
    private final Tokens blackTokens;

    public Board() {
        this.whiteTokens = new Tokens(SIZE, GamesQueueSingleton.MAX_NUMBER_OF_TOKENS, Color.WHITE);
        this.blackTokens = new Tokens(SIZE, GamesQueueSingleton.MAX_NUMBER_OF_TOKENS, Color.BLACK);
    }

    public Integer size() {
        return SIZE;
    }

    public void placeWhiteToken(int x, int y) throws IllegalMoveException, TooManyTokensException {
        whiteTokens.addToken(x, y);
    }

    public void placeBlackToken(int x, int y) throws IllegalMoveException, TooManyTokensException {
        blackTokens.addToken(x, y);
    }
}
