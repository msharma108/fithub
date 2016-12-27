package com.fithub.repository.salesorder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fithub.domain.SalesOrder;

/**
 * Repository for SalesOrder
 *
 */
@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {

	@Query("select salesorder from SalesOrder salesorder where salesorder.user=:userName")

	List<SalesOrder> getSalesOrderByUserName(@Param("userName") String userName);
}
