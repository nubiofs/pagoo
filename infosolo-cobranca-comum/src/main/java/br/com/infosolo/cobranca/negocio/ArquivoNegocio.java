package br.com.infosolo.cobranca.negocio;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import br.com.infosolo.cobranca.dominio.arquivo.ArquivoRetorno;
import br.com.infosolo.cobranca.dominio.arquivo.ArquivoRetorno150;
import br.com.infosolo.cobranca.dominio.arquivo.ArquivoRetorno240;
import br.com.infosolo.cobranca.dominio.arquivo.LoteArquivo;
import br.com.infosolo.cobranca.dominio.arquivo.Registro;
import br.com.infosolo.cobranca.dominio.arquivo.Registro150;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroCabecalhoArquivo;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroCabecalhoLote;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheA150;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheG150;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheSegmentoE;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheSegmentoT;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheSegmentoU;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheZ150;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroRodapeArquivo;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroRodapeLoteRemessa;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.Cedente;
import br.com.infosolo.cobranca.enumeracao.Banco;
import br.com.infosolo.cobranca.enumeracao.TipoArquivo;
import br.com.infosolo.cobranca.enumeracao.TipoMovimento;
import br.com.infosolo.cobranca.excecao.CobrancaExcecao;
import br.com.infosolo.cobranca.util.ArquivoFabrica;
import br.com.infosolo.cobranca.util.Constantes;
import br.com.infosolo.cobranca.util.ConstantesErros;
import br.com.infosolo.comum.util.ArquivoUtil;
import br.com.infosolo.comum.util.DataUtil;
import br.com.infosolo.comum.util.Logger;

public class ArquivoNegocio {
	private static Logger logger = new Logger(ArquivoNegocio.class);

	private static final String DIRETORIO_ARQUIVOS_REMESSA = "diretorio_arquivos_remessa";
	private static final String DIRETORIO_ARQUIVOS_RETORNO = "diretorio_arquivos_retorno";
	
	private static String diretorioArquivosRemessa = "";
	private static String diretorioArquivosRetorno = "";
	
	private static String SUBDIR_IN = "/in";
	private static String SUBDIR_OUT = "/out";
	
    static {
        Properties properties = new Properties();
		try {
			properties.load(ArquivoNegocio.class.getResourceAsStream(Constantes.COBRANCA_PROPERTIES));
			
			diretorioArquivosRemessa = properties.getProperty(DIRETORIO_ARQUIVOS_REMESSA);
			diretorioArquivosRetorno = properties.getProperty(DIRETORIO_ARQUIVOS_RETORNO);
			
			logger.info("Diretorio de arquivos de remessa: " + diretorioArquivosRemessa);
			logger.info("Diretorio de arquivos de retorno: " + diretorioArquivosRetorno);
		}
		catch (IOException e) {
			logger.error(ConstantesErros.ERROR_FILE_PROPERTIES + " [" + Constantes.COBRANCA_PROPERTIES + "]");
		}		
    }
    
    public static String getDiretorioArquivosRemessa() {
		return diretorioArquivosRemessa;
	}

	public static String getDiretorioArquivosRetorno() {
		return diretorioArquivosRetorno;
	}

	/**
	 * Gera o arquivo fisicao de remessa.
	 * @param cedente
	 * @param boletos
	 * @param nomeArquivo
	 * @param tipoArquivo
	 */
	public static void gerarArquivoRemessa(Cedente cedente, ArrayList<Boleto> boletos,
			String nomeArquivo, TipoArquivo tipoArquivo) {
		
		int numeroRegistro = 1;
		int numeroRegistroDetalhe = 1;
		
		// Assegura a criacao do diretorio
		ArquivoUtil.asseguraDiretorioExiste(nomeArquivo);	
		
		Banco banco = cedente.getContaBancaria().getBanco();
		BancoNegocio bancoNegocio = BancoNegocio.getInstance(banco);
		String linha;
		//TipoArquivo tipoArquivo = arquivoRemessa.getTipoArquivo();
		
		// Abre o arquivo
		DataOutputStream stream = ArquivoUtil.abrirArquivo(nomeArquivo, false);
		
		try {
			// Grava cabecalho de arquivo
			linha = bancoNegocio.retornarCabecalhoArquivoRemesa(cedente, tipoArquivo);
			ArquivoUtil.adicionarLinhaArquivo(stream, linha);
			numeroRegistro++;
			
			// Grava cabecalho de lote
			linha = bancoNegocio.retornarCabecalhoLoteRemesa(cedente, tipoArquivo);
			ArquivoUtil.adicionarLinhaArquivo(stream, linha);
			numeroRegistro++;
			
			// Adiciona os boletos
			for (Boleto boleto : boletos) {
				// Grava registro de detalhe segmento P
				linha = bancoNegocio.retornarDetalheSegmentoPRemessa(numeroRegistroDetalhe, cedente, boleto);
				ArquivoUtil.adicionarLinhaArquivo(stream, linha);
				numeroRegistroDetalhe++;
				numeroRegistro++;

				// Grava registro de detalhe segmento Q
				linha = bancoNegocio.retornarDetalheSegmentoQRemessa(numeroRegistroDetalhe, boleto);
				ArquivoUtil.adicionarLinhaArquivo(stream, linha);
				numeroRegistroDetalhe++;
				numeroRegistro++;
				
				// Grava registro de detalhe segmento R
				if (boleto.getValorMulta() > 0) {
					
				}
			}
			
			// Grava rodape de lote
			linha = bancoNegocio.retornarRodapeLoteRemesa(numeroRegistro - 1, tipoArquivo);
			ArquivoUtil.adicionarLinhaArquivo(stream, linha);
			numeroRegistro++;

			// Grava rodape de arquivo
			linha = bancoNegocio.retornarRodapeArquivoRemesa(numeroRegistro, tipoArquivo);
			ArquivoUtil.adicionarLinhaArquivo(stream, linha);
		}
		catch (Exception ex) {
			// Remove o arquivo
			if (ArquivoUtil.deletarArquivo(nomeArquivo))
				logger.info("Arquivo de remessa mal acabado excluido.");
			else
				logger.error("Nao consegui excluir arquivo de remessa mal acabado!");
		}
		finally {
			ArquivoUtil.fecharArquivo(stream);
		}
	}

	/**
	 * Obtem o nome de arquivo
	 * @param banco
	 * @param tipoArquivo
	 * @return
	 */
//	public static String obterNomeArquivoRemessa(Banco banco, TipoArquivo tipoArquivo) {
//		Date data = new Date();
//		return DataUtil.formatarDataArquivo(data) + DataUtil.formatarHoraArquivo(data) + 
//			"_" + banco.name() + "_" + tipoArquivo.name() + ".txt";
//	}
	
	/**
	 * Retorna o bean de arquivo de retorno do arquivo informado.
	 */
	@SuppressWarnings("rawtypes")
	public static ArquivoRetorno retornarArquivoRetorno(File arquivo) {
		ArquivoRetorno arquivoRetorno = null;
		TipoArquivo tipoArquivo = null; 
		LoteArquivo loteArquivo = new LoteArquivo();
		
		try {
			FileReader reader = new FileReader(arquivo);
			BufferedReader input =  new BufferedReader(reader);
			
			logger.info("*------------------------------------------------------------------------------------------------------------------------*");
			logger.info("| -> Processando arquivo de retorno: " + String.format("%-84s", arquivo.getPath()) + "|");
			
			try {
				String linha = null;
				int totalRegistros = 0;

				while (( linha = input.readLine()) != null) {
					if (arquivoRetorno == null) {
						if (linha.length() == 240) {
							tipoArquivo = TipoArquivo.CNAB240;
							arquivoRetorno = new ArquivoRetorno240();
						}
						else if (linha.length() == 150) {
							tipoArquivo = TipoArquivo.FEBRABAN150;
							arquivoRetorno = new ArquivoRetorno150();
						}
						else {
							throw new CobrancaExcecao("Formato de arquivo bancário inválido.");
						}
						
						//arquivoRetorno = new ArquivoRetorno(tipoArquivo);							
						arquivoRetorno.setNomeArquivo(arquivo.getPath());
					}
					
					Class classeRegistro = ArquivoFabrica.obterTipoRegistro(linha, tipoArquivo);
					totalRegistros++;
					
					if (tipoArquivo == TipoArquivo.CNAB240) {
						ArquivoRetorno240 arquivoRetorno240 = (ArquivoRetorno240) arquivoRetorno;
						arquivoRetorno240.setTipoArquivo(tipoArquivo);

						Registro registro = (Registro) ArquivoFabrica.gerarDominio(classeRegistro, linha);

						// Processa registro de cabecalho
						if (RegistroCabecalhoArquivo.class.isInstance(registro)) {
							RegistroCabecalhoArquivo reg = (RegistroCabecalhoArquivo) registro; 
							TipoMovimento tipoMovimento = TipoMovimento.findByValor(reg.codigoArquivo);
							Banco banco = Banco.findByCodigo(reg.banco);
							
							if (tipoMovimento != TipoMovimento.RETORNO) {
								throw new CobrancaExcecao("Arquivo não é do tipo de retorno.");
							}
							
							logger.info("| -> Data do arquivo: " + reg.dataGeracaoArquivo + " " + reg.horaGeracaoArquivo);
							
							arquivoRetorno240.setCabecalho(reg);
							arquivoRetorno240.setBanco(banco);
							arquivoRetorno240.setDataArquivo(DataUtil.formatarDataArquivoStr(reg.dataGeracaoArquivo));
						}
						// Processa registro de lote
						else if (RegistroCabecalhoLote.class.isInstance(registro)) {
							RegistroCabecalhoLote reg = (RegistroCabecalhoLote) registro; 
							
							logger.info("| -> Processando lote n. " + reg.lote);
							
							loteArquivo.setCabecalho(reg);
						}
						// Processa detalhe - Segmento T
						else if (RegistroDetalheSegmentoT.class.isInstance(registro)) {
							RegistroDetalheSegmentoT reg = (RegistroDetalheSegmentoT) registro; 
							
							logger.info("| ---> Registro segmento T: " + reg.numeroRegistro);
							
							loteArquivo.getRegistros().add(reg);
							loteArquivo.getLinhas().add(linha);
						}
						// Processa detalhe - Segmento U
						else if (RegistroDetalheSegmentoU.class.isInstance(registro)) {
							RegistroDetalheSegmentoU reg = (RegistroDetalheSegmentoU) registro; 
							
							logger.info("| ---> Registro segmento U: " + reg.numeroRegistro);
							
							loteArquivo.getRegistros().add(reg);
							loteArquivo.getLinhas().add(linha);
						}
						// Processa detalhe - Segmento E
						else if (RegistroDetalheSegmentoE.class.isInstance(registro)) {
							RegistroDetalheSegmentoE reg = (RegistroDetalheSegmentoE) registro; 
							
							logger.info("| ---> Registro segmento E: " + reg.numeroRegistro);
							logger.info("| -----> Data Lancamento: " + DataUtil.formatarDataArquivoStr(reg.dataLancamento));
							logger.info("| -----> Descricao Historico : " + reg.descricaoHistorico);
							
							loteArquivo.getRegistros().add(reg);
							loteArquivo.getLinhas().add(linha);
						}
						// Processa rodape de lote
						else if (RegistroRodapeLoteRemessa.class.isInstance(registro)) {
							RegistroRodapeLoteRemessa reg = (RegistroRodapeLoteRemessa) registro; 
							
							logger.info("| --> Terminando lote n. " + reg.lote + " - Quant. registros: " + reg.qtdeRegistros + " Total: " + (reg.valorSomatoria / 100));
							
							loteArquivo.setRodape(reg);
						}
						// Processa rodape de arquivo
						else if (RegistroRodapeArquivo.class.isInstance(registro)) {
							RegistroRodapeArquivo reg = (RegistroRodapeArquivo) registro; 
							
							logger.info("| -> Terminando processo de arquivo - Qtde Lotes: " + reg.qtdeLotes + " Quant registros: " + reg.qtdeRegistros);
							
							if (totalRegistros != reg.qtdeRegistros) {
								throw new CobrancaExcecao("Total de registros do arquivo inválido.");
							}
							
							arquivoRetorno240.setRodape(reg);
						}
					}
					else if (tipoArquivo == TipoArquivo.FEBRABAN150) {
						ArquivoRetorno150 arquivoRetorno150 = (ArquivoRetorno150) arquivoRetorno;
						arquivoRetorno150.setTipoArquivo(tipoArquivo);

						Registro150 registro = (Registro150) ArquivoFabrica.gerarDominio(classeRegistro, linha);

						// Processa registro de cabecalho
						if (RegistroDetalheA150.class.isInstance(registro)) {
							RegistroDetalheA150 reg = (RegistroDetalheA150) registro; 
							TipoMovimento tipoMovimento = TipoMovimento.findByValor(reg.codigoRemessa);
							Banco banco = Banco.findByCodigo(reg.codigoBanco);
							
							if (tipoMovimento != TipoMovimento.RETORNO) {
								throw new CobrancaExcecao("Arquivo não é do tipo de retorno.");
							}
							
							logger.info("-> Registro tipo A: " + reg.numeroSequencial);
							logger.info("->   Nome Empresa: " + reg.nomeEmpresa);
							logger.info("->   Codigo Banco: " + reg.codigoBanco);
							logger.info("->   Data do arquivo: " + reg.dataArquivo);
							
							arquivoRetorno150.setCabecalho(reg);
							arquivoRetorno150.setBanco(banco);
							arquivoRetorno150.setDataArquivo(DataUtil.formatarDataArquivoStr2(reg.dataArquivo));
						}
						// Processa detalhe - Registro G
						else if (RegistroDetalheG150.class.isInstance(registro)) {
							RegistroDetalheG150 reg = (RegistroDetalheG150) registro; 
							
							logger.info("-> Registro tipo G: " + reg.numeroSequencial);
							logger.info("->   Valor Recebido: " + reg.valorRecebido);
							logger.info("->   Data Pagamento: " + reg.dataPagamento);
							logger.info("->   Data Credito  : " + reg.dataCredito);
							logger.info("->   Atenticacao   : " + reg.numeroAutenticacao);
							
							arquivoRetorno150.getRegistros().add(reg);
							arquivoRetorno150.getLinhas().add(linha);
						}
						// Processa rodape de arquivo
						else if (RegistroDetalheZ150.class.isInstance(registro)) {
							RegistroDetalheZ150 reg = (RegistroDetalheZ150) registro; 
							
							logger.info("-> Registro tipo Z: ");
							logger.info("->   Total Registros: " + reg.totalRegistros);
							logger.info("->   Valor Total    : " + reg.valorTotal);
							
							logger.info("-> Terminando processo de arquivo.");
							
							if (totalRegistros != reg.totalRegistros) {
								throw new CobrancaExcecao("Total de registros do arquivo inválido.");
							}
							
							arquivoRetorno150.setRodape(reg);
						}
					}
			    }
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				logger.info("Fechando arquivo " +  arquivo.getPath());
				IOUtils.closeQuietly(input);

				reader.close();
				input.close();
			}
		}
		catch (IOException ex) {
			logger.error("Erro ao processar arquivo: " + ex.toString());
		}
		
		if (tipoArquivo == TipoArquivo.CNAB240) {
			ArquivoRetorno240 arquivoRetorno240 = (ArquivoRetorno240) arquivoRetorno;
			arquivoRetorno240.getLotes().add(loteArquivo);
		}
		
		return arquivoRetorno;
	}
	
	/**
	 * Processa os subdiretorios do diretorio de retorno.
	 * @param diretorio
	 * @return
	 */
	private static ArrayList<ArquivoRetorno> processarSubdiretorio(String diretorio) {
		ArrayList<ArquivoRetorno> listaArquivosRetorno = new ArrayList<ArquivoRetorno>();

		// Assegura a criacao do diretorio
		ArquivoUtil.asseguraDiretorioExiste(diretorio);	
		
		logger.info("-> Processando " + diretorio);

		File dir = new File(diretorio);
		File[] arquivos = dir.listFiles();
		
		for (File arquivo : arquivos) {
			try {
				ArquivoRetorno arquivoRetorno = retornarArquivoRetorno(arquivo);
			
				listaArquivosRetorno.add(arquivoRetorno);
			}
			catch (CobrancaExcecao ex) {
				logger.info("Ocorreu um erro ao tentar retornar o arquivo.");
			}
		}
		
		return listaArquivosRetorno;
	}
	
	/**
	 * Processa arquivos de retorno do banco.
	 */
	public static ArrayList<ArquivoRetorno> processarArquivosRetorno() throws CobrancaExcecao {
		ArrayList<ArquivoRetorno> listaArquivosRetorno = new ArrayList<ArquivoRetorno>();
		
		// Assegura a criacao do diretorio
		ArquivoUtil.asseguraDiretorioExiste(diretorioArquivosRetorno);	
		
		File dir = new File(diretorioArquivosRetorno);
		File[] arquivos = dir.listFiles();
		
		logger.info("Processando arquivos de retorno em " + diretorioArquivosRetorno);
		
		for (File arquivo : arquivos) {
			if (arquivo.isDirectory()) {
				listaArquivosRetorno.addAll(processarSubdiretorio(arquivo.getPath() + SUBDIR_IN));	
			}
		}
		
		return listaArquivosRetorno;
	}
	
	/**
	 * Move o arquivo para a pasta OUT. Isto acontece depois que o arquivo foi processado.
	 * @param arquivo
	 */
	public static void moverArquivoPastaProcessados(String nomeArquivo) {
		String arquivoOrigem = nomeArquivo;
		arquivoOrigem = arquivoOrigem.replace('\\', '/');
		String destino = arquivoOrigem.substring(0, arquivoOrigem.indexOf(SUBDIR_IN)) + SUBDIR_OUT;
		
		ArquivoUtil.asseguraDiretorioExiste(destino);	

		ArquivoUtil.moverArquivo(arquivoOrigem, destino);
		
		//ArquivoUtil.copiarArquivo(arquivoOrigem, destino);
		//ArquivoUtil.shellCopyFile(arquivoOrigem, destino);
		//ArquivoUtil.deletarArquivo(arquivoOrigem);
		
	}
}
