package org.hsnr.rest.domain.dao;

import org.hsnr.rest.domain.entities.Person;

import java.util.List;

public class PersonDaoService {

  private PersonDao dao;

  public void persist(Person entity) {
    dao.openCurrentSessionWithTransaction();
    dao.persist(entity);
    dao.closeCurrentSessionwithTransaction();
  }

  public void update(Person entity) {
    dao.openCurrentSessionWithTransaction();
    dao.update(entity);
    dao.closeCurrentSessionwithTransaction();
  }

  public Person findById(int id) {
    dao.openCurrentSession();
    Person person = dao.findById(id);
    dao.closeCurrentSession();
    return person;
  }

  public Person findByPhoneNumber(String phoneNumber){
    dao.openCurrentSession();
    Person person = dao.findByPhoneNumber(phoneNumber);
    dao.closeCurrentSession();
    return person;
  }

  public void deleteById(int id) {
    dao.openCurrentSessionWithTransaction();
    Person person = dao.findById(id);
    dao.delete(person);
    dao.closeCurrentSessionwithTransaction();
  }

  public List<Person> getAllPersons() {
    dao.openCurrentSession();
    List<Person> persons = dao.getAllPersons();
    dao.closeCurrentSession();
    return persons;
  }

  public void setDao(PersonDao dao) {
    this.dao = dao;
  }

  public PersonDao getDao() {
    return dao;
  }
}
