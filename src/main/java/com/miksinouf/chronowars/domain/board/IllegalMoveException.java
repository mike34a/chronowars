package com.miksinouf.chronowars.domain.board;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal move.")
public class IllegalMoveException extends RuntimeException {

    private final static String MESSAGE_TEMPLATE =  "%s at Position{x=%d, y=%d}" ;

    public final MoveResult invalidMoveResult;

    public IllegalMoveException(MoveResultType moveResultType, int x, int y) {
        super(String.format(MESSAGE_TEMPLATE, moveResultType.toString(), x, y));
        this.invalidMoveResult = new MoveResult(moveResultType, x, y);
    }
}
