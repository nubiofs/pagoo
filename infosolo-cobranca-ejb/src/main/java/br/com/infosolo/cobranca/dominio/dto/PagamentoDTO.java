package br.com.infosolo.cobranca.dominio.dto;

import java.util.Date;

public class PagamentoDTO {
	private String numeroDocumento;
	private String nossoNumero;
	private Double valorTitulo;
	private Double valorPago;
	private Date dataPagamento;
	private Date dataCredito;
	private String codigoMovimento;
	private String motivoOcorrencia;
	private String arquivoRetorno;
	private int linha;
	private String banco;
	private String codigoCedente;
	private String motivoOcorrenciaArquivoRetorno;
	private String codigoMovimentoArquivoRetorno;
	private String numeroAutenticacao;

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public String getCodigoMovimento() {
		return codigoMovimento;
	}

	public void setCodigoMovimento(String codigoMovimento) {
		this.codigoMovimento = codigoMovimento;
	}

	public String getMotivoOcorrencia() {
		return motivoOcorrencia;
	}

	public void setMotivoOcorrencia(String motivoOcorrencia) {
		this.motivoOcorrencia = motivoOcorrencia;
	}

	public String getArquivoRetorno() {
		return arquivoRetorno;
	}

	public void setArquivoRetorno(String arquivoRetorno) {
		this.arquivoRetorno = arquivoRetorno;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getCodigoCedente() {
		return codigoCedente;
	}

	public void setCodigoCedente(String codigoCedente) {
		this.codigoCedente = codigoCedente;
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

	public Double getValorTitulo() {
		return valorTitulo;
	}

	public void setValorTitulo(Double valorTitulo) {
		this.valorTitulo = valorTitulo;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getMotivoOcorrenciaArquivoRetorno() {
		return motivoOcorrenciaArquivoRetorno;
	}

	public void setMotivoOcorrenciaArquivoRetorno(
			String motivoOcorrenciaArquivoRetorno) {
		this.motivoOcorrenciaArquivoRetorno = motivoOcorrenciaArquivoRetorno;
	}

	public String getCodigoMovimentoArquivoRetorno() {
		return codigoMovimentoArquivoRetorno;
	}

	public void setCodigoMovimentoArquivoRetorno(
			String codigoMovimentoArquivoRetorno) {
		this.codigoMovimentoArquivoRetorno = codigoMovimentoArquivoRetorno;
	}

	public String getNumeroAutenticacao() {
		return numeroAutenticacao;
	}

	public void setNumeroAutenticacao(String numeroAutenticacao) {
		this.numeroAutenticacao = numeroAutenticacao;
	}

}
