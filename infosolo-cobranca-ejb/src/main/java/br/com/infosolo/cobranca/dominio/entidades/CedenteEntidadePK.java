package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class CedenteEntidadePK implements Serializable {
	private static final long serialVersionUID = -2297657731152946804L;

	@Column(name = "numero_cpf_cnpj_cedente", unique = true, nullable = false, length = 14)
	private String numeroCpfCnpjCedente;

	@Column(name = "codigo_convenio", unique = true, nullable = false, precision = 131089)
	private Long codigoConvenio;

	public CedenteEntidadePK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CedenteEntidadePK)) {
			return false;
		}
		CedenteEntidadePK castOther = (CedenteEntidadePK) other;
		return this.numeroCpfCnpjCedente.equals(castOther.numeroCpfCnpjCedente)
				&& (this.codigoConvenio == castOther.codigoConvenio);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numeroCpfCnpjCedente.hashCode();
		hash = hash * prime
				+ ((int) (this.codigoConvenio ^ (this.codigoConvenio >>> 32)));

		return hash;
	}
}