package br.com.fiap.springdatajpa.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class SalesOrderItem {

	@EmbeddedId
	private SalesOrderItemPK id = new SalesOrderItemPK();

	private Integer quantity;

	private Double price;

	public SalesOrderItem(SalesOrderItemPK id, Integer quantity, Double price) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
	}	

	public SalesOrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}



	public SalesOrderItemPK getId() {
		return id;
	}

	public void setId(SalesOrderItemPK id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}	

}
