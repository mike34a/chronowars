package com.miksinouf.chronowars.domain.games;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.miksinouf.chronowars.domain.Game;

public class Games {
    private final Map<String, Game> games = new HashMap<>();

    public void addGame(Game game) {
        games.put(game.whitePlayer.identifier, game);
        games.put(game.blackPlayer.identifier, game);
    }

    public Optional<Game> getGame(String playerIdentifier) {
        return games.containsKey(playerIdentifier) ? Optional.of(games.get(playerIdentifier)) : Optional.empty();
    }

    public Optional<String> getGameIdentifier(String playerIdentifier) {
        return getGame(playerIdentifier).map(Game::getIdentifier);
    }

}
