package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.dto.customer.PhoneDTO;
import br.com.fiap.springdatajpa.dto.salesOrder.ProductDTO;
import br.com.fiap.springdatajpa.dto.salesOrder.SalesOrderRequest;
import br.com.fiap.springdatajpa.dto.salesOrder.SalesOrderResponse;
import br.com.fiap.springdatajpa.model.Address;
import br.com.fiap.springdatajpa.model.Customer;
import br.com.fiap.springdatajpa.model.Product;
import br.com.fiap.springdatajpa.model.SalesOrder;
import br.com.fiap.springdatajpa.model.SalesOrderItem;
import br.com.fiap.springdatajpa.model.enums.SalesOrderStatus;
import br.com.fiap.springdatajpa.service.CustomerService;
import br.com.fiap.springdatajpa.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/persistence/v1/salesOrder")
public class SalesOrderController {

    private SalesOrderService salesOrderService;
    private CustomerService customerService;

    @Autowired
    public SalesOrderController(final SalesOrderService salesOrderService,
                                final CustomerService customerService){
        this.salesOrderService = salesOrderService;
        this.customerService = customerService;
    };

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<SalesOrderResponse>> getSalesOrders(){

        List<SalesOrder> salesOrders = salesOrderService.getAllSalesOrders();
        List<SalesOrderResponse> salesOrderResponse = new ArrayList<>();
        salesOrders.stream().forEach(salesOrder -> salesOrderResponse.add(
                new SalesOrderResponse(
                        salesOrder.getId(),
                        salesOrder.getCustomer().getId(),
                        salesOrder.getStatus(),
                        toProductDTO(salesOrder.getItens()))));
        return ResponseEntity.ok(salesOrderResponse);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<SalesOrderResponse> getSalesOrder(@PathVariable("id") Integer id){
        SalesOrder salesOrderResponse = salesOrderService.getSalesOrderById(id);

        return ResponseEntity.ok(new SalesOrderResponse(
                salesOrderResponse.getId(),
                salesOrderResponse.getCustomer().getId(),
                salesOrderResponse.getStatus(),
                toProductDTO(salesOrderResponse.getItens())));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteSalesOrder(@PathVariable("id") Integer id){
        salesOrderService.deleteSalesOrder(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateSalesOrder(@PathVariable("id") Integer id,
                                             @RequestBody SalesOrderRequest salesOrderRequest){
        Customer customer = customerService.getCustomerById(salesOrderRequest.getCustomerId());

        Address address = customer.getAddress().stream().findFirst().get();

        //salesOrderService.updateSalesOrder(new SalesOrder(id, customer, new Date(),
        //        SalesOrderStatus.toEnum(salesOrderRequest.getStatus()), address,
        //        salesOrderRequest.getItens()));

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<SalesOrderResponse> createSalesOrder(@RequestBody SalesOrderRequest salesOrderRequest){
        Integer id = 0;
        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/salesOrder", id)))
                .body(null);
    }

    private List<ProductDTO> toProductDTO(Set<SalesOrderItem> itensModel) {
        List<ProductDTO> productsDTO = new ArrayList<>();
        itensModel.stream().forEach(products -> productsDTO.add(
                new ProductDTO(products.getId().getSalesOrder().getId(), products.getQuantity())));
        return productsDTO;
    }
}