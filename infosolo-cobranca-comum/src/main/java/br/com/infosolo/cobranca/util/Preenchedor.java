package br.com.infosolo.cobranca.util;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import br.com.infosolo.comum.util.TextoUtil;

/**
 * <p>
 * Preenchedor de caracteres generico. É utilizado para preencher objetos
 * <code>String</code>, tanto da esquerda para a direita como da direita para
 * esquerda, com o objeto generico ate o tamanho especificado. Caso o tamanho
 * especificado seja <strong>menor</strong> ou <strong>igual</strong> a 0
 * (ZERO), este valor sera desconsiderado e nada sera preenchido.
 * </p>
 * <p>
 * É utilizado o metodo <code>toString()</code> do objeto preenchedor.
 * </p>
 * <p>
 * Exemplo:<br />
 * 
 * <pre>
 * Filler&lt;Integer&gt; filler = new Filler(new Integer(10), SideToFill.LEFT);
 * String outPut = filler.fill(&quot;TESTE&quot;, 8);
 * 
 * outPut -&gt; &quot;101TESTE&quot;
 * </pre>
 * 
 * </p>
 * 
 */
public class Preenchedor<G> implements Serializable {
	private static final long serialVersionUID = -3996934478582358340L;

	/**
	 * <p>
	 * Filler padrao para preenchimento com zeros a esquerda.
	 * </p>
	 */
	public static final Preenchedor<Integer> ZERO_ESQUERDA = new Preenchedor<Integer>(0, LadoPreencher.ESQUERDA);

	/**
	 * <p>
	 * Filler padrao para preenchimento com zeros a direita.
	 * </p>
	 */
	public static final Preenchedor<Integer> ZERO_DIREITA = new Preenchedor<Integer>(0, LadoPreencher.DIREITA);

	/**
	 * <p>
	 * Filler padrao para preenchimento com espaços em branco a esquerda.
	 * </p>
	 */
	public static final Preenchedor<String> ESPACO_BRANCO_ESQUERDA = new Preenchedor<String>(TextoUtil.ESPACO_BRANCO, LadoPreencher.ESQUERDA);

	/**
	 * <p>
	 * Filler padrao para preenchimento com espacos em branco a direita.
	 * </p>
	 */
	public static final Preenchedor<String> ESPACO_BRANCO_DIREITA = new Preenchedor<String>(TextoUtil.ESPACO_BRANCO, LadoPreencher.DIREITA);

	/**
	 * <p>
	 * Tamanho do preenchimento
	 * </p>
	 */
	private G preecherCom;

	/**
	 * <p>
	 * Lado a ser prenchido
	 * </p>
	 */
	private LadoPreencher ladoPreencher;

	/**
	 * <p>
	 * Cria um preenchedor com preenchimento e lado a preencher.
	 * </p>
	 * 
	 * @param fillWith
	 *            preenchimento
	 * @param sideToFill
	 *            lado a preencher
	 * @since 0.2
	 */
	public Preenchedor(G fillWith, LadoPreencher sideToFill) {
		setFillWith(fillWith);
		setSideToFill(sideToFill);
	}

	/**
	 * <p>
	 * Tammanho do espaço de preenchimento em caracteres..
	 * </p>
	 * 
	 * @return
	 * 
	 * @since 0.2
	 */

	public G getFillWith() {
		return preecherCom;
	}

	/**
	 * @param fillWith
	 *            valor que preenche o real valor do campo
	 * @since 0.2
	 */
	public void setFillWith(G fillWith) {
		if (fillWith != null)
			this.preecherCom = fillWith;
		else
			throw new IllegalArgumentException("Filler invalido [ " + fillWith + " ]!");
	}

	/**
	 * @return enum SideToFill
	 * @since 0.2
	 */
	public LadoPreencher getSideToFill() {
		return ladoPreencher;
	}

	/**
	 * @param sideToFill
	 *            enum com a informacao de qual lado a ser preenchido
	 */
	public void setSideToFill(LadoPreencher ladoPreencher) {
		if (ladoPreencher != null)
			this.ladoPreencher = ladoPreencher;
		else
			throw new IllegalArgumentException("Lado invalido [ " + ladoPreencher + " ]!");
	}

	/**
	 * <p>
	 * Preenche o campo com o caracter especificado e no lado especificado.
	 * </p>
	 * <p>
	 * Exemplo: <br/>
	 * Se <code>sideToFill == SideToFill.LEFT</code>, o caracter especificado
	 * sera adicionado à String no lado esquerdo ate que o campo fique com o
	 * tamanho que foi definido.
	 * </p>
	 * 
	 * @param toFill
	 *            String a ser preenchida
	 * @param length
	 *            tamanho maximo que a String deve ter depois de preenchida
	 * @return Nova String preenchida de acordo com o preenchedor do objeto ate
	 *         o tamanho especificado
	 * @since 0.2
	 */
	public String fill(String toFill, int length) {
		String str = null;

		switch (ladoPreencher) {
			case ESQUERDA:
				str = fillLeft(toFill, length);
				break;
	
			case DIREITA:
				str = fillRight(toFill, length);
				break;
		}

		return str;
	}

	/**
	 * 
	 * <p>
	 * Executa o metodo <code>fill(String, int)</code> passando o parametro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param toFill
	 *            Valor a ser preenchido
	 * @param length
	 *            tamanho maximo que o valor deve ter depois de preenchido
	 * @return Nova String preenchida de acordo com o preenchedor do objeto ate
	 *         o tamanho especificado
	 * 
	 * @see Preenchedor#fill(String, int)
	 * @since 0.2
	 */
	public String fill(long toFill, int length) {
		return fill(String.valueOf(toFill), length);
	}

	/**
	 * 
	 * <p>
	 * Executa o metodo <code>fill(String, int)</code> passando o parametro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param toFill
	 *            Valor a ser preenchido
	 * @param length
	 *            tamanho maximo que o valor deve ter depois de preenchido
	 * @return Nova String preenchida de acordo com o preenchedor do objeto ate
	 *         o tamanho especificado
	 * 
	 * @see Preenchedor#fill(String, int)
	 * @since 0.2
	 */
	public String fill(int toFill, int length) {
		return fill(String.valueOf(toFill), length);
	}

	/**
	 * 
	 * <p>
	 * Executa o metodo <code>fill(String, int)</code> passando o parametro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param toFill
	 *            Valor a ser preenchido
	 * @param length
	 *            tamanho maximo que o valor deve ter depois de preenchido
	 * @return Nova String preenchida de acordo com o preenchedor do objeto ate
	 *         o tamanho especificado
	 * 
	 * @see Preenchedor#fill(String, int)
	 * @since 0.2
	 */
	public String fill(short toFill, int length) {
		return fill(String.valueOf(toFill), length);
	}

	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param toFill
	 *            Valor a ser preenchido
	 * @param length
	 *            tamanho máximo que o valor deve ter depois de preenchido
	 * @return Nova String preenchida de acordo com o preenchedor do objeto até
	 *         o tamanho especificado
	 * 
	 * @see Preenchedor#fill(String, int)
	 * @since 0.2
	 */
	public String fill(byte toFill, int length) {
		return fill(String.valueOf(toFill), length);
	}

	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param toFill
	 *            Valor a ser preenchido
	 * @param length
	 *            tamanho máximo que o valor deve ter depois de preenchido
	 * @return Nova String preenchida de acordo com o preenchedor do objeto até
	 *         o tamanho especificado
	 * 
	 * @see Preenchedor#fill(String, int)
	 * @since 0.2
	 */
	public String fill(char toFill, int length) {
		return fill(String.valueOf(toFill), length);
	}

	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param toFill
	 *            Valor a ser preenchido
	 * @param length
	 *            tamanho máximo que o valor deve ter depois de preenchido
	 * @return Nova String preenchida de acordo com o preenchedor do objeto até
	 *         o tamanho especificado
	 * 
	 * @see Preenchedor#fill(String, int)
	 * @since 0.2
	 */
	public String fill(double toFill, int length) {
		return fill(String.valueOf(toFill), length);
	}

	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param toFill
	 *            Valor a ser preenchido
	 * @param length
	 *            tamanho máximo que o valor deve ter depois de preenchido
	 * @return Nova String preenchida de acordo com o preenchedor do objeto até
	 *         o tamanho especificado
	 * 
	 * @see Preenchedor#fill(String, int)
	 * @since 0.2
	 */
	public String fill(float toFill, int length) {
		return fill(String.valueOf(toFill), length);
	}

	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>toFill.toString()</code>. <br/>
	 * </p>
	 * <p>
	 * Caso <code>toFill</code> seja <code>null</code>, o método
	 * <code>fill(String, int)</code> receberá uma String nula como parâmetro.
	 * </p>
	 * 
	 * @param toFill
	 *            Valor a ser preenchido
	 * @param length
	 *            tamanho máximo que o valor deve ter depois de preenchido
	 * @return Nova String preenchida de acordo com o preenchedor do objeto até
	 *         o tamanho especificado
	 * 
	 * @see Preenchedor#fill(String, int)
	 * @since 0.2
	 */
	public String fill(Object toFill, int length) {
		String toFillTemp = null;

		if (toFill != null) {
			toFillTemp = toFill.toString();
		}

		return fill(toFillTemp, length);
	}

	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>toFill.write()</code>. <br/>
	 * </p>
	 * <p>
	 * Caso <code>toFill</code> seja <code>null</code>, o método
	 * <code>fill(String, int)</code> receberá uma String nula como parâmetro.
	 * </p>
	 * 
	 * @param toFill
	 *            Valor a ser preenchido
	 * @param length
	 *            tamanho máximo que o valor deve ter depois de preenchido
	 * @return Nova String preenchida de acordo com o preenchedor do objeto até
	 *         o tamanho especificado
	 * 
	 * @see Preenchedor#fill(String, int)
	 * @since 0.2
	 */
	public String fill(TextoStream toFill, int length) {
		String toFillTemp = null;

		if (toFill != null)
			toFillTemp = toFill.write();

		return fill(toFillTemp, length);
	}

	/**
	 * <p>
	 * Preenche a String a direita com valor do atributo <tt>"fillWith".</tt>
	 * </p>
	 * 
	 * @param toFill
	 *            Valor a ser preenchido
	 * @param length
	 *            tamanho máximo que o valor deve ter depois de preenchido
	 * @return Nova String preenchida de acordo com o preenchedor do objeto até
	 *         o tamanho especificado
	 * @since 0.2
	 */
	private String fillRight(String toFill, int length) {
		return StringUtils.rightPad(toFill, length, preecherCom.toString());
	}

	/**
	 * <p>
	 * Preenche a String a esquerda com valor do atributo <tt>"fillWith".</tt>
	 * </p>
	 * 
	 * @param toFill
	 *            Valor a ser preenchido
	 * @param length
	 *            tamanho máximo que o valor deve ter depois de preenchido
	 * @return Nova String preenchida de acordo com o preenchedor do objeto até
	 *         o tamanho especificado
	 * @since 0.2
	 */
	private String fillLeft(String toFill, int length) {
		return StringUtils.leftPad(toFill, length, preecherCom.toString());
	}

	/**
	 * <p>
	 * Lados para preencher.
	 * </p>
	 * 
	 * @since 0.2
	 */
	public enum LadoPreencher {
		ESQUERDA, DIREITA;
	}

}
