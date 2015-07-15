package br.com.ael.infosolo.pagoo.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the convenio_bancario database table.
 * 
 */
@Entity
@Table(name="convenio_bancario")
@NamedQuery(name="ConvenioBancario.findAll", query="SELECT c FROM ConvenioBancario c")
@XmlRootElement
public class ConvenioBancario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="agencia_banco", nullable=false, length=10)
	private String agenciaBanco;

	@Column(name="cod_banco", nullable=false, length=10)
	private String codBanco;

	@Column(name="cod_convenio", nullable=false, length=10)
	private String codConvenio;

	@Column(name="conta_banco", nullable=false, length=10)
	private String contaBanco;

	@Column(name="nome_banco", nullable=false, length=255)
	private String nomeBanco;

	@Column(name="ativo")
	private Boolean ativo;
	
	@Column(name="cpfcnpj",length=14)
	@Size(min = 12, max = 14)
	private String cpfcnpj;
	
	//bi-directional many-to-one association to Cobranca
	@OneToMany(mappedBy="convenioBancario")
	private Set<Cobranca> cobrancas;

	//bi-directional many-to-many association to Cobranca
	@ManyToMany
	@JoinTable(
		name="convenio_x_entidade"
		, joinColumns={
			@JoinColumn(name="id_convenio", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_entidade", nullable=false)
			}
		)
	@JsonIgnore
	private Set<Entidade> entidades = new HashSet<Entidade>();
	
	@Length(max=1)
	@Column(name="tipo",length=1)
	private String tipo;
	
	public ConvenioBancario() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAgenciaBanco() {
		return this.agenciaBanco;
	}

	public void setAgenciaBanco(String agenciaBanco) {
		this.agenciaBanco = agenciaBanco;
	}

	public String getCodBanco() {
		return this.codBanco;
	}

	public void setCodBanco(String codBanco) {
		this.codBanco = codBanco;
	}

	public String getCodConvenio() {
		return this.codConvenio;
	}

	public void setCodConvenio(String codConvenio) {
		this.codConvenio = codConvenio;
	}

	public String getContaBanco() {
		return this.contaBanco;
	}

	public void setContaBanco(String contaBanco) {
		this.contaBanco = contaBanco;
	}

	public String getNomeBanco() {
		return this.nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public Set<Cobranca> getCobrancas() {
		return this.cobrancas;
	}

	public void setCobrancas(Set<Cobranca> cobrancas) {
		this.cobrancas = cobrancas;
	}

	public Cobranca addCobranca(Cobranca cobranca) {
		getCobrancas().add(cobranca);
		cobranca.setConvenioBancario(this);

		return cobranca;
	}

	public Cobranca removeCobranca(Cobranca cobranca) {
		getCobrancas().remove(cobranca);
		cobranca.setConvenioBancario(null);

		return cobranca;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getCpfcnpj() {
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

	public Set<Entidade> getEntidades() {
		return entidades;
	}

	public void setEntidades(Set<Entidade> entidades) {
		this.entidades = entidades;
	}
	
	

}