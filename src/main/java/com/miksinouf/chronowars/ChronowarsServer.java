package com.miksinouf.chronowars;

import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

import com.miksinouf.chronowars.domain.games.GameQueueSingleton;

public class ChronowarsServer {
  public static void main(String[] args) {
      staticFileLocation("/web-resources");
      get("/hello/:name", (request, response) -> "hello " + request.params("name"));
      get("/gametoken/:nickname", (request, response) -> GameQueueSingleton.INSTANCE.addNewPlayer(request.params("nickname")));
      get("/startnewgame", (request, response) -> GameQueueSingleton.INSTANCE.runNextGame());
  }
}
