import {Component, OnInit} from '@angular/core';
import {ProductService} from '../_services/index.service';
import {Product} from "../_models/product";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {
  private productArray: Product[] = [];
  private productFilteredArray: Product[] = [];
  private errorMessage: any = '';


  constructor(private productService: ProductService) {
    this.loadProductsToArray();
  }

  ngOnInit() {
  }

  private loadProductsToArray() {
    this.productService.getAll()
      .subscribe(
        products => this.productArray = products,
        error => this.errorMessage = <any>error);
  }


  getCategories() {
    return this.productService.categoryKeys();
  }
}


