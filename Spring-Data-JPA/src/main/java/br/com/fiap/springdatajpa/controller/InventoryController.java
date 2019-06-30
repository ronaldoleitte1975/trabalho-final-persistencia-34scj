package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.dto.inventory.InventoryRequest;
import br.com.fiap.springdatajpa.dto.inventory.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@RestController
@RequestMapping("/persistence/v1/inventory")
public class InventoryController {

    @Autowired
    //private InventoryService inventoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable("id") Integer id){
        return ResponseEntity.ok(null);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<InventoryResponse> deleteInventory(@PathVariable("id") Integer id){
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<InventoryResponse> updateInventory(@PathVariable("id") Integer id,
                                                             @RequestBody InventoryRequest inventoryRequest){
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<InventoryResponse> createInventory(@RequestBody InventoryRequest inventoryRequest){
        Integer id = 0;
        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/inventory", id)))
                .body(null);
    }
}