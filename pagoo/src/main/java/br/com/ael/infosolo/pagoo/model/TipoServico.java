package br.com.ael.infosolo.pagoo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the tipo_servico database table.
 * 
 */
@Entity
@Table(name="tipo_servico")
@NamedQuery(name="TipoServico.findAll", query="SELECT t FROM TipoServico t")
@XmlRootElement
public class TipoServico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(nullable=false, length=255)
	private String descricao;

	@Column(nullable=false, length=30)
	private String nome;

	@Column(nullable=false, precision=10, scale=4)
	private BigDecimal valor;

	@Column(name="valor_repasse", nullable=false, precision=10, scale=4)
	private BigDecimal valorRepasse;

	//bi-directional many-to-one association to Servico
	@OneToMany(mappedBy="tipoServico")
	@JsonIgnore
	private Set<Servico> servicos = new HashSet<Servico>();

	//bi-directional many-to-one association to Segmento
	@ManyToOne
	@JoinColumn(name="id_segmento", nullable=false)
	@JsonIgnore
	private Segmento segmento;

	public TipoServico() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorRepasse() {
		return this.valorRepasse;
	}

	public void setValorRepasse(BigDecimal valorRepasse) {
		this.valorRepasse = valorRepasse;
	}

	public Set<Servico> getServicos() {
		return this.servicos;
	}

	public void setServicos(Set<Servico> servicos) {
		this.servicos = servicos;
	}

	public Servico addServico(Servico servico) {
		getServicos().add(servico);
		servico.setTipoServico(this);

		return servico;
	}

	public Servico removeServico(Servico servico) {
		getServicos().remove(servico);
		servico.setTipoServico(null);

		return servico;
	}

	public Segmento getSegmento() {
		return this.segmento;
	}

	public void setSegmento(Segmento segmento) {
		this.segmento = segmento;
	}

}