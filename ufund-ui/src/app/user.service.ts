import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

// import { shoppingCart } from './shoppingCart';
import { MessageService } from './message.service';
import { Need } from './need';
import { FundingBasket } from './FundingBasket';


@Injectable({ providedIn: 'root' })
export class UserService {

  private fundingBasketUrl = 'http://localhost:8080/funding-basket';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

    updateNeed(need: Need): Observable<any> {
      const url = `${this.needsUrl}/${need.id}`;
      return this.http.put(this.needsUrl, need, this.httpOptions).pipe(
        tap(_ => this.log(`updated need cost=${need.id}`)),
        catchError(this.handleError<any>('updateNeed'))
      );
    }  
      /** GET need by id. Will 404 if id not found */
  getFundingBasket(name: String): Observable<FundingBasket> {
    const url = `${this.fundingBasketUrl}/${name}`;
    return this.http.get<FundingBasket>(url).pipe(
      tap(_ => this.log(`fetched need name=${name}`)),
      catchError(this.handleError<FundingBasket>(`getFundingBasket name=${name}`))
    );
  }


  makeFundingBasket(fundingBasket: FundingBasket): Observable<FundingBasket> {
    const {username, needs} = fundingBasket;
    return this.http.post<FundingBasket>(`${this.fundingBasketUrl}/funding-basket`, fundingBasket, this.httpOptions).pipe(
      catchError(this.handleError<FundingBasket>('makeFundingBasket'))
    );
  }

    /** DELETE: delete the need from the server */
    // deleteNeed(id: number): Observable<Need> {
    //   const url = `${this.needsUrl}/${id}`;
  
    //   return this.http.delete<Need>(url, this.httpOptions).pipe(
    //     tap(_ => this.log(`deleted need cost=${id}`)),
    //     catchError(this.handleError<Need>('deleteNeed'))
    //   );
    // }
      /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
    /** Log a NeedService message with the MessageService */
    private log(message: string) {
      this.messageService.add(`NeedService: ${message}`);
    }
    
}