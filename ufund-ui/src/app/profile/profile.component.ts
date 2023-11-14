import { Component } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { UserService } from '../user.service';
import { FundingBasket } from '../FundingBasket';
import { AppComponent } from '../app.component';

import { Router, NavigationExtras } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  fundingBaskets: FundingBasket[] = [];
  needs: Need[] = [];
  empty: Need[] = [];
  example: Need[] = [];
  need!: Need;

  constructor(private router :Router, private userService: UserService, private appComponent: AppComponent, private needService: NeedService) { }

  ngOnInit(): void {
    this.getTopBought();
  }

  logout(): void{
    this.appComponent.logout();
  }
  
  getUsername(): void{
    this.appComponent.getUsername();
  }
  getTopBought(): void{
    let top_1: number = 0;
    const username: String = (this.appComponent.login).trim();
    console.log(username);
    const basket = this.userService.getFundingBasket(username);
    console.log(basket);
    basket.subscribe((fundingBasket) => {this.fundingBaskets.push(fundingBasket);
      console.log(fundingBasket);
      this.fundingBaskets[0].bought.forEach((need) => {
        if(need.quantity > top_1){
          top_1 = need.quantity;
          this.need = need;
        }
      });
      this.needs.push(this.need);
  });
  }
}
