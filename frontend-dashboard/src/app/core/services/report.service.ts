import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private apiUrl = `http://localhost/8082/api/reports/reports`; // Ton URL de l'API

  constructor(private http: HttpClient) { }

  // Récupérer tous les signalements
  getAllReports(): Observable<ReportDto[]> {
    return this.http.get<ReportDto[]>(this.apiUrl);
  }
}
