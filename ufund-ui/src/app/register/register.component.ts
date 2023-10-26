import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { AppComponent } from '../app.component';
import { User } from '../user';
import { AuthenticationService } from '../authentication.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['../app.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  users: User[] = [];

  constructor(private http: HttpClient, private router :Router, private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder){
      this.registerForm = this.formBuilder.group({
        username: ['', Validators.required],
        password: ['', Validators.required]
      });

  }

  ngOnInit(): void{
  }

  attemptRegister() {
    if (this.registerForm.valid) {
      const username = this.registerForm.get('username')?.value;
      const password = this.registerForm.get('password')?.value;
      console.log(username);
      console.log(password);
      this.authenticationService.register({username, password}as User).subscribe(
        (user) => {
          this.users.push(user);
          if (user   != null && this.authenticationService.searchUsers(user.username)) {
            this.router.navigate(['login']);
          } else {
            window.alert('This username is already taken.');
          }
        }
      );
  }
}
}
