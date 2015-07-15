package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
//import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the boleto database table.
 * 
 */
@Entity
@Table(name="boleto",schema = "cobranca")
@NamedQueries({
	@NamedQuery(name="boleto.retornarBoletoLitePorNossoNumero", query="FROM BoletoEntidadeLite boleto WHERE boleto.nossoNumero = :nossoNumero"), 
	@NamedQuery(name="retornarPagamentosEfetuadosDiario", query="FROM BoletoEntidadeLite boleto WHERE boleto.dataCredito IS NOT NULL AND boleto.dataCredito < :dataFinal AND boleto.cedente = :cedente AND boleto.boletoComissao IS NULL"),
	@NamedQuery(name="retornarBoletoLitePorNumeroDocumento", query="FROM BoletoEntidadeLite boleto WHERE boleto.numeroDocumento = :numeroDocumento")
})
public class BoletoEntidadeLite implements Serializable {
	private static final long serialVersionUID = 5482257570567757699L;

	@Id
//	@SequenceGenerator(name="BOLETO_IDBOLETO_GENERATOR", sequenceName="SQ_BOLETO")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BOLETO_IDBOLETO_GENERATOR")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_boleto", unique=true, nullable=false, precision=131089)
	private Long idBoleto;
	
	@Column(name="assunto_email", length=2147483647)
	private String assuntoEmail;

    @Temporal(TemporalType.DATE)
	@Column(name="data_credito")
	private Date dataCredito;

    @Temporal(TemporalType.DATE)
	@Column(name="data_emissao")
	private Date dataEmissao;

    @Temporal(TemporalType.DATE)
	@Column(name="data_pagamento")
	private Date dataPagamento;

	@Column(name="data_pagamento_computado")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataPagamentoComputado;

	@Column(name="data_processamento")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataProcessamento;

	@Column(name="data_ultimo_acesso")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltimoAcesso;

    @Temporal(TemporalType.DATE)
	@Column(name="data_vencimento")
	private Date dataVencimento;

	@Column(name="ic_pagamento_computado", length=1)
	private String icPagamentoComputado;

	@Column(name="nosso_numero", length=20)
	private String nossoNumero;

	@Column(name="numero_documento", length=20)
	private String numeroDocumento;

	@Column(name="quantidade_acesso_email", precision=4)
	private Long quantidadeAcessoEmail;

	@Column(name="texto_email", length=2147483647)
	private String textoEmail;

	@Column(name="token_acesso_email", length=2147483647)
	private String tokenAcessoEmail;

	@Column(name="valor_abatimento", precision=131089)
	private BigDecimal valorAbatimento;

	@Column(name="valor_boleto", nullable=false, precision=131089)
	private BigDecimal valorBoleto;

	@Column(name="valor_credito", precision=131089)
	private BigDecimal valorCredito;

	@Column(name="valor_desconto", precision=131089)
	private BigDecimal valorDesconto;

	@Column(name="valor_iof", precision=131089)
	private BigDecimal valorIof;

	@Column(name="valor_juros_mora", precision=131089)
	private BigDecimal valorJurosMora;

	@Column(name="valor_multa", precision=131089)
	private BigDecimal valorMulta;

	//bi-directional many-to-one association to CedenteEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="codigo_convenio", referencedColumnName="codigo_convenio", nullable=false),
		@JoinColumn(name="numero_cpf_cnpj_cedente", referencedColumnName="numero_cpf_cnpj_cedente", nullable=false)
		})
	private CedenteEntidade cedente;

	//bi-directional many-to-one association to SacadoEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="numero_cpf_cnpj_sacado")
	private SacadoEntidade sacado;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_boleto_comissao")
	private BoletoEntidadeLite boletoComissao;

	@Column(name="valor_ir", precision=131089)
	private BigDecimal valorIr;

	@Column(name="valor_pis", precision=131089)
	private BigDecimal valorPis;

	@Column(name="valor_cofins", precision=131089)
	private BigDecimal valorCofins;

	@Column(name="valor_csll", precision=131089)
	private BigDecimal valorCsll;

	@Column(name="valor_iss", precision=131089)
	private BigDecimal valorIss;

	@Column(name="instrucoes_bancarias", length=800)
	private String instrucoesBancarias;

	@Column(name="numero_autenticacao", length=23)
	private String numeroAutenticacao;

    public BoletoEntidadeLite() {
    }

	public Long getIdBoleto() {
		return this.idBoleto;
	}

	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}

	public String getAssuntoEmail() {
		return this.assuntoEmail;
	}

	public void setAssuntoEmail(String assuntoEmail) {
		this.assuntoEmail = assuntoEmail;
	}

	public Date getDataCredito() {
		return this.dataCredito;
	}

	public void setDataCredito(Date dataCredito) {
		this.dataCredito = dataCredito;
	}

	public Date getDataEmissao() {
		return this.dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataPagamento() {
		return this.dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDataPagamentoComputado() {
		return this.dataPagamentoComputado;
	}

	public void setDataPagamentoComputado(Date dataPagamentoComputado) {
		this.dataPagamentoComputado = dataPagamentoComputado;
	}

	public Date getDataProcessamento() {
		return this.dataProcessamento;
	}

	public void setDataProcessamento(Date dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public Date getDataUltimoAcesso() {
		return this.dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public Date getDataVencimento() {
		return this.dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getIcPagamentoComputado() {
		return this.icPagamentoComputado;
	}

	public void setIcPagamentoComputado(String icPagamentoComputado) {
		this.icPagamentoComputado = icPagamentoComputado;
	}

	public String getNossoNumero() {
		return this.nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Long getQuantidadeAcessoEmail() {
		return this.quantidadeAcessoEmail;
	}

	public void setQuantidadeAcessoEmail(Long quantidadeAcessoEmail) {
		this.quantidadeAcessoEmail = quantidadeAcessoEmail;
	}

	public String getTextoEmail() {
		return this.textoEmail;
	}

	public void setTextoEmail(String textoEmail) {
		this.textoEmail = textoEmail;
	}

	public String getTokenAcessoEmail() {
		return this.tokenAcessoEmail;
	}

	public void setTokenAcessoEmail(String tokenAcessoEmail) {
		this.tokenAcessoEmail = tokenAcessoEmail;
	}

	public BigDecimal getValorAbatimento() {
		return this.valorAbatimento;
	}

	public void setValorAbatimento(BigDecimal valorAbatimento) {
		this.valorAbatimento = valorAbatimento;
	}

	public BigDecimal getValorBoleto() {
		return this.valorBoleto;
	}

	public void setValorBoleto(BigDecimal valorBoleto) {
		this.valorBoleto = valorBoleto;
	}

	public BigDecimal getValorCredito() {
		return this.valorCredito;
	}

	public void setValorCredito(BigDecimal valorCredito) {
		this.valorCredito = valorCredito;
	}

	public BigDecimal getValorDesconto() {
		return this.valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorIof() {
		return this.valorIof;
	}

	public void setValorIof(BigDecimal valorIof) {
		this.valorIof = valorIof;
	}

	public BigDecimal getValorJurosMora() {
		return this.valorJurosMora;
	}

	public void setValorJurosMora(BigDecimal valorJurosMora) {
		this.valorJurosMora = valorJurosMora;
	}

	public BigDecimal getValorMulta() {
		return this.valorMulta;
	}

	public void setValorMulta(BigDecimal valorMulta) {
		this.valorMulta = valorMulta;
	}

	public SacadoEntidade getSacado() {
		return this.sacado;
	}

	public void setSacado(SacadoEntidade sacado) {
		this.sacado = sacado;
	}
	
	public void setCedente(CedenteEntidade cedente) {
		this.cedente = cedente;
	}

	public CedenteEntidade getCedente() {
		return cedente;
	}

	public void setBoletoComissao(BoletoEntidadeLite boletoComissao) {
		this.boletoComissao = boletoComissao;
	}

	public BoletoEntidadeLite getBoletoComissao() {
		return boletoComissao;
	}

	public BigDecimal getValorIr() {
		return valorIr;
	}

	public void setValorIr(BigDecimal valorIr) {
		this.valorIr = valorIr;
	}

	public BigDecimal getValorPis() {
		return valorPis;
	}

	public void setValorPis(BigDecimal valorPis) {
		this.valorPis = valorPis;
	}

	public BigDecimal getValorCofins() {
		return valorCofins;
	}

	public void setValorCofins(BigDecimal valorCofins) {
		this.valorCofins = valorCofins;
	}

	public BigDecimal getValorCsll() {
		return valorCsll;
	}

	public void setValorCsll(BigDecimal valorCsll) {
		this.valorCsll = valorCsll;
	}

	public void setValorIss(BigDecimal valorIss) {
		this.valorIss = valorIss;
	}

	public BigDecimal getValorIss() {
		return valorIss;
	}

	public void setInstrucoesBancarias(String instrucoesBancarias) {
		this.instrucoesBancarias = instrucoesBancarias;
	}

	public String getInstrucoesBancarias() {
		return instrucoesBancarias;
	}

	public String getNumeroAutenticacao() {
		return numeroAutenticacao;
	}

	public void setNumeroAutenticacao(String numeroAutenticacao) {
		this.numeroAutenticacao = numeroAutenticacao;
	}
	
}