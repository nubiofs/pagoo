package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the cedente database table.
 * 
 */
@Entity
@Table(name="cedente",schema = "cobranca")
public class CedenteEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CedenteEntidadePK id;

	@Column(length=3)
	private String carteira;

	@Column(name="codigo_geral", nullable=false, length=50)
	private String codigoGeral;

	@Column(name="identificacao_aceite", nullable=false, length=2)
	private String identificacaoAceite;

	@Column(name="instrucao_sacado", length=300)
	private String instrucaoSacado;

	@Column(name="local_pagamento", length=300)
	private String localPagamento;

	@Column(nullable=false, length=300)
	private String nome;

	@Column(name="quantidade_dias_protesto", nullable=false, precision=3)
	private BigDecimal quantidadeDiasProtesto;

	@Column(name="tipo_cobranca", precision=1)
	private BigDecimal tipoCobranca;

	//bi-directional many-to-one association to BoletoEntidade
	@OneToMany(mappedBy="cedente")
	private List<BoletoEntidade> boletos;

	//bi-directional many-to-one association to ContaBancariaEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_conta", nullable=false)
	private ContaBancariaEntidade contaBancaria;

	//bi-directional many-to-one association to EnderecoEntidade
	@OneToMany(mappedBy="cedente")
	private List<EnderecoEntidade> enderecos;

	//bi-directional many-to-one association to InstrucoesBancariaEntidade
	@OneToMany(mappedBy="cedente")
	private List<InstrucoesBancariaEntidade> instrucoesBancarias;

	@Basic(fetch = FetchType.LAZY) 
	@Column(name="logo")
	private byte[] logo;

    public CedenteEntidade() {
    }

	public CedenteEntidadePK getId() {
		return this.id;
	}

	public void setId(CedenteEntidadePK id) {
		this.id = id;
	}
	
	public String getCarteira() {
		return this.carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public String getCodigoGeral() {
		return this.codigoGeral;
	}

	public void setCodigoGeral(String codigoGeral) {
		this.codigoGeral = codigoGeral;
	}

	public String getIdentificacaoAceite() {
		return this.identificacaoAceite;
	}

	public void setIdentificacaoAceite(String identificacaoAceite) {
		this.identificacaoAceite = identificacaoAceite;
	}

	public String getInstrucaoSacado() {
		return this.instrucaoSacado;
	}

	public void setInstrucaoSacado(String instrucaoSacado) {
		this.instrucaoSacado = instrucaoSacado;
	}

	public String getLocalPagamento() {
		return this.localPagamento;
	}

	public void setLocalPagamento(String localPagamento) {
		this.localPagamento = localPagamento;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getQuantidadeDiasProtesto() {
		return this.quantidadeDiasProtesto;
	}

	public void setQuantidadeDiasProtesto(BigDecimal quantidadeDiasProtesto) {
		this.quantidadeDiasProtesto = quantidadeDiasProtesto;
	}

	public BigDecimal getTipoCobranca() {
		return this.tipoCobranca;
	}

	public void setTipoCobranca(BigDecimal tipoCobranca) {
		this.tipoCobranca = tipoCobranca;
	}

	public List<BoletoEntidade> getBoletos() {
		return this.boletos;
	}

	public void setBoletos(List<BoletoEntidade> boletos) {
		this.boletos = boletos;
	}
	
	public ContaBancariaEntidade getContaBancaria() {
		return this.contaBancaria;
	}

	public void setContaBancaria(ContaBancariaEntidade contaBancaria) {
		this.contaBancaria = contaBancaria;
	}
	
	public List<EnderecoEntidade> getEnderecos() {
		return this.enderecos;
	}

	public void setEnderecos(List<EnderecoEntidade> enderecos) {
		this.enderecos = enderecos;
	}
	
	public List<InstrucoesBancariaEntidade> getInstrucoesBancarias() {
		return this.instrucoesBancarias;
	}

	public void setInstrucoesBancarias(List<InstrucoesBancariaEntidade> instrucoesBancarias) {
		this.instrucoesBancarias = instrucoesBancarias;
	}

	public byte[] getLogo() {
		return this.logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

}