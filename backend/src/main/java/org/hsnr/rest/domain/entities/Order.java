package org.hsnr.rest.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDER_TB")
public class Order {

  @Id
  @GenericGenerator(name = "id", strategy = "increment")
  @GeneratedValue(generator = "id")
  private int id;

  @Column(nullable = false)
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date invoiceDate;

  @OneToMany(targetEntity = Product.class)
  private List<Product> productList;

  public Order() {
    invoiceDate = new Date();
  }

  public Order(List<Product> productList){
    invoiceDate = new Date();
    this.productList = productList;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public List<Product> getProductList() {
    return productList;
  }

  public void setProductList(List<Product> productList) {
    this.productList = productList;
  }
}
