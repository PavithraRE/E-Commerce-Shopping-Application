package com.ekart.product.controller;

import com.ekart.product.dto.ProductRequest;
import com.ekart.product.dto.ProductResponse;
import com.ekart.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Product operations
 * Provides endpoints for product CRUD, search, and filtering
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Create a new product with all details")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by ID", description = "Get product details by product ID")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        ProductResponse response = productService.getProductById(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Get all active products with pagination")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Page<ProductResponse> response = productService.getAllProducts(page, size, sortBy, sortDir);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category", description = "Get all products in a specific category")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable String category) {
        List<ProductResponse> response = productService.getProductsByCategory(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}/subcategory/{subcategory}")
    @Operation(summary = "Get products by category and subcategory", description = "Get products in a specific category and subcategory")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryAndSubcategory(
            @PathVariable String category,
            @PathVariable String subcategory) {
        List<ProductResponse> response = productService.getProductsByCategoryAndSubcategory(category, subcategory);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seller/{sellerId}")
    @Operation(summary = "Get products by seller", description = "Get all products for a specific seller")
    public ResponseEntity<List<ProductResponse>> getProductsBySeller(@PathVariable Long sellerId) {
        List<ProductResponse> response = productService.getProductsBySeller(sellerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search products by name keyword")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword) {
        List<ProductResponse> response = productService.searchProducts(keyword);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/paginated")
    @Operation(summary = "Search products with pagination", description = "Search products with pagination support")
    public ResponseEntity<Page<ProductResponse>> searchProductsPaginated(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProductResponse> response = productService.searchProductsPaginated(keyword, page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    @Operation(summary = "Update product", description = "Update product details")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.updateProduct(productId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productId}/stock")
    @Operation(summary = "Update product stock", description = "Update product stock quantity")
    public ResponseEntity<ProductResponse> updateStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        ProductResponse response = productService.updateStock(productId, quantity);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productId}/toggle-status")
    @Operation(summary = "Toggle product status", description = "Activate or deactivate a product")
    public ResponseEntity<ProductResponse> toggleProductStatus(@PathVariable Long productId) {
        ProductResponse response = productService.toggleProductStatus(productId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete product", description = "Delete a product permanently")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
