package org.hsnr.rest.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hsnr.rest.util.FileUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

  @Id
  @GenericGenerator(name = "id", strategy = "increment")
  @GeneratedValue(generator = "id")
  private int id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(columnDefinition = "BLOB NOT NULL")
  private byte[] imageByteArray;

  @Column(nullable = false)
  private double price;

  @Column(nullable = false)
  private int stockAmount;

  @Column(nullable = false)
  private Category category;

  public Product() {
    // ORM constructor
    if(imageByteArray == null){
      imageByteArray = FileUtil.getDefaultImage();
    }
  }

  public Product(String name, String description, byte[] imageByteArray, double price, int stockAmount, Category category) {
    this.name = name;
    this.description = description;
    this.imageByteArray = imageByteArray;
    this.price = price;
    this.stockAmount = stockAmount;
    this.category = category;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public byte[] getImageByteArray() {
    return imageByteArray;
  }

  public void setImageByteArray(byte[] imageByteArray) {
    this.imageByteArray = imageByteArray;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getStockAmount() {
    return stockAmount;
  }

  public void setStockAmount(int stockAmount) {
    this.stockAmount = stockAmount;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}
