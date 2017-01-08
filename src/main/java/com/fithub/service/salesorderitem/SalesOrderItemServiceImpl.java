package com.fithub.service.salesorderitem;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fithub.domain.SalesOrderItem;
import com.fithub.repository.salesorderitem.SalesOrderItemRepository;

@Service
public class SalesOrderItemServiceImpl implements SalesOrderItemService {

	private final SalesOrderItemRepository salesOrderItemRepository;

	public SalesOrderItemServiceImpl(SalesOrderItemRepository salesOrderItemRepository) {

		this.salesOrderItemRepository = salesOrderItemRepository;
	}

	@Override
	public List<SalesOrderItem> saveSalesOrderItemList(List<SalesOrderItem> salesOrderItemList) {
		if (salesOrderItemList.isEmpty())
			throw new IllegalArgumentException("SalesOrderItem list is empty");
		return salesOrderItemRepository.save(salesOrderItemList);
	}

	@Override
	public long countNumberOfSalesOrderItemInDatabase() {
		return salesOrderItemRepository.count();
	}

}
