import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {ProductService} from '../../services/product.service';
import {ProductModelServer} from '../../models/product.model';
import {map} from 'rxjs/operators';
import {CartService} from '../../services/cart.service';



declare let $: any;

@Component({
  selector: 'mg-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements AfterViewInit, OnInit {

  constructor(private route: ActivatedRoute,
              private productService: ProductService,
              private cartService: CartService) {


  }
  authRole: any;
  id: number;
  product;
  thumbimages: any[] = [];


  @ViewChild('quantity') quantityInput;

    ngAfterViewInit(): void {
        throw new Error('Method not implemented.');
    }

  ngOnInit(): void {
   console.log(this.route.url);
  }



  //   // Product img zoom
  //   var zoomMainProduct = document.getElementById('product-main-img');
  //   if (zoomMainProduct) {
  //     $('#product-main-img .product-preview').zoom();
  //   }
  // }

  // addToCart(id: Number) {
  //   this.cartService.AddProductToCart(id, this.quantityInput.nativeElement.value);
  // }

  // Increase() {
  //   let value = parseInt(this.quantityInput.nativeElement.value);
  //   if (this.product.quantity >= 1){
  //     value++;

  //     if (value > this.product.quantity) {
  //       // @ts-ignore
  //       value = this.product.quantity;
  //     }
  //   } else {
  //     return;
  //   }

  //   this.quantityInput.nativeElement.value = value.toString();
  // }

  // Decrease() {
  //   let value = parseInt(this.quantityInput.nativeElement.value);
  //   if (this.product.quantity > 0){
  //     value--;

  //     if (value <= 0) {
  //       // @ts-ignore
  //       value = 0;
  //     }
  //   } else {
  //     return;
  //   }
  //   this.quantityInput.nativeElement.value = value.toString();
  // }
}
