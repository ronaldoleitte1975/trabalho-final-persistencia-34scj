package br.com.fiap.springdatajpa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Inventory {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Product product;
	
	private Integer amount;
	
	private Date createdDate;
	private Date lastUpdatedDate;
	
	@OneToMany(mappedBy = "id.salesOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SalesOrderItem> salesOrderItens = new HashSet<>();

}
