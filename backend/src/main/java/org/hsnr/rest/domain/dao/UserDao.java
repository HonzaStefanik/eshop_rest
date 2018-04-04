package org.hsnr.rest.domain.dao;

import org.hibernate.query.Query;
import org.hsnr.rest.domain.entities.User;

import javax.persistence.NoResultException;
import java.util.List;

public class UserDao extends AbstractDao {

  User findById(int id) {
    return getCurrentSession().get(User.class, id);
  }

  User findByEmail(String email) {
    Query query = getCurrentSession().createQuery("from User u where u.email = :email");
    query.setParameter("email", email);

    try {
      return (User) query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  void persist(User entity) {
    getCurrentSession().save(entity);
  }

  void update(User entity) {
    getCurrentSession().update(entity);
  }

  void delete(User entity) {
    getCurrentSession().delete(entity);
  }

  List<User> getAllUsers() {
    return (List<User>) getCurrentSession().createQuery("from User").list();
  }

}
