package com.miksinouf.chronowars;

import static spark.Spark.get;

public class ChronowarsServer {
  public static void main(String[] args) {
      get("/hello/:name", (request, response) -> "hello " + request.params("name"));
  }
}
