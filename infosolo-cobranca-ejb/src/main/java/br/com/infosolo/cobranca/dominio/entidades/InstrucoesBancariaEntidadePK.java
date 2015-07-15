package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InstrucoesBancariaEntidadePK implements Serializable {
	private static final long serialVersionUID = 1044340657639633923L;

	@Column(name = "numero_cpf_cnpj_cedente", unique = true, nullable = false, length = 14)
	private String numeroCpfCnpjCedente;

	@Column(name = "codigo_convenio", unique = true, nullable = false, precision = 131089)
	private Long codigoConvenio;

	public InstrucoesBancariaEntidadePK() {
	}

	public String getNumeroCpfCnpjCedente() {
		return this.numeroCpfCnpjCedente;
	}

	public void setNumeroCpfCnpjCedente(String numeroCpfCnpjCedente) {
		this.numeroCpfCnpjCedente = numeroCpfCnpjCedente;
	}

	public Long getCodigoConvenio() {
		return this.codigoConvenio;
	}

	public void setCodigoConvenio(Long codigoConvenio) {
		this.codigoConvenio = codigoConvenio;
	}

}
