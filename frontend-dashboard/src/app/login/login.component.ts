import { Component } from '@angular/core';
import { AuthService } from "../core/services/auth.service";
import { Router } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
declare let bootstrap: any;

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    CommonModule,
    RouterModule
  ],
  standalone: true,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  email: string = '';
  password: string = '';
  errorMessage: string = '';
  forgotEmail: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    if (this.email && this.password) {

      this.authService.login(this.email, this.password).subscribe(

        (response: any) => {
          this.authService.saveToken(response);  // Sauvegarde le token
          this.router.navigate(['/dashboard']);
        },
        (error) => {

          console.error('Erreur lors de la connexion:', error);

          // Récupération du message d'erreur renvoyé par le backend
          if (error.status === 403 || error.status === 401 || error.status === 400 || error.status === 404 || error.status === 409) {

            this.errorMessage = error.error;

          } else {

            this.errorMessage = 'Une erreur inattendue s’est produite. Veuillez réessayer plus tard.';
          }
        }
      );
    } else {

      this.errorMessage = 'Veuillez remplir tous les champs.';
    }
  }

  openForgotPasswordModal() {
    const modal = new bootstrap.Modal(document.getElementById('forgotPasswordModal')!);
    modal.show();
  }

  // Soumettre la réinitialisation du mot de passe
  onForgotPasswordSubmit() {
    this.authService.forgotPassword(this.forgotEmail).subscribe({
      next: () => {
        // Afficher l'alerte de succès
        const alertElement = document.getElementById('successAlert');
        if (alertElement) {
          alertElement.classList.remove('d-none');
          alertElement.classList.add('show');
        }

        // Fermer la modale
        const modalElement = document.getElementById('forgotPasswordModal');
        if (modalElement) {
          const modal = bootstrap.Modal.getInstance(modalElement);
          modal?.hide();
        }

        // Masquer l'alerte après 3 secondes
        setTimeout(() => {
          if (alertElement) {
            alertElement.classList.remove('show');
            alertElement.classList.add('d-none');
          }
        }, 3000);
      },
      error: (error) => {
        alert("Erreur : " + (error.error.message || "Une erreur est survenue."));
      }
    });
  }

}
