package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class MovimentoRetornoEntidadePK implements Serializable {
	private static final long serialVersionUID = -1694841143722289494L;
	
	@Column(name="codigo_movimento", nullable=false, insertable=false, updatable=false)
	private Integer codigoMovimento;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_banco", nullable=false, insertable=false, updatable=false)
	private BancoEntidade banco;

	
	public Integer getCodigoMovimento() {
		return codigoMovimento;
	}
	
	public void setCodigoMovimento(Integer codigoMovimento) {
		this.codigoMovimento = codigoMovimento;
	}
	
	public BancoEntidade getBanco() {
		return banco;
	}
	
	public void setBanco(BancoEntidade banco) {
		this.banco = banco;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MovimentoRetornoEntidadePK)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		
		final MovimentoRetornoEntidadePK other = (MovimentoRetornoEntidadePK) obj;
		return new EqualsBuilder().append(getCodigoMovimento(), other.getCodigoMovimento())
				.append(getBanco(), other.getBanco()).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCodigoMovimento())
				.append(getBanco()).toHashCode();
	}

}
