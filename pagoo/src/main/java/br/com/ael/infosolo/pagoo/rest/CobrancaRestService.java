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

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import br.com.ael.infosolo.pagoo.data.CobrancaRepository;
import br.com.ael.infosolo.pagoo.dto.CompraDTO;
import br.com.ael.infosolo.pagoo.dto.CompradorDTO;
import br.com.ael.infosolo.pagoo.dto.ConsultaDTO;
import br.com.ael.infosolo.pagoo.dto.ServicoContratadoDTO;
import br.com.ael.infosolo.pagoo.model.Cobranca;
import br.com.ael.infosolo.pagoo.model.Servico;
import br.com.ael.infosolo.pagoo.service.CobrancaService;

/**
 * Endpoint rest for para geração dos borderôs / cobrancas.
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 07/07/2015
 *
 */
@Path("/cobranca")
@RequestScoped
public class CobrancaRestService {
	
	@Inject
	private Logger logger;
	
	@Inject
    private CobrancaService cobrancaService;
	
	@Inject 
	private CobrancaRepository cobrancaRepository;
    /**
     * Encapsula a chamada para criação do boleto / borderô / cobrança passando um serviço adquirido /utilizado e um comprador (sacado).
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response gerarBorderoCobrancaPagooGuiaBoleto(CompraDTO compra) {
    	Response.ResponseBuilder builder = null;
    	logger.info("Gerar Bordero Acionado!!!");
    	//
    	Cobranca cobranca = cobrancaService.finalizarCompra(compra);

        builder = Response.status(Response.Status.CREATED).entity(cobranca);
    	return builder.build();
    }
    
    /**
     * Serviço para retornar consulta de compras de uma determinada entidade.
     * @param compra
     * @param idEntidade
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/entidade/{identidade:[0-9][0-9]*}")
    public List<ConsultaDTO> consultarCobrancasDaEntidade(ConsultaDTO consulta, @PathParam("identidade") Long idEntidade) { 
    	List<Servico> servicos = cobrancaRepository.findAllOrderedByDataCobranca(consulta, idEntidade);
    	List<ConsultaDTO> res = null;
    	if(!CollectionUtils.isEmpty(servicos)){
    		res = new ArrayList<ConsultaDTO>(servicos.size());
    		for (Servico s : servicos) {
    			ConsultaDTO c = new ConsultaDTO();
    			CompradorDTO comp = new CompradorDTO();
    			comp.setNome(s.getEvento().getComprador().getNome());
    			comp.setCpfcnpj(s.getEvento().getComprador().getCpgcnpj());
    			comp.setPlaca(s.getPlaca());
    			ServicoContratadoDTO sc = new ServicoContratadoDTO();
    			sc.setDescricao(s.getTipoServico().getDescricao());
    			sc.setNome(s.getTipoServico().getNome());
    			sc.setValor(s.getValor());
    			sc.setId(s.getId());
    			sc.setDtCobranca(s.getEvento().getDataEvento());
    			c.setComprador(comp);
    			c.setServicoContratado(sc);
    			c.setIdEntidade(idEntidade);
    			res.add(c);
				
			}
    	}
    	// TODO - VERIFICAR O PORQUE NAO ESTA FUNCIONANDO ENVIAR LISTA DIRETO DE SERVICOS.
    	// NA HORA DE TRANSFORMAR EM JSON SOMENTE O PRIMEIRO DA LISTA EH FEITO.
    	// EM VIRTUDE DO HORARIO AVANÇADO DA NOITE E PRAZO IMPOSSÍVEL TERÁ QUE FICAR ASSIM MESMO.
    	
    	return res;
    }



}
