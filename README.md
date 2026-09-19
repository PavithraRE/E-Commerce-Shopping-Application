# E-Kart E-Commerce Application

A full-stack e-commerce shopping application with React frontend and Spring Boot microservices backend.

## 🏗️ Architecture

### Frontend (React)
- **Framework**: React 19 with Create React App
- **Routing**: React Router DOM
- **State Management**: React Context API
- **HTTP Client**: Axios
- **Deployment**: Vercel

### Backend (Spring Boot Microservices)
- **Authentication Service** (Port 8081) - MySQL
  - User Registration & Login
  - JWT Authentication
  - Role-Based Access Control (RBAC)
  - Token Refresh

- **Product & Inventory Service** (Port 8082) - MongoDB
  - Product CRUD Operations
  - Category Management
  - Search & Filtering
  - Inventory Tracking

- **Order Processing Service** (Port 8083) - MongoDB
  - Order Creation & Management
  - Status Tracking
  - Payment Processing
  - Returns & Refunds

- **Cart & Wishlist Service** (Port 8084) - MongoDB
  - Cart Operations
  - Wishlist Management
  - Stock Validation
  - Persistent Storage

- **Review & Recommendation Service** (Port 8085) - MongoDB
  - Product Reviews
  - Ratings System
  - Recommendation Engine
  - Admin Moderation

## 📁 Project Structure

```
E-Commerce_Shopping_App/
├── my-app/                          # Frontend (React)
│   ├── public/
│   ├── src/
│   │   ├── api/                    # API integration
│   │   │   ├── authApi.js
│   │   │   ├── productApi.js
│   │   │   ├── orderApi.js
│   │   │   ├── cartApi.js
│   │   │   └── reviewApi.js
│   │   ├── components/             # Reusable components
│   │   ├── context/                # State management
│   │   ├── data/                   # Static data
│   │   ├── pages/                  # Page components
│   │   ├── App.js
│   │   └── index.js
│   ├── .env                        # Environment variables (dev)
│   ├── .env.production            # Environment variables (prod)
│   ├── package.json
│   └── README.md
│
├── backend/                         # Backend (Spring Boot)
│   ├── authentication-service/      # Auth Service (MySQL)
│   ├── product-service/            # Product Service (MongoDB)
│   ├── order-service/              # Order Service (MongoDB)
│   ├── cart-service/               # Cart Service (MongoDB)
│   ├── review-service/             # Review Service (MongoDB)
│   ├── docker-compose.yml
│   ├── DATABASE_SETUP.md
│   ├── VS_CODE_SETUP.md
│   ├── DOCKER_SETUP.md
│   ├── POSTMAN_COLLECTION.md
│   └── DEPLOYMENT_GUIDE.md
│
└── README.md                        # This file
```

## 🚀 Quick Start

### Prerequisites

- **Java 17+**: https://adoptium.net/
- **Maven 3.9+**: https://maven.apache.org/
- **Node.js 18+**: https://nodejs.org/
- **MySQL 8.0+**: https://dev.mysql.com/downloads/
- **MongoDB 7.0+**: https://www.mongodb.com/try/download/community
- **Docker** (optional): https://www.docker.com/products/docker-desktop/

### Database Setup

#### MySQL (Authentication Service)

```bash
# Start MySQL service
# Windows: Start MySQL from Services
# macOS: brew services start mysql
# Linux: sudo systemctl start mysql

# Create database
mysql -u root -p
CREATE DATABASE ekart_auth;
EXIT;
```

#### MongoDB (Other Services)

```bash
# Start MongoDB service
# Windows: Start MongoDB from Services
# macOS: brew services start mongodb-community
# Linux: sudo systemctl start mongod

# MongoDB creates databases automatically
```

### Backend Setup

#### Option 1: Run with Maven (Development)

```bash
# Navigate to backend directory
cd backend

# Start Authentication Service
cd authentication-service
mvn spring-boot:run

# Start Product Service (new terminal)
cd ../product-service
mvn spring-boot:run

# Start Order Service (new terminal)
cd ../order-service
mvn spring-boot:run

# Start Cart Service (new terminal)
cd ../cart-service
mvn spring-boot:run

# Start Review Service (new terminal)
cd ../review-service
mvn spring-boot:run
```

#### Option 2: Run with Docker (Recommended)

```bash
cd backend
docker-compose up --build
```

### Frontend Setup

```bash
# Navigate to frontend directory
cd my-app

# Install dependencies
npm install

# Start development server
npm start
```

The frontend will be available at: http://localhost:3000

## 🔌 API Endpoints

### Authentication Service (Port 8081)

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `POST /api/auth/refresh` - Refresh access token
- `GET /api/auth/me` - Get current user
- `GET /api/auth/user/{userId}` - Get user by ID

**Swagger UI**: http://localhost:8081/api/auth/swagger-ui.html

### Product Service (Port 8082)

- `POST /api/products/products` - Create product
- `GET /api/products/products` - Get all products (paginated)
- `GET /api/products/products/{productId}` - Get product by ID
- `GET /api/products/products/category/{category}` - Get products by category
- `GET /api/products/products/search` - Search products
- `PUT /api/products/products/{productId}` - Update product
- `DELETE /api/products/products/{productId}` - Delete product

**Swagger UI**: http://localhost:8082/api/products/swagger-ui.html

### Order Service (Port 8083)

- `POST /api/orders/orders` - Create order
- `GET /api/orders/orders/order-id/{orderId}` - Get order by order ID
- `GET /api/orders/orders/user/{userId}` - Get orders by user ID
- `PATCH /api/orders/orders/{orderId}/status` - Update order status
- `PATCH /api/orders/orders/{orderId}/cancel` - Cancel order

**Swagger UI**: http://localhost:8083/api/orders/swagger-ui.html

### Cart Service (Port 8084)

- `GET /api/cart/cart/{userId}` - Get cart
- `POST /api/cart/cart/{userId}/items` - Add item to cart
- `PUT /api/cart/cart/{userId}/items/{productId}` - Update item quantity
- `DELETE /api/cart/cart/{userId}/items/{productId}` - Remove item from cart
- `DELETE /api/cart/cart/{userId}` - Clear cart
- `GET /api/cart/wishlist/{userId}` - Get wishlist
- `POST /api/cart/wishlist/{userId}/items` - Add item to wishlist
- `DELETE /api/cart/wishlist/{userId}/items/{productId}` - Remove item from wishlist

**Swagger UI**: http://localhost:8084/api/cart/swagger-ui.html

### Review Service (Port 8085)

- `POST /api/reviews/reviews` - Create review
- `GET /api/reviews/reviews/product/{productId}` - Get reviews by product
- `GET /api/reviews/reviews/user/{userId}` - Get reviews by user
- `GET /api/reviews/reviews/product/{productId}/average-rating` - Get average rating
- `GET /api/reviews/reviews/product/{productId}/count` - Get review count
- `PUT /api/reviews/reviews/{id}` - Update review
- `DELETE /api/reviews/reviews/{id}` - Delete review

**Swagger UI**: http://localhost:8085/api/reviews/swagger-ui.html

## 🧪 Testing

### Postman Collection

See `backend/POSTMAN_COLLECTION.md` for complete Postman testing guide.

### Manual Testing Steps

1. **Register a User**:
   - POST to `http://localhost:8081/api/auth/register`
   - Body: `{"name":"John","email":"john@example.com","password":"password123","role":"BUYER"}`

2. **Login**:
   - POST to `http://localhost:8081/api/auth/login`
   - Body: `{"email":"john@example.com","password":"password123"}`
   - Copy `accessToken` from response

3. **Create Product** (as Seller):
   - POST to `http://localhost:8082/api/products/products`
   - Headers: `Authorization: Bearer {accessToken}`
   - Body: Product data

4. **Add to Cart**:
   - POST to `http://localhost:8084/api/cart/cart/1/items`
   - Body: Cart item data

5. **Create Order**:
   - POST to `http://localhost:8083/api/orders/orders`
   - Body: Order data

6. **Add Review**:
   - POST to `http://localhost:8085/api/reviews/reviews`
   - Body: Review data

## 🛠️ Development

### VS Code Setup

See `backend/VS_CODE_SETUP.md` for detailed VS Code setup instructions.

### Required VS Code Extensions

- Extension Pack for Java
- Spring Boot Extension Pack
- Maven for Java
- Lombok Annotations Support
- SonarLint
- REST Client or Thunder Client
- MongoDB for VS Code
- MySQL

### Running Services in VS Code

1. Open backend folder in VS Code
2. Open Spring Boot Dashboard (left sidebar)
3. Click play button next to each service
4. Services start in integrated terminals

## 🐳 Docker Deployment

### Quick Start with Docker Compose

```bash
cd backend
docker-compose up --build
```

This will start:
- MySQL database
- MongoDB database
- All 5 Spring Boot services

### Individual Service Docker Commands

```bash
# Build specific service
docker-compose build authentication-service

# Start specific service
docker-compose up authentication-service

# View logs
docker-compose logs -f authentication-service

# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

See `backend/DOCKER_SETUP.md` for detailed Docker instructions.

## 🌐 Deployment

### Frontend (Vercel)

See `DEPLOYMENT_GUIDE.md` for detailed deployment instructions.

Quick steps:
1. Push frontend to GitHub
2. Import repository in Vercel
3. Configure build settings
4. Deploy

### Backend (Render)

See `DEPLOYMENT_GUIDE.md` for detailed deployment instructions.

Quick steps:
1. Push backend to GitHub
2. Create web services in Render for each microservice
3. Configure environment variables
4. Deploy

## 📊 Database Schema

### MySQL (Authentication Service)

**Users Table:**
- `id` (BIGINT, PRIMARY KEY)
- `email` (VARCHAR, UNIQUE)
- `password` (VARCHAR)
- `name` (VARCHAR)
- `role` (ENUM: BUYER, SELLER, ADMIN)
- `store_name` (VARCHAR)
- `phone` (VARCHAR)
- `address` (VARCHAR)
- `active` (BOOLEAN)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### MongoDB (Other Services)

**Products Collection:**
- Product details, categories, inventory
- Seller information
- Ratings and reviews

**Orders Collection:**
- Order details, items, shipping
- Payment information
- Status tracking

**Carts Collection:**
- Cart items per user
- Quantity and pricing

**Wishlists Collection:**
- Wishlist items per user
- Product references

**Reviews Collection:**
- Review details
- Ratings and comments
- User information

## 🔐 Security Features

- JWT-based authentication
- Role-based access control (RBAC)
- Password encryption (BCrypt)
- Token refresh mechanism
- CORS configuration
- Input validation
- SQL injection prevention
- XSS protection

## 📱 Features

### Buyer Features
- User registration and login
- Browse products by category
- Search and filter products
- Add to cart and wishlist
- Checkout with multiple payment options
- Order tracking
- Product reviews and ratings
- Profile settings

### Seller Features
- Product management (CRUD)
- Inventory tracking
- Order management
- Sales analytics
- Store settings

### Admin Features
- User management
- Product moderation
- Review moderation
- Order management
- Analytics dashboard

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📝 License

This project is licensed under the MIT License.

## 📞 Support

For support, email support@ekart.com or open an issue in the repository.

## 🙏 Acknowledgments

- React team for the amazing framework
- Spring Boot team for the robust backend framework
- MongoDB for the flexible NoSQL database
- Vercel for hosting the frontend
- Render for hosting the backend services

---

**Built with ❤️ using React, Spring Boot, MySQL, and MongoDB**
