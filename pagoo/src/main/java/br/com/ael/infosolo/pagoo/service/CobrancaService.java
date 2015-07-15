package br.com.ael.infosolo.pagoo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import br.com.ael.infosolo.pagoo.data.TipoServicoRepository;
import br.com.ael.infosolo.pagoo.data.UsuarioRepository;
import br.com.ael.infosolo.pagoo.dto.CompraDTO;
import br.com.ael.infosolo.pagoo.dto.CompradorDTO;
import br.com.ael.infosolo.pagoo.dto.ServicoContratadoDTO;
import br.com.ael.infosolo.pagoo.model.Cobranca;
import br.com.ael.infosolo.pagoo.model.Comprador;
import br.com.ael.infosolo.pagoo.model.Entidade;
import br.com.ael.infosolo.pagoo.model.Evento;
import br.com.ael.infosolo.pagoo.model.Segmento;
import br.com.ael.infosolo.pagoo.model.Servico;
import br.com.ael.infosolo.pagoo.model.TipoServico;
import br.com.ael.infosolo.pagoo.model.Usuario;
import br.com.ael.infosolo.pagoo.qualifiers.LoggedUser;
import br.com.ael.infosolo.pagoo.util.PagooUtil;
import br.com.infosolo.cobranca.dominio.dto.BoletoDTO;
import br.com.infosolo.cobranca.dominio.dto.CedenteDTO;
import br.com.infosolo.cobranca.dominio.dto.EnderecoDTO;
import br.com.infosolo.cobranca.dominio.dto.SacadoDTO;
import br.com.infosolo.cobranca.negocio.ejb.CobrancaBancariaNegocioLocal;

/**
 * Classe com negócio referente à cobrança.
 * @author David Reis (davidfdr@gmail.com)
 * @since 10/07/2015
 *
 */
@Stateless
@Named
public class CobrancaService {
	
	
	@Inject
	@LoggedUser
	private Usuario loggedUser;
	
	@EJB
	private CobrancaBancariaNegocioLocal cobrancaBancariaNegocio;
	
    @Inject
    private EntityManager em;
	
	@Inject 
	private UsuarioRepository usuarioRepository;
	
	@Inject 
	private TipoServicoRepository tipoServicoRepository;
	
	
	@Inject
	private Logger logger;
		
	
	/**
	 * Finaliza a cobranca de uma compra. :)
	 * @param compra
	 * @return Cobranca
	 */
	public Cobranca finalizarCompra (CompraDTO compra){
		logger.info("Usuario Logado: {}",loggedUser.getEmail());
		Usuario usuario = usuarioRepository.findById(loggedUser.getId());
		Date dataCobranca = new Date();
	
		// obj comprador
		Comprador comprador = new Comprador();
		CompradorDTO compradorDTO = compra.getComprador();
		comprador.setCpgcnpj(compradorDTO.getCpfcnpj());
		comprador.setNome(compradorDTO.getNome());
		String jsonCompra = PagooUtil.toJsonString(compra);
		comprador.setJsonInfoSuplementares(jsonCompra);
		em.persist(comprador);
		
		//obj servico
		Servico servico = new Servico();
		ServicoContratadoDTO servicoContratadoDTO = compra.getServicoContratado();
		TipoServico tipoServico = tipoServicoRepository.findById(servicoContratadoDTO.getId());
		servico.setTipoServico(tipoServico);
		servico.setPlaca(PagooUtil.limparString(compradorDTO.getPlaca()));
		servico.setValor(tipoServico.getValor());
		servico.setValorRepasse(tipoServico.getValorRepasse());
		
		
		
		//obj evento (aquele que armazena evento da compra.
		Evento evento = new Evento();
		evento.setComprador(comprador);
		evento.setDataEvento(dataCobranca);
		evento.setUsuario(usuario);
		evento.setEntidade(usuario.getEntidade());
		evento.setValorTotal(servico.getValor()); // Como aqui a compra tem um servico somente passa-se somente o valor do item comprado
		evento.setValorTotalRepasse(servico.getValorRepasse()); // Como aqui a compra tem um servico somente passa-se somente o valor repasse do item comprado
		evento.getServicos().add(servico); // Adiciona somente 1 serviço.
		servico.setEvento(evento); // seta o evento no servico
		
		em.persist(evento);
		em.persist(servico);
		
		//obj cobranca
		Cobranca cobranca = new Cobranca();
		cobranca.setDataCobranca(new Date());
		
		// todo recuperar dias para vencimento (por enquanto 10 dias)
		Calendar c = Calendar.getInstance();
		c.setTime(dataCobranca);
		c.add(Calendar.DATE,10);
		cobranca.setDataVencimento(c.getTime());
		cobranca.setValor(evento.getValorTotal());
		cobranca.setValorRepasse(evento.getValorTotalRepasse());
		cobranca.getEventos().add(evento);
		
		em.persist(cobranca);
		
		evento.getCobrancas().add(cobranca);
		
		em.persist(evento);
		
		gerarGuiaBorderoBoleto(cobranca);
		
		// dummy
		return cobranca;
	}
	
	/**
	 * OBS Este método não está completo. Ainda é necessário acertar de onde vem a quantidade de dias para vencimento,
	 * os convênios que estao fixos, a geração de boletos duplos ou simples baseato em regra de negócio.
	 * Falta também verificar de onde virão as instruções bancárias.
	 * @param cobranca
	 */
	public void gerarGuiaBorderoBoleto(Cobranca cobranca){
		//ArrayList<BoletoDTO> boletos = new ArrayList<BoletoDTO>();
		
		Date dataVencimento = cobranca.getDataCobranca();
		Date dataCobranca = cobranca.getDataCobranca();
		Calendar c = Calendar.getInstance();
		c.setTime(dataCobranca);
		c.add(Calendar.DATE,5); // POR ENQUANTO FIXO OS DIAS PARA VENCIMENTO. TODO COLOCAR EM BANCO TABELA CONVÊNIO
		dataVencimento = c.getTime();
		
		EnderecoDTO endereco = new EnderecoDTO();
		endereco.setLogradouro("");
		endereco.setBairro("");
		endereco.setCidade("BRASILIA");
		endereco.setUf("DF");
		endereco.setCep("");
		
		SacadoDTO sacado = new SacadoDTO();
		
		Evento evento = cobranca.getEventos().iterator().next(); // Apesar das cobrancas serem várias, pega a primeira e mais recente.
		Comprador comprador = evento.getComprador();
		
		sacado.setCpfCnpjSacado(comprador.getCpgcnpj());
		sacado.setNome(comprador.getNome());
		sacado.getEnderecos().add(endereco);
		
		CedenteDTO cedente = new CedenteDTO();
		cedente.setCodigoConvenio(new Long("123456")); // TODO carregar tabela convenio
		cedente.setCpfCnpjCedente("10213834000139"); // TODO carregar tabela convenio
		
		CedenteDTO cedente2 = new CedenteDTO();
		cedente2.setCodigoConvenio(new Long("10112048")); //TODO carregar tabela convênio
		cedente2.setCpfCnpjCedente("17279056000120"); // todo carregar tabela convenio
		
		// Boleto arrecadacao relatorio
	    List<BoletoDTO> boletos = new ArrayList<BoletoDTO>();
				
	    BigDecimal valorBoletoEnt = evento.getValorTotal().subtract(evento.getValorTotalRepasse());
	    
		BoletoDTO boleto = new BoletoDTO();
		boleto.setDataVencimento(dataVencimento);
		boleto.setValorBoleto(valorBoletoEnt.doubleValue());
		boleto.setSacado(sacado);
		boleto.setCedente(cedente);
		boleto.setInstrucoesBancarias(retornarInstrucoesBancarias());
		boleto.setNumeroDocumento(cobranca.getId().toString());
		boleto.setDataEmissao(dataCobranca);
		
		BoletoDTO boleto2 = new BoletoDTO();
		boleto2.setDataVencimento(dataVencimento);
		boleto2.setValorBoleto(evento.getValorTotalRepasse().doubleValue());
		boleto2.setSacado(sacado);
		boleto2.setInstrucoesBancarias(retornarInstrucoesBancarias());
		boleto2.setCedente(cedente2);
		boleto2.setNumeroDocumento(cobranca.getId().toString());
		boleto2.setDataEmissao(dataCobranca);
		
	
		boletos.add(boleto);
		boletos.add(boleto2);
		
		
		BoletoDTO boletoDTO = cobrancaBancariaNegocio.gerarBoletoAvulso(cedente, sacado, dataVencimento, 100D, null, null, null, null, null,true);
		
		logger.info("Boleto gerado com sucesso para cedente: {} ",boletoDTO.getCedente().getCodigoConvenio());
		logger.info("Nosso numero: {}", boletoDTO.getNossoNumero());
		
        boletos = cobrancaBancariaNegocio.gerarBoletoArrecadacao(boletos);
        
        logger.info(" --------------------------------------------------------------------------------------------------- ");
        
		for (BoletoDTO boletoDTO2 : boletos) {
			logger.info("Boleto gerado com sucesso para cedente: {} ", boletoDTO2.getCedente().getCodigoConvenio());
			logger.info("Nosso numero: {}", boletoDTO2.getNossoNumero());
		}
		
		if(boletos.size() > 1) { // POSSUI DOIS BOLETOS ENTAO É CODIGO DE BARRAS DUPLO TODO GERACAO PARA DECIDIR SE É 1 OU 2 CÓDIGOS DE BARRAS AINDA DEVE SER IMPLEMEENTADA.
			String nossoNumero1 = boletos.get(0).getNossoNumero();
			String nossoNumero2 = boletos.get(1).getNossoNumero();
			cobranca.setNossoNumero(nossoNumero1);
			cobranca.setNossoNumero2(nossoNumero2);
		} else {
			String nossoNumero1 = boletos.get(0).getNossoNumero();
			cobranca.setNossoNumero(nossoNumero1);
		}
		em.persist(cobranca);
	}
	
	/**
	 * Método auxiliar para gerar as instrucoes bancarias.
	 * Retorna baseado no usuário logado.
	 * @return
	 */
	private String retornarInstrucoesBancarias(){
		loggedUser = em.find(Usuario.class, loggedUser.getId());
		Entidade entidade = loggedUser.getEntidade();
		Segmento segmento = entidade.getSegmento();
		
		
		StringBuilder instrucoes = new StringBuilder();
		instrucoes.append("Pagável em qualquer agência do Banco do Brasil SA").append("\n");
		instrucoes.append(entidade.getNome()).append("\n");
		instrucoes.append(segmento.getNome()).append("\n");
		instrucoes.append(segmento.getDescricao()).append("\n");
		instrucoes.append("Infosolo LTDA - www.infosolo.com.br");
		

		return instrucoes.toString();
	}

}
