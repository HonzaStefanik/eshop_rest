package org.hsnr.rest.domain.dao;

import org.hsnr.rest.domain.entities.Order;

import java.util.List;

public class OrderDaoService {

  private OrderDao dao;

  public void persist(Order entity) {
    dao.openCurrentSessionWithTransaction();
    dao.persist(entity);
    dao.closeCurrentSessionwithTransaction();
  }

  public void update(Order entity) {
    dao.openCurrentSessionWithTransaction();
    dao.update(entity);
    dao.closeCurrentSessionwithTransaction();
  }

  public Order findById(int id) {
    dao.openCurrentSession();
    Order order = dao.findById(id);
    dao.closeCurrentSession();
    return order;
  }

  public void deleteById(int id) {
    dao.openCurrentSessionWithTransaction();
    Order order = dao.findById(id);
    dao.delete(order);
    dao.closeCurrentSessionwithTransaction();
  }

  public List<Order> getAllOrders() {
    dao.openCurrentSession();
    List<Order> orders = dao.getAllOrders();
    dao.closeCurrentSession();
    return orders;
  }

  public void setDao(OrderDao dao) {
    this.dao = dao;
  }

  public OrderDao getDao() {
    return dao;
  }
}
