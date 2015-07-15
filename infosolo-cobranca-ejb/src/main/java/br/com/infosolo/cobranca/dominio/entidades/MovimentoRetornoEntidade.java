package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="movimento_retorno",schema = "cobranca")
public class MovimentoRetornoEntidade implements Serializable {
	private static final long serialVersionUID = -1694841143722289494L;
	
	@EmbeddedId
	private MovimentoRetornoEntidadePK id;
	
	@Column(name="descricao",length=255)
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public MovimentoRetornoEntidadePK getId() {
		return id;
	}
	
	public void setId(MovimentoRetornoEntidadePK id) {
		this.id = id;
	}
	
}
