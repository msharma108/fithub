package com.fithub.repository.salesorderitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fithub.domain.SalesOrderItem;

/**
 * Repository for performing SalesOrderItem CRUD operations
 *
 */
@Repository
public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Integer> {

}
