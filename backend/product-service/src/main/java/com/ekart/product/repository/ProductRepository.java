package com.ekart.product.repository;

import com.ekart.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Product entity
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Product findByProductId(Long productId);

    List<Product> findByCategory(String category);

    List<Product> findByCategoryAndSubcategory(String category, String subcategory);

    List<Product> findBySellerId(Long sellerId);

    List<Product> findByIsActiveTrue();

    Page<Product> findByIsActiveTrue(Pageable pageable);

    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<Product> searchByName(String keyword);

    @Query("{ 'name': { $regex: ?0, $options: 'i' }, 'isActive': true }")
    Page<Product> searchByNameAndActive(String keyword, Pageable pageable);

    @Query("{ 'category': ?0, 'isActive': true }")
    Page<Product> findByCategoryAndActive(String category, Pageable pageable);

    @Query("{ 'category': ?0, 'subcategory': ?1, 'isActive': true }")
    Page<Product> findByCategoryAndSubcategoryAndActive(String category, String subcategory, Pageable pageable);

    boolean existsByProductId(Long productId);
}
