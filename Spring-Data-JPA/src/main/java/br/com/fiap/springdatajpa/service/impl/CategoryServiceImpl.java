package br.com.fiap.springdatajpa.service.impl;

import br.com.fiap.springdatajpa.advice.ResponseError;
import br.com.fiap.springdatajpa.model.Category;
import br.com.fiap.springdatajpa.model.Customer;
import br.com.fiap.springdatajpa.repository.CategoryRepository;
import br.com.fiap.springdatajpa.repository.CustomerRepository;
import br.com.fiap.springdatajpa.service.CategoryService;
import br.com.fiap.springdatajpa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> getAllCategories() {
		List<Category> categories= StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return categories;
	}

	@Override
	public Category getCategoryById(Integer id) {
		return categoryRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
	}

	@Override
	public Category addCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void updateCategory(Category category) {
		Category storedCayegory = categoryRepository.findById(category.getId()).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

		storedCayegory.setName(category.getName());

		categoryRepository.save(storedCayegory);
	}

	@Override
	public void deleteCategory(Integer id) {
		categoryRepository.delete(categoryRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Categoria não encontrada")));
	}
}
