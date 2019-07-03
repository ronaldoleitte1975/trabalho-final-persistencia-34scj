package br.com.fiap.springdatajpa.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import br.com.fiap.springdatajpa.advice.ResponseError;
import br.com.fiap.springdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.springdatajpa.model.Product;
import br.com.fiap.springdatajpa.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<Product> getAllProduct() {
		List<Product> product = StreamSupport.stream(productRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return product;
	}

	@Override
	public Product getProductById(Integer id) {
		return productRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}

	@Override
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		Product storedProduct = productRepository.findById(product.getId()).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado"));

		storedProduct.setName(product.getName());
		storedProduct.setDescription(product.getDescription());
		storedProduct.setPrice(product.getPrice());

		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(Integer id) {
		productRepository.delete(productRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado")));
		
	}

}
