package br.com.infosolo.cobranca.boleto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import br.com.infosolo.cobranca.boleto.campolivre.CampoLivre;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.ContaBancaria;
import br.com.infosolo.cobranca.util.AbstractLinhaDeCampos;
import br.com.infosolo.cobranca.util.BancoUtil;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.cobranca.util.Preenchedor;
import br.com.infosolo.comum.util.Logger;


/**
 * 
 * E um numero unico para cada Boleto composto dos seguintes campos:
 * 
 * <br />
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="85%" id="composicao"> <thead>
 * <tr>
 * <th>Posicao </th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteudo</th>
 * </tr>
 * </thead>
 * <tr>
 * <td>01-03</td>
 * <td>3</td>
 * <td>9(3)</td>
 * <td>Identificacao do banco </td>
 * </tr>
 * <tr>
 * <td>04-04 </td>
 * <td>1 </td>
 * <td>9 </td>
 * <td>Codigo moeda (9-Real) </td>
 * </tr>
 * <tr>
 * <td>05-05 </td>
 * <td>1 </td>
 * <td>9 </td>
 * <td>Digito verificador do composicao de barras (DV) </td>
 * </tr>
 * <tr>
 * <td>06-09 </td>
 * <td>4 </td>
 * <td>9(4)</td>
 * <td>Posicaes 06 a 09 – fator de vencimento</td>
 * </tr>
 * <tr>
 * <td>10-19</td>
 * <td>10</td>
 * <td>9(08)v99</td>
 * <td>Posições 10 a 19 – valor nominal do titulo&nbsp;</td>
 * </tr>
 * <tr>
 * <td>20-44 </td>
 * <td>25 </td>
 * <td>9(25) </td>
 * <td>Field livre – utilizado de acordo com a especificacao interna do banco
 * emissor</td>
 * </tr>
 * </table>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public final class CodigoBarras extends AbstractLinhaDeCampos {
	private static final long serialVersionUID = 748913164143978133L;
	
	private static Logger logger = new Logger(CodigoBarras.class);
	
	/**
	 * Tamanho do codigo sem o digito verificador (Digito de Autoconferencia)
	 */
	private static final int TAMANHO_SEM_DV = 43;

	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 6;
	
	/**
	 * 
	 */
	private static final Integer STRING_LENGTH = 44;

	/**
	 * Data Base de 07.10.1997, data usada pela FEBRABAN para realizar o calculo
	 * do fator de vencimento.
	 * 
	 * @see BancoUtil#calcularFatorDeVencimento(Date)
	 */
	public static final Date DATA_BASE_DO_FATOR_DE_VENCIMENTO = new GregorianCalendar(
			1997, Calendar.OCTOBER, 7).getTime();

	
	/**
	 * Codigo do Banco.
	 */
	private Campo<String> codigoDoBanco;
	
	/**
	 * Codigo da moeda usada no boleto.
	 */
	private Campo<Integer> codigoDaMoeda;
	
	/**
	 * Mecanismo de autenticacao usado no composicao de barras.
	 * 
	 */
	private Campo<Integer> digitoVerificadorGeral;
	
	/**
	 * Representa a quantidade de dias decorridos da data base a data de
	 * vencimento do titulo.
	 * 
	 * @see BancoUtil#calcularFatorDeVencimento(Date)
	 */
	private Campo<Integer> fatorDeVencimento;
	
	/**
	 * Valor do titulo.
	 */
	private Campo<BigDecimal> valorNominalDoTitulo;
	
	/**
	 * @see br.com.infosolo.cobranca.boleto.campolivre.CampoLivreGuia.jrimum.bopepo.campolivre.CampoLivre
	 */
	private Campo<String> campoLivre;
	
	/**
	 * <p>
	 * Cria um Codigo de Barras a partir do titulo e campo livre passados.
	 * </p>
	 * 
	 * @param titulo
	 * @param campoLivre
	 * 
	 * @see CampoLivre
	 */
	public CodigoBarras(Boleto boleto, CampoLivre campoLivre) {
		super(FIELDS_LENGTH ,STRING_LENGTH);
		
		this.codigoDoBanco = new Campo<String>("0", 3, Preenchedor.ZERO_ESQUERDA );
		this.codigoDaMoeda = new Campo<Integer>(0, 1, Preenchedor.ZERO_ESQUERDA);
		this.digitoVerificadorGeral = new Campo<Integer>(0, 1, Preenchedor.ZERO_ESQUERDA);
		this.fatorDeVencimento = new Campo<Integer>(0, 4, Preenchedor.ZERO_ESQUERDA);
		this.valorNominalDoTitulo = new Campo<BigDecimal>(new BigDecimal(0), 10, Preenchedor.ZERO_ESQUERDA);
		this.campoLivre = new Campo<String>(StringUtils.EMPTY, 25);
		
		add(this.codigoDoBanco);
		add(this.codigoDaMoeda);
		add(this.digitoVerificadorGeral);
		add(this.fatorDeVencimento);
		add(this.valorNominalDoTitulo);
		add(this.campoLivre);
	
		ContaBancaria contaBancaria = boleto.getCedente().getContaBancaria();
		
		this.codigoDoBanco.setValue(String.format("%03d", contaBancaria.getBanco().getCodigo()));
		this.codigoDaMoeda.setValue(boleto.getTipoMoeda().getCodigo());
		
		//Was here DigitoVerificador 
		//But wait
		this.calcularSetarFatorDeVencimento(boleto.getDataVencimento());
		
		BigDecimal valorBoleto = new BigDecimal(boleto.getValorBoleto());
		valorBoleto.setScale(2, RoundingMode.HALF_UP);
		
		this.valorNominalDoTitulo.setValue(valorBoleto);
		this.campoLivre.setValue(campoLivre.write());
		
		// Preparando o conjunto de informacoes que sera a base para o calculo
		// do digito verificador, conforme normas da FEBRABAN.
		StringBuilder numeroCodigoBarras = new StringBuilder(this.codigoDoBanco.write())
			.append(this.codigoDaMoeda.write())
			.append(this.fatorDeVencimento.write())
			.append(this.valorNominalDoTitulo.write())
			.append(this.campoLivre.write());

		// Realizando o calculo digito verificador e em seguida armazenando 
		// a informacao no campo "digitoVerificadorGeral".
		this.digitoVerificadorGeral.setValue(calcularDVCodigoBarras(numeroCodigoBarras.toString()));

		//logger.info("Codigo de Barras: " + numeroCodigoBarras + " Tamanho: " + numeroCodigoBarras.length());
		logger.info("Digito Verificador Geral do Cod. Barras: " + digitoVerificadorGeral.getValue());

		numeroCodigoBarras.append(this.digitoVerificadorGeral.write());
		logger.info("Codigo de Barras: " + numeroCodigoBarras + " (Tamanho: " + numeroCodigoBarras.length() + ")");
	}

	/**
	 * <p>
	 * Representa a quantidade de dias decorridos da data base a data de
	 * vencimento do titulo.
	 * </p>
	 * <p>
	 * E o resultado da subtracao entre a data do vencimento do titulo e a DATA
	 * BASE, fixada em 07.10.1997 (03.07.2000 retrocedidos 1000 dias do inicio
	 * do processo).
	 * </p>
	 * <p>
	 * Os bloquetos de cobranca emitidos a partir de primeiro de setembro de
	 * 2000 devem conter essas caracteristicas, para que quando forem capturados
	 * pela rede bancaria, os sistemas facam a operacao inversa, ou seja,
	 * adicionar a data base o fator de vencimento capturado, obtendo, dessa
	 * forma, a data do vencimento do bloqueto.
	 * </p>
	 * 
	 * @param vencimento
	 */
	private void calcularSetarFatorDeVencimento(Date vencimento) {
		fatorDeVencimento.setValue(BancoUtil.calcularFatorDeVencimento(vencimento));
	}

	/**
	 * @return the codigoDoBanco
	 */
	Campo<String> getCodigoDoBanco() {
		return codigoDoBanco;
	}

	/**
	 * @param codigoDoBanco the codigoDoBanco to set
	 */
	void setCodigoDoBanco(Campo<String> codigoDoBanco) {
		this.codigoDoBanco = codigoDoBanco;
	}

	/**
	 * @return the codigoDaMoeda
	 */
	Campo<Integer> getCodigoDaMoeda() {
		return codigoDaMoeda;
	}

	/**
	 * @param codigoDaMoeda the codigoDaMoeda to set
	 */
	void setCodigoDaMoeda(Campo<Integer> codigoDaMoeda) {
		this.codigoDaMoeda = codigoDaMoeda;
	}

	/**
	 * @return the digitoVerificadorGeral
	 */
	Campo<Integer> getDigitoVerificadorGeral() {
		return digitoVerificadorGeral;
	}

	/**
	 * @param digitoVerificadorGeral the digitoVerificadorGeral to set
	 */
	void setDigitoVerificadorGeral(Campo<Integer> digitoVerificadorGeral) {
		this.digitoVerificadorGeral = digitoVerificadorGeral;
	}

	/**
	 * @return the fatorDeVencimento
	 */
	Campo<Integer> getFatorDeVencimento() {
		return fatorDeVencimento;
	}

	/**
	 * @param fatorDeVencimento the fatorDeVencimento to set
	 */
	void setFatorDeVencimento(Campo<Integer> fatorDeVencimento) {
		this.fatorDeVencimento = fatorDeVencimento;
	}

	/**
	 * @return the valorNominalDoTitulo
	 */
	Campo<BigDecimal> getValorNominalDoTitulo() {
		return valorNominalDoTitulo;
	}

	/**
	 * @param valorNominalDoTitulo the valorNominalDoTitulo to set
	 */
	void setValorNominalDoTitulo(Campo<BigDecimal> valorNominalDoTitulo) {
		this.valorNominalDoTitulo = valorNominalDoTitulo;
	}

	/**
	 * @return the campoLivre
	 */
	Campo<String> getCampoLivre() {
		return campoLivre;
	}

	/**
	 * @param campoLivre the campoLivre to set
	 */
	void setCampoLivre(Campo<String> campoLivre) {
		this.campoLivre = campoLivre;
	}
	
	/**
	 * <p>
	 * Logica de calculo do digito verificador do ccdigo de barras de um boleto.<br />
	 * A logica funciona da seguinte forma:
	 * </p>
	 * <p>
	 * Utilizando-se o modulo 11, considerando-se os 43 digitos que compoem o codigo
	 * de barras, ja excluida a 5.a posicao (posicao do digito verificador), segue-se
	 * o procedimento abaixo:
	 * </p>
	 * <p>
	 * Calcula-se o digito verificador atraves da expressao <code>DV = 11 - R</code>
	 * , onde R é o resultado do calculo do modulo.<br />
	 * Observacao: O digito verificador sera 1 para os restos (resultado do modulo):
	 * 0 , 10 ou 1 (zero, dez, um).
	 * </p>
	 * <p>
	 * Obs.: A rotina de modulo utilizada é o modulo 11.
	 * </p>
	 * 
	 * @see Modulo
	 * 
	 */
	public static int calcularDVCodigoBarras(String numero) throws IllegalArgumentException {
		int dv = 0;
		int resto = 0;
		
		Modulo modulo11 = new Modulo(Modulo.MODULO11);

		if (StringUtils.isNotBlank(numero) && StringUtils.isNumeric(numero)
				&& (numero.length() == TAMANHO_SEM_DV)) {

			// Realizando o calculo do digito verificador utilizando modulo 11.
			// Obtendo o resto da divisao por 11.
			resto = modulo11.calcule(numero);

			// Seguindo as especificacoes da FEBRABAN, caso o resto seja
			// (0), (1) ou (10), sera atribuido (1) ao digito verificador.
			if ((resto == 0) || (resto == 1) || (resto == 10))
				dv = 1;
			// Caso contrario, dv = 11 - resto.
			else
				dv = modulo11.valor() - resto;

		} else {
			throw new IllegalArgumentException("O codigo de barras " + "[ "
					+ numero + " ] deve conter apenas numeros e "
					+ TAMANHO_SEM_DV + " caracteres.");
		}

		return dv;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
