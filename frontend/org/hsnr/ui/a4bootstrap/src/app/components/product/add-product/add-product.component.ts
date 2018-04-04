import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Product} from "../../_models/product";
import {ProductService} from "../../_services/product.service";
import {AlertService} from "../../_services/alert.service";

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {
  rForm: FormGroup;

  constructor(private productService: ProductService,
              private formBuilder: FormBuilder,
              private alertService: AlertService) {
    this.createForm();
  }

  ngOnInit() {
  }

  private createForm() {
    this.rForm = this.formBuilder.group({
      'name': [null, Validators.required],
      'description': [null, Validators.required],
      'price': [null, Validators.required],
      'stockAmount': [null, Validators.required],
      'category': [null, Validators.required],
    });
  }

  submitProduct(post) {
    let p: Product = new Product(post.name, post.description, post.price, post.stockAmount, post.category);
    this.productService.save(p)
      .subscribe(
        data => {
          this.alertService.success('Product created successfully');
          this.rForm.reset();

        },
        error => {
          console.log(error);
          this.alertService.error(error.text());
        });
  }

  getCategories() {
    return this.productService.categoryKeys();
  }

}
