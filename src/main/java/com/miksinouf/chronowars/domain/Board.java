package com.miksinouf.chronowars.domain;

import java.util.HashMap;
import java.util.Map;

import com.miksinouf.chronowars.domain.token.IllegalMoveException;
import com.miksinouf.chronowars.domain.token.Token;

public class Board {

    public static final int BOARD_SIZE = 8;

    private Map<Position, Token> whiteTokens = new HashMap<>();
    private Map<Position, Token> blackTokens = new HashMap<>();

    public Integer size() {
        return BOARD_SIZE;
    }

    public void placeWhiteToken(int x, int y) throws IllegalMoveException {
        final Position position = new Position(x, y);
        if (whiteTokens.containsKey(position)) {
            throw new IllegalMoveException(IllegalMoveException.TOKEN_ALREADY_HERE, position);
        }

        whiteTokens.put(position, new Token(position, BOARD_SIZE));
    }
}
