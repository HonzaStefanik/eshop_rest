package org.hsnr.rest.context;

import org.apache.commons.lang.StringUtils;

public class PasswordChangeContext {

  private String userEmail;

  private String oldPassword;

  private String newPassword;

  private String newPasswordRepeat;

  public String getUserEmail() {
    return userEmail;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public String getNewPasswordRepeat() {
    return newPasswordRepeat;
  }

  public boolean isFieldEmpty() {
    return StringUtils.isBlank(userEmail) || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword) || StringUtils.isBlank(newPasswordRepeat);
  }
}
