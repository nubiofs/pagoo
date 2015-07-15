package br.com.infosolo.cobranca.enumeracao;

public enum TipoRegistro {
	HEADER_ARQUIVO(0),
	HEADER_LOTE(1),
	DETALHE(3),
	TRAILER_LOTE(5),
	TRAILER_ARQUIVO(9);
	
	private int value;
	
	private TipoRegistro(int value) {
		this.setValue(value);
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
