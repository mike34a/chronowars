package com.miksinouf.chronowars.domain.games;

import static org.mockito.Mockito.*;

import org.junit.Test;

import com.miksinouf.chronowars.domain.player.Player;

public class GamesQueueTest {

    private GamesQueue gamesQueue;

    @Test
    public void should_not_add_players_when_two_ones_have_registered() throws Exception {
        // Given
        final Players players = mock(Players.class);

        gamesQueue = new GamesQueue();
        gamesQueue.setPlayers(players);


        // When
        gamesQueue.register("robert");

        // Then
        verify(players, never()).addPlayers(any(Player.class), any(Player.class));
    }

    @Test
    public void should_add_players_when_two_ones_have_registered() throws Exception {
        // Given
        final Players players = mock(Players.class);

        gamesQueue = new GamesQueue();
        gamesQueue.setPlayers(players);


        // When
        gamesQueue.register("robert");
        gamesQueue.register("pascal");

        // Then
        verify(players, times(1)).addPlayers(any(Player.class), any(Player.class));
    }

    @Test
    public void should_not_add_players_when_the_third_player_have_registered() throws Exception {
        // Given
        final Players players = mock(Players.class);

        gamesQueue = new GamesQueue();
        gamesQueue.setPlayers(players);
        gamesQueue.register("robert");
        gamesQueue.register("pascal");


        // When
        gamesQueue.register("georges");

        // Then
        verify(players, times(1)).addPlayers(any(Player.class), any(Player.class));
    }

    @Test
    public void should_add_players_when_the_fourth_player_have_registered() throws Exception {
        // Given
        final Players players = mock(Players.class);

        gamesQueue = new GamesQueue();
        gamesQueue.setPlayers(players);
        gamesQueue.register("robert");
        gamesQueue.register("pascal");


        // When
        gamesQueue.register("georges");
        gamesQueue.register("yvette");

        // Then
        verify(players, times(2)).addPlayers(any(Player.class), any(Player.class));
    }
}
