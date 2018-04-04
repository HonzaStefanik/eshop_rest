package org.hsnr.rest.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.hsnr.rest.domain.entities.User;

public class Base64Util {

  private Base64Util(){}

  public static String encodeCredentials(String username, String password) {
    String credentials = username + ":" + password;
    return Base64.getEncoder().encodeToString(credentials.getBytes());
  }

  public static User decodeCredentials(String authString) {
    byte[] credentialsBytes = Base64.getDecoder().decode(authString);
    String credentials = new String(credentialsBytes, StandardCharsets.UTF_8);
    String[] credentialsArray = credentials.split(":");
    String email = credentialsArray[0];
    String password = credentialsArray[1];
    return new User(email, password);
  }

}
