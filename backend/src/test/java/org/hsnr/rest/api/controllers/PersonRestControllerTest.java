package org.hsnr.rest.api.controllers;

import org.hsnr.rest.domain.dao.PersonDaoService;
import org.hsnr.rest.domain.entities.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hsnr.rest.api.controllers.PersonRestController.PERSON_CREATED;
import static org.hsnr.rest.api.controllers.PersonRestController.PERSON_NULL;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonRestControllerTest {

  @InjectMocks
  private PersonRestController controller = new PersonRestController();

  @Mock
  private PersonDaoService personsService;


  @Test
  public void testCreatePersonSuccess() {
    Person person = new Person();

    doNothing().when(personsService).persist(person);
    ResponseEntity result = controller.createPerson(person);

    assertEquals(ResponseEntity.ok().body(PERSON_CREATED), result);
  }

  @Test
  public void testGetAllPersons() {
    List<Person> personList = new ArrayList<>();

    doReturn(personList).when(personsService).getAllPersons();
    ResponseEntity result = controller.getAllPersons();

    assertEquals(ResponseEntity.ok(personList), result);
  }

  @Test
  public void testGetPersonByIdSuccess() {
    Person person = new Person();

    when(personsService.findById(anyInt())).thenReturn(person);
    ResponseEntity result = controller.getPersonById(anyInt());

    assertEquals(ResponseEntity.ok().body(person), result);
  }

  @Test
  public void testGetPersonByIdFail() {
    when(personsService.findById(anyInt())).thenReturn(null);

    ResponseEntity result = controller.getPersonById(anyInt());

    assertEquals(ResponseEntity.badRequest().body(PERSON_NULL), result);
  }
}
