package com.miksinouf.chronowars.domain.games;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public enum GamesQueueSingleton {
	INSTANCE;

	public final GamesQueue gamesQueue = new GamesQueue();

    public synchronized String register(String nickname) {
		try {
			return gamesQueue.register(URLDecoder.decode(nickname, StandardCharsets.UTF_8.name()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
