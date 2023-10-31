import { Component } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { UserService } from '../user.service';
import { fundingBasket } from '../fundingBasket';
import { AppComponent } from '../app.component';
@Component({
  selector: 'app-funding-baskets',
  templateUrl: './funding-baskets.component.html',
  styleUrls: ['./funding-baskets.component.css']
})
export class fundingBasketsComponent {
  fundingBaskets: fundingBasket[] = [];
  needs: Need[] = [];

  constructor(private userService: UserService, private appComponent: AppComponent) { }

  ngOnInit(): void {
    this.getFundingBasket();
  }

  getFundingBasket(): void {
    this.userService.getFundingBasket(this.appComponent.login)
    .subscribe(fundingBaskets => this.fundingBaskets = fundingBaskets);
  }

  add(username: String, neeD: Need[]): void {
    username = username.trim();
    const needs = neeD;

    if (!username) { return; }
    console.log(this.userService.addToFundingBasket({username, needs} as fundingBasket));
    this.userService.addToFundingBasket({username, needs} as fundingBasket)
      .subscribe(fundingBasket => {
        this.fundingBaskets.push(fundingBasket);
      });
  }

}
