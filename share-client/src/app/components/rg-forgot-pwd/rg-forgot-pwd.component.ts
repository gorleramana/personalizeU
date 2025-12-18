import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-rg-forgot-pwd',
  templateUrl: './rg-forgot-pwd.component.html',
  styleUrl: './rg-forgot-pwd.component.css'
})
export class RgForgotPwdComponent {
  forgotPasswordForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSubmit() {
    if (this.forgotPasswordForm.valid) {
      console.log('Reset password for:', this.forgotPasswordForm.value.email);
    }
  }

  goBack() {
    window.history.back();
  }
}
