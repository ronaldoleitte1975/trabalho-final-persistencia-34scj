package br.com.fiap.springdatajpa.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class SalesOrderItemPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/* Criando um relacioamento de muitos para um com sales_order */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sales_order_id")
	private SalesOrder salesOrder;

	/* Criando um relacioamento de muitos para um com product */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	public SalesOrderItemPK(SalesOrder salesOrder, Product product) {
		super();
		this.salesOrder = salesOrder;
		this.product = product;
	}

	public SalesOrderItemPK() {
	}

	public SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
