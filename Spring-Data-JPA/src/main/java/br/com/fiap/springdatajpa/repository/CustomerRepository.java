package br.com.fiap.springdatajpa.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.fiap.springdatajpa.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}
