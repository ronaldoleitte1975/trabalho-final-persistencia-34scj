package br.com.fiap.springdatajpa.dto.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class InventoryResponse {

    private Integer productId;
    private Integer amount;

    public InventoryResponse(Integer productId, Integer amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
