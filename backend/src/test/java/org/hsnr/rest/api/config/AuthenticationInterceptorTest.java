package org.hsnr.rest.api.config;

import org.hsnr.rest.domain.entities.User;
import org.hsnr.rest.domain.dao.UserDaoService;
import org.hsnr.rest.util.Base64Util;
import org.hsnr.rest.util.PasswordUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationInterceptorTest {

  @InjectMocks
  private AuthenticationInterceptor authInterceptor = new AuthenticationInterceptor();

  @Mock
  private AuthenticationInterceptor aiMock;

  @Mock
  private UserDaoService userService;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Before
  public void setUp() throws IOException {
    PrintWriter writer = mock(PrintWriter.class);
    doNothing().when(writer).write(anyString());
    when(response.getWriter()).thenReturn(writer);
    when(request.getRequestURI()).thenReturn("/order/mockURI");
  }

  @Test
  public void testUriNotSecured() throws Exception {
    when(request.getRequestURI()).thenReturn("/mockURI");

    boolean result = authInterceptor.preHandle(request, response, null);

    assertEquals(true, result);
    verify(request).getRequestURI();
  }

  @Test
  public void testNoHeader() throws Exception {
    when(request.getHeader(anyString())).thenReturn(null);

    boolean result = authInterceptor.preHandle(request, response, null);

    assertEquals(false, result);
    verify(request).getRequestURI();
    verify(request).getHeader(anyString());
  }

  @Test
  public void testUserNotFound() throws Exception {
    User user = new User("test@test.com", "test");
    String header = Base64Util.encodeCredentials(user.getEmail(), user.getPassword());

    when(request.getHeader(anyString())).thenReturn(header);
    when(userService.findByEmail(anyString())).thenReturn(null);

    boolean result = authInterceptor.preHandle(request, response, null);

    assertEquals(false, result);
    verify(request).getRequestURI();
    verify(request).getHeader(anyString());
    verify(userService).findByEmail(anyString());
  }

  @Test
  public void testAuthenticationSuccess() throws Exception {
    String password = "test";
    User user = new User("test@test.com", password);
    User userWithEncryptedPW = new User("test@test.com", PasswordUtil.hashPassword(password));
    String header = Base64Util.encodeCredentials(user.getEmail(), password);

    when(request.getHeader(anyString())).thenReturn(header);
    when(userService.findByEmail(anyString())).thenReturn(userWithEncryptedPW);


    boolean result = authInterceptor.preHandle(request, response, null);

    assertEquals(true, result);
    verify(request).getRequestURI();
    verify(request).getHeader(anyString());
    verify(userService).findByEmail(anyString());
  }

  @Test
  public void testAuthenticationFail() throws Exception {
    User user = new User("test@test.com", PasswordUtil.hashPassword("testXXX"));
    User user2 = new User("test@test.com", PasswordUtil.hashPassword("test"));

    String header = Base64Util.encodeCredentials(user.getEmail(), user.getPassword());

    when(aiMock.checkURI(anyString())).thenReturn(true);
    when(request.getHeader(anyString())).thenReturn(header);
    when(userService.findByEmail(anyString())).thenReturn(user2);

    boolean result = authInterceptor.preHandle(request, response, null);

    assertEquals(false, result);
    verify(request).getRequestURI();
    verify(request).getHeader(anyString());
    verify(userService).findByEmail(anyString());
  }
}
