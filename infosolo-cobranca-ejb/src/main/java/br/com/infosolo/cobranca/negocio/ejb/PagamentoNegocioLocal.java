package br.com.infosolo.cobranca.negocio.ejb;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.infosolo.cobranca.dominio.dto.CedenteDTO;
import br.com.infosolo.cobranca.dominio.dto.PagamentoDTO;
import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidadeLite;

@Local
public interface PagamentoNegocioLocal {
	/**
	 * Retorno de lista de pagamentos.
	 * @param listaBoleto
	 * @return Lista com os pagamentos computados pelos retornos.
	 */
	public List<PagamentoDTO> retornarListaPagamentos(CedenteDTO cedente);
	
	/**
	 * Informa ao sistema de cobrança que o retorno do pagamento já foi computado.
	 * @param listaBoleto
	 * @return Lista com os pagamentos computados pelos retornos.
	 */
	public void confirmarLeituraPagamento(List<PagamentoDTO> pagamentos);
	
	/**
	 * Retorna o somatorio do total de pagamentos creditados na data informada para o convenio informado.
	 * @param cedente
	 * @param dataCredito
	 * @return
	 */
	public List<BoletoEntidadeLite> retornarPagamentosEfetuados(CedenteDTO cedente);
	
}
