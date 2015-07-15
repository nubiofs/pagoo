package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.util.TextoStream;

/**
 * <p>
 * Essa é uma Interace com um propósito de marcar e agrupar tipos campo livre.
 * </p>
 * 
 */
public interface CampoLivre extends TextoStream {

	/**
	 * Tamanho do Campo Livre, igual para qualquer que seja o banco.
	 */
	static final Integer TAMANHO_CAMPO_LIVRE = 25;
	
}
