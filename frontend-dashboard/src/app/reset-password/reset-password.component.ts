import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ActivatedRoute, Router, RouterModule} from "@angular/router";
import {AuthService} from "../core/services/auth.service";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    RouterModule,
    ReactiveFormsModule
  ],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.css'
})
export class ResetPasswordComponent implements OnInit {

  token: string = '';
  newPassword: string = '';
  confirmPassword: string = '';
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private authService: AuthService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    // Récupérer le token à partir de l'URL
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
      if (!this.token) {
        this.errorMessage = 'Aucun token trouvé dans l\'URL.';
      }
    });
  }

  onSubmit(): void {
    if (this.token && this.newPassword && this.confirmPassword === this.newPassword) {
      this.authService.resetPassword(this.token, this.newPassword).subscribe(
        (response: any) => {
          this.successMessage = 'Votre mot de passe a été réinitialisé avec succès !';
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        },
        (error) => {
          console.error('Erreur lors de la réinitialisation du mot de passe:', error);
          if (error.status === 400) {

            this.errorMessage = error.error;

          } else {

            this.errorMessage = 'Une erreur inattendue s’est produite. Veuillez réessayer plus tard.';
          }
        }
      );
    } else {
      this.errorMessage = 'Les mots de passe ne correspondent pas ou le formulaire est incomplet.';
    }
  }
}
