package br.com.infosolo.cobranca.dominio.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Informações relacionadas ao Sacado.
 * 
 * @author david
 * 
 */
public class SacadoDTO {
	private String cpfCnpjSacado;
	private String nome;
	private List<EnderecoDTO> enderecos = new ArrayList<EnderecoDTO>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<EnderecoDTO> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoDTO> enderecos) {
		this.enderecos = enderecos;
	}

	public String getCpfCnpjSacado() {
		return cpfCnpjSacado;
	}

	public void setCpfCnpjSacado(String cpfCnpjSacado) {
		this.cpfCnpjSacado = cpfCnpjSacado;
	}

}
