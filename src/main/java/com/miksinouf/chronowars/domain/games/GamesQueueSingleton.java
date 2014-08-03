package com.miksinouf.chronowars.domain.games;

import com.miksinouf.chronowars.domain.board.IllegalMoveException;
import com.miksinouf.chronowars.domain.player.TooManyTokensException;
import com.miksinouf.chronowars.domain.player.UnknownPlayerException;

public enum GamesQueueSingleton {
    INSTANCE;
    
    private final GamesQueue gamesQueue = new GamesQueue();

    public synchronized String hasPlayerAGame(String playerIdentifier) {
        return gamesQueue.hasPlayerAGame(playerIdentifier);
    }

    public synchronized String register(String nickname) {
        return gamesQueue.register(nickname);
    }

    public synchronized String nextRandomIdentifier() {
        return gamesQueue.nextRandomIdentifier();
    }

    public synchronized String getGame(String playerIdentifier)
            throws UnknownPlayerException {
        return gamesQueue.getGame(playerIdentifier);
    }

    public String setToken(String playerIdentifier, int x, int y)
            throws UnknownPlayerException, IllegalMoveException,
            TooManyTokensException {
        return gamesQueue.setToken(playerIdentifier, x, y);
    }

    public String moveToken(String playerIdentifier, int x, int y, String move)
            throws UnknownPlayerException, IllegalMoveException {
        return gamesQueue.moveToken(playerIdentifier, x, y, move);
    }
}
