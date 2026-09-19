# Docker Setup Guide

This guide explains how to set up and run the E-Kart backend services using Docker and Docker Compose.

## Prerequisites

1. **Docker Desktop** (Windows/Mac) or **Docker Engine** (Linux)
   - Download from: https://www.docker.com/products/docker-desktop/
   - Install and start Docker Desktop
   - Verify: `docker --version`

2. **Docker Compose** (usually included with Docker Desktop)
   - Verify: `docker-compose --version`

## Quick Start

### 1. Build and Start All Services

```bash
cd backend
docker-compose up --build
```

This will:
- Build Docker images for all 5 services
- Start MySQL and MongoDB containers
- Start all Spring Boot services
- Create networks and volumes for persistence

### 2. Verify Services Are Running

```bash
# Check all containers
docker-compose ps

# View logs for all services
docker-compose logs -f

# View logs for specific service
docker-compose logs -f authentication-service
```

### 3. Stop Services

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (deletes data)
docker-compose down -v
```

## Individual Service Management

### Start Specific Service

```bash
# Start only authentication service
docker-compose up authentication-service

# Start authentication and product services
docker-compose up authentication-service product-service
```

### Stop Specific Service

```bash
# Stop authentication service
docker-compose stop authentication-service

# Remove specific service
docker-compose rm -f authentication-service
```

### Restart Service

```bash
# Restart authentication service
docker-compose restart authentication-service
```

## Docker Commands Reference

### Build Images

```bash
# Build all images
docker-compose build

# Build specific image
docker-compose build authentication-service

# Rebuild without cache
docker-compose build --no-cache
```

### View Logs

```bash
# View all logs
docker-compose logs

# Follow logs (real-time)
docker-compose logs -f

# View last 100 lines
docker-compose logs --tail=100

# View logs for specific service
docker-compose logs authentication-service
```

### Execute Commands in Container

```bash
# Access authentication service container
docker-compose exec authentication-service bash

# Run command in container
docker-compose exec authentication-service java -version

# Access MySQL container
docker-compose exec mysql mysql -u ekart_user -pekart_password ekart_auth

# Access MongoDB container
docker-compose exec mongodb mongosh
```

### Inspect Containers

```bash
# View container details
docker inspect ekart-auth-service

# View container stats
docker stats ekart-auth-service
```

## Volume Management

### List Volumes

```bash
docker volume ls
```

### Inspect Volume

```bash
docker volume inspect backend_mysql-data
docker volume inspect backend_mongodb-data
```

### Delete Volumes

```bash
# Delete all volumes
docker-compose down -v

# Delete specific volume
docker volume rm backend_mysql-data
```

## Network Management

### List Networks

```bash
docker network ls
```

### Inspect Network

```bash
docker network inspect backend_ekart-network
```

## Troubleshooting

### Port Already in Use

```bash
# Find process using port
netstat -ano | findstr :8081

# Or change port in docker-compose.yml
ports:
  - "8082:8081"  # Map container 8081 to host 8082
```

### Container Not Starting

```bash
# Check logs
docker-compose logs authentication-service

# Check container status
docker-compose ps

# Rebuild image
docker-compose build --no-cache authentication-service
docker-compose up authentication-service
```

### Database Connection Issues

```bash
# Check if database container is running
docker-compose ps mysql
docker-compose ps mongodb

# Check database logs
docker-compose logs mysql
docker-compose logs mongodb

# Restart database
docker-compose restart mysql
docker-compose restart mongodb
```

### Out of Disk Space

```bash
# Clean up unused images
docker image prune -a

# Clean up unused containers
docker container prune

# Clean up unused volumes
docker volume prune

# Clean up everything
docker system prune -a
```

## Production Deployment

### Environment Variables

Create `.env` file in backend directory:

```env
MYSQL_ROOT_PASSWORD=your_secure_password
MYSQL_DATABASE=ekart_auth
MYSQL_USER=ekart_user
MYSQL_PASSWORD=your_secure_password

MONGODB_URI=mongodb://user:password@mongodb-host:27017/ekart_products
```

Update `docker-compose.yml`:

```yaml
environment:
  SPRING_DATASOURCE_PASSWORD: ${MYSQL_PASSWORD}
  SPRING_DATA_MONGODB_URI: ${MONGODB_URI}
```

### Use External Databases

Update `docker-compose.yml` to use external databases:

```yaml
services:
  authentication-service:
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://your-mysql-host:3306/ekart_auth
    depends_on: []  # Remove local database dependency
```

### Resource Limits

Add resource limits to services:

```yaml
services:
  authentication-service:
    deploy:
      resources:
        limits:
          cpus: '1'
          memory: 512M
        reservations:
          cpus: '0.5'
          memory: 256M
```

### Health Checks

Services already include health checks. View health status:

```bash
docker-compose ps
```

## Backup and Restore

### Backup MySQL Data

```bash
# Backup from container
docker-compose exec mysql mysqldump -u ekart_user -pekart_password ekart_auth > backup.sql

# Restore to container
docker-compose exec -T mysql mysql -u ekart_user -pekart_password ekart_auth < backup.sql
```

### Backup MongoDB Data

```bash
# Backup from container
docker-compose exec mongodb mongodump --archive=/data/backup.archive

# Restore to container
docker-compose exec mongodb mongorestore --archive=/data/backup.archive
```

## Monitoring

### View Resource Usage

```bash
# Real-time stats
docker stats

# Specific container
docker stats ekart-auth-service
```

### View Container Logs

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f authentication-service
```

## Security Best Practices

1. **Change Default Passwords**: Update MySQL and MongoDB passwords in docker-compose.yml
2. **Use Secrets**: Use Docker secrets for sensitive data in production
3. **Limit Container Privileges**: Run containers with non-root user
4. **Scan Images**: Use `docker scan` to check for vulnerabilities
5. **Update Regularly**: Keep Docker images updated

## Next Steps

After setting up Docker:
1. Start all services with `docker-compose up`
2. Test APIs using Swagger UI or Postman
3. Deploy to production using Render or similar platform
4. Set up CI/CD pipeline for automated deployments
