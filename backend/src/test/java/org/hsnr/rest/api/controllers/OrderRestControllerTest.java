package org.hsnr.rest.api.controllers;

import org.hsnr.rest.domain.entities.Category;
import org.hsnr.rest.domain.entities.Order;
import org.hsnr.rest.domain.entities.Product;
import org.hsnr.rest.domain.dao.OrderDaoService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.hsnr.rest.api.controllers.OrderRestController.*;
import static org.hsnr.rest.api.controllers.ProductRestController.PRODUCT_NULL;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderRestControllerTest {

  @InjectMocks
  private OrderRestController controller = new OrderRestController();

  @Mock
  private OrderDaoService orderService;

  @Test
  public void testCreateOrderSuccess() {
    List<Product> list = new ArrayList<>();
    Product product = new Product();
    list.add(product);
    Order order = new Order(list);

    doNothing().when(orderService).persist(order);
    ResponseEntity result = controller.createOrder(order);

    assertEquals(ResponseEntity.ok().body(ORDER_CREATED), result);
  }

  @Test
  public void testCreateOrderEmptyList() {
    List<Product> list = new ArrayList<>();
    Order order = new Order(list);

    ResponseEntity result = controller.createOrder(order);

    assertEquals(ResponseEntity.badRequest().body(ORDER_FAILED), result);
  }

  @Test
  public void testGetAllOrders() {
    List<Order> productList = new ArrayList<>();

    doReturn(productList).when(orderService).getAllOrders();
    ResponseEntity result = controller.getAllOrders();

    assertEquals(ResponseEntity.ok(productList), result);
  }

  @Test
  public void testGetProductByIdSuccess() {
    Order order = new Order();

    when(orderService.findById(anyInt())).thenReturn(order);
    ResponseEntity result = controller.getOrderById(anyInt());

    assertEquals(ResponseEntity.ok().body(order), result);
  }

  @Test
  public void testGetProductByIdFail() {
    when(orderService.findById(anyInt())).thenReturn(null);

    ResponseEntity result = controller.getOrderById(anyInt());

    assertEquals(ResponseEntity.badRequest().body(ORDER_NULL), result);
  }

  @Test
  public void tesUpdateOrderSuccess(){
    Order order = new Order();
    List<Product> productList = new ArrayList<>();
    productList.add(new Product());
    order.setProductList(productList);

    ResponseEntity result = controller.updateOrder(order);
    assertEquals(ResponseEntity.ok().body(ORDER_UPDATED), result);

    verify(orderService, times(1)).update(order);
  }

  @Test
  public void tesUpdateOrderEmptyList(){
    Order order = new Order();
    List<Product> productList = new ArrayList<>();
    order.setProductList(productList);

    doNothing().when(orderService).deleteById(anyInt());
    when(orderService.findById(anyInt())).thenReturn(order);
    ResponseEntity result = controller.updateOrder(order);

    assertEquals(ResponseEntity.ok().body(ORDER_DELETED), result);
    verify(orderService, times(1)).findById(anyInt());
    verify(orderService, times(1)).deleteById(anyInt());
  }
}
