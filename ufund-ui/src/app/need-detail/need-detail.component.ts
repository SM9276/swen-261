
import { Need } from '../need';
import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { UserService } from '../user.service';

import { AppComponent } from '../app.component';


import { NeedService } from '../need.service';
import { User } from '../user';
import { AppComponent } from '../app.component';
import { shoppingCart } from '../shoppingCart';
@Component({
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrls: ['./need-detail.component.css']
})
export class NeedDetailComponent {
  constructor(
    private route: ActivatedRoute,
    private needService: NeedService,
    private location: Location,

    private userService: UserService,

    private appComponent: AppComponent
  ) {}
  @Input() need?: Need;
  @Input() needs: Need[] = [];
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
  save(): void {
    if (this.need) {
      this.needService.updateNeed(this.need)
        .subscribe(() => this.goBack());
    }
  }

  addToCart(): void{
    if (this.needs) {
      const username: String = (this.appComponent.login).trim();
      const needs: Need[]= this.needs;
      this.userService.addToShoppingCart({username, needs} as shoppingCart).subscribe(() => this.goBack());
    }


  getUsername(): String {
    return this.appComponent.getUsername();

  }
}
