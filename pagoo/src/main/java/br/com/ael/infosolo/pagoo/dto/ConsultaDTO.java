package br.com.ael.infosolo.pagoo.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO para ser utilizado na hora de executar uma consulta / regeração
 * de bordero / boleto / guia.
 * @author David Faulstich Diniz Reis (davidfdr@gmailcom)
 * @since 12/07/2015
 *
 */
@XmlRootElement
public class ConsultaDTO extends PagooDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8333140703452436474L;
	
	private CompradorDTO comprador;
	
	private ServicoContratadoDTO servicoContratado;
	
	/**
	 * Nosso numero do boleto principal ou unico. (Boleto principal em caso de duas guias)
	 * TODO: esta no DTO para ja carregar os boletos para o link de download.
	 */
	private String nossoNumero;
	
	/**
	 * Segundo nosso numero do segundo codigo de barras
	 */
	private String nossoNumero2;
	
	
	
	private Long idEntidade;
	
	
	
	
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

	public Long getIdEntidade() {
		return idEntidade;
	}

	public void setIdEntidade(Long idEntidade) {
		this.idEntidade = idEntidade;
	}
	
	
}
