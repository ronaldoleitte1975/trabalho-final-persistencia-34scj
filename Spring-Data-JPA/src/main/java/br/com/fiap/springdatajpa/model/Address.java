package br.com.fiap.springdatajpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.fiap.springdatajpa.model.enums.AddressType;

@Entity
public class Address {

	/**
	 * A chave é gerada de forma automática, pois não a necessidade de 
	 * gerenciamento do id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer id;

	private String street;
	private Integer number;
	private String complement;
	private String postalCode;
	private String city;
	private String province;
	private String country;

	/* Criando um relacionamento de muitos para um */
	@ManyToOne(fetch = FetchType.EAGER)
	/* A coluna customer_id vai ingressar em uma associação de entidade */
	@JoinColumn(name = "customer_id")
	private Customer customer;

	/* O campo Type sera persistido como um tipo enumerado */
	@Enumerated(EnumType.STRING)
	private AddressType type;

	public Address(){}

	public Address(String street, Integer number, String complement, String postalCode, String city,
				   String province, String country, AddressType type) {
		super();
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.postalCode = postalCode;
		this.city = city;
		this.province = province;
		this.country = country;
		this.type = type;
	}

	public Address(String street, Integer number, String complement, String postalCode, String city,
				   String province, String country, Customer customer, AddressType type) {
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.postalCode = postalCode;
		this.city = city;
		this.province = province;
		this.country = country;
		this.customer = customer;
		this.type = type;
	}

	public Address(Integer id, String street, Integer number, String complement, String postalCode, String city,
				   String province, String country, Customer customer, AddressType type) {
		super();
		this.id = id;
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.postalCode = postalCode;
		this.city = city;
		this.province = province;
		this.country = country;
		this.customer = customer;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer cliente) {
		this.customer = cliente;
	}

	public AddressType getType() { return type;	}

	public void setType(AddressType type) {
		this.type = type;
	}
	
	
	
	

}
