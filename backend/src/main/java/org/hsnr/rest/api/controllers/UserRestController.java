package org.hsnr.rest.api.controllers;

import org.hsnr.rest.context.PasswordChangeContext;
import org.hsnr.rest.domain.dao.UserDaoService;
import org.hsnr.rest.domain.entities.User;
import org.hsnr.rest.util.EmailUtil;
import org.hsnr.rest.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("user")
public class UserRestController {

  public static final String WRONG_EMAIL_FORMAT = "Make sure you enter your email in a correct format.";

  public static final String EMAIL_TAKEN = "This email address is already taken.";

  public static final String EMAIL_SENT = "Email has been successfully sent.";

  public static final String USER_CREATED = "User created successfully.";

  public static final String LOGIN_FAILED = "Login failed, make sure you enter the correct email and password.";

  public static final String LOGIN_SUCCESS = "You logged in successfully.";

  public static final String USER_NULL = "This user does not exist.";

  public static final String USER_UPDATED = "User updated successfully.";

  public static final String USER_DELETED = "User deleted successfully.";

  public static final String EMPTY_FIELD = "Make sure you fill in all the fields.";

  public static final String INVALID_PW = "Make sure the new password is the same in both fields. New password also can not be the same as your old password.";

  public static final String PW_CHANGED = "Password successfully changed";

  private static final Logger LOG = LoggerFactory.getLogger(UserRestController.class);

  private UserDaoService userDaoService;


  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public ResponseEntity registerUser(@RequestBody User user) {
    String email = user.getEmail();
    boolean validFormat = EmailUtil.validateAddress(email);
    if (!validFormat) {
      LOG.debug("Email  wrong format '{}'", email);
      return ResponseEntity.badRequest().body(WRONG_EMAIL_FORMAT);
    }
    if (isEmailAvailable(email)) {
      String hashedPw = PasswordUtil.hashPassword(user.getPassword());
      user.setPassword(hashedPw);
      userDaoService.persist(user);
      LOG.debug("User with email '{}' was created", email);
      return ResponseEntity.ok().body(user);
    } else {
      LOG.debug("Email '{}' is already taken", email);
      return ResponseEntity.unprocessableEntity().body(EMAIL_TAKEN);
    }
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity loginUser(@RequestBody User user) {
    String givenEmail = user.getEmail();
    String givenPassword = user.getPassword();
    User dbUser = userDaoService.findByEmail(givenEmail);
    if (dbUser != null) {
      String dbPassword = dbUser.getPassword();
      if (PasswordUtil.isCorrectPassword(givenPassword, dbPassword)) {
        LOG.debug("Login of user '{}' succeeded", givenEmail);
        return ResponseEntity.ok().body(user);
      } else {
        LOG.debug("Login of user '{}' failed", givenEmail);
        return ResponseEntity.badRequest().body(LOGIN_FAILED);
      }
    } else {
      return ResponseEntity.badRequest().body(LOGIN_FAILED);
    }
  }

  @RequestMapping(value = "/users", method = RequestMethod.GET) // needs to be admin only
  public ResponseEntity getAllUsers() {
    List<User> userList = userDaoService.getAllUsers();
    return ResponseEntity.ok(userList);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET) // needs to be admin only or only for the user himself
  public ResponseEntity getUserById(@PathVariable("id") int id) {
    User user = userDaoService.findById(id);
    if (user != null) {
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.badRequest().body(USER_NULL);
    }
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT) // needs to be admin only or only for the user himself
  public ResponseEntity updateUser(@PathVariable User user) {
    userDaoService.update(user);
    return ResponseEntity.ok().body(USER_UPDATED);
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE) // needs to be admin only or only for the user himself
  public ResponseEntity deleteUser(@PathVariable("id") int id) {
    if (userDaoService.findById(id) != null) {
      userDaoService.deleteById(id);
      return ResponseEntity.ok().body(USER_DELETED);
    } else {
      return ResponseEntity.badRequest().body(USER_NULL);
    }
  }

  @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
  public ResponseEntity resetPassword(@RequestParam String userEmail) {
    boolean validFormat = EmailUtil.validateAddress(userEmail);
    if (!validFormat) {
      return ResponseEntity.badRequest().body(WRONG_EMAIL_FORMAT);
    }
    User user = userDaoService.findByEmail(userEmail);
    if (user != null) {
      String email = user.getEmail();
      String newPassword = PasswordUtil.generate(15);
      user.setPassword(newPassword);
      user.setPasswordChangeNeeded(true);
      userDaoService.update(user);
      EmailUtil.newPasswordEmail(email, newPassword);
      return ResponseEntity.ok().body(EMAIL_SENT);
    } else {
      return ResponseEntity.badRequest().body(USER_NULL);
    }
  }

  @RequestMapping(value = "/change-password", method = RequestMethod.POST) // only for the user himself
  public ResponseEntity changePassword(@RequestBody PasswordChangeContext passwordChangeContext) {
    if (passwordChangeContext.isFieldEmpty()) {
      return ResponseEntity.badRequest().body(EMPTY_FIELD);
    }

    String email = passwordChangeContext.getUserEmail();
    User user = userDaoService.findByEmail(email);
    if (user == null) {
      return ResponseEntity.badRequest().body(USER_NULL);
    }

    String oldPassword = passwordChangeContext.getOldPassword();
    String newPassword = passwordChangeContext.getNewPassword();
    String newPasswordRepeat = passwordChangeContext.getNewPasswordRepeat();
    if (newPassword.equals(newPasswordRepeat) && !oldPassword.equals(newPassword)) {
      user.setPassword(newPassword);
      userDaoService.update(user);
      return ResponseEntity.ok().body(PW_CHANGED);
    } else {
      return ResponseEntity.badRequest().body(INVALID_PW);
    }
  }

  private boolean isEmailAvailable(String email) {
    User user = userDaoService.findByEmail(email);
    return user == null;
  }

  public void setUserDaoService(UserDaoService userDaoService) {
    this.userDaoService = userDaoService;
  }

}
