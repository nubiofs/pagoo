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
	
	private Long idEntidade;
	
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
