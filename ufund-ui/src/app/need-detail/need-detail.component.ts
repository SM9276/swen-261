
import { Need } from '../need';
import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

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
    if (this.need) {
      const username: String = (this.appComponent.login).trim();
      const basket = this.userService.getFundingBasket(username);
      const id = Number(this.route.snapshot.paramMap.get('id'));
      basket.subscribe((fundingBasket) => {this.fundingBaskets.push(fundingBasket);
      this.needService.getNeed(id).subscribe(need => this.need = need);
      console.log(this.need);
      console.log(this.need.quantity);
      this.fundingBaskets[0].needs.forEach((need) => {
        if(this.need.id == need.id){
          need.quantity += 1;
          console.log(need);
        }
        else {
          this.fundingBaskets[0].needs.push(this.need);
        }
      });
      this.userService.updateFundingBasket(this.fundingBaskets[0]).subscribe(() => this.goBack());
    });
    }
  }

  getUsername(): String {
    return this.appComponent.getUsername();

  }
}
