import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { AuthenticationService } from './authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
<<<<<<< HEAD
  title = 'ufund-ui';
  login: string = "";
  canDisplay: string = "";
  constructor(private cookie: CookieService, private authenticationService: AuthenticationService, private router: Router){

  }

  ngOnInit(): void{
    this.login = this.cookie.get("UserId");
    console.log("Get cookie " + this.login);
    console.log("Get cookie " + this.canDisplay);
  }

  setCookie(login: string) {
    this.login = login;
    this.cookie.set("UserId",this.login);
    console.log("Set cookie " + this.login);
  }
  public logout() {
    this.login = "";
    this.cookie.delete("UserId");
    this.router.navigate(['login']);
  }
  
=======
  title = 'ufund';
>>>>>>> 60e77287a7b6cd69617e8695537d10fafc2a8bb8
}
