package com.miksinouf.chronowars.domain.player;

public class WaitingPlayer {

    public final String nickname;
    public final String identifier;

    public WaitingPlayer(String nickname, String identifier) {
        this.nickname = nickname;
        this.identifier = identifier;
    }
}
