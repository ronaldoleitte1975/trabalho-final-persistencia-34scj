package br.com.fiap.springdatajpa.model.enums;

public enum AddressType {

	RESIDENCIAL(1, "Residencial"), COMERCIAL(2, "Comercial"), ENTREGA(3, "Entrega"), OUTROS(4, "Outros");

	private int code;

	private String descr;

	private AddressType(int code, String descr) {

		this.code = code;

		this.descr = descr;

	}

	public int getCode() {

		return code;

	}

	public String getdescr() {

		return descr;

	}

	public static AddressType toEnum(Integer code) {

		if (code == null) {

			return null;

		}

		for (AddressType addressType : AddressType.values()) {

			if (code.equals(Integer.valueOf(addressType.getCode()))) {

				return addressType;

			}

		}

		throw new IllegalArgumentException("Id inválido: " + code);

	}

}
