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

import br.com.ael.infosolo.pagoo.model.Entidade;
import br.com.ael.infosolo.pagoo.model.Segmento;
import br.com.ael.infosolo.pagoo.model.Usuario;

/**
 * Repositório para segmentos
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 12/07/2015
 *
 */
@Named
@ApplicationScoped
public class SegmentoRepository {
    @Inject
    private Logger log;
    
    @Inject
    private EntityManager em;

    
    public Segmento findById(Long id) {
        return em.find(Segmento.class, id);
    }

    /**
     * Retorna a lista de segmentos.
     * @return
     */
    public List<Segmento> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Segmento> criteria = cb.createQuery(Segmento.class);
        Root<Segmento> segmento = criteria.from(Segmento.class);
        criteria.select(segmento).orderBy(cb.asc(segmento.get("nome")));
        log.info("Retornando todos os segmtos");
        return em.createQuery(criteria).getResultList();
    }
    
    /**
     * Retorna a entidade associada a um usuário dado um ID
     * @return
     */
    public Segmento findSegmentoByUsuarioId(Long id) {
        Query query = em.createNamedQuery("Segmento.findByIdUsuario");
        query.setParameter("idusuario", id);
        Usuario usuario = (Usuario) query.getSingleResult();
        Entidade entidade = usuario.getEntidade();
        Segmento segmento = entidade.getSegmento();
        return segmento;
    } 
}
