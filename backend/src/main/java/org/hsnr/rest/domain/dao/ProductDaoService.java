package org.hsnr.rest.domain.dao;

import org.hsnr.rest.domain.entities.Product;

import java.util.List;

public class ProductDaoService {

  private ProductDao dao;

  public void persist(Product entity) {
    dao.openCurrentSessionWithTransaction();
    dao.persist(entity);
    dao.closeCurrentSessionwithTransaction();
  }

  public void update(Product entity) {
    dao.openCurrentSessionWithTransaction();
    dao.update(entity);
    dao.closeCurrentSessionwithTransaction();
  }

  public Product findById(int id) {
    dao.openCurrentSession();
    Product product = dao.findById(id);
    dao.closeCurrentSession();
    return product;
  }

  public void deleteById(int id) {
    dao.openCurrentSessionWithTransaction();
    Product product = dao.findById(id);
    dao.delete(product);
    dao.closeCurrentSessionwithTransaction();
  }

  public Product findByName(String name) {
    dao.openCurrentSession();
    Product product = dao.findByName(name);
    dao.closeCurrentSession();
    return product;
  }


  public List<Product> getAllProducts() {
    dao.openCurrentSession();
    List<Product> products = dao.getAllProducts();
    dao.closeCurrentSession();
    return products;
  }

  public void setDao(ProductDao dao) {
    this.dao = dao;
  }

  public ProductDao getDao() {
    return dao;
  }
}
