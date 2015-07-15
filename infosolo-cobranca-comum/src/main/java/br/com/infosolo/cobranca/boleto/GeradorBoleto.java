package br.com.infosolo.cobranca.boleto;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.excecao.CobrancaExcecao;
import br.com.infosolo.comum.util.Logger;

import com.lowagie.text.DocumentException;

/**
 * 
 * <p>
 * Agrupa as formas de visualizacao de um boleto.
 * </p>
 * 
 * <p>
 * EXEMPLO de formas de visualizacao:
 * <ul>
 * <li>PDF</li>
 * <li>Stream</li>
 * <li>Array de Bytes</li>
 * </ul>
 * </p>
 * 
 * @author <a href="mailto:leandro.lima@infosolo.com.br">Leandro Lima</a>
 *
 * @version 0.2
 */

public class GeradorBoleto {
	private static Logger logger = new Logger(GeradorBoleto.class.getName());

	private static String EXTENSAO_ARQUIVO_PDF = ".pdf";

	/**
	 * <p> Engine responsavel pela visualizacao em formato <em>PDF</em>.
	 */
	private VisualizadorPDF visualizadorPDF;

	/**
	 * @param boleto
	 * @throws DocumentException
	 * @throws IOException
	 */
	public GeradorBoleto(Boleto boleto) throws CobrancaExcecao {
		iniciaVisualizadorPDF(null, null, boleto);			
	}
	
	/**
	 * @param boleto
	 * @param templatePathName
	 * @throws JRimumException
	 */
	public GeradorBoleto(Boleto boleto, String templatePathName) throws CobrancaExcecao {
		iniciaVisualizadorPDF(templatePathName, null, boleto);
	}
	
	/**
	 * @param boleto
	 * @param template
	 * @throws JRimumException
	 */
	public GeradorBoleto(Boleto boleto, File template) throws CobrancaExcecao {
		iniciaVisualizadorPDF(null, template, boleto);
	}

	/**
	 *<p> Para uso interno do componente </p> 
	 */
	public GeradorBoleto() {
		this.visualizadorPDF = new VisualizadorPDF();
	}

	/**
	 * <p>
	 * Agrupo varios boletos em um unico pdf.
	 * </p>
	 * 
	 * @param pathName
	 *            Caminho no qual sera gerado o pdf
	 * @param boletos
	 *            Boletos a serem agrupados
	 * @return Arquivo pdf
	 * @throws JRimumException
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePDF(String pathName, List<Boleto> boletos) throws CobrancaExcecao {
		File group = null;
		
		if (validatePathName(pathName) && validateBoletosList(boletos)) {
//			if (validateBoletosList(boletos)) {
				group = agrupaEmUmPDF(pathName, boletos, new GeradorBoleto());
//			}
		}

		return group;
	}

	public static File groupInOnePDF(String destPathName, List<Boleto> boletos, String templatePathName) throws CobrancaExcecao {
		File group = null;

		if (validatePathName(destPathName) &&validateBoletosList(boletos) && validatePathName(templatePathName)) {
//			if (validateBoletosList(boletos)) {
//				if (validatePathName(templatePathName)) {
					group = agrupaEmUmPDF(destPathName, boletos, new GeradorBoleto().setTemplate(templatePathName));
//				}
//			}
		}
					
		return group;
	}
	
	public static File groupInOnePDF(String destPathName, List<Boleto> boletos, File templateFile) throws CobrancaExcecao {
		File group = null;

		if (validatePathName(destPathName) && validateBoletosList(boletos) && validateFile(templateFile, "template")) {
//			if (validateBoletosList(boletos))
//				if (validateFile(templateFile, "template"))
					group = agrupaEmUmPDF(destPathName, boletos, new GeradorBoleto().setTemplate(templateFile));
		}
					
		return group;
	}

	/**
	 * <p>
	 * Gera varios arquivos pdf, cada qual com o seu boleto.
	 * </p>
	 * 
	 * @param path Caminho no qual sera gerados os arquivos
	 * @param extensao
	 * @param boletos Boletos a partir dos quais serao gerados os arquivos
	 * @return Varios arquivos pdf
	 * @throws CobrancaExcecao
	 * 
	 * @since 0.2
	 */
	public List<File> getListaArquivosPDF(String path, List<Boleto> boletos) throws CobrancaExcecao {
		List<File> files = new ArrayList<File>();

		if (path != null && boletos != null) {
			if (StringUtils.isNotBlank(path)) {
				if (!boletos.isEmpty()) {
					files.addAll(visualizadorPDF.umPorPDF(path, EXTENSAO_ARQUIVO_PDF, boletos));
				} else {
					throw new IllegalArgumentException("A Lista de boletos esta vazia!");
				}
			} else {
				throw new IllegalArgumentException("Caminho destinado a geracao dos arquivos nao contem informacao!");
			}
		}
		
		return files;
	}

	public File getTemplate() {
		return visualizadorPDF.getTemplate();
	}

	/**
	 * <p>
	 * Define o template que sera utilizado para construir o boleto.
	 * </p>
	 * 
	 * @param template
	 * 
	 * @since 0.2
	 */
	public GeradorBoleto setTemplate(File template) {
		if (template != null) {
			this.visualizadorPDF.setTemplate(template);
		} else {
			throw new NullPointerException("Arquivo de template invalido: valor [null]");
		}
		
		return this;
	}

	/**
	 * <p>
	 * @see GeradorBoleto#setTemplate(File)
	 * </p>
	 * 
	 * @param pathName
	 * 
	 * @since 0.2
	 */
	public GeradorBoleto setTemplate(String pathName) {
		if (isNotBlank(pathName)) {
			visualizadorPDF.setTemplate(pathName);
		} else {
			throw new IllegalArgumentException("Caminho do template invalido: valor [" + pathName + "]");
		}
		
		return this;
	}
	
	/**
	 * <p>
	 * Caso algum template tenha sido utilizado, este metodo define que apos sua
	 * execucao o boleto sera construido com o template padrao.
	 * </p>
	 * 
	 * @since 0.2
	 */
	public GeradorBoleto removerTemplate() {
		final String PADRAO = null;

		if (visualizadorPDF != null) {
			visualizadorPDF.setTemplate(PADRAO);
		}

		return this;
	}

	/**
	 * <p>
	 * Retorna o boleto em um arquivo pdf.
	 * </p>
	 * 
	 * @param pathName Caminho onde sera criado o arquivo pdf
	 * @return File
	 * @throws IllegalArgumentException
	 * 
	 * @since 0.2
	 */
	public File getArquivoPdf(String pathName) {
		//logger.info("documento instance : " + visualizadorPDF);
		return visualizadorPDF.getFile(pathName);
	}

	/**
	 * <p>
	 * Retorna o boleto em uma stream.
	 * </p>
	 * 
	 * @return ByteArrayOutputStream
	 * 
	 * @since 0.1
	 */
	public ByteArrayOutputStream getPdfAsStream() {
		logger.info("documento instance : " + visualizadorPDF);
		return visualizadorPDF.getStream();
	}

	/**
	 * <p>
	 * Retorna o boleto em um array de bytes.
	 * </p>
	 * 
	 * @return byte[]
	 * 
	 * @since 0.1
	 */
	public byte[] getPdfAsByteArray() {
		return visualizadorPDF.getBytes();
	}

	/**
	 * @return the boleto
	 * 
	 * @since 0.2
	 */
	public Boleto getBoleto() {
		return visualizadorPDF.getBoleto();
	}

	/**
	 * @param boleto
	 *            the boleto to set
	 * 
	 * @since 0.2
	 */
	public GeradorBoleto setBoleto(Boleto boleto) {
		if (boleto != null) {
			atualizaVisualizadorPDF(boleto);
		}
		
		return this;
	}

	private static boolean validatePathName(String pathName){
		boolean ok = false;
		
		if (pathName != null) {
			
			if (StringUtils.isNotBlank(pathName)) {
				ok = true;
			} else {
				throw new IllegalArgumentException("Caminho destinado a geracao do(s) arquivo(s) nao contem informacao!");
			}
		}
		
		return ok;
	}
	
	private static boolean validateFile(File file, String name){
		boolean ok = false;
		
		if (file != null) {
			ok = true;
		} else {
			throw new NullPointerException("Arquivo destinado a geracao do(s) documento(s) [" + name + "] nulo!");
		}
		
		return ok;
	}
	
	private static boolean validateBoletosList(List<Boleto> boletos){
		boolean ok = false;
		
		if (boletos != null) {
			if (!boletos.isEmpty()) {
				ok = true;
			} else {
				throw new IllegalArgumentException("A Lista de boletos esta vazia!");
			}
		}
		
		return ok;
	}
	
	private static File agrupaEmUmPDF(String pathName, List<Boleto> boletos, GeradorBoleto boletoViewer){
		return VisualizadorPDF.agruparEmUmPDF(pathName, boletos, boletoViewer);		
	}

	private void iniciaVisualizadorPDF(String templatePathName, File template, Boleto boleto) {
		if (boleto != null) {
			this.visualizadorPDF = new VisualizadorPDF(boleto);
		}
		
		/*
		 * O arquivo tem prioridade 
		 */
		if (isNotBlank(templatePathName) && template != null) {
			setTemplate(template);
		} else {
			if (isNotBlank(templatePathName)) {
				setTemplate(templatePathName);
			}
			
			if (template != null) {
				setTemplate(template);
			}
		}
	}
	
	/**
	 * <p>
	 * Atualiza o objeto BoletoViewer mantendo as "invariantes".
	 * </p>
	 * 
	 * @param boleto
	 * 
	 * @since 
	 */
	private void atualizaVisualizadorPDF(Boleto boleto) {
		if (this.visualizadorPDF != null) {
			this.visualizadorPDF = new VisualizadorPDF(boleto, this.visualizadorPDF.getTemplate());
			
		} else {
			this.visualizadorPDF = new VisualizadorPDF(boleto);
		}
	}
}
