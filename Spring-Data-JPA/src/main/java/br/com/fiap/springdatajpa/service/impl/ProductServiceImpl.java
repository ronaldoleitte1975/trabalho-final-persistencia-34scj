package br.com.fiap.springdatajpa.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import br.com.fiap.springdatajpa.advice.ResponseError;
import br.com.fiap.springdatajpa.model.Category;
import br.com.fiap.springdatajpa.repository.CategoryRepository;
import br.com.fiap.springdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.springdatajpa.model.Product;
import br.com.fiap.springdatajpa.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;

	@Autowired
	public ProductServiceImpl(final ProductRepository productRepository,
							  final CategoryRepository categoryRepository){
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	};

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
		List<Integer> ids = new ArrayList<>();
		product.getCategories().stream().forEach(category -> ids.add(category.getId()));
		Optional<List<Category>> categories = categoryRepository.findByIdIn(ids);

		if (!categories.isPresent()) {
			throw new ResponseError(HttpStatus.CONFLICT, "Categorias não encontradas.");
		} else {
			if (categories.get().size() != ids.size()){
				throw new ResponseError(HttpStatus.CONFLICT, "Nem todas as categorias foram encontradas.");
			}
		}

		return productRepository.save(product);
	}

	@Override
	public void updateProduct(Product product) {
		Product storedProduct = productRepository.findById(product.getId()).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado"));

		List<Integer> ids = new ArrayList<>();
		product.getCategories().stream().forEach(category -> ids.add(category.getId()));
		Optional<List<Category>> categories = categoryRepository.findByIdIn(ids);

		if (!categories.isPresent()) {
			throw new ResponseError(HttpStatus.CONFLICT, "Categorias não encontradas.");
		} else {
			if (categories.get().size() != ids.size()){
				throw new ResponseError(HttpStatus.CONFLICT, "Nem todas as categorias foram encontradas.");
			}
		}

		storedProduct.setName(product.getName());
		storedProduct.setDescription(product.getDescription());
		storedProduct.setPrice(product.getPrice());
		storedProduct.setCategories(product.getCategories());

		productRepository.save(storedProduct);
	}

	@Override
	public void deleteProduct(Integer id) {
		productRepository.delete(productRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado")));
		
	}

}
