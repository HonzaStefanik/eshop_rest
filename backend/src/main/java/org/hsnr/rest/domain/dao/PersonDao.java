package org.hsnr.rest.domain.dao;

import org.hibernate.query.Query;
import org.hsnr.rest.domain.entities.Person;

import javax.persistence.NoResultException;
import java.util.List;

public class PersonDao extends AbstractDao {

  Person findById(int id) {
    return getCurrentSession().get(Person.class, id);
  }

  Person findByPhoneNumber(String phoneNumber){
    Query query = getCurrentSession().createQuery("from Person p where p.phoneNumber = :phoneNumber");
    query.setParameter("phoneNumber", phoneNumber);

    try {
      return (Person) query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  void persist(Person entity) {
    getCurrentSession().save(entity);
  }

  void update(Person entity) {
    getCurrentSession().update(entity);
  }

  void delete(Person entity) {
    getCurrentSession().delete(entity);
  }

  List<Person> getAllPersons() {
    return (List<Person>) getCurrentSession().createQuery("from Person").list();
  }

}
