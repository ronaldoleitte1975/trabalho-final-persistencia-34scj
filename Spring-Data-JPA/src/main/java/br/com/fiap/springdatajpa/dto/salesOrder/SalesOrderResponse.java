package br.com.fiap.springdatajpa.dto.salesOrder;

import java.util.List;

public class SalesOrderResponse {
    private Integer id;
    private Integer customerId;
    private String status;
    private List<ProductDTO> itens;

    public SalesOrderResponse(Integer id, Integer customerId, String status, List<ProductDTO> itens) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.itens = itens;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProductDTO> getItens() {
        return itens;
    }

    public void setItens(List<ProductDTO> itens) {
        this.itens = itens;
    }
}
