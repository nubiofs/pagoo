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

import org.slf4j.Logger;

import br.com.ael.infosolo.pagoo.model.Perfil;
import br.com.ael.infosolo.pagoo.model.Usuario;

/**
 * Repositório de Usuários.
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 01/07/2015
 *
 */
@Named
@ApplicationScoped
public class UsuarioRepository {
    @Inject
    private Logger log;
    
    @Inject
    private EntityManager em;

    public Usuario findById(Long id) {
        return em.find(Usuario.class, id);
    }

    /**
     * Encontrar por email
     * @param email
     * @return
     */
    public Usuario findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
        Root<Usuario> user = criteria.from(Usuario.class);
        criteria.select(user).where(cb.equal(user.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }
    
    /**
     * Encontrar por email e senha.
     * @param email
     * @param password
     * @return
     */
    public Usuario findByEmailAndPassword(String email, String password) {
    	Usuario u = null;
    	try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
			Root<Usuario> user = criteria.from(Usuario.class);
			predicates.add(cb.equal(user.get("email"), email));
			predicates.add(cb.equal(user.get("senha"), password));
			criteria.select(user).where(predicates.toArray(new Predicate[]{}));
			u = em.createQuery(criteria).getSingleResult();
			Perfil p = u.getPerfil(); // CONFIGURA PERFIL.
			u.setRole(p.getNome());
		} catch (Exception e) {
			log.info("Não foi possível encontrar usuario: " + e.toString());
		}
        return u;

        
    }

    /**
     * Todos ordenados por nome
     * @param email
     * @param password
     * @return
     */
    public List<Usuario> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
        Root<Usuario> user = criteria.from(Usuario.class);
        criteria.select(user).orderBy(cb.asc(user.get("nome")));
        return em.createQuery(criteria).getResultList();
    }
}
