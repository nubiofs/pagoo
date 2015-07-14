/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.ael.infosolo.pagoo.data;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.ael.infosolo.pagoo.model.Perfil;
import br.com.ael.infosolo.pagoo.model.Segmento;
import br.com.ael.infosolo.pagoo.model.TipoServico;

/**
 * Repositório de Serviços.
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 07/07/2015
 *
 */
@Named
@ApplicationScoped
public class TipoServicoRepository {
    
    @Inject
    private EntityManager em;

    /**
     * Retornar por ID.
     * @param id
     * @return
     */
    public TipoServico findById(Long id) {
        return em.find(TipoServico.class, id);
    }


    /**
     * Todos ordenados por nome
     * @param email
     * @param password
     * @return
     */
    public List<TipoServico> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TipoServico> criteria = cb.createQuery(TipoServico.class);
        Root<TipoServico> tipoServico = criteria.from(TipoServico.class);
        criteria.select(tipoServico).orderBy(cb.asc(tipoServico.get("nome")));
        return em.createQuery(criteria).getResultList();
    }
    
    /**
     * Retorna tipo de servicos pelo segmento.
     * @param id
     * @return
     */
    public List<TipoServico> findAllBySegmentoOrderedByName(long id) {
    	Segmento segmento = em.find(Segmento.class, id);
    	List<TipoServico> retorno = null;
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TipoServico> criteria = cb.createQuery(TipoServico.class);
		Root<TipoServico> tipoServico = criteria.from(TipoServico.class);
		predicates.add(cb.equal(tipoServico.get("segmento"), segmento));
		criteria.select(tipoServico).where(predicates.toArray(new Predicate[]{})).orderBy(cb.asc(tipoServico.get("nome")));
		retorno = em.createQuery(criteria).getResultList();
        return retorno;
    }    
}
