package br.com.fiap.springdatajpa.dto.customer;

import java.util.List;

public class CustomerRequest {

    String name;
    String surname;
    String birthDate;
    Character gender;
    List<AddressDTO> adress;
    List<PhoneDTO> phones;

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

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
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

