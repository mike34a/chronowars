package com.miksinouf.chronowars;

import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

public class ChronowarsServer {
  public static void main(String[] args) {
      staticFileLocation("/web-resources");
      get("/hello/:name", (request, response) -> "hello " + request.params("name"));
  }
}
