package br.com.infosolo.cobranca.boleto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.ContaBancaria;
import br.com.infosolo.cobranca.dominio.boleto.Endereco;
import br.com.infosolo.cobranca.dominio.boleto.Sacado;
import br.com.infosolo.cobranca.dominio.boleto.SacadorAvalista;
import br.com.infosolo.cobranca.excecao.CobrancaExcecao;
import br.com.infosolo.cobranca.util.BancoUtil;
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
 * Esta classe imprime no template PDF as informacoes do boleto. 
 * 
 * @version 1
 */
public class VisualizadorPDF {
	private static Logger logger = new Logger(VisualizadorPDF.class);

	private static URL TEMPLATE_PADRAO_COM_SACADOR_AVALISTA = VisualizadorPDF.class.getResource("/recursos/templates/BoletoTemplateComSacadorAvalista.pdf");
	private static URL TEMPLATE_PADRAO_SEM_SACADOR_AVALISTA = VisualizadorPDF.class.getResource("/recursos/templates/SNRBoletoTemplateSemSacadorAvalista.pdf");

	private PdfReader reader;
	private PdfStamper stamper;
	private AcroFields form;
	private ByteArrayOutputStream outputStream;
	private Boleto boleto;
	private File template;

	/**
	 *<p> Para uso interno do componente </p> 
	 */
	VisualizadorPDF() {
		
	}
	
	VisualizadorPDF(Boleto boleto) {
		this.boleto = boleto;
	}
	
	VisualizadorPDF(Boleto boleto, File template) {
		this.boleto = boleto;
		
		setTemplate(template);
	}

	/**
	 * <p>
	 * Agrupa todos boletos em um arquivo.
	 * </p>
	 * 
	 * @param pathName arquivo de destino
	 * @param boletos a serem agrupados
	 * @param boletoViewer visualizador
	 * @return File contendo boletos gerados
	 * 
	 * @throws JRimumException Quando ocorrer um problema na geracao do PDF que esta fora do controle
	 * da biblioteca.
	 * 
	 * @since 0.2
	 */
	protected static File agruparEmUmPDF(String pathName, List<Boleto> boletos, GeradorBoleto boletoViewer) {
		File arq = null;

		List<byte[]> boletosEmBytes = new ArrayList<byte[]>(boletos.size());

		for (Boleto bop : boletos) {
			boletosEmBytes.add(boletoViewer.setBoleto(bop).getPdfAsByteArray());
		}

		try {
			arq = ArquivoUtil.bytes2File(pathName, PDFUtil.mergeFiles(boletosEmBytes));
			
		} catch (FileNotFoundException e) {
			logger.error("Erro durante geracao do PDF." + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante geracao do PDF. Causado por " + e.getLocalizedMessage(), e);
			
		} catch (IOException e) {
			logger.error("Erro durante geracao do PDF." + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante geracao do PDF. Causado por " + e.getLocalizedMessage(), e);
		}

		return arq;
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @param path
	 * @param extensao 
	 * @param boletos
	 * @return List<File> com os boletos gerados.
	 * 
	 * @since 0.2
	 */
	protected List<File> umPorPDF(String path, String extensao, List<Boleto> boletos) {
		List<File> arquivos = new ArrayList<File>(boletos.size());
		//int cont = 1;
		String strBanco = "";
		
		for (Boleto bop : boletos) {
			if (strBanco.equals(""))
				strBanco = "_" + bop.getCedente().getContaBancaria().getBanco().name() + "_";
			
			boleto = bop;
			//arquivos.add(getFile(path + "/Boleto" + strBanco + cont++ + extensao));
			arquivos.add(getFile(path + "/Boleto" + strBanco + bop.getNossoNumero() + extensao));
		}

		return arquivos;
	}

	/**
	 * Retorna o PDF como File
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
			logger.error("Erro ao tentar acessar arquivo inexistente. " + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro ao tentar acessar arquivo inexistente: [" + pathName + "]. " +
					"Causado por " + e.getLocalizedMessage(), e);
			
		} catch (IOException e) {
			logger.error("Erro durante a criação do arquivo. " + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante a criação do arquivo: [" + pathName + "]. " +
					"Causado por " + e.getLocalizedMessage(), e);
			
		} catch (DocumentException e) {
			logger.error("Erro durante a criação do arquivo. " + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante a criação do arquivo: [" + pathName + "]. " +
					"Causado por " + e.getLocalizedMessage(), e);
		}
		
		return file;
	}

	/**
	 * Retorna o PDF em Stream
	 * 
	 * @return
	 */
	protected ByteArrayOutputStream getStream() {
		ByteArrayOutputStream baos = null;
		
		try {
			processarPdf();
			
			baos = ArquivoUtil.bytes2Stream(outputStream.toByteArray());
			
		} catch (IOException e) {
			logger.error("Erro durante a criação do stream. " + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante a criação do stream. " +
					"Causado por " + e.getLocalizedMessage(), e);
			
		} catch (DocumentException e) {
			logger.error("Erro durante a criação do stream. " + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante a criação do stream. " +
					"Causado por " + e.getLocalizedMessage(), e);
		}
		
		return baos;
	}

	/**
	 * Retorna o PDF em array de bytes.
	 * 
	 * @return
	 */
	protected byte[] getBytes() {
		byte[] bytes = null;
		
		try {
			processarPdf();
			bytes = outputStream.toByteArray();
			
		} catch (IOException e) {
			logger.error("Erro durante a criação do stream. " + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante a criação do stream. " +
					"Causado por " + e.getLocalizedMessage(), e);
			
		} catch (DocumentException e) {
			logger.error("Erro durante a criação do stream. " + e.getLocalizedMessage(), e);
			throw new CobrancaExcecao("Erro durante a criação do stream. " +
					"Causado por " + e.getLocalizedMessage(), e);
		}
		
		return bytes;
	}

	protected File getTemplate() {
		return template;
	}

	protected void setTemplate(File template) {
		this.template = template;
	}

	protected void setTemplate(String pathname) {
		setTemplate(new File(pathname));
	}

	/**
	 * @return the boleto
	 * 
	 * @since 0.2
	 */
	protected Boleto getBoleto() {
		return this.boleto;
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
		URL templateFromResource = null;

		if (boleto.getSacadorAvalista() != null) {
			templateFromResource = TEMPLATE_PADRAO_COM_SACADOR_AVALISTA;
		} else {
			templateFromResource = TEMPLATE_PADRAO_SEM_SACADOR_AVALISTA;
		}

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
			reader = new PdfReader(getTemplate().getAbsolutePath());
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
										 * Sets the document's compression to
										 * the new 1.5 mode with object streams
										 * and xref streams.
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
		setCodigoBanco();
		setLinhaDigitavel();
		setCedente();
		setAgenciaCondigoCedente();
		setEspecie();
		setQuantidade();
		setNossoNumero();
		setNumeroDocumento();
		setAbstractCPRFCedente();
		setDataVencimeto();
		setValorDocumento();
		setDescontoAbatimento();
		setOutraDeducao();
		setMoraMulta();
		setOutroAcrescimo();
		setInstrucaoAoSacado();
		setInstrucaoAoCaixa();
		setSacado();
		setLocalPagamento();
		setDataDocumento();
		setEspecieDoc();
		setAceite();
		setDataProcessamento();
		setSacadorAvalista();
		setCodigoBarra();
		setCarteira();
		setCamposExtra();
		setImagensNosCampos();
	}

	private void setCamposExtra() throws IOException, DocumentException {
//		if (isNotNull(boleto.getTextosExtras())) {
//			
//			for (String campo : boleto.getTextosExtras().keySet()) {
//				form.setField(campo, boleto.getTextosExtras().get(campo));
//			}
//		}
	}

	private void setCodigoBarra() throws DocumentException {

		// Montando o codigo de barras.
		BarcodeInter25 barCode = new BarcodeInter25();
		barCode.setCode(boleto.getCodigoBarras().write());
		barCode.setExtended(true);
		barCode.setBarHeight(40);
		barCode.setFont(null);
		barCode.setN(3);

	
		// FICHA DE COMPENSACAO
		PdfContentByte cb = null;

		// Verifcando se existe o field(campo) da imagem no template do boleto.
		float posCampoImgLogo[] = form.getFieldPositions("txtFcCodigoBarra");
		
		if (posCampoImgLogo != null) {
			RetanguloPDF field = new RetanguloPDF(posCampoImgLogo);
			
			cb = stamper.getOverContent(field.getPage());
			Image imgBarCode = barCode.createImageWithBarcode(cb, null, null);
			
			PDFUtil.changeField2Image(stamper, field, imgBarCode);
		}
	}

	private void setDataProcessamento() throws IOException, DocumentException {
		form.setField("txtFcDataProcessamento", DataUtil.formatarData(boleto.getDataProcessamento()));
	}

	private void setAceite() throws IOException, DocumentException {
		if (boleto.getIdentificacaoAceite() != null) {
			form.setField("txtFcAceite", boleto.getIdentificacaoAceite());
		}
	}

	private void setEspecieDoc() throws IOException, DocumentException {
		if (boleto.getEspecieTitulo() != null) {
			form.setField("txtFcEspecieDocumento", boleto.getEspecieTitulo().name());
		}
	}

	private void setDataDocumento() throws IOException, DocumentException {
		form.setField("txtFcDataDocumento", DataUtil.formatarData(boleto.getDataEmissao()));
	}

	private void setLocalPagamento() throws IOException, DocumentException {
		form.setField("txtFcLocalPagamento", (boleto.getLocalPagamento()));
	}

	private void setSacado() throws IOException, DocumentException {
		StringBuilder sb = new StringBuilder();
		Sacado sacado = boleto.getSacado();

		if (sacado.getNome() != null) {
			sb.append(String.format("%-50s", sacado.getNome()));
		}
		
		if (sacado.getCpfCnpj() != null) {
			sb.append(" ");
		
			if (sacado.isPessoaFisica()) {
				sb.append("CPF: ");
				sb.append(TextoUtil.formataNumeroCPF(sacado.getCpfCnpj()));
			} else if (sacado.isPessoaJuridica()) {
				sb.append("CNPJ: ");
				sb.append(TextoUtil.formataNumeroCNPJ(sacado.getCpfCnpj()));
			}
		}
		
		form.setField("txtRsSacado", sb.toString());
		form.setField("txtFcSacadoL1", sb.toString());

		sb.delete(0, sb.length());
		Endereco endereco = sacado.getEndereco();
		
		setEndereco(endereco, "txtFcSacadoL2", "txtFcSacadoL3", sb);
	}

	private void setSacadorAvalista() throws IOException, DocumentException {
		if (boleto.getSacadorAvalista() != null) {
			SacadorAvalista sacadorAvalista = boleto.getSacadorAvalista(); 
			StringBuilder sb = new StringBuilder();

			if (sacadorAvalista.getNome() != null) {
				sb.append(sacadorAvalista.getNome());
			}
			
			if (sacadorAvalista.getCpfCnpj() != null) {
				sb.append(", ");
				
				if (sacadorAvalista.isPessoaFisica()) {
					sb.append("CPF: ");
					sb.append(TextoUtil.formataNumeroCPF(sacadorAvalista.getCpfCnpj()));
				} else if (sacadorAvalista.isPessoaJuridica()) {
					sb.append("CNPJ: ");
					sb.append(TextoUtil.formataNumeroCNPJ(sacadorAvalista.getCpfCnpj()));
				}
			}
			
			form.setField("txtFcSacadorAvalistaL1", sb.toString());

			sb.delete(0, sb.length());
			Endereco endereco = sacadorAvalista.getEndereco();

			setEndereco(endereco, "txtFcSacadorAvalistaL2", "txtFcSacadorAvalistaL3", sb);
		}
	}
	
	private void setEndereco(Endereco endereco, String campoEndereco1, String campoEndereco2, StringBuilder sb) 
		throws IOException, DocumentException {
		
		if (endereco != null) {
			if (endereco.getBairro() != null) {
				sb.append(endereco.getBairro());
			}
			
			if (endereco.getCidade() != null) {
				sb.append(" - ");
				sb.append(endereco.getCidade());
			}
			
			if (endereco.getUf() != null) {
				sb.append(" - ");
				sb.append(endereco.getUf());
			}

			if (endereco.getCep() != null && !endereco.getCep().isEmpty()) {
				sb.append(" - ");
				sb.append(endereco.getCep());
			}

			form.setField(campoEndereco2, sb.toString());

			sb.delete(0, sb.length());
			
			if (endereco.getLogradouro() != null) {
				sb.append(endereco.getLogradouro());
			}

			if (endereco.getEndereco() != null) {
				if (endereco.getLogradouro() != null && !endereco.getLogradouro().isEmpty())
					sb.append(" ");
				sb.append(endereco.getEndereco());
			}

			if (endereco.getNumero() != null && !endereco.getNumero().isEmpty()) {
				sb.append(", n. ");
				sb.append(endereco.getNumero());
			}

			form.setField(campoEndereco1, sb.toString());
		}
	}

	private void setInstrucaoAoCaixa() throws IOException, DocumentException {
		form.setField("txtFcInstrucaoAoCaixa1", boleto.getInstrucao1());
		form.setField("txtFcInstrucaoAoCaixa2", boleto.getInstrucao2());
		form.setField("txtFcInstrucaoAoCaixa3", boleto.getInstrucao3());
		form.setField("txtFcInstrucaoAoCaixa4", boleto.getInstrucao4());
		form.setField("txtFcInstrucaoAoCaixa5", boleto.getInstrucao5());
		form.setField("txtFcInstrucaoAoCaixa6", boleto.getInstrucao6());
		form.setField("txtFcInstrucaoAoCaixa7", boleto.getInstrucao7());
		form.setField("txtFcInstrucaoAoCaixa8", boleto.getInstrucao8());
	}

	private void setMoraMulta() throws IOException, DocumentException {
		double total = boleto.getValorMulta() + boleto.getValorJurosMora();
		if (total != 0) {
			form.setField("txtRsMoraMulta", TextoUtil.formataValor(total));
			form.setField("txtFcMoraMulta", TextoUtil.formataValor(total));
		}
	}

	private void setInstrucaoAoSacado() throws IOException, DocumentException {
		if (boleto.getInstrucaoSacado() != null) {
			String[] texto = boleto.getInstrucaoSacado().split("\r\n");
			for (int i = 1; i <= 8; i++) {
				if (i < texto.length)
					form.setField("txtRsInstrucaoAoSacado" + i, texto[i-1]);
			}
		}
	}

	private void setOutroAcrescimo() throws IOException, DocumentException {
		double total = boleto.getValorIOF();
		if (total != 0) {
			form.setField("txtRsOutroAcrescimo", TextoUtil.formataValor(total));
			form.setField("txtFcOutroAcrescimo", TextoUtil.formataValor(total));
		}
	}

	private void setOutraDeducao() throws IOException, DocumentException {
		double total = boleto.getValorAbatimento();
		if (total != 0) {
			form.setField("txtRsOutraDeducao", TextoUtil.formataValor(total));
			form.setField("txtFcOutraDeducao", TextoUtil.formataValor(total));
		}
	}

	private void setDescontoAbatimento() throws IOException, DocumentException {
		double total = boleto.getValorDesconto();
		if (total != 0) {
			form.setField("txtRsDescontoAbatimento", TextoUtil.formataValor(total));
			form.setField("txtFcDescontoAbatimento", TextoUtil.formataValor(total));
		}
	}

	private void setValorDocumento() throws IOException, DocumentException {
		form.setField("txtRsValorDocumento", TextoUtil.formataValor(boleto.getValorBoleto()));
		form.setField("txtFcValorDocumento", TextoUtil.formataValor(boleto.getValorBoleto()));
	}

	private void setDataVencimeto() throws IOException, DocumentException {
		form.setField("txtRsDataVencimento", DataUtil.formatarData(boleto.getDataVencimento()));
		form.setField("txtFcDataVencimento", DataUtil.formatarData(boleto.getDataVencimento()));
	}

	private void setAbstractCPRFCedente() throws IOException, DocumentException {
		form.setField("txtRsCpfCnpj", 
				TextoUtil.formataNumeroCNPJ(boleto.getCedente().getCpfCnpj()));
	}

	private void setNumeroDocumento() throws IOException, DocumentException {
		form.setField("txtRsNumeroDocumento", String.valueOf(boleto.getNumeroDocumento()));
		form.setField("txtFcNumeroDocumento", String.valueOf(boleto.getNumeroDocumento()));
	}

	private void setCedente() throws IOException, DocumentException {
		form.setField("txtRsCedente", boleto.getCedente().getNome());
		form.setField("txtFcCedente", 
				String.format("%-50s CNPJ: %s", boleto.getCedente().getNome(),
						TextoUtil.formataNumeroCNPJ(boleto.getCedente().getCpfCnpj())));
	}
	
	private void setCarteira() throws IOException, DocumentException {
		if (boleto.getCarteira() != null) {
			form.setField("txtFcCarteira", boleto.getCarteira());
		}
	}	

	private void setQuantidade() throws IOException, DocumentException {
		form.setField("txtRsQuantidade", StringUtils.EMPTY);
		form.setField("txtFcQuantidade", StringUtils.EMPTY);
	}

	private void setEspecie() throws IOException, DocumentException {
		form.setField("txtRsEspecie", boleto.getTipoMoeda().name());
		form.setField("txtFcEspecie", boleto.getTipoMoeda().name());
	}

	private void setLinhaDigitavel() throws DocumentException, IOException {
		form.setField("txtRsLinhaDigitavel", boleto.getLinhaDigitavel().write());
		form.setField("txtFcLinhaDigitavel", boleto.getLinhaDigitavel().write());
	}

	private void setLogoBanco() throws MalformedURLException, IOException, DocumentException {
		ContaBancaria conta = boleto.getCedente().getContaBancaria();
		Image imgLogoBanco = null;

		if (conta.getBanco().getImagemLogo() != null) {
			imgLogoBanco = Image.getInstance(conta.getBanco().getImagemLogo(), null);
			setImageLogo(imgLogoBanco);
		} 
		else {
			URL url = this.getClass().getResource(Constantes.DIRETORIO_IMAGENS_BANCO
					+ String.format("%03d.png", conta.getBanco().getCodigo()));

			if (url == null) {
				url = this.getClass().getResource(Constantes.DIRETORIO_IMAGENS_BANCO
						+ String.format("%03d.jpg", conta.getBanco().getCodigo()));
			}
			
			if (url != null) {
				imgLogoBanco = Image.getInstance(url);

				if (imgLogoBanco != null) {
					conta.getBanco().setImagemLogo(ImageIO.read(url));
				}

				setImageLogo(imgLogoBanco);
			} 
			else {
				logger.info("Banco sem imagem definida. O nome da instituicao sera usado como logo.");

				form.setField("txtRsLogoBanco", conta.getBanco().getNome());
				form.setField("txtFcLogoBanco", conta.getBanco().getNome());
			}
		}
	}

//	private void setLogoConvenio() throws MalformedURLException, IOException, DocumentException {
//		Cedente cedente = boleto.getCedente();
//		Image imgLogoCedente = null;
//
//		URL url = this.getClass().getResource(Constantes.DIRETORIO_IMAGENS_CONVENIO
//				+ String.format("%d.gif", cedente.getConvenio()));
//
//		if (url != null) {
//			imgLogoCedente = Image.getInstance(url);
//
//			setImagemNoCampo("txtRsLogoEmpresa", imgLogoCedente);	
//		} 
//	}

	/**
	 * <p>
	 * Coloca as imagens dos campos no pdf de acordo com o nome dos campos do boleto atribuidos no map e templante.
	 * </p>
	 * 
	 * @throws DocumentException
	 * @throws IOException 
	 * 
	 * @since 0.2
	 */
	private void setImagensNosCampos() throws DocumentException, IOException {
//		if (isNotNull(boleto.getImagensExtras())) {
//			for (String campo : boleto.getImagensExtras().keySet()) {
//				setImagemNoCampo(campo, Image.getInstance(boleto.getImagensExtras().get(campo),null));
//			}
//		}
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
	private void setImagemNoCampo(String nomeDoCampo, Image imagem) throws DocumentException {
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
	 * Coloca a logo do passada na ficha de compensacao do boleto e no recibo do
	 * sacado.
	 * </p>
	 * 
	 * @param imgLogoBanco
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 */
	private void setImageLogo(Image imgLogoBanco) throws DocumentException {
		// RECIBO DO SACADO
		setImagemNoCampo("txtRsLogoBanco",imgLogoBanco);

		// FICHA DE COMPENSACAO
		setImagemNoCampo("txtFcLogoBanco",imgLogoBanco);	
	}

	private void setCodigoBanco() throws IOException, DocumentException {
		ContaBancaria conta = boleto.getCedente().getContaBancaria();
		
		form.setField("txtRsCodBanco", BancoUtil.retornarCodigoBancoBACEN(conta.getBanco()));
		form.setField("txtFcCodBanco", BancoUtil.retornarCodigoBancoBACEN(conta.getBanco()));
	}

	private void setAgenciaCondigoCedente() throws IOException, DocumentException {
		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);
		ContaBancaria conta = boleto.getCedente().getContaBancaria();

		if (conta.getAgencia() != null)
			sb.append(conta.getAgencia());

		if (conta.getDigitoAgencia() != null && StringUtils.isNotBlank(conta.getDigitoAgencia())) {
			sb.append(Constantes.HIFEN_SEPERADOR);
			sb.append(conta.getDigitoAgencia());
		}
		
		if (conta.getConta() != null) {
			sb.append(" / ");
			sb.append(conta.getConta());

			if (conta.getDigitoConta() != null && StringUtils.isNotBlank(conta.getDigitoConta())) {
				sb.append(Constantes.HIFEN_SEPERADOR);
				sb.append(conta.getDigitoConta());
			}
		}

		form.setField("txtRsAgenciaCodigoCedente", sb.toString());
		form.setField("txtFcAgenciaCodigoCedente", sb.toString());
	}

	private void setNossoNumero() throws IOException, DocumentException {
		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);

		if (boleto.getNossoNumero() != null) {
			sb.append(boleto.getNossoNumero());
		}

		form.setField("txtRsNossoNumero", sb.toString());
		form.setField("txtFcNossoNumero", sb.toString());
	}

	/**
	 * Exibe os valores de instancia.
	 * 
	 */
	@Override
	public String toString() {
		ToStringBuilder tsb = new ToStringBuilder(this);
		tsb.append(boleto);
		return tsb.toString();
	}
}
