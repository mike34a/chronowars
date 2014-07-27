package com.miksinouf.chronowars.domain.board;

public enum MoveResultType {
    TOKEN_ALREADY_HERE,
    NOT_ON_PROPER_COLOR,
    TOKEN_OUT_OF_BOUNDS,
    NO_MORE_TOKENS_AVAILABLE,
    BAD_PLAYER,
    NO_TOKEN_INBETWEEN,
    NO_TOKEN_HERE, SUCCESS,
}
