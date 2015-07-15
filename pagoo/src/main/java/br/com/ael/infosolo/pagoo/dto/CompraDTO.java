package br.com.ael.infosolo.pagoo.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO para ser utilizado na hora de executar uma compra / geração
 * de bordero / boleto / guia.
 * @author David Faulstich Diniz Reis (davidfdr@gmailcom)
 * @since 07/07/2015
 *
 */
@XmlRootElement
public class CompraDTO extends PagooDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CompradorDTO comprador;
	
	private ServicoContratadoDTO servicoContratado;
	
	private ServicoContratadoDTO[] servicosContratados;
	
	/**
	 * Nosso numero do boleto gerado.
	 */
	private String nossoNumero;
	
	/**
	 * Nosso numero do 2 boleto (segundo codigo de barras)
	 */
	private String nossoNumero2;

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public String getNossoNumero2() {
		return nossoNumero2;
	}

	public void setNossoNumero2(String nossoNumero2) {
		this.nossoNumero2 = nossoNumero2;
	}

	public CompradorDTO getComprador() {
		return comprador;
	}

	public void setComprador(CompradorDTO comprador) {
		this.comprador = comprador;
	}

	public ServicoContratadoDTO getServicoContratado() {
		return servicoContratado;
	}

	public void setServicoContratado(ServicoContratadoDTO servicoContratado) {
		this.servicoContratado = servicoContratado;
	}

	public ServicoContratadoDTO[] getServicosContratados() {
		return servicosContratados;
	}

	public void setServicosContratados(ServicoContratadoDTO[] servicosContratados) {
		this.servicosContratados = servicosContratados;
	}




	
	
}
