package br.com.fiap.springdatajpa.model.enums;

public enum SalesOrderStatus {

	NOVO(0, "Novo"), AGUARDANDO_PAGAMENTO(1, "AguardandoPagamento"), EM_SEPARACAO(2, "EmSeparacao"),
	EM_DESLOCAMENTO(3, "EmDeslocamento"), ENTREGUE(4, "Entregue");

	private int code;

	private String descr;

	private SalesOrderStatus(int code, String descr) {

		this.code = code;

		this.descr = descr;

	}

	public int getCode() {

		return code;

	}

	public String getdescr() {

		return descr;

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
