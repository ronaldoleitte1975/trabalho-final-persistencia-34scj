package br.com.fiap.springdatajpa.dto.product;

import br.com.fiap.springdatajpa.dto.category.CategoryDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductRequest {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    @NotNull
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
