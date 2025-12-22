import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './app-footer.component.html',
  styleUrls: ['./app-footer.component.css']
})
export class AppFooterComponent {
  currentYear = new Date().getFullYear();
  showChat = false;

  openChat(): void {
    this.showChat = true;
  }

  closeChat(): void {
    this.showChat = false;
  }
}
