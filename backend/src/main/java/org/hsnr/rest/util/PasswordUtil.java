package org.hsnr.rest.util;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {

  private PasswordUtil() {}

  public static String generate(int length) {
    return RandomStringUtils.randomAlphanumeric(length);
  }

  public static String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(12));
  }

  public static boolean isCorrectPassword(String inputPassword, String encryptedPassword) {
    return BCrypt.checkpw(inputPassword, encryptedPassword);
  }
}
