package org.hsnr.rest.domain.dao;

import org.hsnr.rest.domain.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoServiceTest {

  @Mock
  private UserDaoService service;

  @Before
  public void setUp() {
    when(service.findById(anyInt())).thenReturn(new User());
  }

  @Test
  public void testFindById() {
    User user = service.findById(1);
    assertNotNull(user);
  }

}
