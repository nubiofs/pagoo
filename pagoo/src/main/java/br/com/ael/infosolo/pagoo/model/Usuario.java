package br.com.ael.infosolo.pagoo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
@XmlRootElement
public class Usuario implements Serializable,  UserDetails {
	
	public Usuario(String username) {
		this.username = username;
	}

	public Usuario(String username, Date expires) {
		this.username = username;
		this.expires = expires.getTime();
	}
	private static final long serialVersionUID = 1L;
	
	@Transient
	private String username;
	
	@Transient
	private String password;
	
	
	@Transient
	private Long expires;
	
	@Column(name="id_entidade", updatable=false, insertable =false)
	private Long idEntidade;
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;
	
    @NotNull
    @Size(min = 1, max = 100)
    @Pattern(regexp = "[^0-9]*", message = "Nome não deve conter números")
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
	@Size(min = 3, max = 255)
	private String complemento;

	@Column(length=14)
	@Size(min = 12, max = 14)
	private String cpfcnpj;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_inclusao", nullable=false)
	private Date dataInclusao;

	@Column(nullable=false, length=50)
    @NotNull
    @NotEmpty
    @Email	
	private String email;

	@Column(length=255)
	private String endereco;

	@Column(length=2)
	private String estado;

	@Column(length=100)
	private String senha;

	@Column(nullable=false, length=11)
    @Size(min = 10, max = 11)
    @Digits(fraction = 0, integer = 11)	
	private String telefone;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="usuario")
	@JsonIgnore
	private Set<Evento> eventos = new HashSet<Evento>();

	//bi-directional many-to-one association to Entidade
	@ManyToOne
	@JoinColumn(name="id_entidade")
	@JsonIgnore
	private Entidade entidade;

	//bi-directional many-to-one association to Perfil
	@ManyToOne
	@JoinColumn(name="id_perfil", nullable=false)
	@JsonIgnore
	private Perfil perfil;
	
	// wraper role name para nao precisar carregar todos os relacionamentos de perfil
	@Transient 
	private String role;

	public Usuario() {
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

	public Date getDataInclusao() {
		return this.dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@JsonIgnore
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Set<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento addEvento(Evento evento) {
		getEventos().add(evento);
		evento.setUsuario(this);

		return evento;
	}

	public Evento removeEvento(Evento evento) {
		getEventos().remove(evento);
		evento.setUsuario(null);

		return evento;
	}

	public Entidade getEntidade() {
		return this.entidade;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> retorno = new ArrayList<SimpleGrantedAuthority>();
		if(this.getPerfil() != null){
			retorno.add(new SimpleGrantedAuthority(this.getPerfil().getNome()));
		}
		return retorno;
	}

	@Override
	@JsonIgnore
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return username;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	public Long getExpires() {
		return expires;
	}

	public void setExpires(Long expires) {
		this.expires = expires;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public Long getIdEntidade() {
		return idEntidade;
	}

	public void setIdEntidade(Long idEntidade) {
		this.idEntidade = idEntidade;
	}
	
	

}