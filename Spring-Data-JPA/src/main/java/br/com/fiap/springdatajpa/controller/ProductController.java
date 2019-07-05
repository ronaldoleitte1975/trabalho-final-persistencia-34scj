package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.dto.category.CategoryDTO;
import br.com.fiap.springdatajpa.dto.category.CategoryResponse;
import br.com.fiap.springdatajpa.dto.product.ProductRequest;
import br.com.fiap.springdatajpa.dto.product.ProductResponse;
import br.com.fiap.springdatajpa.model.Category;
import br.com.fiap.springdatajpa.model.Product;
import br.com.fiap.springdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persistence/v1/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(final ProductService productService){
        this.productService = productService;
    };

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<Product> products = productService.getAllProduct();
        List<ProductResponse> productResponse = new ArrayList<>();
        products.stream().forEach(product -> productResponse.add(
                new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        toCategoryResponse(product.getCategories()))));

        return ResponseEntity.ok(productResponse);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Integer id){
        Product productResponse = productService.getProductById(id);

        return ResponseEntity.ok(new ProductResponse(
                productResponse.getId(),
                productResponse.getName(),
                productResponse.getDescription(),
                productResponse.getPrice(),
                toCategoryResponse(productResponse.getCategories())));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id){
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id,
                                           @RequestBody ProductRequest productRequest){

        productService.updateProduct(new Product(id, productRequest.getName(), productRequest.getDescription(),
                productRequest.getPrice(), toCategoriesModel(productRequest.getCategories())));

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        Product product = productService.createProduct(new Product(productRequest.getName(), productRequest.getDescription(),
                productRequest.getPrice(), toCategoriesModel(productRequest.getCategories())));

        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/product", product.getId())))
                .body(new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        toCategoryResponse(product.getCategories())));
    }

    private List<Category> toCategoriesModel(List<CategoryDTO> categoriesDTO) {
        List<Category> categoriesModel = new ArrayList<>();
        categoriesDTO.stream().forEach(category -> categoriesModel.add(new Category(category.getId())));
        return categoriesModel;
    }

    private List<CategoryResponse> toCategoryResponse(List<Category> categories) {
        List<CategoryResponse> categoryResponse = new ArrayList<>();
        categories.stream().forEach(category -> categoryResponse.add(new CategoryResponse(
                category.getId(), category.getName())));
        return categoryResponse;
    }
}