package com.miksinouf.chronowars.domain.board;

public class IllegalMoveException extends Exception {

    public final static String TOKEN_ALREADY_HERE = "There is already a token at %s";
    public final static String NO_TOKEN_HERE = "There is no token at %s";
    public final static String TOKEN_OUT_OF_BOUNDS = "The position %s is out of bounds";
    public static final String ILLEGAL_POSITION_FOR_PLAYER = "Illegal position %s for %s player";


    public IllegalMoveException(String reason, String... reasons) {
        super(String.format(reason, reasons));
    }
}
