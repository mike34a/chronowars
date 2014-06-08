package com.miksinouf.chronowars.domain.piece;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.miksinouf.chronowars.domain.Position;

public class PieceTest {

    @Test
    public void should_move_down_left() throws InvalidMoveException {
        //GIVEN
        final Piece piece = new Piece(new Position(1,2), 8);

        //WHEN
        final Position position = piece.move(Move.DOWN_LEFT);

        //THEN
        assertThat(position).isEqualTo(new Position(0, 3));
    }

    @Test(expected=InvalidMoveException.class)
    public void should_throw_exception_on_negative_position() throws InvalidMoveException {
        //GIVEN
        final Piece piece = new Piece(new Position(0,0), 8);

        //WHEN
        piece.move(Move.UP_LEFT);
    }

    @Test(expected=InvalidMoveException.class)
    public void should_throw_exception_on_overmax_position() throws InvalidMoveException {
        //GIVEN
        final Piece piece = new Piece(new Position(0,7), 8);

        //WHEN
        piece.move(Move.DOWN_RIGHT);
    }
}
