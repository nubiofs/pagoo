package br.com.infosolo.cobranca.boleto.guia;

import java.awt.Image;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.infosolo.cobranca.boleto.TipoSeguimento;
import br.com.infosolo.cobranca.boleto.campolivre.CampoLivre;
import br.com.infosolo.cobranca.boleto.campolivre.CampoLivreFabrica;
import br.com.infosolo.cobranca.boleto.campolivre.CampoLivreUtil;
import br.com.infosolo.cobranca.boleto.campolivre.NaoSuportadoBancoExecao;
import br.com.infosolo.cobranca.boleto.campolivre.NaoSuportadoCampoLivreExcecao;
import br.com.infosolo.cobranca.dominio.boleto.Cedente;
import br.com.infosolo.comum.util.DataUtil;
import br.com.infosolo.comum.util.Logger;
import br.com.infosolo.comum.util.TextoUtil;

/**
 * <p>
 * É a representação do documento Guia que por sua vez, representa um meio
 * para a arrecadação de fundos.
 * </p>
 * 
 * <p>
 * A classe encapsula os atributos integrantes e as funcionalidades inerentes 
 * à construção de tal documento.
 * </p>
 * 
 */
public final class Guia {
	private static final long serialVersionUID = 4436063640418293021L;
	
	private static Logger logger = new Logger(Guia.class);
	
	

	/**
	 * @see Arrecadacao
	 */
	private Arrecadacao arrecadacao;

	private Arrecadacao arrecadacao2;

	private Cedente cedente;

	private Cedente cedente2;

	/**
	 * @see #setDataDeProcessamento(Date)
	 */
	private Date dataEmissao;
	
	/**
	 * @see CodigoDeBarras
	 */
	private CodigoDeBarras codigoDeBarras;

	private CodigoDeBarras codigoDeBarras2;

	/**
	 * @see LinhaDigitavel
	 */
	private LinhaDigitavel linhaDigitavel;

	private LinhaDigitavel linhaDigitavel2;

	/**
	 * @see CampoLivreGuia
	 */
	private CampoLivre campoLivre;

	private CampoLivre campoLivre2;

	/**
	 * @see #setLocalPagamento(String)
	 */
	private String localPagamento;
	
	/**
	 * @see #setInstrucaoAoSacado(String)
	 */
	private String instrucoes;
	
	/**
	 * @see #setTextosExtras(Map)
	 */
	private Map<String, String> textosExtras; 
	
	/**
	 *<p>
	 * Map com nome do campo e imagem para este campo.
	 *</p>
	 */
	private Map<String, Image> imagensExtras; 
	
	/**
	 * Apenas cria um instâcia de guia com os dados nulos. 
	 */
	public Guia() {
		super();
	}
	
	/**
	 * Cria uma guia pronto para ser gerada.
	 * 
	 * @param arrecadacao
	 * @throws NotSupportedBancoException 
	 * @throws NotSupportedCampoLivreException 
	 */
	public Guia(Arrecadacao arrecadacao, Arrecadacao arrecadacao2) throws IllegalArgumentException, 
			NaoSuportadoBancoExecao, NaoSuportadoCampoLivreExcecao {

		logger.debug("Instanciando uma guia...");
		
		logger.debug("Arrecadaca instance : " + arrecadacao);
		
		if (arrecadacao != null) {
			this.setArrecadacao(arrecadacao);
			this.setCampoLivre(CampoLivreFabrica.create(arrecadacao), arrecadacao.getOrgaoRecebedor().getTipoSeguimento());

			if (arrecadacao2 != null) {
				this.setArrecadacao2(arrecadacao2);
				this.setCampoLivre2(CampoLivreFabrica.create(arrecadacao2), arrecadacao2.getOrgaoRecebedor().getTipoSeguimento());
			}

			this.load();

			logger.debug("Guia instance : " + this);
		}
		else {
			IllegalArgumentException e = new IllegalArgumentException("Guia nula!");
			logger.error("Valor não permitido!", e);
			throw e;
		}
		
		logger.debug("Guia Instanciado : " + this);

	}

	/**
	 * @param arrecadacao
	 * @param campoLivre
	 */
//	public Guia(Arrecadacao arrecadacao, CampoLivre campoLivre) {
//		super();
//
//		try {
//			TextoUtil.checkNotNull(arrecadacao);
//			TextoUtil.checkNotNull(arrecadacao.getOrgaoRecebedor());
//			TextoUtil.checkNotNull(arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
//			TextoUtil.checkNotNull(campoLivre);	
//		} catch (Exception e) {
//			throw new IllegalArgumentException(e);
//		}
//
//		if(logger.isTraceEnabled())
//			logger.trace("Instanciando guia... ");
//		
//		if(logger.isDebugEnabled())
//			logger.debug("Arrecadacao instance : " + arrecadacao);
//		
//		if(logger.isDebugEnabled())
//			logger.debug("campoLivre instance : " + campoLivre);
//				
//		this.setArrecadacao(arrecadacao);
//		this.setCampoLivre(campoLivre, arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
//		this.load();
//			
//		if(logger.isDebugEnabled())
//			logger.debug("Arrecadaca instance : "+this);
//			
//		if(logger.isDebugEnabled() || logger.isTraceEnabled())
//			logger.trace("Guia instanciada : " + this);
//		
//	}

	private void load() {
		dataEmissao = new Date();
		logger.info("Data de Processamento da Guia: " + DataUtil.formatarData(dataEmissao));

		logger.info("Campo Livre: " + campoLivre.write());
		
		codigoDeBarras = new CodigoDeBarras(arrecadacao, campoLivre);
		logger.info("Codigo de Barras: " + codigoDeBarras.write());
		
		linhaDigitavel = new LinhaDigitavel(codigoDeBarras);
		
		if (arrecadacao2 != null) {
			codigoDeBarras2 = new CodigoDeBarras(arrecadacao2, campoLivre2);
			logger.info("Codigo de Barras 2: " + codigoDeBarras2.write());
			
			linhaDigitavel2 = new LinhaDigitavel(codigoDeBarras2);
		}
	}
	
	public CampoLivre getCampoLivre() {
		return campoLivre;
	}

	public void setCampoLivre(CampoLivre campoLivre, TipoSeguimento tipoSeguimento) {		
		TextoUtil.checkNotNull(campoLivre);
		//TextoUtil.checkNotNull(tipoSeguimento);		
		
		CampoLivreUtil.validar(campoLivre, tipoSeguimento);
		this.campoLivre = campoLivre;
	}

	public void setCampoLivre2(CampoLivre campoLivre2, TipoSeguimento tipoSeguimento) {
		TextoUtil.checkNotNull(campoLivre2);
		
		CampoLivreUtil.validar(campoLivre2, tipoSeguimento);
		this.campoLivre2 = campoLivre2;
	}

	public CampoLivre getCampoLivre2() {
		return campoLivre2;
	}

	/**
	 * @return Arrecadacao
	 */
	public Arrecadacao getArrecadacao() {
		return arrecadacao;
	}

	/**
	 * @param arrecadacao
	 */
	public void setArrecadacao(Arrecadacao arrecadacao) {
		this.arrecadacao = arrecadacao;
	}

	public void setArrecadacao2(Arrecadacao arrecadacao2) {
		this.arrecadacao2 = arrecadacao2;
	}

	public Arrecadacao getArrecadacao2() {
		return arrecadacao2;
	}

	/**
	 * @see #getDataDeProcessamento()
	 * 
	 * @return the dataDeProcessamento
	 */
	public Date getDataEmissao() {
		return dataEmissao;
	}

	/**
	 * <p>
	 * Data de emissão do boleto de cobrança.
	 * </p>
	 * 
	 * @param dataDeProcessamento the dataDeProcessamento to set
	 */
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	/**
	 * @return the codigoDeBarras
	 */
	public CodigoDeBarras getCodigoDeBarras() {
		return codigoDeBarras;
	}

	/**
	 * @param codigoDeBarras the codigoDeBarras to set
	 */
	public void setCodigoDeBarras(CodigoDeBarras codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public void setCodigoDeBarras2(CodigoDeBarras codigoDeBarras2) {
		this.codigoDeBarras2 = codigoDeBarras2;
	}

	public CodigoDeBarras getCodigoDeBarras2() {
		return codigoDeBarras2;
	}

	/**
	 * @return the linhaDigitavel
	 */
	public LinhaDigitavel getLinhaDigitavel() {
		return linhaDigitavel;
	}

	/**
	 * @param linhaDigitavel the linhaDigitavel to set
	 */
	public void setLinhaDigitavel(LinhaDigitavel linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}

	public void setLinhaDigitavel2(LinhaDigitavel linhaDigitavel2) {
		this.linhaDigitavel2 = linhaDigitavel2;
	}

	public LinhaDigitavel getLinhaDigitavel2() {
		return linhaDigitavel2;
	}

	/**
	 * @see #setLocalPagamento(String)
	 * 
	 * @return String local de pagamento
	 */
	public String getLocalPagamento() {
		return localPagamento;
	}

	/**
	 * <p>
	 * Possíveis locais para pagamento.
	 * </p>
	 * <p>
	 * Exemplo: <em>Pagável preferencialmente na Rede X ou em qualquer Banco até 
	 * o Vencimento.</em>
	 * </p>
	 * 
	 * @param localPagamento1 the localPagamento1 to set
	 */
	public void setLocalPagamento(String localPagamento1) {
		this.localPagamento = localPagamento1;
	}

	public String getInstrucoes() {
		return instrucoes;
	}

	public void setInstrucoes(String instrucoes) {
		this.instrucoes = instrucoes;
	}

	public Map<String, String> getTextosExtras() {
		
		return this.textosExtras;
	}

	public void setTextosExtras(Map<String,String> textosExtras) {

		this.textosExtras = textosExtras;
	}
	
	public void addTextosExtras(String nome, String valor) {
		
		if (getTextosExtras() == null) {
			setTextosExtras(new HashMap<String, String>());
		}
		
		getTextosExtras().put(nome, valor);
	}
	
	public Map<String, Image> getImagensExtras() {
		return this.imagensExtras;
	}

	public void setImagensExtras(Map<String,Image> imagensExtras) {
		this.imagensExtras = imagensExtras;
	}
	
	public void addImagensExtras(String fieldName, Image image) {
		if (getImagensExtras() == null) {
			setImagensExtras(new HashMap<String, Image>());
		}
		
		getImagensExtras().put(fieldName, image);
	}

	public void setCedente(Cedente cedente) {
		this.cedente = cedente;
	}

	public Cedente getCedente() {
		return cedente;
	}

	public void setCedente2(Cedente cedente2) {
		this.cedente2 = cedente2;
	}

	public Cedente getCedente2() {
		return cedente2;
	}

	@Override
	public String toString() {
		return TextoUtil.toString(this);
	}

}
