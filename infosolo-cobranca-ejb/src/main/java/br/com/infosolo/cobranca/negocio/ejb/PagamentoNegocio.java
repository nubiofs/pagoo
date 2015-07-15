package br.com.infosolo.cobranca.negocio.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.infosolo.cobranca.dominio.dto.CedenteDTO;
import br.com.infosolo.cobranca.dominio.dto.PagamentoDTO;
import br.com.infosolo.cobranca.dominio.entidades.BancoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidadeLite;
import br.com.infosolo.cobranca.dominio.entidades.CedenteEntidade;
import br.com.infosolo.cobranca.dominio.entidades.CedenteEntidadePK;
import br.com.infosolo.cobranca.dominio.entidades.ContaBancariaEntidade;
import br.com.infosolo.cobranca.util.Constantes;
import br.com.infosolo.comum.util.Logger;

/**
 * Classe com negocios relacionados a efetivação de pagamentos de boleto.
 */
@Stateless
public class PagamentoNegocio implements PagamentoNegocioLocal {
	private static Logger logger = new Logger(PagamentoNegocio.class.getName());

	@PersistenceContext(unitName = "infosolo-cobraca")
	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.infosolo.cobranca.negocio.ejb.PagamentosNegocioLocal#
	 * retornarListaPagamentos(java.util.List)
	 */
	public List<PagamentoDTO> retornarListaPagamentos(CedenteDTO cedente) {
		CedenteEntidadePK cedenteID = new CedenteEntidadePK();
		cedenteID.setCodigoConvenio(cedente.getCodigoConvenio());
		cedenteID.setNumeroCpfCnpjCedente(cedente.getCpfCnpjCedente());
		CedenteEntidade cedenteEntidade = em.find(CedenteEntidade.class, cedenteID);
	
		Query query = em.createNamedQuery("retornarPagamentosEfetuados");
		query.setParameter("cedente", cedenteEntidade);
		
		ContaBancariaEntidade contaBancariaEntidade = cedenteEntidade.getContaBancaria();
		BancoEntidade bancoEntidade = contaBancariaEntidade.getBanco();
		
		@SuppressWarnings("unchecked")
		List<BoletoEntidade> listaBoletoEntidade = query.getResultList();
		List<PagamentoDTO> listaPagamentos = new ArrayList<PagamentoDTO>();
		
		for (BoletoEntidade boletoEntidade : listaBoletoEntidade) {
			PagamentoDTO pagamento = new PagamentoDTO();
			if (cedenteEntidade.getTipoCobranca().intValue() == 1) {
				pagamento.setArquivoRetorno("ARQUIVO COB - COBRANCA REGISTRADA - " + cedenteEntidade.getId().getCodigoConvenio());
			}
			else {
				pagamento.setArquivoRetorno("ARQUIVO COB - COBRANCA NAO REGISTRADA - " + cedenteEntidade.getId().getCodigoConvenio());
			}
			pagamento.setBanco(bancoEntidade.getNomeBanco());
			pagamento.setCodigoCedente(cedenteID.getCodigoConvenio().toString());
			pagamento.setDataCredito(boletoEntidade.getDataCredito());
			pagamento.setDataPagamento(boletoEntidade.getDataPagamento());
			pagamento.setNossoNumero(boletoEntidade.getNossoNumero());
			pagamento.setNumeroDocumento(boletoEntidade.getNumeroDocumento());
			pagamento.setValorTitulo(boletoEntidade.getValorBoleto() != null ? boletoEntidade.getValorBoleto().doubleValue() : null);
			pagamento.setValorPago(boletoEntidade.getValorCredito() != null ? boletoEntidade.getValorCredito().doubleValue() : null);
			// pagamento.setMotivoOcorrencia(boletoEntidade.getMotivoOcorrenciaArquivoRetorno());
			//pagamento.setCodigoMovimentoArquivoRetorno(boletoEntidade.getCodigoMovimentoArquivoRetorno());
			pagamento.setNumeroAutenticacao(boletoEntidade.getNumeroAutenticacao());
			
			listaPagamentos.add(pagamento);
		}
		return listaPagamentos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.infosolo.cobranca.negocio.ejb.PagamentosNegocioLocal#
	 * confirmarLeituraPagamento
	 * (br.com.infosolo.cobranca.servicos.ejb.dto.BoletoDTO)
	 */
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void confirmarLeituraPagamento(List<PagamentoDTO> pagamentos) {
		for (PagamentoDTO pagamentoDTO : pagamentos) {
			logger.info("-> Confirmando pagamento para nosso numero " + pagamentoDTO.getNossoNumero() + " e autenticacao " + pagamentoDTO.getNumeroAutenticacao());
			
			Query query = em.createNamedQuery("boleto.retornarBoletoPorNossoNumeroAutenticacao");
			query.setParameter("nossoNumero", pagamentoDTO.getNossoNumero());
			query.setParameter("numeroAutenticacao", pagamentoDTO.getNumeroAutenticacao());
			
			BoletoEntidade boletoEntidade = (BoletoEntidade) query.getSingleResult();
			boletoEntidade.setIcPagamentoComputado(Constantes.IC_SIM);
			boletoEntidade.setDataPagamentoComputado(new Date());
			em.persist(boletoEntidade);
		}
		
		em.flush();
		
		logger.info("Informando que o pagamento foi lido!");
	}
	
	
	/**
	 * Retorna o somatorio do total de pagamentos creditados na data informada para o convenio informado.
	 * @param cedente
	 * @param dataCredito
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BoletoEntidadeLite> retornarPagamentosEfetuados(CedenteDTO cedente) {
		ArrayList<BoletoEntidadeLite> listaResultado = null;
		
		CedenteEntidadePK cedenteID = new CedenteEntidadePK();
		cedenteID.setCodigoConvenio(cedente.getCodigoConvenio());
		cedenteID.setNumeroCpfCnpjCedente(cedente.getCpfCnpjCedente());
		CedenteEntidade cedenteEntidade = em.find(CedenteEntidade.class, cedenteID);

		Query query = em.createNamedQuery("retornarPagamentosEfetuadosDiario");
		query.setParameter("cedente", cedenteEntidade);
		query.setParameter("dataFinal", new Date());
		
		try {
			listaResultado = (ArrayList<BoletoEntidadeLite>) query.getResultList();
		}
		catch (NoResultException nre) {  
			nre.printStackTrace(); 
		} 

		return listaResultado;
	}
	
}
