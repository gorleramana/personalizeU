# personalize_U
Small demo project that contains an Angular client (`personalize-client`) and a Spring Boot backend (`personalize-web`). The work in this repository includes a user registration flow, basic authentication utilities for development, and consolidated client styles.

## Repositories / Folders
- `personalize-client` — Angular application (Reactive Forms, HttpClient, Router).
- `personalize-web` — Spring Boot backend (Spring Security, JPA, Mongo/Redis stubs).

## Quick start

1) Start the backend (Windows PowerShell):

```powershell
cd C:\workspace\personalizeU\personalize-web
.\mvnw.cmd spring-boot:run
```

The backend runs on port 8085 by default (Tomcat). Ensure Mongo/Postgres configs in `personalize-web/src/main/resources/application.properties` are set if you need persistence; the project will start without them for local dev in many cases.

2) Build / serve the frontend (PowerShell):

```powershell
cd C:\workspace\personalizeU\personalize-client
npm install
npm run build
```

Or for development serve with live reload:

```powershell
npm start
```

Open the client at `http://localhost:4200` (default Angular dev server).

## Important Notes
- The registration endpoint `/users/register` now returns JSON. Successful responses are:
	- 201 Created: { "message": "User created successfully", "userId": <id>, "updated": false }
	- 200 OK: { "message": "User updated successfully", "userId": <id>, "updated": true }

- The register endpoint is intentionally unauthenticated (permitted in security config). Other endpoints use basic auth for development and an `AuthService` + HTTP interceptor attach Authorization headers.

- The frontend stores a short registration success message in `sessionStorage` under the key `registration_success` to show on the login page after redirect.

## Files of interest
- Backend: `personalize-web/src/main/java/com/rg/web/controller/UserController.java` (register endpoint)
- Backend DTOs: `personalize-web/src/main/java/com/rg/web/dto/UserRegistrationRequest.java`, `UserRegistrationResponse.java`
- Frontend service: `personalize-client/src/app/services/rg.service.ts` (registerUser now expects JSON)
- Frontend registration component: `personalize-client/src/app/components/rg-register/rg-register.component.ts`
- Global styles: `personalize-client/src/styles.css`

## Troubleshooting
- CORS: the backend config uses explicit `allowedOrigins` when `allowCredentials` is true — do not use `*` with credentials.
- If you see validation failures from the server, ensure the JSON payload field names match the backend DTO names (e.g., `name`, `dateOfBirth`, `phoneNumber`).
- If the Angular client fails to parse responses, verify `rg.service.ts` uses the correct `responseType` (it currently expects JSON for `/users/register`).

## Next steps / Suggestions
- Consider switching from client-stored Basic Auth credentials to token-based auth (JWT) for production.
- Optionally change the backend register response to a more detailed JSON schema (already implemented with `UserRegistrationResponse`).

If you want, I can run the backend and a frontend build here and perform a quick smoke test POST to `/users/register`.
