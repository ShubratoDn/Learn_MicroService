# UserService Spring Security Implementation

## Overview

The UserService now uses Spring Security with JWT authentication to handle user authentication and authorization directly, eliminating the need to rely on request headers passed from the API Gateway.

## Key Components

### 1. JwtTokenUtil
- **Location**: `src/main/java/com/microservice/learn/config/JwtTokenUtil.java`
- **Purpose**: Extracts user information from JWT tokens
- **Methods**:
  - `extractUsername(token)`: Gets username from JWT
  - `extractEmail(token)`: Gets email from JWT
  - `extractRole(token)`: Gets role from JWT
  - `extractDesignation(token)`: Gets designation from JWT
  - `extractName(token)`: Gets name from JWT
  - `validateToken(token)`: Validates JWT token

### 2. CustomUserDetails
- **Location**: `src/main/java/com/microservice/learn/config/CustomUserDetails.java`
- **Purpose**: Custom UserDetails implementation to hold user information from JWT
- **Features**: Stores username, email, role, designation, and name

### 3. JwtAuthenticationFilter
- **Location**: `src/main/java/com/microservice/learn/config/JwtAuthenticationFilter.java`
- **Purpose**: Intercepts requests and sets up Spring Security authentication from JWT tokens
- **Process**:
  1. Extracts JWT from Authorization header
  2. Validates the token
  3. Extracts user information
  4. Creates CustomUserDetails
  5. Sets up Authentication in SecurityContext

### 4. SecurityConfig
- **Location**: `src/main/java/com/microservice/learn/config/SecurityConfig.java`
- **Purpose**: Configures Spring Security
- **Features**:
  - Stateless session management
  - JWT filter integration
  - Method-level security enabled
  - CSRF disabled for API endpoints

### 5. CurrentUserUtil
- **Location**: `src/main/java/com/microservice/learn/config/CurrentUserUtil.java`
- **Purpose**: Utility class for easy access to current user information
- **Methods**:
  - `getCurrentUser()`: Returns CustomUserDetails
  - `getCurrentUsername()`: Returns current username
  - `getCurrentUserEmail()`: Returns current user email
  - `getCurrentUserRole()`: Returns current user role
  - `getCurrentUserDesignation()`: Returns current user designation
  - `getCurrentUserName()`: Returns current user name
  - `isAuthenticated()`: Checks if user is authenticated

## API Endpoints

### Authentication Required Endpoints
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `PUT /api/users/{id}` - Update user
- `GET /api/users/me` - Get current user info
- `GET /api/users/profile` - Get current user profile
- `GET /api/users/me-util` - Get current user info (utility method)

### Admin Only Endpoints
- `GET /api/users/` - Get all users (requires ADMIN role)
- `DELETE /api/users/{id}` - Delete user (requires ADMIN role)

### Public Endpoints
- `POST /api/users/` - Create new user (registration)

## Usage Examples

### 1. Using Authentication Parameter
```java
@GetMapping("/me")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<UserInfo> getCurrentUserInfo(Authentication authentication) {
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    // Use userDetails.getUsername(), userDetails.getEmail(), etc.
}
```

### 2. Using Utility Class
```java
@GetMapping("/me-util")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<UserInfo> getCurrentUserInfoUtil() {
    String username = CurrentUserUtil.getCurrentUsername();
    String email = CurrentUserUtil.getCurrentUserEmail();
    // Use the extracted information
}
```

### 3. Method-Level Security
```java
@GetMapping("/")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<List<User>> getAllUsers() {
    // Only accessible by users with ADMIN role
}
```

## Benefits

1. **Clean Architecture**: No dependency on request headers
2. **Security**: Proper authentication and authorization
3. **Flexibility**: Easy to access user information anywhere in the service
4. **Method-Level Security**: Fine-grained access control
5. **Stateless**: No session management required
6. **Consistent**: Uses standard Spring Security patterns

## Testing

To test the endpoints:

1. **Get JWT Token**: First authenticate through AuthService
   ```bash
   POST /api/auth/login
   {
     "username": "your_username",
     "password": "your_password"
   }
   ```

2. **Use JWT Token**: Include the token in Authorization header
   ```bash
   GET /api/users/me
   Authorization: Bearer <your_jwt_token>
   ```

3. **Test Different Roles**: Try accessing admin endpoints with different user roles

## Migration from Header-Based Approach

The old approach relied on request headers:
```java
@RequestHeader(value = "X-User-Email", required = false) String email
```

The new approach uses Spring Security:
```java
Authentication authentication
CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
String email = userDetails.getEmail();
```

This provides better security, cleaner code, and more flexibility. 