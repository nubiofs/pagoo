package br.com.infosolo.comum.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Rotinas diversas para manipulação de strings.
 * 
 * @author Leandro Lima (leandro.lima@infsolo.com.br)
 *
 */
public class TextoUtil {
	public static final String LOCALE_LANGUAGE = "pt";
	public static final String LOCALE_COUNTRY = "BR";

	public static final String ESPACO_BRANCO = " ";

	/**
	 * Formata um numero de CPF informado sem pontuacoes para o formato com pontuacoes. 
	 * @param cpf
	 * @return
	 */
	public static String formataNumeroCPF(String cpf) {
		String numeroFormatado = "";
		
		try {
			cpf = String.format("%011d", new Long(cpf));
			StringBuilder codigoFormatado = new StringBuilder(cpf);

			codigoFormatado.insert(3, '.');
			codigoFormatado.insert(7, '.');
			codigoFormatado.insert(11, '-');
			
			numeroFormatado = codigoFormatado.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return numeroFormatado;
	}
	
	/**
	 * Formata um numero de CPF informado sem pontuacoes para o formato com pontuacoes. 
	 * @param cpf
	 * @return
	 */
	public static String formataNumeroCNPJ(String cnpj) {
		String numeroFormatado = "";
		
		try {
			cnpj = String.format("%014d", new Long(cnpj));
			StringBuilder codigoFormatado = new StringBuilder(cnpj);

			codigoFormatado.insert(2, '.');
			codigoFormatado.insert(6, '.');
			codigoFormatado.insert(10, '/');
			codigoFormatado.insert(15, '-');
			
			numeroFormatado = codigoFormatado.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return numeroFormatado;
	}
	
	/**
	 * Formata um CPF ou CNPJ dependendo do tamanho do número informado.
	 * @param numero
	 * @return
	 */
	public static String formataNumeroCPFCNPJ(String numero) {
		if (numero.length() > 11)
			return formataNumeroCNPJ(numero);
		else
			return formataNumeroCPF(numero);
	}
	
	/**
	 * Formata um número de CEP
	 * @param numero
	 * @return
	 */
	public static String formataNumeroCEP(String numero) {
		String numeroFormatado = "";
		
		try {
			StringBuilder codigoFormatado = new StringBuilder(numero);
			codigoFormatado.insert(5, '-');
			
			numeroFormatado = codigoFormatado.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return numeroFormatado;
	}

	/**
	 * Retorna um string com a formatação definida de um número flutuante.
	 * @param valor
	 * @return
	 */
	public static String formataValor(Double valor) {
		NumberFormat nf = NumberFormat.getInstance(new Locale(LOCALE_LANGUAGE, LOCALE_COUNTRY));
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		nf.setMinimumIntegerDigits(1);
		return nf.format(valor);
	}

	/**
	 * <p>
	 * Verifica se o objeto <strong>n�o</strong> � nulo e lan�a <code>NullPointerException</code>, com
	 * a mensagem informada, caso seja.
	 * </p>
	 * 
	 * @param object - Objeto analisado
	 * @param message - Mensagem utilizada na exce��o
	 * 
	 * @thows NullPointerException - Caso o objeto seja <code>null</code>.
	 * 
	 * @see #isNull(Object)
	 * @see #isNotNull(Object)
	 * 
	 * @since 0.2
	 */
	public static void checkNotNull(Object object, String message) {
		if (object == null) {
			throw new NullPointerException(message);
		}
	}

	/**
	 * <p>
	 * Verifica se o objeto <strong>nao</strong> é nulo e lança <code>NullPointerException</code> 
	 * caso seja.
	 * </p>
	 * 
	 * @param object - Objeto analisado
	 * 
	 * @thows NullPointerException - Caso o objeto seja <code>null</code>.
	 * 
	 * @see #checkNotNull(Object, String)
	 * @see #isNull(Object)
	 * @see #isNotNull(Object)
	 * 
	 * @since 0.2
	 */
	public static void checkNotNull(Object object) {
		checkNotNull(object, "Objeto nulo");
	}

	/**
	 * <p>
	 * Verifica se o objeto e nulo e lanca <code>IllegalArgumentException</code>, com a mensagem 
	 * informada, caso <strong>nao</strong> seja.
	 * </p>
	 * 
	 * @thows IllegalArgumentException - Caso o objeto <strong>nao</strong> seja <code>null</code>.
	 * 
	 * @see #isNull(Object)
	 * @see #isNotNull(Object)
	 * 
	 * @since 0.2
	 * 
	 * @param object - Objeto analisado
	 * @param message - Mensagem utilizada na exce��o
	 */
	public static void checkNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * <p>
	 * Verifica se o objeto � nulo e lan�a <code>IllegalArgumentException</code> caso 
	 * <strong>n�o</strong> seja.
	 * </p>
	 * 
	 * @param object - Objeto analisado
	 * 
	 * @thows IllegalArgumentException - Caso o objeto <strong>n�o</strong> seja <code>null</code>.
	 * 
	 * @see #checkNull(Object, String)
	 * @see #isNull(Object)
	 * @see #isNotNull(Object)
	 * 
	 * @since 0.2
	 */
	public static void checkNull(Object object) {
		checkNull(object, "Objeto não nulo. Valor [" + object + "]");
	}

	/**
	 * <p>
	 * Metodo privado para fins de reutilizacao de codigo.
	 * </p>
	 * <p>
	 * Verifica se a <code>String</code> passada por parametro nao é <code>null</code>, 
	 * nao é vazia (<code>""</code>) e não possui apenas espa�os em branco. 
	 * </p>
	 * <p>
	 * Lanca <code>NullPointerException</code>, com a mensagem definida em <code>messageNullPointer</code> 
	 * (segundo par�metro String), caso o valor passado seja <code>null</code>
	 * </p>
	 * <p>
	 * Lanca <code>IllegalArgumentException</code>, com a mensagem definida em <code>messageIllegalArgument</code> 
	 * (terceiro par�metro String), caso o valor passado seja vazio ou contenha apenas espa�os em branco.
	 * </p>
	 * 
	 * @param value - String analisada
	 * 
	 * @throws NullPointerException - Caso a string seja <code>null</code>.
	 * @thows IllegalArgumentException - Caso a string seja vazia ou contenha apenas espa�os em branco.
	 * 
	 * @since 0.2
	 */
	private static void checkNotBlank(String value, String messageNullPointer, String messageIllegalArgument) {
		if (value == null) {
			throw new NullPointerException(messageNullPointer);
		}

		if (StringUtils.isBlank(value)) {
			throw new IllegalArgumentException(messageIllegalArgument);
		}
	}

	/**
	 * <p>
	 * Elimina simbolos como: <pre>><,;.:!*&%+-_<>[]\/</pre>
	 * </p>
	 * 
	 * @param str
	 *            String com os símbolos a serem removidos.
	 * @return String sem símbolos.
	 * @since 0.2
	 */
	public static String retiraSimbolos(final String str) {
		String modifiedStr = str;

		if (modifiedStr != null) {
			modifiedStr = StringUtils.replace(modifiedStr, "-", "");
			modifiedStr = StringUtils.replace(modifiedStr, "_", "");
			modifiedStr = StringUtils.replace(modifiedStr, "=", "");
			modifiedStr = StringUtils.replace(modifiedStr, "+", "");
			modifiedStr = StringUtils.replace(modifiedStr, "%", "");
			modifiedStr = StringUtils.replace(modifiedStr, "*", "");
			modifiedStr = StringUtils.replace(modifiedStr, "@", "");
			modifiedStr = StringUtils.replace(modifiedStr, "#", "");
			modifiedStr = StringUtils.replace(modifiedStr, "&", "");
			modifiedStr = StringUtils.replace(modifiedStr, ":", "");
			modifiedStr = StringUtils.replace(modifiedStr, ".", "");
			modifiedStr = StringUtils.replace(modifiedStr, ";", "");
			modifiedStr = StringUtils.replace(modifiedStr, ",", "");
			modifiedStr = StringUtils.replace(modifiedStr, "!", "");
			modifiedStr = StringUtils.replace(modifiedStr, "?", "");
			modifiedStr = StringUtils.replace(modifiedStr, "(", "");
			modifiedStr = StringUtils.replace(modifiedStr, ")", "");
			modifiedStr = StringUtils.replace(modifiedStr, "{", "");
			modifiedStr = StringUtils.replace(modifiedStr, "}", "");
			modifiedStr = StringUtils.replace(modifiedStr, "[", "");
			modifiedStr = StringUtils.replace(modifiedStr, "]", "");
			modifiedStr = StringUtils.replace(modifiedStr, "/", "");
			modifiedStr = StringUtils.replace(modifiedStr, "\\", "");
			modifiedStr = StringUtils.replace(modifiedStr, ">", "");
			modifiedStr = StringUtils.replace(modifiedStr, "<", "");
			modifiedStr = StringUtils.replace(modifiedStr, "\"", "");
			modifiedStr = StringUtils.replace(modifiedStr, "'", "");
			modifiedStr = StringUtils.replace(modifiedStr, "`", "");
		}

		return modifiedStr;
	}

	/**
	 * <p>
	 * Remove os zeros iniciais de uma <code>String</code>, seja ela num�rica ou
	 * n�o.
	 * </p>
	 * <p>
	 * <code>removeStartWithZeros("00000") => 0</code><br />
	 * <code>removeStartWithZeros("00023") => 23</code><br />
	 * <code>removeStartWithZeros("02003") => 2003</code>
	 * <p>
	 * 
	 * @param str
	 * @return a string sem zeros inicias ou um �nico zero.
	 * 
	 * @since 0.2
	 */

	public static String removeStartWithZeros(final String str) {
		String withoutZeros = "";
		final String zero = "0";

		if (str != null) {
			if (StringUtils.startsWith(str, zero)) {
				withoutZeros = StringUtils.removeStart(str, zero);

				while (StringUtils.startsWith(withoutZeros, zero)) {
					withoutZeros = StringUtils.removeStart(withoutZeros, zero);
				}

				if (withoutZeros.trim().length() == 0) {
					withoutZeros = zero;
				}

			} else {
				withoutZeros = str;
			}
		}

		return withoutZeros;
	}

	/**
	 * <p>
	 * Remove a acentua��o do texto, que inclui os acentos:
	 * <ul>
	 * <li>Agudo. ex.: �</li>
	 * <li>Grave. ex.: �</li>
	 * <li>Til. ex.: �</li>
	 * <li>Trema. ex.: �</li>
	 * <li>Circunflexo. ex.: �</li>
	 * </ul>
	 * e o Cedilha (�).
	 * </p>
	 * <p>
	 * Os acentos s�o removidos tanto para letras min�sculas como para letras
	 * mai�sculas.
	 * </p>
	 * 
	 * @param value
	 *            String com os caracteres a serem removidos.
	 * @return String sem acentua��o.
	 * @since 0.2
	 */
	public static String eliminateAccent(final String value) {
		String modifiedValue = value;

		// Para � e �
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00E7', 'c');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00C7', 'C');

		// Para �, �, �, � e �
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00E0', 'a');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00E1', 'a');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00E2', 'a');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00E3', 'a');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00E4', 'a');

		// Para �, �, � e �
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00E8', 'e');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00E9', 'e');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00EA', 'e');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00EB', 'e');

		// Para �, �, � e �
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00EC', 'i');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00ED', 'i');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00EE', 'i');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00EF', 'i');

		// Para �, �, �, � e �
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00F2', 'o');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00F3', 'o');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00F4', 'o');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00F5', 'o');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00F6', 'o');

		// Para �, �, � e �
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00F9', 'u');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00FA', 'u');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00FB', 'u');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00FC', 'u');

		// Para �, �, �, � e �
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00C0', 'A');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00C1', 'A');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00C2', 'A');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00C3', 'A');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00C4', 'A');

		// Para �, �, � e �
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00C8', 'E');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00C9', 'E');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00CA', 'E');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00CB', 'E');

		// Para �, �, � e �
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00CC', 'I');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00CD', 'I');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00CE', 'I');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00CF', 'I');

		// Para �, �, �, � e �
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00D2', 'O');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00D3', 'O');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00D4', 'O');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00D5', 'O');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00D6', 'O');

		// Para �, �, � e �
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00D9', 'U');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00DA', 'U');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00DB', 'U');
		modifiedValue = StringUtils.replaceChars(modifiedValue, '\u00DC', 'U');

		return modifiedValue;
	}
	
	/**
	 * <p>
	 * Verifica se a <code>String</code> passada por par�metro não é <code>null</code>, 
	 * não é vazia (<code>""</code>) e não possui apenas espaços em branco. 
	 * </p>
	 * <p>
	 * Lança exceção, com a mensagem passada por parâmetro (segundo parâmetro String), 
	 * caso não preencha estes requisitos.
	 * </p>
	 * 
	 * @param value - String analisada
	 * 
	 * @throws NullPointerException - Caso a string seja <code>null</code>.
	 * @thows IllegalArgumentException - Caso a string seja vazia ou contenha apenas espaços em branco.
	 * 
	 * @since 0.2
	 */
	public static void checkNotBlank(String value, String message) {
		checkNotBlank(value, message, message);
	}
	
	/**
	 * <p>
	 * Verifica se a <code>String</code> passada por parâmetro não é <code>null</code>, 
	 * não é vazia (<code>""</code>) e não possui apenas espaços em branco. 
	 * Lança exceção caso não preencha estes requisitos.
	 * </p>
	 * 
	 * @param value - String analisada
	 * 
	 * @throws NullPointerException - Caso a string seja <code>null</code>.
	 * @thows IllegalArgumentException - Caso a string seja vazia ou contenha apenas espaços em branco.
	 * 
	 * @since 0.2
	 */
	public static void checkNotBlank(String value) {
		checkNotBlank(value, "String nula", "Valor inválido. String vazia ou contendo apenas espaços em brancos");
	}

	
	/**
	 * Gera um texto UUID
	 * @return
	 */
	public static String gerarTextoUUID() {
		//
		// Creating a random UUID (Universally unique identifier).
		//
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		
		return randomUUIDString;
	}

	/**
	 * Transforma um valor string em um double.
	 */
	public static double parseDouble(String value) {
		if (value != null) {
			value = value.replaceAll("[.]","").replace(',','.');
			if (value.equals("")) value = "0.0";
			return Double.parseDouble(value);
		}
		return 0;
	}

	/**
	 * Exibe os valores de instância para um objeto.
	 * 
	 * @see org.apache.commons.lang.builder.ToStringBuilder#reflectionToString
	 * 
	 * @see java.lang.Object#toString()
	 */
	public static String toString(Object obj) {
		return ToStringBuilder.reflectionToString(obj);
	}
	
	/**
	 * Retorna um string de um InputStream
	 * @param is
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String readStringFromStream(InputStream is, String charset) throws IOException {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte[] bytes = new byte[4096];
	    
	    for (int len;(len = is.read(bytes))>0;)
	        baos.write(bytes, 0, len);
	    
	    return new String(baos.toByteArray(), charset);
	}
}
