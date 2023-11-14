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
  title = 'Orphan Helpers';
  login: string = "";
  canDisplay: string = "";
  logStatus: boolean = false;
  constructor(private cookie: CookieService, private authenticationService: AuthenticationService, private router: Router){
  }

  ngOnInit(): void{
    this.login = this.cookie.get("UserId");
    this.isLoggedIn();
    console.log("Get cookie " + this.login);
    console.log("Get cookie " + this.canDisplay);
  }

  setCookie(login: string) {
    this.login = login;
    this.cookie.set("UserId",this.login);
    console.log("Set cookie " + this.login);
  }
  isLoggedIn() {
    const loggedIn: string = this.getUsername();
  
    if (loggedIn !== "") {
      console.log(this.getUsername)
      console.log("Logged In");
      this.logStatus = true;
      return true;
    } else {
      console.log("Not Logged In");
      this.logStatus = false;
      return false;
    }
  }
  public logout() {
    this.login = "";
    this.cookie.delete("UserId");
    this.router.navigate(['login']);
  }

  public getUsername() {
    return this.login;
  }
  
}
