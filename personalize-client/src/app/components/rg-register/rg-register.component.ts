import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
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
  isProfileMode = false;

  constructor(private fb: FormBuilder, private router: Router, private route: ActivatedRoute, private personalizeService: PersonalizeService, private authService: AuthService) {
    // Detect if we're in profile update mode
    this.isProfileMode = this.router.url.includes('/profile');
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

  ngOnInit() {
    // If in profile mode, populate form with stored user details
    if (this.isProfileMode) {
      try {
        const userDetailsStr = sessionStorage.getItem('user_details');
        if (userDetailsStr) {
          const userDetails = JSON.parse(userDetailsStr);
          this.registerForm.patchValue({
            fullName: userDetails.name || '',
            email: userDetails.email || '',
            username: userDetails.username || '',
            password: userDetails.password || '',
            address: userDetails.address || '',
            dob: userDetails.dateOfBirth || '',
            phone: userDetails.phoneNumber || ''
          });
        }
      } catch (e) {
        console.error('Error loading user details', e);
      }
    }
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
          this.successMessage = this.isProfileMode ? 'Profile updated successfully!' : 'User registration successful, please log in to continue.';
          this.isSubmitting = false;
          // store message so it can be shown on the next page after redirect
          const storageKey = this.isProfileMode ? 'profile_update_success' : 'registration_success';
          const redirectPath = this.isProfileMode ? '/home' : '/login';
          try { sessionStorage.setItem(storageKey, this.successMessage); } catch (e) {}
          // navigate after brief pause so user sees the message
          setTimeout(() => this.router.navigate([redirectPath]), 1500);
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
