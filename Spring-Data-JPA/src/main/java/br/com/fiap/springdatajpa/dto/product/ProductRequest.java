package br.com.fiap.springdatajpa.dto.product;

import br.com.fiap.springdatajpa.dto.category.CategoryDTO;

import java.util.List;

public class ProductRequest {

    private String name;
    private String description;
    private Double price;
    private List<CategoryDTO> categories;

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

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}
