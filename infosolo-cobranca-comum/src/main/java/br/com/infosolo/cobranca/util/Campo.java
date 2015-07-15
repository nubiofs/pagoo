package br.com.infosolo.cobranca.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import br.com.infosolo.comum.util.TextoUtil;

/**
 * 
 * <p>
 * Um campo de texto, numero, data ou outro como <code>TextStream</code>.
 * </p>
 * 
 * @see TextoStream
 * 
 */
public class Campo<G> implements TextoStream {
	private static final long serialVersionUID = -7432509456997808459L;

	/**
	 * <p>
	 * Tamanho do campo como string.
	 * </p>
	 */
	private Integer length;

	/**
	 * <p>
	 * Valor do campo.
	 * </p>
	 */
	private G value;

	/**
	 * <p>
	 * Formatador utilizado na leitura e na escrita do value.
	 * </p>
	 * 
	 */
	private Format format;

	/**
	 * <p>
	 * Preenchedor do value utilizado na hora da escrita.
	 * </p>
	 */
	private Preenchedor<?> filler;

	/**
	 * <p>
	 * Cria um <code>Field</code> sem um formatador. Isto significa que a
	 * leitura da String pelo objeto criado sera como uma atribuicao simples.
	 * </p>
	 * 
	 * @param value
	 *            Valor do campo
	 * @param length
	 *            Tamanho que o value deve possuir.
	 * @since 0.2
	 */
	public Campo(G value, Integer length) {
		setValue(value);
		setLength(length);
	}

	/**
	 * <p>
	 * Cria um <code>Field</code> com um formatador. Isto significa que a
	 * leitura da String pelo objeto criado sera de acordo com o formatador.
	 * </p>
	 * 
	 * @param value
	 *            Valor do campo
	 * @param length
	 *            tamanho do campo
	 * @param format
	 *            Formatador que ira formatar a String fornecida na leitura para
	 *            o value especificado.
	 * @since 0.2
	 */
	public Campo(G value, Integer length, Format format) {
		setLength(length);
		setValue(value);
		setFormat(format);
	}

	/**
	 * <p>
	 * Cria um <code>Field</code> com um preenchedor. Este preenchedor e
	 * utilizado na escrita do <code>Field</code> quado e necessario preencher
	 * com caracteres especificados ate o length definido.
	 * </p>
	 * 
	 * @param value
	 *            valor do campo
	 * @param length
	 *            tamaho do campo
	 * @param filler
	 *            preenchedor
	 * @since 0.2
	 */
	public Campo(G value, Integer length, Preenchedor<?> filler) {
		setLength(length);
		setValue(value);
		setFiller(filler);
	}

	/**
	 * <p>
	 * Cria um <code>Field</code> com um formatador e com um preenchedor.
	 * </p>
	 * 
	 * @param value
	 *            Valor do campo
	 * @param length
	 *            Tamanho do campo
	 * @param format
	 *            Formatador que ira formatar a String fornecida na leitura para
	 *            o value especificado.
	 * @param filler
	 *            preenchedor
	 * @since 0.2
	 */
	public Campo(G value, Integer length, Format format, Preenchedor<?> filler) {
		setLength(length);
		setValue(value);
		setFormat(format);
		setFiller(filler);
	}

	/**
	 * <p>
	 * Converte a String fornecida para o value representado pelo objeto.
	 * </p>
	 * <p>
	 * A conversao e realizada a partir do formatador fornecido para o objeto.
	 * Se não houver formatador a String fornecida sera atribuida como o valor
	 * do value.
	 * </p>
	 * 
	 * @param valueAsString
	 *            valor do campo como uma String
	 * @since 0.2
	 */
	public void read(String valueAsString) {
		if (valueAsString == null) {
			throw new IllegalArgumentException("String inválida [ " + valueAsString + " ]!");
		}

		if (valueAsString.length() != length) {
			throw new IllegalArgumentException("O tamanho da String [ "
					+ valueAsString + " ] é incompatível com o especificado [ "
					+ length + " ]!");
		}

		if (value instanceof TextoStream) {
			TextoStream reader = (TextoStream) value;
			reader.read(valueAsString);

		} else if (value instanceof BigDecimal) {
			readDecimalField(valueAsString);

		} else if (value instanceof Date) {
			readDateField(valueAsString);

		} else {
			readStringOrNumericField(valueAsString);
		}
	}

	@SuppressWarnings("unchecked")
	private void readStringOrNumericField(String valueAsString) {
		Class<?> c = value.getClass();

		for (Constructor<?> cons : c.getConstructors()) {
			if (cons.getParameterTypes().length == 1) {
				if (cons.getParameterTypes()[0].equals(String.class)) {
					try {
						value = (G) cons.newInstance(valueAsString);

					} catch (IllegalArgumentException e) {
						getGenericReadError(e, valueAsString).printStackTrace();
						
					} catch (InstantiationException e) {
						getGenericReadError(e, valueAsString).printStackTrace();
						
					} catch (IllegalAccessException e) {
						getGenericReadError(e, valueAsString).printStackTrace();
						
					} catch (InvocationTargetException e) {
						getGenericReadError(e, valueAsString).printStackTrace();
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void readDateField(String valueAsString) {
		try {
			value = (G) format.parseObject(valueAsString);
		} catch (ParseException e) {
			getGenericReadError(e, valueAsString);
		}
	}

	@SuppressWarnings("unchecked")
	private void readDecimalField(String valueAsString) {
		DecimalFormat decimalFormat = (DecimalFormat) format;

		try {
			Long parsedValue = (Long) format.parseObject(valueAsString);
			BigDecimal decimalValue = new BigDecimal(parsedValue.longValue());
			decimalValue = decimalValue.movePointLeft(decimalFormat.getMaximumFractionDigits());

			value = (G) decimalValue;

		} catch (ParseException e) {
			getGenericReadError(e, valueAsString);
		}
	}

	/**
	 * <p>
	 * Escreve o campo no formato e tamanho especificado.
	 * </p>
	 * 
	 * @see TextoStream#write()
	 * 
	 * @return campo escrito
	 * @since 0.2
	 */
	public String write() {
		String str = null;

		if (value instanceof TextoStream) {
			TextoStream its = (TextoStream) value;
			str = its.write();

		} else if (value instanceof Date) {
			Date campoDate = (Date) value;

			if (campoDate == null) {
				str = StringUtils.EMPTY;

			} else {
				str = format.format(value);
			}

		} else if (value instanceof BigDecimal) {
			str = new DecimalFormat("0.00").format(value);
			str = StringUtils.replaceChars(str, ",", StringUtils.EMPTY);
			str = StringUtils.replaceChars(str, ".", StringUtils.EMPTY);

		} else {
			str = value.toString();
		}

		str = fill(str);

		if (str.length() != length) {
			throw new IllegalArgumentException("O tamaho do campo [ " + str
					+ " ] é incompatível com o especificado [" + length + "]!");
		}

		return TextoUtil.eliminateAccent(str).toUpperCase();
	}

	private String fill(String str) {
		if (filler != null) {
			str = filler.fill(str, length);
		}

		return str;
	}

	/**
	 * <p>
	 * Retorna o valor que o campo contem.
	 * </p>
	 * 
	 * @return objeto valor do campo
	 * 
	 * @since 0.2
	 */

	public G getValue() {
		return value;
	}

	/**
	 * <p>
	 * Atribui um valor a instancia.
	 * </p>
	 * 
	 * @param field
	 * 
	 * @since 0.2
	 */

	public void setValue(G field) {
		if (field != null) {
			this.value = field;

		} else {
			throw new IllegalArgumentException("Campo inválido [" + field + "]!");
		}
	}

	/**
	 * <p>
	 * Tamanho do campo como string.
	 * </p>
	 * 
	 * @return
	 * 
	 * @since 0.2
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * <p>
	 * Atribui um tamanho (maior que zero) a instancia.
	 * </p>
	 * 
	 * @param length
	 * 
	 * @since 0.2
	 */
	public void setLength(Integer length) {
		if (length > 0) {
			this.length = length;

		} else {
			throw new IllegalArgumentException("Tamanho inválido [ " + length + " ]!");
		}
	}

	/**
	 * <p>
	 * Formatador utilizado na leitura e na escrita do value.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>Na leitura para realizar o <code>parse</code> da String.</li>
	 * <li>Na escrita para transformar o objeto em uma String e assim ser
	 * possivel trata-la para ser escrita.</li>
	 * </ul>
	 * </p>
	 * 
	 * @return formatador
	 * 
	 * @since 0.2
	 */
	public Format getFormat() {
		return format;
	}

	/**
	 * <p>
	 * Atribui um formatador a instancia.
	 * </p>
	 * 
	 * @param format
	 * 
	 * @since 0.2
	 */
	public void setFormat(Format format) {
		if (format != null) {
			this.format = format;

		} else {
			throw new IllegalArgumentException("Formato inválido [ " + format + " ]!");
		}
	}

	/**
	 * <p>
	 * Retorna o preenchedor do campo.
	 * </p>
	 * 
	 * @return preenchedor
	 * 
	 * @since 0.2
	 */
	public Preenchedor<?> getFiller() {
		return filler;
	}

	/**
	 * <p>
	 * Atribui um preenchedor a instancia.
	 * </p>
	 * 
	 * @param filler
	 * 
	 * @since 0.2
	 */
	public void setFiller(Preenchedor<?> filler) {
		if (filler != null) {
			this.filler = filler;

		} else {
			throw new IllegalArgumentException("Filler inválido [ " + filler + " ]!");
		}
	}

	private static Exception getGenericReadError(Exception e, String value) {
		StackTraceElement[] stackTrace = e.getStackTrace();
		e = new RuntimeException("VALOR INVALIDO [ " + value + " ]!\nCausado por: " + e.getCause());
		e.setStackTrace(stackTrace);

		return e;
	}

}