package com.miksinouf.chronowars.domain.server.controller;

import org.springframework.web.bind.annotation.*;

import com.miksinouf.chronowars.domain.board.MoveResult;
import com.miksinouf.chronowars.domain.games.GamesQueueSingleton;
import com.miksinouf.chronowars.domain.server.ChronowarsAdapter;

@RestController
public class ChronowarsRestController {

    @RequestMapping(value = "register/{nickname}", method = RequestMethod.GET)
    @ResponseBody
    public String register(@PathVariable String nickname) {
        return GamesQueueSingleton.INSTANCE.register(nickname);
    }

    @RequestMapping(value = "have_i_running_game/{identifier}", method = RequestMethod.GET)
    @ResponseBody
    public String haveIrunningGame(@PathVariable String identifier) {
        return GamesQueueSingleton.INSTANCE.gamesQueue.players.hasPlayerAGame(identifier);
    }

    @RequestMapping(value = "get_game/{playerIdentifier}", method = RequestMethod.GET)
    @ResponseBody
    public String getGame(@PathVariable String playerIdentifier) {
        final ChronowarsAdapter chronowarsAdapter = new ChronowarsAdapter();
        return chronowarsAdapter.adaptGame(GamesQueueSingleton.INSTANCE.gamesQueue.players.getGame(playerIdentifier));
    }

    @RequestMapping(value = "set_token/{playerIdentifier}/{x}/{y}", method = RequestMethod.PUT)
    @ResponseBody
    public MoveResult setToken(@PathVariable String playerIdentifier, @PathVariable Integer x, @PathVariable Integer y) {
        return GamesQueueSingleton.INSTANCE.gamesQueue.players.setToken(playerIdentifier, x, y);
    }

    @RequestMapping(value = "move_token/{playerIdentifier}/{x}/{y}/{moveDirection}", method = RequestMethod.PATCH)
    @ResponseBody
    public MoveResult moveToken(@PathVariable String playerIdentifier, @PathVariable Integer x, @PathVariable Integer y,
                                @PathVariable String moveDirection) {
        return GamesQueueSingleton.INSTANCE.gamesQueue.players.moveToken(playerIdentifier, x, y, moveDirection);
    }

}
