package br.com.fiap.springdatajpa.service;

import br.com.fiap.springdatajpa.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Integer id);
    Category createCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Integer id);
}