package com.miksinouf.chronowars.domain.administration;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.miksinouf.chronowars.domain.games.GamesQueueSingleton;
import com.miksinouf.chronowars.domain.player.Player;

public class GamesAdminitrator implements GamesAdminitratorMBean {
    
    @Override
    public Set<String> listPlayers() {
        return new HashSet<>(GamesQueueSingleton.INSTANCE.gamesQueue.players.players.keySet());
    }

    @Override
    public void killGame(String playerId) {
        final Map<String, Player> players = GamesQueueSingleton.INSTANCE.gamesQueue.players.players;
        final Player player = players.get(playerId);
        final Player opponent = player.getOpponent();

        players.remove(playerId);
        players.remove(opponent.getIdentifier());
    }

    @Override
    public void clearAllGames() {
        GamesQueueSingleton.INSTANCE.gamesQueue.players.players.clear();
    }
}
