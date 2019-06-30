package br.com.fiap.springdatajpa.service;

import java.util.List;

import br.com.fiap.springdatajpa.model.SalesOrder;

public interface SalesOrderService {
	List<SalesOrder> getAllSalesOrders();
	SalesOrder getSalesOrderById(Integer id);
	SalesOrder addSalesOrder(SalesOrder salesOrder);
	SalesOrder updateSalesOrder(SalesOrder salesOrder);
	void deleteSalesOrder(Integer id);
}
