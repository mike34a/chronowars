package com.miksinouf.chronowars.domain.games;

import static com.miksinouf.chronowars.domain.board.MoveResultType.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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

        whitePlayer = new Player("whitePlayerNick", Color.WHITE,
                "whitePlayerIdentifier", board);
        blackPlayer = new Player("blackPlayerNick", Color.WHITE,
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

    @Test
    public void should_fill_serialized_game_with_proper_fields() throws Exception {

        // Given
        final Board board = new Board();
        final String whiteIdentifier = "whiteIdentifier";
        final String whiteNick = "whiteNick";
        final Player whitePlayer = mock(Player.class);
        when(whitePlayer.getBoard()).thenReturn(board);
        when(whitePlayer.getIdentifier()).thenReturn(whiteIdentifier);
        when(whitePlayer.getNickname()).thenReturn(whiteNick);
        when(whitePlayer.getScore()).thenReturn(10);
        when(whitePlayer.getColor()).thenReturn(Color.WHITE);

        final String blackIdentifier = "blackIdentifier";
        final String blackNick = "blackNick";
        final Player blackPlayer = mock(Player.class);
        when(blackPlayer.getBoard()).thenReturn(board);
        when(blackPlayer.getIdentifier()).thenReturn(blackIdentifier);
        when(blackPlayer.getNickname()).thenReturn(blackNick);
        when(blackPlayer.getScore()).thenReturn(20);
        when(blackPlayer.getColor()).thenReturn(Color.BLACK);

        when(whitePlayer.getOpponent()).thenReturn(blackPlayer);
        when(blackPlayer.getOpponent()).thenReturn(whitePlayer);

        players.addPlayers(whitePlayer, blackPlayer);

        // When
        final GameResponse playersGame = players.getGame("whiteIdentifier");

        // Then
        assertThat(playersGame.board).isEqualTo(board);
        assertThat(playersGame.status).isEqualTo("running");
        assertThat(playersGame.whiteScore).isEqualTo("10");
        assertThat(playersGame.blackScore).isEqualTo("20");
        assertThat(playersGame.whiteNick).isEqualTo(whiteNick);
        assertThat(playersGame.blackNick).isEqualTo(blackNick);
    }
    
    @Test
    public void should_set_game_to_running_when_score_is_below_maximum() throws Exception {

        // Given
        final Board board = new Board();
        final String whiteIdentifier = "whiteIdentifier";
        final Player whitePlayer = mock(Player.class);
        when(whitePlayer.getIdentifier()).thenReturn(whiteIdentifier);
        when(whitePlayer.getScore()).thenReturn(10);

        final String blackIdentifier = "blackIdentifier";
        final Player blackPlayer = mock(Player.class);
        when(blackPlayer.getScore()).thenReturn(20);

        when(whitePlayer.getOpponent()).thenReturn(blackPlayer);
        when(blackPlayer.getOpponent()).thenReturn(whitePlayer);

        players.addPlayers(whitePlayer, blackPlayer);

        // When
        final GameResponse playersGame = players.getGame("whiteIdentifier");

        // Then
        assertThat(playersGame.status).isEqualTo("running");
    }

    @Test
    public void should_set_game_to_finished_when_score_is_over_maximum() throws Exception {

        // Given
        final Board board = new Board();
        final String whiteIdentifier = "whiteIdentifier";
        final Player whitePlayer = mock(Player.class);
        when(whitePlayer.getIdentifier()).thenReturn(whiteIdentifier);
        when(whitePlayer.getScore()).thenReturn(10);

        final String blackIdentifier = "blackIdentifier";
        final Player blackPlayer = mock(Player.class);
        when(blackPlayer.getScore()).thenReturn(40);

        when(whitePlayer.getOpponent()).thenReturn(blackPlayer);
        when(blackPlayer.getOpponent()).thenReturn(whitePlayer);

        players.addPlayers(whitePlayer, blackPlayer);

        // When
        final GameResponse playersGame = players.getGame("whiteIdentifier");

        // Then
        assertThat(playersGame.status).isEqualTo("finished");
        when(whitePlayer.getScore()).thenReturn(40);
        when(blackPlayer.getScore()).thenReturn(10);
        assertThat(playersGame.status).isEqualTo("finished");
    }
}