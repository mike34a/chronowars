package com.miksinouf.chronowars.domain.games;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;

import com.miksinouf.chronowars.domain.board.Board;
import com.miksinouf.chronowars.domain.board.IllegalMoveException;
import com.miksinouf.chronowars.domain.board.Move;
import com.miksinouf.chronowars.domain.board.MoveResult;
import com.miksinouf.chronowars.domain.player.*;

public enum GamesQueueSingleton {
    INSTANCE;
    public static final Integer MAX_NUMBER_OF_TOKENS = 8;

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
    
    public synchronized String getGame(String playerIdentifier) throws UnknownPlayerException {
    	return players.getGame(playerIdentifier);
    }

    public MoveResult setToken(String playerIdentifier, int x, int y) throws UnknownPlayerException, IllegalMoveException, TooManyTokensException {
        return players.setToken(playerIdentifier, x, y);
    }

    public MoveResult moveToken(String playerIdentifier, int x, int y, String move) throws UnknownPlayerException, IllegalMoveException {
        return players.moveToken(playerIdentifier, x, y, Move.valueOf(move));
    }
}
