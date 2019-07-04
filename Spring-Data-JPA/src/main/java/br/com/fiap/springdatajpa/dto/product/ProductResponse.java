package br.com.fiap.springdatajpa.dto.product;

import br.com.fiap.springdatajpa.dto.category.CategoryDTO;
import br.com.fiap.springdatajpa.dto.category.CategoryResponse;

import java.util.List;

public class ProductResponse {
    public ProductResponse() {
    }

    public ProductResponse(Integer id, String name, String description, Double price, List<CategoryResponse> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categories = categories;
    }

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private List<CategoryResponse> categories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResponse> categories) {
        this.categories = categories;
    }
}
