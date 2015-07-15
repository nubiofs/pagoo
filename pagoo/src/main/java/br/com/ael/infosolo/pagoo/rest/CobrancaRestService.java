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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
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
import br.com.infosolo.cobranca.dominio.dto.BoletoDTO;
import br.com.infosolo.cobranca.dominio.dto.CedenteDTO;
import br.com.infosolo.cobranca.dominio.dto.EnderecoDTO;
import br.com.infosolo.cobranca.dominio.dto.SacadoDTO;
import br.com.infosolo.cobranca.negocio.ejb.CobrancaBancariaNegocioLocal;
import br.com.infosolo.comum.util.DataUtil;

/**
 * Endpoint rest for para geração dos borderôs / cobrancas.
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 07/07/2015
 *
 */
@Path("/cobranca")
@RequestScoped
public class CobrancaRestService {
	
	
	@EJB
	private CobrancaBancariaNegocioLocal cobrancaBancariaNegocio;
	
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
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/gerarbordero")
    public Response gerarGuiaArrecadacaoBoletoBordero(CompraDTO compra){


		//ArrayList<BoletoDTO> boletos = new ArrayList<BoletoDTO>();
		
		Date dataVencimento = null;
		try {
			dataVencimento = DataUtil.getData("27/07/2015");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		EnderecoDTO endereco = new EnderecoDTO();
		endereco.setLogradouro("SQN 414 BL Z APTO 554");
		endereco.setBairro("ASA NORTE");
		endereco.setCidade("BRASILIA");
		endereco.setUf("DF");
		endereco.setCep("70000000");
		
		SacadoDTO sacado = new SacadoDTO();
		sacado.setCpfCnpjSacado("75342862000100");
		sacado.setNome("João Fulano da Silva");
		sacado.getEnderecos().add(endereco);
		
		CedenteDTO cedente = new CedenteDTO();
		cedente.setCodigoConvenio(new Long("123456"));
		cedente.setCpfCnpjCedente("10213834000139");
		
		CedenteDTO cedente2 = new CedenteDTO();
		cedente2.setCodigoConvenio(new Long("10112048"));
		cedente2.setCpfCnpjCedente("17279056000120");
		
		// Boleto arrecadacao relatorio
	    List<BoletoDTO> boletos = new ArrayList<BoletoDTO>();
				

		BoletoDTO boleto = new BoletoDTO();
		boleto.setDataVencimento(dataVencimento);
		boleto.setValorBoleto(1.14D);
		boleto.setSacado(sacado);
		boleto.setCedente(cedente);
		boleto.setInstrucoesBancarias("Pagável em qualquer agência do Banco do Brasil SA\nCRDD - Conselho Regional dos Despachantes - DF\nInfosolo LTDA\nwww.infosolo.com.br");
		boleto.setNumeroDocumento("27");
		boleto.setDataEmissao(new Date());
		
		BoletoDTO boleto2 = new BoletoDTO();
		boleto2.setDataVencimento(dataVencimento);
		boleto2.setValorBoleto(275D);
		boleto2.setSacado(sacado);
		boleto2.setInstrucoesBancarias("Pagável em qualquer agência do Banco do Brasil SA\n CRDD - Conselho Regional dos Despachantes - DF\nInfosolo LTDA\nwww.infosolo.com.br");
		boleto2.setCedente(cedente2);
		boleto2.setNumeroDocumento("27");
		boleto2.setDataEmissao(new Date());
		
	
		boletos.add(boleto);
		boletos.add(boleto2);
		

//		List<BoletoDTO> boletosResposta = cobrancaBancariaNegocio.registrarCobrancaBancaria(boletos);
//		for (BoletoDTO boletoResposta : boletosResposta) {
//			System.out.println("-> Número documento: " + boletoResposta.getNumeroDocumento());
//			System.out.println("-> Nosso numero: " + boletoResposta.getNossoNumero());
//		}
		
		BoletoDTO boletoDTO = cobrancaBancariaNegocio.gerarBoletoAvulso(cedente, sacado, dataVencimento, 100D, null, null, null, null, null,true);
		
		System.out.println("Boleto gerado com sucesso para cedente " + boletoDTO.getCedente().getCodigoConvenio());
		System.out.println("Nosso numero: " + boletoDTO.getNossoNumero());
		
        boletos = cobrancaBancariaNegocio.gerarBoletoArrecadacao(boletos);
        
        System.out.println(" --------------------------------------------------------------------------------------------------- ");
        
		for (BoletoDTO boletoDTO2 : boletos) {
			System.out.println("Boleto gerado com sucesso para cedente " + boletoDTO2.getCedente().getCodigoConvenio());
			System.out.println("Nosso numero: " + boletoDTO2.getNossoNumero());
		}
		Response.ResponseBuilder builder = Response.status(Response.Status.CREATED).entity(boletos);
    	return builder.build();
    }



}
