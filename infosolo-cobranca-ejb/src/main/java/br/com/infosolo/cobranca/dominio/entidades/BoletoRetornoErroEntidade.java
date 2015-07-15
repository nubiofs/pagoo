package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="boleto_retorno_erro",schema = "cobranca")
@NamedQueries({
	@NamedQuery(name="boletoRetornoErro.retornarBoletoRetornoErroPorNossoNumero", 
			   query="FROM BoletoRetornoErroEntidade boleto WHERE boleto.nossoNumero = :nossoNumero"), 
})
public class BoletoRetornoErroEntidade implements Serializable {
	private static final long serialVersionUID = 3715519104983531572L;

	@Id
	@Column(name="nosso_numero", length=20)
	private String nossoNumero;

    @Temporal(TemporalType.DATE)
	@Column(name="data_pagamento")
	private Date dataPagamento;

    @Temporal(TemporalType.DATE)
	@Column(name="data_credito")
	private Date dataCredito;

	@Column(name="valor_abatimento", precision=131089)
	private BigDecimal valorAbatimento;

	@Column(name="valor_credito", precision=131089)
	private BigDecimal valorCredito;

	@Column(name="valor_desconto", precision=131089)
	private BigDecimal valorDesconto;

	@Column(name="valor_iof", precision=131089)
	private BigDecimal valorIof;

	@Column(name="valor_juros_mora", precision=131089)
	private BigDecimal valorJurosMora;

	@Column(name="numero_autenticacao", length=23)
	private String numeroAutenticacao;

    public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDataCredito() {
		return dataCredito;
	}

	public void setDataCredito(Date dataCredito) {
		this.dataCredito = dataCredito;
	}

	public BigDecimal getValorAbatimento() {
		return valorAbatimento;
	}

	public void setValorAbatimento(BigDecimal valorAbatimento) {
		this.valorAbatimento = valorAbatimento;
	}

	public BigDecimal getValorCredito() {
		return valorCredito;
	}

	public void setValorCredito(BigDecimal valorCredito) {
		this.valorCredito = valorCredito;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorIof() {
		return valorIof;
	}

	public void setValorIof(BigDecimal valorIof) {
		this.valorIof = valorIof;
	}

	public BigDecimal getValorJurosMora() {
		return valorJurosMora;
	}

	public void setValorJurosMora(BigDecimal valorJurosMora) {
		this.valorJurosMora = valorJurosMora;
	}

	public String getNumeroAutenticacao() {
		return numeroAutenticacao;
	}

	public void setNumeroAutenticacao(String numeroAutenticacao) {
		this.numeroAutenticacao = numeroAutenticacao;
	}

}
