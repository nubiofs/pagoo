package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sacador_avalista database table.
 * 
 */
@Entity
@Table(name="sacador_avalista",schema = "cobranca")
public class SacadorAvalistaEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="numero_cpf_cnpj_avalista", unique=true, nullable=false, length=14)
	private String numeroCpfCnpjAvalista;

	@Column(length=300)
	private String nome;

	//bi-directional many-to-one association to BoletoEntidade
	@OneToMany(mappedBy="sacadorAvalista")
	private List<BoletoEntidade> boletos;

	//bi-directional many-to-one association to EnderecoEntidade
	@OneToMany(mappedBy="sacadorAvalista")
	private List<EnderecoEntidade> enderecos;

    public SacadorAvalistaEntidade() {
    }

	public String getNumeroCpfCnpjAvalista() {
		return this.numeroCpfCnpjAvalista;
	}

	public void setNumeroCpfCnpjAvalista(String numeroCpfCnpjAvalista) {
		this.numeroCpfCnpjAvalista = numeroCpfCnpjAvalista;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<BoletoEntidade> getBoletos() {
		return this.boletos;
	}

	public void setBoletos(List<BoletoEntidade> boletos) {
		this.boletos = boletos;
	}
	
	public List<EnderecoEntidade> getEnderecos() {
		return this.enderecos;
	}

	public void setEnderecos(List<EnderecoEntidade> enderecos) {
		this.enderecos = enderecos;
	}
	
}