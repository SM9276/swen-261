import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { MessageService } from './message.service';
import { User } from './user';
import { AppComponent } from './app.component';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private userUrl = 'http://localhost:8080/user';
  

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient, private messageService: MessageService) {}

  login(user: User): Observable<User> {
    const url = `${this.userUrl}/login?id=` + user.username;
    return this.http.get<User>(url).pipe(
      catchError(this.handleError<User>(`getUser id=${user.username}`))
    );
  }

  register(user: User): Observable<User> {
    const {username, password} = user;
    console.log(user);
    return this.http.post<User>(`${this.userUrl}/register`, user, this.httpOptions).pipe(
      catchError(this.handleError<User>('updateUser'))
    );
  }

  private handleError<T>(operation = 'operation') {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption

      // Let the app keep running by returning an empty result.
      return of(error as T);
    };
  }
  searchUsers(term: String): Observable<User[]> {
    console.log(term);
    return this.http.get<User[]>(`${this.userUrl}/?user=${term}`).pipe(tap(x => x.length),
      catchError(this.handleError<User[]>('searchUsers'))
    );
  }
}
