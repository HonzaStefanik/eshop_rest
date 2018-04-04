package org.hsnr.rest.domain.dao;

import org.hsnr.rest.domain.entities.Order;

import java.util.List;

public class OrderDao extends AbstractDao {

  Order findById(int id) {
    return getCurrentSession().get(Order.class, id);
  }

  void persist(Order entity) {
    getCurrentSession().save(entity);
  }

  void update(Order entity) {
    getCurrentSession().update(entity);
  }

  void delete(Order entity) {
    getCurrentSession().delete(entity);
  }

  List<Order> getAllOrders() {
    return (List<Order>) getCurrentSession().createQuery("from Order").list();
  }
}
