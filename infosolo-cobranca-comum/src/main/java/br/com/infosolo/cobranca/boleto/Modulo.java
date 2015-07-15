package br.com.infosolo.cobranca.boleto;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Representa o módulo no contexto de autenticação, ou seja, uma rotina que
 * auxilia no cálculo do dígito verificador.
 * </p>
 * <p>
 * As rotinas tradicionais são Módulo 10 e Módulo 11.
 * </p>
 * 
 */
public class Modulo {
	/**
	 * <p>
	 * Mensagem da exceção lançada no método calcular.
	 * </p>
	 */
	private static final String O_ARGUMENTO_DEVE_CONTER_APENAS_NUMEROS = "O argumento deve conter apenas n�meros !";

	/**
	 *<p>
	 * Valor inteiro do módulo 10
	 * </p>
	 */
	public static final int MODULO10 = 10;

	/**
	 *<p>
	 * Valor inteiro do módulo 11
	 * </p>
	 */
	public static final int MODULO11 = 11;

	/**
	 *<p>
	 * Tipo do m�dulo
	 * </p>
	 */
	private int mod;

	/**
	 *<p>
	 * Valor inteiro do "peso" mínimo mutiplicador utilizado no cálculo do
	 * m�dulo.
	 * </p>
	 */
	private int limiteMaximo;

	/**
	 *<p>
	 * Valor inteiro do "peso" máximo mutiplicador utilizado no cálculo do
	 * m�dulo.
	 * </p>
	 */
	private int limiteMinimo;

	/**
	 * <p>
	 * Inicializa um módulo com valores default.
	 * </p>
	 * 
	 * @param mod
	 *            tipo do módulo
	 * @see #initDefault
	 * @see #calcule(String)
	 * @since 0.2
	 */
	public Modulo(int mod) {
		super();

		this.mod = mod;
		initDefault();
	}

	/**
	 * <p>
	 * Inicializa um m�dulo com o tipo definido com valores default.
	 * </p>
	 * 
	 * @param limiteMaximo
	 *            peso m�ximo
	 * @param limiteMinimo
	 *            peso m�nimo
	 * @param mod
	 *            tipo do m�dulo
	 * @see #calcule(String)
	 * @since 0.2
	 */
	public Modulo(int mod, int limiteMaximo, int limiteMinimo) {
		super();

		this.limiteMaximo = limiteMaximo;
		this.limiteMinimo = limiteMinimo;
		this.mod = mod;
	}

	/**
	 * <p>
	 * Retorna o valor da inst�ncia do m�dulo de acordo com a <code>enum</code>
	 * da inst�ncia.
	 * </p>
	 * 
	 * <p>
	 * Se por um acaso a inst�ncia <code>enum</code> for nula uma
	 * <code>NullPointerException</code> ser� lan�ada. Caso a <code>enum</code>
	 * contenha um m�dulo n�o implementado por essa classe o retorno ser�
	 * <tt>-1</tt>
	 * </p>
	 * 
	 * @return valor da inst�ncia do m�dulo.
	 * 
	 * @since 0.2
	 */
	public int valor() {
		return mod;
	}

	/**
	 * <p>
	 * Executa o c�culo do m�dulo 11 com os limites definidos.
	 * </p>
	 * 
	 * <p>
	 * Executa o m�todo <code>calculeSomaSequencialMod11</code> e aplica o
	 * resultado em % 11
	 * </p>
	 * 
	 * @param numero
	 * @param limiteMin
	 * @param limiteMax
	 * @return resultado de calculeSomaSequencialMod11 % 11
	 * 
	 * @since 0.2
	 * @see #calculeSomaSequencialMod11(String, int, int)
	 */
	public static int calculeMod11(String numero, int limiteMin, int limiteMax)
			throws IllegalArgumentException {

		return (calculeSomaSequencialMod11(numero, limiteMin, limiteMax) % 11);
	}

	/**
	 * <p>
	 * Executa o c�culo do m�dulo 11 com os limites definidos.
	 * </p>
	 * 
	 * <p>
	 * Transforma o <code>numero</code> em string e executa o m�todo
	 * calculeMod11
	 * </p>
	 * 
	 * @param numero
	 * @param limiteMin
	 * @param limiteMax
	 * @return resultado do c�lculo
	 * 
	 * @since 0.2
	 * @see #calculeMod11(String, int, int)
	 */
	public static int calculeMod11(long numero, int limiteMin, int limiteMax) {
		return calculeMod11(String.valueOf(numero), limiteMin, limiteMax);
	}

	/**
	 * <p>
	 * Realiza o c�lculo da soma na forma do m�dulo 11.
	 * </p>
	 * <p>
	 * O m�dulo 11 funciona da seguinte maneira:
	 * </p>
	 * <p>
	 * Cada d�gito do n�mero, come�ando da direita para a esquerda (menos
	 * significativo para o mais significativo), � multiplicado pelo n�meros
	 * limite m�nimo, limite m�nimo + 1, limite m�nimo + 2 e assim
	 * sucessivamente at� o limite m�xmio definido, ent�o inicia-se novamente a
	 * contagem.
	 * </p>
	 * <p>
	 * Exemplo para o n�mero <tt>654321</tt>:
	 * 
	 * <pre>
	 * +---+---+---+---+---+---+
	 * | 6 | 5 | 4 | 3 | 2 | 1 |
	 * +---+---+---+---+---+---+
	 *   |   |   |   |   |   |
	 *  x7  x6  x5  x4  x3  x2
	 *   |   |   |   |   |   |
	 *  =42 =30 =20 =12 =6  =2
	 *   +---+---+---+---+---+-&gt;
	 * </pre
	 * 
	 * </p>
	 * 
	 * @param numero
	 * @param limiteMin
	 * @param limiteMax
	 * @return
	 * @throws IllegalArgumentException
	 * 
	 * @since 0.2
	 */

	public static int calculeSomaSequencialMod11(String numero, int limiteMin,
			int limiteMax) throws IllegalArgumentException {

		int peso = 0;
		int soma = 0;

		if (StringUtils.isNotBlank(numero) && StringUtils.isNumeric(numero)) {

			StringBuilder sb = new StringBuilder(numero);
			sb.reverse();

			peso = limiteMin;

			for (char c : sb.toString().toCharArray()) {
				soma += peso * Character.getNumericValue(c);
				peso++;

				if (peso > limiteMax)
					peso = limiteMin;
			}
		} else
			throw new IllegalArgumentException(
					O_ARGUMENTO_DEVE_CONTER_APENAS_NUMEROS);

		return soma;
	}

	/**
	 * <p>
	 * Executa o c�culo do m�dulo 10 com os limites definidos.
	 * </p>
	 * 
	 * <p>
	 * Transforma o <code>numero</code> em string e executa o m�todo
	 * calculeMod10
	 * </p>
	 * 
	 * @param numero
	 * @param limiteMin
	 * @param limiteMax
	 * @return resultado do c�lculo
	 * 
	 * @since 0.2
	 * @see #calculeMod10(String, int, int)
	 */
	public static int calculeMod10(long numero, int limiteMin, int limiteMax) {
		return calculeMod10(String.valueOf(numero), limiteMin, limiteMax);
	}

	/**
	 * <p>
	 * Executa o c�culo do m�dulo 10 com os limites definidos.
	 * </p>
	 * 
	 * <p>
	 * Executa o m�todo <code>calculeSomaSequencialMod10</code> e aplica o
	 * resultado em % 10
	 * </p>
	 * 
	 * @param numero
	 * @param limiteMin
	 * @param limiteMax
	 * @return resultado de calculeSomaSequencialMod10 % 10
	 * 
	 * @since 0.2
	 * @see #calculeSomaSequencialMod10(String, int, int)
	 */
	public static int calculeMod10(String numero, int limiteMin, int limiteMax)
			throws IllegalArgumentException {

		return (calculeSomaSequencialMod10(numero, limiteMin, limiteMax) % 10);
	}

	/**
	 * <p>
	 * Realiza o c�lculo da soma na forma do m�dulo 10.
	 * </p>
	 * <p>
	 * O m�dulo 10 funciona da seguinte maneira:
	 * </p>
	 * <p>
	 * Cada d�gito do n�mero, come�ando da direita para a esquerda (menos
	 * significativo para o mais significativo), � multiplicado pelo n�meros
	 * limite m�nimo, limite m�nimo + 1, limite m�nimo + 2 e assim
	 * sucessivamente at� o limite m�xmio definido, ent�o inicia-se novamente a
	 * contagem.
	 * </p>
	 * <p>
	 * Exemplo para o n�mero <tt>123456</tt>:
	 * 
	 * <pre>
	 * +---+---+---+---+---+---+
	 * | 1 | 2 | 3 | 4 | 5 | 6 |
	 * +---+---+---+---+---+---+
	 *   |   |   |   |   |   |
	 *  x1  x2  x1  x2  x1  x2
	 *   |   |   |   |   |   |
	 *  =1  =4  =3  =8  =5  =[ 3 &lt;= ( 1 + 2 &lt;==12 ) ] = 24
	 *   +---+---+---+---+---+-&gt; = (24 / 10) = 3, resto 3; Ent�o o m�dulo � igual a 3.
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * Geralmente os limites para o m�dulo 10 s�o m�nimo 1 e m�ximo 2 apenas.
	 * </p>
	 * 
	 * @param numero
	 * @param limiteMin
	 * @param limiteMax
	 * @return soma sequencial usada no c�lculo do m�dulo
	 * @throws IllegalArgumentException
	 * 
	 * @since 0.2
	 */
	public static int calculeSomaSequencialMod10(String numero, int limiteMin,
			int limiteMax) throws IllegalArgumentException {

		int produto = 0;
		int peso = 0;
		int soma = 0;

		if (StringUtils.isNotBlank(numero) && StringUtils.isNumeric(numero)) {

			StringBuilder sb = new StringBuilder(numero);
			sb.reverse();

			peso = limiteMax;

			for (char c : sb.toString().toCharArray()) {

				produto = peso * Character.getNumericValue(c);

				if (produto > 9) {

					soma += produto / 10;
					soma += produto % 10;
				} else
					soma += produto;

				peso = (peso == limiteMax) ? limiteMin : limiteMax;
			}

		} else
			throw new IllegalArgumentException(O_ARGUMENTO_DEVE_CONTER_APENAS_NUMEROS);

		return soma;
	}

	/**
	 * <p>
	 * Executa o c�culo do m�dulo da inst�ncia.
	 * </p>
	 * 
	 * @param numero
	 * @return
	 * @throws IllegalArgumentException
	 * 
	 * @since 0.2
	 */

	public int calcule(String numero) throws IllegalArgumentException {
		int modulo = 0;

		switch (mod) {
			case MODULO10:
				modulo = calculeMod10(numero, getLimiteMinimo(), getLimiteMaximo());
				break;
	
			case MODULO11:
				modulo = calculeMod11(numero, getLimiteMinimo(), getLimiteMaximo());
				break;
		}

		return modulo;
	}

	/**
	 * <p>
	 * Executa o c�culo do m�dulo da inst�ncia.
	 * </p>
	 * 
	 * @param numero
	 * @return
	 * 
	 * @since 0.2
	 */

	public int calcule(long numero) {
		return calcule(String.valueOf(numero));
	}

	/**
	 * <p>
	 * Inicializa as vari�veis <code>limiteMaximo</code> e
	 * <code>limiteMinimo</code> com os valores padr�es de acordo com a
	 * inst�ncia do m�dulo da classe.
	 * </p>
	 * 
	 * <p>
	 * Valores padr�es: <br />
	 * <br />
	 * <code>MODULO10</code>: (limiteMinimo = 1 e limiteMaximo = 2)<br />
	 * 
	 * <code>MODULO11</code>: (limiteMinimo = 2 e limiteMaximo = 9)<br />
	 * </p>
	 * 
	 * @since 0.2
	 */

	private void initDefault() {
		switch (mod) {
			case MODULO10:
				setLimiteMinimo(1);
				setLimiteMaximo(2);
				break;
	
			case MODULO11:
				setLimiteMinimo(2);
				setLimiteMaximo(9);
				break;
		}
	}

	/**
	 * @return the limiteMaximo
	 */
	public int getLimiteMaximo() {
		return limiteMaximo;
	}

	/**
	 * @param limiteMaximo
	 *            the limiteMaximo to set
	 */
	public void setLimiteMaximo(int limiteMaximo) {
		this.limiteMaximo = limiteMaximo;
	}

	/**
	 * @return the limiteMinimo
	 */
	public int getLimiteMinimo() {
		return limiteMinimo;
	}

	/**
	 * @param limiteMinimo
	 *            the limiteMinimo to set
	 */
	public void setLimiteMinimo(int limiteMinimo) {
		this.limiteMinimo = limiteMinimo;
	}

	/**
	 * @return the mod
	 */
	public int getMod() {
		return mod;
	}

	/**
	 * @param mod
	 *            the mod to set
	 */
	public void setMod(int mod) {
		this.mod = mod;
	}

}
