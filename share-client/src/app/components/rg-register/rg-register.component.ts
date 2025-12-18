import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-rg-register',
  templateUrl: './rg-register.component.html',
  styleUrl: './rg-register.component.css'
})
export class RgRegisterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router) {
    this.registerForm = this.fb.group({
      username:['',[Validators.required, Validators.minLength]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });

  }

  onSubmit() {
    if (this.registerForm.valid) {
      // Handle login logic here
      console.log(this.registerForm.value);
     this.router.navigate(['/']);
    }
  }

  goBack() {
    window.history.back();
  }
}
