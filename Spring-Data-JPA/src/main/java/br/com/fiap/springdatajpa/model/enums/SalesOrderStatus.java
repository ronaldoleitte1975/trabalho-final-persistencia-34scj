package br.com.fiap.springdatajpa.model.enums;

public enum SalesOrderStatus {

	NOVO(0, "NOVO"),
	AGUARDANDO_PAGAMENTO(1, "AGUARDANDO PAGAMENTO"),
	EM_SEPARACAO(2, "EM SEPARAÇÃO"),
	EM_DESLOCAMENTO(3, "EM DESLOCAMENTO"),
	ENTREGUE(4, "ENTREGUE");

	private int code;

	private String description;

	private SalesOrderStatus(int code, String description) {

		this.code = code;

		this.description = description;

	}

	public int getCode() {

		return code;

	}

	public String getDescription() {

		return description;

	}

	public static SalesOrderStatus toEnum(Integer code) {

		if (code == null) {

			return null;

		}

		for (SalesOrderStatus sales : SalesOrderStatus.values()) {

			if (code.equals(Integer.valueOf(sales.getCode()))) {

				return sales;

			}

		}

		throw new IllegalArgumentException("Id inválido: " + code);

	}

}
