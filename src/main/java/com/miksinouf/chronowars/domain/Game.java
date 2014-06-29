package com.miksinouf.chronowars.domain;

import com.miksinouf.chronowars.domain.games.GameQueueSingleton;
import com.miksinouf.chronowars.domain.player.Player;

public class Game {
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
