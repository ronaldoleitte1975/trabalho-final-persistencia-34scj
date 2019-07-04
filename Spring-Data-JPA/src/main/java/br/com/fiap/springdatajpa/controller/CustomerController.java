package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.advice.ResponseError;
import br.com.fiap.springdatajpa.dto.customer.AddressDTO;
import br.com.fiap.springdatajpa.dto.customer.CustomerRequest;
import br.com.fiap.springdatajpa.dto.customer.CustomerResponse;
import br.com.fiap.springdatajpa.dto.customer.PhoneDTO;
import br.com.fiap.springdatajpa.model.Address;
import br.com.fiap.springdatajpa.model.Customer;
import br.com.fiap.springdatajpa.model.enums.AddressType;
import br.com.fiap.springdatajpa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static br.com.fiap.springdatajpa.utils.UtilMethods.toDate;
import static br.com.fiap.springdatajpa.utils.UtilMethods.toDateString;

@RestController
@RequestMapping("/persistence/v1/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(final CustomerService customerService){
        this.customerService = customerService;
    };

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomer();
        List<CustomerResponse> customerResponse = new ArrayList<>();
        customers.stream().forEach(customer -> customerResponse.add(
                new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                toDateString(customer.getBirthDate()),
                customer.getGender(),
                toAddressDTO(customer.getAddress()),
                toPhoneDTO(customer.getPhones()))));

        return ResponseEntity.ok(customerResponse);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") Integer id){
        Customer customerResponse = customerService.getCustomerById(id);

        return ResponseEntity.ok(new CustomerResponse(
                customerResponse.getId(),
                customerResponse.getName(),
                customerResponse.getSurname(),
                toDateString(customerResponse.getBirthDate()),
                customerResponse.getGender(),
                toAddressDTO(customerResponse.getAddress()),
                toPhoneDTO(customerResponse.getPhones())));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Integer id){
        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Integer id,
                                                           @RequestBody CustomerRequest customerRequest){

        customerService.updateCustomer(new Customer(id, customerRequest.getName(), customerRequest.getSurname(),
                toDate(customerRequest.getBirthDate()), customerRequest.getGender(),
                toModelAddresses(customerRequest.getAdress()), toModelPhones(customerRequest.getPhones())));

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest){

        Customer customer = customerService.addCustomer(new Customer(customerRequest.getName(), customerRequest.getSurname(),
                toDate(customerRequest.getBirthDate()), customerRequest.getGender(),
                toModelAddresses(customerRequest.getAdress()),  toModelPhones(customerRequest.getPhones())));

        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/customer", customer.getId())))
                .body(new CustomerResponse(
                            customer.getId(),
                            customer.getName(),
                            customer.getSurname(),
                            toDateString(customer.getBirthDate()),
                            customer.getGender(),
                            toAddressDTO(customer.getAddress()),
                            toPhoneDTO(customer.getPhones())));
    }

    private List<PhoneDTO> toPhoneDTO(Set<String> phonesModel) {
        List<PhoneDTO> phonesDTO = new ArrayList<>();
        phonesModel.stream().forEach(phones -> phonesDTO.add(new PhoneDTO(phones)));
        return phonesDTO;
    }

    private List<AddressDTO> toAddressDTO(Set<Address> addresses) {
        List<AddressDTO> addressDTO = new ArrayList<>();
        addresses.stream().forEach(address -> addressDTO.add(new AddressDTO(
                address.getStreet(), address.getNumber(), address.getComplement(), address.getPostalCode(),
                address.getCity(), address.getProvince(), address.getCountry(), address.getType().getCode()
        )));
        return addressDTO;
    }

    private Set<String> toModelPhones(List<PhoneDTO> phonesDTO) {
        Set<String> phonesModel = new HashSet<>();
        phonesDTO.stream().forEach(phones -> phonesModel.add(phones.getNumber()));
        return phonesModel;
    }

    private Set<Address> toModelAddresses(List<AddressDTO> addressDTO) {
        Set<Address> addressModel = new HashSet<>();
        addressDTO.stream().forEach(address -> addressModel.add(new Address(
                address.getStreetName(), address.getNumber(), address.getComplement(), address.getPostalCode(),
                address.getCity(), address.getProvince(), address.getCountry(), AddressType.toEnum(address.getType())
        )));
        return addressModel;
    }




}