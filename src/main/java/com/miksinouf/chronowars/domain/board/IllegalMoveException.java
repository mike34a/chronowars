package com.miksinouf.chronowars.domain.board;

public class IllegalMoveException extends Exception {

    private final static String MESSAGE_TEMPLATE =  "%s at Position{x=%d, y=%d}" ;

    public final MoveResult invalidMoveResult;

    public IllegalMoveException(MoveResultType moveResultType, int x, int y) {
        super(String.format(MESSAGE_TEMPLATE, moveResultType.toString(), x, y));
        this.invalidMoveResult = new MoveResult(moveResultType, x, y);
    }
}
