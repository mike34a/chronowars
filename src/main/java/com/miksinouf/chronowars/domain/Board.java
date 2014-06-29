package com.miksinouf.chronowars.domain;

import java.util.Map;

import com.miksinouf.chronowars.domain.token.Token;

public class Board {

    private final int BOARD_SIZE = 8;

    private Map<Position, Token> whiteTokens;

    private Map<Position, Token> blackTokens;

    public Integer size() {
        return BOARD_SIZE;
    }
}
