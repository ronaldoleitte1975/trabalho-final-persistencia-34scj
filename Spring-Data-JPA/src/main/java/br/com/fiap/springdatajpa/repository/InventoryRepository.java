package br.com.fiap.springdatajpa.repository;

import br.com.fiap.springdatajpa.model.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Integer>{

}
