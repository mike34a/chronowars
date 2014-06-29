package com.miksinouf.chronowars.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.miksinouf.chronowars.domain.token.IllegalMoveException;

public class BoardTest {
    private Board board = new Board();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void should_be_able_to_place_token_on_a_free_place() throws Exception {
        //No exception
        board.placeWhiteToken(1, 2);
    }

    @Test
    public void should_not_be_able_to_place_token_on_a_busy_place() throws Exception {
        //GIVEN
        board.placeWhiteToken(1, 2);

        //THEN
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("There is already a token at Position{x=1, y=2}");

        //WHEN
        board.placeWhiteToken(1, 2);
    }

    @Test
    public void should_not_be_able_to_place_token_out_of_board() throws Exception {
        //THEN
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("The position Position{x=10, y=2} is out of bounds");

        //WHEN
        board.placeWhiteToken(10, 2);
    }
}
