package br.com.fiap.springdatajpa.controller;

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

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller de categoria, utilizado para definir a categoria de um produto.
 */
@RestController
@RequestMapping("/persistence/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    /**
     * Construtor padrão da classe com injeção do service de categoria
     */
    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Busca todas as categorias cadastradas
     * @return lista de categorias
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponse> categoriesResponse = new ArrayList<>();
        categories.stream().forEach(category -> categoriesResponse.add(
                new CategoryResponse(category.getId(), category.getName())));

        return ResponseEntity.ok(categoriesResponse);
    }

    /**
     * Busca uma categoria cadastrada a partir do seu id
     * @param id id da categoria
     * @return categoria
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable("id") Integer id) {
        Category categoryResponse = categoryService.getCategoryById(id);

        return ResponseEntity.ok(new CategoryResponse(categoryResponse.getId(), categoryResponse.getName()));
    }

    /**
     * Exclui uma categoria cadastrada a partir do seu id
     * @param id id da categoria
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza uma categoria cadastrada a partir do seu id
     * @param id id da categoria
     * @param categoryRequest dados da categoria a serem alterados
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Integer id,
                                            @Valid @RequestBody CategoryRequest categoryRequest) {
        categoryService.updateCategory(new Category(id, categoryRequest.getName()));
        return ResponseEntity.noContent().build();
    }

    /**
     * Cria uma nova categoria
     * @param categoryRequest dados da categoria a ser cadastrada
     * @return categoria cadastrada
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {

        Category category = categoryService.createCategory(new Category(categoryRequest.getName()));

        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/category", category.getId())))
                .body(new CategoryResponse(category.getId(), category.getName()));
    }

}