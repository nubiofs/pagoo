package br.com.infosolo.cobranca.dominio.dto;

import java.util.List;
/**
 * Informações referentes ao Sacador Avalista.
 * @author david
 *
 */
public class SacadorAvalistaDTO {
	private String cpfCnpjSacadorAvalista;
	private String nome;
	private List<EnderecoDTO> enderecos;
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
	public String getCpfCnpjSacadorAvalista() {
		return cpfCnpjSacadorAvalista;
	}
	public void setCpfCnpjSacadorAvalista(String cpfCnpjSacadorAvalista) {
		this.cpfCnpjSacadorAvalista = cpfCnpjSacadorAvalista;
	}
	
}
