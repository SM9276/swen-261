import { Component } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { UserService } from '../user.service';
import { FundingBasket } from '../FundingBasket';
import { AppComponent } from '../app.component';
@Component({
  selector: 'app-funding-baskets',
  templateUrl: './funding-baskets.component.html',
  styleUrls: ['./funding-baskets.component.css']
})
export class fundingBasketsComponent {
  fundingBaskets: FundingBasket[] = [];
  needs: Need[] = [];

  constructor(private userService: UserService, private appComponent: AppComponent) { }

  ngOnInit(): void {
    this.getFundingBasket();
  }

  logout(): void{
    this.appComponent.logout();
  }
  
  getFundingBasket(): void {
    const username: String = (this.appComponent.login).trim();
    console.log(username);
    const basket = this.userService.getFundingBasket(username);
    console.log(basket);
    basket.subscribe((fundingBasket) => {this.fundingBaskets.push(fundingBasket);
      console.log(fundingBasket);
      this.needs = this.fundingBaskets[0].needs;
  });
  
  }
}
