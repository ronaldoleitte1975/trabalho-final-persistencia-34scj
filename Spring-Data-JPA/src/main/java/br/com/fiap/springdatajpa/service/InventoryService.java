package br.com.fiap.springdatajpa.service;

import br.com.fiap.springdatajpa.model.Inventory;

import java.util.List;

public interface InventoryService {
	List<Inventory> getInventory();
	Inventory getInventoryItemByProductId(Integer id);
	Inventory createInventoryItem(Inventory inventory);
	void updateInventoryItem(Inventory inventory);
	void deleteInventoryItem(Integer id);
}
