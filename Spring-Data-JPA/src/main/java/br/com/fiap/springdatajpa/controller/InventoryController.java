package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.dto.inventory.InventoryRequest;
import br.com.fiap.springdatajpa.dto.inventory.InventoryResponse;;
import br.com.fiap.springdatajpa.dto.inventory.InventoryUpdateRequest;
import br.com.fiap.springdatajpa.model.Inventory;
import br.com.fiap.springdatajpa.model.Product;
import br.com.fiap.springdatajpa.service.InventoryService;
import br.com.fiap.springdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persistence/v1/inventory")
public class InventoryController {

    private InventoryService inventoryService;
    private ProductService productService;

    @Autowired
    public InventoryController(final InventoryService inventoryService,
                               final ProductService productService){
        this.inventoryService = inventoryService;
        this.productService = productService;
    };

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<InventoryResponse>> getInventory(){
        List<InventoryResponse> inventoryResponse = new ArrayList<>();
        inventoryService.getInventory().stream().forEach(inventoryItem -> inventoryResponse.add(
                new InventoryResponse(inventoryItem.getProduct().getId(), inventoryItem.getAmount())));

        return ResponseEntity.ok(inventoryResponse);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<InventoryResponse> getInventoryByProductId(@PathVariable("id") Integer id){
        Inventory inventory = inventoryService.getInventoryItemByProductId(id);

        return ResponseEntity.ok(new InventoryResponse(
                inventory.getProduct().getId(), inventory.getAmount()));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteInventoryByProductId(@PathVariable("id") Integer id){
        Product product = productService.getProductById(id);
        Inventory inventory = inventoryService.getInventoryItemByProductId(id);
        product.setInventory(null);
        inventory.setProduct(product);
        inventoryService.updateInventoryItem(inventory);

        inventoryService.deleteInventoryItem(inventory.getInventoryId());

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateInventoryByProductId(@PathVariable("id") Integer id,
                                                        @RequestBody InventoryUpdateRequest inventoryUpdateRequest){
        Inventory inventory = inventoryService.getInventoryItemByProductId(id);
        inventory.setAmount(inventoryUpdateRequest.getAmount());
        inventoryService.updateInventoryItem(inventory);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> createInventory(@RequestBody InventoryRequest inventoryRequest){
        Product product = productService.getProductById(inventoryRequest.getProductId());
        Inventory inventory = inventoryService.createInventoryItem(new Inventory(inventoryRequest.getAmount()));
        product.setInventory(inventory);
        inventory.setProduct(product);

        inventoryService.updateInventoryItem(inventory);

        return ResponseEntity.status(201).build();
    }
}