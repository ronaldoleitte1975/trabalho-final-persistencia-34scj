package br.com.fiap.springdatajpa.dto.category;

public class CategoryDTO {
    private Integer id;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
