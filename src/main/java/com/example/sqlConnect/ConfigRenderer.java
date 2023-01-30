package com.example.sqlConnect;

import java.io.FileReader;
import java.util.Properties;

public class ConfigRenderer {
  public static Integer DB_PORT;
  public static String HOST;
  public static String DB_NAME;
  public static String DB_USERNAME;
  public static String DB_PASSWORD;
  public static Integer SERVER_PORT;

  public static void renderConfig(){
    Properties properties = new Properties();
    try {
      properties.load(new FileReader("src/main/resources/config.properties"));
    } catch (Throwable e) {
      System.out.println("can't read config file");
    }

    DB_PORT = Integer.parseInt(properties.getProperty("DB_PORT"));
    HOST = properties.getProperty("HOST");
    DB_NAME = properties.getProperty("DB_NAME");
    DB_USERNAME = properties.getProperty("DB_USERNAME");
    DB_PASSWORD = properties.getProperty("DB_PASSWORD");
    SERVER_PORT = Integer.parseInt(properties.getProperty("SERVER_PORT"));
  }
}
