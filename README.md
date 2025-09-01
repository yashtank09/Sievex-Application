# Sievex Application

A secure, scalable Spring Boot application with JWT authentication and role-based access control.

## 🚀 Features

- **JWT Authentication** - Secure token-based authentication
- **Role-Based Access Control** - Multiple user roles (USER, MODERATOR, ADMIN)
- **Dynamic Request Matchers** - Configurable security rules
- **Email Service** - For user verification and notifications
- **Redis Caching** - For token management and performance optimization
- **Comprehensive Validation** - Input validation for all user data

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.4.5
- **Database**: MySQL
- **Authentication**: JWT
- **Caching**: Redis
- **Build Tool**: Maven
- **Java Version**: 17+

## 📦 Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- MySQL 8.0+
- Redis 6.0+

## 🚀 Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/Sievex-Application.git
   cd Sievex-Application
   ```

2. **Configure Database**
   - Create a MySQL database
   - Update `application.properties` with your database credentials
   
 
3. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## 🔒 Security Configuration

The application features a dynamic security configuration that can be managed via the database:
- Public endpoints (no authentication required)
- User endpoints (requires USER role)
- Moderator endpoints (requires MODERATOR role)
- Admin endpoints (full system access)

## 📚 API Documentation

API documentation is available at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/v3/api-docs`

## 📂 Project Structure

```
src/
├── main/
│   ├── java/com/sievex/
│   │   ├── auth/           # Authentication and user management
│   │   ├── automation/     # Automated tasks and jobs management
│   │   ├── configs/        # Configuration classes
│   │   ├── constants/      # Application-wide constants
│   │   ├── crawler/        # Web crawling functionality
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── exception/      # Custom exception handling
│   │   ├── properties/     # Configuration properties
│   │   └── security/       # Security configurations and JWT
│   └── resources/
│       ├── static/         # Static resources
│       └── application.properties  # Application configuration
```

## 🔄 Environment Variables

Create a `.env` file in the root directory with the following variables:

```properties
# Database
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/sievex_db
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password

# JWT
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION_MS=86400000  # 24 hours

# Email
SPRING_MAIL_HOST=smtp.example.com
SPRING_MAIL_USERNAME=your_email@example.com
SPRING_MAIL_PASSWORD=your_email_password

# Redis
SPRING_REDIS_HOST=localhost
SPRING_REDIS_PORT=6379
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch suffix with your short custom name (`git checkout -b feature/AmazingFeature_bk`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature_bk'`)
4. Push to the branch (`git push origin feature/AmazingFeature_bk`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Spring Boot Team
- All contributors who helped in the development
