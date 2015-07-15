package br.com.infosolo.cobranca.dominio.dto;

import java.util.List;

/**
 * DTO com informações do Cedente. Utilizado pelo serviço WEB.
 * 
 * @author david
 * 
 */
public class CedenteDTO {
	private String cpfCnpjCedente;
	private Long codigoConvenio;
	private List<EnderecoDTO> enderecos;
	private String instrucoes;
	private String informacaoExtra1;
	private String informacaoExtra2;

	public String getCpfCnpjCedente() {
		return cpfCnpjCedente;
	}

	public void setCpfCnpjCedente(String cpfCnpjCedente) {
		this.cpfCnpjCedente = cpfCnpjCedente;
	}

	public List<EnderecoDTO> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoDTO> enderecos) {
		this.enderecos = enderecos;
	}

	public Long getCodigoConvenio() {
		return codigoConvenio;
	}

	public void setCodigoConvenio(Long codigoConvenio) {
		this.codigoConvenio = codigoConvenio;
	}

	public void setInstrucoes(String instrucoes) {
		this.instrucoes = instrucoes;
	}

	public String getInstrucoes() {
		return instrucoes;
	}

	public String getInformacaoExtra1() {
		return informacaoExtra1;
	}

	public void setInformacaoExtra1(String informacaoExtra1) {
		this.informacaoExtra1 = informacaoExtra1;
	}

	public String getInformacaoExtra2() {
		return informacaoExtra2;
	}

	public void setInformacaoExtra2(String informacaoExtra2) {
		this.informacaoExtra2 = informacaoExtra2;
	}

}
