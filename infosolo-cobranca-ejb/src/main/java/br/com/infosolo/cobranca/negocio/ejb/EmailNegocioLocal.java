package br.com.infosolo.cobranca.negocio.ejb;

import javax.ejb.Local;

@Local
public interface EmailNegocioLocal {
	
	/**
	 * Método para envio de email
	 * @param listaEmail
	 * @param assunto
	 * @param texto
	 */
	public boolean enviarMensagemEmail(String[] listaEmail, String assunto, String texto,
			byte[] boletoFisico, byte[] anexoRelatorio, byte[] anexoPlanilha);
	
}
