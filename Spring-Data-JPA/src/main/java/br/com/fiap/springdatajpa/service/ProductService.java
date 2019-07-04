package br.com.fiap.springdatajpa.service;

import java.util.List;

import br.com.fiap.springdatajpa.model.Product;

public interface ProductService {
	
	List<Product> getAllProduct();
	Product getProductById(Integer id);
	Product addProduct(Product customer);
	void updateProduct(Product customer);
	void deleteProduct(Integer id);

}
