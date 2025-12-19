import { Injectable } from '@angular/core';

interface Creds {
  username: string;
  password: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private creds: Creds | null = null;
  private storageKey = 'rg_auth_credentials';

  constructor() {
    this.loadFromSession();
  }

  setCredentials(username: string, password: string, persist = false) {
    this.creds = { username, password };
    if (persist) {
      this.saveToSession();
    }
  }

  persistCredentials() {
    if (this.creds) this.saveToSession();
  }

  clearCredentials() {
    this.creds = null;
    try { sessionStorage.removeItem(this.storageKey); } catch (e) {}
  }

  getAuthHeader(): string | null {
    if (!this.creds) return null;
    try {
      return 'Basic ' + btoa(`${this.creds.username}:${this.creds.password}`);
    } catch (e) {
      return null;
    }
  }

  private saveToSession() {
    try {
      sessionStorage.setItem(this.storageKey, JSON.stringify(this.creds));
    } catch (e) {}
  }

  private loadFromSession() {
    try {
      const raw = sessionStorage.getItem(this.storageKey);
      if (raw) {
        const parsed = JSON.parse(raw) as Creds;
        if (parsed && parsed.username) this.creds = parsed;
      }
    } catch (e) {
      this.creds = null;
    }
  }
}
