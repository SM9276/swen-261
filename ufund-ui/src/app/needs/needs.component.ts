import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { AppComponent } from '../app.component';
import { User } from '../user';

@Component({
  selector: 'app-needs',
  templateUrl: './needs.component.html',
  styleUrls: ['./needs.component.css']
})
export class NeedsComponent implements OnInit {
  needs: Need[] = [];

  constructor(private needService: NeedService, private appComponent: AppComponent) { }

  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.needService.getNeeds()
    .subscribe(needs => this.needs = needs);
  }

  logout(): void{
    this.appComponent.logout();
  }
  
  add(name: string,needPrice: string, needQuantity: string): void {
    name = name.trim();
    const price:number = parseFloat(needPrice);
    const quantity:number = parseFloat(needQuantity);
    console.log(price)
    console.log(quantity)
    if (price<0 || quantity<0 ){
      window.alert('Invalid Price or Quantity');
    }
    else{
      if (!name && !needPrice && !needQuantity) { return; }
      console.log(this.needService.addNeed({name,price,quantity} as Need));
      this.needService.addNeed({name,price,quantity} as Need)
        .subscribe(need => {
          this.needs.push(need);
        });
    }
  }

  delete(need: Need): void {
    console.log(1)
    this.needs = this.needs.filter(h => h !== need);
    this.needService.deleteNeed(need.id).subscribe();
  }

  getUsername(): String {
    return this.appComponent.getUsername();
  }
}
