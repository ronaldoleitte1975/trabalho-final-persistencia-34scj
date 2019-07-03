package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.dto.product.ProductRequest;
import br.com.fiap.springdatajpa.dto.product.ProductResponse;
import br.com.fiap.springdatajpa.model.Product;
import br.com.fiap.springdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
        return ResponseEntity.ok(null);
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
                productResponse.getCategories().stream().findFirst().get().getId()));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable("id") Integer id){
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Integer id,
                                                         @RequestBody ProductRequest productRequest){
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        Integer id = 0;
        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/product", id)))
                .body(null);
    }
}