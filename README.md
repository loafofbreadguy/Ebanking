**Banking Application**  
A secure, feature-rich banking application built with Spring Boot, React and MySQL. This application provides comprehensive banking operations including user authentication, wallet management, transactions, and complaint handling.  
**Project Overview**  
This is a REST API-based banking application that handles core banking operations with advanced features like rate limiting, JWT authentication, transaction auditing, and multi-layered security.  
**Technology Stack**  
- **Backend Framework**: Spring Boot 4.0.6  
- **Java Version**: 17  
- **Database**: MySQL  
- **ORM**: Spring Data JPA  
- **Security**: Spring Security with JWT  
- **API Documentation**: OpenAPI 3.0 (Swagger UI)  
- **Build Tool**: Maven  
- **Additional Libraries**: Lombok, Validation, Spring Actuator  
**Prerequisites**  
Before running this application, ensure you have:  
- Java 17 or higher installed  
- MySQL Server running locally (or accessible remotely)  
- Maven installed  
- Git for version control  

**Installation and Setup**

**1. Clone the Repository**  
git clone <repository-url>  
 cd banking  
   
**2. Configure Database**  
Create a MySQL database:  
CREATE DATABASE walletdb;  
   
Update the database credentials in src/main/resources/application.properties:  
spring.datasource.url=jdbc:mysql://localhost:3306/walletdb  
 spring.datasource.username=your_username  
 spring.datasource.password=your_password  
   
**3. Configure Email Service**  
Update email credentials for verification and notifications:  
spring.mail.username=your_email@gmail.com  
 spring.mail.password=your_app_password  
   
**4. Configure JWT Secret**  
Set a strong JWT secret in application.properties:  
jwt.secret=your_long_secret_key_here  
 jwt.access-expiration=100000  
 jwt.refresh-expiration=604800000  
   
**5. Build the Application**  
mvn clean install  
   
**6. Run the Application**  
mvn spring-boot:run  
   
The application will start on http://localhost:8080  
**Project Structure**  
banking/  
 ├── src/  
 │   ├── main/  
 │   │   ├── java/com/banking/banking/  
 │   │   │   ├── BankingApplication.java          # Main application class  
 │   │   │   ├── config/                          # Configuration classes  
 │   │   │   │   ├── OpenApiConfig.java  
 │   │   │   │   ├── RateLimitFilter.java  
 │   │   │   │   └── SecurityConfig.java  
 │   │   │   ├── controller/                      # REST API endpoints  
 │   │   │   │   ├── AuthController.java  
 │   │   │   │   ├── ComplaintController.java  
 │   │   │   │   ├── TransactionController.java  
 │   │   │   │   ├── UserController.java  
 │   │   │   │   └── WalletController.java  
 │   │   │   ├── dto/                             # Data Transfer Objects  
 │   │   │   ├── enums/                           # Enumeration classes  
 │   │   │   │   ├── AuditAction.java  
 │   │   │   │   ├── TransactionStatus.java  
 │   │   │   │   ├── TransactionType.java  
 │   │   │   │   └── UserRole.java  
 │   │   │   ├── exception/                       # Exception handling  
 │   │   │   │   └── GlobalExceptionHandler.java  
 │   │   │   ├── model/                           # Entity classes  
 │   │   │   │   ├── User.java  
 │   │   │   │   ├── Wallet.java  
 │   │   │   │   ├── Transaction.java  
 │   │   │   │   ├── Complaint.java  
 │   │   │   │   ├── RefreshToken.java  
 │   │   │   │   ├── VerificationToken.java  
 │   │   │   │   ├── IdempotencyKey.java  
 │   │   │   │   └── TransactionAuditLog.java  
 │   │   │   ├── repository/                      # Data access layer  
 │   │   │   ├── security/                        # Security utilities  
 │   │   │   │   └── CookieService.java  
 │   │   │   ├── service/                         # Business logic layer  
 │   │   │   └── util/                            # Utility classes  
 │   │   └── resources/  
 │   │       ├── application.properties           # Configuration properties  
 │   │       ├── static/                          # Static resources  
 │   │       └── templates/                       # HTML templates  
 │   └── test/  
 │       └── java/                                # Unit and integration tests  
 ├── pom.xml                                      # Maven dependencies  
 └── README.md                                    # This file  
   
**Key Features**  

**Authentication & Authorization**  
- User registration and login  
- JWT-based authentication with refresh tokens  
- Role-based access control (ADMIN, USER)  
- Email verification for new accounts  
- Secure password handling with encryption  

**User Management**  
- User registration and profile management  
- User role management  
- Account verification via email tokens  
- Refresh token management  

**Wallet Management**  
- Create and manage multiple wallets  
- View wallet balance and transaction history  
- Wallet transfer capabilities  

**Transaction Management**  
- Send and receive money  
- Track transaction status (PENDING, COMPLETED, FAILED)  
- Transaction history with detailed logs  
- Idempotency key support to prevent duplicate transactions  
- Transaction audit logging  

**Complaint Management**  
- File complaints about transactions or services  
- Track complaint status and resolution  
- Complaint response handling  

**Security Features**  
- Rate limiting to prevent abuse  
- JWT token-based authentication  
- Spring Security integration  
- Input validation and sanitization  
- Global exception handling  
- HTTPS/TLS support configuration  

**API Documentation**  
- OpenAPI 3.0 specification  
- Interactive Swagger UI available at http://localhost:8080/swagger-ui.html  

**API Endpoints Overview**  

**Authentication Endpoints**  
- POST /auth/register - Register a new user  
- POST /auth/login - User login  
- POST /auth/refresh - Refresh access token  
- POST /auth/logout - User logout  

**User Endpoints**  
- GET /users/{id} - Get user details  
- PUT /users/{id} - Update user profile  
- GET /users - List all users  

**Wallet Endpoints**  
- POST /wallets - Create a new wallet  
- GET /wallets/{id} - Get wallet details  
- GET /wallets/{id}/balance - Get wallet balance  
- GET /wallets/{id}/transactions - Get wallet transactions  

**Transaction Endpoints**  
- POST /transactions/transfer - Initiate a transfer  
- GET /transactions/{id} - Get transaction details  
- GET /transactions - List transactions  

**Complaint Endpoints**  
- POST /complaints - File a new complaint  
- GET /complaints/{id} - Get complaint details  
- PUT /complaints/{id} - Update complaint  
- GET /complaints - List complaints  

**Configuration Details**  

**Database Configuration**  
- DDL Auto: Update (automatically creates/updates tables)  
- Show SQL: Enabled for development  

**JWT Configuration**  
- Access token expiration: 1800000ms (approximately 1.67 minutes)  
- Refresh token expiration: 604800000 ms (7 days)  

**Email Configuration**  
- SMTP Host: smtp.gmail.com  
- Port: 587  
- TLS: Enabled  

**Application Profile**  
- Default: dev (for local development)  
- Can be changed to prod for production deployment  

**Building the Application**  

**Build JAR**  
mvn clean package  
   
**Run JAR**  
java -jar target/banking-0.0.1-SNAPSHOT.jar  
   
**Testing**  
Run unit tests:  
mvn test  
   
Run specific test class:  
mvn test -Dtest=BankingApplicationTests  
   
**Development**  

**Hot Reload**  
The application includes Spring Boot DevTools for automatic restart on code changes. Simply save your files and the application will restart automatically.  

**Accessing Swagger UI**  
Once the application is running, access the API documentation at:  
- http://localhost:8080/swagger-ui.html  

**Database Schema**  
The application uses Hibernate ORM with the following main entities:  

**User Table**  
| | | |  
|-|-|-|  
| **Field** | **Type** | **Description** |   
| id | BIGINT (Primary Key) | Unique user identifier |   
| email | VARCHAR(255) | User email address (unique) |   
| password | VARCHAR(255) | Encrypted password |   
| first_name | VARCHAR(100) | User first name |   
| last_name | VARCHAR(100) | User last name |   
| phone | VARCHAR(20) | User phone number |   
| role | VARCHAR(50) | User role (ADMIN, USER) |   
| is_enabled | BOOLEAN | Account active status |   
| is_verified | BOOLEAN | Email verification status |   
| created_at | TIMESTAMP | Account creation timestamp |   
| updated_at | TIMESTAMP | Last update timestamp |   
   
**Wallet Table**  
| | | |  
|-|-|-|  
| **Field** | **Type** | **Description** |   
| id | BIGINT (Primary Key) | Unique wallet identifier |   
| user_id | BIGINT (Foreign Key) | Reference to User |   
| wallet_name | VARCHAR(100) | Wallet name/label |   
| balance | DECIMAL(19,2) | Current wallet balance |   
| currency | VARCHAR(10) | Currency code (e.g., USD, INR) |   
| is_active | BOOLEAN | Wallet active status |   
| created_at | TIMESTAMP | Wallet creation timestamp |   
| updated_at | TIMESTAMP | Last update timestamp |   
   
**Transaction Table**  
| | | |  
|-|-|-|  
| **Field** | **Type** | **Description** |   
| id | BIGINT (Primary Key) | Unique transaction identifier |   
| sender_wallet_id | BIGINT (Foreign Key) | Reference to sender Wallet |   
| receiver_wallet_id | BIGINT (Foreign Key) | Reference to receiver Wallet |   
| amount | DECIMAL(19,2) | Transaction amount |   
| type | VARCHAR(50) | Transaction type (TRANSFER, DEPOSIT, WITHDRAWAL) |   
| status | VARCHAR(50) | Status (PENDING, COMPLETED, FAILED) |   
| description | VARCHAR(500) | Transaction description |   
| idempotency_key | VARCHAR(255) | Idempotency key for duplicate prevention |   
| created_at | TIMESTAMP | Transaction creation timestamp |   
| updated_at | TIMESTAMP | Last update timestamp |   
   
**Complaint Table**  
| | | |  
|-|-|-|  
| **Field** | **Type** | **Description** |   
| id | BIGINT (Primary Key) | Unique complaint identifier |   
| user_id | BIGINT (Foreign Key) | Reference to complaining User |   
| transaction_id | BIGINT (Foreign Key) | Reference to related Transaction |   
| title | VARCHAR(255) | Complaint title |   
| description | TEXT | Detailed complaint description |   
| status | VARCHAR(50) | Complaint status (OPEN, IN_PROGRESS, RESOLVED) |   
| priority | VARCHAR(50) | Priority level (LOW, MEDIUM, HIGH) |   
| response | TEXT | Admin response to complaint |   
| created_at | TIMESTAMP | Complaint creation timestamp |   
| resolved_at | TIMESTAMP | Complaint resolution timestamp |   
   
**RefreshToken Table**  
| | | |  
|-|-|-|  
| **Field** | **Type** | **Description** |   
| id | BIGINT (Primary Key) | Unique token identifier |   
| user_id | BIGINT (Foreign Key) | Reference to User |   
| token | TEXT | Refresh token value |   
| expiry_date | TIMESTAMP | Token expiration timestamp |   
| is_revoked | BOOLEAN | Token revocation status |   
| created_at | TIMESTAMP | Token creation timestamp |   
   
**VerificationToken Table**  
| | | |  
|-|-|-|  
| **Field** | **Type** | **Description** |   
| id | BIGINT (Primary Key) | Unique token identifier |   
| user_id | BIGINT (Foreign Key) | Reference to User |   
| token | VARCHAR(255) | Verification token value |   
| expiry_date | TIMESTAMP | Token expiration timestamp |   
| is_used | BOOLEAN | Token usage status |   
| created_at | TIMESTAMP | Token creation timestamp |   
   
**IdempotencyKey Table**  
| | | |  
|-|-|-|  
| **Field** | **Type** | **Description** |   
| id | BIGINT (Primary Key) | Unique key identifier |   
| idempotency_key | VARCHAR(255) | Unique idempotency key |   
| user_id | BIGINT (Foreign Key) | Reference to User |   
| request_body | TEXT | Original request body |   
| response_body | TEXT | Cached response body |   
| created_at | TIMESTAMP | Key creation timestamp |   
| expires_at | TIMESTAMP | Key expiration timestamp |   
   
**TransactionAuditLog Table**  
| | | |  
|-|-|-|  
| **Field** | **Type** | **Description** |   
| id | BIGINT (Primary Key) | Unique audit log identifier |   
| transaction_id | BIGINT (Foreign Key) | Reference to Transaction |   
| user_id | BIGINT (Foreign Key) | Reference to User performing action |   
| action | VARCHAR(100) | Audit action (CREATE, UPDATE, DELETE, VIEW) |   
| old_value | TEXT | Previous value (for updates) |   
| new_value | TEXT | New value (for updates) |   
| ip_address | VARCHAR(50) | IP address of the request |   
| user_agent | VARCHAR(500) | User agent information |   
| created_at | TIMESTAMP | Audit log creation timestamp |   
   
**Troubleshooting**  

**Database Connection Issues**  
- Ensure MySQL server is running  
- Verify database credentials in application.properties  
- Check if database 'walletdb' exists  
**Port Already in Use**  
If port 8080 is already in use, change it in application.properties:  
server.port=8081  
   
**Email Verification Not Working**  
- Verify Gmail app password is correct  
- Enable "Less secure apps" or use App Passwords if using Gmail  
- Check SMTP settings are correct  
**Future Work**  
- Implement a working Admin Panel(Frontend)  
- within the panel ensure they can view the users, complaints, resolve them etc  
