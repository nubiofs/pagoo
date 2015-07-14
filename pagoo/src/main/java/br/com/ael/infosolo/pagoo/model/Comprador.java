package br.com.ael.infosolo.pagoo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the comprador database table.
 * 
 */
@Entity
@Table(name="comprador")
@NamedQuery(name="Comprador.findAll", query="SELECT c FROM Comprador c")
@XmlRootElement
public class Comprador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=14)
	private String cpgcnpj;

	@Column(name="json_info_suplementares", length=2147483647)
	private String jsonInfoSuplementares;

	@Column(length=100)
	private String nome;
	
	@OneToMany(mappedBy="comprador")
	private Set<Evento> eventos = new HashSet<Evento>();

	public Comprador() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpgcnpj() {
		return this.cpgcnpj;
	}

	public void setCpgcnpj(String cpgcnpj) {
		this.cpgcnpj = cpgcnpj;
	}

	public String getJsonInfoSuplementares() {
		return this.jsonInfoSuplementares;
	}

	public void setJsonInfoSuplementares(String jsonInfoSuplementares) {
		this.jsonInfoSuplementares = jsonInfoSuplementares;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}
	
	

}