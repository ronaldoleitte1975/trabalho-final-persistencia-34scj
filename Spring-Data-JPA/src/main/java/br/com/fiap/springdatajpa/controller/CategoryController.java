package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.dto.category.CategoryDTO;
import br.com.fiap.springdatajpa.dto.category.CategoryRequest;
import br.com.fiap.springdatajpa.dto.category.CategoryResponse;
import br.com.fiap.springdatajpa.model.Category;
import br.com.fiap.springdatajpa.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persistence/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponse> categoriesResponse = new ArrayList<>();
        categories.stream().forEach(category -> categoriesResponse.add(
                new CategoryResponse(category.getId(), category.getName())));

        return ResponseEntity.ok(categoriesResponse);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable("id") Integer id) {
        Category categoryResponse = categoryService.getCategoryById(id);

        return ResponseEntity.ok(new CategoryResponse(categoryResponse.getId(), categoryResponse.getName()));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Integer id,
                                            @RequestBody CategoryRequest categoryRequest) {

        categoryService.updateCategory(new Category(id, categoryRequest.getName()));

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {

        Category category = categoryService.createCategory(new Category(categoryRequest.getName()));

        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/category", category.getId())))
                .body(new CategoryResponse(category.getId(), category.getName()));
    }

}