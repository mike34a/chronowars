package com.miksinouf.chronowars.domain.token;

import com.miksinouf.chronowars.domain.Position;

public class IllegalMoveException extends Exception {

    public final static String TOKEN_ALREADY_HERE = "There is already a token at %s";
    public final static String NO_TOKEN_HERE = "There is no token at %s";
    public final static String TOKEN_OUT_OF_BOUNDS = "The position %s is out of bounds";


    public IllegalMoveException(String reason, Position position) {
        super(String.format(reason, position.toString()));
    }
}
