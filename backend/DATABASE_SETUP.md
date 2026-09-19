# Database Setup Guide

This guide explains how to set up MySQL and MongoDB databases for the E-Kart backend services.

## Architecture Overview

### Why MySQL for Authentication?
- **Relational Data**: User authentication data requires strict relationships (users, roles, permissions)
- **ACID Compliance**: Authentication operations need to be atomic and consistent
- **Security**: SQL databases provide robust security features for sensitive user data
- **Mature Technology**: MySQL has excellent support for transactions and constraints

### Why MongoDB for Other Services?
- **Flexible Schema**: Products, orders, cart items have varying structures
- **High Performance**: NoSQL databases handle large volumes of read/write operations efficiently
- **Scalability**: MongoDB scales horizontally for high-traffic e-commerce scenarios
- **Document Model**: Nested data structures (order items, cart items) fit naturally in documents

## MySQL Database Setup (Authentication Service)

### 1. Install MySQL

#### Windows:
```bash
# Download MySQL Installer from https://dev.mysql.com/downloads/installer/
# Run the installer and follow the setup wizard
# Remember your root password (default: root)
```

#### macOS:
```bash
brew install mysql
brew services start mysql
```

#### Linux (Ubuntu):
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

### 2. Create Database

```sql
-- Connect to MySQL
mysql -u root -p

-- Create database
CREATE DATABASE ekart_auth;

-- Verify database
SHOW DATABASES;

-- Exit
EXIT;
```

### 3. Database Schema

The Authentication Service uses Hibernate auto-DDL mode, so tables are created automatically. The schema includes:

**Users Table:**
- `id` (BIGINT, AUTO_INCREMENT, PRIMARY KEY)
- `email` (VARCHAR(100), UNIQUE, NOT NULL)
- `password` (VARCHAR(255), NOT NULL)
- `name` (VARCHAR(100), NOT NULL)
- `role` (ENUM: BUYER, SELLER, ADMIN, NOT NULL)
- `store_name` (VARCHAR(100)) - For sellers
- `phone` (VARCHAR(20))
- `address` (VARCHAR(255))
- `active` (BOOLEAN, DEFAULT TRUE)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### 4. Connection Configuration

The connection is configured in `authentication-service/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ekart_auth?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
```

**Update the credentials if needed:**
- Change `username` if you created a different MySQL user
- Change `password` to your MySQL root password

### 5. Verify Connection

Start the Authentication Service:
```bash
cd backend/authentication-service
mvn spring-boot:run
```

If successful, you'll see:
- Tables created automatically
- "Authentication Service Started Successfully!" message

## MongoDB Database Setup (Other Services)

### 1. Install MongoDB

#### Windows:
```bash
# Download MongoDB Community Server from https://www.mongodb.com/try/download/community
# Run the installer and follow the setup wizard
# MongoDB will start automatically as a Windows service
```

#### macOS:
```bash
brew tap mongodb/brew
brew install mongodb-community
brew services start mongodb-community
```

#### Linux (Ubuntu):
```bash
wget -qO - https://www.mongodb.org/static/pgp/server-7.0.asc | sudo apt-key add -
echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/7.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-7.0.list
sudo apt update
sudo apt install -y mongodb-org
sudo systemctl start mongod
sudo systemctl enable mongod
```

### 2. Create Databases

MongoDB creates databases automatically when you first write to them. The services will create:

- `ekart_products` - Product & Inventory Service
- `ekart_orders` - Order Processing Service
- `ekart_cart` - Cart & Wishlist Service
- `ekart_reviews` - Review & Recommendation Service

### 3. Database Collections

Each service creates its own collections automatically:

**Product Service Collections:**
- `products` - Product documents
- `categories` - Category documents

**Order Service Collections:**
- `orders` - Order documents

**Cart Service Collections:**
- `carts` - Cart documents
- `wishlists` - Wishlist documents

**Review Service Collections:**
- `reviews` - Review documents

### 4. Connection Configuration

Each service has its MongoDB connection in `application.yml`:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/ekart_products
      auto-index-creation: true
```

**Connection URL Format:**
```
mongodb://[username:password@]host:port/database
```

**Default Configuration:**
- Host: `localhost`
- Port: `27017`
- No authentication (development)
- Auto-index creation: `true`

### 5. Verify Connection

Start any MongoDB service:
```bash
cd backend/product-service
mvn spring-boot:run
```

If successful, you'll see:
- Collections created automatically
- Service started successfully message

### 6. MongoDB Compass (Optional GUI)

Download MongoDB Compass from: https://www.mongodb.com/try/download/compass

Connect using:
- Connection String: `mongodb://localhost:27017`
- Click "Connect"

You can view all databases and collections through the GUI.

## Testing Database Connections

### MySQL Test
```bash
# From authentication-service directory
mvn spring-boot:run

# Check logs for:
# - "HikariPool-1 - Starting..."
# - "Hibernate: create table users..."
```

### MongoDB Test
```bash
# From product-service directory
mvn spring-boot:run

# Check logs for:
# - "MongoDbClient connected"
# - "Created collection products"
```

## Common Issues & Solutions

### MySQL Issues

**Issue: "Access denied for user 'root'@'localhost'"**
```bash
# Reset MySQL root password
mysql -u root -p
ALTER USER 'root'@'localhost' IDENTIFIED BY 'newpassword';
FLUSH PRIVILEGES;
```

**Issue: "Unknown database 'ekart_auth'"**
- The service will create it automatically with `createDatabaseIfNotExist=true`
- Or create manually: `CREATE DATABASE ekart_auth;`

**Issue: "Communications link failure"**
- Check if MySQL service is running
- Windows: Check Services → MySQL
- Linux: `sudo systemctl status mysql`
- macOS: `brew services list`

### MongoDB Issues

**Issue: "Connection refused"**
```bash
# Check if MongoDB is running
# Windows: Check Services → MongoDB
# Linux: sudo systemctl status mongod
# macOS: brew services list

# Start MongoDB if not running
# Windows: Start service from Services
# Linux: sudo systemctl start mongod
# macOS: brew services start mongodb-community
```

**Issue: "Authentication failed"**
- Default MongoDB installation doesn't require authentication
- If you enabled authentication, update connection URL:
  ```yaml
  uri: mongodb://username:password@localhost:27017/database
  ```

**Issue: Port 27017 already in use**
```bash
# Kill process using port 27017
# Windows:
netstat -ano | findstr :27017
taskkill /PID <PID> /F

# Linux/macOS:
lsof -i :27017
kill -9 <PID>
```

## Production Database Setup

### MySQL Production
1. Create a dedicated database user:
```sql
CREATE USER 'ekart_user'@'%' IDENTIFIED BY 'strong_password';
GRANT ALL PRIVILEGES ON ekart_auth.* TO 'ekart_user'@'%';
FLUSH PRIVILEGES;
```

2. Enable SSL in connection URL:
```yaml
url: jdbc:mysql://localhost:3306/ekart_auth?useSSL=true&requireSSL=true
```

3. Use connection pooling (already configured with HikariCP)

### MongoDB Production
1. Enable authentication:
```bash
# Create admin user
mongosh
use admin
db.createUser({
  user: "admin",
  pwd: "strong_password",
  roles: ["root"]
})
```

2. Update connection URL:
```yaml
uri: mongodb://ekart_user:strong_password@localhost:27017/ekart_products?authSource=admin
```

3. Use MongoDB Atlas for cloud hosting (recommended for production)

## Backup & Restore

### MySQL Backup
```bash
# Backup
mysqldump -u root -p ekart_auth > ekart_auth_backup.sql

# Restore
mysql -u root -p ekart_auth < ekart_auth_backup.sql
```

### MongoDB Backup
```bash
# Backup
mongodump --db ekart_products --out /backup/mongodb

# Restore
mongorestore --db ekart_products /backup/mongodb/ekart_products
```

## Next Steps

After setting up databases:
1. Start all backend services
2. Test API endpoints using Swagger UI
3. Import Postman collection for API testing
4. Update frontend to connect to backend APIs
5. Deploy to production environment
