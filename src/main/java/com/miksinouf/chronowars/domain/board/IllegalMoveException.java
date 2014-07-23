package com.miksinouf.chronowars.domain.board;

public class IllegalMoveException extends Exception {

    private final static String MESSAGE_TEMPLATE =  "%s at Position{x=%d, y=%d}" ;

    public final MoveResultType moveResultType;

    public final Position invalidPosition;

    public IllegalMoveException(MoveResultType moveResultType, int x, int y) {
        super(String.format(MESSAGE_TEMPLATE, moveResultType.toString(), x, y));
        this.moveResultType = moveResultType;
        this.invalidPosition = new Position(x, y);
    }

}
