package com.miksinouf.chronowars.domain.games;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;

import com.miksinouf.chronowars.domain.Board;
import com.miksinouf.chronowars.domain.Game;
import com.miksinouf.chronowars.domain.player.Color;
import com.miksinouf.chronowars.domain.player.Player;
import com.miksinouf.chronowars.domain.player.WaitingPlayer;

public enum GameQueueSingleton {
    INSTANCE;

    private final Games games = new Games();
    private final SecureRandom secureRandom = new SecureRandom();
    private Optional<WaitingPlayer> waitingPlayer = Optional.empty();

    public synchronized Optional<String> hasPlayerAGame(String identifier) {
        return games.getGameIdentifier(identifier);
    }

    public synchronized String register(String nickname) {
        final String playerIdentifier = nextRandomIdentifier();
        if (waitingPlayer.isPresent()) {
            Board board = new Board();

            final Player whitePlayer = new Player(waitingPlayer.get(), board, Color.WHITE);
            final Player blackPlayer = new Player(nickname, Color.BLACK, 8, playerIdentifier, board);
            final Game nextGame = new Game(whitePlayer, blackPlayer, board);
            games.addGame(nextGame);
            
            this.waitingPlayer = Optional.empty();
        } else {
            waitingPlayer = Optional.of(new WaitingPlayer(nickname, playerIdentifier));
        }
        
        return playerIdentifier;
    }
    
    public synchronized String nextRandomIdentifier() {
        return new BigInteger(130, secureRandom).toString(32);
    }
}
