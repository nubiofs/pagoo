package br.com.ael.infosolo.pagoo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the entidade database table.
 * 
 */
@Entity
@Table(name="entidade")
@NamedQueries ({
	@NamedQuery(name="Entidade.findAll", query="SELECT e FROM Entidade e"),
	//@NamedQuery(name="Entidade.findByIdUsuario", query="SELECT u FROM Usuario u JOIN FETCH u.entidade as e where u.id = :id")
	@NamedQuery(name="Entidade.findByIdUsuario", query="SELECT u,u.entidade FROM Usuario u JOIN FETCH u.entidade as e where u.id = :id")
})
@XmlRootElement
public class Entidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name="id_segmento", updatable=false, insertable =false)
	private Long idSegmento;
	
	@NotNull
	@Size(min = 1, max = 100)
	@Column(nullable=false)
	private String nome;

	@Column(length=11)
    @Size(min = 10, max = 11)
    @Digits(fraction = 0, integer = 11)		
	private String celular;

	@Column(length=8)
	@Size(min = 8, max = 8)
	private String cep;

	@Column(length=50)
	@Size(min = 3, max = 50)
	private String cidade;

	@Column(length=255)
	@Size(max = 50)
	private String complemento;

	@Column(length=14)
	@Size(min = 12, max = 14)
	private String cpfcnpj;

	@Column(name="data_inclusao", nullable=false)
	private Timestamp dataInclusao;

	@Column(name="email_responsavel", nullable=false, length=50)
    @NotNull
    @NotEmpty
    @Email		
	private String emailResponsavel;
	
	@Column(length=255)
	private String endereco;

	@Column(length=2)
	private String estado;

	@Column(nullable=false, length=11)
    @Size(min = 10, max = 11)
    @Digits(fraction = 0, integer = 11)		
	private String telefone;

	//bi-directional many-to-one association to Segmento
	@ManyToOne
	@JoinColumn(name="id_segmento", nullable=false)
	private Segmento segmento;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="entidade")
	@JsonIgnore
	private Set<Evento> eventos = new HashSet<Evento>();

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="entidade")
	@JsonIgnore
	private Set<Usuario> usuarios = new HashSet<Usuario>();

	public Entidade() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
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

	public String getCpfcnpj() {
		return this.cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

	public Timestamp getDataInclusao() {
		return this.dataInclusao;
	}

	public void setDataInclusao(Timestamp dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getEmailResponsavel() {
		return this.emailResponsavel;
	}

	public void setEmailResponsavel(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Segmento getSegmento() {
		return this.segmento;
	}

	public void setSegmento(Segmento segmento) {
		this.segmento = segmento;
	}

	public Set<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento addEvento(Evento evento) {
		getEventos().add(evento);
		evento.setEntidade(this);

		return evento;
	}

	public Evento removeEvento(Evento evento) {
		getEventos().remove(evento);
		evento.setEntidade(null);

		return evento;
	}

	public Set<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setEntidade(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setEntidade(null);

		return usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdSegmento() {
		return idSegmento;
	}

	public void setIdSegmento(Long idSegmento) {
		this.idSegmento = idSegmento;
	}
	

}