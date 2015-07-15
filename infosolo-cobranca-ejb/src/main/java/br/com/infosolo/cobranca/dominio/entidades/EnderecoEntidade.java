package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the endereco database table.
 * 
 */
@Entity
@Table(name="endereco",schema = "cobranca")
@NamedQueries({
	@NamedQuery(name="retornarEnderecoSacadoPorCep", query="SELECT c FROM EnderecoEntidade c WHERE c.cep = :cep AND c.sacado.numeroCpfCnpjSacado = :cpfCnpj"), 
	@NamedQuery(name="retornarEnderecoSacadoPorEmail", query="SELECT c FROM EnderecoEntidade c WHERE c.email = :email AND c.sacado.numeroCpfCnpjSacado = :cpfCnpj"), 
	@NamedQuery(name="retornarEnderecoCedentePorCep", query="FROM EnderecoEntidade endereco WHERE endereco.cep = :cep") 
})
public class EnderecoEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="ENDERECO_IDENDERECO_GENERATOR", sequenceName="COBRANCA.SQ_ENDERECO")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ENDERECO_IDENDERECO_GENERATOR")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_endereco", unique=true, nullable=false)
	private Long idEndereco;

	@Column(length=200)
	private String bairro;

	@Column(length=8)
	private String cep;

	@Column(length=100)
	private String cidade;

	@Column(length=400)
	private String complemento;

	@Column(length=200)
	private String descricao;

	@Column(length=7000)
	private String email;

	@Column(length=200)
	private String logradouro;

	@Column(length=5)
	private String numero;

	@Column(length=2)
	private String uf;

	//bi-directional many-to-one association to CedenteEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="codigo_convenio", referencedColumnName="codigo_convenio"),
		@JoinColumn(name="numero_cpf_cnpj_cedente", referencedColumnName="numero_cpf_cnpj_cedente")
		})
	private CedenteEntidade cedente;

	//bi-directional many-to-one association to SacadoEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="numero_cpf_cnpj_sacado")
	private SacadoEntidade sacado;

	//bi-directional many-to-one association to SacadorAvalistaEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="numero_cpf_cnpj_avalista")
	private SacadorAvalistaEntidade sacadorAvalista;

    public EnderecoEntidade() {
    }

	public Long getIdEndereco() {
		return this.idEndereco;
	}

	public void setIdEndereco(Long idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public CedenteEntidade getCedente() {
		return this.cedente;
	}

	public void setCedente(CedenteEntidade cedente) {
		this.cedente = cedente;
	}
	
	public SacadoEntidade getSacado() {
		return this.sacado;
	}

	public void setSacado(SacadoEntidade sacado) {
		this.sacado = sacado;
	}
	
	public SacadorAvalistaEntidade getSacadorAvalista() {
		return this.sacadorAvalista;
	}

	public void setSacadorAvalista(SacadorAvalistaEntidade sacadorAvalista) {
		this.sacadorAvalista = sacadorAvalista;
	}
	
}