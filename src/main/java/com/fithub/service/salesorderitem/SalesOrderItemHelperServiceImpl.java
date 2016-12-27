package com.fithub.service.salesorderitem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fithub.domain.OrderDTO;
import com.fithub.domain.Product;
import com.fithub.domain.ProductDTO;
import com.fithub.domain.SalesOrder;
import com.fithub.domain.SalesOrderItem;
import com.fithub.service.product.ProductService;
import com.fithub.service.product.ProductTasksHelperService;

@Service
public class SalesOrderItemHelperServiceImpl implements SalesOrderItemHelperService {

	private final ProductTasksHelperService productTasksHelperService;
	private final SalesOrderItemService salesOrderItemService;
	private final ProductService productService;

	@Autowired
	public SalesOrderItemHelperServiceImpl(SalesOrderItemService salesOrderItemService, ProductService productService,
			ProductTasksHelperService productTasksHelperService) {
		this.salesOrderItemService = salesOrderItemService;
		this.productService = productService;
		this.productTasksHelperService = productTasksHelperService;

	}

	@Override
	public void synchronizeSalesOrderItem(OrderDTO orderDTO, SalesOrder salesOrder) {

		List<SalesOrderItem> salesOrderItemList = new ArrayList<SalesOrderItem>();

		// Create a SalesOrderItem record for each of the product in the order
		for (ProductDTO productInOrder : orderDTO.getOrderProductList()) {
			SalesOrderItem salesOrderItem = new SalesOrderItem();

			// Set product for order item
			Product product = new Product();
			product = productTasksHelperService.createProductFromProductDTO(product, productInOrder);
			product = productService.getProductByProductName(product.getProductName());

			// Update the product stock quantity in the product table
			product.setQuantitySold(product.getQuantitySold() + productInOrder.getQuantityInCart().intValue());
			product.setStockQuantity(product.getStockQuantity() - product.getQuantitySold());

			// Set the sales order item record
			salesOrderItem.setProduct(product);
			salesOrderItem.setSalesOrder(salesOrder);
			salesOrderItem.setSalesOrderItemQuantitySold(productInOrder.getQuantityInCart().intValue());
			salesOrderItemList.add(salesOrderItem);

		}
		// Save the SalesOrderItem records created for each sales order
		salesOrderItemService.saveSalesOrderItemList(salesOrderItemList);
	}

}
