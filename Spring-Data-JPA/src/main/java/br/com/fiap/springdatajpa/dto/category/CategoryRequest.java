package br.com.fiap.springdatajpa.dto.category;

import javax.validation.constraints.NotNull;

public class CategoryRequest {
    @NotNull
    private String name;

    public CategoryRequest() {
    }

    public CategoryRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
