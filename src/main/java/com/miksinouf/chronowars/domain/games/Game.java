package com.miksinouf.chronowars.domain.games;

import com.miksinouf.chronowars.domain.board.Board;
import com.miksinouf.chronowars.domain.player.Player;

public class Game {
    public static final Integer MAX_NUMBER_OF_TOKENS = 8;
    public final Player whitePlayer, blackPlayer;
    public final Board board;
    public final String identifier;

    public Game(Player whitePlayer, Player blackPlayer, Board board) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;

        this.identifier = GameQueueSingleton.INSTANCE.nextRandomIdentifier();
    }

    public String getIdentifier() {
        return identifier;
    }
}
