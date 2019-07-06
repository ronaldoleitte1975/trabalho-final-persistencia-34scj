package br.com.fiap.springdatajpa.controller;

import br.com.fiap.springdatajpa.advice.ResponseError;
import br.com.fiap.springdatajpa.dto.salesOrder.ProductDTO;
import br.com.fiap.springdatajpa.dto.salesOrder.SalesOrderRequest;
import br.com.fiap.springdatajpa.dto.salesOrder.SalesOrderRequestUpdate;
import br.com.fiap.springdatajpa.dto.salesOrder.SalesOrderResponse;
import br.com.fiap.springdatajpa.model.Address;
import br.com.fiap.springdatajpa.model.Customer;
import br.com.fiap.springdatajpa.model.Inventory;
import br.com.fiap.springdatajpa.model.SalesOrder;
import br.com.fiap.springdatajpa.model.SalesOrderItem;
import br.com.fiap.springdatajpa.model.SalesOrderItemPK;
import br.com.fiap.springdatajpa.model.enums.SalesOrderStatus;
import br.com.fiap.springdatajpa.service.CustomerService;
import br.com.fiap.springdatajpa.service.InventoryService;
import br.com.fiap.springdatajpa.service.ProductService;
import br.com.fiap.springdatajpa.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static br.com.fiap.springdatajpa.model.enums.AddressType.ENTREGA;

/**
 * Controller de pedido, utilizado para gerenciar um pedido.
 */
@RestController
@RequestMapping("/persistence/v1/salesOrder")
public class SalesOrderController {

    private SalesOrderService salesOrderService;
    private CustomerService customerService;
    private ProductService productService;
    private InventoryService inventoryService;

    /**
     * Construtor padrão da classe com a injeção dos services necessários
     */
    @Autowired
    public SalesOrderController(final SalesOrderService salesOrderService,
                                final CustomerService customerService,
                                final ProductService productService,
                                final InventoryService inventoryService){
        this.salesOrderService = salesOrderService;
        this.customerService = customerService;
        this.productService = productService;
        this.inventoryService = inventoryService;
    };

    /**
     * Busca todos os pedidos cadastrados
     * @return lista de pedidos
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<SalesOrderResponse>> getSalesOrders(){

        List<SalesOrder> salesOrders = salesOrderService.getAllSalesOrders();
        List<SalesOrderResponse> salesOrderResponse = new ArrayList<>();
        salesOrders.stream().forEach(salesOrder -> salesOrderResponse.add(
                getSalesOrderResponse(salesOrder)));
        return ResponseEntity.ok(salesOrderResponse);
    }

    /**
     * Busca um pedido cadastrado a partir do seu id
     * @param id id do pedido
     * @return pedido
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<SalesOrderResponse> getSalesOrder(@PathVariable("id") Integer id){
        SalesOrder salesOrderResponse = salesOrderService.getSalesOrderById(id);

        return ResponseEntity.ok(getSalesOrderResponse(salesOrderResponse));
    }

    /**
     * Exclui um pedido cadastrado a partir do seu id
     * @param id id do pedido
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteSalesOrder(@PathVariable("id") Integer id){
        salesOrderService.deleteSalesOrder(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza um pedido cadastrado a partir do seu id
     * @param id id do pedido
     * @param salesOrderRequest dados do pedido a serem alterados
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
            produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateSalesOrder(@PathVariable("id") Integer id,
                                              @Valid @RequestBody SalesOrderRequestUpdate salesOrderRequest){
        List<Inventory> inventoryList = validateInventory(salesOrderRequest.getItens());
        Customer customer = customerService.getCustomerById(salesOrderRequest.getCustomerId());

        salesOrderService.updateSalesOrder(new SalesOrder(id, customer, new Date(),
                SalesOrderStatus.toEnum(salesOrderRequest.getStatus()), getAddress(customer.getAddress()),
                toSalesOrderItem(id, salesOrderRequest.getItens())));

        inventoryList.stream().forEach(inventoryService::updateInventoryItem);

        return ResponseEntity.noContent().build();
    }

    /**
     * Cria um novo pedido
     * @param salesOrderRequest dados do pedido a ser cadastrado
     * @return pedido cadastrado
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<SalesOrderResponse> createSalesOrder(@Valid @RequestBody SalesOrderRequest salesOrderRequest){

        List<Inventory> inventoryList = validateInventory(salesOrderRequest.getItens());

        Customer customer = customerService.getCustomerById(salesOrderRequest.getCustomerId());

        SalesOrder salesOrder = salesOrderService.createSalesOrder(new SalesOrder(customer, new Date(),
            SalesOrderStatus.NOVO, getAddress(customer.getAddress())));

        salesOrderService.updateSalesOrder(new SalesOrder(salesOrder.getId(), customer, new Date(), SalesOrderStatus.NOVO,
                getAddress(customer.getAddress()), toSalesOrderItem(salesOrder.getId(), salesOrderRequest.getItens())));

        inventoryList.stream().forEach(inventoryService::updateInventoryItem);

        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/persistence/v1/salesOrder", salesOrder.getId())))
                .body(getSalesOrderResponse(salesOrder));
    }

    /**
     * Valida se todos os produtos e suas respectivas quantidades pode ser subtraídas do estoque corretamente
     * @param itens lista de produtos
     * @return estoque
     */
    private List<Inventory> validateInventory(List<ProductDTO> itens) {
        List<Inventory> inventoryList = new ArrayList<>();
        itens.stream().forEach(item -> inventoryList.add(inventoryService.getInventoryItemByProductId(item.getProductId())));

        for (Inventory inventory : inventoryList) {
            Optional<ProductDTO> product = itens.stream().filter(item ->
                    item.getProductId().equals(inventory.getProduct().getId())).findFirst();

            if (!product.isPresent() || product.get().getQuantity() > inventory.getAmount()){
                throw new ResponseError(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Não é possível realizar o pedido pois um mais produtos informados não possui a quantidade suficiente em estoque.");
            } else {
                inventory.setAmount(inventory.getAmount() - product.get().getQuantity());
            }
        }

        return inventoryList;
    }

    /**
     * Método utilitário para o objeto de itens do pedido
     * Conversão do tipo de collection e da classe de modelo para o DTO
     * @param itensModel itens do pedido armazenados como set collection
     * @return itens do pedido convertidos em list collection
     */
    private List<ProductDTO> toProductDTO(Set<SalesOrderItem> itensModel) {
        List<ProductDTO> productsDTO = new ArrayList<>();
        itensModel.stream().forEach(products -> productsDTO.add(
                new ProductDTO(products.getId().getSalesOrder().getId(), products.getQuantity())));
        return productsDTO;
    }

    /**
     * Método utilitário para o objeto de itens do pedido
     * Conversão do tipo de collection e da classe de DTO para o modelo
     * @param products produtos armazenados como list collection
     * @return endereços convertidos em set collection
     */
    private Set<SalesOrderItem> toSalesOrderItem(Integer id, List<ProductDTO> products){
        Set<SalesOrderItem> salesOrderItem = new HashSet<>();

        products.stream().forEach(product -> salesOrderItem.add(new SalesOrderItem(
                new SalesOrderItemPK(
                        this.salesOrderService.getSalesOrderById(id),
                        this.productService.getProductById(product.getProductId())), product.getQuantity(),
                productService.getProductById(product.getProductId()).getPrice())));
        return salesOrderItem;
    }

    /**
     * Método utilitário para obtenção do endereço a ser utilizado como endereço de entrega no pedido
     * @param addressesCollection set collection de endereços
     * @return endereço a ser utilizado como endereço entrega
     */
    private Address getAddress(Set<Address> addressesCollection) {
        Address address;
        if (addressesCollection.stream().count() > 0) {
            address = addressesCollection.stream()
                    .filter(addresses -> addresses.getType().equals(ENTREGA))
                    .findAny()
                    .orElse(addressesCollection.stream().findFirst().get());
        } else {
            throw new ResponseError(HttpStatus.CONFLICT, "Houve um erro ao obter o endereço de entrega.");
        }
        return address;
    }

    /**
     * Método utilitário para o objeto de response do pedido
     * Conversão do modelo para o dto de response
     * @param salesOrder pedido
     * @return pedido convertido
     */
    private SalesOrderResponse getSalesOrderResponse(SalesOrder salesOrder) {
        return new SalesOrderResponse(
                salesOrder.getId(),
                salesOrder.getCustomer().getId(),
                salesOrder.getStatus().getDescription(),
                toProductDTO(salesOrder.getItens()));
    }
}