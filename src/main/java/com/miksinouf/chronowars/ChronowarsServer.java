package com.miksinouf.chronowars;

import spark.Spark;

public class ChronowarsServer {
  public static void main(String[] args) {
      Spark.get("/hello/:name", (request, response) -> "hello " + request.params("name"));
  }
}
