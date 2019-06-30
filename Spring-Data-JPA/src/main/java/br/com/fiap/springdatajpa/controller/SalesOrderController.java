package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.dto.salesOrder.SalesOrderRequest;
import br.com.fiap.springdatajpa.dto.salesOrder.SalesOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/persistence/v1/salesOrder")
public class SalesOrderController {

    @Autowired
    //private SalesOrderService salesOrderService;

    @RequestMapping(method = RequestMethod.GET, headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<List<SalesOrderResponse>> getSalesOrders(){
        return ResponseEntity.ok(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<SalesOrderResponse> getSalesOrder(@PathVariable("id") Integer id){
        return ResponseEntity.ok(null);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<SalesOrderResponse> deleteSalesOrder(@PathVariable("id") Integer id){
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<SalesOrderResponse> updateSalesOrder(@PathVariable("id") Integer id,
                                                               @RequestBody SalesOrderRequest salesOrderRequest){
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<SalesOrderResponse> createSalesOrder(@RequestBody SalesOrderRequest salesOrderRequest){
        Integer id = 0;
        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/salesOrder", id)))
                .body(null);
    }
}