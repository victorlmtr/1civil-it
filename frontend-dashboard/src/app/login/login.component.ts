import { Component } from '@angular/core';
import { AuthService } from "../core/services/auth.service";
import { Router } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    CommonModule,
    RouterModule
  ],
  standalone: true,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css' // Correction ici
})
export class LoginComponent {

  email: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    if (this.email && this.password) {
      this.authService.login(this.email, this.password).subscribe(
        (response: any) => {
          console.log('Réponse du serveur (token) :', response);  // Vérifie la réponse dans la console
          this.authService.saveToken(response);  // Sauvegarde le token
          this.router.navigate(['/dashboard']);
        },
        (error) => {
          console.error('Erreur lors de la connexion:', error);  // Affichage d'erreur détaillée
          this.errorMessage = 'Identifiants incorrects, veuillez réessayer.';
        }
      );
    } else {
      this.errorMessage = 'Veuillez remplir tous les champs.';
    }
  }

}
