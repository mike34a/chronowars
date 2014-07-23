package com.miksinouf.chronowars.domain.server;

import com.miksinouf.chronowars.domain.games.GameQueueSingleton;

public class ChronowarsServerAdapter {
    static String setToken(String playerIdentifier, String x, String y) {
        try {
            GameQueueSingleton.INSTANCE.setToken(playerIdentifier, x, y);
        } catch (Exception e) {

        }

        return null;
    }
}
