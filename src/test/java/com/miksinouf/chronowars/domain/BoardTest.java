package com.miksinouf.chronowars.domain;

import static com.miksinouf.chronowars.domain.board.Move.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.miksinouf.chronowars.domain.board.BadModelFormatException;
import com.miksinouf.chronowars.domain.board.Board;
import com.miksinouf.chronowars.domain.board.ShapeModels;
import com.miksinouf.chronowars.domain.board.IllegalMoveException;
import com.miksinouf.chronowars.domain.board.Position;

public class BoardTest {
    private Board board = new Board();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void should_move_down_left() throws IllegalMoveException {
        //GIVEN
        final Position givenPosition = new Position(1,2);

        //WHEN
        Position newPosition = Position.from(givenPosition).moving(DOWN_LEFT);

        //THEN
        assertThat(newPosition).isEqualTo(new Position(0, 3));
    }

    @Test
    public void should_be_able_to_place_white_token_on_a_free_place() throws Exception {
        //No exception
        board.placeWhiteToken(2, 1);
    }

    @Test
    public void should_not_be_able_to_place_white_token_on_a_busy_place() throws Exception {
        //GIVEN
        board.placeWhiteToken(2, 1);

        //THEN
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("TOKEN_ALREADY_HERE at Position{x=2, y=1}");

        //WHEN
        board.placeWhiteToken(2, 1);
    }

    @Test
    public void should_not_be_able_to_place_white_token_out_of_board() throws Exception {
        //THEN
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("TOKEN_OUT_OF_BOUNDS at Position{x=10, y=1}");

        //WHEN
        board.placeWhiteToken(10, 1);
    }

    @Test
    public void should_not_be_able_to_place_white_token_on_black_slot() throws Exception {
        //THEN
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("NOT_ON_PROPER_COLOR at Position{x=2, y=2}");

        //WHEN
        board.placeWhiteToken(2, 2);
    }

    @Test
    public void should_be_able_to_place_black_token_on_a_free_place() throws Exception {
        //No exception
        board.placeBlackToken(2, 2);
    }

    @Test
    public void should_not_be_able_to_place_black_token_on_a_busy_place() throws Exception {
        //GIVEN
        board.placeBlackToken(2, 2);

        //THEN
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("TOKEN_ALREADY_HERE at Position{x=2, y=2}");

        //WHEN
        board.placeBlackToken(2, 2);
    }

    @Test
    public void should_not_be_able_to_place_black_token_out_of_board() throws Exception {
        //THEN
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("TOKEN_OUT_OF_BOUNDS at Position{x=10, y=2}");

        //WHEN
        board.placeBlackToken(10, 2);
    }

    @Test
    public void should_not_be_able_to_place_black_token_on_white_slot() throws Exception {
        //THEN
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("NOT_ON_PROPER_COLOR at Position{x=1, y=2}");

        //WHEN
        board.placeBlackToken(1, 2);
    }

    @Test
    public void should_throw_exception_on_negative_position() throws Exception {
        //GIVEN
        final Position position = new Position(0,0);

        //THEN
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("TOKEN_OUT_OF_BOUNDS at Position{x=-1, y=-1}");

        //WHEN
        Position.from(position).moving(UP_LEFT);
    }

    @Test
    public void should_throw_exception_on_overmax_position() throws IllegalMoveException {
        //GIVEN
        final Position position = new Position(0,7);

        //THEN
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("TOKEN_OUT_OF_BOUNDS at Position{x=1, y=8}");

        //WHEN
        Position.from(position).moving(DOWN_RIGHT);
    }
    
}
