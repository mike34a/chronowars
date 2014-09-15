package com.miksinouf.chronowars.domain.player;

import org.eclipse.jetty.websocket.api.Session;

public class WaitingPlayer {

    public final String nickname;
    public final String identifier;
    public Session sess;

    public WaitingPlayer(String nickname, String identifier, Session sess) {
        this.nickname = nickname;
        this.identifier = identifier;
        this.sess = sess;
    }
}
