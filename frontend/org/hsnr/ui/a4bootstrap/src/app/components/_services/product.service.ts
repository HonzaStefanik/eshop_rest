import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

import { Product } from  '../_models/models.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import {Category} from "../_models/category";



@Injectable()
export class ProductService {
  category = Category;



  constructor(private http: Http) { }

  getAll(): Observable<Product[]>{
  return this.http.get('http://localhost:8080/product/products')
                    .map(this.extractData)
                    .catch(this.handleError);
  }

  save(product: Product){
    return this.http.post('http://localhost:8080/product/create', product);
  }

  private extractData(res:Response) {
    let body = res.json();
    return body || [];
  }

  private handleError(error:any) {
    let errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg);
    return Observable.throw(errMsg);
  }

  categoryKeys(): Array<string> {
    let keys = Object.keys(this.category);
    return keys.slice(keys.length / 2);
  }

}
