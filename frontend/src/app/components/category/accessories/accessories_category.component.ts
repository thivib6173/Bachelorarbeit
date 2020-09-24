import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {ProductService} from "../../../services/product.service";
import {CartService} from "../../../services/cart.service";
import {map} from "rxjs/operators";

@Component({
  selector: 'mg-category',
  templateUrl: './accessories_category.component.html',
  styleUrls: ['./accessories_category.component.scss']
})
export class Accessories_categoryComponent implements OnInit {

  id: Number;
  product;

  constructor(private route: ActivatedRoute,
              private productService: ProductService,
              private cartService: CartService) {
  }

  ngOnInit(): void {
    this.route.paramMap.pipe(
      map((param: ParamMap) => {
        // @ts-ignore
        return param.params.id;
      })
    ).subscribe(prodId => {
      this.id = prodId;
      this.productService.getAllProducts().subscribe(prod => {
        this.product = prod;

      })
    })

    this.productService.getProductsFromCategory("asd").subscribe(prod => {
      this.product = prod;

    });

  }

}
