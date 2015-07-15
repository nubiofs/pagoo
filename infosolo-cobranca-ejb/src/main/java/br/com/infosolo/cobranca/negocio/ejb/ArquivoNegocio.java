package br.com.infosolo.cobranca.negocio.ejb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.infosolo.cobranca.dominio.arquivo.ArquivoRetorno;
import br.com.infosolo.cobranca.dominio.arquivo.ArquivoRetorno150;
import br.com.infosolo.cobranca.dominio.arquivo.ArquivoRetorno240;
import br.com.infosolo.cobranca.dominio.arquivo.LoteArquivo;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalhe;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheG150;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheSegmentoT;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheSegmentoU;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.Cedente;
import br.com.infosolo.cobranca.dominio.entidades.ArquivoRemessaEntidade;
import br.com.infosolo.cobranca.dominio.entidades.ArquivoRetornoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidadeLite;
import br.com.infosolo.cobranca.dominio.entidades.BoletoRetornoErroEntidade;
import br.com.infosolo.cobranca.dominio.entidades.CedenteEntidade;
import br.com.infosolo.cobranca.dominio.entidades.CobrancaEntidade;
import br.com.infosolo.cobranca.dominio.entidades.LeiauteArquivoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.RetornoDetalheEntidade;
import br.com.infosolo.cobranca.enumeracao.Banco;
import br.com.infosolo.cobranca.enumeracao.TipoArquivo;
import br.com.infosolo.cobranca.enumeracao.TipoOcorrencia;
import br.com.infosolo.cobranca.negocio.BancoNegocio;
import br.com.infosolo.cobranca.util.Constantes;
import br.com.infosolo.cobranca.util.DominioUtil;
import br.com.infosolo.comum.util.ArquivoUtil;
import br.com.infosolo.comum.util.DataUtil;
import br.com.infosolo.comum.util.Logger;

/**
 * Session Bean implementation class ArquivoNegocio
 */
@Stateless
public class ArquivoNegocio implements ArquivoNegocioLocal {
	
	private static Logger logger = new Logger(ArquivoNegocio.class);

	@PersistenceContext(unitName = "infosolo-cobraca")
	private EntityManager em;

	@Override
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public List<Boleto> gerarArquivoRemessa(CobrancaEntidade cobrancaEntidade, CedenteEntidade cedenteEntidade,
			List<BoletoEntidade> boletosEntidade, ArquivoRemessaEntidade arquivoEntidade) {
		
		Banco banco = DominioUtil.retornarDominioBanco(cedenteEntidade.getContaBancaria().getBanco());
		BancoNegocio bancoNegocio = BancoNegocio.getInstance(banco);
		
		String nomeArquivo =  bancoNegocio.calcularNomeArquivoRemessa(cedenteEntidade.getId().getCodigoConvenio());
		logger.info("Gerando arquivo de remessa: " + nomeArquivo);
		
		Cedente cedente = DominioUtil.retornarDominioCedente(cedenteEntidade);
		ArrayList<Boleto> boletos = (ArrayList<Boleto>) DominioUtil.retornarDominioListaBoleto(boletosEntidade);
		
		// Gera o arquivo de remessa
		LeiauteArquivoEntidade leiauteArquivoEntidade = em.find(LeiauteArquivoEntidade.class, new Long(TipoArquivo.CNAB240.getCodigo()));
		br.com.infosolo.cobranca.negocio.ArquivoNegocio.gerarArquivoRemessa(cedente, boletos, nomeArquivo, TipoArquivo.CNAB240);
		
		// Persiste o arquivo de remessa no banco
		File arquivo = new File(nomeArquivo);
		byte[] byteArray = null;
		
		try {
			byteArray = ArquivoUtil.loadBytesFromStream(new FileInputStream(arquivo));
			arquivoEntidade.setArquivoFisico(byteArray);
			arquivoEntidade.setLeiauteArquivo(leiauteArquivoEntidade);
			arquivoEntidade.setDataArquivo(new Date());
			arquivoEntidade.setNomeArquivoRemessa(arquivo.getName());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return boletos;
	}

	@SuppressWarnings("unchecked")
	private List<BoletoEntidadeLite> retornarListaBoletoEntidade(String nossoNumero) {
		List<BoletoEntidadeLite> listaBoletoEntidadeLite = new ArrayList<BoletoEntidadeLite>();
		
		Query query = em.createNamedQuery("boleto.retornarBoletoLitePorNossoNumero");
		query.setParameter("nossoNumero", nossoNumero.trim());
		
		try {
			listaBoletoEntidadeLite = (ArrayList<BoletoEntidadeLite>) query.getResultList();
		}
		catch (NoResultException nre) {
			logger.error("Nosso numero de Boleto nao encontrado na base.");
		} 
		return listaBoletoEntidadeLite;
	}

	private BoletoRetornoErroEntidade retornarBoletoRetornoErroEntidade(String nossoNumero) {
		BoletoRetornoErroEntidade boletoRetornoErro = null;
		
		Query query = em.createNamedQuery("boletoRetornoErro.retornarBoletoRetornoErroPorNossoNumero");
		query.setParameter("nossoNumero", nossoNumero.trim());
		
		try {
			boletoRetornoErro = (BoletoRetornoErroEntidade) query.getSingleResult();
		}
		catch (NoResultException nre) {
			boletoRetornoErro = null;
			//logger.error("Nosso numero de Boleto nao encontrado na base.");
		} 
		return boletoRetornoErro;
	}

	/**
	 * Processa os arquivos de retornos se existir retornando uma lista de entidade de arquivos
	 * e atualiza a lista de entidades de boletos.
	 * @param listaBoletosEntidade
	 * @return
	 */
	@Override
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public List<ArquivoRetornoEntidade> processarArquivosRetorno(List<BoletoEntidadeLite> listaBoletosEntidade,
			List<BoletoRetornoErroEntidade> listaBoletosRetornoErro) {
		
		ArrayList<ArquivoRetornoEntidade> arquivosRetorno = new ArrayList<ArquivoRetornoEntidade>();
		
		ArrayList<ArquivoRetorno> retornos = br.com.infosolo.cobranca.negocio.ArquivoNegocio.processarArquivosRetorno();
		
		// Limpa o vetor
		listaBoletosEntidade.clear();
		listaBoletosRetornoErro.clear();
		
		for (ArquivoRetorno arquivoRetorno : retornos) {
			if (arquivoRetorno == null) {
				logger.error("Nao retornou o nome do arquivo de retorno. Provavelmente formato invalido de arquivo.");
				continue;
			}
			
			File arquivo = new File(arquivoRetorno.getNomeArquivo());
			byte[] byteArray = null;
			FileInputStream inputStream = null;
			
			logger.info("Arquivo: " + arquivoRetorno.getNomeArquivo());
			
			String caminhoArquivo = arquivo.getPath();
			caminhoArquivo = caminhoArquivo.replace('\\', '/');

			Query query = em.createNamedQuery("arquivoRetorno.retornarArquivoRetornoPorNomeArquivo");
			query.setParameter("nomeArquivoRetorno", caminhoArquivo);
			
			ArquivoRetornoEntidade arquivoRetornoEntidade = null;

			try {
				arquivoRetornoEntidade = (ArquivoRetornoEntidade) query.getSingleResult();
			}
			catch (NoResultException nre) {  
				arquivoRetornoEntidade = new ArquivoRetornoEntidade();
			} 
			
			arquivoRetornoEntidade.setNomeArquivoRetorno(caminhoArquivo);
			arquivoRetornoEntidade.setDataArquivo(arquivoRetorno.getDataArquivo());
			
			LeiauteArquivoEntidade leiauteArquivoEntidade = em.find(LeiauteArquivoEntidade.class, new Long(arquivoRetorno.getTipoArquivo().getCodigo()));
			arquivoRetornoEntidade.setLeiauteArquivo(leiauteArquivoEntidade);

			try {
				inputStream = new FileInputStream(arquivo);

				byteArray = ArquivoUtil.loadBytesFromStream(inputStream);
				arquivoRetornoEntidade.setArquivoFisico(byteArray);
				inputStream.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}

			RetornoDetalheEntidade retornoDetalheEntidade = null;
			BoletoEntidadeLite boletoEntidadeLite = null;
			
			if (arquivoRetorno.getTipoArquivo() == TipoArquivo.CNAB240) {
				ArquivoRetorno240 arquivoRetorno240 = (ArquivoRetorno240) arquivoRetorno;

				// Processa os detalhes do retorno
				for (LoteArquivo loteArquivo : arquivoRetorno240.getLotes()) {
					for (int i = 0; i < loteArquivo.getRegistros().size(); i++) {
						RegistroDetalhe registro = loteArquivo.getRegistros().get(i);
						String linha = loteArquivo.getLinhas().get(i);
						
						if (RegistroDetalheSegmentoT.class.isInstance(registro)) {
							RegistroDetalheSegmentoT reg = (RegistroDetalheSegmentoT) registro;
	
							String nossoNumero = reg.nossoNumero.trim();
							logger.info("Processando nosso numero: " + nossoNumero);
							logger.info("-> Registro n.: " + reg.numeroRegistro);
							
							List<BoletoEntidadeLite> listaBoletos = retornarListaBoletoEntidade(nossoNumero);
							boletoEntidadeLite = listaBoletos.get(0);
							
							retornoDetalheEntidade = new RetornoDetalheEntidade();
							retornoDetalheEntidade.setArquivoRetorno(arquivoRetornoEntidade);
							retornoDetalheEntidade.setNumeroLote(new BigDecimal(registro.lote));
							retornoDetalheEntidade.setNumeroRegistro(new BigDecimal(registro.numeroRegistro));
							retornoDetalheEntidade.setCodigoMovimento(new Integer(registro.codigoMovimento));
							retornoDetalheEntidade.setMotivoOcorrencia(reg.motivoOcorrencia);
							retornoDetalheEntidade.setLinhaSeguimentoT(linha);
							retornoDetalheEntidade.setBoleto(boletoEntidadeLite);
							
							logger.info("-> Cod. Movimento(T): " + registro.codigoMovimento);
							
							if (registro.codigoMovimento == TipoOcorrencia.RETORNO_REGISTRO_CONFIRMADO.getCodigo()) {
								retornoDetalheEntidade.setValorTarifaBoleto(new BigDecimal(reg.valorTarifa));
							}
							else if (registro.codigoMovimento == TipoOcorrencia.RETORNO_LIQUIDACAO_TITULO_NAO_REGISTRADO.getCodigo()) {
								logger.info("Liquidação após baixa ou liquidação título não registrado.");
							}
						}
						else {
							RegistroDetalheSegmentoU reg = (RegistroDetalheSegmentoU) registro;
							logger.info("-> Registro n.: " + reg.numeroRegistro);
	
							retornoDetalheEntidade.setDataOcorrencia(DataUtil.formatarDataArquivoStr(reg.dataOcorrencia));
							retornoDetalheEntidade.setDataCredito(DataUtil.formatarDataArquivoStr(reg.dataCredito));
							retornoDetalheEntidade.setLinhaSeguimentoU(linha);
	
							logger.info("-> Cod. Movimento(U): " + registro.codigoMovimento);
	
							if (boletoEntidadeLite != null && (
									registro.codigoMovimento == TipoOcorrencia.RETORNO_LIQUIDADO.getCodigo() || 
									registro.codigoMovimento == TipoOcorrencia.RETORNO_LIQUIDACAO_TITULO_NAO_REGISTRADO.getCodigo())) {
								
								logger.info("-> Adicionndo boleto para liquidação.");
								
								boletoEntidadeLite.setDataPagamento(retornoDetalheEntidade.getDataOcorrencia());
								boletoEntidadeLite.setDataCredito(retornoDetalheEntidade.getDataCredito());
								boletoEntidadeLite.setValorAbatimento(new BigDecimal(reg.valorAbatimento));
								boletoEntidadeLite.setValorDesconto(new BigDecimal(reg.valorDesconto));
								boletoEntidadeLite.setValorJurosMora(new BigDecimal(reg.valorAcrescimo));
								boletoEntidadeLite.setValorIof(new BigDecimal(reg.valorIOF));
								boletoEntidadeLite.setValorCredito(new BigDecimal(reg.valorPago));
								boletoEntidadeLite.setIcPagamentoComputado(Constantes.IC_NAO);
								boletoEntidadeLite.setDataPagamentoComputado(null);
								
								listaBoletosEntidade.add(boletoEntidadeLite);
							}
							else {
								logger.info("-> Não foi processado dados de liquidação.");
							}

							if (arquivoRetornoEntidade.getRetornoDetalhes() == null)
								arquivoRetornoEntidade.setRetornoDetalhes(new ArrayList<RetornoDetalheEntidade>());
							
							arquivoRetornoEntidade.getRetornoDetalhes().add(retornoDetalheEntidade);
						}
					}
				}
			}
			else if (arquivoRetorno.getTipoArquivo() == TipoArquivo.FEBRABAN150) {
				ArquivoRetorno150 arquivoRetorno150 = (ArquivoRetorno150) arquivoRetorno;

				// Processa os detalhes do retorno
				for (int i = 0; i < arquivoRetorno150.getRegistros().size(); i++) {
					RegistroDetalheG150 registro = arquivoRetorno150.getRegistros().get(i);
					String linha = arquivoRetorno150.getLinhas().get(i);
					
					String nossoNumero = linha.substring(60, 78);
					
					if (nossoNumero.substring(0, 4).equals(nossoNumero.substring(4, 8))) {
						nossoNumero = nossoNumero.substring(4);
					}

					logger.info("+--------------------------------------------------------------------------------------------------------+");
					logger.info("-> Registro n. : " + registro.numeroSequencial);
					logger.info(" - Nosso Numero: " + nossoNumero);
					logger.info(" - Autenticacao: " + registro.numeroAutenticacao);
					
					//Query query = em.createNamedQuery("retornarBoletoLitePorNossoNumero");
					//query.setParameter("nossoNumero", nossoNumero.trim());
					
					List<BoletoEntidadeLite> listaBoletos = retornarListaBoletoEntidade(nossoNumero);
					boletoEntidadeLite = null;
					
					if (listaBoletos.size() > 1) {
						// Se existir mais de um boleto para o mesmo nosso numero (pagamento duplicado) 
						// entao ele retorna o boleto cujo bater a autenticacao.
						for (BoletoEntidadeLite boleto : listaBoletos) {
							if (boleto.getNumeroAutenticacao() != null &&
									boleto.getNumeroAutenticacao().equals(registro.numeroAutenticacao)) {
								boletoEntidadeLite = boleto;
								break;
							}
						}
					}
					else if (listaBoletos.size() == 1) {
						boletoEntidadeLite = listaBoletos.get(0);
						if (boletoEntidadeLite.getNumeroAutenticacao() != null &&
								!boletoEntidadeLite.getNumeroAutenticacao().equals(registro.numeroAutenticacao)) {
							// Se o numero de autenticacao for diferente entao duplica pois veio pagamento duplicado
							BoletoEntidadeLite novoBoletoEntidadeLite = new BoletoEntidadeLite();
							novoBoletoEntidadeLite.setNossoNumero(boletoEntidadeLite.getNossoNumero());
							novoBoletoEntidadeLite.setCedente(boletoEntidadeLite.getCedente());
							novoBoletoEntidadeLite.setSacado(boletoEntidadeLite.getSacado());
							novoBoletoEntidadeLite.setValorBoleto(boletoEntidadeLite.getValorBoleto());
							novoBoletoEntidadeLite.setNumeroDocumento(boletoEntidadeLite.getNumeroDocumento());
							novoBoletoEntidadeLite.setValorCredito(boletoEntidadeLite.getValorCredito());
							novoBoletoEntidadeLite.setDataPagamento(boletoEntidadeLite.getDataPagamento());
							novoBoletoEntidadeLite.setDataCredito(boletoEntidadeLite.getDataCredito());
							novoBoletoEntidadeLite.setNumeroAutenticacao(boletoEntidadeLite.getNumeroAutenticacao());
							novoBoletoEntidadeLite.setDataProcessamento(new Date());
							em.persist(novoBoletoEntidadeLite);
							
							boletoEntidadeLite = novoBoletoEntidadeLite;
						}
					}
					
					retornoDetalheEntidade = new RetornoDetalheEntidade();
					retornoDetalheEntidade.setDataOcorrencia(DataUtil.formatarDataArquivoStr2(registro.dataPagamento));
					retornoDetalheEntidade.setDataCredito(DataUtil.formatarDataArquivoStr2(registro.dataCredito));
					retornoDetalheEntidade.setArquivoRetorno(arquivoRetornoEntidade);
					retornoDetalheEntidade.setNumeroRegistro(new BigDecimal(registro.numeroSequencial));
					retornoDetalheEntidade.setMotivoOcorrencia(String.valueOf(registro.formaPagamento) + registro.formaArrecadacao);
					retornoDetalheEntidade.setLinhaSeguimentoT(linha);
					retornoDetalheEntidade.setBoleto(boletoEntidadeLite);
					retornoDetalheEntidade.setValorTarifaBoleto(new BigDecimal(registro.valorTarifa));
					
					// Como nao existe codigos de movimentos para FEBRABAN 150 adotamos 1 como padrao.
					retornoDetalheEntidade.setCodigoMovimento(1);
					
					if (boletoEntidadeLite != null) {
						boletoEntidadeLite.setDataPagamento(retornoDetalheEntidade.getDataOcorrencia());
						boletoEntidadeLite.setNumeroAutenticacao(registro.numeroAutenticacao);
						
						if (retornoDetalheEntidade.getDataCredito() != null) {
							boletoEntidadeLite.setDataCredito(retornoDetalheEntidade.getDataCredito());
							boletoEntidadeLite.setValorCredito(new BigDecimal(registro.valorRecebido));
						}
						
						boletoEntidadeLite.setIcPagamentoComputado(Constantes.IC_NAO); 
						boletoEntidadeLite.setDataPagamentoComputado(null);
						
						listaBoletosEntidade.add(boletoEntidadeLite);
					}
					else {
						// Gera um registro de boleto de retorno com erro
						BoletoRetornoErroEntidade boletoRetornoErro = retornarBoletoRetornoErroEntidade(nossoNumero);
						
						if (boletoRetornoErro == null) {
							boletoRetornoErro = new BoletoRetornoErroEntidade();
							boletoRetornoErro.setNossoNumero(nossoNumero);
						}
						
						boletoRetornoErro.setDataPagamento(retornoDetalheEntidade.getDataOcorrencia());
						boletoRetornoErro.setNumeroAutenticacao(registro.numeroAutenticacao);
						
						if (retornoDetalheEntidade.getDataCredito() != null) {
							boletoRetornoErro.setDataCredito(retornoDetalheEntidade.getDataCredito());
							boletoRetornoErro.setValorCredito(new BigDecimal(registro.valorRecebido));
						}
						
						em.persist(boletoRetornoErro);
						
						listaBoletosRetornoErro.add(boletoRetornoErro);
					}
					
					if (arquivoRetornoEntidade.getRetornoDetalhes() == null)
						arquivoRetornoEntidade.setRetornoDetalhes(new ArrayList<RetornoDetalheEntidade>());
					
					arquivoRetornoEntidade.getRetornoDetalhes().add(retornoDetalheEntidade);
				}
			}
			
			arquivosRetorno.add(arquivoRetornoEntidade);
		}
		
		return arquivosRetorno;
	}

	/**
	 * Marca os arquivos informados como processados.
	 * Nesta rotina sera escolhido o que fazer com estes arquivos, como apaga-los, move-los para outro
	 * local ou renomea-los.
	 * @param listaArquivosRetorno
	 */
	public void marcarArquivosProcessados(List<ArquivoRetornoEntidade> listaArquivosRetorno) {
		for (ArquivoRetornoEntidade arquivoRetornoEntidade : listaArquivosRetorno) {
			br.com.infosolo.cobranca.negocio.ArquivoNegocio.moverArquivoPastaProcessados(arquivoRetornoEntidade.getNomeArquivoRetorno());			
		}
	}

}
