package com.miksinouf.chronowars.domain.games;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;

import com.miksinouf.chronowars.domain.Game;
import com.miksinouf.chronowars.domain.player.Player;
import com.miksinouf.chronowars.domain.player.WaitingPlayer;

public enum GameQueueSingleton {
    INSTANCE;
    private final static int BOARD_SIZE = 9;

    private final PlayersQueue playersQueue = new PlayersQueue();
    private final Games games = new Games();
    private final SecureRandom secureRandom = new SecureRandom();

    public synchronized Optional<String> runNextGame() {
        if (playersQueue.size() < 2) {
            return Optional.empty();
        }

        final Player whitePlayer = playersQueue.poll();
        final Player blackPlayer = playersQueue.poll();

        final Game nextGame = new Game(whitePlayer, blackPlayer);
        return Optional.of(nextGame.identifier);
    }

    public synchronized String addNewPlayer(String nickname) {
        final String playerIdentifier = nextRandomIdentifier();
        playersQueue.add(new WaitingPlayer(nickname, playerIdentifier));
        
        return playerIdentifier;
    }
    
    public synchronized String nextRandomIdentifier() {
        return new BigInteger(130, secureRandom).toString(32);
    }
}
