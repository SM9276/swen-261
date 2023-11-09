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
  fundingBasket?: FundingBasket;
  needs: Need[] = [];

  constructor(private userService: UserService, private appComponent: AppComponent) { }

  ngOnInit(): void {
    this.getFundingBasket();
  }

  logout(): void{
    this.appComponent.logout();
  }
  
  getFundingBasket(): void {
    this.userService.getFundingBasket(this.appComponent.login)
    .subscribe(fundingBaskets => this.fundingBasket = fundingBaskets);
  }

}
