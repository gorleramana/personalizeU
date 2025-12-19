import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl, ValidationErrors } from '@angular/forms';

@Component({
  selector: 'app-rg-forgot-pwd',
  templateUrl: './rg-forgot-pwd.component.html',
  styleUrl: './rg-forgot-pwd.component.css'
})
export class RgForgotPwdComponent {
  forgotPasswordForm: FormGroup;
isSubmitting = false;

  constructor(private fb: FormBuilder) {
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, { validators: this.passwordsMatchValidator });
  }

  onSubmit() {
    if (this.forgotPasswordForm.invalid) {
      this.forgotPasswordForm.markAllAsTouched();
      return;
    }

    const fv = this.forgotPasswordForm.value;
    console.log('Reset password for:', fv.email);
    // TODO: call reset-password API when available
  }

  private passwordsMatchValidator(control: AbstractControl): ValidationErrors | null {
    const pwd = control.get('password')?.value;
    const cpwd = control.get('confirmPassword')?.value;
    if (pwd && cpwd && pwd !== cpwd) {
      return { passwordMismatch: true };
    }
    return null;
  }

  goBack() {
    window.history.back();
  }
}
