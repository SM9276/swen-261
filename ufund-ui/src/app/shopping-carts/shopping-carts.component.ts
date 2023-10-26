import { Component } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { UserService } from '../user.service';
import { shoppingCart } from '../shoppingCart';
import { AppComponent } from '../app.component';
@Component({
  selector: 'app-shopping-carts',
  templateUrl: './shopping-carts.component.html',
  styleUrls: ['./shopping-carts.component.css']
})
export class ShoppingCartsComponent {
  shoppingCarts: shoppingCart[] = [];
  needs: Need[] = [];

  constructor(private userService: UserService, private appComponent: AppComponent) { }

  ngOnInit(): void {
    this.getShoppingCart();
  }

  getShoppingCart(): void {
    this.userService.getShoppingCart(this.appComponent.login)
    .subscribe(shoppingCarts => this.shoppingCarts = shoppingCarts);
  }

  add(username: String, neeD: Need[]): void {
    username = username.trim();
    const needs = neeD;

    if (!username) { return; }
    console.log(this.userService.addToShoppingCart({username, needs} as shoppingCart));
    this.userService.addToShoppingCart({username, needs} as shoppingCart)
      .subscribe(shoppingCart => {
        this.shoppingCarts.push(shoppingCart);
      });
  }

}
