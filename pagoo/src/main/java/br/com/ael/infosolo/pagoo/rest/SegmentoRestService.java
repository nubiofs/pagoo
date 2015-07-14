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
package br.com.ael.infosolo.pagoo.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import br.com.ael.infosolo.pagoo.data.SegmentoRepository;
import br.com.ael.infosolo.pagoo.model.Entidade;
import br.com.ael.infosolo.pagoo.model.Segmento;

/**
 * Endpoint rest for users.
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 01/07/2015
 *
 */
@Path("/segmentos")
@RequestScoped
public class SegmentoRestService {
    
    @Inject
    private Logger log;

    @Inject
    private SegmentoRepository segmentoRepository;
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Segmento lookupSegmentoById(@PathParam("id") long id) {
    	Segmento segmento = segmentoRepository.findById(id);
        if (segmento == null) {
        	log.info("Nao foi possivel encontrar segmento id: {}",id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        log.info("Retornando id: {}",id);
        return segmento;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Segmento> listAllEntidades() {
        return segmentoRepository.findAllOrderedByName();
    }
    
    @GET
    @Path("/usuario/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Segmento lookupSegmentoByIdUsuario(@PathParam("id") long id) {
    	Segmento segmento = segmentoRepository.findSegmentoByUsuarioId(id);

        if (segmento == null) {
        	log.info("Nao foi possivel encontrar segmento id do usuario de id: {}",id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        log.info("Retornando segmento do usuario de id: {}",id);
        return segmento;
    }    
}
