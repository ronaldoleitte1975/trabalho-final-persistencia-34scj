package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.dto.customer.AddressDTO;
import br.com.fiap.springdatajpa.dto.customer.CustomerRequest;
import br.com.fiap.springdatajpa.dto.customer.CustomerResponse;
import br.com.fiap.springdatajpa.dto.customer.PhoneDTO;
import br.com.fiap.springdatajpa.model.Address;
import br.com.fiap.springdatajpa.model.Customer;
import br.com.fiap.springdatajpa.model.enums.AddressType;
import br.com.fiap.springdatajpa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static br.com.fiap.springdatajpa.utils.UtilMethods.toDate;
import static br.com.fiap.springdatajpa.utils.UtilMethods.toDateString;

/**
 * Controller de cliente, utilizado para gerenciar um cliente.
 */
@RestController
@RequestMapping("/persistence/v1/customer")
public class CustomerController {

    private CustomerService customerService;

    /**
     * Construtor padrão da classe com injeção do service de cliente
     */
    @Autowired
    public CustomerController(final CustomerService customerService){
        this.customerService = customerService;
    };

    /**
     * Busca todos os clientes cadastrados
     * @return lista de clientes
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomer();
        List<CustomerResponse> customerResponse = new ArrayList<>();
        customers.stream().forEach(customer -> customerResponse.add(
                getCustomerResponse(customer)));

        return ResponseEntity.ok(customerResponse);
    }

    /**
     * Busca um cliente cadastrado a partir do seu id
     * @param id id do cliente
     * @return cliente
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") Integer id){
        Customer customerResponse = customerService.getCustomerById(id);

        return ResponseEntity.ok(getCustomerResponse(customerResponse));
    }

    /**
     * Exclui um cliente cadastrado a partir do seu id
     * @param id id do cliente
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Integer id){
        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza um cliente cadastrado a partir do seu id
     * @param id id do cliente
     * @param customerRequest dados do cliente a serem alterados
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Integer id,
                                            @Valid @RequestBody CustomerRequest customerRequest){

        customerService.updateCustomer(getCustomer(id, customerRequest.getName(), customerRequest.getSurname(),
                toDate(customerRequest.getBirthDate()), customerRequest.getGender(), customerRequest.getAdress(),
                customerRequest.getPhones()));

        return ResponseEntity.noContent().build();
    }

    /**
     * Cria um novo cliente
     * @param customerRequest dados do cliente a ser cadastrado
     * @return cliente cadastrado
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest){

        //Primeiro persiste o cliente
        Customer customer = customerService.createCustomer(new Customer(customerRequest.getName(), customerRequest.getSurname(),
                toDate(customerRequest.getBirthDate()), customerRequest.getGender(), toModelPhones(customerRequest.getPhones())));

        //Em seguida persiste os endereços do cliente, após ter sido obtido o seu id
        customerService.updateCustomer(getCustomer(customer.getId(), customer.getName(), customer.getSurname(),
                customer.getBirthDate(), customer.getGender(), customerRequest.getAdress(), customerRequest.getPhones()));

        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/customer", customer.getId())))
                .body(getCustomerResponse(customer));
    }

    /**
     * Método utilitário para o objeto de request do cliente
     * Conversão do dto de request para o modelo
     * @param id id do cliente
     * @param name nome do cliente
     * @param surname sobrenome do cliente
     * @param birthDate data de nascimento do cliente
     * @param gender gênero do cliente
     * @param addresses lista de endereços do cliente
     * @param phones lista de telefones do cliente
     * @return cliente convertido
     */
    private Customer getCustomer(Integer id, String name, String surname, Date birthDate, char gender,
                                 List<AddressDTO> addresses, List<PhoneDTO> phones) {
        return new Customer(id, name, surname, birthDate, gender, toModelAddresses(id, addresses), toModelPhones(phones));
    }

    /**
     * Método utilitário para o objeto de response do cliente
     * Conversão do modelo para o dto de response
     * @param customer cliente
     * @return cliente convertido
     */
    private CustomerResponse getCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                toDateString(customer.getBirthDate()),
                customer.getGender(),
                toAddressDTO(customer.getAddress()),
                toPhoneDTO(customer.getPhones()));
    }

    /**
     * Método utilitário para o objeto de telefone
     * Conversão do tipo de collection e da classe de modelo para o DTO
     * @param phonesModel telefones armazenados como set collection
     * @return telefones convertidos em list collection
     */
    private List<PhoneDTO> toPhoneDTO(Set<String> phonesModel) {
        List<PhoneDTO> phonesDTO = new ArrayList<>();
        phonesModel.stream().forEach(phones -> phonesDTO.add(new PhoneDTO(phones)));
        return phonesDTO;
    }

    /**
     * Método utilitário para o objeto de telefone
     * Conversão do tipo de collection e da classe dd DTO para o modelo
     * @param phonesDTO endereços armazenados como list collection
     * @return telefones convertidos em set collection
     */
    private Set<String> toModelPhones(List<PhoneDTO> phonesDTO) {
        Set<String> phonesModel = new HashSet<>();
        phonesDTO.stream().forEach(phones -> phonesModel.add(phones.getNumber()));
        return phonesModel;
    }

    /**
     * Método utilitário para o objeto de endereço
     * Conversão do tipo de collection e da classe de modelo para o DTO
     * @param addresses endereços armazenados como set collection
     * @return endereços convertidos em list
     */
    private List<AddressDTO> toAddressDTO(Set<Address> addresses) {
        List<AddressDTO> addressDTO = new ArrayList<>();
        addresses.stream().forEach(address -> addressDTO.add(new AddressDTO(
                address.getStreet(), address.getNumber(), address.getComplement(), address.getPostalCode(),
                address.getCity(), address.getProvince(), address.getCountry(), address.getType().getCode()
        )));
        return addressDTO;
    }

    /**
     * Método utilitário para o objeto de endereço
     * Conversão do tipo de collection e da classe do DTO para o modelo
     * @param addressDTO endereços armazenados como list collection
     * @return endereços convertidos em set collection
     */
    private Set<Address> toModelAddresses(Integer id, List<AddressDTO> addressDTO) {
        Customer customer = this.customerService.getCustomerById(id);
        Set<Address> addressModel = new HashSet<>();
        addressDTO.stream().forEach(address -> addressModel.add(new Address(address.getStreetName(),
                address.getNumber(), address.getComplement(), address.getPostalCode(), address.getCity(),
                address.getProvince(), address.getCountry(), customer, AddressType.toEnum(address.getType())
        )));
        return addressModel;
    }
}