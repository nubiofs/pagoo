package br.com.ael.infosolo.pagoo.data;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.util.StringUtils;

import br.com.ael.infosolo.pagoo.dto.ConsultaDTO;
import br.com.ael.infosolo.pagoo.model.Servico;


/**
 * Classe helper para repositório de cobranca
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 12/07/2015
 *
 */
final public class CobrancaRepositoryHelper {
	
	/**
	 * Retorna a query como string baseado nos parâmetros de consulta do DTO.
	 * @param consulta
	 * @return String com a query
	 */
	public static Query buildQueryFindCobrancaByConsultaDTO(ConsultaDTO consulta, Long entidadeID, EntityManager em){
		Long idEntidade = entidadeID;//ok
		boolean filtroIdentidade = idEntidade != null;//ok
		String nomeCompador = consulta!= null &&  consulta.getComprador() != null ? consulta.getComprador().getNome() : null;
		boolean filtroNomeComprador = !StringUtils.isEmpty(nomeCompador);
		String cpfCnpj = consulta!= null && consulta.getComprador() != null ? consulta.getComprador().getCpfcnpj() : null;
		boolean filtroCpfCnpjComprador = !StringUtils.isEmpty(cpfCnpj);
		String placa = consulta!= null && consulta.getComprador() != null ? consulta.getComprador().getPlaca() : null;
		boolean filtroPlaca = !StringUtils.isEmpty(placa);
		Long idTipoServico = consulta!= null && consulta.getServicoContratado() != null ? consulta.getServicoContratado().getId() : null;
		boolean filtroTipoServico = idTipoServico != null;
		
		StringBuilder sb = new StringBuilder("select servico from Servico as servico \n");
		
		// configurando joins 
		
		if(filtroTipoServico){
			sb.append("join fetch servico.tipoServico tpsrv").append(" \n");	
		}
		
		sb.append("join fetch servico.evento as ev").append(" \n");
		
		if(filtroIdentidade){
			sb.append("join fetch ev.entidade as ent").append(" \n");
		}
		
		if(filtroNomeComprador || filtroCpfCnpjComprador){
			sb.append("join fetch ev.comprador as compr").append(" \n");	
		}
				
		sb.append("join fetch ev.cobrancas as cob").append(" \n");
		
		// configurando clausura where
		
		sb.append("where 0=0").append(" \n");
		
		if(filtroIdentidade){
			sb.append("and ent.id =").append(":idEntidade").append(" \n");
			
		}
		
		if(filtroNomeComprador){
			sb.append("and compr.nome like ").append(":nomeComprador").append("").append(" \n");
		}
		
		if(filtroCpfCnpjComprador){
			sb.append("and compr.cpgcnpj= ").append(":cpfcnpj").append("").append(" \n");	
		}
		
		if(filtroPlaca){
			sb.append("and servico.placa = ").append(":placa").append("").append(" \n");
		}
		
		if(filtroTipoServico){
			sb.append("and tpsrv.id =").append(":idTipoServico").append(" \n");
		}
		
		// CONFIGURANDO ORDER BY
		
		sb.append(" order by cob.dataCobranca desc");
		
		Query query = em.createQuery(sb.toString(),Servico.class);
		
		// configurando parametros
		
		if(filtroTipoServico){
			query.setParameter("idTipoServico", idTipoServico);
		}
		if(filtroPlaca){
			query.setParameter("placa", placa);
		}
		if(filtroCpfCnpjComprador){
			query.setParameter("cpfcnpj", cpfCnpj);
		}
		if(filtroNomeComprador){
			query.setParameter("nomeComprador", "%" + nomeCompador + "%");
		}
		if(filtroIdentidade){
			query.setParameter("idEntidade", idEntidade);
		}
		
		query.setMaxResults(100); // FIXA EM 100 RESULTADOS NA PAGINA
		
		return query;
	}

}
