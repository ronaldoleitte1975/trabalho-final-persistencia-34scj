package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.dto.product.ProductRequest;
import br.com.fiap.springdatajpa.dto.product.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/persistence/v1/product")
public class ProductController {

    @Autowired
    //private ProductService productService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Integer id){
        return ResponseEntity.ok(null);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable("id") Integer id){
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Integer id,
                                                         @RequestBody ProductRequest productRequest){
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        Integer id = 0;
        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/product", id)))
                .body(null);
    }
}