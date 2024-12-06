import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8081/api-user/auth';

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    const body = { email, password };
    return this.http.post<any>(`${this.apiUrl}/login`, body, { responseType: 'text' as 'json' });
  }

  saveToken(token: string): void {
    localStorage.setItem('authToken', token);
  }

  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    localStorage.removeItem('authToken');
  }

  // appel de la méthode pour l'envoi du lien de réinitialisation du mot de passe
  forgotPassword(email: string): Observable<any> {
    const payload = { email };
    return this.http.post<any>(`${this.apiUrl}/forgot-password`, payload);
  }

  // appel de la méthode pour la réinitialisation du mdp
  resetPassword(token: string, newPassword: string) {
    const url = `${this.apiUrl}/reset-password?token=${token}`;
    return this.http.post(url, { newPassword }).pipe(

      catchError(error => {
        // On lance l'erreur pour que le composant puisse la gérer
        return throwError(() => error);
      })
    );
  }
}
