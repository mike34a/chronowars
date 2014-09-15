package com.miksinouf.chronowars.domain.games;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.eclipse.jetty.websocket.api.Session;

public enum GamesQueueSingleton {
	INSTANCE;

	public final GamesQueue gamesQueue = new GamesQueue();

    public synchronized String register(String nickname) {
		try {
			return gamesQueue.register(URLDecoder.decode(nickname, StandardCharsets.UTF_8.name()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
    
    public void setSession(Session session) {
    	gamesQueue.setSession(session);
    }

}
