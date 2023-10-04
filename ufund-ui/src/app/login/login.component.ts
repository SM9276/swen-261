import { Component } from '@angular/core';
import { Username } from '../username';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: Username[] = [];
  
}
