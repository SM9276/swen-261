import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { NeedsComponent } from './needs/needs.component';
import { NeedsDetailComponent } from './needs-detail/needs-detail.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NeedsSearchComponent } from './needs-search/needs-search.component';
import { MessagesComponent } from './messages/messages.component';
import { AppRoutingModule } from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    NeedsComponent,
    NeedsDetailComponent,
    DashboardComponent,
    NeedsSearchComponent,
    MessagesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
