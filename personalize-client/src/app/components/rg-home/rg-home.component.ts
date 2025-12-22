import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-rg-home',
  templateUrl: './rg-home.component.html',
  styleUrl: './rg-home.component.css'
})
export class RgHomeComponent {

  person:string ='';
  loginSuccess = '';

  constructor(){
    
  }

  ngOnInit() {
    try {
      const msg = sessionStorage.getItem('login_success');
      if (msg) {
        this.loginSuccess = msg;
        sessionStorage.removeItem('login_success');
      }
    } catch (e) {}
  }

  addPerson(person:string){
    alert('in progreess'+person);
  }
}
