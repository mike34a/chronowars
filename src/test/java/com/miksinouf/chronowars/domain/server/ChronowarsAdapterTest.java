package com.miksinouf.chronowars.domain.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.miksinouf.chronowars.domain.board.Board;
import com.miksinouf.chronowars.domain.games.GameResponse;

public class ChronowarsAdapterTest {

    @Test
    public void should_adapt_content_from_game_response() throws Exception {
        // Given
        final ChronowarsAdapter chronowarsAdapter = new ChronowarsAdapter();

        // When
        final String serializedGame = chronowarsAdapter.adaptGame(new GameResponse(
                new Board(),
                "running",
                "0",
                "0",
                "whiteNick",
                "blackNick"
        ));

        // Then
        assertThat(serializedGame)
                .isEqualTo("{\"board\":{\"whiteTokens\":{\"color\":\"WHITE\",\"tokensPositions\":[]," +
                        "\"boardSize\":8,\"numberOfTokens\":5},\"blackTokens\":{\"color\":\"BLACK\"," +
                        "\"tokensPositions\":[],\"boardSize\":8,\"numberOfTokens\":5},\"shapes\":[]," +
                        "\"maxShape\":{\"shape\":\"NONE\",\"tokens\":[],\"playerColor\":\"WHITE\"}," +
                        "\"colorToPlay\":\"WHITE\"},\"status\":\"running\",\"whiteScore\":\"0\",\"blackScore\":\"0\"," +
                        "\"whiteNick\":\"whiteNick\",\"blackNick\":\"blackNick\"}");
    }
}
