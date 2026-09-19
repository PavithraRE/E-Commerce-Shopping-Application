# Deployment Guide

This guide explains how to deploy the E-Kart e-commerce application to production using Vercel for the frontend and Render for the backend services.

## Architecture Overview

### Frontend (React)
- **Platform**: Vercel
- **Framework**: React with Create React App
- **URL**: https://your-ekart.vercel.app

### Backend (Spring Boot Microservices)
- **Platform**: Render (Docker)
- **Services**: 5 Spring Boot microservices
- **Databases**: 
  - MySQL (Authentication Service) - Render MySQL
  - MongoDB (Other Services) - MongoDB Atlas

## Prerequisites

1. **Vercel Account**
   - Sign up at https://vercel.com
   - Connect your GitHub repository

2. **Render Account**
   - Sign up at https://render.com
   - Connect your GitHub repository

3. **MongoDB Atlas Account**
   - Sign up at https://www.mongodb.com/cloud/atlas
   - Create a free cluster

4. **Domain Names** (Optional)
   - Purchase domain from Namecheap, GoDaddy, etc.
   - Configure DNS for both platforms

## Frontend Deployment (Vercel)

### Step 1: Prepare Frontend

1. Update `.env.production` in `my-app/`:
```env
REACT_APP_AUTH_API_URL=https://your-auth-service.onrender.com/api/auth
REACT_APP_PRODUCT_API_URL=https://your-product-service.onrender.com/api/products
REACT_APP_ORDER_API_URL=https://your-order-service.onrender.com/api/orders
REACT_APP_CART_API_URL=https://your-cart-service.onrender.com/api/cart
REACT_APP_REVIEW_API_URL=https://your-review-service.onrender.com/api/reviews
```

2. Commit and push to GitHub:
```bash
cd my-app
git add .
git commit -m "Update production environment variables"
git push origin main
```

### Step 2: Deploy to Vercel

1. Go to https://vercel.com/dashboard
2. Click "Add New Project"
3. Import your GitHub repository
4. Configure project settings:
   - **Framework Preset**: Create React App
   - **Root Directory**: `my-app`
   - **Build Command**: `npm run build`
   - **Output Directory**: `build`
   - **Environment Variables**: Add from `.env.production`

5. Click "Deploy"

6. Wait for deployment to complete (2-3 minutes)

7. Your frontend will be live at: `https://your-project.vercel.app`

### Step 3: Configure Custom Domain (Optional)

1. In Vercel dashboard, go to project settings
2. Click "Domains"
3. Add your custom domain
4. Update DNS records as instructed by Vercel

## Backend Deployment (Render)

### Step 1: Prepare MongoDB Atlas

1. Create MongoDB Atlas account and cluster
2. Create database user with read/write permissions
3. Get connection string:
```
mongodb+srv://username:password@cluster.mongodb.net/ekart_products?retryWrites=true&w=majority
```
4. Whitelist IP addresses (0.0.0.0/0 for Render)

### Step 2: Prepare Backend Services

#### Update application.yml for each service

**Authentication Service** (`authentication-service/src/main/resources/application.yml`):
```yaml
spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

jwt:
  secret: ${JWT_SECRET}
  expiration: ${JWT_EXPIRATION}
  refresh-expiration: ${JWT_REFRESH_EXPIRATION}
```

**Product Service** (`product-service/src/main/resources/application.yml`):
```yaml
spring:
  data:
    mongodb:
      uri: ${MONGODB_URI}
```

**Order Service** (`order-service/src/main/resources/application.yml`):
```yaml
spring:
  data:
    mongodb:
      uri: ${MONGODB_URI}
```

**Cart Service** (`cart-service/src/main/resources/application.yml`):
```yaml
spring:
  data:
    mongodb:
      uri: ${MONGODB_URI}
```

**Review Service** (`review-service/src/main/resources/application.yml`):
```yaml
spring:
  data:
    mongodb:
      uri: ${MONGODB_URI}
```

### Step 3: Deploy Authentication Service

1. Go to Render dashboard
2. Click "New" → "Web Service"
3. Connect your GitHub repository
4. Configure:
   - **Name**: `ekart-auth-service`
   - **Root Directory**: `backend/authentication-service`
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/authentication-service-1.0.0.jar`
   - **Environment Variables**:
     ```
     DATABASE_URL=jdbc:mysql://your-mysql-host:3306/ekart_auth
     DB_USERNAME=your_db_user
     DB_PASSWORD=your_db_password
     JWT_SECRET=your_secure_jwt_secret_key
     JWT_EXPIRATION=86400000
     JWT_REFRESH_EXPIRATION=604800000
     ```
   - **Database**: Create PostgreSQL or MySQL database in Render

5. Click "Create Web Service"

### Step 4: Deploy Product Service

1. Create new web service in Render
2. Configure:
   - **Name**: `ekart-product-service`
   - **Root Directory**: `backend/product-service`
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/product-service-1.0.0.jar`
   - **Environment Variables**:
     ```
     MONGODB_URI=mongodb+srv://user:pass@cluster.mongodb.net/ekart_products
     ```

3. Click "Create Web Service"

### Step 5: Deploy Order Service

1. Create new web service in Render
2. Configure:
   - **Name**: `ekart-order-service`
   - **Root Directory**: `backend/order-service`
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/order-service-1.0.0.jar`
   - **Environment Variables**:
     ```
     MONGODB_URI=mongodb+srv://user:pass@cluster.mongodb.net/ekart_orders
     ```

3. Click "Create Web Service"

### Step 6: Deploy Cart Service

1. Create new web service in Render
2. Configure:
   - **Name**: `ekart-cart-service`
   - **Root Directory**: `backend/cart-service`
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/cart-service-1.0.0.jar`
   - **Environment Variables**:
     ```
     MONGODB_URI=mongodb+srv://user:pass@cluster.mongodb.net/ekart_cart
     ```

3. Click "Create Web Service"

### Step 7: Deploy Review Service

1. Create new web service in Render
2. Configure:
   - **Name**: `ekart-review-service`
   - **Root Directory**: `backend/review-service`
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/review-service-1.0.0.jar`
   - **Environment Variables**:
     ```
     MONGODB_URI=mongodb+srv://user:pass@cluster.mongodb.net/ekart_reviews
     ```

3. Click "Create Web Service"

### Step 8: Update Frontend Environment Variables

1. Go to Vercel project settings
2. Update environment variables with Render URLs:
```env
REACT_APP_AUTH_API_URL=https://ekart-auth-service.onrender.com/api/auth
REACT_APP_PRODUCT_API_URL=https://ekart-product-service.onrender.com/api/products
REACT_APP_ORDER_API_URL=https://ekart-order-service.onrender.com/api/orders
REACT_APP_CART_API_URL=https://ekart-cart-service.onrender.com/api/cart
REACT_APP_REVIEW_API_URL=https://ekart-review-service.onrender.com/api/reviews
```

3. Redeploy frontend

## Database Setup

### MySQL (Render)

1. In Render, create a new PostgreSQL/MySQL database
2. Get connection details
3. Add to Authentication Service environment variables

### MongoDB Atlas

1. Create cluster in MongoDB Atlas
2. Create database user
3. Get connection string
4. Add to each service environment variables

## Monitoring and Logs

### Vercel Monitoring

1. Go to Vercel dashboard
2. Select your project
3. View logs, analytics, and deployments

### Render Monitoring

1. Go to Render dashboard
2. Select each service
3. View logs, metrics, and events

### MongoDB Atlas Monitoring

1. Go to MongoDB Atlas dashboard
2. View cluster metrics, logs, and performance

## CI/CD Pipeline

### GitHub Actions (Optional)

Create `.github/workflows/deploy.yml`:

```yaml
name: Deploy Backend

on:
  push:
    branches: [ main ]

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 17
      uses: actions/setup-java@v2
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Build with Maven
      run: mvn clean package -DskipTests
    
    - name: Deploy to Render
      run: |
        curl -X POST https://api.render.com/v1/services/your-service-id/deploys \
          -H "Authorization: Bearer ${{ secrets.RENDER_API_KEY }}"
```

## Security Best Practices

### Environment Variables

1. Never commit `.env` files to Git
2. Use strong, unique passwords
3. Rotate secrets regularly
4. Use different secrets for dev and prod

### API Security

1. Enable HTTPS everywhere
2. Use CORS properly
3. Implement rate limiting
4. Validate all inputs
5. Use prepared statements

### Database Security

1. Use strong passwords
2. Enable SSL/TLS connections
3. Regular backups
4. Limit database user permissions
5. Monitor database access logs

## Scaling

### Horizontal Scaling

1. Enable auto-scaling in Render
2. Use load balancers
3. Implement caching (Redis)
4. Use CDN for static assets

### Database Scaling

1. MongoDB Atlas auto-scaling
2. Render database scaling
3. Read replicas for read-heavy operations
4. Connection pooling

## Troubleshooting

### Deployment Failures

1. Check build logs in Render/Vercel
2. Verify environment variables
3. Check database connectivity
4. Review service dependencies

### Runtime Errors

1. Check service logs
2. Verify database connections
3. Check API endpoints
4. Monitor resource usage

### Performance Issues

1. Enable caching
2. Optimize database queries
3. Use CDN for static assets
4. Implement lazy loading

## Cost Optimization

### Render Free Tier

- Authentication Service: $0/month (free tier)
- Product Service: $0/month (free tier)
- Order Service: $0/month (free tier)
- Cart Service: $0/month (free tier)
- Review Service: $0/month (free tier)

### MongoDB Atlas Free Tier

- 512 MB storage
- Shared RAM
- Sufficient for development/small production

### Vercel Free Tier

- 100 GB bandwidth/month
- Unlimited deployments
- Sufficient for most projects

## Backup Strategy

### Database Backups

1. MongoDB Atlas: Automated backups
2. Render MySQL: Manual backups via pg_dump
3. Export data regularly

### Code Backups

1. GitHub repository
2. Branching strategy (main, dev, feature)
3. Tag releases

## Next Steps

After deployment:
1. Monitor application performance
2. Set up error tracking (Sentry)
3. Implement analytics (Google Analytics)
4. Set up uptime monitoring
5. Create backup and recovery procedures
