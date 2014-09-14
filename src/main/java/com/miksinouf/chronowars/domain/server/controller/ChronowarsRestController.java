package com.miksinouf.chronowars.domain.server.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.miksinouf.chronowars.domain.games.GamesQueueSingleton;
import com.miksinouf.chronowars.domain.server.ChronowarsAdapter;

@RestController
public class ChronowarsRestController {

    @RequestMapping(value = "register/{nickname}", method = RequestMethod.GET)
    public String register(@PathVariable String nickname) {
        return GamesQueueSingleton.INSTANCE.register(nickname);
    }

    @RequestMapping(value = "have_i_running_game/{identifier}", method = RequestMethod.GET)
    public String haveIrunningGame(@PathVariable String identifier) {
        return GamesQueueSingleton.INSTANCE.gamesQueue.players
                .hasPlayerAGame(identifier);
    }

    @RequestMapping(value = "get_game/{playerIdentifier}", method = RequestMethod.GET)
    public String getGame(@PathVariable String playerIdentifier) {
        final ChronowarsAdapter chronowarsAdapter = new ChronowarsAdapter();
        return chronowarsAdapter
                .adaptGame(GamesQueueSingleton.INSTANCE.gamesQueue.players
                        .getGame(playerIdentifier));
    }

}
