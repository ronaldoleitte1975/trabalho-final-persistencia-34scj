package br.com.fiap.springdatajpa.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.fiap.springdatajpa.model.SalesOrder;

public interface SalesOrderRepository extends CrudRepository<SalesOrder, Integer>  {

}
