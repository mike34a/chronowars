package com.miksinouf.chronowars.domain.token;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.miksinouf.chronowars.domain.Position;

public class TokenTest {

    @Test
    public void should_move_down_left() throws IllegalMoveException {
        //GIVEN
        final Token token = new Token(new Position(1,2), 8);

        //WHEN
        token.move(Move.DOWN_LEFT);

        //THEN
        assertThat(token.getPosition()).isEqualTo(new Position(0, 3));
    }

    @Test(expected=IllegalMoveException.class)
    public void should_throw_exception_on_negative_position() throws IllegalMoveException {
        //GIVEN
        final Token token = new Token(new Position(0,0), 8);

        //WHEN
        token.move(Move.UP_LEFT);
    }

    @Test(expected=IllegalMoveException.class)
    public void should_throw_exception_on_overmax_position() throws IllegalMoveException {
        //GIVEN
        final Token token = new Token(new Position(0,7), 8);

        //WHEN
        token.move(Move.DOWN_RIGHT);
    }
}
