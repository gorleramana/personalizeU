import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonalizeService {
  private baseUrl = 'http://localhost:8085'; // Adjust base URL as needed

  constructor(private http: HttpClient) { }

  validateUser(userData: any): Observable<any> {
    // Backend uses POST to accept request body
    return this.http.post(`${this.baseUrl}/users/validate`, userData);
  }

  registerUser(userData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/users/register`, userData);
  }
}