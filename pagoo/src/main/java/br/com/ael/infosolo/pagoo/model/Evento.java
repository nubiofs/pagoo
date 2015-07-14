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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the evento database table.
 * 
 */
/**
 * @author david
 *
 */
@Entity
@Table(name="evento")
@NamedQuery(name="Evento.findAll", query="SELECT e FROM Evento e")
@XmlRootElement
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_evento", nullable=false)
	private Date dataEvento;

	@Column(name="valor_total", nullable=false, precision=10, scale=4)
	private BigDecimal valorTotal;

	@Column(name="valor_total_repasse", nullable=false, precision=10, scale=4)
	private BigDecimal valorTotalRepasse;

	//bi-directional many-to-one association to Entidade
	@ManyToOne
	@JoinColumn(name="id_entidade", nullable=false)
	private Entidade entidade;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable=false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="id_comprador", nullable=true)
	private Comprador comprador;

	//bi-directional many-to-many association to Cobranca
	@ManyToMany
	@JoinTable(
		name="evento_x_cobranca"
		, joinColumns={
			@JoinColumn(name="id_evento", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_cobranca", nullable=false)
			}
		)
	@JsonIgnore
	private Set<Cobranca> cobrancas = new HashSet<Cobranca>();

	//bi-directional many-to-one association to Servico
	@OneToMany(mappedBy="evento")
	@JsonIgnore
	private Set<Servico> servicos = new HashSet<Servico>();

	public Evento() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataEvento() {
		return this.dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public BigDecimal getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorTotalRepasse() {
		return this.valorTotalRepasse;
	}

	public void setValorTotalRepasse(BigDecimal valorTotalRepasse) {
		this.valorTotalRepasse = valorTotalRepasse;
	}

	public Entidade getEntidade() {
		return this.entidade;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<Cobranca> getCobrancas() {
		return this.cobrancas;
	}

	public void setCobrancas(Set<Cobranca> cobrancas) {
		this.cobrancas = cobrancas;
	}

	public Set<Servico> getServicos() {
		return this.servicos;
	}

	public void setServicos(Set<Servico> servicos) {
		this.servicos = servicos;
	}

	public Servico addServico(Servico servico) {
		getServicos().add(servico);
		servico.setEvento(this);

		return servico;
	}

	public Servico removeServico(Servico servico) {
		getServicos().remove(servico);
		servico.setEvento(null);

		return servico;
	}

	public Comprador getComprador() {
		return comprador;
	}

	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}
	

}