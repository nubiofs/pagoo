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
package br.com.ael.infosolo.pagoo.producers;


import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.ael.infosolo.pagoo.model.Usuario;
import br.com.ael.infosolo.pagoo.qualifiers.LoggedUser;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans
 * 
 * <p>
 * Example injection on a managed bean field:
 * </p>
 * 
 * <pre>
 * &#064;Inject
 * private EntityManager em;
 * </pre>
 */
public class Resources {
	
	/**
	 * Produz o entity manager permitindo injeção através do @Inject
	 */
    @Produces
    @PersistenceContext
    private EntityManager em;


    /**
     * Produz o log da aplicação.
     * @param injectionPoint
     * @return
     */
    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
    
    /**
     * Producer para recuperar do contexto sprint o usuário logado.
     * @return
     */
    @Produces
    @LoggedUser
    public Usuario producePrincipalFromSpringSecurity(){
    	Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
    	Usuario user = (Usuario) auth.getDetails();
    	return user;
    }
}
