package br.com.fiap.springdatajpa.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Category {

	/**
	 * A chave será  gerada de forma automática pelo gerenciador do hibernate, utilizando uma tabela de sequence própria.
	 */
	@Id
	@GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(initialValue = 1, name = "generator", sequenceName = "category_sequence")
	@Column
	private Integer id;

	private String name;

	/*
	 * Criando um relacionamento de muitos para muitos com a tabela products
	 */
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
	private List<Product> products = new ArrayList<Product>();

	public Category(Integer id) {
		this.id = id;
	}

	public Category(String name) {
		this.name = name;
	}

	public Category(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Category(Integer id, String name, List<Product> products) {
		super();
		this.id = id;
		this.name = name;
		this.products = products;
	}

	public Category() {
	}

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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
