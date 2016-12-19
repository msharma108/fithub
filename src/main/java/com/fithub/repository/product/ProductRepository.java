package com.fithub.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fithub.domain.Product;

/**
 * Repository for Product Domain
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Product findOneByProductName(String productName);

	void deleteByProductName(String productName);

	@Query("select product from Product product, ProductCategory prodcategory where prodcategory.category =:category and product.productCategory = prodcategory.productCategoryId")
	List<Product> getProductsByCategory(@Param("category") String category);

}
