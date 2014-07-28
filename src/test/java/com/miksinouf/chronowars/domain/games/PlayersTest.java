package com.miksinouf.chronowars.domain.games;

import static com.miksinouf.chronowars.domain.board.MoveResultType.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.miksinouf.chronowars.domain.board.Board;
import com.miksinouf.chronowars.domain.board.IllegalMoveException;
import com.miksinouf.chronowars.domain.board.MoveResult;
import com.miksinouf.chronowars.domain.player.Color;
import com.miksinouf.chronowars.domain.player.Player;
import com.miksinouf.chronowars.domain.player.UnknownPlayerException;

public class PlayersTest {

    private Players players;
    private Player whitePlayer;
    private Player blackPlayer;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        players = new Players();
        final Board board = new Board();

        whitePlayer = new Player("whitePlayerNick", Color.WHITE, 2,
                "whitePlayerIdentifier", board);
        blackPlayer = new Player("blackPlayerNick", Color.WHITE, 2,
                "blackPlayerIdentifier", board);

        players.addPlayers(whitePlayer, blackPlayer);
    }

    @Test
    public void should_oppose_players_when_added_together() throws Exception {
        // Given
        whitePlayer = mock(Player.class);
        blackPlayer = mock(Player.class);

        // WHEN
        players.addPlayers(whitePlayer, blackPlayer);

        // THEN
        verify(whitePlayer).setOpponent(blackPlayer);
        verify(blackPlayer).setOpponent(whitePlayer);
    }

    @Test
    public void should_set_token_at_a_right_position() throws Exception {
        assertThat(players.setToken("whitePlayerIdentifier", 1, 2)).isEqualTo(
                new MoveResult(SUCCESS, 1, 2));
    }

    @Test
    public void should_throw_out_of_bounds_if_place_not_valid() throws Exception {
        expectedException.expect(IllegalMoveException.class);
        expectedException.expectMessage("TOKEN_OUT_OF_BOUNDS at Position{x=-1, y=2}");

        players.setToken("whitePlayerIdentifier", -1, 2);
    }

    @Test(expected = UnknownPlayerException.class)
    public void should_throw_error_if_player_does_not_exist() throws Exception {

        assertThat(players.setToken("invalidPlayerIdentifier", 1, 2))
                .isEqualTo(new MoveResult(SUCCESS, 1, 2));
    }
}
