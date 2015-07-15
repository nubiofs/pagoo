package br.com.infosolo.comum.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AssinaturaUtil {

	/**
	 * Gera uma nova assinatura para ser usada nas transacoes.
	 * 
	 * @see http://www.javapractices.com/topic/TopicAction.do?Id=56
	 * @see http://download.oracle.com/javase/1.4.2/docs/guide/security/CryptoSpec.html#AppA
	 * 
	 * @return
	 */
	public static String gerarAssinatura() {
		String assinatura = null;
		
		try {
			// Initialize SecureRandom
			// This is a lengthy operation, to be done only upon
			// initialization of the application
			SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

			// generate a random number
			String randomNum = new Integer(prng.nextInt()).toString();

			// get its digest
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] result = sha.digest(randomNum.getBytes());

			assinatura = hexEncode(result);
			
			assinatura = assinatura.substring(0, 21);
			
			System.out.println("Random number: " + randomNum);
			System.out.println("Message digest: " + assinatura);
			
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex);
		}
		return assinatura;
	}
	
	/**
	  * The byte[] returned by MessageDigest does not have a nice
	  * textual representation, so some form of encoding is usually performed.
	  *
	  * This implementation follows the example of David Flanagan's book
	  * "Java In A Nutshell", and converts a byte array into a String
	  * of hex characters.
	  *
	  * Another popular alternative is to use a "Base64" encoding.
	  */
	private static String hexEncode(byte[] aInput) {
		StringBuilder result = new StringBuilder();
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		for (int idx = 0; idx < aInput.length; ++idx) {
			byte b = aInput[idx];
			result.append(digits[(b & 0xf0) >> 4]);
			result.append(digits[b & 0x0f]);
		}
		return result.toString();
	}
}
