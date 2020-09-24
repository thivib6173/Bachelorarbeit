import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {ProductModelServer} from "../models/product.model";
import { HttpClientModule } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})


export class ProductService {
  private url = "http://localhost:9081";

  constructor(private http: HttpClient) {
  }

  getAllProducts(limitOfResults=10): Observable<ProductModelServer> {
    return this.http.get<ProductModelServer>(this.url + '/product/service' , {
      params: {
        limit: limitOfResults.toString()
      }
    });
  }

  getSingleProduct(id: Number): Observable<ProductModelServer> {
    return this.http.get<ProductModelServer>(this.url + 'product/service' + id);
  }

  getProductsFromCategory(catName: String): Observable<ProductModelServer[]> {
    return this.http.get<ProductModelServer[]>(this.url + 'products/service/' + catName);
  }

}
