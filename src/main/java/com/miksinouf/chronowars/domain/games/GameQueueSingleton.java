package com.miksinouf.chronowars.domain.games;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;

import com.miksinouf.chronowars.domain.board.Board;
import com.miksinouf.chronowars.domain.player.Color;
import com.miksinouf.chronowars.domain.player.Player;
import com.miksinouf.chronowars.domain.player.UnknownPlayerException;
import com.miksinouf.chronowars.domain.player.WaitingPlayer;

public enum GameQueueSingleton {
    INSTANCE;

    private final Players players = new Players();
    private final SecureRandom secureRandom = new SecureRandom();
    private Optional<WaitingPlayer> waitingPlayer = Optional.empty();

    public synchronized Boolean hasPlayerAGame(String playerIdentifier) {
        return players.hasPlayerAGame(playerIdentifier);
    }

    public synchronized String register(String nickname) {
        final String playerIdentifier = nextRandomIdentifier();
        if (waitingPlayer.isPresent()) {
            Board board = new Board();

            final Player whitePlayer = new Player(waitingPlayer.get(), board, Color.WHITE);
            final Player blackPlayer = new Player(nickname, Color.BLACK, 8, playerIdentifier, board);
            players.addPlayers(whitePlayer, blackPlayer);

            this.waitingPlayer = Optional.empty();
        } else {
            waitingPlayer = Optional.of(new WaitingPlayer(nickname, playerIdentifier));
        }
        
        return playerIdentifier;
    }
    
    public synchronized String nextRandomIdentifier() {
        return new BigInteger(130, secureRandom).toString(32);
    }

    public void setToken(String playerIdentifier, String x, String y) throws UnknownPlayerException {
        players.setToken(playerIdentifier, Integer.parseInt(x), Integer.parseInt(y));
    }

    public Object moveToken(String gameIdentifier, String playerIdentifier, String x, String y, String move) {
        return null;
    }
}
