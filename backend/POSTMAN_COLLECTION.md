# Postman Collection Guide

This guide provides complete Postman testing steps for all E-Kart backend APIs.

## Import Collection

1. Open Postman
2. Click "Import" in the top left
3. Copy the JSON collection below and save as `ekart-backend-collection.json`
4. Import the file into Postman

## Collection JSON

```json
{
  "info": {
    "name": "E-Kart Backend APIs",
    "description": "Complete API collection for E-Kart e-commerce backend services",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "variable": [
    {
      "key": "base_url_auth",
      "value": "http://localhost:8081/api/auth",
      "type": "string"
    },
    {
      "key": "base_url_products",
      "value": "http://localhost:8082/api/products",
      "type": "string"
    },
    {
      "key": "base_url_orders",
      "value": "http://localhost:8083/api/orders",
      "type": "string"
    },
    {
      "key": "base_url_cart",
      "value": "http://localhost:8084/api/cart",
      "type": "string"
    },
    {
      "key": "base_url_reviews",
      "value": "http://localhost:8085/api/reviews",
      "type": "string"
    },
    {
      "key": "access_token",
      "value": "",
      "type": "string"
    }
  ],
  "item": [
    {
      "name": "Authentication Service",
      "item": [
        {
          "name": "Register User",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"name\": \"John Buyer\",\n  \"email\": \"buyer@example.com\",\n  \"password\": \"password123\",\n  \"role\": \"BUYER\"\n}"
            },
            "url": {
              "raw": "{{base_url_auth}}/register",
              "host": ["{{base_url_auth}}"],
              "path": ["register"]
            }
          }
        },
        {
          "name": "Register Seller",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"name\": \"Jane Seller\",\n  \"email\": \"seller@example.com\",\n  \"password\": \"password123\",\n  \"role\": \"SELLER\",\n  \"storeName\": \"Jane's Electronics\"\n}"
            },
            "url": {
              "raw": "{{base_url_auth}}/register",
              "host": ["{{base_url_auth}}"],
              "path": ["register"]
            }
          }
        },
        {
          "name": "Login",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"email\": \"buyer@example.com\",\n  \"password\": \"password123\"\n}"
            },
            "url": {
              "raw": "{{base_url_auth}}/login",
              "host": ["{{base_url_auth}}"],
              "path": ["login"]
            }
          }
        },
        {
          "name": "Refresh Token",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"refreshToken\": \"{{refresh_token}}\"\n}"
            },
            "url": {
              "raw": "{{base_url_auth}}/refresh",
              "host": ["{{base_url_auth}}"],
              "path": ["refresh"]
            }
          }
        },
        {
          "name": "Get Current User",
          "request": {
            "method": "GET",
            "header": [
              {
                "key": "Authorization",
                "value": "Bearer {{access_token}}"
              }
            ],
            "url": {
              "raw": "{{base_url_auth}}/me",
              "host": ["{{base_url_auth}}"],
              "path": ["me"]
            }
          }
        }
      ]
    },
    {
      "name": "Product Service",
      "item": [
        {
          "name": "Create Product",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"productId\": 1,\n  \"name\": \"Wireless Headphones\",\n  \"category\": \"Electronics\",\n  \"subcategory\": \"Headphones\",\n  \"description\": \"Premium wireless headphones with noise cancellation\",\n  \"price\": 699.0,\n  \"stock\": 100,\n  \"rating\": 4.5,\n  \"reviews\": 234,\n  \"image\": \"https://example.com/headphones.jpg\",\n  \"images\": [\"https://example.com/headphones.jpg\"],\n  \"isActive\": true,\n  \"sellerId\": 2,\n  \"sellerName\": \"Jane's Electronics\"\n}"
            },
            "url": {
              "raw": "{{base_url_products}}/products",
              "host": ["{{base_url_products}}"],
              "path": ["products"]
            }
          }
        },
        {
          "name": "Get All Products",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{base_url_products}}/products?page=0&size=10&sortBy=createdAt&sortDir=desc",
              "host": ["{{base_url_products}}"],
              "path": ["products"],
              "query": [
                {
                  "key": "page",
                  "value": "0"
                },
                {
                  "key": "size",
                  "value": "10"
                },
                {
                  "key": "sortBy",
                  "value": "createdAt"
                },
                {
                  "key": "sortDir",
                  "value": "desc"
                }
              ]
            }
          }
        },
        {
          "name": "Get Product by ID",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{base_url_products}}/products/1",
              "host": ["{{base_url_products}}"],
              "path": ["products", "1"]
            }
          }
        },
        {
          "name": "Get Products by Category",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{base_url_products}}/products/category/Electronics",
              "host": ["{{base_url_products}}"],
              "path": ["products", "category", "Electronics"]
            }
          }
        },
        {
          "name": "Search Products",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{base_url_products}}/products/search?keyword=headphones",
              "host": ["{{base_url_products}}"],
              "path": ["products", "search"],
              "query": [
                {
                  "key": "keyword",
                  "value": "headphones"
                }
              ]
            }
          }
        },
        {
          "name": "Update Product",
          "request": {
            "method": "PUT",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"productId\": 1,\n  \"name\": \"Wireless Headphones Pro\",\n  \"category\": \"Electronics\",\n  \"subcategory\": \"Headphones\",\n  \"description\": \"Premium wireless headphones with noise cancellation\",\n  \"price\": 799.0,\n  \"stock\": 90,\n  \"rating\": 4.5,\n  \"reviews\": 234,\n  \"image\": \"https://example.com/headphones.jpg\",\n  \"images\": [\"https://example.com/headphones.jpg\"],\n  \"isActive\": true,\n  \"sellerId\": 2,\n  \"sellerName\": \"Jane's Electronics\"\n}"
            },
            "url": {
              "raw": "{{base_url_products}}/products/1",
              "host": ["{{base_url_products}}"],
              "path": ["products", "1"]
            }
          }
        },
        {
          "name": "Delete Product",
          "request": {
            "method": "DELETE",
            "url": {
              "raw": "{{base_url_products}}/products/1",
              "host": ["{{base_url_products}}"],
              "path": ["products", "1"]
            }
          }
        }
      ]
    },
    {
      "name": "Order Service",
      "item": [
        {
          "name": "Create Order",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"userId\": 1,\n  \"userName\": \"John Buyer\",\n  \"totalAmount\": 699.0,\n  \"shippingAmount\": 49.0,\n  \"taxAmount\": 35.0,\n  \"paymentMethod\": \"CASH_ON_DELIVERY\",\n  \"shippingAddress\": {\n    \"fullName\": \"John Buyer\",\n    \"phone\": \"9876543210\",\n    \"addressLine1\": \"123 Main Street\",\n    \"addressLine2\": \"Apt 4B\",\n    \"city\": \"Mumbai\",\n    \"state\": \"Maharashtra\",\n    \"pinCode\": \"400001\",\n    \"country\": \"India\"\n  },\n  \"items\": [\n    {\n      \"productId\": 1,\n      \"productName\": \"Wireless Headphones\",\n      \"productImage\": \"https://example.com/headphones.jpg\",\n      \"quantity\": 1,\n      \"price\": 699.0,\n      \"totalPrice\": 699.0\n    }\n  ]\n}"
            },
            "url": {
              "raw": "{{base_url_orders}}/orders",
              "host": ["{{base_url_orders}}"],
              "path": ["orders"]
            }
          }
        },
        {
          "name": "Get Order by Order ID",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{base_url_orders}}/orders/order-id/ORD-20240524-12345678",
              "host": ["{{base_url_orders}}"],
              "path": ["orders", "order-id", "ORD-20240524-12345678"]
            }
          }
        },
        {
          "name": "Get Orders by User ID",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{base_url_orders}}/orders/user/1?page=0&size=10",
              "host": ["{{base_url_orders}}"],
              "path": ["orders", "user", "1"],
              "query": [
                {
                  "key": "page",
                  "value": "0"
                },
                {
                  "key": "size",
                  "value": "10"
                }
              ]
            }
          }
        },
        {
          "name": "Update Order Status",
          "request": {
            "method": "PATCH",
            "url": {
              "raw": "{{base_url_orders}}/orders/ORD-20240524-12345678/status?status=CONFIRMED",
              "host": ["{{base_url_orders}}"],
              "path": ["orders", "ORD-20240524-12345678", "status"],
              "query": [
                {
                  "key": "status",
                  "value": "CONFIRMED"
                }
              ]
            }
          }
        },
        {
          "name": "Cancel Order",
          "request": {
            "method": "PATCH",
            "url": {
              "raw": "{{base_url_orders}}/orders/ORD-20240524-12345678/cancel",
              "host": ["{{base_url_orders}}"],
              "path": ["orders", "ORD-20240524-12345678", "cancel"]
            }
          }
        }
      ]
    },
    {
      "name": "Cart Service",
      "item": [
        {
          "name": "Get Cart",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{base_url_cart}}/cart/1",
              "host": ["{{base_url_cart}}"],
              "path": ["cart", "1"]
            }
          }
        },
        {
          "name": "Add Item to Cart",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"productId\": 1,\n  \"productName\": \"Wireless Headphones\",\n  \"productImage\": \"https://example.com/headphones.jpg\",\n  \"price\": 699.0,\n  \"quantity\": 1\n}"
            },
            "url": {
              "raw": "{{base_url_cart}}/cart/1/items",
              "host": ["{{base_url_cart}}"],
              "path": ["cart", "1", "items"]
            }
          }
        },
        {
          "name": "Update Cart Item Quantity",
          "request": {
            "method": "PUT",
            "url": {
              "raw": "{{base_url_cart}}/cart/1/items/1?quantity=2",
              "host": ["{{base_url_cart}}"],
              "path": ["cart", "1", "items", "1"],
              "query": [
                {
                  "key": "quantity",
                  "value": "2"
                }
              ]
            }
          }
        },
        {
          "name": "Remove Item from Cart",
          "request": {
            "method": "DELETE",
            "url": {
              "raw": "{{base_url_cart}}/cart/1/items/1",
              "host": ["{{base_url_cart}}"],
              "path": ["cart", "1", "items", "1"]
            }
          }
        },
        {
          "name": "Clear Cart",
          "request": {
            "method": "DELETE",
            "url": {
              "raw": "{{base_url_cart}}/cart/1",
              "host": ["{{base_url_cart}}"],
              "path": ["cart", "1"]
            }
          }
        },
        {
          "name": "Get Wishlist",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{base_url_cart}}/wishlist/1",
              "host": ["{{base_url_cart}}"],
              "path": ["wishlist", "1"]
            }
          }
        },
        {
          "name": "Add Item to Wishlist",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"productId\": 1,\n  \"productName\": \"Wireless Headphones\",\n  \"productImage\": \"https://example.com/headphones.jpg\",\n  \"price\": 699.0,\n  \"rating\": 4.5,\n  \"reviews\": 234,\n  \"category\": \"Electronics\"\n}"
            },
            "url": {
              "raw": "{{base_url_cart}}/wishlist/1/items",
              "host": ["{{base_url_cart}}"],
              "path": ["wishlist", "1", "items"]
            }
          }
        },
        {
          "name": "Remove Item from Wishlist",
          "request": {
            "method": "DELETE",
            "url": {
              "raw": "{{base_url_cart}}/wishlist/1/items/1",
              "host": ["{{base_url_cart}}"],
              "path": ["wishlist", "1", "items", "1"]
            }
          }
        }
      ]
    },
    {
      "name": "Review Service",
      "item": [
        {
          "name": "Create Review",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"productId\": 1,\n  \"userId\": 1,\n  \"userName\": \"John Buyer\",\n  \"rating\": 5,\n  \"title\": \"Amazing product!\",\n  \"comment\": \"Best headphones I've ever used. Great sound quality and comfort.\"\n}"
            },
            "url": {
              "raw": "{{base_url_reviews}}/reviews",
              "host": ["{{base_url_reviews}}"],
              "path": ["reviews"]
            }
          }
        },
        {
          "name": "Get Reviews by Product ID",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{base_url_reviews}}/reviews/product/1",
              "host": ["{{base_url_reviews}}"],
              "path": ["reviews", "product", "1"]
            }
          }
        },
        {
          "name": "Get Reviews by User ID",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{base_url_reviews}}/reviews/user/1",
              "host": ["{{base_url_reviews}}"],
              "path": ["reviews", "user", "1"]
            }
          }
        },
        {
          "name": "Get Average Rating",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{base_url_reviews}}/reviews/product/1/average-rating",
              "host": ["{{base_url_reviews}}"],
              "path": ["reviews", "product", "1", "average-rating"]
            }
          }
        },
        {
          "name": "Get Review Count",
          "request": {
            "method": "GET",
            "url": {
              "raw": "{{base_url_reviews}}/reviews/product/1/count",
              "host": ["{{base_url_reviews}}"],
              "path": ["reviews", "product", "1", "count"]
            }
          }
        },
        {
          "name": "Update Review",
          "request": {
            "method": "PUT",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"productId\": 1,\n  \"userId\": 1,\n  \"userName\": \"John Buyer\",\n  \"rating\": 4,\n  \"title\": \"Good product\",\n  \"comment\": \"Updated review after using for a week.\"\n}"
            },
            "url": {
              "raw": "{{base_url_reviews}}/reviews/review-id",
              "host": ["{{base_url_reviews}}"],
              "path": ["reviews", "review-id"]
            }
          }
        },
        {
          "name": "Delete Review",
          "request": {
            "method": "DELETE",
            "url": {
              "raw": "{{base_url_reviews}}/reviews/review-id",
              "host": ["{{base_url_reviews}}"],
              "path": ["reviews", "review-id"]
            }
          }
        }
      ]
    }
  ]
}
```

## Testing Steps

### 1. Authentication Service Testing

#### Register a Buyer
1. Open "Authentication Service" folder
2. Click "Register User"
3. Click "Send"
4. Verify response contains user data and tokens
5. Copy `accessToken` to collection variable `access_token`
6. Copy `refreshToken` to collection variable `refresh_token`

#### Register a Seller
1. Click "Register Seller"
2. Click "Send"
3. Verify response contains seller data with storeName

#### Login
1. Click "Login"
2. Click "Send"
3. Verify response contains tokens
4. Update `access_token` variable with new token

#### Refresh Token
1. Ensure `refresh_token` variable is set
2. Click "Refresh Token"
3. Click "Send"
4. Verify new access token is returned
5. Update `access_token` variable

#### Get Current User
1. Ensure `access_token` variable is set
2. Click "Get Current User"
3. Click "Send"
4. Verify user data is returned

### 2. Product Service Testing

#### Create Product
1. Open "Product Service" folder
2. Click "Create Product"
3. Click "Send"
4. Verify product is created with ID
5. Note the productId for subsequent tests

#### Get All Products
1. Click "Get All Products"
2. Click "Send"
3. Verify list of products is returned
4. Check pagination parameters work

#### Get Product by ID
1. Click "Get Product by ID"
2. Replace `1` with actual productId
3. Click "Send"
4. Verify product details are returned

#### Get Products by Category
1. Click "Get Products by Category"
2. Replace `Electronics` with desired category
3. Click "Send"
4. Verify filtered products are returned

#### Search Products
1. Click "Search Products"
2. Replace `headphones` with search keyword
3. Click "Send"
4. Verify search results are returned

#### Update Product
1. Click "Update Product"
2. Replace `1` with actual productId
3. Modify product data as needed
4. Click "Send"
5. Verify product is updated

#### Delete Product
1. Click "Delete Product"
2. Replace `1` with actual productId
3. Click "Send"
4. Verify product is deleted

### 3. Order Service Testing

#### Create Order
1. Open "Order Service" folder
2. Click "Create Order"
3. Modify order data as needed
4. Click "Send"
5. Verify order is created with orderId
6. Note the orderId for subsequent tests

#### Get Order by Order ID
1. Click "Get Order by Order ID"
2. Replace orderId with actual order ID
3. Click "Send"
4. Verify order details are returned

#### Get Orders by User ID
1. Click "Get Orders by User ID"
2. Replace `1` with actual userId
3. Click "Send"
4. Verify user's orders are returned

#### Update Order Status
1. Click "Update Order Status"
2. Replace orderId with actual order ID
3. Change status parameter as needed
4. Click "Send"
5. Verify order status is updated

#### Cancel Order
1. Click "Cancel Order"
2. Replace orderId with actual order ID
3. Click "Send"
4. Verify order is cancelled

### 4. Cart Service Testing

#### Get Cart
1. Open "Cart Service" folder
2. Click "Get Cart"
3. Replace `1` with actual userId
4. Click "Send"
5. Verify cart data is returned

#### Add Item to Cart
1. Click "Add Item to Cart"
2. Replace `1` with actual userId
3. Modify item data as needed
4. Click "Send"
5. Verify item is added to cart

#### Update Cart Item Quantity
1. Click "Update Cart Item Quantity"
2. Replace userId and productId with actual values
3. Modify quantity parameter
4. Click "Send"
5. Verify quantity is updated

#### Remove Item from Cart
1. Click "Remove Item from Cart"
2. Replace userId and productId with actual values
3. Click "Send"
4. Verify item is removed

#### Clear Cart
1. Click "Clear Cart"
2. Replace `1` with actual userId
3. Click "Send"
4. Verify cart is cleared

#### Get Wishlist
1. Click "Get Wishlist"
2. Replace `1` with actual userId
3. Click "Send"
4. Verify wishlist data is returned

#### Add Item to Wishlist
1. Click "Add Item to Wishlist"
2. Replace `1` with actual userId
3. Modify item data as needed
4. Click "Send"
5. Verify item is added to wishlist

#### Remove Item from Wishlist
1. Click "Remove Item from Wishlist"
2. Replace userId and productId with actual values
3. Click "Send"
4. Verify item is removed

### 5. Review Service Testing

#### Create Review
1. Open "Review Service" folder
2. Click "Create Review"
3. Modify review data as needed
4. Click "Send"
5. Verify review is created
6. Note the review ID for subsequent tests

#### Get Reviews by Product ID
1. Click "Get Reviews by Product ID"
2. Replace `1` with actual productId
3. Click "Send"
4. Verify product reviews are returned

#### Get Reviews by User ID
1. Click "Get Reviews by User ID"
2. Replace `1` with actual userId
3. Click "Send"
4. Verify user's reviews are returned

#### Get Average Rating
1. Click "Get Average Rating"
2. Replace `1` with actual productId
3. Click "Send"
4. Verify average rating is returned

#### Get Review Count
1. Click "Get Review Count"
2. Replace `1` with actual productId
3. Click "Send"
4. Verify review count is returned

#### Update Review
1. Click "Update Review"
2. Replace `review-id` with actual review ID
3. Modify review data as needed
4. Click "Send"
5. Verify review is updated

#### Delete Review
1. Click "Delete Review"
2. Replace `review-id` with actual review ID
3. Click "Send"
4. Verify review is deleted

## JWT Token Usage

### Setting Token Variable

After login:
1. Copy the `accessToken` from login response
2. Click on the collection name "E-Kart Backend APIs"
3. Go to "Variables" tab
4. Set `access_token` variable to the copied token
5. Save the collection

### Using Token in Requests

All protected endpoints require JWT token:
1. Ensure `access_token` variable is set
2. Requests automatically include `Authorization: Bearer {{access_token}}` header
3. If token expires, login again and update the variable

## Environment Setup

### Development Environment

Create environment "Development":
- `base_url_auth`: `http://localhost:8081/api/auth`
- `base_url_products`: `http://localhost:8082/api/products`
- `base_url_orders`: `http://localhost:8083/api/orders`
- `base_url_cart`: `http://localhost:8084/api/cart`
- `base_url_reviews`: `http://localhost:8085/api/reviews`

### Production Environment

Create environment "Production":
- `base_url_auth`: `https://your-auth-service.com/api/auth`
- `base_url_products`: `https://your-product-service.com/api/products`
- `base_url_orders`: `https://your-order-service.com/api/orders`
- `base_url_cart`: `https://your-cart-service.com/api/cart`
- `base_url_reviews`: `https://your-review-service.com/api/reviews`

## Test Scripts

Add test scripts to requests for automated testing:

```javascript
// Status code test
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

// Response time test
pm.test("Response time is less than 500ms", function () {
    pm.expect(pm.response.responseTime).to.be.below(500);
});

// JSON body test
pm.test("Response has correct structure", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property("data");
});
```

## Collection Runner

1. Click "Runner" in Postman
2. Select "E-Kart Backend APIs" collection
3. Select environment
4. Click "Run E-Kart Backend APIs"
5. View test results

## Tips

1. **Use Environments**: Separate dev and prod environments
2. **Save Responses**: Save sample responses for documentation
3. **Use Tests**: Add automated tests to critical endpoints
4. **Document**: Add descriptions to each request
5. **Organize**: Group related requests in folders
6. **Version Control**: Export collection and commit to Git
