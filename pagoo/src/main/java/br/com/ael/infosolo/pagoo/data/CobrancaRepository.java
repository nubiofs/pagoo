package br.com.ael.infosolo.pagoo.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;

import br.com.ael.infosolo.pagoo.dto.ConsultaDTO;
import br.com.ael.infosolo.pagoo.model.Cobranca;
import br.com.ael.infosolo.pagoo.model.Servico;

/**
 * Repositório para cobranca
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 12/07/2015
 *
 */
@Named
@ApplicationScoped
public class CobrancaRepository {
    @Inject
    private Logger log;
    
    @Inject
    private EntityManager em;

    
    public Cobranca findById(Long id) {
        return em.find(Cobranca.class, id);
    }

    /**
     * Retorna a lista de cobrancas.
     * @return
     */
    public List<Cobranca> findAllOrderedByDataCobranca() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cobranca> criteria = cb.createQuery(Cobranca.class);
        Root<Cobranca> cobranca = criteria.from(Cobranca.class);
        criteria.select(cobranca).orderBy(cb.asc(cobranca.get("dataCobranca")));
        log.info("Retornando todas as cobrancas");
        return em.createQuery(criteria).getResultList();
    }
    
    /**
     * Retorna a entidade associada a um usuário dado um ID
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Servico> findAllOrderedByDataCobranca(ConsultaDTO consulta, Long entidadeId) {
        Query query = CobrancaRepositoryHelper.buildQueryFindCobrancaByConsultaDTO(consulta, entidadeId, em);
        List<Servico> ret = (List<Servico>) query.getResultList();
        return ret; //em.createQuery(criteria).getResultList();
    }
}
