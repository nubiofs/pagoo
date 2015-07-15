package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="boleto_movimento_retorno",schema = "cobranca")
@NamedQueries({
	@NamedQuery(name="retornarBoletoMovimentoRetornoPorBoleto", query="FROM BoletoMovimentoRetornoEntidade bmr WHERE bmr.boleto = :boleto AND bmr.codigoMovimentoRetorno = :codigoMovimentoRetorno") 
})
public class BoletoMovimentoRetornoEntidade implements Serializable {
	private static final long serialVersionUID = -2893265621594405569L;
	
	@Id
	@Column(name = "id_movimento", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idMovimento;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_boleto", nullable=false)
	private BoletoEntidadeLite boleto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="codigo_movimento", referencedColumnName="codigo_movimento", insertable=true, updatable=true),
		@JoinColumn(name="codigo_banco", referencedColumnName="codigo_banco", insertable=true, updatable=true)
	})
	private MovimentoRetornoEntidade codigoMovimentoRetorno;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_movimento")
	private Date dataMovimento;

	public Long getIdMovimento() {
		return idMovimento;
	}

	public void setIdMovimento(Long idMovimento) {
		this.idMovimento = idMovimento;
	}

	public BoletoEntidadeLite getBoleto() {
		return boleto;
	}

	public void setBoleto(BoletoEntidadeLite boleto) {
		this.boleto = boleto;
	}

	public MovimentoRetornoEntidade getCodigoMovimentoRetorno() {
		return codigoMovimentoRetorno;
	}

	public void setCodigoMovimentoRetorno(
			MovimentoRetornoEntidade codigoMovimentoRetorno) {
		this.codigoMovimentoRetorno = codigoMovimentoRetorno;
	}

	public Date getDataMovimento() {
		return dataMovimento;
	}

	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}

}
