import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RgService } from '../../services/rg.service';

@Component({
  selector: 'app-rg-login',
  templateUrl: './rg-login.component.html',
  styleUrl: './rg-login.component.css'
})
export class RgLoginComponent {
  loginForm: FormGroup;
  registrationSuccess = '';

  constructor(private fb: FormBuilder, private router: Router, private rgService: RgService) {

    this.loginForm = this.fb.group({
      username:['',[Validators.required, Validators.minLength]],
      password: ['', Validators.required],
      remember:[false]
    });
  }

  ngOnInit() {
    try {
      const msg = sessionStorage.getItem('registration_success');
      if (msg) {
        this.registrationSuccess = msg;
        sessionStorage.removeItem('registration_success');
      }
    } catch (e) {}
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.rgService.validateUser(this.loginForm.value).subscribe(
        response => {
          console.log('User validated:', response);
          this.router.navigate(['/home']);
        },
        error => {
          console.error('Validation failed:', error);
        }
      );
    }
  }

  registerUser() {
    this.router.navigate(['/register']);
  }

  forgotPassword(){
    this.router.navigate(['/forgotpwd']);
  }
}
