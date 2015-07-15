package br.com.infosolo.comum.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validacao de email.
 * 
 * Source: http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
 * 
 * @author Leandro
 *
 */
public class EmailValidator {
	private static Pattern pattern;
	private static Matcher matcher;

	//private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	static {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	/**
	 * Validate hex with regular expression
	 * 
	 * @param hex
	 *            hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public static boolean validate(final String hex) {
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}
	
	public static void main(String[] args) {
		String[] emails = { "RNEVES@GRUPODISAL.COM.BR",
				"RAQUINO@GRUPODISAL.COM.BR",
				"MARIA-OZELIA.FERNANDES@ITAU-UNIBANCO.COM.BR",
				"CARLOS.SANTOS@BICBANCO.COM.BR",
				"JOSE.IVANEZ@BICBANCO.COM.BR"
		};
		
		for (String email : emails) {
			boolean valid = validate(email);
			System.out.println("Validar: " + email + " => " + (valid ? "Válido" : "Inválido"));
		}
	}
}
