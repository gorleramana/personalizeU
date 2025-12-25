import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-header',
  templateUrl: './app-header.component.html',
  styleUrls: ['./app-header.component.css']
})
export class AppHeaderComponent implements OnInit {
  showNavLinks = false;
  private authRoutes = ['login', 'register', 'forgotpwd'];

  constructor(private router: Router) {}

  ngOnInit() {
    // Check current route on init
    this.updateNavLinksVisibility(this.router.url);
    
    // Listen for route changes
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      this.updateNavLinksVisibility(event.url);
    });
  }

  private updateNavLinksVisibility(url: string) {
    // Hide nav-links if URL contains any auth route keywords or is empty (login default)
    const path = url.toLowerCase();
    const isAuthRoute = this.authRoutes.some(route => path.includes(route)) || url === '' || url === '/';
    this.showNavLinks = !isAuthRoute;
  }

  navigateToHome() {
    this.router.navigate(['/home']);
  }

  onLogoClick() {
    // Only navigate to home if on a logged-in page (not auth routes)
    if (this.showNavLinks) {
      this.navigateToHome();
    }
  }

  updateProfile() {
    this.router.navigate(['/profile']);
  }

  logout() {
    // Clear any stored user data
    sessionStorage.clear();
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
