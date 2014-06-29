package com.miksinouf.chronowars;

import static java.util.function.Function.identity;
import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

import com.miksinouf.chronowars.domain.games.GameQueueSingleton;

public class ChronowarsServer {

  public static void main(String[] args) {
      staticFileLocation("/web-resources");
      get("/hello/:name", (request, response) -> "hello " + request.params("name"));
      get("/register/:nickname", (request, response) -> GameQueueSingleton.INSTANCE.register(request.params("nickname")));
      get("/have_i_new_game/:identifier", (request, response) -> GameQueueSingleton.INSTANCE
              .hasPlayerAGame(request.params("identifier"))
              .map(identity())
              .orElse("No game started yet."));
  }
}
