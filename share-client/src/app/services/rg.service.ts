import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RgService {
  private baseUrl = 'http://localhost:8085'; // Adjust base URL as needed

  constructor(private http: HttpClient) { }

  validateUser(userData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/validate-user`, userData);
  }

  registerUser(userData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/users/register`, userData);
  }
}