package com.miksinouf.chronowars.domain;

import com.miksinouf.chronowars.domain.games.GameQueueSingleton;
import com.miksinouf.chronowars.domain.player.Player;

public class Game {
    public final Player whitePlayer, blackPlayer;
    public final String identifier;

    public Game(Player whitePlayer, Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;

        this.identifier = GameQueueSingleton.INSTANCE.nextRandomIdentifier();
    }
}
