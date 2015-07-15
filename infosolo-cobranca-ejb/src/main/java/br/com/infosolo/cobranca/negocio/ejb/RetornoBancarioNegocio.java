package br.com.infosolo.cobranca.negocio.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.infosolo.cobranca.dominio.entidades.ArquivoRetornoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidadeLite;
import br.com.infosolo.cobranca.dominio.entidades.BoletoMovimentoRetornoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.BoletoRetornoErroEntidade;
import br.com.infosolo.cobranca.dominio.entidades.MovimentoRetornoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.MovimentoRetornoEntidadePK;
import br.com.infosolo.cobranca.dominio.entidades.RetornoDetalheEntidade;
import br.com.infosolo.comum.util.Logger;

/**
 * Session Bean implementation class RetornoBancarioNegocio
 */
@Stateless
public class RetornoBancarioNegocio implements RetornoBancarioNegocioLocal {
	private static final Logger logger = new Logger(RetornoBancarioNegocio.class);
	
	private static final String RETORNO_TIMER = "Retorno Timer";

	@PersistenceContext(unitName = "infosolo-cobraca")
	private EntityManager em;

	@EJB
	private ArquivoNegocioLocal arquivoNegocio;
	
	@Resource 
	private SessionContext ctx;
	
	/**
	 * Controla o inicio do agendamento devido ao bug do Jboss ja iniciar uma vez o metodo de agendamento.
	 */
	private static boolean timerStarted = false;

    /**
     * Default constructor. 
     */
    public RetornoBancarioNegocio() {
        
    }
    
	/**
	 * Processa os retornos bancarios.
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public ArrayList<BoletoEntidadeLite> processarRetornoBancario() {
		List<ArquivoRetornoEntidade> listaArquivosRetorno = null;
		
		ArrayList<BoletoEntidadeLite> listaBoletosEntidadeLite = new ArrayList<BoletoEntidadeLite>();
		ArrayList<BoletoRetornoErroEntidade> listaBoletosRetornoErro = new ArrayList<BoletoRetornoErroEntidade>();
		
		logger.info("***********************************************************************************************");
		logger.info("Processando retornos bancarios...");
		logger.info("***********************************************************************************************");

		listaArquivosRetorno = arquivoNegocio.processarArquivosRetorno(listaBoletosEntidadeLite, listaBoletosRetornoErro);
		
		if (listaArquivosRetorno.size() > 0) {
			// Persiste os arquivo de retorno
			for (ArquivoRetornoEntidade arquivoEntidade : listaArquivosRetorno) {
				
				// Persiste arquivo de retorno
				logger.info("Persistindo entidade de arquivo de retorno: " + arquivoEntidade.getNomeArquivoRetorno());
				em.persist(arquivoEntidade);
				
				List<RetornoDetalheEntidade> listaRetornoDetalhe = (List<RetornoDetalheEntidade>) arquivoEntidade.getRetornoDetalhes();
				
				for (RetornoDetalheEntidade retornoDetalheEntidade : listaRetornoDetalhe) {
					
					// Persiste o codigo de movimento de retorno
					BoletoEntidadeLite boletoEntidadeLite = retornoDetalheEntidade.getBoleto();
					
					if (boletoEntidadeLite != null) {
						// Persiste retorno detalhe
						em.persist(retornoDetalheEntidade);

						MovimentoRetornoEntidadePK codigoMovimentoID = new MovimentoRetornoEntidadePK();
						codigoMovimentoID.setBanco(boletoEntidadeLite.getCedente().getContaBancaria().getBanco());
						codigoMovimentoID.setCodigoMovimento(retornoDetalheEntidade.getCodigoMovimento());
						
						MovimentoRetornoEntidade movimentoRetornoEntidade = em.find(MovimentoRetornoEntidade.class, codigoMovimentoID);
						
						if (movimentoRetornoEntidade != null) {
							Query query = em.createNamedQuery("retornarBoletoMovimentoRetornoPorBoleto");
							query.setParameter("boleto", boletoEntidadeLite);
							query.setParameter("codigoMovimentoRetorno", movimentoRetornoEntidade);
							
							ArrayList<BoletoMovimentoRetornoEntidade> listaResultado = null;
							
							try {
								listaResultado = (ArrayList<BoletoMovimentoRetornoEntidade>) query.getResultList();
							}
							catch (NoResultException nre) {  
								listaResultado = null;
								nre.printStackTrace(); 
							} 
							
							if (listaResultado == null || listaResultado.size() == 0) {
								logger.info("Persistindo movimento de retorno " + 
										movimentoRetornoEntidade.getId().getCodigoMovimento() + " para boleto " + boletoEntidadeLite.getNossoNumero());
								
								BoletoMovimentoRetornoEntidade boletoMovimentoRetornoEntidade = new BoletoMovimentoRetornoEntidade();
								boletoMovimentoRetornoEntidade.setBoleto(boletoEntidadeLite);
								boletoMovimentoRetornoEntidade.setCodigoMovimentoRetorno(movimentoRetornoEntidade);
								boletoMovimentoRetornoEntidade.setDataMovimento(arquivoEntidade.getDataArquivo());
								
								em.persist(boletoMovimentoRetornoEntidade);
							}
						}
					}
					else {
						logger.error("Boleto nao encontrado para registro de detalhe: " + retornoDetalheEntidade.getNumeroRegistro());
						logger.error("-> " + retornoDetalheEntidade.getLinhaSeguimentoT());
					}
				}
			}
			
			// Persiste boletos
			for (BoletoEntidadeLite boletoEntidadeLite : listaBoletosEntidadeLite) {
				logger.info("Boleto atualizado: " + boletoEntidadeLite.getIdBoleto());
				em.persist(boletoEntidadeLite);
			}

			// Persiste boletos erro
//			for (BoletoRetornoErroEntidade boletoRetornoErro : listaBoletosRetornoErro) {
//				logger.info("Boleto erro atualizado. Nosso Numero: " + boletoRetornoErro.getNossoNumero());
//				em.persist(boletoRetornoErro);
//			}

			logger.info("Fazendo o flush das alteracoes...");
			em.flush();
			
			// Se chegar ate este ponto entao vamos marcar os arquivos de retornos como processados
			logger.info("Marcando arquivos como processados...");
			arquivoNegocio.marcarArquivosProcessados(listaArquivosRetorno);
			
			logger.info("Terminado com sucesso.");
		}
		else {
			logger.info("Sem arquivos de retorno no momento para processamento.");
		}

		logger.info("***********************************************************************************************");
		logger.info("Finalizado o processando retornos bancarios.");
		logger.info("***********************************************************************************************");

		return listaBoletosEntidadeLite;
	}
	
	/**
	 * Inicia o agendamento para processamento dos arquivos de retorno.
	 */
	@Override
	public void iniciarAgendamento(long milisegundos) {
		cancelarAgendamento();
		
		logger.info("Iniciando agendamento de retorno bancario a cada " + milisegundos + " milisegundos.");
		
		TimerService timerService = ctx.getTimerService();
		timerService.createTimer(new Date(new Date().getTime() + milisegundos), milisegundos, RETORNO_TIMER);
		
		timerStarted = true;
	}

	
	/**
	 * Metodo agendado. Este método será invocado pelo timer do EJB a um intervalo de tempo especificado para executar
	 * determinada tarefa.
	 *  
	 * @param timer
	 */
	@Timeout
	public void processearRetornoTimedOut(Timer timer) {
		if (timerStarted) {
			logger.info("*--------------------------------------------------*");
			logger.info("* Recebido evento de tempo: [" + timer.getInfo() + "] *");
			logger.info("*--------------------------------------------------*");
			
			processarRetornoBancario();
		}
	}
	
	/**
	 * Cancela o agendamento.
	 */
	public void cancelarAgendamento() {
		try {
			TimerService ts = ctx.getTimerService();
			
			for (Object obj : ts.getTimers()) {
				Timer timer = (Timer) obj;
				if ((timer.getInfo().equals(RETORNO_TIMER))) {
					timer.cancel();
					logger.info("Agendamento cancelado com sucesso: " + RETORNO_TIMER);
					timerStarted = false;
				}
			}
		} catch (Exception e) {
			logger.error("Erro ao tentar cancelar agendamento: " + e.toString());
		}
	}

}
