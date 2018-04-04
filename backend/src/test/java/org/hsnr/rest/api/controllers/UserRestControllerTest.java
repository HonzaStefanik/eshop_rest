package org.hsnr.rest.api.controllers;

import org.hsnr.rest.domain.entities.User;
import org.hsnr.rest.domain.dao.UserDaoService;
import org.hsnr.rest.util.PasswordUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hsnr.rest.api.controllers.UserRestController.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerTest {

  @InjectMocks
  private UserRestController controller = new UserRestController();

  @Mock
  private UserDaoService service;

  @Test
  public void testUserRegistration() {
    User user = new User("test@test.com", "test");

    doNothing().when(service).persist(user);
    when(service.findByEmail(anyString())).thenReturn(null);
    ResponseEntity result = controller.registerUser(user);

    assertEquals(ResponseEntity.ok().body(user), result);
    verify(service, times(1)).persist(user);
  }

  @Test
  public void testUserRegistrationAlreadyTaken() {
    User user = new User("test@test.com", "test");

    doNothing().when(service).persist(user);
    when(service.findByEmail(anyString())).thenReturn(user);
    ResponseEntity result = controller.registerUser(user);

    assertEquals(ResponseEntity.unprocessableEntity().body(EMAIL_TAKEN), result);
    verify(service, times(0)).persist(user);
  }

  @Test
  public void testUserRegistrationWrongEmailFormat(){
    User user = new User("test", "test");

    ResponseEntity result = controller.registerUser(user);

    assertEquals(ResponseEntity.badRequest().body(WRONG_EMAIL_FORMAT), result);
  }

  @Test
  public void testLoginSuccess() {
    User user = new User("test@test.com", "test");
    User userWithEncryptedPW = new User("test@test.com", PasswordUtil.hashPassword("test"));

    when(service.findByEmail(anyString())).thenReturn(userWithEncryptedPW);
    ResponseEntity result = controller.loginUser(user);

    assertEquals(ResponseEntity.ok().body(user), result);
    verify(service, times(1)).findByEmail(anyString());
  }

  @Test
  public void testLoginFail() {
    User user = new User("test@test.com", "test");

    when(service.findByEmail(anyString())).thenReturn(null);
    ResponseEntity result = controller.loginUser(user);

    assertEquals(ResponseEntity.badRequest().body(LOGIN_FAILED), result);
    verify(service, times(1)).findByEmail(anyString());
  }

  @Test
  public void testGetAllUsers(){
    List<User> userList = new ArrayList<>();

    doReturn(userList).when(service).getAllUsers();
    ResponseEntity result = controller.getAllUsers();

    assertEquals(ResponseEntity.ok(userList), result);
  }

  @Test
  public void testGetUserByIdSuccess(){
    User user = new User("test@test.com", "test");

    when(service.findById(anyInt())).thenReturn(user);
    ResponseEntity result = controller.getUserById(anyInt());

    assertEquals(ResponseEntity.ok().body(user), result);
  }

  @Test
  public void testGetUserByIdFail(){
    when(service.findById(anyInt())).thenReturn(null);

    ResponseEntity result = controller.getUserById(anyInt());

    assertEquals(ResponseEntity.badRequest().body(USER_NULL), result);
  }
  
}
