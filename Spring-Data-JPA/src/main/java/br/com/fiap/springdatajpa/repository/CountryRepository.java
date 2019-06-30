package br.com.fiap.springdatajpa.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.fiap.springdatajpa.model.Country;

public interface CountryRepository extends CrudRepository<Country, Integer>{

}
