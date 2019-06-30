package br.com.fiap.springdatajpa.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.fiap.springdatajpa.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{

}
