# BuildGuard AI MVP

AI Home Construction Tracker starter project.

## Stack

- Angular frontend
- Java Spring Boot backend
- PostgreSQL database

## MVP Features

- Dashboard
- Create construction projects
- Track inspection/defect items
- Assign items to contractors
- Priority and status fields
- Photo URL field for first MVP version
- Ready for future AI photo analysis and PDF reports

## Database Setup

Create PostgreSQL database:

```sql
CREATE DATABASE buildguarddb;
```

Update backend credentials in:

```text
backend/src/main/resources/application.properties
```

Default:

```properties
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Run Backend

```bash
cd backend
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

## Run Frontend

```bash
cd frontend
npm install
npm start
```

Frontend runs on:

```text
http://localhost:4200
```

## API Endpoints

### Projects

```http
GET    /api/projects
GET    /api/projects/{id}
POST   /api/projects
PUT    /api/projects/{id}
DELETE /api/projects/{id}
```

### Inspection Items

```http
GET    /api/inspection-items/project/{projectId}
POST   /api/inspection-items/project/{projectId}
PUT    /api/inspection-items/{id}
DELETE /api/inspection-items/{id}
```

## Next Features To Add

1. Real image upload instead of photo URL
2. PDF inspection report generation
3. User login with JWT
4. Contractor portal
5. Mobile app using Angular + Capacitor
6. AI photo analysis
7. English/Turkish language toggle
8. Before/after repair verification

## Suggested Product Names

- BuildGuard AI
- SiteProof AI
- HomeBuild Tracker
- YapıTakip AI
