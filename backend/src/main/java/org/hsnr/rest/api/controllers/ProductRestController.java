package org.hsnr.rest.api.controllers;

import org.hsnr.rest.domain.dao.ProductDaoService;
import org.hsnr.rest.domain.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductRestController {

  public static final String PRODUCT_NULL = "This product does not exist.";

  public static final String PRODUCT_DELETED = "Product successfully deleted.";

  public static final String PRODUCT_UPDATED = "Product successfully updated.";

  public static final String NAME_TAKEN = "A product with this name already exists.";

  private ProductDaoService productDaoService;

  @RequestMapping(value = "/products", method = RequestMethod.GET)
  public ResponseEntity getAllProducts() {
    List<Product> productList = productDaoService.getAllProducts();
    return ResponseEntity.ok(productList);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity getProductById(@PathVariable("id") int id) {
    Product product = productDaoService.findById(id);
    if (product != null) {
      return ResponseEntity.ok(product);
    } else {
      return ResponseEntity.badRequest().body(PRODUCT_NULL);
    }
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ResponseEntity createProduct(@RequestBody Product product) {  // needs to be restricted for admins only
    String name = product.getName();
    Product dbProduct = productDaoService.findByName(name);
    if (dbProduct != null) {
      return ResponseEntity.unprocessableEntity().body(NAME_TAKEN);
    }
    productDaoService.persist(product);
    return ResponseEntity.ok(product);
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public ResponseEntity deleteProduct(@PathVariable("id") int id) { // needs to be restricted for admins only
    if (productDaoService.findById(id) != null) {
      productDaoService.deleteById(id);
      return ResponseEntity.ok().body(PRODUCT_DELETED);
    } else {
      return ResponseEntity.badRequest().body(PRODUCT_NULL);
    }
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public ResponseEntity updateProduct(@PathVariable Product product) {  // needs to be restricted for admins only
    String name = product.getName();
    Product dbProduct = productDaoService.findByName(name);
    if (dbProduct == null) {
      productDaoService.update(product);
      return ResponseEntity.ok().body(PRODUCT_UPDATED);
    } else {
      return ResponseEntity.unprocessableEntity().body(NAME_TAKEN);
    }
  }

  public void setProductDaoService(ProductDaoService productDaoService) {
    this.productDaoService = productDaoService;
  }
}
