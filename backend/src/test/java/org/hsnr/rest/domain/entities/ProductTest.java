package org.hsnr.rest.domain.entities;

import org.junit.Test;


public class ProductTest {

  @Test
  public void placeHolder() {

  }

  // TODO fix issues with test order

  /*private Product product = new Product("testProduct", "testProduct", "testImgPath", 10, 1, Category.ELECTRONICS);;

  @Test
  public void createProduct() {
    new ProductDaoService().persist(product);
    int id = product.getId();
    assertNotNull(id);
  }

  @Test
  public void updateProduct() {
    String updatedDescription = "updated description test" ;
    product.setDescription(updatedDescription);
    new ProductDaoService().update(product);
    assertEquals(updatedDescription, product.getDescription());
  }


  @Test
  public void getAllProducts() {
    List<Product> productList = new ProductDaoService().getAllProducts();
    int productAmount = productList.size();
    assertThat(productAmount, greaterThan(0));

  }

  @Test
  public void deleteProduct() {
    int id = product.getId();
    new ProductDaoService().deleteById(id);
    assertNull(product);
  }*/
}
