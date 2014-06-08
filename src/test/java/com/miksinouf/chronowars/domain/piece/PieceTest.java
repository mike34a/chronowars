package com.miksinouf.chronowars.domain.piece;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.miksinouf.chronowars.domain.Position;

public class PieceTest {

    @Test
    public void should_move_down_left() {
        //GIVEN
        final Piece piece = new Piece(new Position(1,2));

        //WHEN
        final Position position = piece.move(Move.DOWN_LEFT);

        //THEN
        assertThat(position).isEqualTo(new Position(0, 3));
    }


}
