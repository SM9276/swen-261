import { Component } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { UserService } from '../user.service';
import { FundingBasket } from '../FundingBasket';
import { AppComponent } from '../app.component';

import { Router, NavigationExtras } from '@angular/router';


@Component({
  selector: 'app-funding-baskets',
  templateUrl: './funding-baskets.component.html',
  styleUrls: ['./funding-baskets.component.css']
})
export class fundingBasketsComponent {
  fundingBaskets: FundingBasket[] = [];
  needs: Need[] = [];
  empty: Need[] = [];
  example: Need[] = [];
  need!: Need;

  constructor(private router :Router, private userService: UserService, private appComponent: AppComponent, private needService: NeedService) { }

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
  checkout(): void{
    if(this.appComponent.getUsername()){
      const username: String = (this.appComponent.login).trim();
      console.log(username);
      const basket = this.userService.getFundingBasket(username);
      console.log(basket);
      basket.subscribe((fundingBasket) => {this.fundingBaskets.push(fundingBasket);
        console.log(fundingBasket);
        console.log(this.fundingBaskets[1]);
        if(this.fundingBaskets[1].bought.length == 0){
          this.fundingBaskets[1].needs.forEach((need) => {
            this.fundingBaskets[1].bought.push(need);
            this.needService.getNeed(need.id).subscribe((need_1) => {this.need = need_1
              this.need.amount = this.need.amount + need.quantity;
              this.needService.updateNeed(this.need).subscribe();
            });
          });
        }
        else{
          this.fundingBaskets[1].needs.forEach((need) => {
            console.log(need);
            this.needService.getNeed(need.id).subscribe((need_1) => {this.need = need_1
              this.need.amount = this.need.amount + need.quantity;
              this.needService.updateNeed(this.need).subscribe();
              console.log(this.need);
            });
            this.fundingBaskets[1].bought.forEach((item) => {
              console.log(item);
              if(item.id == need.id){
                item.quantity += need.quantity;
                
              }
              else{
                this.fundingBaskets[1].bought.push(need);
              }
            });
          });
        }
        this.fundingBaskets[1].needs = this.empty;
        console.log(this.fundingBaskets[1]);
        console.log(this.fundingBaskets[1].username);
        this.userService.updateFundingBasket(this.fundingBaskets[1]).subscribe();
        this.router.navigate(['dashboard']);
        window.alert('Thank you for your donation!');
    });}else{
      this.router.navigate(['login']);
      window.alert('Please Login before adding to funding basket');
    }
  }
  removeNeedFromBasket(need: Need): void {
    const list = this.example;
    this.needs = this.needs.filter(h => h !== need);
    const username: String = (this.appComponent.login).trim();
    console.log(username);
    const basket = this.userService.getFundingBasket(username);
    console.log(basket);
    basket.subscribe((fundingBasket) => {this.fundingBaskets.push(fundingBasket);
      this.fundingBaskets[1].needs.forEach((item) => {
        if(item.id != need.id){
          list.push(item);
        }
      });
      this.fundingBaskets[1].needs = list;
      this.userService.updateFundingBasket(this.fundingBaskets[1]).subscribe();
    });
  }
}
