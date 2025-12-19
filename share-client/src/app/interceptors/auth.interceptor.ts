import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Skip adding/removing auth header for preflight and registration endpoint
    try {
      if (!req.url) return next.handle(req);
      const url = req.url.toString();
      // Allow preflight through unchanged
      if (req.method === 'OPTIONS') {
        return next.handle(req);
      }
      // If this is the public register endpoint, ensure no Authorization header is sent
      if (url.includes('/users/register')) {
        // remove any existing Authorization header for safety
        const cleaned = req.clone({ headers: req.headers.delete('Authorization') });
        return next.handle(cleaned);
      }
    } catch (e) {
      // fallthrough to normal behavior
    }

    const header = this.auth.getAuthHeader();
    if (header) {
      const cloned = req.clone({ setHeaders: { Authorization: header } });
      return next.handle(cloned);
    }
    return next.handle(req);
  }
}
