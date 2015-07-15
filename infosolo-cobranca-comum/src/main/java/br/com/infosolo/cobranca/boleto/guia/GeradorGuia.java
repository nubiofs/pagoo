package br.com.infosolo.cobranca.boleto.guia;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.infosolo.cobranca.excecao.CobrancaExcecao;
import br.com.infosolo.comum.util.Logger;

import com.lowagie.text.DocumentException;

/**
 * 
 * <p>
 * Agrupa as formas de visualização de um boleto.
 * </p>
 * 
 * <p>
 * EXEMPLO de formas de visualização:
 * <ul>
 * <li>PDF</li>
 * <li>Stream</li>
 * <li>Array de Bytes</li>
 * </ul>
 * </p>
 * 
 */
public class GeradorGuia {
	private static final long serialVersionUID = 1L;
	
	
	private static Logger logger = new Logger(GeradorGuia.class);

	/**
	 * <p> Engine responsável pela visualização em formato <em>PDF</em>.
	 */
	private VisualizadorPDF visualizadorPDF;

	/**
	 * @param guia
	 * @throws DocumentException
	 * @throws IOException
	 */
	public GeradorGuia(Guia guia) throws CobrancaExcecao {
		initViewerPDF(null, null, guia);			
	}
	
	/**
	 * @param guia
	 * @param templatePathName
	 * @throws JRimumException
	 */
	public GeradorGuia(Guia guia, String templatePathName) throws CobrancaExcecao {
		initViewerPDF(templatePathName, null, guia);
	}
	
	/**
	 * @param guia
	 * @param template
	 * @throws JRimumException
	 */
	public GeradorGuia(Guia guia, URL template) throws CobrancaExcecao {
		initViewerPDF(null, template, guia);
	}

	/**
	 *<p> Para uso interno do componente </p> 
	 */
	protected GeradorGuia() {
		this.visualizadorPDF = new VisualizadorPDF();
	}

	/**
	 * <p>
	 * Agrupo vários guias em um único pdf.
	 * </p>
	 * 
	 * @param pathName
	 *            Caminho no qual será gerado o pdf
	 * @param guias
	 *            Guias a serem agrupadas
	 * @return Arquivo pdf
	 * @throws JRimumException
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePDF(String pathName, List<Guia> guias) throws CobrancaExcecao {

		File group = null;
		
		if (validatePathName(pathName) && validateGuiasList(guias)) {
				group = groupInOnePDF(pathName, guias, new GeradorGuia());
		}

		return group;
	}

	public static File groupInOnePDF(String destPathName, List<Guia> guias, String templatePathName) throws CobrancaExcecao {

		File group = null;

		if (validatePathName(destPathName) &&validateGuiasList(guias) && validatePathName(templatePathName)) {
			group = groupInOnePDF(destPathName, guias, new GeradorGuia().setTemplate(templatePathName));
		}
					
		return group;
	}
	
	public static File groupInOnePDF(String destPathName, List<Guia> guias, URL templateFile) throws CobrancaExcecao {

		File group = null;

		if (validatePathName(destPathName) && validateGuiasList(guias) && validateFile(templateFile, "template")) {
					group = groupInOnePDF(destPathName, guias, new GeradorGuia().setTemplate(templateFile));
		}
					
		return group;
	}
	
	public URL getTemplate() {
		return visualizadorPDF.getTemplate();
	}

	
	/**
	 * <p>
	 * Define o template que será utilizado para construir o guia.
	 * </p>
	 * 
	 * @param template
	 * 
	 * @since 0.2
	 */
	public GeradorGuia setTemplate(URL template) {

		if (template != null) {
			this.visualizadorPDF.setTemplate(template);
			
		} else {
			throw new NullPointerException("Arquivo de template inválido: valor [null]");
		}
		
		return this;
	}

	
	/**
	 * <p>
	 * @see GeradorGuia#setTemplate(File)
	 * </p>
	 * 
	 * @param pathName
	 * 
	 * @since 0.2
	 */
		
	public GeradorGuia setTemplate(String pathName) {
		
		if (isNotBlank(pathName)) {
			visualizadorPDF.setTemplate(pathName);
			
		} else {
			throw new IllegalArgumentException("Caminho do template inválido: valor [" + pathName + "]");
		}
		
		return this;
	}
	
	/**
	 * <p>
	 * Caso algum template tenha sido utilizado, este método define que após sua
	 * execução o guia será consturído com o template padrão.
	 * </p>
	 * 
	 * @since 0.2
	 */
	public GeradorGuia removeTemplate() {

		final String PADRAO = null;

		if (visualizadorPDF != null) {
			visualizadorPDF.setTemplate(PADRAO);
		}

		return this;
	}

	/**
	 * <p>
	 * Retorna o guia em um arquivo pdf.
	 * </p>
	 * 
	 * @param pathName Caminho onde será criado o arquivo pdf
	 * @return File
	 * @throws IllegalArgumentException
	 * 
	 * @since 0.2
	 */
	public File getPdfAsFile(String pathName) {

		logger.debug("Documento instance: " + visualizadorPDF);
		

		return visualizadorPDF.getFile(pathName);
	}

	/**
	 * <p>
	 * Retorna o guia em uma stream.
	 * </p>
	 * 
	 * @return ByteArrayOutputStream
	 * 
	 * @since 0.1
	 */
	public ByteArrayOutputStream getPdfAsStream() {

		logger.debug("documento instance : " + visualizadorPDF);

		return visualizadorPDF.getStream();

	}

	/**
	 * <p>
	 * Retorna o guia em um array de bytes.
	 * </p>
	 * 
	 * @return byte[]
	 * 
	 * @since 0.1
	 */
	public byte[] getPdfAsByteArray() {

		logger.debug("documento instance : " + visualizadorPDF);

		return visualizadorPDF.getBytes();
	}

	/**
	 * @return the guia
	 * 
	 * @since 0.2
	 */
	public Guia getGuia() {
		return visualizadorPDF.getGuia();
	}

	/**
	 * @param guia
	 *            the guia to set
	 * 
	 * @since 0.2
	 */
	public GeradorGuia setGuia(Guia guia) {
		
		if (guia != null) {
			updateViewerPDF(guia);
		}
		
		return this;
	}

	private static boolean validatePathName(String pathName){
		boolean ok = false;
		
		if (pathName != null) {
			
			if (StringUtils.isNotBlank(pathName)) {
				ok = true;
			} else {
				throw new IllegalArgumentException("Path(Diretório) destinado a geração do(s) arquivo(s) não contém informação!");
			}
		}
		
		return ok;
	}
	
	private static boolean validateFile(URL file, String name){
		boolean ok = false;
		
		if (file != null) {
				ok = true;
		} else {
			throw new NullPointerException("File(Arquivo) destinado a geração do(s) documento(s) [" + name + "] nulo!");
		}
		
		return ok;
	}
	
	private static boolean validateGuiasList(List<Guia> guias){
		boolean ok = false;
		
		if (guias != null) {
			
			if(!guias.isEmpty()) {
				ok = true;
				
			} else {
				throw new IllegalArgumentException("A Lista de guias está vazia!");
			}
		}
		
		return ok;
	}
	
	private static File groupInOnePDF(String pathName, List<Guia> guias, GeradorGuia guiaViewer){
		
		return VisualizadorPDF.groupInOnePDF(pathName, guias, guiaViewer);		
	}

	private void initViewerPDF(String templatePathName, URL template, Guia guia) {
		
		if (guia != null) {
			this.visualizadorPDF = new VisualizadorPDF(guia);
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
	 * Atualiza o objeto GuiaViewer mantendo as "invariantes".
	 * </p>
	 * 
	 * @param guia
	 * 
	 * @since 
	 */
		
	private void updateViewerPDF(Guia guia) {

		if (this.visualizadorPDF != null) {
			this.visualizadorPDF = new VisualizadorPDF(guia, this.visualizadorPDF.getTemplate());
			
		} else {
			this.visualizadorPDF = new VisualizadorPDF(guia);
		}
	}
}
