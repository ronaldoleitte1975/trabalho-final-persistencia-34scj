package br.com.fiap.springdatajpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.springdatajpa.model.Customer;
import br.com.fiap.springdatajpa.repository.CustomerRepository;

public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> customers = new ArrayList<>();
		customerRepository.findAll().forEach(customer -> customers.add(customer));
		return customers;
	}

	@Override
	public Customer getCustomerById(Integer id) {
		return customerRepository.findById(id).get();
	}

	@Override
	public Customer addCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void deleteCustomer(Integer id) {
		customerRepository.delete(customerRepository.findById(id).get());
	}
}
