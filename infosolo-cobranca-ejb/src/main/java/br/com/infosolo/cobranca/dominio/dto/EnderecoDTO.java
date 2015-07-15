package br.com.infosolo.cobranca.dominio.dto;

import java.io.Serializable;


/**
 * Informações de Endereço.
 * @author david
 *
 */
public class EnderecoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long idEndereco;

	private String bairro;

	private String cep;

	private String cidade;

	private String complemento;

	private String descricao;

	private String email;

	private String logradouro;

	private String numero;

	private String uf;

    public EnderecoDTO() {
    }

	public long getIdEndereco() {
		return this.idEndereco;
	}

	public void setIdEndereco(long idEndereco) {
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
	
}