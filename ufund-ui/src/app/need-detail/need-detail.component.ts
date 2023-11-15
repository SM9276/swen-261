import { Need } from '../need';
import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Router, NavigationExtras } from '@angular/router';
import { UserService } from '../user.service';

import { AppComponent } from '../app.component';


import { NeedService } from '../need.service';
import { User } from '../user';
import { FundingBasket } from '../FundingBasket';
import { Observable } from 'rxjs';
@Component({
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrls: ['./need-detail.component.css']
})
export class NeedDetailComponent {
  fundingBaskets: FundingBasket[] = [];
  need!: Need;
  constructor(
    private route: ActivatedRoute,
    private needService: NeedService,
    private location: Location,

    private userService: UserService,

    private appComponent: AppComponent,
    private router :Router,
  ) {}
  ngOnInit(): void {
    this.getNeed();
  }
  
  getNeed(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.needService.getNeed(id)
      .subscribe(need => this.need = need);
  }
  goBack(): void {
    this.location.back();
  }
  logout(): void{
    this.appComponent.logout();
  }
  save(): void {
    if (this.need) {
      this.needService.updateNeed(this.need)
        .subscribe(() => this.goBack());
    }
  }

  addToBasket(): void{
    if(this.appComponent.getUsername()){
      if (this.need) {
        const username: String = (this.appComponent.login).trim();
        const basket = this.userService.getFundingBasket(username);
        const id = Number(this.route.snapshot.paramMap.get('id'));
        basket.subscribe((fundingBasket) => {this.fundingBaskets.push(fundingBasket);
        console.log(fundingBasket);
        this.needService.getNeed(id).subscribe(need => this.need = need);
        console.log(this.need);
        console.log(this.need.quantity);
        console.log(this.fundingBaskets[0].needs.length);
        console.log(this.needInBasket());
        if(this.fundingBaskets[0].needs.length == 0){
          this.fundingBaskets[0].needs.push(this.need);
          console.log(this.fundingBaskets[0].needs);
        }
        else if(this.needInBasket()){
          this.fundingBaskets[0].needs.forEach((need) => {
            if(this.need.id == need.id){
              need.quantity = Number(need.quantity) + Number(this.need.quantity);
            }
          });
        }
        else {
          this.fundingBaskets[0].needs.push(this.need);
          console.log(this.fundingBaskets[0].needs);
        }
        this.userService.updateFundingBasket(this.fundingBaskets[0]).subscribe(() => this.goBack());
      });
      }
    }else{
      this.router.navigate(['login']);
      window.alert('Please Login before adding item to basket!');
    }
  }
  getUsername(): String {
    return this.appComponent.getUsername();

  }
  needInBasket(): boolean {
    let result: boolean = false;
    const username: String = (this.appComponent.login).trim();
    const basket = this.userService.getFundingBasket(username);
    basket.subscribe((fundingBasket) => {this.fundingBaskets.push(fundingBasket);
      });
      this.fundingBaskets[0].needs.forEach((need) => {
        if(this.need.id == need.id){
          result = this.need.id == need.id;
        }
      });
      console.log(result);
      return result
  }
}