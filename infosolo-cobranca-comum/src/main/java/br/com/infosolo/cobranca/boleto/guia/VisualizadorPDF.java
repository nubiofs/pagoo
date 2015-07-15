package br.com.infosolo.cobranca.boleto.guia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import br.com.infosolo.cobranca.boleto.Contribuinte;
import br.com.infosolo.cobranca.boleto.Convenio;
import br.com.infosolo.cobranca.boleto.OrgaoRecebedor;
import br.com.infosolo.cobranca.boleto.TipoValorReferencia;
import br.com.infosolo.cobranca.dominio.boleto.Endereco;
import br.com.infosolo.cobranca.enumeracao.TipoInscricao;
import br.com.infosolo.cobranca.excecao.CobrancaExcecao;
import br.com.infosolo.cobranca.util.Constantes;
import br.com.infosolo.cobranca.util.PDFUtil;
import br.com.infosolo.cobranca.util.RetanguloPDF;
import br.com.infosolo.comum.util.ArquivoUtil;
import br.com.infosolo.comum.util.DataUtil;
import br.com.infosolo.comum.util.Logger;
import br.com.infosolo.comum.util.TextoUtil;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BarcodeInter25;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * 
 * <p>
 * DEFINIÇÃO DA CLASSE
 * </p>
 * 
 * <p>
 * OBJETIVO/PROPÓSITO
 * </p>
 * 
 * <p>
 * EXEMPLO:
 * </p>
 * 
 * @author misael
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
class VisualizadorPDF {
	private static final long serialVersionUID = 1L;

	private static Logger logger = new Logger(VisualizadorPDF.class);

	private static URL TEMPLATE_PADRAO = VisualizadorPDF.class.getResource("/recursos/templates/SNRGuiaTemplatePadrao.pdf");

	private PdfReader reader;
	private PdfStamper stamper;
	private AcroFields form;

	private ByteArrayOutputStream outputStream;

	private Guia guia;

	private URL template;

	/**
	 *<p>
	 * Para uso interno do componente
	 * </p>
	 */
	VisualizadorPDF() {
	}

	VisualizadorPDF(Guia guia) {
		this.guia = guia;
	}

	VisualizadorPDF(Guia guia, URL template) {
		this.guia = guia;
		setTemplate(template);
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @param pathName
	 *            arquivo de destino
	 * @param guias
	 *            a serem agrupados
	 * @param guiaViewer
	 *            visualizador
	 *            
	 * @return File contendo guias geradas.
	 * 
	 * @throws JRimumException
	 *             Quando ocorrer um problema na geração do PDF que está fora do
	 *             controle da biblioteca.
	 * 
	 * @since 0.3
	 */
	protected static File groupInOnePDF(String pathName, List<Guia> guias,
			GeradorGuia guiaViewer) {

		File arq = null;

		List<byte[]> guiasEmBytes = new ArrayList<byte[]>(guias.size());

		for (Guia guia : guias) {
			guiasEmBytes.add(guiaViewer.setGuia(guia).getPdfAsByteArray());
		}

		try {

			arq = ArquivoUtil.bytes2File(pathName, PDFUtil.mergeFiles(guiasEmBytes));

		} catch (FileNotFoundException e) {

			logger.error("Erro durante geração do PDF." + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante geração do PDF. Causado por "
							+ e.getLocalizedMessage(), e);

		} catch (IOException e) {

			logger.error("Erro durante geração do PDF." + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante geração do PDF. Causado por "
							+ e.getLocalizedMessage(), e);
		}

		return arq;
	}

	/**
	 * <p>
	 * Gera vários arquivos pdf, cada qual com a sua guia.
	 * </p>
	 * 
	 * @param path Caminho no qual será gerados os arquivos
	 * @param extensao 
	 * @param guias Guias a partir dos quais serão gerados os arquivos
	 * @return Vários arquivos pdf
	 * 
	 * @since 0.2
	 */
	protected List<File> onePerPDF(String path, String extensao, List<Guia> guias) {

		List<File> arquivos = new ArrayList<File>(guias.size());
		int cont = 1;
		String strBanco = "";

		for (Guia guia : guias) {
			if (strBanco.equals(""))
				strBanco = "_" + guia.getArrecadacao().getConvenio().getBanco().name() + "_";
			
			//arquivos.add(new GeradorGuia(guia).getPdfAsFile(path + "Guia" + cont++ + extensao));
			arquivos.add(getFile(path + "/Guia" + strBanco + cont++ + extensao));
		}

		return arquivos;
	}

	/**
	 * 
	 * @param pathName
	 * @return
	 * @throws IllegalArgumentException
	 */
	protected File getFile(String pathName) {

		File file = null;

		try {

			processarPdf();

			file = ArquivoUtil.bytes2File(pathName, outputStream.toByteArray());

		} catch (FileNotFoundException e) {

			logger.error("Erro ao tentar acessar arquivo inexistente. "
					+ e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro ao tentar acessar arquivo inexistente: [" + pathName
							+ "]. " + "Causado por " + e.getLocalizedMessage(), e);

		} catch (IOException e) {

			logger.error("Erro durante a criação do arquivo. " + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante a criação do arquivo: ["
					+ pathName + "]. " + "Causado por "
					+ e.getLocalizedMessage(), e);

		} catch (DocumentException e) {

			logger.error("Erro durante a criação do arquivo. " + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante a criação do arquivo: ["
					+ pathName + "]. " + "Causado por " + e.getLocalizedMessage(), e);
		}

		return file;
	}

	/**
	 * @throws JRimumException
	 * 
	 * @return
	 */
	protected ByteArrayOutputStream getStream() {

		ByteArrayOutputStream baos = null;

		try {

			processarPdf();

			baos = ArquivoUtil.bytes2Stream(outputStream.toByteArray());

		} catch (IOException e) {

			logger.error("Erro durante a criação do stream. "
					+ e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante a criação do stream. "
					+ "Causado por " + e.getLocalizedMessage(), e);

		} catch (DocumentException e) {

			logger.error("Erro durante a criação do stream. "
					+ e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante a criação do stream. "
					+ "Causado por " + e.getLocalizedMessage(), e);
		}

		return baos;
	}

	/**
	 * @throws JRimumException
	 * 
	 * @return
	 */
	protected byte[] getBytes() {

		byte[] bytes = null;

		try {

			processarPdf();

			bytes = outputStream.toByteArray();

		} catch (IOException e) {

			logger.error("Erro durante a criação do stream. "
					+ e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante a criação do stream. "
					+ "Causado por " + e.getLocalizedMessage(), e);

		} catch (DocumentException e) {

			logger.error("Erro durante a criação do stream. "
					+ e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante a criação do stream. "
					+ "Causado por " + e.getLocalizedMessage(), e);
		}

		return bytes;
	}

	protected URL getTemplate() {
		return template;
	}

	protected void setTemplate(URL template) {
		this.template = template;
	}

	protected void setTemplate(String pathname) {
		File file = new File(pathname);
		try {
			setTemplate(file.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the boleto
	 * 
	 * @since 0.2
	 */
	protected Guia getGuia() {
		return this.guia;
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 * 
	 * @since
	 */
	private void processarPdf() throws IOException, DocumentException {
		inicializar();
		preencher();
		finalizar();
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @return URL template
	 * 
	 * @since
	 */
	private URL getTemplateFromResource() {
		URL templateFromResource = TEMPLATE_PADRAO;
		return templateFromResource;
	}

	/**
	 * <p>
	 * Verifica se o template que será utilizado virá do resource ou é externo,
	 * ou seja, se o usuário definiu ou não um template.
	 * </p>
	 * 
	 * @return true caso o template que pode ser definido pelo usuário for null;
	 *         false caso o usuário tenha definido um template.
	 * 
	 * @since
	 */
	private boolean isTemplateFromResource() {
		return getTemplate() == null;
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 * 
	 * @since
	 */

	private void inicializar() throws IOException, DocumentException {

		if (isTemplateFromResource()) {
			reader = new PdfReader(getTemplateFromResource());

		} else {
			//reader = new PdfReader(getTemplate().getAbsolutePath());
			reader = new PdfReader(getTemplate());
		}

		outputStream = new ByteArrayOutputStream();
		stamper = new PdfStamper(reader, outputStream);
		form = stamper.getAcroFields();
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @throws DocumentException
	 * @throws IOException
	 * 
	 * @since
	 */
	private void finalizar() throws DocumentException, IOException {

		reader.consolidateNamedDestinations();/*
											 * Replaces all the local named
											 * links with the actual
											 * destinations.
											 */

		stamper.setFormFlattening(true);/*
										 * Determines if the fields are
										 * flattened on close.
										 */
		stamper.setRotateContents(true);/*
										 * Flags the content to be automatically
										 * adjusted to compensate the original
										 * page rotation.
										 */

		reader.removeFields();/* Removes all the fields from the document. */

		stamper.setFullCompression();/*
									 * Sets the document's compression to the
									 * new 1.5 mode with object streams and xref
									 * streams.
									 */

		reader.eliminateSharedStreams();/*
										 * Eliminates shared streams if they
										 * exist.
										 */

		// Send immediately
		outputStream.flush();

		// close All in this order
		outputStream.close();
		reader.close();
		stamper.close();
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 * 
	 * @since
	 */
	private void preencher() throws MalformedURLException, IOException, DocumentException {
		//setLogoConvenio();
		setLogoBanco();
		setLogoOrgaoRecebedor();
		setContribuinteNome();
		setContribuinteCpfCnpj();
		setDescricao();
		setTitulo();
		setNumeroDocumento();
		setNossoNumero();
		setNomeCedente();
		setValorDocumento();
		setDataDocumento();
		setDataVencimeto();
		setInstrucaoAoCaixa();
		setLinhaDigitavel();
		setCodigoBarra();
		setCamposExtra();
		setImagensNosCampos();
	}

	private void setCamposExtra() throws IOException, DocumentException {
		if (guia.getTextosExtras() != null) {
			for (String campo : guia.getTextosExtras().keySet()) {
				form.setField(campo, guia.getTextosExtras().get(campo));
			}
		}
	}

	private void setCodigoBarra() throws DocumentException {
		// Montando o código de barras.
		BarcodeInter25 barCode = new BarcodeInter25();
		BarcodeInter25 barCode2 = new BarcodeInter25();
		
		barCode.setCode(guia.getCodigoDeBarras().write());
		barCode.setExtended(true);
		barCode.setBarHeight(40);
		barCode.setFont(null);
		barCode.setN(3);

		if (guia.getCodigoDeBarras2() != null) {
			barCode2.setCode(guia.getCodigoDeBarras2().write());
			barCode2.setExtended(true);
			barCode2.setBarHeight(40);
			barCode2.setFont(null);
			barCode2.setN(3);
		}
		
		PdfContentByte cb = null;

		// Verifcando se existe o field(campo) da imagem no template do boleto.
		float posCampoImgLogo[] = form.getFieldPositions("txtCodigoBarra");

		if (posCampoImgLogo != null) {
			RetanguloPDF field = new RetanguloPDF(posCampoImgLogo);

			cb = stamper.getOverContent(field.getPage());
			Image imgBarCode = barCode.createImageWithBarcode(cb, null, null);

			PDFUtil.changeField2Image(stamper, field, imgBarCode);
		}
		
		posCampoImgLogo = form.getFieldPositions("txtCodigoBarra2");

		if (posCampoImgLogo != null) {
			RetanguloPDF field = new RetanguloPDF(posCampoImgLogo);

			cb = stamper.getOverContent(field.getPage());
			Image imgBarCode = barCode2.createImageWithBarcode(cb, null, null);

			PDFUtil.changeField2Image(stamper, field, imgBarCode);
		}
	}

	private void setDataDocumento() throws IOException, DocumentException {
		String dataDocumento = DataUtil.formatarData(guia.getArrecadacao().getDataDoDocumento());
		form.setField("txtDataDocumento", dataDocumento);
		form.setField("txtDataEmissao", dataDocumento);
	}

	private void setInstrucaoAoCaixa() throws IOException, DocumentException {
		form.setField("txtInstrucaoAoCaixa", guia.getInstrucoes());
		form.setField("txtInstrucaoAoCaixa1", guia.getInstrucoes());
	}

	private void setValorDocumento() throws IOException, DocumentException {
		String valorStr = null;
		
		if ( (guia.getArrecadacao().getTipoValorReferencia() == TipoValorReferencia.VALOR_COBRADO_EM_REAL_COM_DV_MODULO_10)
				|| (guia.getArrecadacao().getTipoValorReferencia() == TipoValorReferencia.VALOR_COBRADO_EM_REAL_COM_DV_MODULO_11)  ) {
			valorStr = TextoUtil.formataValor(guia.getArrecadacao().getValorDocumento());
		}
		else {
			valorStr = TextoUtil.formataValor(guia.getArrecadacao().getValorDocumento());
		}
				
		form.setField("txtValorDocumento", valorStr);
		form.setField("txtValorDocumento1", valorStr);
		
		if (guia.getArrecadacao2() != null) {
			form.setField("txtValorDocumento2", TextoUtil.formataValor(guia.getArrecadacao2().getValorDocumento()));
		}
	}

	private void setDataVencimeto() throws IOException, DocumentException {
		String dataFormatada = null;
		if (guia.getArrecadacao().getDataDoVencimento() != null)
			dataFormatada =	DataUtil.formatarData(guia.getArrecadacao().getDataDoVencimento());
		else
			dataFormatada = "CONTRA APRESENTAÇÃO";
		
		form.setField("txtDataVencimento", dataFormatada);
		form.setField("txtDataVencimento1", dataFormatada);
		form.setField("txtDataVencimento2", dataFormatada);
		form.setField("txtDataVencimento3", dataFormatada);

	}

	private void setContribuinteNome() throws IOException, DocumentException {
		Contribuinte contribuinte = guia.getArrecadacao().getContribuinte();
		form.setField("txtNomeContribuinte", contribuinte.getNome());
		
		Endereco endereco = contribuinte.getEndereco();
		
		String sacado = getCpfCnpjSacado() + " " + contribuinte.getNome() + "\n" + endereco.getLogradouro() + " " + 
			(endereco.getNumero() != null ? endereco.getNumero() : "") + "\n" + 
			endereco.getCidade() + " - " + endereco.getUf() + "  " + endereco.getCep();
		form.setField("txtSacado", sacado);

		String sacado1 = getCpfCnpjSacado() + "\n" + contribuinte.getNome();
		form.setField("txtSacado1", sacado1);

		String sacado2 = getCpfCnpjSacado() + "\n" + contribuinte.getNome();
		form.setField("txtSacado2", sacado2);
		
		form.setField("txtTextoSacado", "FICHA DE COMPENSAÇÃO");
	}

	private String getCpfCnpjSacado() {
		if (guia.getArrecadacao().getContribuinte().getTipoInscricao() == TipoInscricao.CPF)
			return TextoUtil.formataNumeroCPF(guia.getArrecadacao().getContribuinte().getCPF());
		else if (guia.getArrecadacao().getContribuinte().getTipoInscricao() == TipoInscricao.CNPJ)
			return TextoUtil.formataNumeroCNPJ(guia.getArrecadacao().getContribuinte().getCNPJ());
		return null;
	}
	
	private void setContribuinteCpfCnpj() throws IOException, DocumentException {
		form.setField("txtContribuinteCPF", getCpfCnpjSacado());
	}

	private void setDescricao() throws IOException, DocumentException {
		form.setField("txtDescricao", guia.getArrecadacao().getDescricao());
	}

	private void setTitulo() throws IOException, DocumentException {
		form.setField("txtTitulo", guia.getArrecadacao().getTitulo());
	}

	private void setLinhaDigitavel() throws DocumentException, IOException {
		form.setField("txtLinhaDigitavel", guia.getLinhaDigitavel().write());
		if (guia.getLinhaDigitavel2() != null) {
			form.setField("txtLinhaDigitavel2", guia.getLinhaDigitavel2().write());
		}
	}

	private void setLogoBanco() throws MalformedURLException, IOException,
			DocumentException {

		// Através da conta bancária será descoberto a imagem que representa o
		// banco, com base
		// no código do banco.
		Convenio convenio = guia.getArrecadacao().getConvenio();
		Image imgLogoBanco = null;

		if (convenio.getBanco().getImagemLogo() != null) {
			imgLogoBanco = Image.getInstance(convenio.getBanco().getImagemLogo(), null);
			setImageLogo(imgLogoBanco);

		} else {
			URL url = this.getClass().getResource(Constantes.DIRETORIO_IMAGENS_BANCO
					+ String.format("%03d.png", convenio.getBanco().getCodigo()));

			if (url == null) {
				url = this.getClass().getResource(Constantes.DIRETORIO_IMAGENS_BANCO
						+ String.format("%03d.jpg", convenio.getBanco().getCodigo()));
			}
			
			if (url != null) {
				imgLogoBanco = Image.getInstance(url);

				if (imgLogoBanco != null) {

					// Esta imagem gerada aqui é do tipo java.awt.Image
					convenio.getBanco().setImagemLogo(ImageIO.read(url));
				}

				// Se o banco em questão é suportado nativamente pelo
				// componente,
				// então um alerta será exibido.
				logger.debug("Banco sem imagem da logo informada. "
							+ "Com base no c�digo do banco, uma imagem foi "
							+ "encontrada no resource e está sendo utilizada.");
				

				setImageLogo(imgLogoBanco);

			} else {

				// Sem imagem, um alerta é exibido.
				logger.info("Banco sem imagem definida. O nome da institui��o ser� usado como logo.");

				form.setField("txtLogoBanco", convenio.getBanco().getNome());
				form.setField("txtLogoBanco2", convenio.getBanco().getNome());
			}
		}
	}

//	private void setLogoConvenio() throws MalformedURLException, IOException, DocumentException {
//		byte[] Cedente cedente = guia.getCedente();
//		Image imgLogoCedente = null;
//
//		URL url = this.getClass().getResource(Constantes.DIRETORIO_IMAGENS_CONVENIO
//				+ String.format("%d.gif", cedente.getConvenio()));
//
//		if (url != null) {
//			imgLogoCedente = Image.getInstance(url);
//			
//			setImagemNoCampo("txtLogoEmpresa", imgLogoCedente);	
//		} 
//	}
	
	private void setLogoOrgaoRecebedor() throws MalformedURLException, IOException,
			DocumentException {

		Image imgLogoBanco = null;
		OrgaoRecebedor orgaoRecebedor = guia.getArrecadacao().getOrgaoRecebedor();
		
		if (orgaoRecebedor.getImgLogo() != null) {
			imgLogoBanco = Image.getInstance(orgaoRecebedor.getImgLogo(),	null);
			
			setImagemNoCampo("txtLogoOrgaoRecebedor1", imgLogoBanco);
			setImagemNoCampo("txtLogoOrgaoRecebedor2", imgLogoBanco);
		}
	}
	
	
	/**
	 * <p>
	 * Coloca as imagens dos campos no pdf de acordo com o nome dos campos do
	 * boleto atribuídos no map e templante.
	 * </p>
	 * 
	 * @throws DocumentException
	 * @throws IOException
	 * 
	 * @since 0.2
	 */
	private void setImagensNosCampos() throws DocumentException, IOException {
		if (guia.getImagensExtras() != null) {
			for (String campo : guia.getImagensExtras().keySet()) {
				setImagemNoCampo(campo, Image.getInstance(guia.getImagensExtras().get(campo), null));
			}
		}
	}

	/**
	 * <p>
	 * Coloca uma imagem no pdf de acordo com o nome do field no templante.
	 * </p>
	 * 
	 * @param nomeDoCampo
	 * @param imagem
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 */
	private void setImagemNoCampo(String nomeDoCampo, Image imagem)
			throws DocumentException {

		float posCampoImgLogo[];

		if (StringUtils.isNotBlank(nomeDoCampo)) {

			posCampoImgLogo = form.getFieldPositions(nomeDoCampo);

			if (posCampoImgLogo != null) {
				PDFUtil.changeField2Image(stamper, posCampoImgLogo, imagem);
			}
		}
	}

	/**
	 * <p>
	 * Coloca a logo do passada na ficha de compensação do boleto e no recibo do
	 * sacado.
	 * </p>
	 * 
	 * @param imgLogoBanco
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 */
	private void setImageLogo(Image imgLogoBanco) throws DocumentException {
		setImagemNoCampo("txtLogoBanco", imgLogoBanco);
		setImagemNoCampo("txtLogoBanco2", imgLogoBanco);
	}

	private void setNossoNumero() throws IOException, DocumentException {
		form.setField("txtNossoNumero", guia.getArrecadacao().getNossoNumero());
		form.setField("txtNossoNumero1", guia.getArrecadacao().getNossoNumero());
		
		if (guia.getArrecadacao2() != null) {
			form.setField("txtNossoNumero2", guia.getArrecadacao2().getNossoNumero());
		}
	}

	private void setNumeroDocumento() throws IOException, DocumentException {
		form.setField("txtNumero", guia.getArrecadacao().getNumeroDoDocumento());
	}

	private void setNomeCedente() throws IOException, DocumentException {
		String cedente = TextoUtil.formataNumeroCNPJ(guia.getCedente().getCpfCnpj()) + "\n" + guia.getCedente().getNome();

		form.setField("txtCedente", cedente);
		form.setField("txtCedente1", cedente);
		
		if (guia.getCedente2() != null) {

			String cedente2 = null;			
			if(guia.getCedente2().getDescricaoConcedente()!=null && !guia.getCedente2().getDescricaoConcedente().equals("")){
				cedente2 = guia.getCedente2().getDescricaoConcedente();	
			}else{
				cedente2 = TextoUtil.formataNumeroCNPJ(guia.getCedente2().getCpfCnpj()) + "\n" + guia.getCedente2().getNome();
			}
			form.setField("txtCedente2", cedente2);
		}
	}

	/**
	 * Exibe os valores de instância.
	 * 
	 * @see br.com.nordestefomento.jrimum.utilix.ObjectUtil#toString()
	 */
	@Override
	public String toString() {
		ToStringBuilder tsb = new ToStringBuilder(this);
		tsb.append(guia);
		return tsb.toString();
	}
}
