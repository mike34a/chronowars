package com.miksinouf.chronowars.domain.server;

import com.google.gson.Gson;
import com.miksinouf.chronowars.domain.games.GameResponse;

public class ChronowarsAdapter {

    private Gson gson = new Gson();

    public String adaptGame(GameResponse gameResponse) {
        return gson.toJson(gameResponse);
    }
}
