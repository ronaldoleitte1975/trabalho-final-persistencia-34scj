package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.dto.inventory.InventoryRequest;
import br.com.fiap.springdatajpa.dto.inventory.InventoryResponse;;
import br.com.fiap.springdatajpa.dto.inventory.InventoryUpdateRequest;
import br.com.fiap.springdatajpa.model.Inventory;
import br.com.fiap.springdatajpa.model.Product;
import br.com.fiap.springdatajpa.service.InventoryService;
import br.com.fiap.springdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller de estoque, utilizado para gerenciar o estoque.
 */
@RestController
@RequestMapping("/persistence/v1/inventory")
public class InventoryController {

    private InventoryService inventoryService;
    private ProductService productService;

    /**
     * Construtor padrão da classe com a injeção do services necessários
     */
    @Autowired
    public InventoryController(final InventoryService inventoryService,
                               final ProductService productService){
        this.inventoryService = inventoryService;
        this.productService = productService;
    };

    /**
     * Busca o estoque de produtos
     * @return lista de estoque de produtos
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<InventoryResponse>> getInventory(){
        List<InventoryResponse> inventoryResponse = new ArrayList<>();
        inventoryService.getInventory().stream().forEach(inventoryItem -> inventoryResponse.add(
                new InventoryResponse(inventoryItem.getProduct().getId(), inventoryItem.getAmount())));

        return ResponseEntity.ok(inventoryResponse);
    }

    /**
     * Busca o estoque de um produto cadastrado
     * @param id id do produto
     * @return estoque do produto
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<InventoryResponse> getInventoryByProductId(@PathVariable("id") Integer id){
        Inventory inventory = inventoryService.getInventoryItemByProductId(id);

        return ResponseEntity.ok(new InventoryResponse(
                inventory.getProduct().getId(), inventory.getAmount()));
    }

    /**
     * Exclui o estoque de um produto cadastrado
     * @param id id do produto
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteInventoryByProductId(@PathVariable("id") Integer id){
        Product product = productService.getProductById(id);

        //Desassociando o produto do estoque
        Inventory inventory = inventoryService.getInventoryItemByProductId(id);
        product.setInventory(null);
        inventory.setProduct(product);
        inventoryService.updateInventoryItem(inventory);

        //Deletando o produto do estoque
        inventoryService.deleteInventoryItem(inventory.getInventoryId());

        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza um produto cadastrado no estoque a partir do seu id
     * @param id id do produto
     * @param inventoryUpdateRequest dados do estoque do produto a serem alterados
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateInventoryByProductId(@PathVariable("id") Integer id,
                                                        @Valid @RequestBody InventoryUpdateRequest inventoryUpdateRequest){
        Inventory inventory = inventoryService.getInventoryItemByProductId(id);
        inventory.setAmount(inventoryUpdateRequest.getAmount());
        inventoryService.updateInventoryItem(inventory);

        return ResponseEntity.noContent().build();
    }

    /**
     * Adiciona um produto ao estoque
     * @param inventoryRequest dados do produto a ser cadastrado
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> createInventory(@Valid @RequestBody InventoryRequest inventoryRequest){
        Product product = productService.getProductById(inventoryRequest.getProductId());

        //Criando produto no estoque
        Inventory inventory = inventoryService.createInventoryItem(new Inventory(inventoryRequest.getAmount()));
        product.setInventory(inventory);
        inventory.setProduct(product);

        //Atualizando associações entre estoque e produto
        inventoryService.updateInventoryItem(inventory);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}