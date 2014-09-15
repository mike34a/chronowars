package com.miksinouf.chronowars.domain.games;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;

import org.eclipse.jetty.websocket.api.Session;

import com.google.common.annotations.VisibleForTesting;
import com.miksinouf.chronowars.domain.board.Board;
import com.miksinouf.chronowars.domain.player.Color;
import com.miksinouf.chronowars.domain.player.Player;
import com.miksinouf.chronowars.domain.player.WaitingPlayer;

public class GamesQueue {
    public static final Integer MAX_NUMBER_OF_TOKENS = 5;
    public static final Integer MAX_SCORE = 30;
    public Players players = new Players();
    private Session nextSession;
    private final SecureRandom secureRandom = new SecureRandom();
    private Optional<WaitingPlayer> waitingPlayer = Optional.empty();

    public synchronized String register(String nickname) {
        final String playerIdentifier = nextRandomIdentifier();
        if (waitingPlayer.isPresent()) {
            Board board = new Board();
            final Player whitePlayer = new Player(waitingPlayer.get(), board,
                    Color.WHITE);
            final Player blackPlayer = new Player(nickname, Color.BLACK,
                    playerIdentifier, board);
            blackPlayer.session = nextSession;
            players.addPlayers(whitePlayer, blackPlayer);
            this.waitingPlayer = Optional.empty();
            launchGame(whitePlayer, blackPlayer);
        } else {
            waitingPlayer = Optional.of(new WaitingPlayer(nickname,
                    playerIdentifier, nextSession));
        }

        return playerIdentifier;
    }
    
    public synchronized void launchGame(Player white, Player black) {
    	try {
			white.session.getRemote().sendString(white.getIdentifier());
			black.session.getRemote().sendString(black.getIdentifier());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void setSession(Session sess) {
    	this.nextSession = sess;
    }
    
    public synchronized String nextRandomIdentifier() {
        return new BigInteger(130, secureRandom).toString(32);
    }

    @VisibleForTesting
    void setPlayers(Players players) {
        this.players = players;
    }
}
