import {Category} from "./category";

export class Product {

  constructor(name: string, description: string, price: number, stockAmount: number, category: Category) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.stockAmount = stockAmount;
    this.category = category;
  }

  id: number;
  name: string;
  description: string;
  image: any[];  // supposed to be image byte array
  price: number;
  stockAmount: number;
  category: Category;
}
