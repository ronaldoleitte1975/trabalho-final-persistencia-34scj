package br.com.fiap.springdatajpa.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.fiap.springdatajpa.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}
