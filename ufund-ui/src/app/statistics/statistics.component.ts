
import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent {
  needs: Need[] = [];
  top: Need[] = [];
  need!: Need;

  constructor(private needService: NeedService, private appComponent: AppComponent) { }

  ngOnInit(): void {
    this.getTopNeeds();
  }

  getTopNeeds(): void {
    let top_1: number = 0;
    this.needService.getNeeds().subscribe((needs) => {this.needs = needs
      this.needs.forEach((need) => {
        if(need.amount > top_1){
          top_1 = need.amount;
          this.need = need;
        }
      });
      this.top.push(this.need);
      console.log(this.top);
    });
  }

  logout(): void{
    this.appComponent.logout();
  }
  
  getUsername(): String {
    return this.appComponent.getUsername();
  }
}
