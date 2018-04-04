package org.hsnr.rest.api.controllers;

import org.hsnr.rest.domain.dao.ProductDaoService;
import org.hsnr.rest.domain.entities.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hsnr.rest.api.controllers.ProductRestController.PRODUCT_NULL;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductRestControllerTest {

  @InjectMocks
  private ProductRestController controller = new ProductRestController();

  @Mock
  private ProductDaoService service;

  @Test
  public void testGetAllProducts(){
    List<Product> productList = new ArrayList<>();

    doReturn(productList).when(service).getAllProducts();
    ResponseEntity result = controller.getAllProducts();

    assertEquals(ResponseEntity.ok(productList), result);
  }

  @Test
  public void testGetProductByIdSuccess(){
    Product product = new Product();

    when(service.findById(anyInt())).thenReturn(product);
    ResponseEntity result = controller.getProductById(anyInt());

    assertEquals(ResponseEntity.ok().body(product), result);
  }

  @Test
  public void testGetProductByIdFail(){
    when(service.findById(anyInt())).thenReturn(null);
    ResponseEntity result = controller.getProductById(anyInt());

    assertEquals(ResponseEntity.badRequest().body(PRODUCT_NULL), result);
  }
}
