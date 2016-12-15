package com.fithub.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fithub.domain.Product;

/**
 * Repository for Product Domain
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findOneByProductName(String productName);

	void deleteByProductName(String productName);

	@Query("select product.* from Product product, ProductCategory prodcategory where prodcategory.category =?1 and product.productCategory = prodcategory.productCategoryId")
	List<Product> getProductsByCategory(String category);

}
