package br.com.infosolo.cobranca.dominio.dto;

import java.io.Serializable;
import java.util.Date;

public class PesquisaBoletoDTO implements Serializable {
	private static final long serialVersionUID = 9117143192712181770L;

	private String nossoNumero;
	private Date dataEmissaoInicial;
	private Date dataEmissaoFinal;
	private String numeroDocumento;
	private Date dataPagamentoInicial;
	private Date dataPagamentoFinal;
	private Date dataCreditoInicial;
	private Date dataCreditoFinal;
	private Long convenio;

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public Date getDataEmissaoInicial() {
		return dataEmissaoInicial;
	}

	public void setDataEmissaoInicial(Date dataEmissaoInicial) {
		this.dataEmissaoInicial = dataEmissaoInicial;
	}

	public Date getDataEmissaoFinal() {
		return dataEmissaoFinal;
	}

	public void setDataEmissaoFinal(Date dataEmissaoFinal) {
		this.dataEmissaoFinal = dataEmissaoFinal;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Date getDataPagamentoInicial() {
		return dataPagamentoInicial;
	}

	public void setDataPagamentoInicial(Date dataPagamentoInicial) {
		this.dataPagamentoInicial = dataPagamentoInicial;
	}

	public Date getDataPagamentoFinal() {
		return dataPagamentoFinal;
	}

	public void setDataPagamentoFinal(Date dataPagamentoFinal) {
		this.dataPagamentoFinal = dataPagamentoFinal;
	}

	public Date getDataCreditoInicial() {
		return dataCreditoInicial;
	}

	public void setDataCreditoInicial(Date dataCreditoInicial) {
		this.dataCreditoInicial = dataCreditoInicial;
	}

	public Date getDataCreditoFinal() {
		return dataCreditoFinal;
	}

	public void setDataCreditoFinal(Date dataCreditoFinal) {
		this.dataCreditoFinal = dataCreditoFinal;
	}

	public Long getConvenio() {
		return convenio;
	}

	public void setConvenio(Long convenio) {
		this.convenio = convenio;
	}

}
