import { Component } from '@angular/core';
import { MessageService } from '../message.service';
import { AppComponent } from '../app.component';
@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent {
  constructor(public messageService: MessageService, private appComponent: AppComponent) {}

  getUsername(): String {
    return this.appComponent.getUsername();
  }
}

