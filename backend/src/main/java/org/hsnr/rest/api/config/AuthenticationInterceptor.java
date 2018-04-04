package org.hsnr.rest.api.config;

import org.hsnr.rest.domain.dao.UserDaoService;
import org.hsnr.rest.domain.entities.User;
import org.hsnr.rest.util.Base64Util;
import org.hsnr.rest.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class AuthenticationInterceptor implements HandlerInterceptor {

  private static final Logger LOG = LoggerFactory.getLogger(AuthenticationInterceptor.class);

  private UserDaoService userDaoService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
    String uri = request.getRequestURI();
    if (checkURI(uri)) {
      String authHeader = request.getHeader("authentication");
      if (authHeader == null || authHeader.isEmpty()) {
        LOG.debug("Authentication failed, header was null or empty");
        response.setStatus(401);
        response.getWriter().write("Authentication is required to access this page");
        return false;
      }
      User authUser = Base64Util.decodeCredentials(authHeader);
      String authUserEmail = authUser.getEmail();
      String authUserPassword = authUser.getPassword();
      User dbUser = userDaoService.findByEmail(authUserEmail);
      if (dbUser != null) {
        String dbPassword = dbUser.getPassword();
        if (PasswordUtil.isCorrectPassword(authUserPassword, dbPassword)) {
          LOG.debug("User with email '{}' successfully authenticated", authUserEmail);
          return true;
        } else {
          LOG.debug("Password '{}' doesn't match the user with email '{}'", authUserPassword, authUserEmail);
          response.setStatus(403);
          response.getWriter().write("Authentication failed");
          return false;
        }
      } else {
        LOG.debug("User with email '{}' doesn't exist", authUserEmail);
        response.setStatus(403);
        response.getWriter().write("Authentication failed");
        return false;
      }
    } else {
      return true;
    }
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    // not used
  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    // not used
  }

  public boolean checkURI(String uri) {
    String[] secureUris = setSecureUris();
    return Arrays.stream(secureUris).anyMatch(uri::contains);
  }

  private String[] setSecureUris(){
    return new String[]{"order", "user/delete", "user/update", "user/change-password", "user/update", "user/users", /*"product/create", "product/delete",*/ "person"};
  }

  public void setUserDaoService(UserDaoService userDaoService) {
    this.userDaoService = userDaoService;
  }

}
