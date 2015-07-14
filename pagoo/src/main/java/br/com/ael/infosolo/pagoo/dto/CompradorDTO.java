package br.com.ael.infosolo.pagoo.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO para ser utilizado na hora de executar uma compra / geração
 * de bordero / boleto / guia.
 * Comprador - dados do sacado.
 * @author David Faulstich Diniz Reis (davidfdr@gmailcom)
 * @since 07/07/2015
 *
 */
@XmlRootElement
public class CompradorDTO extends PagooDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private String cpfcnpj;
	private String placa;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpfcnpj() {
		return cpfcnpj;
	}
	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}

}
