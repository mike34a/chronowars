package com.miksinouf.chronowars.domain.server;

import static spark.Spark.*;
import static spark.SparkBase.staticFileLocation;

import spark.Response;

import com.miksinouf.chronowars.domain.games.GamesQueueSingleton;
import com.miksinouf.chronowars.domain.player.UnknownPlayerException;

public class ChronowarsServer {

    public static void main(String[] args) {
        staticFileLocation("/web-resources");

        // permet d'inscrire le joueur à la liste d'attente des parties et
        // renvoie l'identifiant unique
        get("/register/:nickname",
                (request, response) -> GamesQueueSingleton.INSTANCE
                        .register(request.params("nickname")));

        // boolean
        get("/have_i_running_game/:identifier",
                (request, response) -> GamesQueueSingleton.INSTANCE
                        .hasPlayerAGame(request.params("identifier")));

        /**
         * renvoie une réponse json de la forme { "currentColorToPlay": "BLACK",
         * "status": "running", "lastRoundPoints": "12", "blackTokens":
         * "(2,2),(1,3)", "whiteTokens": "(1,2),(2,5),(3,2)" }
         * 
         * ou :
         * 
         * { "currentColorToPlay": "WHITE", "status": "finished", "winner":
         * "BLACK", "blackTokens": "(2,2),(1,3)", "whiteTokens":
         * "(1,2),(2,5),(3,2)" }
         */
        get("/get_game/:gameIdentifier",
                (request, response) -> GamesQueueSingleton.INSTANCE
                        .hasPlayerAGame(request.params("gameIdentifier")));

        /**
         * permet de placer un pion
         */
        put("/set_token/:playerIdentifier/:x/:y",
                (request, response) -> {
                    final String x = request.params("x");
                    final String y = request.params("y");
                    try {
                        return GamesQueueSingleton.INSTANCE.setToken(
                                request.params("playerIdentifier"),
                                parseInt(x), parseInt(y));
                    } catch (UnknownPlayerException e) {
                        return badRequest(response,
                                "This player identifier does not exist!");
                    } catch (ChronowarsNumberFormatException e) {
                        return badRequest(response,
                                "One of the coordinates is not an integer.");
                    }
                });

        /**
         * Permet de déplacer un pion moveDirection = UP | UP_RIGHT | RIGHT |
         * DOWN_RIGHT | DOWN | DOWN_LEFT | LEFT | UP_LEFT
         */
        patch("/move_token/:playerIdentifier/:x/:y/:moveDirection",
                (request, response) -> {
                    final String x = request.params("x");
                    final String y = request.params("y");
                    try {
                        return GamesQueueSingleton.INSTANCE.moveToken(
                                request.params("playerIdentifier"),
                                parseInt(x), parseInt(y),
                                request.params("moveDirection"));
                    } catch (UnknownPlayerException e) {
                        return badRequest(response,
                                "This player identifier does not exist!");
                    } catch (ChronowarsNumberFormatException e) {
                        return badRequest(response,
                                "One of the coordinates is not an integer.");
                    }
                });
    }

    private static String badRequest(Response response, String message) {
        response.status(400);
        return message;
    }

    private static int parseInt(String intToParse)
            throws ChronowarsNumberFormatException {
        try {
            return Integer.parseInt(intToParse);
        } catch (NumberFormatException e) {
            throw new ChronowarsNumberFormatException();
        }
    }

}
