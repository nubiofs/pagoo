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
import br.com.ael.infosolo.pagoo.model.Usuario;

/**
 * Repositório para entidades
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 12/07/2015
 *
 */
@Named
@ApplicationScoped
public class EntidadeRepository {
    @Inject
    private Logger log;
    
    @Inject
    private EntityManager em;

    
    public Entidade findById(Long id) {
        return em.find(Entidade.class, id);
    }

    /**
     * Retorna a lista de entidades.
     * @return
     */
    public List<Entidade> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entidade> criteria = cb.createQuery(Entidade.class);
        Root<Entidade> entidade = criteria.from(Entidade.class);
        criteria.select(entidade).orderBy(cb.asc(entidade.get("nome")));
        log.info("Retornando todas as entidades.");
        return em.createQuery(criteria).getResultList();
    }
    
    /**
     * Retorna a entidade associada a um usuário dado um ID
     * @return
     */
    public Entidade findEntidadeByUsuarioId(Long id) {
        Query query = em.createNamedQuery("Entidade.findByIdUsuario");
        query.setParameter("id", id);
        Usuario usuario = (Usuario) query.getSingleResult();
        Entidade entidade = usuario.getEntidade();
        return entidade;
    }    
}
