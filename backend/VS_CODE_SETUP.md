# VS Code Setup Guide for Spring Boot Backend

This guide explains how to set up VS Code for developing the E-Kart Spring Boot backend services.

## Prerequisites

1. **Java Development Kit (JDK) 17 or higher**
   - Download from: https://adoptium.net/
   - Install and verify: `java -version`

2. **Apache Maven**
   - Download from: https://maven.apache.org/download.cgi
   - Extract and add to PATH
   - Verify: `mvn -version`

3. **Git** (optional but recommended)
   - Download from: https://git-scm.com/downloads
   - Verify: `git --version`

## Required VS Code Extensions

Install these extensions from VS Code Extensions Marketplace:

### Essential Extensions

1. **Extension Pack for Java** (by Microsoft)
   - Includes: Language Support for Java, Debugger for Java, Java Test Runner, Maven for Java
   - Install: Search "Extension Pack for Java" and install

2. **Spring Boot Extension Pack** (by Pivotal)
   - Includes: Spring Boot Tools, Spring Initializr, Spring Boot Dashboard
   - Install: Search "Spring Boot Extension Pack" and install

3. **Maven for Java** (by Microsoft)
   - Provides Maven project support
   - Install: Search "Maven for Java" and install

### Recommended Extensions

4. **Lombok Annotations Support for VS Code**
   - Provides code completion for Lombok annotations
   - Install: Search "Lombok Annotations Support" and install

5. **SonarLint** (by SonarSource)
   - Code quality and security analysis
   - Install: Search "SonarLint" and install

6. **GitLens** (by GitKraken)
   - Git supercharged - visualize code authorship
   - Install: Search "GitLens" and install

7. **REST Client** (by Huachao Mao)
   - Test REST APIs directly from VS Code
   - Install: Search "REST Client" and install

8. **Thunder Client** (by Ranga Vadhineni)
   - Lightweight REST API client (alternative to REST Client)
   - Install: Search "Thunder Client" and install

9. **MongoDB for VS Code**
   - Connect to MongoDB, view collections, run queries
   - Install: Search "MongoDB for VS Code" and install

10. **MySQL** (by Jun Han)
    - MySQL management in VS Code
    - Install: Search "MySQL" and install

## VS Code Workspace Setup

### 1. Open Project in VS Code

```bash
# Navigate to backend directory
cd d:\E-Commerce_Shopping_App\backend

# Open in VS Code
code .
```

### 2. Configure Java Home

VS Code should auto-detect Java. If not:

1. Press `Ctrl+Shift+P` (Windows) or `Cmd+Shift+P` (macOS)
2. Type "Java: Configure Java Runtime"
3. Select JDK 17 or higher
4. Set as default

### 3. Configure Maven

VS Code should auto-detect Maven. If not:

1. Press `Ctrl+Shift+P`
2. Type "Java: Clean Java Language Server Workspace"
3. Restart VS Code

### 4. Open Spring Boot Dashboard

1. Click on the Spring Boot icon in the left sidebar
2. You'll see all Spring Boot applications detected
3. Each service will appear with start/stop/debug buttons

## Running Services in VS Code

### Method 1: Spring Boot Dashboard (Recommended)

1. Open Spring Boot Dashboard (left sidebar)
2. Click the play button next to each service
3. Services will start in integrated terminals
4. View logs in the terminal

**Services to start:**
- authentication-service (Port 8081)
- product-service (Port 8082)
- order-service (Port 8083)
- cart-service (Port 8084)
- review-service (Port 8085)

### Method 2: Integrated Terminal

1. Press `Ctrl+`` (backtick) to open terminal
2. Navigate to service directory:
```bash
cd authentication-service
mvn spring-boot:run
```
3. Open new terminal for each service

### Method 3: Run Configuration

1. Press `F5` or click "Run and Debug"
2. Select "Java" if prompted
3. Create launch configuration (see below)

## Debugging in VS Code

### Create Launch Configuration

1. Create `.vscode/launch.json` in backend directory:
```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Launch Authentication Service",
      "request": "launch",
      "mainClass": "com.ekart.auth.AuthServiceApplication",
      "projectName": "authentication-service",
      "cwd": "${workspaceFolder}/authentication-service"
    },
    {
      "type": "java",
      "name": "Launch Product Service",
      "request": "launch",
      "mainClass": "com.ekart.product.ProductServiceApplication",
      "projectName": "product-service",
      "cwd": "${workspaceFolder}/product-service"
    },
    {
      "type": "java",
      "name": "Launch Order Service",
      "request": "launch",
      "mainClass": "com.ekart.order.OrderServiceApplication",
      "projectName": "order-service",
      "cwd": "${workspaceFolder}/order-service"
    },
    {
      "type": "java",
      "name": "Launch Cart Service",
      "request": "launch",
      "mainClass": "com.ekart.cart.CartServiceApplication",
      "projectName": "cart-service",
      "cwd": "${workspaceFolder}/cart-service"
    },
    {
      "type": "java",
      "name": "Launch Review Service",
      "request": "launch",
      "mainClass": "com.ekart.review.ReviewServiceApplication",
      "projectName": "review-service",
      "cwd": "${workspaceFolder}/review-service"
    }
  ]
}
```

2. Press `F5` and select the service to debug
3. Set breakpoints by clicking on line numbers
4. Use debug controls: Step Over, Step Into, Continue

## Code Navigation & Editing

### Java Features

- **Go to Definition**: `F12` or `Ctrl+Click`
- **Find References**: `Shift+F12`
- **Rename Symbol**: `F2`
- **Format Document**: `Shift+Alt+F`
- **Organize Imports**: `Ctrl+Shift+O`

### Maven Features

- **View Maven Projects**: Open Command Palette → "Maven: Open Maven Project"
- **Run Maven Goals**: Right-click on pom.xml → Run Maven
- **Update Dependencies**: Right-click on pom.xml → Update Project

### Spring Boot Features

- **Jump to Bean Definition**: Ctrl+Click on @Bean
- **Navigate to @RequestMapping**: Ctrl+Click on endpoint path
- **Live Templates**: Type `@restcontroller` + Tab

## Testing in VS Code

### Run Tests

1. Open test file (e.g., `AuthServiceApplicationTests.java`)
2. Click on the green play button above the test class
3. Or right-click → "Run Java"

### View Test Results

- Test results appear in the "Test Results" panel
- Click on failed tests to see stack traces

## Database Integration

### MongoDB for VS Code

1. Click on MongoDB icon in left sidebar
2. Click "Connect to MongoDB"
3. Enter connection string: `mongodb://localhost:27017`
4. Browse databases and collections
5. Run queries in the integrated shell

### MySQL Extension

1. Click on MySQL icon in left sidebar
2. Click "Create Connection"
3. Enter connection details:
   - Host: `localhost`
   - Port: `3306`
   - Username: `root`
   - Password: `root`
   - Database: `ekart_auth`
4. Browse tables and run queries

## API Testing in VS Code

### Using REST Client

1. Create a file `api-test.http` in backend directory
2. Add API requests:
```http
### Authentication Service
POST http://localhost:8081/api/auth/register
Content-Type: application/json

{
  "name": "Test User",
  "email": "test@example.com",
  "password": "password123",
  "role": "BUYER"
}

### Login
POST http://localhost:8081/api/auth/login
Content-Type: application/json

{
  "email": "test@example.com",
  "password": "password123"
}
```

3. Click "Send Request" above each request
4. View response in a new window

### Using Thunder Client

1. Click on Thunder Client icon in left sidebar
2. Click "New Request"
3. Enter URL, method, and body
4. Click "Send"
5. Save requests to collections

## VS Code Settings

### Recommended Settings

Create `.vscode/settings.json`:
```json
{
  "java.configuration.updateBuildConfiguration": "automatic",
  "java.format.enabled": true,
  "java.saveActions.organizeImports": true,
  "editor.formatOnSave": true,
  "files.autoSave": "afterDelay",
  "files.autoSaveDelay": 1000,
  "spring.boot.ls": {
    "show": "all"
  }
}
```

## Troubleshooting

### Java Extension Not Working

1. Reload VS Code: `Ctrl+Shift+P` → "Developer: Reload Window"
2. Clean Java workspace: `Ctrl+Shift+P` → "Java: Clean Java Language Server Workspace"
3. Check Java version: Ensure JDK 17+ is installed

### Maven Not Detected

1. Check Maven installation: `mvn -version`
2. Add Maven to PATH environment variable
3. Restart VS Code

### Spring Boot Dashboard Empty

1. Ensure Java and Maven extensions are installed
2. Reload VS Code
3. Open a service directory (e.g., authentication-service)
4. Dashboard should auto-detect

### Port Already in Use

```bash
# Find process using port
# Windows:
netstat -ano | findstr :8081

# Kill process
taskkill /PID <PID> /F

# Linux/macOS:
lsof -i :8081
kill -9 <PID>
```

## Keyboard Shortcuts

### General
- `Ctrl+P` - Quick Open File
- `Ctrl+Shift+P` - Command Palette
- `Ctrl+`` - Toggle Terminal
- `Ctrl+B` - Toggle Sidebar

### Java
- `Ctrl+Space` - Code Completion
- `F12` - Go to Definition
- `Shift+F12` - Find References
- `F2` - Rename Symbol
- `Ctrl+Shift+O` - Organize Imports

### Debugging
- `F5` - Start Debugging
- `F9` - Toggle Breakpoint
- `F10` - Step Over
- `F11` - Step Into
- `Shift+F11` - Step Out

## Tips & Best Practices

1. **Use Spring Boot Dashboard** for managing multiple services
2. **Set breakpoints** for debugging complex logic
3. **Use REST Client** for quick API testing
4. **Enable auto-save** to avoid losing changes
5. **Use GitLens** for understanding code history
6. **Run SonarLint** for code quality checks
7. **Format code on save** for consistent style
8. **Use MongoDB for VS Code** for database inspection

## Next Steps

After setting up VS Code:
1. Start all services using Spring Boot Dashboard
2. Test APIs using REST Client or Thunder Client
3. Debug services by setting breakpoints
4. View databases using MongoDB and MySQL extensions
5. Deploy to production using Docker
