package br.com.fiap.springdatajpa.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Customer {

	/**
	 * A chave será  gerada de forma automática pelo gerenciador do hibernate, utilizando uma tabela de sequence própria.
	 */
	@Id
	@GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(initialValue = 1, name = "generator", sequenceName = "customer_sequence")
	@Column
	private Integer id;

	private String name;
	private String surname;
	private Date birthDate;
	private char gender;

	/* Criando um relacionamento de um para muitos com endereço */
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Address> address = new HashSet<>();

	/*
	 * Criando um relacionamento de um para muitos com a tabela
	 * Phone
	 */
	@ElementCollection
	@CollectionTable(name = "PHONE")
	private Set<String> phones = new HashSet<>();

	/* Criando uma relacionamento de um para muitos com a salesOrders */
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SalesOrder> salesOrders = new ArrayList<>();

	public Customer() {
	}

	public Customer(String name, String surname, Date birthDate, char gender, Set<String> phones) {
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phones = phones;
	}

	public Customer(String name, String surname, Date birthDate, char gender, Set<Address> address, Set<String> phones) {
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.gender = gender;
		this.address = address;
		this.phones = phones;
	}

	public Customer(Integer id, String name, String surname, Date birthDate, char gender, Set<Address> address,
			Set<String> phones) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.gender = gender;
		this.address = address;
		this.phones = phones;
	}

	public Customer(Integer id, String name, String surname, Date birthDate, char gender, Set<Address> address,
			Set<String> phones, List<SalesOrder> salesOrders) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.gender = gender;
		this.address = address;
		this.phones = phones;
		this.salesOrders = salesOrders;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}

	public Set<String> getPhones() {
		return phones;
	}

	public void setPhones(Set<String> phones) {
		this.phones = phones;
	}

	public List<SalesOrder> getSalesOrders() {
		return salesOrders;
	}

	public void setSalesOrders(List<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}

}
