package br.com.fiap.springdatajpa.dto.salesOrder;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SalesOrderRequestUpdate {
    @NotNull
    private Integer customerId;

    @NotNull
    private Integer status;

    @NotNull
    private List<ProductDTO> itens;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public List<ProductDTO> getItens() {
        return itens;
    }

    public void setItens(List<ProductDTO> itens) {
        this.itens = itens;
    }
}