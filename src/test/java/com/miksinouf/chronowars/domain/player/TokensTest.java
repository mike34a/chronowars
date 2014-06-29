package com.miksinouf.chronowars.domain.player;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.miksinouf.chronowars.domain.board.Move;
import com.miksinouf.chronowars.domain.board.Position;

public class TokensTest {

    @Test
    public void should_add_token() throws Exception {
        //GIVEN
        final Tokens tokens = new Tokens(5, 2, Color.BLACK);

        //WHEN
        tokens.addToken(0,0);
        tokens.addToken(1,1);
    }

    @Test(expected=TooManyTokensException.class)
    public void should_throw_exception() throws Exception {
        //GIVEN
        final Tokens tokens = new Tokens(5, 2, Color.BLACK);

        //WHEN
        tokens.addToken(0,0);
        tokens.addToken(1,1);
        tokens.addToken(2,2);
    }

    @Test
    public void should_move_the_right_token() throws Exception {
        //GIVEN
        final Tokens tokens = new Tokens(5, 3, Color.BLACK);
        tokens.addToken(0,0);
        tokens.addToken(1,1);
        tokens.addToken(2,2);

        //WHEN
        tokens.moveToken(1,1, Move.DOWN);


        //THEN
        assertThat(tokens.tokensPositions()).containsOnly(new Position(0, 0), new Position(1, 3), new Position(2, 2));
    }
}
