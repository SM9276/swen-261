
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
  
  getUsername(): String {
    return this.appComponent.getUsername();
  }
}
