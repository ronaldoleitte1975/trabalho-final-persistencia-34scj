package br.com.fiap.springdatajpa.service.impl;

import br.com.fiap.springdatajpa.advice.ResponseError;
import br.com.fiap.springdatajpa.model.Inventory;
import br.com.fiap.springdatajpa.repository.InventoryRepository;
import br.com.fiap.springdatajpa.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class InventoryServiceImpl implements InventoryService {
	@Autowired
	private InventoryRepository inventoryRepository;

	@Override
	public List<Inventory> getInventory() {
		return StreamSupport.stream(inventoryRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public Inventory getInventoryItemByProductId(Integer id) {
		return inventoryRepository.findByProductId(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado no estoque"));
	}

	@Override
	public Inventory createInventoryItem(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}

	@Override
	public void updateInventoryItem(Inventory inventory) {
		Inventory inventoryCustomer = inventoryRepository.findById(inventory.getInventoryId())
				.orElseThrow(() -> new ResponseError(HttpStatus.NOT_FOUND, "Inventário não encontrado"));

		inventoryCustomer.setProduct(inventory.getProduct());
		inventoryCustomer.setAmount(inventory.getAmount());

		inventoryRepository.save(inventoryCustomer);
	}

	@Override
	public void deleteInventoryItem(Integer id) {
		inventoryRepository.delete(inventoryRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Item do estoque não encontrado")));
	}
}
