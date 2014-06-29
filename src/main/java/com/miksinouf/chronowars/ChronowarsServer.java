package com.miksinouf.chronowars;

import static java.util.function.Function.identity;
import static spark.Spark.*;
import static spark.SparkBase.staticFileLocation;

import com.miksinouf.chronowars.domain.games.GameQueueSingleton;

public class ChronowarsServer {

  public static void main(String[] args) {
      staticFileLocation("/web-resources");
      get("/hello/:name", (request, response) -> "hello " + request.params("name"));
      get("/register/:nickname", (request, response) -> GameQueueSingleton.INSTANCE.register(request.params("nickname")));
      get("/have_i_running_game/:identifier", (request, response) -> GameQueueSingleton.INSTANCE
              .hasPlayerAGame(request.params("identifier"))
              .map(identity())
              .orElse("No game started yet."));
      
      put("/set_token/:gameIdentifier/:playerIdentifier/:x/:y", (request, response) -> GameQueueSingleton.INSTANCE.setToken(
              request.params("gameIdentifier"),
              request.params("playerIdentifier"),
              request.params("x"),
              request.params("y")
      ));
      
      patch("/move_token/:gameIdentifier/:playerIdentifier/:x/:y/:moveDirection", (request, response) -> GameQueueSingleton.INSTANCE.moveToken(
              request.params("gameIdentifier"),
              request.params("playerIdentifier"),
              request.params("x"),
              request.params("y"),
              request.params("moveDirection")
      ));
  }
}
