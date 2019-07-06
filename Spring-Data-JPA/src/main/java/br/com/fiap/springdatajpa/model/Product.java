package br.com.fiap.springdatajpa.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Product {

	/**
	 * A tabela possui uma sequence para que sua chave sequencial não entre em conflitos com as demais chaves do banco
	 */
	@Id
	@GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(initialValue = 1, name = "generator", sequenceName = "product_sequence")
	@Column
	private Integer id;
	
	private String name;	
	private String description;
	private Double price;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories = new ArrayList<Category>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name = "product_inventory", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "inventory_id"))
	private Inventory inventory;

	public Product(String name, String description, Double price, List<Category> categories) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.categories = categories;
	}

	public Product(Integer id, String name, String description, Double price, List<Category> categories) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.categories = categories;
	}	

	public Product() {
		super();
		// TODO Auto-generated constructor stub
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

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
