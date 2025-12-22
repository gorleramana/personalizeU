import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PersonalizeService } from '../../services/personalize.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-rg-register',
  templateUrl: './rg-register.component.html',
  styleUrl: './rg-register.component.css'
})
export class RgRegisterComponent {
  registerForm: FormGroup;
  minDob: string;
  maxDob: string;
  isSubmitting = false;
  successMessage = '';
  errorMessage = '';

  constructor(private fb: FormBuilder, private router: Router, private personalizeService: PersonalizeService, private authService: AuthService) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      fullName: ['', Validators.required],
      address: [''],
      dob: [''],
      phone: ['']
    });

    this.minDob = '1950-01-01';
    this.maxDob = new Date().toISOString().slice(0,10);

  }

  onSubmit() {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    if (!this.isSubmitting) {
      this.isSubmitting = true;
      this.successMessage = '';
      this.errorMessage = '';
      const fv = this.registerForm.value;
      const payload = {
        name: fv.fullName || '',
        dateOfBirth: fv.dob || null,
        address: fv.address || '',
        phoneNumber: fv.phone || '',
        email: fv.email,
        username: fv.username,
        password: fv.password
      };

      this.personalizeService.registerUser(payload).subscribe({
        next: res => {
          console.log('Registration successful', res);
          this.successMessage = 'User registration successful, please log in to continue.';
          this.isSubmitting = false;
          // store message so it can be shown on the login page after redirect
          try { sessionStorage.setItem('registration_success', this.successMessage); } catch (e) {}
          // navigate after brief pause so user sees the message
          setTimeout(() => this.router.navigate(['/login']), 1500);
        },
        error: err => {
          console.error('Registration failed', err);
          this.errorMessage = (err && err.error && err.error.message) ? err.error.message : 'Registration failed. Please try again.';
          this.isSubmitting = false;
        }
      });
    }
  }

  goBack() {
    window.history.back();
  }
}
