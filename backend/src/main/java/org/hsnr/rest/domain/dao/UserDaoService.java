package org.hsnr.rest.domain.dao;

import org.hsnr.rest.domain.entities.User;

import java.util.List;

public class UserDaoService {

  private UserDao dao;

  public void persist(User entity) {
    dao.openCurrentSessionWithTransaction();
    dao.persist(entity);
    dao.closeCurrentSessionwithTransaction();
  }

  public void update(User entity) {
    dao.openCurrentSessionWithTransaction();
    dao.update(entity);
    dao.closeCurrentSessionwithTransaction();
  }

  public User findById(int id) {
    dao.openCurrentSession();
    User user = dao.findById(id);
    dao.closeCurrentSession();
    return user;
  }

  public User findByEmail(String email) {
    dao.openCurrentSession();
    User user = dao.findByEmail(email);
    dao.closeCurrentSession();
    return user;
  }

  public void deleteById(int id) {
    dao.openCurrentSessionWithTransaction();
    User user = dao.findById(id);
    dao.delete(user);
    dao.closeCurrentSessionwithTransaction();
  }

  public List<User> getAllUsers() {
    dao.openCurrentSession();
    List<User> users = dao.getAllUsers();
    dao.closeCurrentSession();
    return users;
  }

  public void setDao(UserDao dao) {
    this.dao = dao;
  }

  public UserDao getDao() {
    return dao;
  }
}
