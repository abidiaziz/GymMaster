import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, Signal, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../components/users/users.component';

const BASE_URL = ["http://localhost:8080/"]

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  isLoggedIn = signal(false);
  isAdmin = signal(false);
  selectedUser = signal<User | undefined>(undefined);

  constructor(private http: HttpClient) { 
    const jwtToken = localStorage.getItem('jwt');
    const role = localStorage.getItem('role');
    this.isAdmin.set(role === 'admin');
    this.isLoggedIn.set(!!jwtToken);
  }

  register(signRequest: any): Observable<any> {
    return this.http.post(BASE_URL + 'signup', signRequest, {
      headers: this.createAuhtorizationHeader()
    })
  }

  login(loginRequest: any): Observable<any> {
    return this.http.post(BASE_URL + 'login', loginRequest)
  }

  hello(): Observable<any> {
    return this.http.get(BASE_URL + 'api/users', {
      headers: this.createAuhtorizationHeader()
    })
  }

  getClasses(): Observable<any> {
    return this.http.get(BASE_URL+ 'api/classes', {
      headers: this.createAuhtorizationHeader()
    });
  }

  getUserById(id: string): Observable<any> {
    return this.http.get(BASE_URL+ 'api/users/'+id, {
      headers: this.createAuhtorizationHeader()
    });
  }

  createClass(data: any): Observable<any> {
    return this.http.post(BASE_URL + 'api/classes', data, {
      headers: this.createAuhtorizationHeader()
    })
  }

  private createAuhtorizationHeader() {
    const jwtToken = localStorage.getItem('jwt');
    if (jwtToken) {
      console.log("JWT token found in local storage", jwtToken);
      this.isLoggedIn.set(true);
      return new HttpHeaders().set(
        "Authorization", "Bearer " + jwtToken
      )
    } else {
      this.isLoggedIn.set(false);
      console.log("JWT token not found in local storage");
    }
    return null;
  }

}
