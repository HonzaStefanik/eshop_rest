package org.hsnr.rest.api.controllers;

import org.hsnr.rest.domain.dao.PersonDaoService;
import org.hsnr.rest.domain.entities.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonRestController {

  private PersonDaoService personDaoService;

  public static final String PERSON_CREATED = "Person was created successfully.";

  public static final String PHONE_TAKEN = "This phone number is already taken.";

  public static final String PERSON_NULL = "This person does not exist.";

  public static final String PERSON_UPDATED = "Person updated successfully.";

  public static final String PERSON_DELETED = "Person deleted successfully.";

  @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json")
  public ResponseEntity createPerson(@RequestBody Person person) {
    String phoneNumber = person.getPhoneNumber();
    if (isPhoneNumberAvailable(phoneNumber)) {
      return ResponseEntity.ok().body(PERSON_CREATED);
    } else {
      return ResponseEntity.badRequest().body(PHONE_TAKEN);
    }
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public ResponseEntity deletePerson(@PathVariable("id") int id) {
    if (personDaoService.findById(id) != null) {
      personDaoService.deleteById(id);
      return ResponseEntity.ok().body(PERSON_DELETED);
    } else {
      return ResponseEntity.badRequest().body(PERSON_NULL);
    }
  }

  @RequestMapping(value = "/users", method = RequestMethod.GET) // needs to be admin only
  public ResponseEntity getAllPersons() {
    List<Person> userList = personDaoService.getAllPersons();
    return ResponseEntity.ok(userList);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET) // needs to be admin only or only for the user himself
  public ResponseEntity getPersonById(@PathVariable("id") int id) {
    Person person = personDaoService.findById(id);
    if (person != null) {
      return ResponseEntity.ok(person);
    } else {
      return ResponseEntity.badRequest().body(PERSON_NULL);
    }
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT) // needs to be admin only or only for the user himself
  public ResponseEntity updatePerson(@PathVariable Person person) {
    personDaoService.update(person);
    return ResponseEntity.ok().body(PERSON_UPDATED);
  }


  private boolean isPhoneNumberAvailable(String phoneNumber) {
    Person person = personDaoService.findByPhoneNumber(phoneNumber);
    return person == null;
  }

  public void setPersonDaoService(PersonDaoService personDaoService) {
    this.personDaoService = personDaoService;
  }
}
