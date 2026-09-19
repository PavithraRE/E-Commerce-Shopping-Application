package com.ekart.product.service;

import com.ekart.product.dto.ProductRequest;
import com.ekart.product.dto.ProductResponse;
import com.ekart.product.model.Product;
import com.ekart.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Product operations
 * Handles product CRUD, search, and filtering
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Create a new product
     */
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsByProductId(request.getProductId())) {
            throw new RuntimeException("Product with this ID already exists");
        }

        Product product = new Product();
        product.setProductId(request.getProductId());
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setSubcategory(request.getSubcategory());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setRating(request.getRating());
        product.setReviews(request.getReviews());
        product.setImage(request.getImage());
        product.setImages(request.getImages());
        product.setIsActive(request.getIsActive());
        product.setSellerId(request.getSellerId());
        product.setSellerName(request.getSellerName());

        product = productRepository.save(product);
        return new ProductResponse(product);
    }

    /**
     * Get product by ID
     */
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return new ProductResponse(product);
    }

    /**
     * Get product by MongoDB ID
     */
    public ProductResponse getProductByMongoId(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductResponse(product);
    }

    /**
     * Get all products with pagination
     */
    public Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productRepository.findByIsActiveTrue(pageable);
        return products.map(ProductResponse::new);
    }

    /**
     * Get products by category
     */
    public List<ProductResponse> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .filter(Product::getIsActive)
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get products by category and subcategory
     */
    public List<ProductResponse> getProductsByCategoryAndSubcategory(String category, String subcategory) {
        List<Product> products = productRepository.findByCategoryAndSubcategory(category, subcategory);
        return products.stream()
                .filter(Product::getIsActive)
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get products by seller
     */
    public List<ProductResponse> getProductsBySeller(Long sellerId) {
        List<Product> products = productRepository.findBySellerId(sellerId);
        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Search products by name
     */
    public List<ProductResponse> searchProducts(String keyword) {
        List<Product> products = productRepository.searchByName(keyword);
        return products.stream()
                .filter(Product::getIsActive)
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Search products with pagination
     */
    public Page<ProductResponse> searchProductsPaginated(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.searchByNameAndActive(keyword, pageable);
        return products.map(ProductResponse::new);
    }

    /**
     * Update product
     */
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setSubcategory(request.getSubcategory());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImage(request.getImage());
        product.setImages(request.getImages());
        product.setIsActive(request.getIsActive());

        product = productRepository.save(product);
        return new ProductResponse(product);
    }

    /**
     * Update product stock
     */
    public ProductResponse updateStock(Long productId, Integer quantity) {
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        product.setStock(product.getStock() + quantity);
        product = productRepository.save(product);
        return new ProductResponse(product);
    }

    /**
     * Delete product
     */
    public void deleteProduct(Long productId) {
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        productRepository.delete(product);
    }

    /**
     * Toggle product active status
     */
    public ProductResponse toggleProductStatus(Long productId) {
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        product.setIsActive(!product.getIsActive());
        product = productRepository.save(product);
        return new ProductResponse(product);
    }

    /**
     * Update product rating
     */
    public ProductResponse updateRating(Long productId, Double newRating) {
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        int currentReviews = product.getReviews();
        double currentRating = product.getRating();

        // Calculate new average rating
        double totalRating = currentRating * currentReviews + newRating;
        int newReviewCount = currentReviews + 1;
        double averageRating = totalRating / newReviewCount;

        product.setRating(Math.round(averageRating * 10.0) / 10.0);
        product.setReviews(newReviewCount);

        product = productRepository.save(product);
        return new ProductResponse(product);
    }
}
