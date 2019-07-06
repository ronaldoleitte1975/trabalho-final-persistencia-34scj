package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.dto.category.CategoryDTO;
import br.com.fiap.springdatajpa.dto.category.CategoryResponse;
import br.com.fiap.springdatajpa.dto.product.ProductRequest;
import br.com.fiap.springdatajpa.dto.product.ProductResponse;
import br.com.fiap.springdatajpa.model.Category;
import br.com.fiap.springdatajpa.model.Product;
import br.com.fiap.springdatajpa.service.CategoryService;
import br.com.fiap.springdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller de produto, utilizado para gerenciar um produto.
 */
@RestController
@RequestMapping("/persistence/v1/product")
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;

    /**
     * Construtor padrão da classe com a injeção dos serviços necessários
     */
    @Autowired
    public ProductController(final ProductService productService,
                             final CategoryService categoryService){
        this.productService = productService;
        this.categoryService = categoryService;
    };

    /**
     * Busca todos os produtos cadastrados
     * @return lista de produtos
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<Product> products = productService.getAllProduct();
        List<ProductResponse> productResponse = new ArrayList<>();
        products.stream().forEach(product -> productResponse.add(
                getProductResponse(product)));

        return ResponseEntity.ok(productResponse);
    }

    /**
     * Busca um produto cadastrado a partir do seu id
     * @param id id do produto
     * @return produto
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Integer id){
        Product productResponse = productService.getProductById(id);

        return ResponseEntity.ok(getProductResponse(productResponse));
    }

    /**
     * Exclui um produto cadastrado a partir do seu id
     * @param id id do cliente
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id){
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza um produto cadastrado a partir do seu id
     * @param id id do produto
     * @param productRequest dados do produto a serem alterados
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id,
                                           @Valid @RequestBody ProductRequest productRequest){

        productService.updateProduct(new Product(id, productRequest.getName(), productRequest.getDescription(),
                productRequest.getPrice(), toCategoriesModel(productRequest.getCategories())));

        return ResponseEntity.noContent().build();
    }

    /**
     * Cria um novo produto
     * @param productRequest dados do produto a ser cadastrado
     * @return produto cadastrado
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest){
        Product product = productService.createProduct(new Product(productRequest.getName(), productRequest.getDescription(),
                productRequest.getPrice(), toCategoriesModel(productRequest.getCategories())));

        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/product", product.getId())))
                .body(getProductResponse(product));
    }

    /**
     * Método utilitário para o objeto de response do produto
     * Conversão do modelo para o dto de response
     * @param product produto
     * @return produto convertido
     */
    private ProductResponse getProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                toCategoryResponse(product.getCategories()));
    }

    /**
     * Método utilitário para o objeto de categoria
     * Conversão de classe DTO para modelo
     * @param categoriesDTO categorias
     * @return lista de categorias convertidas
     */
    private List<Category> toCategoriesModel(List<CategoryDTO> categoriesDTO) {
        List<Category> categoriesModel = new ArrayList<>();
        categoriesDTO.stream().forEach(category -> categoriesModel.add(new Category(category.getId())));
        return categoriesModel;
    }

    /**
     * Método utilitário para o objeto de categoria
     * Conversão do de classe de modelo para DTO
     * @param categories categorias
     * @return lista de categorias convertidas
     */
    private List<CategoryResponse> toCategoryResponse(List<Category> categories) {
        List<Category> categoriesList = new ArrayList<>();
        categories.stream().forEach(category -> categoriesList.add(categoryService.getCategoryById(category.getId())));

        List<CategoryResponse> categoryResponse = new ArrayList<>();
        categoriesList.stream().forEach(category -> categoryResponse.add(new CategoryResponse(
                category.getId(), category.getName())));
        return categoryResponse;
    }
}