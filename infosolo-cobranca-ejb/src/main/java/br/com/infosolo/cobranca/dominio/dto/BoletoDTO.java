package br.com.infosolo.cobranca.dominio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTo com as informacoes do boleto.
 * 
 */
public class BoletoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idBoleto;
	private String nossoNumero;
	private Date dataEmissao;
	private Date dataVencimento;
	private String numeroDocumento;
	private Double valorAbatimento;
	private Double valorBoleto;
	private Double valorDesconto;
	private Double valorIof;
	private Double valorJurosMora;
	private Double valorMulta;
	private CedenteDTO cedente;
	private CedenteDTO cedente2;
	private SacadoDTO sacado;
	private SacadorAvalistaDTO sacadorAvalista;
	private BigDecimal valorCredito;
	private Date dataPagamento;
	private Date dataCredito;
	private String assuntoEmail;
	private String textoEmail;
	private byte[] anexoRelatorioEmail;
	private byte[] anexoPlanilhaEmail;
	private String operacao;
	private byte[] imagemLogo;
	private String instrucoesBancarias;
	private Double valorIR;
	private Double valorPIS;
	private Double valorCOFINS;
	private Double valorCSLL;
	private Double valorISS;
	private String caminhoFisicoBoleto;

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public void setImagemLogo(byte[] imagemLogo) {
		this.imagemLogo = imagemLogo;
	}

	public byte[] getImagemLogo() {
		return imagemLogo;
	}

	public byte[] getAnexoRelatorioEmail() {
		return anexoRelatorioEmail;
	}

	public void setAnexoRelatorioEmail(byte[] anexoRelatorioEmail) {
		this.anexoRelatorioEmail = anexoRelatorioEmail;
	}

	public byte[] getAnexoPlanilhaEmail() {
		return anexoPlanilhaEmail;
	}

	public void setAnexoPlanilhaEmail(byte[] anexoPlanilhaEmail) {
		this.anexoPlanilhaEmail = anexoPlanilhaEmail;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}



	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Double getValorAbatimento() {
		return valorAbatimento;
	}

	public void setValorAbatimento(Double valorAbatimento) {
		this.valorAbatimento = valorAbatimento;
	}

	public Double getValorBoleto() {
		return valorBoleto;
	}

	public void setValorBoleto(Double valorBoleto) {
		this.valorBoleto = valorBoleto;
	}

	public Double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public Double getValorIof() {
		return valorIof;
	}

	public void setValorIof(Double valorIof) {
		this.valorIof = valorIof;
	}

	public Double getValorJurosMora() {
		return valorJurosMora;
	}

	public void setValorJurosMora(Double valorJurosMora) {
		this.valorJurosMora = valorJurosMora;
	}

	public Double getValorMulta() {
		return valorMulta;
	}

	public void setValorMulta(Double valorMulta) {
		this.valorMulta = valorMulta;
	}

	public CedenteDTO getCedente() {
		return cedente;
	}

	public void setCedente(CedenteDTO cedente) {
		this.cedente = cedente;
	}

	public void setCedente2(CedenteDTO cedente2) {
		this.cedente2 = cedente2;
	}

	public CedenteDTO getCedente2() {
		return cedente2;
	}

	public SacadoDTO getSacado() {
		return sacado;
	}

	public void setSacado(SacadoDTO sacado) {
		this.sacado = sacado;
	}

	public SacadorAvalistaDTO getSacadorAvalista() {
		return sacadorAvalista;
	}

	public void setSacadorAvalista(SacadorAvalistaDTO sacadorAvalista) {
		this.sacadorAvalista = sacadorAvalista;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public BigDecimal getValorCredito() {
		return valorCredito;
	}

	public void setValorCredito(BigDecimal valorCredito) {
		this.valorCredito = valorCredito;
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

	public void setTextoEmail(String textoEmail) {
		this.textoEmail = textoEmail;
	}

	public String getTextoEmail() {
		return textoEmail;
	}

	public void setAssuntoEmail(String assuntoEmail) {
		this.assuntoEmail = assuntoEmail;
	}

	public String getAssuntoEmail() {
		return assuntoEmail;
	}

	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}

	public Long getIdBoleto() {
		return idBoleto;
	}

	public void setInstrucoesBancarias(String instrucoesBancarias) {
		this.instrucoesBancarias = instrucoesBancarias;
	}

	public String getInstrucoesBancarias() {
		return instrucoesBancarias;
	}

	public Double getValorIR() {
		return valorIR;
	}

	public void setValorIR(Double valorIR) {
		this.valorIR = valorIR;
	}

	public Double getValorPIS() {
		return valorPIS;
	}

	public void setValorPIS(Double valorPIS) {
		this.valorPIS = valorPIS;
	}

	public Double getValorCOFINS() {
		return valorCOFINS;
	}

	public void setValorCOFINS(Double valorCOFINS) {
		this.valorCOFINS = valorCOFINS;
	}

	public Double getValorCSLL() {
		return valorCSLL;
	}

	public void setValorCSLL(Double valorCSLL) {
		this.valorCSLL = valorCSLL;
	}

	public void setValorISS(Double valorISS) {
		this.valorISS = valorISS;
	}

	public Double getValorISS() {
		return valorISS;
	}

	public String getCaminhoFisicoBoleto() {
		return caminhoFisicoBoleto;
	}

	public void setCaminhoFisicoBoleto(String caminhoFisicoBoleto) {
		this.caminhoFisicoBoleto = caminhoFisicoBoleto;
	}
	
	
}