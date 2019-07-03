package br.com.fiap.springdatajpa.dto.customer;

import java.util.List;

public class CustomerRequest {

    private String name;
    private String surname;
    private String birthDate;
    private char gender;
    private List<AddressDTO> adress;
    private List<PhoneDTO> phones;

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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public List<AddressDTO> getAdress() {
        return adress;
    }

    public void setAdress(List<AddressDTO> adress) {
        this.adress = adress;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }
}

