package com.miksinouf.chronowars;

import static java.util.function.Function.identity;
import static spark.Spark.*;
import static spark.SparkBase.staticFileLocation;

import com.miksinouf.chronowars.domain.games.GameQueueSingleton;

public class ChronowarsServer {

  public static void main(String[] args) {
      staticFileLocation("/web-resources");

      //permet d'inscrire le joueur à la liste d'attente des parties et renvoie l'identifiant unique
      get("/register/:nickname", (request, response) -> GameQueueSingleton.INSTANCE.register(request.params("nickname")));

      //identifiant de la partie ou message qui indique qu'aucune partie n'a démarré
      get("/have_i_running_game/:identifier", (request, response) -> GameQueueSingleton.INSTANCE
              .hasPlayerAGame(request.params("identifier"))
              .map(identity())
              .orElse("No game started yet."));

      /*renvoie une réponse json de la forme
                 {
                  "currentColorToPlay": "BLACK",
                  "status": "running",
                  "lastRoundPoints": "12",
                  "blackTokens": "(2,2),(1,3)",
                  "whiteTokens": "(1,2),(2,5),(3,2)"
                 }

         ou :

                 {
                  "currentColorToPlay": "WHITE",
                  "status": "finished",
                  "winner": "BLACK",
                  "blackTokens": "(2,2),(1,3)",
                  "whiteTokens": "(1,2),(2,5),(3,2)"
                 }
       */
      get("/get_game/:gameIdentifier", (request, response) -> GameQueueSingleton.INSTANCE
              .hasPlayerAGame(request.params("gameIdentifier")));

      //permet de placer un pion
      put("/set_token/:gameIdentifier/:playerIdentifier/:x/:y", (request, response) -> GameQueueSingleton.INSTANCE.setToken(
              request.params("gameIdentifier"),
              request.params("playerIdentifier"),
              request.params("x"),
              request.params("y")
      ));

      //permet de déplacer un pion
      patch("/move_token/:gameIdentifier/:playerIdentifier/:x/:y/:moveDirection", (request, response) -> GameQueueSingleton.INSTANCE.moveToken(
              request.params("gameIdentifier"),
              request.params("playerIdentifier"),
              request.params("x"),
              request.params("y"),
              request.params("moveDirection")
      ));
  }
}
