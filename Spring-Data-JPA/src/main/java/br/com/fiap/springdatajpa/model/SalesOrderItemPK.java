package br.com.fiap.springdatajpa.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class SalesOrderItemPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "sales_order_id")
	private SalesOrder salesOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inventory_id")
	private Inventory inventory;

	public SalesOrderItemPK(SalesOrder salesOrder, Inventory inventory) {
		super();
		this.salesOrder = salesOrder;
		this.inventory = inventory;
	}

	public SalesOrderItemPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}
