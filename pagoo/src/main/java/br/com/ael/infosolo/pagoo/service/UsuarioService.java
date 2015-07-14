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
package br.com.ael.infosolo.pagoo.service;



import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import br.com.ael.infosolo.pagoo.data.UsuarioRepository;
import br.com.ael.infosolo.pagoo.model.Usuario;

/**
 * Business rules for users.
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 01/07/2015
 *
 */
@Stateless
@Named
public class UsuarioService {


    @Inject
    private EntityManager em;
    
    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private Event<Usuario> userEventSrc;

    public void register(Usuario user) throws Exception {
        em.persist(user);
        userEventSrc.fire(user);
    }
    
    /**
     * Wrapper para indicar o método de autenticação.
     * @param email
     * @param senha
     * @return
     * @throws Exception
     */
    public Usuario login(String email, String senha) throws Exception {
    	Usuario u = usuarioRepository.findByEmailAndPassword(email, senha);
    	return u;
    }
}
