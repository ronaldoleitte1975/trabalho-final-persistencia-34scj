package br.com.fiap.springdatajpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.springdatajpa.model.SalesOrder;
import br.com.fiap.springdatajpa.repository.SalesOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class SalesOrderServiceImpl implements SalesOrderService{
	
	@Autowired
	private SalesOrderRepository salesOrderRepository;
	
	@Override
	public List<SalesOrder> getAllSalesOrders() {
		List<SalesOrder> salesOrders = new ArrayList<>();
		salesOrderRepository.findAll().forEach(salesOrder -> salesOrders.add(salesOrder));
		return salesOrders;
	}

	@Override
	public SalesOrder getSalesOrderById(Integer id) {
		return salesOrderRepository.findById(id).get();
	}

	@Override
	public SalesOrder addSalesOrder(SalesOrder salesOrder) {
		return salesOrderRepository.save(salesOrder);
	}

	@Override
	public SalesOrder updateSalesOrder(SalesOrder salesOrder) {
		return salesOrderRepository.save(salesOrder);
	}

	@Override
	public void deleteSalesOrder(Integer id) {
		salesOrderRepository.delete(salesOrderRepository.findById(id).get());
		
	}
	
}
