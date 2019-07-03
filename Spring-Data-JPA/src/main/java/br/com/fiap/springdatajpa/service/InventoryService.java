package br.com.fiap.springdatajpa.service;

import br.com.fiap.springdatajpa.model.Inventory;
import br.com.fiap.springdatajpa.model.InventoryItem;

public interface InventoryService {
	Inventory getInventory();
	InventoryItem getInventoryItem(Integer id);
	InventoryItem addInventoryItem(InventoryItem inventoryItem);
	InventoryItem updateInventoryItem(InventoryItem inventoryItem);
	void deleteInventoryItem(Integer id);
}
