package br.com.infosolo.comum.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

public class TypesConverter {

	/**
	 * Converte um objeto para o tipo Integer
	 * @param object
	 * @return
	 */
	public static Integer toInteger(Object object) {
		if (object == null)
			return null;
		else if (object instanceof BigInteger) {
			return ((BigInteger) object).intValue();
		}
		else if (object instanceof BigDecimal) {
			return ((BigDecimal) object).intValue();
		}
		else if (object instanceof String) {
			return new Integer((String) object);
		}
		else if (object instanceof Double) {
			return new Integer(((Double) object).intValue());
		}
		return null;
	}
	
	/**
	 * Converte um objeto para o tipo Double
	 * @param object
	 * @return
	 */
	public static Double toDouble(Object object) {
		if (object == null)
			return null;
		else if (object instanceof BigInteger) {
			return ((BigInteger) object).doubleValue();
		}
		else if (object instanceof BigDecimal) {
			return ((BigDecimal) object).doubleValue();
		}
		else if (object instanceof String) {
			return new Double((String) object);
		}
		else if (object instanceof Double) {
			return new Double(((Double) object).intValue());
		}
		return null;
	}
	
	/**
	 * Converte um objeto para o tipo Date
	 * @param object
	 * @return
	 */
	public static Date toDate(Object object) {
		if (object == null)
			return null;
		else if (object instanceof Timestamp) {
			return new Date(((Timestamp) object).getTime());
		}
		return (Date) object;
	}

	/**
	 * Converte um objeto para o tipo String
	 * @param object
	 * @return
	 */
	public static String toString(Object object) {
		if (object == null)
			return null;
		else if (object instanceof Timestamp) {
			return DataUtil.formatarData(new Date(((Timestamp) object).getTime()));
		}
		else if (object instanceof Date) {
			return DataUtil.formatarData(((Timestamp) object));
		}
		return object.toString();
	}
}
