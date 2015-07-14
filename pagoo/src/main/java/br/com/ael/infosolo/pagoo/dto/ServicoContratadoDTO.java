package br.com.ael.infosolo.pagoo.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServicoContratadoDTO extends PagooDTO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date dtCobranca;

	private Long id;

	private String descricao;

	private String nome;

	private BigDecimal valor;

	private BigDecimal valorRepasse;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorRepasse() {
		return valorRepasse;
	}

	public void setValorRepasse(BigDecimal valorRepasse) {
		this.valorRepasse = valorRepasse;
	}

	public Date getDtCobranca() {
		return dtCobranca;
	}

	public void setDtCobranca(Date dtCobranca) {
		this.dtCobranca = dtCobranca;
	}
	
	
	
}
