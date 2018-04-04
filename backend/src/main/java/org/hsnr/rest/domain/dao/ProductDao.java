package org.hsnr.rest.domain.dao;

import org.hibernate.query.Query;
import org.hsnr.rest.domain.entities.Product;

import javax.persistence.NoResultException;
import java.util.List;

public class ProductDao extends AbstractDao {

  Product findById(int id) {
    return getCurrentSession().get(Product.class, id);
  }

  Product findByName(String name) {
    Query query = getCurrentSession().createQuery("from Product p where p.name = :name");
    query.setParameter("name", name);

    try {
      return (Product) query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  void persist(Product entity) {
    getCurrentSession().save(entity);
  }

  void update(Product entity) {
    getCurrentSession().update(entity);
  }

  void delete(Product entity) {
    getCurrentSession().delete(entity);
  }

  List<Product> getAllProducts() {
    return (List<Product>) getCurrentSession().createQuery("from Product").list();
  }

}
