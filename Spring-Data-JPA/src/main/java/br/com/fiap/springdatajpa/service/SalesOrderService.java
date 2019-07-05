package br.com.fiap.springdatajpa.service;

import java.util.List;

import br.com.fiap.springdatajpa.model.SalesOrder;

public interface SalesOrderService {
	List<SalesOrder> getAllSalesOrders();
	SalesOrder getSalesOrderById(Integer id);
	SalesOrder createSalesOrder(SalesOrder salesOrder);
	void updateSalesOrder(SalesOrder salesOrder);
	void deleteSalesOrder(Integer id);
}
