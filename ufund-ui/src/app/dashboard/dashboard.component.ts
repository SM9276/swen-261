import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../authentication.service';
import { AppComponent } from '../app.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  needs: Need[] = [];

  constructor(
    private needService: NeedService,
    private appComponent: AppComponent,
    ) { }

  ngOnInit(): void {
    this.getNeeds();
  }
  logout(): void{
    this.appComponent.logout();
  }

  getNeeds(): void {
    this.needService.getNeeds()
      .subscribe(needs => this.needs = needs.slice(1, 5));
  }
}