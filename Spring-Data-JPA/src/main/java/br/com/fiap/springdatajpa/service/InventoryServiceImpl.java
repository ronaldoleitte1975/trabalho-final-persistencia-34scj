package br.com.fiap.springdatajpa.service;

import br.com.fiap.springdatajpa.model.Inventory;
import br.com.fiap.springdatajpa.model.InventoryItem;
import br.com.fiap.springdatajpa.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InventoryServiceImpl implements InventoryService {
	@Autowired
	private InventoryRepository inventoryRepository;

	@Override
	public Inventory getInventory() {
		return null;
	}

	@Override
	public InventoryItem getInventoryItem(Integer id) {
		return null;
	}

	@Override
	public InventoryItem addInventoryItem(InventoryItem inventoryItem) {
		return null;
	}

	@Override
	public InventoryItem updateInventoryItem(InventoryItem inventoryItem) {
		return null;
	}

	@Override
	public void deleteInventoryItem(Integer id) {

	}
}
