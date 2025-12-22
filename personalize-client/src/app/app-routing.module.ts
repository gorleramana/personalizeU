import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RgHomeComponent } from './components/rg-home/rg-home.component';
import { RgRegisterComponent } from './components/rg-register/rg-register.component';
import { RgForgotPwdComponent } from './components/rg-forgot-pwd/rg-forgot-pwd.component';
import { RgLoginComponent } from './components/rg-login/rg-login.component';

const routes: Routes = [
  {
    path: '',
    component: RgLoginComponent,
    title: 'Login page',
  },
  {
    path: 'login',
    component: RgLoginComponent,
    title: 'Login page',
  },
  {
    path: 'home',
    component: RgHomeComponent,
    title: 'Home page',
  },
  {
    path: 'register',
    component: RgRegisterComponent,
    title: 'Registration',
  },
  {
    path: 'forgotpwd',
    component: RgForgotPwdComponent,
    title: 'Forgot Password',
  },
  {
    path: 'profile',
    component: RgRegisterComponent,
    title: 'Update Profile',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
