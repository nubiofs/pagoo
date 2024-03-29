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
import javax.ws.rs.core.MediaType;

import br.com.ael.infosolo.pagoo.data.TipoServicoRepository;
import br.com.ael.infosolo.pagoo.model.TipoServico;

/**
 * Endpoint rest for tipo de servco.
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 07/07/2015
 *
 */
@Path("/tipoServico")
@RequestScoped
public class TipoServicoRestService {
    
    @Inject
    private TipoServicoRepository tipoServicoRepository;

   
    /**
     * Retorna todos os serviços.
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoServico> listAllTipoServicos() {
    	List<TipoServico> retorno = null;
    	retorno = tipoServicoRepository.findAllOrderedByName();
        return retorno;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/servicosDoSegmento/{id:[0-9][0-9]*}")
    public List<TipoServico> listAllTipoServicosBySegmento(@PathParam("id") long idSegmento) {
    	List<TipoServico> retorno = null;
    	retorno = tipoServicoRepository.findAllBySegmentoOrderedByName(idSegmento);
        return retorno;
    }

}
