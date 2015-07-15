package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sacado database table.
 * 
 */
@Entity
@Table(name="sacado",schema = "cobranca")
public class SacadoEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="numero_cpf_cnpj_sacado", unique=true, nullable=false, length=14)
	private String numeroCpfCnpjSacado;

	@Column(length=300)
	private String nome;

	//bi-directional many-to-one association to BoletoEntidade
	@OneToMany(mappedBy="sacado")
	private List<BoletoEntidade> boletos;

	//bi-directional many-to-one association to EnderecoEntidade
	@OneToMany(mappedBy="sacado")
	private List<EnderecoEntidade> enderecos;

    public SacadoEntidade() {
    }

	public String getNumeroCpfCnpjSacado() {
		return this.numeroCpfCnpjSacado;
	}

	public void setNumeroCpfCnpjSacado(String numeroCpfCnpjSacado) {
		this.numeroCpfCnpjSacado = numeroCpfCnpjSacado;
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