package org.hsnr.rest.api.controllers;

import org.hsnr.rest.domain.dao.OrderDaoService;
import org.hsnr.rest.domain.entities.Order;
import org.hsnr.rest.domain.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("order")
public class OrderRestController {

  public static final String ORDER_CREATED = "Your order was created successfully.";

  public static final String ORDER_DELETED = "Order successfully deleted.";

  public static final String ORDER_FAILED = "Your order couldn't be created.";

  public static final String ORDER_NULL = "This order does not exist.";

  public static final String ORDER_UPDATED = "Product successfully updated.";

  private OrderDaoService orderDaoService;

  @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json")
  public ResponseEntity createOrder(@RequestBody Order order) {
    List<Product> productList = order.getProductList();
    if (!productList.isEmpty()) {
      order.setInvoiceDate(new Date());
      orderDaoService.persist(order);
      return ResponseEntity.ok().body(ORDER_CREATED);
    } else {
      return ResponseEntity.badRequest().body(ORDER_FAILED);
    }
  }

  @RequestMapping(value = "/orders", method = RequestMethod.GET)
  public ResponseEntity getAllOrders() {                        // needs to be restricted for admins only
    List<Order> orderList = orderDaoService.getAllOrders();
    return ResponseEntity.ok(orderList);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity getOrderById(@PathVariable("id") int id) { // needs to be restricted for admins or the user who created the order
    Order order = orderDaoService.findById(id);
    if (order != null) {
      return ResponseEntity.ok(order);
    } else {
      return ResponseEntity.badRequest().body(ORDER_NULL);
    }
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public ResponseEntity deleteOrder(@PathVariable("id") int id) { // needs to be restricted for admins or the user who created the order
    if (orderDaoService.findById(id) != null) {
      orderDaoService.deleteById(id);
      return ResponseEntity.ok().body(ORDER_DELETED);
    } else {
      return ResponseEntity.badRequest().body(ORDER_NULL);
    }
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public ResponseEntity updateOrder(@PathVariable Order order) {  // needs to be restricted for admins or the user who created it
    List<Product> orderList = order.getProductList();
    if (!orderList.isEmpty()) {
      orderDaoService.update(order);
      return ResponseEntity.ok().body(ORDER_UPDATED);
    } else {
      int id = order.getId();
      deleteOrder(id);
      return ResponseEntity.ok().body(ORDER_DELETED);
    }
  }

  public void setOrderDaoService(OrderDaoService orderDaoService) {
    this.orderDaoService = orderDaoService;
  }

}
