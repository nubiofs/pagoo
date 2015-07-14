package br.com.ael.infosolo.pagoo.service;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.xml.crypto.Data;

import org.slf4j.Logger;

import br.com.ael.infosolo.pagoo.data.TipoServicoRepository;
import br.com.ael.infosolo.pagoo.data.UsuarioRepository;
import br.com.ael.infosolo.pagoo.dto.CompraDTO;
import br.com.ael.infosolo.pagoo.dto.CompradorDTO;
import br.com.ael.infosolo.pagoo.dto.ServicoContratadoDTO;
import br.com.ael.infosolo.pagoo.model.Cobranca;
import br.com.ael.infosolo.pagoo.model.Comprador;
import br.com.ael.infosolo.pagoo.model.Evento;
import br.com.ael.infosolo.pagoo.model.Servico;
import br.com.ael.infosolo.pagoo.model.TipoServico;
import br.com.ael.infosolo.pagoo.model.Usuario;
import br.com.ael.infosolo.pagoo.qualifiers.LoggedUser;
import br.com.ael.infosolo.pagoo.util.PagooUtil;

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
		
		// dummy
		return cobranca;
	}

}
