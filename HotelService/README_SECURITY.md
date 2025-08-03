# HotelService Spring Security Implementation

This document describes the Spring Security implementation in the HotelService, which provides JWT-based authentication and authorization.

## Overview

The HotelService now includes Spring Security with JWT authentication, allowing it to:
- Validate JWT tokens from the ApiGateway
- Extract user information from JWT claims
- Provide method-level authorization using `@PreAuthorize` annotations
- Access current user information throughout the service

## Components

### 1. JwtTokenUtil
- **Location**: `src/main/java/com/microservice/hotel/config/JwtTokenUtil.java`
- **Purpose**: Handles JWT token operations (validation, claim extraction)
- **Key Methods**:
  - `validateToken(String token)`: Validates JWT token
  - `extractUsername(String token)`: Extracts username from token
  - `extractEmail(String token)`: Extracts email from token
  - `extractRole(String token)`: Extracts role from token
  - `extractDesignation(String token)`: Extracts designation from token
  - `extractName(String token)`: Extracts name from token

### 2. CustomUserDetails
- **Location**: `src/main/java/com/microservice/hotel/config/CustomUserDetails.java`
- **Purpose**: Implements Spring Security's UserDetails interface
- **Features**: Encapsulates user information from JWT claims

### 3. JwtAuthenticationFilter
- **Location**: `src/main/java/com/microservice/hotel/config/JwtAuthenticationFilter.java`
- **Purpose**: Intercepts requests and validates JWT tokens
- **Functionality**:
  - Extracts JWT from Authorization header
  - Validates token using JwtTokenUtil
  - Creates CustomUserDetails from JWT claims
  - Sets up SecurityContextHolder with authenticated user

### 4. SecurityConfig
- **Location**: `src/main/java/com/microservice/hotel/config/SecurityConfig.java`
- **Purpose**: Configures Spring Security
- **Configuration**:
  - Disables CSRF (stateless API)
  - Sets session management to STATELESS
  - Configures JWT filter
  - Permits actuator endpoints
  - Requires authentication for all other endpoints

### 5. CurrentUserUtil
- **Location**: `src/main/java/com/microservice/hotel/config/CurrentUserUtil.java`
- **Purpose**: Utility class for accessing current user information
- **Static Methods**:
  - `getCurrentUser()`: Returns CustomUserDetails
  - `getCurrentUsername()`: Returns current username
  - `getCurrentUserEmail()`: Returns current user email
  - `getCurrentUserRole()`: Returns current user role
  - `getCurrentUserDesignation()`: Returns current user designation
  - `getCurrentUserName()`: Returns current user name
  - `isAuthenticated()`: Checks if user is authenticated

## API Endpoints

### Protected Endpoints
All hotel endpoints now require authentication:

- `GET /api/hotels` - Get all hotels (requires authentication)
- `GET /api/hotels/{id}` - Get hotel by ID (requires authentication)
- `POST /api/hotels` - Add new hotel (requires ADMIN role)
- `PUT /api/hotels/{id}` - Update hotel (requires ADMIN role)
- `DELETE /api/hotels/{id}` - Delete hotel (requires ADMIN role)
- `GET /api/hotels/searchByName` - Search hotels by name (requires authentication)
- `GET /api/hotels/searchByAddress` - Search hotels by address (requires authentication)

### New User Information Endpoints
- `GET /api/hotels/me` - Get current user information (requires authentication)
- `GET /api/hotels/me-util` - Get current user information using CurrentUserUtil (requires authentication)

## Usage Examples

### 1. Method-Level Authorization
```java
@PreAuthorize("isAuthenticated()")
public List<Hotel> getAllHotels() {
    return hotelService.getAllHotels();
}

@PreAuthorize("hasRole('ADMIN')")
public Hotel addNewHotel(@RequestBody Hotel hotel) {
    return hotelService.addNewHotel(hotel);
}
```

### 2. Accessing Current User in Controller
```java
@GetMapping("/me")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<?> getCurrentUserInfo(Authentication authentication) {
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    // Access user information
    String username = userDetails.getUsername();
    String email = userDetails.getEmail();
    String role = userDetails.getRole();
    // ...
}
```

### 3. Using CurrentUserUtil
```java
@GetMapping("/me-util")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<?> getCurrentUserInfoUtil() {
    String username = CurrentUserUtil.getCurrentUsername();
    String email = CurrentUserUtil.getCurrentUserEmail();
    String role = CurrentUserUtil.getCurrentUserRole();
    // ...
}
```

## Testing

### 1. Get Authentication Token
First, authenticate through AuthService:
```bash
curl -X POST http://localhost:8084/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### 2. Access Protected Endpoints
Use the token from step 1:
```bash
# Get all hotels
curl -X GET http://localhost:8084/api/hotels \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Get current user info
curl -X GET http://localhost:8084/api/hotels/me \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Add new hotel (requires ADMIN role)
curl -X POST http://localhost:8084/api/hotels \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Hotel","address":"Test Address","about":"Test Description"}'
```

## Benefits

1. **Centralized Authentication**: ApiGateway validates JWT tokens
2. **Service-Level Authorization**: Each service handles its own authorization logic
3. **User Context**: Easy access to current user information throughout the service
4. **Method-Level Security**: Fine-grained control over endpoint access
5. **Stateless**: No session management required
6. **Consistent**: Same security pattern across all microservices

## Dependencies

The following dependencies were added to `pom.xml`:
- `spring-boot-starter-security`: Spring Security framework
- `jjwt`: JWT library for token handling
- `jaxb-api`: Required for JWT operations

## Security Flow

1. **Request arrives** at ApiGateway with JWT token
2. **ApiGateway validates** the JWT token
3. **Request forwarded** to HotelService with original Authorization header
4. **JwtAuthenticationFilter** in HotelService extracts and validates the token
5. **SecurityContextHolder** is populated with user information
6. **Controller methods** can access user information and apply authorization rules 