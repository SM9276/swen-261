import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from '../authentication.service';
import { AppComponent } from '../app.component';
import { User } from '../user';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']//'../app.component.css', 
})
export class LoginComponent {
  loginForm!: FormGroup;
  user: User [] = [];
  balance: number = 0;

  constructor(private http: HttpClient, private router :Router, private appComponent: AppComponent, private authenticationService: AuthenticationService,
    ){
  }

  ngOnInit(): void{
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
    if (this.appComponent.login) {
      this.attemptLogin(this.appComponent.login, '');
    }
  }

  
  onSubmit() {
    if (this.loginForm.valid) {
      const username = this.loginForm.get('username')?.value;
      const password = this.loginForm.get('password')?.value;
      this.attemptLogin(username, password);
    }
  }

  attemptLogin(username: string, password: string) {
    const value = this.authenticationService.login({username,password} as User)
      value.subscribe((name) => {this.user.push(name);
        console.log(name);
        if (this.user != null && this.user[0].username != undefined) {
          console.log(this.user)

          if(this.user[0].password == password){
            this.router.navigate(['dashboard']);
            this.appComponent.canDisplay = "yes";
            this.appComponent.setCookie(username);
          }
          else {
            window.alert("Password Incorrect");
            this.loginForm.reset();
          }
        }
      else {
        window.alert("Please Enter a Valid Username or Register");
        this.loginForm.reset();
      }
    });
  }
}