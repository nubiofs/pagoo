package br.com.ael.infosolo.pagoo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the segmento database table.
 * 
 */
@Entity
@Table(name="segmento")
@NamedQueries ({
		@NamedQuery(name="Segmento.findAll", query="SELECT s FROM Segmento s"),
		@NamedQuery(name="Segmento.findByIdUsuario", query="SELECT u, u.entidade, u.entidade.segmento FROM Usuario u JOIN FETCH u.entidade as e JOIN FETCH u.entidade.segmento as s where u.id = :idusuario") 
})
@XmlRootElement
public class Segmento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=255)
	private String descricao;

	@Column(nullable=false, length=50)
	private String nome;

	//bi-directional many-to-one association to Entidade
	@OneToMany(mappedBy="segmento")
	@JsonIgnore
	private Set<Entidade> entidades = new HashSet<Entidade>();

	//bi-directional many-to-one association to TipoServico
	@OneToMany(mappedBy="segmento")
	@JsonIgnore
	private Set<TipoServico> tipoServicos = new HashSet<TipoServico>();

	public Segmento() {
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

	public Set<Entidade> getEntidades() {
		return this.entidades;
	}

	public void setEntidades(Set<Entidade> entidades) {
		this.entidades = entidades;
	}

	public Entidade addEntidade(Entidade entidade) {
		getEntidades().add(entidade);
		entidade.setSegmento(this);

		return entidade;
	}

	public Entidade removeEntidade(Entidade entidade) {
		getEntidades().remove(entidade);
		entidade.setSegmento(null);

		return entidade;
	}

	public Set<TipoServico> getTipoServicos() {
		return this.tipoServicos;
	}

	public void setTipoServicos(Set<TipoServico> tipoServicos) {
		this.tipoServicos = tipoServicos;
	}

	public TipoServico addTipoServico(TipoServico tipoServico) {
		getTipoServicos().add(tipoServico);
		tipoServico.setSegmento(this);

		return tipoServico;
	}

	public TipoServico removeTipoServico(TipoServico tipoServico) {
		getTipoServicos().remove(tipoServico);
		tipoServico.setSegmento(null);

		return tipoServico;
	}

}