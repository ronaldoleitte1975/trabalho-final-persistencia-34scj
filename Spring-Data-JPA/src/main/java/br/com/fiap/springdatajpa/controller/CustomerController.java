package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.dto.customer.CustomerRequest;
import br.com.fiap.springdatajpa.dto.customer.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/persistence/v1/customer")
public class CustomerController {

    @Autowired
    //private CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET, headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        return ResponseEntity.ok(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") Integer id){
        return ResponseEntity.ok(null);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable("id") Integer id){
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable("id") Integer id,
                                                             @RequestBody CustomerRequest customerRequest){
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, headers="Accept=application/json, Content-type=application/json")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest){
        Integer id = 0;
        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/customer", id)))
                .body(null);
    }
}