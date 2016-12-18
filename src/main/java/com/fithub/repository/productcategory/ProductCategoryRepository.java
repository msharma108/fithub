package com.fithub.repository.productcategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fithub.domain.ProductCategory;

/**
 * Repository for Product category
 *
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

	ProductCategory findOneByCategory(String category);

}
