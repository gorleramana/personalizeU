import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PersonalizeService } from '../../services/personalize.service';

@Component({
  selector: 'app-rg-login',
  templateUrl: './rg-login.component.html',
  styleUrl: './rg-login.component.css'
})
export class RgLoginComponent {
  loginForm: FormGroup;
  registrationSuccess = '';
  isSubmitting = false;
  successMessage = '';
  errorMessage = '';

  constructor(private fb: FormBuilder, private router: Router, private personalizeService: PersonalizeService) {

    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', Validators.required],
      remember: [false]
    });
  }

  ngOnInit() {
    try {
      const msg = sessionStorage.getItem('registration_success');
      if (msg) {
        this.registrationSuccess = msg;
        sessionStorage.removeItem('registration_success');
      }
    } catch (e) { }
  }

  onSubmit() {
    // if (this.loginForm.invalid) {
    //   this.loginForm.markAllAsTouched();
    //   return;
    // }
    console.log('Login form submitted', this.loginForm.value);
    if (this.loginForm.invalid) {
      // trigger validation error messages in the template
      this.loginForm.markAllAsTouched();
      return;
    }

    if (!this.isSubmitting) {
      this.isSubmitting = true;
      this.successMessage = '';
      this.errorMessage = '';
      const fv = this.loginForm.value;
      const payload = {
        username: fv.username,
        password: fv.password
      };
  
         this.personalizeService.validateUser(payload).subscribe({
        next: res => {
          console.log('Login successful', res);
          this.successMessage = 'User login successful, welcome home.';
          this.isSubmitting = false;
          // store user details for profile update
          try { 
            sessionStorage.setItem('login_success', this.successMessage);
            sessionStorage.setItem('user_details', JSON.stringify(res));
          } catch (e) {}
          // navigate after brief pause so user sees the message
          setTimeout(() => this.router.navigate(['/home']), 1500);
        },
        error: err => {
          console.error('Login failed', err);
          this.errorMessage = (err && err.error && err.error.message) ? err.error.message : 'Login failed. Please try again.';
          this.isSubmitting = false;
        }
      });
      // const { username, password } = this.loginForm.value;
      // this.personalizeService.validateUser({ username, password }).subscribe(
      //   response => {
      //     console.log('User validated:', response);
      //     this.router.navigate(['/home']);
      //   },
      //   error => {
      //     console.error('Validation failed:', error);
      //   }
      // );
    }
  }

  registerUser() {
    this.router.navigate(['/register']);
  }

  forgotPassword() {
    this.router.navigate(['/forgotpwd']);
  }
}
