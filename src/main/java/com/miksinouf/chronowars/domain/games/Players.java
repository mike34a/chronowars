package com.miksinouf.chronowars.domain.games;

import java.util.HashMap;
import java.util.Map;

import com.miksinouf.chronowars.domain.board.MoveResult;
import com.miksinouf.chronowars.domain.player.Player;
import com.miksinouf.chronowars.domain.player.UnknownPlayerException;

public class Players {
    private final Map<String, Player> players = new HashMap<>();

    public void addPlayers(Player whitePlayer, Player blackPlayer) {
        players.put(whitePlayer.identifier, whitePlayer);
        players.put(blackPlayer.identifier, blackPlayer);
        whitePlayer.setOpponent(blackPlayer);
        blackPlayer.setOpponent(whitePlayer);
    }

    public MoveResult setToken(String playerIdentifier, Integer x, Integer y) throws UnknownPlayerException {
        if (!players.containsKey(playerIdentifier)) {
            throw new UnknownPlayerException();
        }
        return players.get(playerIdentifier).set(x, y);
    }

    public Boolean hasPlayerAGame(String playerIdentifier) {
        return players.containsKey(playerIdentifier);
    }
}
