package br.com.ael.infosolo.pagoo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the cobranca database table.
 * 
 */
@Entity
@Table(name="cobranca_pagoo")
@NamedQuery(name="Cobranca.findAll", query="SELECT c FROM Cobranca c")
@XmlRootElement
public class Cobranca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_cobranca", nullable=false)
	private Date dataCobranca;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_pagamento")
	private Date dataPagamento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_vencimento")
	private Date dataVencimento;

	@Column(nullable=false, precision=10, scale=4)
	private BigDecimal valor;

	@Column(name="valor_pago", precision=10, scale=4)
	private BigDecimal valorPago;

	@Column(name="valor_repasse", nullable=false, precision=10, scale=4)
	private BigDecimal valorRepasse;

	//bi-directional many-to-one association to ConvenioBancario
	@ManyToOne
	@JoinColumn(name="id_convenio")
	private ConvenioBancario convenioBancario;

	//bi-directional many-to-many association to Evento
	@ManyToMany(mappedBy="cobrancas")
	@JsonIgnore
	private Set<Evento> eventos = new HashSet<Evento>();

	public Cobranca() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCobranca() {
		return this.dataCobranca;
	}

	public void setDataCobranca(Date dataCobranca) {
		this.dataCobranca = dataCobranca;
	}

	public Date getDataPagamento() {
		return this.dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDataVencimento() {
		return this.dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorPago() {
		return this.valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public BigDecimal getValorRepasse() {
		return this.valorRepasse;
	}

	public void setValorRepasse(BigDecimal valorRepasse) {
		this.valorRepasse = valorRepasse;
	}

	public ConvenioBancario getConvenioBancario() {
		return this.convenioBancario;
	}

	public void setConvenioBancario(ConvenioBancario convenioBancario) {
		this.convenioBancario = convenioBancario;
	}

	public Set<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}

}