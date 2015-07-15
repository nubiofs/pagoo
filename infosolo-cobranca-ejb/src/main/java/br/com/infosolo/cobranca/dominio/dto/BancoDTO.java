package br.com.infosolo.cobranca.dominio.dto;

import java.io.Serializable;

public class BancoDTO implements Serializable {
	private static final long serialVersionUID = -518248527713595512L;

	private String codigoBanco;
	private String nomeBanco;

	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

}
