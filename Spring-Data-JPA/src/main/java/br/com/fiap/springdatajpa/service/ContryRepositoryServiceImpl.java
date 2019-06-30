package br.com.fiap.springdatajpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.springdatajpa.model.Country;
import br.com.fiap.springdatajpa.repository.CountryRepository;

@Service
public class ContryRepositoryServiceImpl implements ContryRepositoryService {

	@Autowired
	private CountryRepository countryRepository;

	@Override
	public Country addCountry(Country country) {
		return countryRepository.save(country);
	}

}
