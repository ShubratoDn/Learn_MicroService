# API Testing Guide

## Prerequisites
1. Start all services in this order:
   - ServiceRegistry (Eureka Server) - Port 8761
   - ConfigServer - Port 8888
   - AuthService - Port 8086
   - UserService - Port 8081
   - HotelService - Port 8082
   - RatingService - Port 8083
   - ApiGateway - Port 8084

## Testing Flow

### 1. Register a new user
```bash
curl -X POST http://localhost:8084/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "username": "johndoe",
    "email": "john@example.com",
    "password": "password123",
    "role": "USER",
    "designation": "Developer"
  }'
```

### 2. Login to get JWT token
```bash
curl -X POST http://localhost:8084/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "password123"
  }'
```

### 3. Extract user information using the token
```bash
# Replace YOUR_JWT_TOKEN with the token from step 2
curl -X GET http://localhost:8084/api/users/me \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 4. Get full user profile
```bash
# Replace YOUR_JWT_TOKEN with the token from step 2
curl -X GET http://localhost:8084/api/users/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Expected Responses

### Login Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### User Info Response (/me)
```json
{
  "username": "johndoe",
  "email": "john@example.com",
  "role": "USER",
  "designation": "Developer",
  "name": "John Doe"
}
```

### User Profile Response (/profile)
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "about": null,
  "username": "johndoe",
  "password": null,
  "address": null,
  "remark": null,
  "designation": "Developer",
  "image": null,
  "isLocked": false,
  "createdAt": "2024-01-01T10:00:00.000+00:00",
  "updatedAt": "2024-01-01T10:00:00.000+00:00",
  "ratings": []
}
```

## Security Features

1. **JWT Authentication**: All protected endpoints require a valid JWT token
2. **Token Validation**: API Gateway validates tokens before forwarding requests
3. **User Information Extraction**: User details are extracted from JWT and passed as headers
4. **Excluded Paths**: Login and register endpoints are excluded from authentication

## Error Responses

### Unauthorized (401)
```json
{
  "timestamp": "2024-01-01T10:00:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Incorrect password."
}
```

### Bad Request (400)
```json
{
  "timestamp": "2024-01-01T10:00:00",
  "status": 400,
  "error": "User Not Found",
  "message": "User not found."
}
``` 