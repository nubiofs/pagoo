package br.com.ael.infosolo.pagoo.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Classe para erros de autenticação.
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 02/07/2015
 *
 */
public class PagooAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PagooAuthenticationException(String msg) {
		super(msg);
	}
	
	public PagooAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

}
